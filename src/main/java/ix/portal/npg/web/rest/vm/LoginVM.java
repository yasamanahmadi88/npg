package ix.portal.npg.web.rest.vm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
public class LoginVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    /*    @NotNull
    private String userEnteredCaptchaCode;

    @NotNull
    private String captchaId;*/

    private boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /*   public String getUserEnteredCaptchaCode() {
        return userEnteredCaptchaCode;
    }

    public void setUserEnteredCaptchaCode(String userEnteredCaptchaCode) {
        this.userEnteredCaptchaCode = userEnteredCaptchaCode;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }*/

    @Override
    public String toString() {
        return (
            "LoginVM{" +
            "username='" +
            username +
            '\'' +
            ", password='" +
            password +
            '\'' +
            /*   ", userEnteredCaptchaCode='" +
            userEnteredCaptchaCode +
            '\'' +
            ", captchaId='" +
            captchaId +
            '\'' +*/
            ", rememberMe=" +
            rememberMe +
            '}'
        );
    }
}


