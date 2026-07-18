package ix.portal.npg.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ix.portal.npg.security.AuthoritiesConstants;
import ix.portal.npg.security.PortalUser;
import ix.portal.npg.security.SecurityCache;
import ix.portal.npg.service.ResourceAuthorityQueryService;
import ix.portal.npg.service.SettingQueryService;
import jakarta.servlet.FilterChain;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import tech.jhipster.config.JHipsterProperties;

class JWTFilterTest {

    private TokenProvider tokenProvider;
    private JWTFilter jwtFilter;
    private SecurityCache securityCache;
    private ResourceAuthorityQueryService resourceAuthorityQueryService;
    private SettingQueryService settingQueryService;

    @BeforeEach
    public void setup() {
        resourceAuthorityQueryService = mock(ResourceAuthorityQueryService.class);
        settingQueryService = mock(SettingQueryService.class);

        when(resourceAuthorityQueryService.findByAuthorities(any(), any(Pageable.class))).thenReturn(Page.empty());
        when(settingQueryService.findByKey(any())).thenReturn(Optional.empty());

        JHipsterProperties jHipsterProperties = new JHipsterProperties();
        String base64Secret = "fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8";
        jHipsterProperties.getSecurity().getAuthentication().getJwt().setBase64Secret(base64Secret);

        tokenProvider = new TokenProvider(jHipsterProperties, resourceAuthorityQueryService);
        ReflectionTestUtils.setField(tokenProvider, "key", Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)));
        ReflectionTestUtils.setField(tokenProvider, "tokenValidityInMilliseconds", 60000);

        securityCache = new SecurityCache(settingQueryService);
        ReflectionTestUtils.setField(securityCache, "bucketSizeForGet", 100L);
        ReflectionTestUtils.setField(securityCache, "tokenPerMinuteForGet", 100L);
        ReflectionTestUtils.setField(securityCache, "bucketSizeForPost", 100L);
        ReflectionTestUtils.setField(securityCache, "tokenPerMinuteForPost", 100L);

        jwtFilter = new JWTFilter(tokenProvider, securityCache);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testJWTFilter() throws Exception {
        UsernamePasswordAuthenticationToken authentication = createAuthentication();
        String jwt = tokenProvider.createToken(authentication, false);

        securityCache.storeSession(
            authentication.getPrincipal(),
            "test-session-id",
            "127.0.0.1",
            authentication.getName(),
            jwt,
            "test-user-agent",
            LocalDateTime.now(),
            null,
            true
        );

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        request.setRequestURI("/api/test");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = (servletRequest, servletResponse) -> {};

        jwtFilter.doFilter(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getRedirectedUrl()).isNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("test-user");
        assertThat(SecurityContextHolder.getContext().getAuthentication().getCredentials()).hasToString(jwt);
    }

    @Test
    void testJWTFilterInvalidToken() throws Exception {
        String jwt = "wrong_jwt";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        request.setRequestURI("/api/test");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = (servletRequest, servletResponse) -> {};

        jwtFilter.doFilter(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getRedirectedUrl()).isNull();
        assertThat(response.getContentAsString()).isEqualTo("error.npg.token.empty");
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void testJWTFilterMissingAuthorization() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = (servletRequest, servletResponse) -> {};

        jwtFilter.doFilter(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getRedirectedUrl()).isNull();
        assertThat(response.getContentAsString()).isEqualTo("error.npg.token.empty");
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void testJWTFilterMissingToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer ");
        request.setRequestURI("/api/test");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = (servletRequest, servletResponse) -> {};

        jwtFilter.doFilter(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getRedirectedUrl()).isNull();
        assertThat(response.getContentAsString()).isEqualTo("error.npg.token.empty");
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void testJWTFilterWrongScheme() throws Exception {
        UsernamePasswordAuthenticationToken authentication = createAuthentication();
        String jwt = tokenProvider.createToken(authentication, false);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Basic " + jwt);
        request.setRequestURI("/api/test");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = (servletRequest, servletResponse) -> {};

        jwtFilter.doFilter(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getRedirectedUrl()).isNull();
        assertThat(response.getContentAsString()).isEqualTo("error.npg.token.empty");
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void testJWTFilterRehydratesMissingSessionForValidToken() throws Exception {
        UsernamePasswordAuthenticationToken authentication = createAuthentication();
        String jwt = tokenProvider.createToken(authentication, false);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        request.setRequestURI("/api/settings");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = (servletRequest, servletResponse) -> {};

        jwtFilter.doFilter(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(securityCache.getSessionInfoByToken(jwt)).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("test-user");
    }

    @Test
    void testJWTFilterSkipsRateLimitForAccountEndpoint() throws Exception {
        UsernamePasswordAuthenticationToken authentication = createAuthentication();
        String jwt = tokenProvider.createToken(authentication, false);

        securityCache.storeSession(
            authentication.getPrincipal(),
            "test-session-id",
            "127.0.0.1",
            authentication.getName(),
            jwt,
            "test-user-agent",
            LocalDateTime.now(),
            null,
            true
        );

        // Replace GET bucket with an already-empty bucket to force 429 on normal APIs.
        io.github.bucket4j.Bandwidth empty = io.github.bucket4j.Bandwidth.classic(
            1,
            io.github.bucket4j.Refill.greedy(1, java.time.Duration.ofHours(1))
        );
        io.github.bucket4j.Bucket emptyBucket = io.github.bucket4j.Bucket.builder().addLimit(empty).build();
        emptyBucket.tryConsume(1);
        securityCache.getSessionInfoByToken(jwt).setBucketGet(emptyBucket);

        MockHttpServletRequest settings = new MockHttpServletRequest();
        settings.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        settings.setRequestURI("/api/settings");
        settings.setMethod("GET");
        MockHttpServletResponse settingsResponse = new MockHttpServletResponse();
        jwtFilter.doFilter(settings, settingsResponse, (req, res) -> {});
        assertThat(settingsResponse.getStatus()).isEqualTo(429);

        MockHttpServletRequest account = new MockHttpServletRequest();
        account.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        account.setRequestURI("/api/account");
        account.setMethod("GET");
        MockHttpServletResponse accountResponse = new MockHttpServletResponse();
        jwtFilter.doFilter(account, accountResponse, (req, res) -> {});

        assertThat(accountResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    private UsernamePasswordAuthenticationToken createAuthentication() {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER));

        PortalUser principal = new PortalUser(
            "test-user",
            "test-password",
            true,
            true,
            true,
            true,
            authorities,
            "test-party-id",
            Collections.emptyList(),
            null
        );

        return new UsernamePasswordAuthenticationToken(principal, "test-password", authorities);
    }
}