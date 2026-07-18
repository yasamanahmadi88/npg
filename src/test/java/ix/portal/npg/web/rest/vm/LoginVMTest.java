package ix.portal.npg.web.rest.vm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoginVMTest {

    @Test
    void toString_doesNotIncludePassword() {
        LoginVM login = new LoginVM();
        login.setUsername("alice");
        login.setPassword("super-secret");
        login.setRememberMe(true);

        String text = login.toString();

        assertThat(text).contains("alice");
        assertThat(text).doesNotContain("super-secret");
        assertThat(text).doesNotContain("password");
    }
}
