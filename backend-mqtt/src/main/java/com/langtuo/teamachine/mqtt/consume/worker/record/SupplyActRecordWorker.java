package com.langtuo.teamachine.mqtt.consume.worker.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.dao.accessor.record.SupplyActRecordAccessor;
import com.langtuo.teamachine.dao.po.record.SupplyActRecordPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.request.record.SupplyActRecordPutRequest;
import com.langtuo.teamachine.mqtt.util.SpringTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author Jiaqing
 */
@Slf4j
public class SupplyActRecordWorker implements Runnable {
    /**
     * 转换后的请求列表
     */
    private List<SupplyActRecordPutRequest> requestList = Lists.newArrayList();

    /**
     * 事务管理器
     */
    private TransactionTemplate transactionTemplate;

    public SupplyActRecordWorker(JSONObject jsonPayload) {
        JSONArray jsonList = jsonPayload.getJSONArray(AliyunConsts.MQTT_RECEIVE_KEY_LIST);
        jsonList.forEach(jsonObject -> {
            SupplyActRecordPutRequest request = TypeUtils.castToJavaBean(jsonObject, SupplyActRecordPutRequest.class);
            requestList.add(request);
        });
        transactionTemplate = SpringTemplateUtils.getTransactionTemplate();
    }

    public void run() {
        if (CollectionUtils.isEmpty(requestList)) {
            log.error("checkParam|illegalArgument|requestListEmpty");
        }

        for (SupplyActRecordPutRequest request : requestList) {
            put(request);
        }
    }

    public void put(SupplyActRecordPutRequest request) {
        if (request == null || !request.isValid()) {
            log.error("checkParam|illegalArgument|request="
                    + (request == null ? null : JSON.toJSONString(request)));
            return;
        }

        SupplyActRecordPO po = convert(request);
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    SupplyActRecordAccessor supplyActRecordAccessor = SpringAccessorUtils.getSupplyActRecordAccessor();
                    SupplyActRecordPO exist = supplyActRecordAccessor.getByIdempotentMark(po.getTenantCode(), po.getIdempotentMark());
                    if (exist == null) {
                        int inserted = supplyActRecordAccessor.insert(po);
                        if (CommonConsts.DB_INSERTED_ONE_ROW != inserted) {
                            log.error("insertActRecord|error|inserted=" + inserted + ";po=" + JSON.toJSONString(po));
                        }
                    }
                } catch (Exception e) {
                    log.error("insertActRecord|fatal|e=" + e.getMessage(), e);
                    status.setRollbackOnly();
                }
                return null;
            }
        });
    }

    private SupplyActRecordPO convert(SupplyActRecordPutRequest request) {
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
