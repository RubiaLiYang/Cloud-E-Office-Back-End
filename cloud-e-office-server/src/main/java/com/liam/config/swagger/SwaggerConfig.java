package com.liam.config.swagger;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author XiaoYang
 * @date 2021-05-31 10:14
 * @projectName cloud-e-office-back-end
 * @name SwaggerConfig
 * @description Swagger配置类
 */
@Configuration
@EnableSwagger2 //开启swagger
public class SwaggerConfig  {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.liam.controller"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(securityContexts())
        .securitySchemes(securitySchemes());
  }

  public ApiInfo apiInfo(){

    Contact liam = new Contact("Liam", "http:localhost:8081/doc.html", "LiamYang99@outlook.com");

    return new ApiInfoBuilder()
        .title("云E办 api 文档")
        .description("接口文档")
        .contact(liam)
        .version("1.0")
        .build();
  }

  private List<ApiKey> securitySchemes() {
    //设置请求头信息
    List<ApiKey> result = new ArrayList<>();
    ApiKey apiKey = new ApiKey("Authorization","Authorization", "Header");
    result.add(apiKey);
    return result;
  }

  private List<SecurityContext> securityContexts() {
    //设置需要登录认证的路径
    List<SecurityContext> result = new ArrayList<>();
    result.add(getContextByPath("/hello/.*"));
    return result;
  }

  private SecurityContext getContextByPath(String pathRegex) {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex(pathRegex))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    List<SecurityReference> result = new ArrayList<>();
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    result.add(new SecurityReference("Authorization", authorizationScopes));
    return result;
  }
}
