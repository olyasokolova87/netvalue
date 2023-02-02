package nz.netvalue.config.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Configuration
@RequestMapping("/")
public class SwaggerRedirectController {

    @GetMapping(value = "/swagger-ui")
    public RedirectView swaggerUi() {
        return new RedirectView("swagger-ui/index.html");
    }

    @GetMapping(value = "/swagger")
    public RedirectView swaggerApiDocs() {
        return new RedirectView("v2/api-docs");
    }
}
