package com.langtuo.teamachine.web.security.component;

import com.alibaba.fastjson.JSON;
import com.langtuo.teamachine.api.result.IceMachineResult;
import com.langtuo.teamachine.web.constant.WebConsts;
import com.langtuo.teamachine.web.security.model.AdminDetails;
import com.langtuo.teamachine.web.helper.JwtTokenHelper;
import com.langtuo.teamachine.web.model.LoginSuccessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TeaMachineAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${teamachine.jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        HttpSession session = request.getSession();
        UserDetails authUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute(WebConsts.SESSION_ATTR_USER, authUser);
        session.setAttribute(WebConsts.SESSION_ATTR_USERNAME, authUser.getUsername());
        session.setAttribute(WebConsts.SESSION_ATTR_AUTHORITIES, authentication.getAuthorities());

        // 设置 resp 头部信息
        response.setHeader(WebConsts.RESP_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, WebConsts.RESP_HEADER_VAL_ALL);
        response.setHeader(WebConsts.RESP_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, WebConsts.RESP_HEADER_VAL_TRUE);
        response.setStatus(HttpServletResponse.SC_OK);

        // 构建返回内容
        LoginSuccessDTO dto = new LoginSuccessDTO();
        dto.setJwtToken(tokenHead + jwtTokenHelper.generateToken(authUser));
        dto.setLoginName(authUser.getUsername());
        if (authUser instanceof AdminDetails) {
            AdminDetails authAdmin = (AdminDetails) authUser;
            dto.setPermitActCodeList(authAdmin.getPermitActCodeList());
        }

        IceMachineResult<LoginSuccessDTO> result = IceMachineResult.success(dto);
        response.getWriter().println(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
