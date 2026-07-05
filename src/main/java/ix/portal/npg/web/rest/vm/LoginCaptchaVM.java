package ix.portal.npg.web.rest.vm;

public class LoginCaptchaVM extends LoginVM {

    private String captchaId;

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