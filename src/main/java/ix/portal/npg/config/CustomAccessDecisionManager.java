package ix.portal.npg.config;

import ix.portal.npg.security.AuthoritiesConstants;

import ix.portal.npg.service.ResourceAuthorityQueryService;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Custom Access decision manager for method security
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

    private final Logger log = LoggerFactory.getLogger(CustomAccessDecisionManager.class);
    private final ResourceAuthorityQueryService resourceAuthorityQueryService;

    public CustomAccessDecisionManager(ResourceAuthorityQueryService resourceAuthorityQueryService) {
        this.resourceAuthorityQueryService = resourceAuthorityQueryService;
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
        throws AccessDeniedException, InsufficientAuthenticationException {
        ConfigAttribute configAttribute = configAttributes.iterator().next();

        MethodInvocation methodInvocation = (MethodInvocation) object;

        try {
            if (authentication != null) {
                UserDetails user = ix.portal.npg.security.SecurityUtils.getCurrentUser(authentication);
                String method = "";
                for (Annotation annotation : methodInvocation.getMethod().getDeclaredAnnotations()) {
                    if (annotation instanceof GetMapping) {
                        method = "VIEW";
                        break;
                    } else if (annotation instanceof PostMapping) {
                        method = "CREATE";
                        break;
                    } else if (annotation instanceof PutMapping || annotation instanceof PatchMapping) {
                        method = "EDIT";
                        break;
                    } else if (annotation instanceof DeleteMapping) {
                        method = "DELETE";
                        break;
                    } else {
                        method = "NO_GRANT";
                    }
                }

                //String resourceName = SecurityUtils.getResourceName(configAttribute);
                String resourceName = configAttribute.getAttribute();

                if (user != null && !isAdmin(user) && !hasAccessToResource(user, method, resourceName)) {
                    throw new AccessDeniedException(
                        "User does not have enough privileges to access this method:" +
                        method +
                        " resource:" +
                        resourceName +
                        " ,Authoriry:" +
                        (
                            user.getAuthorities().stream().findAny().isPresent()
                                ? user.getAuthorities().stream().findAny().get().getAuthority()
                                : ""
                        )
                    );
                }
            }
        } catch (ClassCastException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MethodInvocation.class);
    }

    /**
     * checks if the resource has grants in any roles for the user
     * @param user
     * @param method
     * @param resourceName
     * @return
     */

    private boolean isAdmin(UserDetails user) {
        return user != null &&
            user.getAuthorities() != null &&
            user.getAuthorities()
                .stream()
                .anyMatch(authority -> AuthoritiesConstants.ADMIN.equals(authority.getAuthority()));
    }
    private boolean hasAccessToResource(UserDetails user, String method, String resourceName) {
        /*
         *  The resources are converted into
         *  a map while retrieving user for first time and later use the map to fetch the section and check access.
         */

        List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
        List<String> ids = new ArrayList<>();
        authorities.forEach(
            grantedAuthority -> {
                ids.add(grantedAuthority.getAuthority());
            }
        );
        List<ResourceAuthorityDTO> resources = resourceAuthorityQueryService.findByAuthorities(ids, Pageable.unpaged()).getContent();
        return resources
            .stream()
            .anyMatch(x -> x.getResource().getName().equalsIgnoreCase(resourceName) && x.getVerb().name().equalsIgnoreCase(method));
        /*List<ResourceAuthorityDTO> l1= resources.stream().filter( x -> x.getResourceName().equalsIgnoreCase(resourceName) && x.getVerb().name().equalsIgnoreCase(method)).collect(Collectors.toList());
        if (l1.size()<=0) {
            return false;
        }*/

        /*Map<String, ResourceDTO> validResources = user.getValidResources();
        if(validResources !=null && !validResources.isEmpty()) {
            ResourceDTO validResource = validResources.get(resourceName);
            if (validResource != null && hasGrants(validResource, method)) {
                log.debug("Role has access to resource:{} ", validResource);
                return true;
            }
        }*/
    }
}


