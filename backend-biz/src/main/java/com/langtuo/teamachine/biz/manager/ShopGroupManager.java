package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.shop.ShopGroupAccessor;
import com.langtuo.teamachine.dao.po.shop.ShopGroupPO;
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
    private ShopGroupAccessor shopGroupAccessor;

    public List<String> getShopGroupCodeListByOrgNameList(String tenantCode, List<String> orgNameList) {
        List<ShopGroupPO> shopGroupPOList = shopGroupAccessor.list(tenantCode, orgNameList);
        if (CollectionUtils.isEmpty(shopGroupPOList)) {
            return null;
        }

        List<String> shopGroupCodeList = shopGroupPOList.stream()
                .map(ShopGroupPO::getShopGroupCode)
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
