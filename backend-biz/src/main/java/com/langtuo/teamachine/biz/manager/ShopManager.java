package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiaqing
 */
@Component
public class ShopManager {
    @Resource
    private ShopGroupManager shopGroupManager;

    @Resource
    private ShopAccessor shopAccessor;

    /**
     * 根据店铺组编码获取其关联的店铺编码列表
     * @param tenantCode
     * @return
     */
    public List<String> getShopCodeListByShopGroupCodeList(String tenantCode, List<String> shopGroupCodeList) {
        if (CollectionUtils.isEmpty(shopGroupCodeList)) {
            return null;
        }

        List<ShopPO> shopPOList = shopAccessor.listByShopGroupCodeList(tenantCode,
                shopGroupCodeList);
        if (CollectionUtils.isEmpty(shopPOList)) {
            return null;
        }

        List<String> shopCodeList = shopPOList.stream()
                .map(ShopPO::getShopCode)
                .collect(Collectors.toList());
        return shopCodeList;
    }

    public List<String> getShopCodeListByLoginSession(String tenantCode) {
        List<String> shopGroupCodeList = shopGroupManager.getShopGroupCodeListByLoginSession(tenantCode);
        if (CollectionUtils.isEmpty(shopGroupCodeList)) {
            return null;
        }

        List<ShopPO> shopPOList = shopAccessor.listByShopGroupCodeList(tenantCode, shopGroupCodeList);
        if (CollectionUtils.isEmpty(shopPOList)) {
            return null;
        }

        List<String> shopCodeList = shopPOList.stream()
                .map(ShopPO::getShopCode)
                .collect(Collectors.toList());
        return shopCodeList;
    }
}
