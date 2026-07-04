package ix.portal.npg.web.rest;

import ix.portal.npg.security.captcha.LocalCaptchaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api")
public class CaptchaResource {

    private final LocalCaptchaService localCaptchaService;

    public CaptchaResource(LocalCaptchaService localCaptchaService) {
        this.localCaptchaService = localCaptchaService;
    }

    //@PostMapping("/captcha-endpoint")
    @RequestMapping(value = "/captcha-endpoint", method = { RequestMethod.GET, RequestMethod.POST })
    public Map<String, String> createCaptcha() {
        LocalCaptchaService.Issue issue = localCaptchaService.issue();

        return Map.of(
            "captchaId", issue.id,
            "captchaImageUrl", "/api/captcha.png?cid=" + issue.id
        );
    }

    @GetMapping(value = "/captcha.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> image(@RequestParam("cid") String captchaId) {
        byte[] png = localCaptchaService.renderPng(captchaId);

        if (png == null) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        return ResponseEntity
            .ok()
            .cacheControl(CacheControl.noStore())
            .header(HttpHeaders.PRAGMA, "no-cache")
            .header(HttpHeaders.EXPIRES, "0")
            .body(png);
    }
}


