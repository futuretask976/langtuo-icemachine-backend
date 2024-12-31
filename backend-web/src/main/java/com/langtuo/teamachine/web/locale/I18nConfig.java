package com.langtuo.teamachine.web.locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @author Jiaqing
 */
@Configuration
public class I18nConfig {
    /**
     * 用于变更 locale 的参数名称
     */
    private static final String LOCALE_CHANGE_PARAM_NAME = "Lang";

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE); // 设置默认Locale
        return localeResolver;
    }

    @Bean
    public WebMvcConfigurer localeInterceptor() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                TeaMachineLocaleChangeInterceptor teaMachineLocaleChangeInterceptor = new TeaMachineLocaleChangeInterceptor();
                teaMachineLocaleChangeInterceptor.setParamName(LOCALE_CHANGE_PARAM_NAME); // 可以通过 URL 参数改变语言
                registry.addInterceptor(teaMachineLocaleChangeInterceptor);
            }
        };
        return webMvcConfigurer;
    }
}
