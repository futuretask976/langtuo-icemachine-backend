package com.langtuo.teamachine.web.testor.record;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.record.CleanActRecordMapper;
import com.langtuo.teamachine.dao.mapper.record.InvalidActRecordMapper;
import com.langtuo.teamachine.dao.po.record.CleanActRecordPO;
import com.langtuo.teamachine.dao.po.record.InvalidActRecordPO;
import com.langtuo.teamachine.mqtt.request.record.CleanActRecordPutRequest;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class CleanActRecordTestor {
    public static void main(String args[]) {
        insert();
    }

    public static void insert() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        CleanActRecordMapper mapper = sqlSession.getMapper(CleanActRecordMapper.class);

        CleanActRecordPutRequest request = new CleanActRecordPutRequest();
        request.setTenantCode("tenant_001");
        request.setIdempotentMark(String.valueOf(System.currentTimeMillis()));
        request.setMachineCode("abcd");
        request.setShopCode("shop_001");
        request.setShopGroupCode("shopGroup_02");
        request.setCleanStartTime(new Date());
        request.setCleanEndTime(new Date());
        request.setToppingCode("topping_001");
        request.setPipelineNum(1);
        request.setCleanType(1);
        request.setCleanRuleCode("123");
        request.setCleanContent(1);
        request.setWashSec(10);
        request.setSoakMin(20);
        request.setFlushSec(30);
        request.setFlushIntervalMin(40);
        mapper.insert(convert(request));

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(request);

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put("bizCode", "cleanActRecord");
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

    private static CleanActRecordPO convert(CleanActRecordPutRequest request) {
        if (request == null) {
            return null;
        }

        CleanActRecordPO po = new CleanActRecordPO();
        po.setExtraInfo(request.getExtraInfo());
        po.setIdempotentMark(request.getIdempotentMark());
        po.setMachineCode(request.getMachineCode());
        po.setShopCode(request.getShopCode());
        po.setShopGroupCode(request.getShopGroupCode());
        po.setCleanStartTime(request.getCleanStartTime());
        po.setCleanEndTime(request.getCleanEndTime());
        po.setToppingCode(request.getToppingCode());
        po.setPipelineNum(request.getPipelineNum());
        po.setCleanType(request.getCleanType());
        po.setCleanRuleCode(request.getCleanRuleCode());
        po.setCleanContent(request.getCleanContent());
        po.setWashSec(request.getWashSec());
        po.setSoakMin(request.getSoakMin());
        po.setFlushSec(request.getFlushSec());
        po.setFlushIntervalMin(request.getFlushIntervalMin());
        return po;
    }
}
