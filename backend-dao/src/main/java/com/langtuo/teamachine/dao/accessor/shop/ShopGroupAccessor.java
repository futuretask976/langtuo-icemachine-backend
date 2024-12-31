package com.langtuo.teamachine.dao.accessor.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.shop.MachineGroupMapper;
import com.langtuo.teamachine.dao.po.device.MachineGroupPO;
import com.langtuo.teamachine.dao.query.shop.ShopGroupQuery;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ShopGroupAccessor {
    @Resource
    private MachineGroupMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public MachineGroupPO getByShopGroupCode(String tenantCode, String shopGroupCode) {
        // 首先访问缓存
        MachineGroupPO cached = getCache(tenantCode, shopGroupCode);
        if (cached != null) {
            return cached;
        }

        MachineGroupPO po = mapper.selectOne(tenantCode, shopGroupCode);

        // 设置缓存
        setCache(tenantCode, shopGroupCode, po);
        return po;
    }

    public List<MachineGroupPO> list(String tenantCode, List<String> orgNameList) {
        List<MachineGroupPO> list = mapper.selectListByOrgNameList(tenantCode, orgNameList);
        return list;
    }

    public PageInfo<MachineGroupPO> search(String tenantCode, String shopGroupName, int pageNum, int pageSize,
                                           List<String> orgNameList) {
        PageHelper.startPage(pageNum, pageSize);

        ShopGroupQuery shopGroupQuery = new ShopGroupQuery();
        shopGroupQuery.setTenantCode(tenantCode);
        shopGroupQuery.setShopGroupName(StringUtils.isBlank(shopGroupName) ? null : shopGroupName);
        shopGroupQuery.setOrgNameList(orgNameList);
        List<MachineGroupPO> list = mapper.search(shopGroupQuery);

        PageInfo<MachineGroupPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(MachineGroupPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getMachineGroupCode());
            deleteCacheCountByOrgName(po.getTenantCode(), po.getOrgName());
        }
        return inserted;
    }

    public int update(MachineGroupPO po) {
        MachineGroupPO exist = mapper.selectOne(po.getTenantCode(), po.getMachineGroupCode());
        if (exist == null) {
            return CommonConsts.DB_UPDATED_ZERO_ROW;
        }

        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheOne(po.getTenantCode(), po.getMachineGroupCode());
            deleteCacheCountByOrgName(exist.getTenantCode(), exist.getOrgName());
            deleteCacheCountByOrgName(po.getTenantCode(), po.getOrgName());
        }
        return updated;
    }

    public int deleteByShopGroupCode(String tenantCode, String shopGroupCode) {
        MachineGroupPO po = getByShopGroupCode(tenantCode, shopGroupCode);
        if (po == null) {
            return CommonConsts.DB_DELETED_ZERO_ROW;
        }

        int deleted = mapper.delete(tenantCode, shopGroupCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(tenantCode, po.getMachineGroupCode());
            deleteCacheCountByOrgName(tenantCode, po.getOrgName());
        }
        return deleted;
    }

    public int countByOrgName(String tenantCode, String orgName) {
        int count = mapper.countByOrgName(tenantCode, orgName);
        return count;
    }

    private Integer getCacheCountByOrgName(String tenantCode, String orgName) {
        String key = getCacheCountKeyByOrgName(tenantCode, orgName);
        Object cached = redisManager4Accessor.getValue(key);
        Integer count = (Integer) cached;
        return count;
    }

    private void setCacheCountByOrgName(String tenantCode, String orgName, Integer count) {
        String key = getCacheCountKeyByOrgName(tenantCode, orgName);
        redisManager4Accessor.setValue(key, count);
    }

    private String getCacheCountKeyByOrgName(String tenantCode, String orgName) {
        return "shopGroupAcc-cnt-orgName-" + tenantCode + "-" + orgName;
    }

    private String getCacheKey(String tenantCode, String shopGroupCode) {
        return "shopGroupAcc-" + tenantCode + "-" + shopGroupCode;
    }

    private MachineGroupPO getCache(String tenantCode, String shopGroupCode) {
        String key = getCacheKey(tenantCode, shopGroupCode);
        Object cached = redisManager4Accessor.getValue(key);
        MachineGroupPO po = (MachineGroupPO) cached;
        return po;
    }

    private void setCache(String tenantCode, String shopGroupCode, MachineGroupPO po) {
        String key = getCacheKey(tenantCode, shopGroupCode);
        redisManager4Accessor.setValue(key, po);
    }

    private void deleteCacheOne(String tenantCode, String shopGroupCode) {
        redisManager4Accessor.deleteKey(getCacheKey(tenantCode, shopGroupCode));
    }


    private void deleteCacheCountByOrgName(String tenantCode, String orgName) {
        redisManager4Accessor.deleteKey(getCacheCountKeyByOrgName(tenantCode, orgName));
    }
}
