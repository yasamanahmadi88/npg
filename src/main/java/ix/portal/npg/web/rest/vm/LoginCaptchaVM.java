package ix.portal.npg.web.rest.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Login credentials plus CAPTCHA challenge fields.
 */
public class LoginCaptchaVM extends LoginVM {

    @NotBlank
    @Size(max = 64)
    private String captchaId;

    @NotBlank
    @Size(min = 1, max = 16)
    private String captchaToken;

    public String getCaptchaId() {
        return captchaId;
    }

    public LoginCaptchaVM setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
        return this;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }

    public LoginCaptchaVM setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
        return this;
    }

    @Override
    public String toString() {
        return (
            "LoginCaptchaVM{" +
            "username='" +
            getUsername() +
            '\'' +
            ", rememberMe=" +
            isRememberMe() +
            ", captchaId='" +
            captchaId +
            '\'' +
            '}'
        );
    }
}
