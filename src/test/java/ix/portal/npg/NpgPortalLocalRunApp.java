package ix.portal.npg;

/**
 * Convenience entry point for running the portal with the {@code localrun} profile.
 * CAPTCHA stays enforced (same as default/prod); do not disable it here.
 */
public final class NpgPortalLocalRunApp {

    private NpgPortalLocalRunApp() {}

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "localrun");
        System.setProperty("oracle.jdbc.autoCommitSpecCompliant", "false");
        NpgPortalApp.main(args);
    }
}
