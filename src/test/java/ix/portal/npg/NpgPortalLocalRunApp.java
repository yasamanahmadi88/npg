package ix.portal.npg;

public final class NpgPortalLocalRunApp {

    private NpgPortalLocalRunApp() {
    }

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "localrun");
        System.setProperty("oracle.jdbc.autoCommitSpecCompliant", "false");

        // Local development only: bypass captcha to isolate authentication/page issues.
        System.setProperty("captcha.enabled", "false");
        System.setProperty("captcha.dev-bypass", "true");
        System.setProperty("captcha.devBypass", "true");

        NpgPortalApp.main(args);
    }
}