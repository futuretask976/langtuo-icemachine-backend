package com.langtuo.teamachine.web.testor.record;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.record.SupplyActRecordMapper;
import com.langtuo.teamachine.dao.po.record.SupplyActRecordPO;
import com.langtuo.teamachine.mqtt.request.record.SupplyActRecordPutRequest;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SupplyActRecordTestor {
    public static void main(String args[]) {
        insert();
    }

    public static void insert() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        SupplyActRecordMapper mapper = sqlSession.getMapper(SupplyActRecordMapper.class);

        SupplyActRecordPutRequest request = new SupplyActRecordPutRequest();
        request.setTenantCode("tenant_001");
        request.setExtraInfo(new HashMap<String, String>(){{}});
        request.setIdempotentMark(String.valueOf(System.currentTimeMillis()));
        request.setMachineCode("1234");
        request.setShopGroupCode("shopGroup_02");
        request.setShopCode("shop_001");
        request.setSupplyTime(new Date());
        request.setToppingCode("topping_001");
        request.setPipelineNum(1);
        request.setSupplyAmount(10);
        // mapper.insert(convert(request));

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(request);

        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put("bizCode", "supplyActRecord");
        jsonMsg.put("list", jsonArray);
        System.out.println(jsonMsg.toJSONString());

        sqlSession.commit();
        sqlSession.close();
    }

    public static void select() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        SupplyActRecordMapper mapper = sqlSession.getMapper(SupplyActRecordMapper.class);

        List<SupplyActRecordPO> list = mapper.selectList("tenant_001");
        for (SupplyActRecordPO po : list) {
            System.out.printf("$$$$$ list->po: %s\n", po);
        }

        SupplyActRecordPO po = mapper.selectOne("tenant_001", "1234567890");
        System.out.printf("$$$$$ po: %s\n", po);

        sqlSession.commit();
        sqlSession.close();
    }

    private static SupplyActRecordPO convert(SupplyActRecordPutRequest request) {
        if (request == null) {
            return null;
        }

        SupplyActRecordPO po = new SupplyActRecordPO();
        po.setTenantCode(request.getTenantCode());
        po.setExtraInfo(request.getExtraInfo());
        po.setIdempotentMark(request.getIdempotentMark());
        po.setMachineCode(request.getMachineCode());
        po.setShopCode(request.getShopCode());
        po.setShopGroupCode(request.getShopGroupCode());
        po.setSupplyTime(request.getSupplyTime());
        po.setToppingCode(request.getToppingCode());
        po.setPipelineNum(request.getPipelineNum());
        po.setSupplyAmount(request.getSupplyAmount());
        return po;
    }
}
