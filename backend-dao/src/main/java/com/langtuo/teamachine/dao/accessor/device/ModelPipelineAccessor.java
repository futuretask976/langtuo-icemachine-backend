package com.langtuo.teamachine.dao.accessor.device;

import com.langtuo.teamachine.dao.cache.RedisManager4Accessor;
import com.langtuo.teamachine.dao.mapper.device.ModelPipelineMapper;
import com.langtuo.teamachine.dao.po.device.ModelPipelinePO;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ModelPipelineAccessor {
    @Resource
    private ModelPipelineMapper mapper;

    @Resource
    private RedisManager4Accessor redisManager4Accessor;

    public List<ModelPipelinePO> listByModelCode(String modelCode) {
        // 首先访问缓存
        List<ModelPipelinePO> cachedList = getCacheList(modelCode);
        if (cachedList != null) {
            return cachedList;
        }
        
        List<ModelPipelinePO> list = mapper.selectList(modelCode);

        // 设置缓存
        setCacheList(list);
        return list;
    }

    public int insert(ModelPipelinePO po) {
        int inserted = mapper.insert(po);
        if (inserted == CommonConsts.DB_INSERTED_ONE_ROW) {
            deleteCacheOne(po.getModelCode());
            deleteCacheList();
        }
        return inserted;
    }

    public int deleteByModelCode(String modelCode) {
        int deleted = mapper.delete(modelCode);
        if (deleted == CommonConsts.DB_DELETED_ONE_ROW) {
            deleteCacheOne(modelCode);
            deleteCacheList();
        }
        return deleted;
    }

    private String getCacheKey(String modelCode) {
        return "modelPlAcc-" + modelCode;
    }

    private String getCacheListKey() {
        return "modelPlAcc";
    }

    private List<ModelPipelinePO> getCacheList(String modelCode) {
        String key = getCacheKey(modelCode);
        Object cached = redisManager4Accessor.getValue(key);
        List<ModelPipelinePO> poList = (List<ModelPipelinePO>) cached;
        return poList;
    }

    private void setCacheList(List<ModelPipelinePO> poList) {
        String key = getCacheListKey();
        redisManager4Accessor.setValue(key, poList);
    }

    private void deleteCacheOne(String modelCode) {
        redisManager4Accessor.deleteKey(getCacheKey(modelCode));
    }

    private void deleteCacheList() {
        redisManager4Accessor.deleteKey(getCacheListKey());
    }
}
