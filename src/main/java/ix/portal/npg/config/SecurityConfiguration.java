package ix.portal.npg.config;

import ix.portal.npg.security.AuthoritiesConstants;
import ix.portal.npg.security.SecurityCache;
import ix.portal.npg.security.jwt.JWTFilter;
import ix.portal.npg.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = false, jsr250Enabled = false)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final JHipsterProperties jHipsterProperties;
    private final TokenProvider tokenProvider;
    private final SecurityCache securityCache;
    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        CorsFilter corsFilter,
        JHipsterProperties jHipsterProperties,
        SecurityCache securityCache,
        SecurityProblemSupport problemSupport
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.securityCache = securityCache;
        this.problemSupport = problemSupport;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
            )
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives(jHipsterProperties.getSecurity().getContentSecurityPolicy()))
                .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                .frameOptions(frameOptions -> frameOptions.deny())
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                    "/",
                    "/*.html",
                    "/*.js",
                    "/*.css",
                    "/i18n/**",
                    "/content/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/test/**"
                ).permitAll()
                .requestMatchers(request -> {
                    String path = request.getRequestURI().substring(request.getContextPath().length());
                    return path.startsWith("/app/") && (path.endsWith(".js") || path.endsWith(".html"));
                }).permitAll()
                .requestMatchers("/api/authenticate").permitAll()
                .requestMatchers("/api/cp-eyrtyertye").permitAll()
                .requestMatchers("/api/activate").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/captcha-endpoint").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/captcha.png").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/management/health").permitAll()
                .requestMatchers("/management/health/**").permitAll()
                .requestMatchers("/management/info").hasAuthority(AuthoritiesConstants.ADMIN)
                .requestMatchers("/management/prometheus").denyAll()
                .requestMatchers("/management/threaddump").denyAll()
                .requestMatchers("/management/jhimetrics").denyAll()
                .requestMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .anyRequest().permitAll()
            )
            .addFilterBefore(new JWTFilter(tokenProvider, securityCache), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
