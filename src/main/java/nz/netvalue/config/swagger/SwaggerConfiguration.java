package nz.netvalue.config.swagger;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.model.version.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final Version version;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nz.netvalue.controller"))
                .build()
                .apiInfo(apiEndpointInfo());
    }

    private ApiInfo apiEndpointInfo() {
        return new ApiInfoBuilder().title("Charge points controller")
                .version(version.getApplication())
                .build();
    }

    @Primary
    @Profile("development")
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource resource = new SwaggerResource();
            resource.setName("NetValue");
            resource.setSwaggerVersion("2.0");
            resource.setLocation("openapi/openapi.yaml");

            return Collections.singletonList(resource);
        };
    }
}
