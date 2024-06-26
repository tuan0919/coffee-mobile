package nlu.hcmuaf.android_coffee_app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(enforceUniqueMethods = false)
public class ConfigFilterBean {
    @Bean(name = "JwtAuthFilterDisabler")
    public FilterRegistrationBean<JwtAuthFilter> disableRegisterBean(JwtAuthFilter f1) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(f1);
        registration.setEnabled(false);
        return registration;
    }
}
