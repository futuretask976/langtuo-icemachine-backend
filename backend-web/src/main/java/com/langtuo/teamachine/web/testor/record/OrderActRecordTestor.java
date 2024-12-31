package com.langtuo.teamachine.web.testor.record;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.langtuo.teamachine.biz.util.BizUtils;
import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.record.InvalidActRecordMapper;
import com.langtuo.teamachine.dao.mapper.record.OrderActRecordMapper;
import com.langtuo.teamachine.dao.mapper.record.OrderSpecItemActRecordMapper;
import com.langtuo.teamachine.dao.mapper.record.OrderToppingActRecordMapper;
import com.langtuo.teamachine.dao.po.record.InvalidActRecordPO;
import com.langtuo.teamachine.dao.po.record.OrderActRecordPO;
import com.langtuo.teamachine.dao.po.record.OrderSpecItemActRecordPO;
import com.langtuo.teamachine.dao.po.record.OrderToppingActRecordPO;
import com.langtuo.teamachine.mqtt.consume.worker.record.OrderActRecordWorker;
import com.langtuo.teamachine.mqtt.request.record.OrderActRecordPutRequest;
import com.langtuo.teamachine.mqtt.request.record.OrderSpecItemActRecordPutRequest;
import com.langtuo.teamachine.mqtt.request.record.OrderToppingActRecordPutRequest;
import org.apache.ibatis.session.SqlSession;

import java.util.Calendar;
import java.util.List;

public class OrderActRecordTestor {
    public static void main(String args[]) {
        insert();
//        select();
    }

    public static void insert() {
//        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
//        OrderActRecordMapper orderActRecordMapper = sqlSession.getMapper(OrderActRecordMapper.class);
//        OrderSpecItemActRecordMapper orderSpecItemActRecordMapper = sqlSession.getMapper(OrderSpecItemActRecordMapper.class);
//        OrderToppingActRecordMapper orderToppingActRecordMapper = sqlSession.getMapper(OrderToppingActRecordMapper.class);


        for (int i = 0; i < 1; i++) {
            OrderActRecordPutRequest request = new OrderActRecordPutRequest();
            request.setTenantCode("tenant_001");
            request.setIdempotentMark(String.valueOf(System.currentTimeMillis()));
            request.setMachineCode("machine_444");
            request.setShopCode("shop_002");
            request.setShopGroupCode("shopGroup_03");

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -4);
            request.setOrderGmtCreated(calendar.getTime());

            request.setTeaTypeCode("TEA_TYPE_01");
            request.setTeaCode("TEA_07");
            request.setOuterOrderId(String.valueOf(System.currentTimeMillis()));
            request.setState(2);

            List<OrderSpecItemActRecordPutRequest> specItemList = Lists.newArrayList();
            specItemList.add(getSpecItemRequest1());
            specItemList.add(getSpecItemRequest2());
            specItemList.add(getSpecItemRequest3());
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

            System.out.println(JSONObject.toJSON(request));

//            OrderActRecordPO orderActRecordPO = OrderActRecordWorker.convertToOrderActRecordPO(request);
//            orderActRecordMapper.insert(orderActRecordPO);
//
//            List<OrderSpecItemActRecordPO> orderSpecItemActRecordPOList =
//                    OrderActRecordWorker.convertToSpecItemActRecordPO(request);
//            for (OrderSpecItemActRecordPO orderSpecItemActRecordPO : orderSpecItemActRecordPOList) {
//                orderSpecItemActRecordMapper.insert(orderSpecItemActRecordPO);
//            }
//
//            List<OrderToppingActRecordPO> orderToppingActRecordPOList =
//                    OrderActRecordWorker.convertToOrderToppingActRecordPO(request);
//            for (OrderToppingActRecordPO orderToppingActRecordPO : orderToppingActRecordPOList) {
//                orderToppingActRecordMapper.insert(orderToppingActRecordPO);
//            }
        }

//        sqlSession.commit();
//        sqlSession.close();
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

    private static OrderSpecItemActRecordPutRequest getSpecItemRequest1() {
        List<OrderSpecItemActRecordPutRequest> list = Lists.newArrayList();
        OrderSpecItemActRecordPutRequest specItemReq;

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_SWEET");
        specItemReq.setSpecItemCode("SPEC_ITEM_7_SWEET");
        list.add(specItemReq);

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_SWEET");
        specItemReq.setSpecItemCode("SPEC_ITEM_5_SWEET");
        list.add(specItemReq);

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_SWEET");
        specItemReq.setSpecItemCode("SPEC_ITEM_5_SWEET");
        list.add(specItemReq);

        int idx = BizUtils.calcRandom(1, list.size());
        return list.get(idx - 1);
    }

    private static OrderSpecItemActRecordPutRequest getSpecItemRequest2() {
        List<OrderSpecItemActRecordPutRequest> list = Lists.newArrayList();
        OrderSpecItemActRecordPutRequest specItemReq;

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_BEIXING");
        specItemReq.setSpecItemCode("SPEC_ITEM_BIG");
        list.add(specItemReq);

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_BEIXING");
        specItemReq.setSpecItemCode("SPEC_ITEM_MIDDLE");
        list.add(specItemReq);

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_BEIXING");
        specItemReq.setSpecItemCode("SPEC_ITEM_SMALL");
        list.add(specItemReq);

        int idx = BizUtils.calcRandom(1, list.size());
        return list.get(idx - 1);
    }

    private static OrderSpecItemActRecordPutRequest getSpecItemRequest3() {
        List<OrderSpecItemActRecordPutRequest> list = Lists.newArrayList();
        OrderSpecItemActRecordPutRequest specItemReq;

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_TEMP");
        specItemReq.setSpecItemCode("SPEC_ITEM_WARM");
        list.add(specItemReq);

        specItemReq = new OrderSpecItemActRecordPutRequest();
        specItemReq.setSpecCode("SPEC_TEMP");
        specItemReq.setSpecItemCode("SPEC_ITEM_FREEZE");
        list.add(specItemReq);

        int idx = BizUtils.calcRandom(1, list.size());
        return list.get(idx - 1);
    }
}
