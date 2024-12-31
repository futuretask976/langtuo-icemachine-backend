package com.langtuo.teamachine.web.security.conf;

import com.langtuo.teamachine.web.helper.JwtTokenHelper;
import com.langtuo.teamachine.web.security.component.*;
import com.langtuo.teamachine.web.security.encoder.MD5PasswordEncoder;
import com.langtuo.teamachine.web.security.service.TeaMachineUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityBeanConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MD5PasswordEncoder();
    }

    @Bean
    public TeaMachineUserDetailService gxTeaMachineUserDetailService() {
        return new TeaMachineUserDetailService();
    }

    @Bean
    public AccessDeniedHandler restfulAccessDeniedHandler() {
        return new TeaMachineAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint restfulAuthenticationEntryPoint() {
        return new TeaMachineAuthenticationEntryPoint();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public TeaMachineJwtAuthenticationTokenFilter gxJwtAuthenticationTokenFilter() {
        return new TeaMachineJwtAuthenticationTokenFilter();
    }

    @Bean
    public JwtTokenHelper gxJwtTokenHelper() {
        return new JwtTokenHelper();
    }

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有域名进行跨域调用
        config.addAllowedOriginPattern("*");
        // 允许跨越发送cookie
        config.setAllowCredentials(true);
        // 放行全部原始头信息
        config.addAllowedHeader("*");
        // 允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationSuccessHandler authSuccessHandler() {
        return new TeaMachineAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authFailureHandler() {
        return new TeaMachineAuthFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new TeaMachineLogoutSuccessHandler();
    }
}
