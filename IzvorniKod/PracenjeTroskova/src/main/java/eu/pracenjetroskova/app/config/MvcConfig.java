package eu.pracenjetroskova.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/profil").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/stednje").setViewName("stednje");
        registry.addViewController("/prihodi").setViewName("prihodi");
        registry.addViewController("/troskovi").setViewName("troskovi");
        registry.addViewController("/profil/postavke").setViewName("account");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/js/**")
                .addResourceLocations(
                        "classpath:/static/js/");
    }




}