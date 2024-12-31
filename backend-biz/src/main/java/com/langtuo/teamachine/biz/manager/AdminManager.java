package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.user.AdminAccessor;
import com.langtuo.teamachine.dao.po.user.AdminPO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@Component
public class AdminManager {
    @Resource
    private AdminAccessor adminAccessor;

    public AdminPO getAdminPOByLoginName(String tenantCode, String loginName) {
        AdminPO adminPO = adminAccessor.getByLoginName(tenantCode, loginName);
        return adminPO;
    }

    /**
     * 获取当前登录的管理员
     * @param tenantCode
     * @return
     */
    public AdminPO getAdminPOByLoginSession(String tenantCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("couldn't find login session");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String adminLoginName = userDetails.getUsername();
        if (StringUtils.isBlank(adminLoginName)) {
            throw new IllegalArgumentException("couldn't find login session");
        }

        AdminPO adminPO = adminAccessor.getByLoginName(tenantCode, adminLoginName);
        return adminPO;
    }
}
