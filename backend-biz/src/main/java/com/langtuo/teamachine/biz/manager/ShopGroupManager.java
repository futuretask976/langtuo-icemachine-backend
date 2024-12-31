package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiaqing
 */
@Component
public class ShopGroupManager {
    @Resource
    private AdminManager adminManager;

    @Resource
    private OrgManager orgManager;

    @Resource
    private MachineGroupAccessor machineGroupAccessor;

    public List<String> getShopGroupCodeListByOrgNameList(String tenantCode, List<String> orgNameList) {
        List<MachineGroupPO> machineGroupPOList = machineGroupAccessor.list(tenantCode, orgNameList);
        if (CollectionUtils.isEmpty(machineGroupPOList)) {
            return null;
        }

        List<String> shopGroupCodeList = machineGroupPOList.stream()
                .map(MachineGroupPO::getMachineGroupCode)
                .collect(Collectors.toList());
        return shopGroupCodeList;
    }

    public List<String> getShopGroupCodeListByLoginSession(String tenantCode) {
        List<String> orgNameList = orgManager.getOrgNameListByLoginSession(tenantCode);
        if (CollectionUtils.isEmpty(orgNameList)) {
            return null;
        }


        List<String> shopGroupCodeList = getShopGroupCodeListByOrgNameList(tenantCode, orgNameList);
        return shopGroupCodeList;
    }

    public List<String> getShopGroupCodeListByLoginName(String tenantCode, String loginName) {
        List<String> orgNameList = orgManager.getOrgNameListByLoginName(tenantCode, loginName);
        if (CollectionUtils.isEmpty(orgNameList)) {
            return null;
        }


        List<String> shopGroupCodeList = getShopGroupCodeListByOrgNameList(tenantCode, orgNameList);
        return shopGroupCodeList;
    }
}
