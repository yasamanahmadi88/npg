package ix.portal.npg.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientForwardController {

    @GetMapping(
        value = {
            "/{path:^(?!api|management|swagger-ui|swagger-resources|v3|content|i18n|app|assets|.*\\..*).*$}",
            "/{path:^(?!api|management|swagger-ui|swagger-resources|v3|content|i18n|app|assets|.*\\..*).*$}/**"
        }
    )
    public String forward() {
        return "forward:/index.html";
    }
}