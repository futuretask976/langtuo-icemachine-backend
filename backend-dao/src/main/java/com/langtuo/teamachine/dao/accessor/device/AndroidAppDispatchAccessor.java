package com.langtuo.teamachine.dao.accessor.device;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.device.AndroidAppDispatchMapper;
import com.langtuo.teamachine.dao.po.device.AndroidAppDispatchPO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AndroidAppDispatchAccessor {
    @Resource
    private AndroidAppDispatchMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<AndroidAppDispatchPO> listByVersion(String tenantCode, String version) {
        // 首先访问缓存
        List<AndroidAppDispatchPO> cachedList = getCacheList(tenantCode, version);
        if (cachedList != null) {
            return cachedList;
        }

        List<AndroidAppDispatchPO> list = mapper.selectListByVersion(tenantCode, version);

        // 设置缓存
        setCacheList(tenantCode, version, list);
        return list;
    }

    public int insert(AndroidAppDispatchPO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheList(po.getTenantCode(), po.getVersion());
        }
        return inserted;
    }

    public int update(AndroidAppDispatchPO po) {
        int updated = mapper.update(po);
        if (updated == CommonConsts.DB_UPDATED_ONE_ROW) {
            deleteCacheList(po.getTenantCode(), po.getVersion());
        }
        return updated;
    }

    public int deleteByVersion(String tenantCode, String version) {
        int deleted = mapper.delete(tenantCode, version);
        if (deleted >= CommonConsts.DB_DELETED_ZERO_ROW) {
            deleteCacheList(tenantCode, version);
        }
        return deleted;
    }

    private String getCacheListKey(String tenantCode, String version) {
        return "menuDispatchAcc-" + tenantCode + "-" + version;
    }

    private List<AndroidAppDispatchPO> getCacheList(String tenantCode, String version) {
        String key = getCacheListKey(tenantCode, version);
        Object cached = redisManager4Accessor.getValue(key);
        List<AndroidAppDispatchPO> poList = (List<AndroidAppDispatchPO>) cached;
        return poList;
    }

    private void setCacheList(String tenantCode, String version, List<AndroidAppDispatchPO> poList) {
        String key = getCacheListKey(tenantCode, version);
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheList(String tenantCode, String version) {
        redisManager4Accessor.deleteKey(getCacheListKey(tenantCode, version));
    }
}
