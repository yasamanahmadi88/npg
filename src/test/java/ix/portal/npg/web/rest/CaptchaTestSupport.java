package ix.portal.npg.web.rest;

import ix.portal.npg.security.captcha.LocalCaptchaService;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Test-only helper to read the current CAPTCHA answer without consuming it.
 */
final class CaptchaTestSupport {

    private CaptchaTestSupport() {}

    @SuppressWarnings("unchecked")
    static String peekAnswer(LocalCaptchaService service, String captchaId) throws Exception {
        Field storeField = LocalCaptchaService.class.getDeclaredField("store");
        storeField.setAccessible(true);
        Map<String, ?> store = (Map<String, ?>) storeField.get(service);
        Object entry = store.get(captchaId);
        if (entry == null) {
            throw new IllegalStateException("No captcha entry for id=" + captchaId);
        }
        Field textField = entry.getClass().getDeclaredField("text");
        textField.setAccessible(true);
        return (String) textField.get(entry);
    }
}
