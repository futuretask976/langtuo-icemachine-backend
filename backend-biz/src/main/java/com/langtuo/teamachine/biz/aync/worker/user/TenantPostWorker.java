package com.langtuo.teamachine.biz.aync.worker.user;

import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.dao.node.user.OrgNode;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public class TenantPostWorker implements Runnable {
    /**
     *
     */
    private String tenantCode;

    public TenantPostWorker(JSONObject jsonPayload) {
        this.tenantCode = jsonPayload.getString(CommonConsts.JSON_KEY_TENANT_CODE);
        if (StringUtils.isBlank(tenantCode)) {
            log.error("checkParam|illegalArgument|tenantCode=" + tenantCode);
            throw new IllegalArgumentException("tenantCode is blank");
        }
    }

    @Override
    public void run() {
        RolePO rolePO = new RolePO();
        rolePO.setTenantCode(tenantCode);
        rolePO.setRoleCode(CommonConsts.ROLE_CODE_TENANT_SUPER);
        rolePO.setRoleName(CommonConsts.ROLE_NAME_TENANT_SUPER);
        rolePO.setSysReserved(CommonConsts.ROLE_SYS_RESERVED);

        RoleAccessor roleAccessor = SpringAccessorUtils.getRoleAccessor();
        int inserted4Role = roleAccessor.insert(rolePO);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4Role) {
            log.error("insertRole|error|inserted4Role=" + inserted4Role);
        }

        PermitActAccessor permitActAccessor = SpringAccessorUtils.getPermitActAccessor();
        List<PermitActPO> permitActPOList = permitActAccessor.selectPermitActList();
        if (CollectionUtils.isEmpty(permitActPOList)) {
            log.error("getPermitActPOList|empty");
            return;
        }

        RoleActRelAccessor roleActRelAccessor = SpringAccessorUtils.getRoleActRelAccessor();
        for (PermitActPO permitActPO : permitActPOList) {
            RoleActRelPO actRelPO = new RoleActRelPO();
            actRelPO.setTenantCode(tenantCode);
            actRelPO.setRoleCode(CommonConsts.ROLE_CODE_TENANT_SUPER);
            actRelPO.setPermitActCode(permitActPO.getPermitActCode());
            int inserted4ActRel = roleActRelAccessor.insert(actRelPO);
            if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4Role) {
                log.error("insert4ActRel|error|inserted4ActRel=" + inserted4ActRel);
            }
        }

        OrgNode orgNode = new OrgNode();
        orgNode.setTenantCode(tenantCode);
        orgNode.setOrgName(CommonConsts.ORG_NAME_TOP);

        OrgAccessor orgAccessor = SpringAccessorUtils.getOrgAccessor();
        int inserted4Org = orgAccessor.insert(orgNode);
        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted4Org) {
            log.error("insert4Org|error|inserted4Org=" + inserted4Org);
        }
    }
}
