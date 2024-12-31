package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ModelPutRequest {
    /**
     * 型号编码
     */
    private String modelCode;

    /**
     * 是否启用同时出料，0：禁用，1：启用
     */
    private int enableFlowAll;

    /**
     * 管道同时清洗的最大数量
     */
    private int pipeCleanSimultMaxCnt;

    /**
     * 同MachineModelDTO
     */
    private List<ModelPipelinePutRequest> pipelineList;

    /**
     * 同MachineModelDTO
     */
    private Map<String, String> extraInfo;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(modelCode, true)) {
            return false;
        }
        if (!isValidPipelineList()) {
            return false;
        }
        return true;
    }

    private boolean isValidPipelineList() {
        if (CollectionUtils.isEmpty(pipelineList)) {
            return false;
        }
        for (ModelPipelinePutRequest m : pipelineList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
