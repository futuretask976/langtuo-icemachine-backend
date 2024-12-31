package com.langtuo.teamachine.web.security.component;

import com.langtuo.teamachine.web.constant.WebConsts;
import com.langtuo.teamachine.web.helper.JwtTokenHelper;
import com.langtuo.teamachine.web.security.service.TeaMachineUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 */
@Slf4j
public class TeaMachineJwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TeaMachineUserDetailService teaMachineUserDetailService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Value("${teamachine.jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${teamachine.jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String tenantCodeFromHeader = request.getHeader(WebConsts.REQ_HEADER_TENANT_CODE);
        String deployCodeFromHeader = request.getHeader(WebConsts.REQ_HEADER_DEPLOY_CODE);
        String machineCodeFromHeader = request.getHeader(WebConsts.REQ_HEADER_MACHINE_CODE);
        if (StringUtils.isBlank(machineCodeFromHeader) || StringUtils.isBlank(tenantCodeFromHeader)
                || StringUtils.isBlank(deployCodeFromHeader)) {
            // 判断是管理员登录，走正常流程
            String authHeader = request.getHeader(this.tokenHeader);
            if (authHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String authToken = authHeader.substring(this.tokenHead.length());
                String tenantCode = jwtTokenHelper.getTenantCodeFromToken(authToken);
                String userName = jwtTokenHelper.getUserNameFromToken(authToken);
                UserDetails userDetails = this.teaMachineUserDetailService.loadAdminUserDetails(tenantCode, userName);
                if (jwtTokenHelper.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } else {
            // 判断是设备登录，走特殊路程
            UserDetails machineDetails = this.teaMachineUserDetailService.loadMachineUserDetails(tenantCodeFromHeader,
                    deployCodeFromHeader, machineCodeFromHeader);
            if (machineDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        machineDetails, null, machineDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
