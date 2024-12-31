package com.langtuo.teamachine.web.controller.record;

import com.langtuo.teamachine.api.model.PageDTO;
import com.langtuo.teamachine.api.model.record.OrderActRecordDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.record.OrderActRecordMgtService;
import com.langtuo.teamachine.mqtt.consume.MqttConsumer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiaqing
 */
@RestController
@RequestMapping("/recordset/order")
public class OrderActRecordController {
    @Resource
    private OrderActRecordMgtService service;

    @Resource
    private MqttConsumer mqttConsumer;
    
    @GetMapping(value = "/get")
    public TeaMachineResult<OrderActRecordDTO> get(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("shopGroupCode") String shopGroupCode,
            @RequestParam("idempotentMark") String idempotentMark) {
        TeaMachineResult<OrderActRecordDTO> rtn = service.get(tenantCode, shopGroupCode, idempotentMark);
        return rtn;
    }
    
    @GetMapping(value = "/search")
    public TeaMachineResult<PageDTO<OrderActRecordDTO>> search(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("shopGroupCode") String shopGroupCode,
            @RequestParam(name = "shopCode", required = false) String shopCode,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        TeaMachineResult<PageDTO<OrderActRecordDTO>> rtn = service.search(tenantCode, shopGroupCode, shopCode,
                pageNum, pageSize);
        return rtn;
    }

    @DeleteMapping(value = "/delete")
    public TeaMachineResult<Void> delete(@RequestParam("tenantCode") String tenantCode,
            @RequestParam("shopGroupCode") String shopGroupCode,
            @RequestParam("idempotentMark") String idempotentMark) {
        TeaMachineResult<Void> rtn = service.delete(tenantCode, shopGroupCode, idempotentMark);
        return rtn;
    }

    /**
     * url: http://localhost:8080/teamachinebackend/recordset/order/test
     * @return
     */
    @GetMapping(value = "/test")
    public TeaMachineResult<Void> test() {
        String str = "{\n" +
                "  \"bizCode\": \"orderActRecord\",\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"idempotentMark\": \"20241107042144_20240904000004_orderAct_0\",\n" +
                "      \"machineCode\": \"20240904000004\",\n" +
                "      \"orderGmtCreated\": 1730967704211,\n" +
                "      \"outerOrderId\": \"20240904000004-1730967704211\",\n" +
                "      \"shopCode\": \"shop_333\",\n" +
                "      \"shopGroupCode\": \"shopGroup_07\",\n" +
                "      \"specItemList\": [\n" +
                "        {\n" +
                "          \"specCode\": \"SPEC_SWEET\",\n" +
                "          \"specItemCode\": \"SPEC_ITEM_7_SWEET\",\n" +
                "          \"specItemName\": \"七分糖\",\n" +
                "          \"specName\": \"糖度\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"specCode\": \"SPEC_TEMP\",\n" +
                "          \"specItemCode\": \"SPEC_ITEM_WARM\",\n" +
                "          \"specItemName\": \"热饮\",\n" +
                "          \"specName\": \"温度\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"state\": 3,\n" +
                "      \"teaCode\": \"TEA_04\",\n" +
                "      \"teaTypeCode\": \"TEA_TYPE_04\",\n" +
                "      \"teaUnitCode\": \"TEA_04-SPEC_ITEM_7_SWEET-SPEC_ITEM_WARM\",\n" +
                "      \"tenantCode\": \"tenant_001\",\n" +
                "      \"toppingList\": [\n" +
                "        {\n" +
                "          \"actualAmount\": 58,\n" +
                "          \"stepIndex\": 1,\n" +
                "          \"toppingCode\": \"topping_002\",\n" +
                "          \"toppingName\": \"脱脂牛奶\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"actualAmount\": 67,\n" +
                "          \"stepIndex\": 2,\n" +
                "          \"toppingCode\": \"topping_005\",\n" +
                "          \"toppingName\": \"绿茶汤\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        mqttConsumer.dispatch(str);
        return TeaMachineResult.success();
    }
}
