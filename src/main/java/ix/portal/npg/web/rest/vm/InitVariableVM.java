package ix.portal.npg.web.rest.vm;

public class InitVariableVM {

    private String backUrl;

    public InitVariableVM(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public InitVariableVM setBackUrl(String backUrl) {
        this.backUrl = backUrl;
        return this;
    }

    public static InitVariableVM of(String backUrl) {
        return new InitVariableVM(backUrl);
    }
}


