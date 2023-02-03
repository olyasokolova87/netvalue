package nz.netvalue.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller processing redirects to swagger-ui
 */
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
