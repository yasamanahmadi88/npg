package ix.portal.npg.security.captcha;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ix.portal.npg.web.rest.errors.InvalidCaptchaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CaptchaValidationServiceTest {

    @Mock
    private LocalCaptchaService localCaptchaService;

    private CaptchaProperties properties;
    private CaptchaValidationService service;

    @BeforeEach
    void setUp() {
        properties = new CaptchaProperties();
        properties.setEnabled(true);
        properties.setDevBypass(false);
        service = new CaptchaValidationService(properties, localCaptchaService);
    }

    @Test
    void validate_skipsWhenDisabled() {
        properties.setEnabled(false);

        assertThatCode(() -> service.validate(null, null, "127.0.0.1")).doesNotThrowAnyException();
        verify(localCaptchaService, never()).verifyAndConsume(anyString(), anyString());
    }

    @Test
    void validate_skipsWhenDevBypass() {
        properties.setDevBypass(true);

        assertThatCode(() -> service.validate("id", "token", "127.0.0.1")).doesNotThrowAnyException();
        verify(localCaptchaService, never()).verifyAndConsume(anyString(), anyString());
    }

    @Test
    void validate_rejectsBlankFieldsWithCaptchaValidationKey() {
        assertThatThrownBy(() -> service.validate(" ", "token", "127.0.0.1"))
            .isInstanceOf(InvalidCaptchaException.class)
            .extracting(ex -> ((InvalidCaptchaException) ex).getErrorKey())
            .isEqualTo("captcha.validation");

        assertThatThrownBy(() -> service.validate("id", "", "127.0.0.1")).isInstanceOf(InvalidCaptchaException.class);
        verify(localCaptchaService, never()).verifyAndConsume(anyString(), anyString());
    }

    @Test
    void validate_rejectsInvalidToken() {
        when(localCaptchaService.verifyAndConsume("cid", "bad")).thenReturn(false);

        assertThatThrownBy(() -> service.validate("cid", "bad", "127.0.0.1"))
            .isInstanceOf(InvalidCaptchaException.class)
            .extracting(ex -> ((InvalidCaptchaException) ex).getErrorKey())
            .isEqualTo("captcha.validation");
    }

    @Test
    void validate_acceptsValidToken() {
        when(localCaptchaService.verifyAndConsume("cid", "good")).thenReturn(true);

        assertThatCode(() -> service.validate("cid", "good", "127.0.0.1")).doesNotThrowAnyException();
        verify(localCaptchaService).verifyAndConsume("cid", "good");
    }
}
