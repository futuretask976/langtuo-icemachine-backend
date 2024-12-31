package com.langtuo.teamachine.biz.manager;

import com.langtuo.teamachine.dao.accessor.menu.MenuDispatchAccessor;
import com.langtuo.teamachine.dao.po.menu.MenuDispatchPO;
import com.langtuo.teamachine.dao.po.menu.MenuPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiaqing
 */
@Component
public class MenuManager {
    @Resource
    private MenuDispatchAccessor menuDispatchAccessor;

    public List<MenuPO> getMenuPOListByShopGroupCode(String tenantCode, String shopGroupCode) {
        List<MenuDispatchPO> menuDispatchPOList = menuDispatchAccessor.listByShopGroupCode(
                tenantCode, shopGroupCode);
        if (CollectionUtils.isEmpty(menuDispatchPOList)) {
            return null;
        }

        List<String> menuCodeList = menuDispatchPOList.stream()
                .map(MenuDispatchPO::getMenuCode)
                .collect(Collectors.toList());
        List<MenuPO> menuPOList = SpringAccessorUtils.getMenuAccessor().listByMenuCode(tenantCode, menuCodeList);
        return menuPOList;
    }
}
