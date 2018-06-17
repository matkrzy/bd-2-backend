package com.photos.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Micha Królewski on 2018-05-26.
 * @version x
 */

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.photos.api"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal Server Error")
                                        .build()
                        )
                )
                .globalResponseMessage(RequestMethod.POST,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal Server Error")
                                        .build()
                        )
                )
                .globalResponseMessage(RequestMethod.PUT,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal Server Error")
                                        .build()
                        )
                )
                .globalResponseMessage(RequestMethod.DELETE,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal Server Error")
                                        .build()
                        )
                );
    }
}
