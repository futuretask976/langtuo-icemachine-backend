package com.langtuo.teamachine.web.security.conf;

import com.langtuo.teamachine.dao.constant.PermitActEnum;
import com.langtuo.teamachine.web.constant.WebConsts;
import com.langtuo.teamachine.web.security.component.TeaMachineJwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * SpringSecurity相关配置，仅用于配置SecurityFilterChain
 */
@Configuration
@EnableWebSecurity
public class SecurityChainConfig {
    @Autowired
    private AccessDeniedHandler teaMachineAccessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint teaMachineAuthenticationEntryPoint;

    @Autowired
    private LogoutSuccessHandler teaMachineLogoutSuccessHandler;

    @Autowired
    private UserDetailsService teaMachineUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler teaMachineAuthSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler teaMachineAuthFailureHandler;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private TeaMachineJwtAuthenticationTokenFilter teaMachineJwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 登录设置
        httpSecurity.formLogin(formLogin -> {
            formLogin
                    .loginPage(WebConsts.ANT_PATTERN_LOGIN_PATH) // Login page will be accessed through this endpoint. We will create a controller method for this.
                    .loginProcessingUrl(WebConsts.ANT_PATTERN_LOGIN_PROCESSING_PATH) // This endpoint will be mapped internally. This URL will be our Login form post action.
                    .usernameParameter(WebConsts.PARAM_KEY_USERNAME)
                    .passwordParameter(WebConsts.PARAM_KEY_PASSWORD)
                    .permitAll() // We re permitting all for login page
                    .successHandler(teaMachineAuthSuccessHandler) // .defaultSuccessUrl("/welcome") // If the login is successful, user will be redirected to this URL.
                    .failureHandler(teaMachineAuthFailureHandler);
                    // .failureUrl("/login?error=true"); // If the user fails to login, application will redirect the user to this endpoint
        });

        // 登出设置
        httpSecurity.logout(logout ->
                logout
                        .logoutUrl(WebConsts.ANT_PATTERN_LOGOUT_PATH)
                        .logoutSuccessHandler(teaMachineLogoutSuccessHandler)
                        //.logoutSuccessUrl("/bye")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies(WebConsts.COOKIE_KEY_JSESSIONID) // 如果你使用cookie来传递session id
        );

        // 允许跨域请求的OPTIONS请求
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll();

        // 不需要保护的资源路径允许访问
        for (String ignoreUrl : ignoreUrlsConfig.getUrls()) {
            httpSecurity.authorizeRequests().antMatchers(ignoreUrl).permitAll();
        }

        // token简单验证
        httpSecurity.addFilterBefore(teaMachineJwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置授权处理，授权管理器可以配置多个
        httpSecurity.authorizeRequests()
                // 安全
                .antMatchers(WebConsts.ANT_PATTERN_OSS_PATH).hasAnyRole(
                        PermitActEnum.TEA_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                .antMatchers(WebConsts.ANT_PATTERN_MQTT_PATH).hasAnyRole(
                        PermitActEnum.TEA_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                // 设备
                .antMatchers(WebConsts.ANT_PATTERN_MODEL_PATH).hasAnyRole(
                        PermitActEnum.MODEL_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                .antMatchers(WebConsts.ANT_PATTERN_DEPLOY_PATH).hasAnyRole(
                        PermitActEnum.DEPLOY_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_MACHINE_PATH).hasAnyRole(
                        PermitActEnum.MACHINE_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_ANDROID_PATH).hasAnyRole(
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_ANDROID_APP_MGT,
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                // 用户
                .antMatchers(WebConsts.ANT_PATTERN_ROLE_LIST_PATH).hasAnyRole(
                        PermitActEnum.ROLE_MGT.getPermitActCode(),
                        PermitActEnum.ORG_MGT.getPermitActCode(),
                        PermitActEnum.ADMIN_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_PERMITACT_LIST_PATH).hasAnyRole(
                        PermitActEnum.ROLE_MGT.getPermitActCode(),
                        PermitActEnum.ORG_MGT.getPermitActCode(),
                        PermitActEnum.ADMIN_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_ORG_LIST_PATH).hasAnyRole(
                        PermitActEnum.ROLE_MGT.getPermitActCode(),
                        PermitActEnum.ORG_MGT.getPermitActCode(),
                        PermitActEnum.ADMIN_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_TENANT_PATH).hasAnyRole(
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_TENANT_MGT)
                .antMatchers(WebConsts.ANT_PATTERN_ORG_PATH).hasAnyRole(
                        PermitActEnum.ORG_MGT.getPermitActCode())
                //.antMatchers(WebConsts.ANT_PATTERN_PERMITACT_PATH).hasAnyRole(
                //        PermitActEnum.PERMIT_ACT_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_ROLE_PATH).hasAnyRole(
                        PermitActEnum.ROLE_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_ADMIN_PATH).hasAnyRole(
                        PermitActEnum.ADMIN_MGT.getPermitActCode())
                // 店铺
                .antMatchers(WebConsts.ANT_PATTERN_SHOP_GROUP_LIST_PATH).hasAnyRole(
                        PermitActEnum.SHOP_GROUP_MGT.getPermitActCode(),
                        PermitActEnum.SHOP_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_SHOP_GROUP_PATH).hasAnyRole(
                        PermitActEnum.SHOP_GROUP_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_SHOP_PATH).hasAnyRole(
                        PermitActEnum.SHOP_MGT.getPermitActCode())
                // 饮品生产
                .antMatchers(WebConsts.ANT_PATTERN_TOPPING_TYPE_LIST_PATH).hasAnyRole(
                        PermitActEnum.TOPPING_TYPE_MGT.getPermitActCode(),
                        PermitActEnum.TOPPING_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_TEA_TYPE_LIST_PATH).hasAnyRole(
                        PermitActEnum.TEA_TYPE_MGT.getPermitActCode(),
                        PermitActEnum.TEA_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_TEA_LIST_PATH).hasAnyRole(
                        PermitActEnum.SERIES_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_TOPPING_TYPE_PATH).hasAnyRole(
                        PermitActEnum.TOPPING_TYPE_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_TOPPING_PATH).hasAnyRole(
                        PermitActEnum.TOPPING_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                .antMatchers(WebConsts.ANT_PATTERN_SEPC_PATH).hasAnyRole(
                        PermitActEnum.SPEC_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                .antMatchers(WebConsts.ANT_PATTERN_TEA_TYPE_PATH).hasAnyRole(
                        PermitActEnum.TEA_TYPE_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_TEA_PATH).hasAnyRole(
                        PermitActEnum.TEA_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_ACCURACY_PATH).hasAnyRole(
                        PermitActEnum.ACCURACY_TPL_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                // 菜单
                .antMatchers(WebConsts.ANT_PATTERN_SERIES_LIST_PATH).hasAnyRole(
                        PermitActEnum.SERIES_MGT.getPermitActCode(),
                        PermitActEnum.MENU_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_SERIES_PATH).hasAnyRole(
                        PermitActEnum.SERIES_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_MENU_PATH).hasAnyRole(
                        PermitActEnum.MENU_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                // 食安规则
                .antMatchers(WebConsts.ANT_PATTERN_DRAIN_PATH).hasAnyRole(
                        PermitActEnum.DRAIN_RULE_RULE_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                .antMatchers(WebConsts.ANT_PATTERN_CLEAN_PATH).hasAnyRole(
                        PermitActEnum.CLEAN_RULE_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                .antMatchers(WebConsts.ANT_PATTERN_WARNING_PATH).hasAnyRole(
                        PermitActEnum.WARNING_RULE_MGT.getPermitActCode(),
                        WebConsts.SPECIAL_PERMIT_ACT_CODE_MACHINE)
                // 日常记录
                .antMatchers(WebConsts.ANT_PATTERN_DRAIN_ACT_PATH).hasAnyRole(
                        PermitActEnum.DRAIN_REC_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_CLEAN_ACT_PATH).hasAnyRole(
                        PermitActEnum.CLEAN_REC_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_INVALID_ACT_PATH).hasAnyRole(
                        PermitActEnum.INVALID_REC_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_SUPPLY_ACT_PATH).hasAnyRole(
                        PermitActEnum.SUPPLY_REC_MGT.getPermitActCode())
                .antMatchers(WebConsts.ANT_PATTERN_ORDER_ACT_PATH).hasAnyRole(
                        PermitActEnum.ORDER_REC_MGT.getPermitActCode())
                // 日常报表
                .antMatchers(WebConsts.ANT_PATTERN_ORDER_REPORT_PATH).hasAnyRole(
                        PermitActEnum.ORDER_REPORT_MGT.getPermitActCode())
                .anyRequest().authenticated();

        // 异常处理
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(teaMachineAccessDeniedHandler)
                .authenticationEntryPoint(teaMachineAuthenticationEntryPoint);

        return httpSecurity.build();
    }
}
