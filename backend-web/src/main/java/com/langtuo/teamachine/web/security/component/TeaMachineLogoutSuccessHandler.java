package com.langtuo.teamachine.web.security.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.web.constant.WebConsts;
import com.langtuo.teamachine.web.model.LogoutSuccessDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TeaMachineLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 清除会话
        request.getSession().invalidate();

        // 删除cookie
        Cookie cookie = new Cookie(WebConsts.COOKIE_NAME_JSESSION_ID, null);
        cookie.setMaxAge(WebConsts.COOKIE_MAX_AGE);
        cookie.setPath(WebConsts.COOKIE_PATH);
        response.addCookie(cookie);

        response.setHeader(WebConsts.RESP_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, WebConsts.RESP_HEADER_VAL_ALL);
        response.setHeader(WebConsts.RESP_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, WebConsts.RESP_HEADER_VAL_TRUE);
        response.setStatus(HttpServletResponse.SC_OK);

        // 如果是前后端分离项目，这里可以返回JSON字符串提示前端注销成功
        LogoutSuccessDTO dto = new LogoutSuccessDTO();
        TeaMachineResult<LogoutSuccessDTO> result = TeaMachineResult.success(dto);
        response.getWriter().println(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
