package ix.portal.npg.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ix.portal.npg.domain.enumeration.Verb;
import ix.portal.npg.security.AuthoritiesConstants;
import ix.portal.npg.security.PortalUser;
import ix.portal.npg.service.ResourceAuthorityQueryService;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import ix.portal.npg.service.dto.ResourceDTO;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;

class CustomSecuredAuthorizationManagerTest {

    private ResourceAuthorityQueryService resourceAuthorityQueryService;
    private CustomSecuredAuthorizationManager authorizationManager;

    @BeforeEach
    void setUp() {
        resourceAuthorityQueryService = mock(ResourceAuthorityQueryService.class);
        authorizationManager = new CustomSecuredAuthorizationManager(resourceAuthorityQueryService);
    }

    @Test
    void shouldAllowAdminWithoutResourceLookup() {
        PortalUser admin = portalUser(AuthoritiesConstants.ADMIN);
        MethodInvocation invocation = methodInvocation(SecuredResourceController.class, "viewResource");

        var result = authorizationManager.authorize(() -> authentication(admin), invocation);

        assertThat(result).isInstanceOf(AuthorizationDecision.class);
        assertThat(((AuthorizationDecision) result).isGranted()).isTrue();
    }

    @Test
    void shouldAllowUserWithMatchingResourcePermission() {
        PortalUser user = portalUser(AuthoritiesConstants.USER);
        ResourceAuthorityDTO grant = resourceGrant("portability", Verb.VIEW);
        when(resourceAuthorityQueryService.findByAuthorities(any(), any(Pageable.class)))
            .thenReturn(new PageImpl<>(List.of(grant)));

        MethodInvocation invocation = methodInvocation(SecuredResourceController.class, "viewPortability");
        var result = authorizationManager.authorize(() -> authentication(user), invocation);

        assertThat(((AuthorizationDecision) result).isGranted()).isTrue();
    }

    @Test
    void shouldDenyUserWithoutMatchingVerb() {
        PortalUser user = portalUser(AuthoritiesConstants.USER);
        ResourceAuthorityDTO grant = resourceGrant("portability", Verb.VIEW);
        when(resourceAuthorityQueryService.findByAuthorities(any(), any(Pageable.class)))
            .thenReturn(new PageImpl<>(List.of(grant)));

        MethodInvocation invocation = methodInvocation(SecuredResourceController.class, "createPortability");
        var result = authorizationManager.authorize(() -> authentication(user), invocation);

        assertThat(((AuthorizationDecision) result).isGranted()).isFalse();
    }

    @Test
    void shouldDenyWhenSecuredAnnotationMissing() {
        PortalUser user = portalUser(AuthoritiesConstants.USER);
        MethodInvocation invocation = methodInvocation(SecuredResourceController.class, "unsecured");

        var result = authorizationManager.authorize(() -> authentication(user), invocation);

        assertThat(((AuthorizationDecision) result).isGranted()).isFalse();
    }

    private static UsernamePasswordAuthenticationToken authentication(PortalUser user) {
        return new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
    }

    private static PortalUser portalUser(String role) {
        return new PortalUser(
            "test-user",
            "password",
            true,
            true,
            true,
            true,
            List.of(new SimpleGrantedAuthority(role)),
            "party-id",
            Collections.emptyList(),
            null
        );
    }

    private static ResourceAuthorityDTO resourceGrant(String resourceName, Verb verb) {
        ResourceDTO resource = new ResourceDTO();
        resource.setName(resourceName);
        ResourceAuthorityDTO grant = new ResourceAuthorityDTO();
        grant.setResource(resource);
        grant.setVerb(verb);
        return grant;
    }

    private static MethodInvocation methodInvocation(Class<?> controllerClass, String methodName) {
        Method method = null;
        for (Method candidate : controllerClass.getDeclaredMethods()) {
            if (candidate.getName().equals(methodName)) {
                method = candidate;
                break;
            }
        }
        if (method == null) {
            throw new IllegalArgumentException("Unknown method: " + methodName);
        }

        MethodInvocation invocation = mock(MethodInvocation.class);
        when(invocation.getMethod()).thenReturn(method);
        return invocation;
    }

    @RestController
    @RequestMapping("/api/test-security")
    static class SecuredResourceController {

        @GetMapping("/portability")
        @Secured("portability")
        void viewPortability() {}

        @PostMapping("/portability")
        @Secured("portability")
        void createPortability() {}

        @GetMapping("/resource")
        @Secured("resource")
        void viewResource() {}

        @GetMapping("/open")
        void unsecured() {}
    }
}
