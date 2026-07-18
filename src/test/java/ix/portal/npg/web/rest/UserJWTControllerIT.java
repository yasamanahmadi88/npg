package ix.portal.npg.web.rest;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.User;
import ix.portal.npg.repository.UserRepository;
import ix.portal.npg.security.captcha.LocalCaptchaService;
import ix.portal.npg.web.rest.vm.LoginCaptchaVM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserJWTController} REST controller.
 */
@AutoConfigureMockMvc
@IntegrationTest
@TestPropertySource(properties = "captcha.enabled=false")
class UserJWTControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void testAuthorize() throws Exception {
        User user = new User();
        user.setLogin("user-jwt-controller");
        user.setEmail("user-jwt-controller@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));

        userRepository.saveAndFlush(user);

        LoginCaptchaVM login = new LoginCaptchaVM();
        login.setUsername("user-jwt-controller");
        login.setPassword("test");
        login.setCaptchaId("unused-when-disabled");
        login.setCaptchaToken("unused");

        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isString())
            .andExpect(jsonPath("$.id_token").isNotEmpty())
            .andExpect(header().string("Authorization", not(nullValue())))
            .andExpect(header().string("Authorization", not(is(emptyString()))));
    }

    @Test
    @Transactional
    void testAuthorizeWithRememberMe() throws Exception {
        User user = new User();
        user.setLogin("user-jwt-controller-remember-me");
        user.setEmail("user-jwt-controller-remember-me@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));

        userRepository.saveAndFlush(user);

        LoginCaptchaVM login = new LoginCaptchaVM();
        login.setUsername("user-jwt-controller-remember-me");
        login.setPassword("test");
        login.setRememberMe(true);
        login.setCaptchaId("unused-when-disabled");
        login.setCaptchaToken("unused");

        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isString())
            .andExpect(jsonPath("$.id_token").isNotEmpty())
            .andExpect(header().string("Authorization", not(nullValue())))
            .andExpect(header().string("Authorization", not(is(emptyString()))));
    }

    @Test
    void testAuthorizeFails() throws Exception {
        LoginCaptchaVM login = new LoginCaptchaVM();
        login.setUsername("wrong-user");
        login.setPassword("wrong password");
        login.setCaptchaId("unused-when-disabled");
        login.setCaptchaToken("unused");

        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.id_token").doesNotExist())
            .andExpect(header().doesNotExist("Authorization"));
    }

    @Test
    void testAuthorizeRejectsMissingCaptchaFields() throws Exception {
        LoginCaptchaVM login = new LoginCaptchaVM();
        login.setUsername("someone");
        login.setPassword("testpassword");
        // captchaId / captchaToken intentionally omitted

        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isBadRequest());
    }
}

@AutoConfigureMockMvc
@IntegrationTest
@TestPropertySource(
    properties = {
        "captcha.enabled=true",
        "captcha.dev-bypass=false",
    }
)
class UserJWTControllerCaptchaIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocalCaptchaService localCaptchaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void authenticate_rejectsInvalidCaptchaWithStandardErrorKey() throws Exception {
        User user = new User();
        user.setLogin("user-jwt-captcha");
        user.setEmail("user-jwt-captcha@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));
        userRepository.saveAndFlush(user);

        LocalCaptchaService.Issue issue = localCaptchaService.issue();

        LoginCaptchaVM login = new LoginCaptchaVM();
        login.setUsername("user-jwt-captcha");
        login.setPassword("test");
        login.setCaptchaId(issue.id);
        login.setCaptchaToken("WRONG1");

        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Captcha Invalid"))
            .andExpect(jsonPath("$.errorKey").value("captcha.validation"))
            .andExpect(jsonPath("$.parameters.errorKey").value("captcha.validation"))
            .andExpect(jsonPath("$.parameters.message").value("error.captcha.validation"));
    }

    @Test
    @Transactional
    void authenticate_acceptsValidCaptcha() throws Exception {
        User user = new User();
        user.setLogin("user-jwt-captcha-ok");
        user.setEmail("user-jwt-captcha-ok@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));
        userRepository.saveAndFlush(user);

        LocalCaptchaService.Issue issue = localCaptchaService.issue();
        // LocalCaptchaService stores lowercase text; peek via verify is consume-only,
        // so render path isn't needed — use reflection-free known pattern by re-issuing
        // and answering with a forced known value through verify after injecting.
        // Instead: issue, then use package-visible store by verifying with wrong then
        // re-issue and read answer from a test helper.
        String answer = CaptchaTestSupport.peekAnswer(localCaptchaService, issue.id);

        LoginCaptchaVM login = new LoginCaptchaVM();
        login.setUsername("user-jwt-captcha-ok");
        login.setPassword("test");
        login.setCaptchaId(issue.id);
        login.setCaptchaToken(answer);

        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isNotEmpty());
    }
}
