package com.langtuo.teamachine.biz.convertor.shop;

import com.langtuo.teamachine.api.model.shop.ShopGroupDTO;
import com.langtuo.teamachine.api.request.shop.ShopGroupPutRequest;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.po.shop.ShopGroupPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ShopGroupMgtConvertor {
    public static List<ShopGroupDTO> convert(List<ShopGroupPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<ShopGroupDTO> list = poList.stream()
                .map(po -> convert(po))
                .collect(Collectors.toList());
        return list;
    }

    public static ShopGroupDTO convert(ShopGroupPO po) {
        if (po == null) {
            return null;
        }

        ShopGroupDTO dto = new ShopGroupDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setShopGroupCode(po.getShopGroupCode());
        dto.setShopGroupName(po.getShopGroupName());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setOrgName(po.getOrgName());

        ShopAccessor shopAccessor = SpringAccessorUtils.getShopAccessor();
        int shopCount = shopAccessor.countByShopGroupCode(po.getTenantCode(), po.getShopGroupCode());
        dto.setShopCount(shopCount);

        return dto;
    }

    public static ShopGroupPO convert(ShopGroupPutRequest request) {
        if (request == null) {
            return null;
        }

        ShopGroupPO po = new ShopGroupPO();
        po.setShopGroupName(request.getShopGroupName());
        po.setShopGroupCode(request.getShopGroupCode());
        po.setComment(request.getComment());
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setOrgName(request.getOrgName());
        return po;
    }
}
