package com.langtuo.teamachine.web.testor.record;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.record.InvalidActRecordMapper;
import com.langtuo.teamachine.dao.po.record.InvalidActRecordPO;
import com.langtuo.teamachine.mqtt.request.record.InvalidActRecordPutRequest;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InvalidActRecordTestor {
    public static void main(String args[]) {
        insert();
    }

    public static void insert() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        InvalidActRecordMapper mapper = sqlSession.getMapper(InvalidActRecordMapper.class);

        InvalidActRecordPutRequest request = new InvalidActRecordPutRequest();
        request.setTenantCode("tenant_001");
        request.setExtraInfo(new HashMap<String, String>(){{}});
        request.setIdempotentMark(String.valueOf(System.currentTimeMillis()));
        request.setMachineCode("1234");
        request.setShopGroupCode("shopGroup_02");
        request.setShopCode("shop_001");
        request.setInvalidTime(new Date());
        request.setToppingCode("topping_001");
        request.setPipelineNum(1);
        request.setInvalidAmount(10);
        // mapper.insert(convert(request));

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(request);

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put("bizCode", "invalidActRecord");
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

    private static InvalidActRecordPO convert(InvalidActRecordPutRequest request) {
        if (request == null) {
            return null;
        }

        InvalidActRecordPO po = new InvalidActRecordPO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setIdempotentMark(request.getIdempotentMark());
        po.setMachineCode(request.getMachineCode());
        po.setShopCode(request.getShopCode());
        po.setShopGroupCode(request.getShopGroupCode());
        po.setInvalidTime(request.getInvalidTime());
        po.setToppingCode(request.getToppingCode());
        po.setPipelineNum(request.getPipelineNum());
        po.setInvalidAmount(request.getInvalidAmount());
        return po;
    }
}
