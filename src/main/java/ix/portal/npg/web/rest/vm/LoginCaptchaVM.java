package ix.portal.npg.web.rest.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginCaptchaVM extends LoginVM {

    @NotBlank
    @Size(max = 128)
    private String captchaId;

    @NotBlank
    @Size(max = 16)
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
}
