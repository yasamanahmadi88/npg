package ix.portal.npg.web.rest.vm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
public class LogoutVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    private String idToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "LogoutVM{" + "username='" + username + '\'' + ", idToken='" + idToken + '\'' + '}';
    }
}


