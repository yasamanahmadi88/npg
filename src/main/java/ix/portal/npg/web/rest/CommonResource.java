package ix.portal.npg.web.rest;

import static ix.portal.npg.config.Constants.EVENT_UNSAFE_CONNECTION;

import ix.portal.npg.repository.CustomAuditEventRepository;
import ix.portal.npg.security.SecurityUtils;
import ix.portal.npg.security.captcha.CaptchaValidationService;
import ix.portal.npg.security.captcha.exception.InvalidCaptchaException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommonResource {

    private final Logger log = LoggerFactory.getLogger(CommonResource.class);

    private final CustomAuditEventRepository customAuditEventRepository;
    private final CaptchaValidationService captchaValidationService;

    public CommonResource(
        CustomAuditEventRepository customAuditEventRepository,
        CaptchaValidationService captchaValidationService
    ) {
        this.customAuditEventRepository = customAuditEventRepository;
        this.captchaValidationService = captchaValidationService;
    }

    @GetMapping("/get-time")
    public ResponseEntity<LocalDateTime> getLocalDateTime() {
        return ResponseEntity.ok().body(LocalDateTime.now());
    }

    @PostMapping(value = "/cp-eyrtyertye", produces = "application/json; charset=utf-8")
    public String yourFormPostAction(@RequestBody LinkedHashMap<String, Object> requestBody, HttpServletRequest request) {
        String userEnteredCaptchaCode = (String) requestBody.get("userEnteredCaptchaCode");
        String captchaId = (String) requestBody.get("captchaId");

        log.debug("Local CAPTCHA validation request received.");

        try {
            captchaValidationService.validate(captchaId, userEnteredCaptchaCode, request.getRemoteAddr());
            return "{\"success\":true}";
        } catch (InvalidCaptchaException ex) {
            return "{\"success\":false}";
        }
    }

    @PostMapping(value = "/cn-notification")
    public ResponseEntity<Void> connectionNotification(@RequestBody LinkedHashMap<String, Object> requestBody, HttpServletRequest request) {
        String clientPrincipal = (String) requestBody.get("clientPrincipal");

        Map<String, Object> data = new HashMap<>();
        data.put("jwt", SecurityUtils.getCurrentUserJWT().orElse(null));
        data.put("sessionId", request.getSession().getId());
        data.put("clientPrincipal", clientPrincipal);

        SecurityUtils.getCurrentUser()
            .ifPresent(user -> customAuditEventRepository.add(new AuditEvent(user.getUsername(), EVENT_UNSAFE_CONNECTION, data)));

        return ResponseEntity.accepted().build();
    }
}
