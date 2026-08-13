package ix.portal.npg.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.web.rest.vm.LoginCaptchaVM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@IntegrationTest
@TestPropertySource(properties = { "captcha.enabled=true", "captcha.dev-bypass=false" })
class UserJWTControllerCaptchaIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void invalidCaptchaBlocksAuthenticationBeforeCredentialsAreChecked() throws Exception {
        LoginCaptchaVM login = new LoginCaptchaVM();
        login.setUsername("admin");
        login.setPassword("admin");
        login.setCaptchaId("missing-or-consumed-captcha-id");
        login.setCaptchaToken("WRONG1");

        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.type").value("https://npg/problem/invalid-captcha-validation"))
            .andExpect(header().doesNotExist("Authorization"));
    }
}
