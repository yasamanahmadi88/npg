package ix.portal.npg.web.rest;

import ix.portal.npg.web.rest.vm.InitVariableVM;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicResource {

    private final Logger log = LoggerFactory.getLogger(PublicResource.class);

    private static final String ENTITY_NAME = "publicRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Value("${portal.captcha.backUrl}")
    private String captchaBackUrl;

    /**
     * {@code GET  /backUrl} : get backUrl for dynamic and configurable captcha backendUrl.
     *
     * @return String captchaBackUrl
     */
    @GetMapping("/backUrl")
    public ResponseEntity<InitVariableVM> getBackUrl() {
        log.debug("REST request to backUrl");
        return ResponseEntity.of(Optional.of(InitVariableVM.of(captchaBackUrl)));
    }
}


