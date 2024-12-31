package com.langtuo.teamachine.dao.accessor.menu;

import com.langtuo.teamachine.dao.mapper.menu.MenuDispatchMapper;
import com.langtuo.teamachine.dao.po.menu.MenuDispatchPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MenuDispatchAccessor {
    @Resource
    private MenuDispatchMapper mapper;

    public List<MenuDispatchPO> listByMenuCode(String tenantCode, String menuCode, List<String> shopGroupCodeList) {
        List<MenuDispatchPO> list = mapper.selectList(tenantCode, menuCode, shopGroupCodeList);
        return list;
    }

    public List<MenuDispatchPO> listByShopGroupCode(String tenantCode, String shopGroupCode) {
        List<MenuDispatchPO> list = mapper.selectListByShopGroupCode(tenantCode, shopGroupCode);
        return list;
    }

    public int insertBatch(List<MenuDispatchPO> poList) {
        int inserted = mapper.insertBatch(poList);
        return inserted;
    }

    public int update(MenuDispatchPO po) {
        int updated = mapper.update(po);
        return updated;
    }

    public int deleteByMenuCode(String tenantCode, String menuCode, List<String> shopGroupCodeList) {
        int deleted = mapper.delete(tenantCode, menuCode, shopGroupCodeList);
        return deleted;
    }
}
