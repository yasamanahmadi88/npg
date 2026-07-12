package ix.portal.npg.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ix.portal.npg.security.SecurityCache;
import java.io.IOException;
import java.time.LocalDateTime;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final SecurityCache securityCache;

    public JWTFilter(TokenProvider tokenProvider, SecurityCache securityCache) {
        this.tokenProvider = tokenProvider;
        this.securityCache = securityCache;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        Authentication existingAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (
            existingAuthentication != null &&
            existingAuthentication.isAuthenticated() &&
            !(existingAuthentication instanceof AnonymousAuthenticationToken)
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        boolean flag = true;

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestUri = httpServletRequest.getRequestURI();
        String jwt = resolveToken(httpServletRequest);
        SessionInfo sessionInfo = securityCache.getSessionInfoByToken(jwt);

        // Rehydrate in-memory session when JWT is still valid (e.g. after app restart).
        // Without this, list APIs return 401 and entity forms render empty despite DB data.
        if (StringUtils.hasText(jwt) && sessionInfo == null && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            securityCache.storeSession(
                authentication.getPrincipal(),
                httpServletRequest.getSession(true).getId(),
                httpServletRequest.getRemoteAddr(),
                authentication.getName(),
                jwt,
                httpServletRequest.getHeader("user-agent"),
                LocalDateTime.now(),
                null,
                Boolean.TRUE
            );
            sessionInfo = securityCache.getSessionInfoByToken(jwt);
        }

        if (!isPublicRequest(requestUri)) {
            if (jwt == null || sessionInfo == null) {
                writeUnauthorized((HttpServletResponse) servletResponse, "error.npg.token.empty");
                flag = false;
            } else if (!sessionInfo.getValidToken()) {
                writeUnauthorized((HttpServletResponse) servletResponse, "error.portal.token.invalid");
                flag = false;
            }
        }

        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (StringUtils.hasText(jwt) && !this.tokenProvider.validateToken(jwt)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            securityCache.removeSession(jwt);
        }

        if (sessionInfo != null) {
            if (httpServletRequest.getMethod().equals("POST")) {
                if (!sessionInfo.getBucketPost().tryConsume(1)) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                    httpServletResponse.setContentType("text/plain");
                    httpServletResponse.setStatus(429);
                    httpServletResponse.getWriter().append("error.too.many.requests");
                    flag = false;
                }
            } else {
                if (!sessionInfo.getBucketGet().tryConsume(1)) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                    httpServletResponse.setContentType("text/plain");
                    httpServletResponse.setStatus(429);
                    httpServletResponse.getWriter().append("error.too.many.requests");
                    flag = false;
                }
            }
        }

        if (flag) filterChain.doFilter(servletRequest, servletResponse);
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

    private boolean isPublicRequest(String requestUri) {
        return (
            requestUri.equals("/") ||
            requestUri.endsWith("/login") ||
            requestUri.endsWith("error") ||
            requestUri.endsWith("api/cp-eyrtyertye") ||
            requestUri.endsWith("api/authenticate") ||
            requestUri.endsWith("api/logout") ||
            requestUri.endsWith("api/public/backUrl") ||
            requestUri.endsWith("/captcha-endpoint") ||
            requestUri.endsWith("api/captcha.png") ||
            requestUri.endsWith("api/captcha-validate") ||
            requestUri.endsWith("management/info") ||
            requestUri.endsWith("management/health") ||
            requestUri.startsWith("/management/health/") ||
            requestUri.startsWith("/i18n/") ||
            requestUri.startsWith("/content/") ||
            requestUri.startsWith("/assets/") ||
            requestUri.startsWith("/app/") ||
            requestUri.endsWith(".js") ||
            requestUri.endsWith(".html") ||
            requestUri.endsWith(".css") ||
            requestUri.endsWith(".json") ||
            requestUri.endsWith(".map") ||
            requestUri.endsWith(".woff") ||
            requestUri.endsWith(".woff2") ||
            requestUri.endsWith(".ttf") ||
            requestUri.endsWith(".eot") ||
            requestUri.endsWith(".png") ||
            requestUri.endsWith(".jpg") ||
            requestUri.endsWith(".jpeg") ||
            requestUri.endsWith(".gif") ||
            requestUri.endsWith(".svg") ||
            requestUri.endsWith(".ico") ||
            requestUri.matches("^/mci-logo.*png$")
        );
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String convertObjectToJson(ErrorResponse object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}