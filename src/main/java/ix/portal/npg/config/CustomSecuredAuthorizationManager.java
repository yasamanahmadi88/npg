package ix.portal.npg.config;

import ix.portal.npg.security.AuthoritiesConstants;
import ix.portal.npg.security.SecurityUtils;
import ix.portal.npg.service.ResourceAuthorityQueryService;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * AuthorizationManager replacement for the legacy AccessDecisionManager-based method security.
 *
 * The application uses @Secured("resourceName") to represent a domain resource,
 * not a direct Spring Security GrantedAuthority.
 */
@Component
public class CustomSecuredAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    private final ResourceAuthorityQueryService resourceAuthorityQueryService;

    public CustomSecuredAuthorizationManager(ResourceAuthorityQueryService resourceAuthorityQueryService) {
        this.resourceAuthorityQueryService = resourceAuthorityQueryService;
    }

    @Override
    public AuthorizationResult authorize(Supplier<? extends Authentication> authenticationSupplier, MethodInvocation methodInvocation) {
        Authentication authentication = authenticationSupplier.get();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        UserDetails user = SecurityUtils.getCurrentUser(authentication);
        if (user == null) {
            return new AuthorizationDecision(false);
        }

        if (isAdmin(user)) {
            return new AuthorizationDecision(true);
        }

        String resourceName = getSecuredResourceName(methodInvocation);
        if (resourceName == null || resourceName.isBlank()) {
            return new AuthorizationDecision(false);
        }

        String method = getResourceVerb(methodInvocation);
        return new AuthorizationDecision(hasAccessToResource(user, method, resourceName));
    }

    private String getSecuredResourceName(MethodInvocation methodInvocation) {
        Secured secured = methodInvocation.getMethod().getAnnotation(Secured.class);

        if (secured == null || secured.value().length == 0) {
            return null;
        }

        return secured.value()[0];
    }

    private String getResourceVerb(MethodInvocation methodInvocation) {
        if (methodInvocation.getMethod().isAnnotationPresent(GetMapping.class)) {
            return "VIEW";
        }
        if (
            methodInvocation.getMethod().isAnnotationPresent(PutMapping.class) ||
            methodInvocation.getMethod().isAnnotationPresent(PatchMapping.class)
        ) {
            return "EDIT";
        }
        if (methodInvocation.getMethod().isAnnotationPresent(PostMapping.class)) {
            return "CREATE";
        }
        if (methodInvocation.getMethod().isAnnotationPresent(DeleteMapping.class)) {
            return "DELETE";
        }

        return "NO_GRANT";
    }

    private boolean isAdmin(UserDetails user) {
        return user != null &&
            user.getAuthorities() != null &&
            user.getAuthorities().stream().anyMatch(authority -> AuthoritiesConstants.ADMIN.equals(authority.getAuthority()));
    }

    private boolean hasAccessToResource(UserDetails user, String method, String resourceName) {
        List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
        List<String> authorityNames = new ArrayList<>();

        authorities.forEach(grantedAuthority -> authorityNames.add(grantedAuthority.getAuthority()));

        List<ResourceAuthorityDTO> resources = resourceAuthorityQueryService.findByAuthorities(authorityNames, Pageable.unpaged()).getContent();

        return resources
            .stream()
            .anyMatch(resourceAuthority ->
                resourceAuthority.getResource() != null &&
                resourceAuthority.getResource().getName() != null &&
                resourceAuthority.getResource().getName().equalsIgnoreCase(resourceName) &&
                resourceAuthority.getVerb() != null &&
                resourceAuthority.getVerb().name().equalsIgnoreCase(method)
            );
    }
}
