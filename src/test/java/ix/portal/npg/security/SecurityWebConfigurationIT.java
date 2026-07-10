package ix.portal.npg.security;

import ix.portal.npg.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@AutoConfigureMockMvc
@TestPropertySource(
    properties = {
        "jhipster.cors.allowed-origins=http://localhost:4200",
        "jhipster.cors.allowed-methods=GET,POST,PUT,PATCH,DELETE,OPTIONS,HEAD",
        "jhipster.cors.allowed-headers=*",
        "jhipster.cors.allow-credentials=true",
        "jhipster.cors.max-age=1800"
    }
)
class SecurityWebConfigurationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void protectedApiRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/account")).andExpect(status().isUnauthorized());
    }

    @Test
    void captchaEndpointIsPublic() throws Exception {
        // Public auth-adjacent endpoint must not require a JWT.
        mockMvc.perform(get("/api/captcha.png")).andExpect(status().is4xxClientError());
    }

    @Test
    void corsPreflightAllowsConfiguredOrigin() throws Exception {
        mockMvc
            .perform(
                options("/api/account")
                    .header(HttpHeaders.ORIGIN, "http://localhost:4200")
                    .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
            )
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:4200"));
    }

    @Test
    void corsPreflightRejectsUnknownOrigin() throws Exception {
        mockMvc
            .perform(
                options("/api/account")
                    .header(HttpHeaders.ORIGIN, "https://evil.example")
                    .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
            )
            .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }
}
