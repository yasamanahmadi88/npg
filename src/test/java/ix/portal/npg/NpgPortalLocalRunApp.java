package ix.portal.npg;

public final class NpgPortalLocalRunApp {

    private NpgPortalLocalRunApp() {
    }

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "localrun");
        System.setProperty("oracle.jdbc.autoCommitSpecCompliant", "false");

        // Interactive local login must enforce CAPTCHA exactly like production.
        System.setProperty("captcha.enabled", "true");
        System.setProperty("captcha.dev-bypass", "false");
        System.setProperty("captcha.devBypass", "false");

        NpgPortalApp.main(args);
    }
}
