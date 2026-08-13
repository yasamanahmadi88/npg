package ix.portal.npg.security.captcha;

import ix.portal.npg.web.rest.errors.InvalidCaptchaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
            log.warn("CAPTCHA validation is disabled. This mode must only be used by isolated automated tests.");
            return;
        }

        if (props.isDevBypass()) {
            log.warn("CAPTCHA development bypass is enabled. This mode must never be used for interactive login.");
            return;
        }

        if (captchaId == null || captchaId.isBlank() || captchaToken == null || captchaToken.isBlank()) {
            throw new InvalidCaptchaException();
        }

        boolean valid = localCaptchaService.verifyAndConsume(captchaId.trim(), captchaToken.trim());

        if (!valid) {
            log.debug("Rejected login because CAPTCHA validation failed for remote address {}.", remoteIp);
            throw new InvalidCaptchaException();
        }
    }
}
