package com.guangyou.rareanimal.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xukai
 * @create 2022-11-02 16:52
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 创建Docket类型的对象，并使用spring容器管理
     * Docket 是swagger的全局配置对象
     * @return
     */
    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        //API 帮助文档的描述信息。 information
        ApiInfo apiInfo =
                new ApiInfoBuilder()
                        .description("")
                        .contact(   //配置swagger文档的主体内容
                                new Contact(
                                        "Swagger开发文档", //文档发布者的名称
                                        "http://www.baidu.com", //文档发布者的官网
                                        "1825913894@qq.com")//文档发布者的邮箱
                        )
                        .title("swagger-xukai")
                        .description("swagger框架开发学习文档-详细描述信息：swagger是一个帮助接口文档开发的框架")
                        .version("v1.0")
                        .build();

        //给docket上下文配置api描述信息
        docket.apiInfo(apiInfo);

        docket
                .select()   //获取Docket 中的选择器。返回ApiSelectorBuilder。构建选择器。 如：什么包的注解
                .apis(RequestHandlerSelectors.basePackage("com.guangyou.rareanimal.controller"))//设定扫描哪个包（包含子包）
                .build(); //重构docket对象

        return docket;
    }



}
