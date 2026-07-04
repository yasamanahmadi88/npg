package ix.portal.npg.security.captcha;

import ix.portal.npg.security.captcha.exception.InvalidCaptchaException;
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
            log.trace("Local CAPTCHA validation bypassed because captcha.enabled=false.");
            return;
        }

        if (props.isDevBypass()) {
            log.trace("Local CAPTCHA validation bypassed because captcha.dev-bypass=true.");
            return;
        }

        if (captchaId == null || captchaId.isBlank()) {
            throw new InvalidCaptchaException("Invalid authentication request");
        }

        if (captchaToken == null || captchaToken.isBlank()) {
            throw new InvalidCaptchaException("Invalid authentication request");
        }

        boolean valid = localCaptchaService.verifyAndConsume(
            captchaId.trim(),
            captchaToken.trim()
        );

        if (!valid) {
            throw new InvalidCaptchaException("Invalid authentication request");
        }
    }
}
