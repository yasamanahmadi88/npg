package ix.portal.npg.security.captcha;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    /**
     * Enables or disables local offline CAPTCHA validation globally.
     * In production this should remain true.
     */
    private boolean enabled = true;

    /**
     * Allows local CAPTCHA bypass only for controlled development scenarios.
     * In production this must remain false.
     */
    private boolean devBypass = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDevBypass() {
        return devBypass;
    }

    public void setDevBypass(boolean devBypass) {
        this.devBypass = devBypass;
    }
}
