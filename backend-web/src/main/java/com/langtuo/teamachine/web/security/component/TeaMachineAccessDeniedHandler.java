package com.langtuo.teamachine.web.security.component;

import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.web.constant.WebConsts;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义无权限访问的返回结果
 */
public class TeaMachineAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException e) throws IOException {
        // 设置 resp 头部信息
        response.setHeader(WebConsts.RESP_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, WebConsts.RESP_HEADER_VAL_ALL);
        response.setHeader(WebConsts.RESP_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, WebConsts.RESP_HEADER_VAL_TRUE);
        response.setHeader(WebConsts.RESP_HEADER_CACHE_CONTROL,WebConsts.RESP_HEADER_VAL_NO_CACHE);
        response.setCharacterEncoding(WebConsts.RESP_HEADER_VAL_ENCODING_UTF8);
        response.setContentType(WebConsts.RESP_HEADER_VAL_CONT_TYPE_APPLICATIONJSON);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // 构建返回内容
        JSONObject responseBody = new JSONObject();
        responseBody.put(WebConsts.JSON_KEY_ACCESS_SUCCESS, WebConsts.JSON_VAL_FALSE);
        response.getWriter().println(responseBody.toJSONString());
        response.getWriter().flush();
    }
}
