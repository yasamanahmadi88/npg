package ix.portal.npg.security.captcha;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ix.portal.npg.web.rest.errors.InvalidCaptchaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaptchaValidationServiceTest {

    private CaptchaProperties properties;
    private LocalCaptchaService localCaptchaService;
    private CaptchaValidationService service;

    @BeforeEach
    void setUp() {
        properties = new CaptchaProperties();
        properties.setEnabled(true);
        properties.setDevBypass(false);
        localCaptchaService = mock(LocalCaptchaService.class);
        service = new CaptchaValidationService(properties, localCaptchaService);
    }

    @Test
    void rejectsMissingCaptcha() {
        assertThatThrownBy(() -> service.validate(null, null, "127.0.0.1")).isInstanceOf(InvalidCaptchaException.class);
    }

    @Test
    void rejectsInvalidCaptcha() {
        when(localCaptchaService.verifyAndConsume("captcha-id", "WRONG1")).thenReturn(false);

        assertThatThrownBy(() -> service.validate("captcha-id", "WRONG1", "127.0.0.1"))
            .isInstanceOf(InvalidCaptchaException.class);

        verify(localCaptchaService).verifyAndConsume("captcha-id", "WRONG1");
    }

    @Test
    void acceptsValidCaptcha() {
        when(localCaptchaService.verifyAndConsume("captcha-id", "ABC123")).thenReturn(true);

        assertThatCode(() -> service.validate("captcha-id", "ABC123", "127.0.0.1")).doesNotThrowAnyException();
    }
}
