package com.langtuo.teamachine.dao.accessor.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.shop.ShopMapper;
import com.langtuo.teamachine.dao.po.shop.ShopPO;
import com.langtuo.teamachine.dao.query.shop.ShopQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ShopAccessor {
    @Resource
    private ShopMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public ShopPO getByShopCode(String tenantCode, String shopCode) {
        // 首先访问缓存
        ShopPO cached = getCache(tenantCode, shopCode);
        if (cached != null) {
            return cached;
        }

        ShopPO po = mapper.selectOne(tenantCode, shopCode);

        // 设置缓存
        getCache(tenantCode, shopCode, po);
        return po;
    }

    public List<ShopPO> listByShopGroupCodeList(String tenantCode, List<String> shopGroupCodeList) {
        List<ShopPO> list = mapper.selectListByShopGroupCodeList(tenantCode, shopGroupCodeList);
        return list;
    }

    public PageInfo<ShopPO> search(String tenantCode, String shopName, List<String> shopGroupCodeList,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ShopQuery shopQuery = new ShopQuery();
        shopQuery.setTenantCode(tenantCode);
        shopQuery.setShopName(StringUtils.isBlank(shopName) ? null : shopName);
        shopQuery.setShopGroupCodeList(shopGroupCodeList);
        List<ShopPO> list = mapper.search(shopQuery);

        PageInfo<ShopPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(ShopPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheCount(po.getTenantCode(), po.getShopGroupCode());
        }
        return inserted;
    }

    public int update(ShopPO po) {
        ShopPO exist = mapper.selectOne(po.getTenantCode(), po.getShopCode());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getShopCode());
            deleteCacheCount(exist.getTenantCode(), exist.getShopGroupCode());
            deleteCacheCount(po.getTenantCode(), po.getShopGroupCode());
        }
        return updated;
    }

    public int delete(String tenantCode, String shopCode) {
        ShopPO po = getByShopCode(tenantCode, shopCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, shopCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getShopCode());
            deleteCacheCount(po.getTenantCode(), po.getShopGroupCode());
        }
        return deleted;
    }

    public int countByShopGroupCode(String tenantCode, String shopGroupCode) {
        int count = mapper.countByShopGroupCode(tenantCode, shopGroupCode);
        return count;
    }

    private String getCacheKey(String tenantCode, String shopCode) {
        return "shopAcc-" + tenantCode + "-" + shopCode;
    }

    private String getCacheCountKey(String tenantCode, String shopGroupCode) {
        return "shopAcc-cnt-" + tenantCode + "-" + shopGroupCode;
    }

    private Integer getCacheCount(String tenantCode, String shopGroupCode) {
        String key = getCacheCountKey(tenantCode, shopGroupCode);
        Object cached = redisManager4Accessor.getValue(key);
        Integer count = (Integer) cached;
        return count;
    }

    private void setCacheCount(String tenantCode, String shopGroupCode, Integer count) {
        String key = getCacheCountKey(tenantCode, shopGroupCode);
        redisManager4Accessor.setValue(key, count);
    }

    private ShopPO getCache(String tenantCode, String shopCode) {
        String key = getCacheKey(tenantCode, shopCode);
        Object cached = redisManager4Accessor.getValue(key);
        ShopPO po = (ShopPO) cached;
        return po;
    }

    private void getCache(String tenantCode, String shopCode, ShopPO po) {
        String key = getCacheKey(tenantCode, shopCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String shopCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, shopCode));
    }

    private void deleteCacheCount(String tenantCode, String shopGroupCode) {
        redisManager4Accessor.deleteKey(getCacheCountKey(tenantCode, shopGroupCode));
    }
}
