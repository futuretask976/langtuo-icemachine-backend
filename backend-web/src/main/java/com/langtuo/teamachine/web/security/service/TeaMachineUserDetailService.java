package com.langtuo.teamachine.web.security.service;

import com.langtuo.teamachine.api.model.device.DeployDTO;
import com.langtuo.teamachine.api.model.user.AdminDTO;
import com.langtuo.teamachine.api.model.user.RoleDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.device.DeployMgtService;
import com.langtuo.teamachine.api.service.device.MachineMgtService;
import com.langtuo.teamachine.api.service.user.AdminMgtService;
import com.langtuo.teamachine.api.service.user.RoleMgtService;
import com.langtuo.teamachine.web.security.model.AdminDetails;
import com.langtuo.teamachine.web.security.model.MachineDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.langtuo.teamachine.api.result.TeaMachineResult.getModel;

@Slf4j
public class TeaMachineUserDetailService implements UserDetailsService {
    @Autowired
    private HttpServletRequest request;

    @Resource
    private DeployMgtService deployMgtService;

    @Autowired
    private AdminMgtService adminMgtService;

    @Autowired
    private RoleMgtService roleMgtService;

    @Autowired
    private MachineMgtService machineMgtService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 会调用这里的是登录页面，或者没有经过认证，所以可以特殊处理
        String tenantCode = request.getParameter("tenantCode");
        if (StringUtils.isBlank(tenantCode)) {
            throw new IllegalArgumentException("tenantCode is blank");
        }

        String deployCode = request.getHeader("machineCode");
        String machineCode = userName;
        if (StringUtils.isBlank(deployCode) || StringUtils.isBlank(machineCode)) {
            return loadAdminUserDetails(tenantCode, userName);
        } else {
            return loadMachineUserDetails(tenantCode, deployCode, machineCode);
        }
    }

    public UserDetails loadMachineUserDetails(String tenantCode, String deployCode, String machineCode) {
        DeployDTO deployDTO = getModel(deployMgtService.getByDeployCode(tenantCode, deployCode));
        if (deployDTO == null) {
            return null;
        }
        if (!deployDTO.getMachineCode().equals(machineCode)) {
            return null;
        }

        MachineDetails adminDetails = new MachineDetails(deployDTO);
        return adminDetails;
    }

    public UserDetails loadAdminUserDetails(String tenantCode, String loginName) {
        TeaMachineResult<AdminDTO> adminResult = adminMgtService.getByLoginName(tenantCode, loginName);
        if (adminResult == null && !adminResult.isSuccess() || adminResult.getModel() == null) {
            return null;
        }
        AdminDTO adminDTO = adminResult.getModel();

        TeaMachineResult<RoleDTO> roleResult = roleMgtService.getByCode(tenantCode, adminDTO.getRoleCode());
        if (roleResult == null && !roleResult.isSuccess() || roleResult.getModel() == null) {
            return null;
        }
        RoleDTO roleDTO = roleResult.getModel();


        AdminDetails adminDetails = new AdminDetails(adminDTO, roleDTO);
        return adminDetails;
    }
}
