package com.langtuo.teamachine.mqtt.consume.worker.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.dao.accessor.record.ActRecordAccessor;
import com.langtuo.teamachine.dao.util.SpringAccessorUtils;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import com.langtuo.teamachine.internal.constant.CommonConsts;
import com.langtuo.teamachine.mqtt.request.record.CleanActRecordPutRequest;
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
public class CleanActRecordWorker implements Runnable {
    /**
     * 转换后的请求列表
     */
    private List<CleanActRecordPutRequest> requestList = Lists.newArrayList();

    /**
     * 事务管理器
     */
    private TransactionTemplate transactionTemplate;

    public CleanActRecordWorker(JSONObject jsonPayload) {
        JSONArray jsonList = jsonPayload.getJSONArray(AliyunConsts.MQTT_RECEIVE_KEY_LIST);
        jsonList.forEach(jsonObject -> {
            CleanActRecordPutRequest request = TypeUtils.castToJavaBean(jsonObject, CleanActRecordPutRequest.class);
            requestList.add(request);
        });
        transactionTemplate = SpringTemplateUtils.getTransactionTemplate();
    }

    @Override
    public void run() {
        if (CollectionUtils.isEmpty(requestList)) {
            log.error("checkParam|illegalArgument|requestListEmpty");
        }

        for (CleanActRecordPutRequest request : requestList) {
            put(request);
        }
    }

    private void put(CleanActRecordPutRequest request) {if (request == null || !request.isValid()) {
            log.error("checkParam|illegalArgument|"
                    + (request == null ? null : JSON.toJSONString(request)));
            return;
        }

        CleanActRecordPO po = convert(request);
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    ActRecordAccessor actRecordAccessor = SpringAccessorUtils.getActRecordAccessor();
                    CleanActRecordPO exist = actRecordAccessor.getByIdempotentMark(po.getTenantCode(),
                            po.getIdempotentMark());
                    if (exist == null) {
                        int inserted = actRecordAccessor.insert(po);
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

    private CleanActRecordPO convert(CleanActRecordPutRequest request) {
        if (request == null) {
            return null;
        }

        CleanActRecordPO po = new CleanActRecordPO();
        po.setTenantCode(request.getTenantCode());
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
        po.setWashSec(request.getWashSec());
        po.setSoakMin(request.getSoakMin());
        po.setFlushSec(request.getFlushSec());
        po.setFlushIntervalMin(request.getFlushIntervalMin());
        po.setRecycleSec(request.getRecycleSec());
        po.setCleanContent(request.getCleanContent());
        po.setCleanAgentType(request.getCleanAgentType());
        po.setStepIndex(request.getStepIndex());
        return po;
    }
}
