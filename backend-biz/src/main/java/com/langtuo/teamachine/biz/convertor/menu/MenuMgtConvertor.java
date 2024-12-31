package com.langtuo.teamachine.biz.convertor.menu;

import com.langtuo.teamachine.api.model.menu.MenuDTO;
import com.langtuo.teamachine.api.model.menu.MenuSeriesRelDTO;
import com.langtuo.teamachine.api.request.menu.MenuDispatchPutRequest;
import com.langtuo.teamachine.api.request.menu.MenuPutRequest;
import com.langtuo.teamachine.dao.accessor.menu.MenuSeriesRelAccessor;
import com.langtuo.teamachine.dao.po.menu.MenuDispatchPO;
import com.langtuo.teamachine.dao.po.menu.MenuPO;
import com.langtuo.teamachine.dao.po.menu.MenuSeriesRelPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MenuMgtConvertor {
    public static List<MenuDTO> convertToMenuDTO(List<MenuPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<MenuDTO> list = poList.stream()
                .map(po -> convertToMenuDTO(po))
                .collect(Collectors.toList());
        return list;
    }

    public static MenuDTO convertToMenuDTO(MenuPO po) {
        if (po == null) {
            return null;
        }

        MenuDTO dto = new MenuDTO();
        dto.setGmtCreated(po.getGmtCreated());
        dto.setGmtModified(po.getGmtModified());
        dto.setComment(po.getComment());
        dto.setExtraInfo(po.getExtraInfo());
        dto.setMenuCode(po.getMenuCode());
        dto.setMenuName(po.getMenuName());
        dto.setValidFrom(po.getValidFrom());

        MenuSeriesRelAccessor menuSeriesRelAccessor = SpringAccessorUtils.getMenuSeriesRelAccessor();
        List<MenuSeriesRelPO> seriesTeaRelPOList = menuSeriesRelAccessor.listBySeriesCode(
                po.getTenantCode(), po.getMenuCode());
        dto.setMenuSeriesRelList(convertToMenuDispatchPO(seriesTeaRelPOList));
        return dto;
    }

    public static MenuPO convertMenuPO(MenuPutRequest request) {
        if (request == null) {
            return null;
        }

        MenuPO po = new MenuPO();
        po.setTenantCode(request.getTenantCode());
        po.setComment(request.getComment());
        po.setExtraInfo(request.getExtraInfo());
        po.setMenuCode(request.getMenuCode());
        po.setMenuName(request.getMenuName());
        po.setValidFrom(request.getValidFrom());
        return po;
    }

    public static List<MenuSeriesRelDTO> convertToMenuDispatchPO(List<MenuSeriesRelPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        return poList.stream().map(po -> {
            MenuSeriesRelDTO dto = new MenuSeriesRelDTO();
            dto.setSeriesCode(po.getSeriesCode());
            dto.setMenuCode(po.getMenuCode());
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<MenuSeriesRelPO> convertToMenuSeriesRelPO(MenuPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getMenuSeriesRelList())) {
            return null;
        }

        return request.getMenuSeriesRelList().stream()
                .map(menuSeriesRelPutRequest -> {
                    MenuSeriesRelPO po = new MenuSeriesRelPO();
                    po.setTenantCode(request.getTenantCode());
                    po.setSeriesCode(menuSeriesRelPutRequest.getSeriesCode());
                    po.setMenuCode(menuSeriesRelPutRequest.getMenuCode());
                    return po;
                }).collect(Collectors.toList());
    }

    public static List<MenuDispatchPO> convertToMenuDispatchPO(MenuDispatchPutRequest request) {
        if (request == null) {
            return null;
        }

        String tenantCode = request.getTenantCode();
        String menuCode = request.getMenuCode();

        return request.getShopGroupCodeList().stream()
                .map(shopGroupCode -> {
                    MenuDispatchPO po = new MenuDispatchPO();
                    po.setTenantCode(tenantCode);
                    po.setMenuCode(menuCode);
                    po.setShopGroupCode(shopGroupCode);
                    return po;
                }).collect(Collectors.toList());
    }
}
