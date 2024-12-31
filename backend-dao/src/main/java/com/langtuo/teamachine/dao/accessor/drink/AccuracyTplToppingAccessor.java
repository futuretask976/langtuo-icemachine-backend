package com.langtuo.teamachine.dao.accessor.drink;

import com.langtuo.teamachine.dao.mapper.drink.AccuracyTplToppingMapper;
import com.langtuo.teamachine.dao.po.drink.AccuracyTplToppingPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AccuracyTplToppingAccessor {
    @Resource
    private AccuracyTplToppingMapper mapper;

    public List<AccuracyTplToppingPO> listByTplCode(String tenantCode, String templateCode) {
        List<AccuracyTplToppingPO> list = mapper.selectList(tenantCode, templateCode);
        return list;
    }

    public int insertBatch(List<AccuracyTplToppingPO> poList) {
        int inserted = mapper.insertBatch(poList);
        return inserted;
    }

    public int deleteByTplCode(String tenantCode, String templateCode) {
        int deleted = mapper.delete(tenantCode, templateCode);
        return deleted;
    }
}
