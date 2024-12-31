package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.user.OrgAccessor;
import com.langtuo.teamachine.dao.node.user.OrgNode;
import com.langtuo.teamachine.dao.po.user.AdminPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiaqing
 */
@Component
public class OrgManager {
    @Resource
    private AdminManager adminManager;

    @Resource
    private OrgAccessor orgAccessor;

    /**
     * 根据当前登录的管理员获取其下属的组织名称列表
     * @param tenantCode
     * @return
     */
    public List<String> getOrgNameListByLoginName(String tenantCode, String loginName) {
        AdminPO adminPO = adminManager.getAdminPOByLoginName(tenantCode, loginName);
        if (adminPO == null) {
            return null;
        }

        List<OrgNode> orgPOList = orgAccessor.listByParentOrgName(tenantCode, adminPO.getOrgName());
        List<String> orgNameList = orgPOList.stream()
                .map(OrgNode::getOrgName)
                .collect(Collectors.toList());
        return orgNameList;
    }

    public List<String> getOrgNameListByOrgName(String tenantCode, String orgName) {
        List<OrgNode> orgPOList = orgAccessor.listByParentOrgName(tenantCode, orgName);
        List<String> orgNameList = orgPOList.stream()
                .map(OrgNode::getOrgName)
                .collect(Collectors.toList());
        return orgNameList;
    }

    /**
     * 根据当前登录的管理员获取其下属的组织名称列表
     * @param tenantCode
     * @return
     */
    public List<String> getOrgNameListByLoginSession(String tenantCode) {
        AdminPO adminPO = adminManager.getAdminPOByLoginSession(tenantCode);
        if (adminPO == null) {
            return null;
        }

        List<OrgNode> orgPOList = orgAccessor.listByParentOrgName(tenantCode, adminPO.getOrgName());
        List<String> orgNameList = orgPOList.stream()
                .map(OrgNode::getOrgName)
                .collect(Collectors.toList());
        return orgNameList;
    }
}
