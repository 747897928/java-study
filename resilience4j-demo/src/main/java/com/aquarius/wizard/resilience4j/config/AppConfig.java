package com.aquarius.wizard.resilience4j.config;

import com.aquarius.wizard.resilience4j.MDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoyijie
 * @since 2024/7/29 16:57
 */
@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<MDCFilter> loggingFilter() {
        FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MDCFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
