package com.langtuo.teamachine.web.testor.record;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.record.DrainActRecordMapper;
import com.langtuo.teamachine.dao.mapper.record.InvalidActRecordMapper;
import com.langtuo.teamachine.dao.po.record.DrainActRecordPO;
import com.langtuo.teamachine.dao.po.record.InvalidActRecordPO;
import com.langtuo.teamachine.mqtt.request.record.DrainActRecordPutRequest;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class DrainActRecordTestor {
    public static void main(String args[]) {
        insert();
    }

    public static void insert() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        DrainActRecordMapper mapper = sqlSession.getMapper(DrainActRecordMapper.class);

        DrainActRecordPutRequest request = new DrainActRecordPutRequest();
        request.setTenantCode("tenant_001");
        request.setIdempotentMark(String.valueOf(System.currentTimeMillis()));
        request.setMachineCode("abcd");
        request.setShopCode("shop_001");
        request.setShopGroupCode("shopGroup_02");
        request.setDrainStartTime(new Date());
        request.setDrainEndTime(new Date());
        request.setToppingCode("topping_001");
        request.setPipelineNum(1);
        request.setDrainType(1);
        request.setDrainRuleCode("123");
        request.setFlushSec(30);
        // mapper.insert(convert(request));

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(request);

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put("bizCode", "drainActRecord");
        jsonMsg.put("list", jsonArray);
        System.out.println(jsonMsg.toJSONString());

        sqlSession.commit();
        sqlSession.close();
    }

    public static void select() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        InvalidActRecordMapper mapper = sqlSession.getMapper(InvalidActRecordMapper.class);

        List<InvalidActRecordPO> list = mapper.selectList("tenant_001");
        for (InvalidActRecordPO po : list) {
            System.out.printf("$$$$$ list->po: %s\n", po);
        }

        InvalidActRecordPO po = mapper.selectOne("tenant_001", "1234567890");
        System.out.printf("$$$$$ po: %s\n", po);

        sqlSession.commit();
        sqlSession.close();
    }

    private static DrainActRecordPO convert(DrainActRecordPutRequest request) {
        if (request == null) {
            return null;
        }

        DrainActRecordPO po = new DrainActRecordPO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setIdempotentMark(request.getIdempotentMark());
        po.setMachineCode(request.getMachineCode());
        po.setShopCode(request.getShopCode());
        po.setShopGroupCode(request.getShopGroupCode());
        po.setDrainStartTime(request.getDrainStartTime());
        po.setDrainEndTime(request.getDrainEndTime());
        po.setToppingCode(request.getToppingCode());
        po.setPipelineNum(request.getPipelineNum());
        po.setDrainType(request.getDrainType());
        po.setDrainRuleCode(request.getDrainRuleCode());
        po.setFlushSec(request.getFlushSec());
        return po;
    }
}
