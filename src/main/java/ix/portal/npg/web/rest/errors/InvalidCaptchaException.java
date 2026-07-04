package ix.portal.npg.web.rest.errors;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class InvalidCaptchaException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public InvalidCaptchaException() {
        super(ErrorConstants.INVALID_CAPTCHA_VALIDATION, "Captcha Invalid", "captchaValidation", "captcha.validation");
    }
}


