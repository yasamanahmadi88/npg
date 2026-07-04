package ix.portal.npg.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;

/**
 * Registers the project-specific @Secured authorization interceptor.
 *
 * Spring's default @Secured handling is disabled in SecurityConfiguration because
 * this project uses @Secured("resourceName") as a resource-permission key, not as
 * a plain GrantedAuthority value.
 */
@Configuration
public class MethodSecurityConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AuthorizationManagerBeforeMethodInterceptor securedMethodInterceptor(
        CustomSecuredAuthorizationManager customSecuredAuthorizationManager
    ) {
        return AuthorizationManagerBeforeMethodInterceptor.secured(customSecuredAuthorizationManager);
    }
}
