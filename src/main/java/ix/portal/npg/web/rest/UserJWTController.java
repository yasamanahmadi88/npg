package ix.portal.npg.web.rest;

import static ix.portal.npg.config.Constants.EVENT_LOGOUT;

import com.fasterxml.jackson.annotation.JsonProperty;
import ix.portal.npg.domain.PersistentAuditEvent;
import ix.portal.npg.repository.CustomAuditEventRepository;
import ix.portal.npg.security.AuthoritiesConstants;
import ix.portal.npg.security.SecurityCache;
import ix.portal.npg.security.captcha.CaptchaValidationService;
import ix.portal.npg.security.jwt.JWTFilter;
import ix.portal.npg.security.jwt.SessionInfo;
import ix.portal.npg.security.jwt.TokenProvider;
import ix.portal.npg.service.AuditEventService;
import ix.portal.npg.web.rest.vm.LoginCaptchaVM;
import ix.portal.npg.web.rest.vm.LogoutVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final SecurityCache securityCache;
    private final CustomAuditEventRepository customAuditEventRepository;
    private final AuditEventService auditEventService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CaptchaValidationService captchaValidationService;

    public UserJWTController(
        TokenProvider tokenProvider,
        SecurityCache securityCache,
        CustomAuditEventRepository customAuditEventRepository,
        AuditEventService auditEventService,
        AuthenticationManagerBuilder authenticationManagerBuilder,
        CaptchaValidationService captchaValidationService
    ) {
        this.tokenProvider = tokenProvider;
        this.securityCache = securityCache;
        this.customAuditEventRepository = customAuditEventRepository;
        this.auditEventService = auditEventService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.captchaValidationService = captchaValidationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(
        HttpServletRequest request,
        HttpServletResponse response,
        @Valid @RequestBody LoginCaptchaVM loginVM
    ) throws Exception {
        captchaValidationService.validate(
            loginVM.getCaptchaId(),
            loginVM.getCaptchaToken(),
            request.getRemoteAddr()
        );

        String userAgent = request.getHeader("user-agent");

        if (!loginVM.getUsername().equalsIgnoreCase("admin") && securityCache.hasConcurrentSession(loginVM.getUsername())) {
            SessionInfo sessionInfo = securityCache.fetchSessionInfo(loginVM.getUsername());
            if (!userAgent.equalsIgnoreCase(sessionInfo.getUserAgent())) {
                throw new ServletException("error.user.has.concurrent.session");
            } else {
                securityCache.removeSession(sessionInfo.getJwtToken());
            }
        }

        List<PersistentAuditEvent> persistentAuditEvents = auditEventService.findTopFiveState(loginVM.getUsername());
        if (persistentAuditEvents.size() > 0) {
            long failureCount = persistentAuditEvents
                .stream()
                .filter(persistentAuditEvent -> "AUTHENTICATION_FAILURE".equalsIgnoreCase(persistentAuditEvent.getAuditEventType()))
                .count();

            AtomicReference<PersistentAuditEvent> pae = new AtomicReference<>();
            persistentAuditEvents
                .stream()
                .filter(persistentAuditEvent -> "AUTHENTICATION_FAILURE".equalsIgnoreCase(persistentAuditEvent.getAuditEventType()))
                .findFirst()
                .ifPresentOrElse(pae::set, () -> pae.set(persistentAuditEvents.get(0)));

            if (failureCount > 40 && LocalDateTime.now().minus(10, ChronoUnit.MINUTES).isBefore(pae.get().getAuditEventDate())) {
                return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());

        securityCache.storeSession(
            authentication.getPrincipal(),
            request.getSession().getId(),
            request.getRemoteAddr(),
            loginVM.getUsername(),
            jwt,
            userAgent,
            LocalDateTime.now(),
            null,
            Boolean.TRUE
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        addSameSiteCookieAttribute(request, response);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutVM logoutVM) {
        try {
            securityCache.removeSession(logoutVM.getIdToken());
            Map<String, Object> data = new HashMap<>();
            data.put("jwt", logoutVM.getIdToken());
            data.put("username", logoutVM.getUsername());
            AuditEvent event = new AuditEvent(logoutVM.getUsername(), EVENT_LOGOUT, data);
            customAuditEventRepository.add(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/sessions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<SessionInfo>> getAllSessions() {
        log.debug("REST request to get all Session");
        final List<SessionInfo> page = securityCache.getAllSessionInfo();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/admin/sessions/remove/{token}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> removeSession(@PathVariable String token) {
        log.debug("REST request to remove Session:{}", token);
        securityCache.removeSession(token);
        return ResponseEntity.ok().build();
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

    private void addSameSiteCookieAttribute(HttpServletRequest request, HttpServletResponse response) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;

        for (String header : headers) {
            if (firstHeader) {
                response.setHeader(
                    HttpHeaders.SET_COOKIE,
                    String.format("%s; %s", header, "Path=/;SameSite=Strict;HttpOnly" + (request.isSecure() ? ";Secure" : ""))
                );
                firstHeader = false;
                continue;
            }

            response.addHeader(
                HttpHeaders.SET_COOKIE,
                String.format("%s; %s", header, "Path=/; SameSite=Strict;HttpOnly" + (request.isSecure() ? ";Secure" : ""))
            );
        }
    }
}
