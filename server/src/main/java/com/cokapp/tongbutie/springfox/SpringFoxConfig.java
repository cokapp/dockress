package com.cokapp.tongbutie.springfox;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan("com.cokapp.quick.module.auth.web")
public class SpringFoxConfig {
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("同步贴").description("API文档").termsOfServiceUrl("http://api.cokapp.com")
				.contact("dev@cokapp.com").license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0").build();
	}

	private ApiKey apiKey() {
		return new ApiKey("mykey", "access_token", "header");
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("mykey", authorizationScopes));
	}

	@Bean
	public Docket engineAPI() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("v1").select().apis(RequestHandlerSelectors.any())
				.paths(this.enginePaths()).build().directModelSubstitute(ObjectNode.class, HashMap.class)
				.apiInfo(this.apiInfo()).securitySchemes(Lists.newArrayList(this.apiKey()))
				.securityContexts(Lists.newArrayList(this.securityContext()))
				// TODO 此处需要通过ApiKey的方式解决
				.globalOperationParameters(
						Lists.newArrayList(new ParameterBuilder().name("access_token").description("访问令牌")
								.modelRef(new ModelRef("string")).parameterType("query").required(false).build()));
	}

	private Predicate<String> enginePaths() {
		return PathSelectors.regex("/.*");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.regex("/.*"))
				.build();
	}

}