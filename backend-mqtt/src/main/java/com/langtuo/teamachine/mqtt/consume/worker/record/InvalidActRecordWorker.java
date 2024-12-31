package com.langtuo.teamachine.mqtt.consume.worker.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.dao.accessor.record.InvalidActRecordAccessor;
import com.langtuo.teamachine.dao.po.record.InvalidActRecordPO;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.request.record.InvalidActRecordPutRequest;
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
public class InvalidActRecordWorker implements Runnable {
    /**
     * 转换后的请求列表
     */
    private List<InvalidActRecordPutRequest> requestList = Lists.newArrayList();

    /**
     * 事务管理器
     */
    private TransactionTemplate transactionTemplate;

    public InvalidActRecordWorker(JSONObject jsonPayload) {
        JSONArray jsonList = jsonPayload.getJSONArray(AliyunConsts.MQTT_RECEIVE_KEY_LIST);
        jsonList.forEach(jsonObject -> {
            InvalidActRecordPutRequest request = TypeUtils.castToJavaBean(jsonObject, InvalidActRecordPutRequest.class);
            requestList.add(request);
        });
        transactionTemplate = SpringTemplateUtils.getTransactionTemplate();
    }

    @Override
    public void run() {
        if (CollectionUtils.isEmpty(requestList)) {
            log.error("checkParam|illegalArgument|requestListEmpty");
        }

        for (InvalidActRecordPutRequest request : requestList) {
            put(request);
        }
    }

    public void put(InvalidActRecordPutRequest request) {
        if (request == null || !request.isValid()) {
            log.error("checkParam|illegalArgument|request="
                    + (request == null ? null : JSON.toJSONString(request)));
            return;
        }

        InvalidActRecordPO po = convert(request);
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    InvalidActRecordAccessor invalidActRecordAccessor = SpringAccessorUtils.getInvalidActRecordAccessor();
                    InvalidActRecordPO exist = invalidActRecordAccessor.getByIdempotentMark(po.getTenantCode(), po.getIdempotentMark());
                    if (exist == null) {
                        int inserted = invalidActRecordAccessor.insert(po);
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

    private InvalidActRecordPO convert(InvalidActRecordPutRequest request) {
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
