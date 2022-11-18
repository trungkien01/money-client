package com.credit.webcredit.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.credit.webcredit"})
public class WebConfig implements WebMvcConfigurer {
    private static String DIR_NAME = "user-photos";

    @Value(value = "${requests.number}")
    private String NUMBER_REQUEST;

    private void exposeDirectory(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(DIR_NAME);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (DIR_NAME.startsWith("../")) DIR_NAME = DIR_NAME.replace("../", "");

        registry.addResourceHandler("/" + DIR_NAME + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // web-users
        registry.addResourceHandler("/users/assets/**").addResourceLocations("classpath:/static/users/assets/");
        registry.addResourceHandler("/users/css/**").addResourceLocations("classpath:/static/users/css/");
        registry.addResourceHandler("/users/img/**").addResourceLocations("classpath:/static/users/img/");
        registry.addResourceHandler("/users/js/**").addResourceLocations("classpath:/static/users/js/");
        registry.addResourceHandler("/users/login/**").addResourceLocations("classpath:/static/users/login/");
        registry.addResourceHandler("/users/logoanhphc/**").addResourceLocations("classpath:/static/users/logoanhphc/");
        exposeDirectory(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/403").setViewName("403");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(false).allowedHeaders("*")
                .exposedHeaders("*").maxAge(60 *30)
                .allowedMethods("*");
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
                    @Override
                    public void customize(Connector connector) {
                        Arrays.stream(connector.getProtocolHandler().findUpgradeProtocols())
                                .filter(upgradeProtocol -> upgradeProtocol instanceof Http2Protocol)
                                .map(upgradeProtocol -> (Http2Protocol) upgradeProtocol)
                                .forEach(http2Protocol -> http2Protocol.setMaxConcurrentStreamExecution(Integer.parseInt(NUMBER_REQUEST)));
                    }
                });
            }
        };
    }
}
