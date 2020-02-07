package com.ecommerce.microcommerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()//Initialiser class ApiSelectorBuilder
                .apis(RequestHandlerSelectors.basePackage("com.ecommerce.microcommerce.web"))//Api filtre la documentation à exposer selon les contrôleurs, la méthode basePackage documente seulement les classes dans le package donnée
                .paths(PathSelectors.regex("/Produits.*"))//Filtre selon un une url donnée avec la méthode Regex
                .build()
                .tags(new Tag("ProductController","API pour les opérations CRUD sur les produits"));
    }


}
