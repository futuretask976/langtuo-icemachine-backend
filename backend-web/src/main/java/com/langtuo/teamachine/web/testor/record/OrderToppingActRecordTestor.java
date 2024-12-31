package com.langtuo.teamachine.web.testor.record;

import com.google.common.collect.Lists;
import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.record.InvalidActRecordMapper;
import com.langtuo.teamachine.dao.mapper.record.OrderToppingActRecordMapper;
import com.langtuo.teamachine.mqtt.request.record.OrderActRecordPutRequest;
import com.langtuo.teamachine.mqtt.request.record.OrderSpecItemActRecordPutRequest;
import com.langtuo.teamachine.mqtt.request.record.OrderToppingActRecordPutRequest;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrderToppingActRecordTestor {
    public static void main(String args[]) {
        insert();
    }

    public static void insert() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        OrderToppingActRecordMapper mapper = sqlSession.getMapper(OrderToppingActRecordMapper.class);

        OrderActRecordPutRequest request = new OrderActRecordPutRequest();
        request.setTenantCode("tenant_001");
        request.setExtraInfo(new HashMap(){{
            put("abc", "def");
        }});
        request.setIdempotentMark(String.valueOf(System.currentTimeMillis()));
        request.setMachineCode("abcd");
        request.setShopCode("shop_001");
        request.setShopGroupCode("shopGroup_02");
        request.setOrderGmtCreated(new Date());
        request.setOuterOrderId("111111");
        request.setState(0);

        List<OrderSpecItemActRecordPutRequest> specItemList = Lists.newArrayList();
        OrderSpecItemActRecordPutRequest specItemReq1 = new OrderSpecItemActRecordPutRequest();
        specItemReq1.setSpecCode("SPEC_SWEET");
        specItemReq1.setSpecItemCode("SPEC_ITEM_7_SWEET");
        specItemList.add(specItemReq1);
        OrderSpecItemActRecordPutRequest specItemReq2 = new OrderSpecItemActRecordPutRequest();
        specItemReq2.setSpecCode("SPEC_BEIXING");
        specItemReq2.setSpecItemCode("SPEC_ITEM_BIG");
        specItemList.add(specItemReq2);
        request.setSpecItemList(specItemList);

        List<OrderToppingActRecordPutRequest> toppingList = Lists.newArrayList();
        OrderToppingActRecordPutRequest toppingReq1 = new OrderToppingActRecordPutRequest();
        toppingReq1.setStepIndex(1);
        toppingReq1.setToppingCode("topping_002");
        toppingReq1.setActualAmount(20);
        toppingList.add(toppingReq1);
        OrderToppingActRecordPutRequest toppingReq2 = new OrderToppingActRecordPutRequest();
        toppingReq2.setStepIndex(1);
        toppingReq2.setToppingCode("topping_003");
        toppingReq2.setActualAmount(30);
        toppingList.add(toppingReq2);
        request.setToppingList(toppingList);

        List<OrderToppingActRecordPO> poList = convertToOrderToppingActRecordPO(request);
        for (OrderToppingActRecordPO po : poList) {
            mapper.insert(po);
        }

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

    private static List<OrderToppingActRecordPO> convertToOrderToppingActRecordPO(OrderActRecordPutRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getSpecItemList())) {
            return null;
        }

        List<OrderToppingActRecordPO> specItemList = request.getToppingList().stream()
                .map(orderToppingActRecordPutRequest -> {
                    OrderToppingActRecordPO po = new OrderToppingActRecordPO();
                    po.setTenantCode(request.getTenantCode());
                    po.setIdempotentMark("1723700246767");
                    po.setStepIndex(orderToppingActRecordPutRequest.getStepIndex());
                    po.setToppingCode(orderToppingActRecordPutRequest.getToppingCode());
                    po.setActualAmount(orderToppingActRecordPutRequest.getActualAmount());
                    return po;
                }).collect(Collectors.toList());
        return specItemList;
    }
}
