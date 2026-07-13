package ix.portal.npg.security.captcha;

import ix.portal.npg.web.rest.errors.InvalidCaptchaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Validates local CAPTCHA tokens for authentication and related endpoints.
 * Failures are raised as {@link InvalidCaptchaException} so the REST layer
 * returns HTTP 400 with {@code errorKey=captcha.validation}.
 */
@Service
public class CaptchaValidationService {

    private static final Logger log = LoggerFactory.getLogger(CaptchaValidationService.class);

    private final CaptchaProperties props;
    private final LocalCaptchaService localCaptchaService;

    public CaptchaValidationService(CaptchaProperties props, LocalCaptchaService localCaptchaService) {
        this.props = props;
        this.localCaptchaService = localCaptchaService;
    }

    public void validate(String captchaId, String captchaToken, String remoteIp) {
        if (!props.isEnabled()) {
            log.trace("Local CAPTCHA validation bypassed because captcha.enabled=false.");
            return;
        }

        if (props.isDevBypass()) {
            log.warn(
                "Local CAPTCHA validation bypassed because captcha.dev-bypass=true (remoteIp={}). " +
                    "This must remain false outside controlled test profiles.",
                remoteIp
            );
            return;
        }

        if (captchaId == null || captchaId.isBlank() || captchaToken == null || captchaToken.isBlank()) {
            throw new InvalidCaptchaException();
        }

        boolean valid = localCaptchaService.verifyAndConsume(captchaId.trim(), captchaToken.trim());
        if (!valid) {
            throw new InvalidCaptchaException();
        }
    }
}
