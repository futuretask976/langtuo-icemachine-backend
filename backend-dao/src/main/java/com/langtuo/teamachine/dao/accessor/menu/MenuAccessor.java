package com.langtuo.teamachine.dao.accessor.menu;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.menu.MenuMapper;
import com.langtuo.teamachine.dao.po.menu.MenuPO;
import com.langtuo.teamachine.dao.query.menu.MenuQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MenuAccessor {
    @Resource
    private MenuMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public MenuPO getByMenuCode(String tenantCode, String menuCode) {
        // 首先访问缓存
        MenuPO cached = getCache(tenantCode, menuCode);
        if (cached != null) {
            return cached;
        }

        MenuPO po = mapper.selectOne(tenantCode, menuCode);

        // 设置缓存
        setCache(tenantCode, menuCode, po);
        return po;
    }

    public List<MenuPO> list(String tenantCode) {
        // 首先访问缓存
        List<MenuPO> cachedList = getCacheList(tenantCode);
        if (cachedList != null) {
            return cachedList;
        }
        
        List<MenuPO> list = mapper.selectList(tenantCode, null);
        
        // 设置缓存
        setCacheList(tenantCode, list);
        return list;
    }

    public List<MenuPO> listByMenuCode(String tenantCode, List<String> menuCodeList) {
        // 这里只是在每台机器初始化的时候会调用，所以先不加缓存
        List<MenuPO> list = mapper.selectList(tenantCode, menuCodeList);
        return list;
    }

    public PageInfo<MenuPO> search(String tenantCode, String menuCode, String menuName,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        MenuQuery query = new MenuQuery();
        query.setTenantCode(tenantCode);
        query.setMenuName(StringUtils.isBlank(menuName) ? null : menuName);
        query.setMenuCode(StringUtils.isBlank(menuCode) ? null : menuCode);
        List<MenuPO> list = mapper.search(query);

        PageInfo<MenuPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(MenuPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getMenuCode());
            deleteCacheList(po.getTenantCode());
        }
        return inserted;
    }

    public int update(MenuPO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getMenuCode());
            deleteCacheList(po.getTenantCode());
        }
        return updated;
    }

    public int deleteByMenuCode(String tenantCode, String menuCode) {
        MenuPO po = getByMenuCode(tenantCode, menuCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, menuCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getMenuCode());
            deleteCacheList(tenantCode);
        }
        return deleted;
    }

    private String getCacheKey(String tenantCode, String menuCode) {
        return "menuAcc-" + tenantCode + "-" + menuCode;
    }

    private String getCacheListKey(String tenantCode) {
        return "menuAcc-" + tenantCode;
    }

    private MenuPO getCache(String tenantCode, String menuCode) {
        String key = getCacheKey(tenantCode, menuCode);
        Object cached = redisManager4Accessor.getValue(key);
        MenuPO po = (MenuPO) cached;
        return po;
    }

    private List<MenuPO> getCacheList(String tenantCode) {
        String key = getCacheListKey(tenantCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<MenuPO> poList = (List<MenuPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, List<MenuPO> poList) {
        String key = getCacheListKey(tenantCode);
        redisManager4Accessor.setValue(key, poList);
    }

    private void setCache(String tenantCode, String menuCode, MenuPO po) {
        String key = getCacheKey(tenantCode, menuCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String menuCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, menuCode));
    }

    private void deleteCacheList(String tenantCode) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode));
    }
}
