package com.langtuo.teamachine.api.model.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class CleanRuleDTO implements Serializable {
    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 清洁规则编码
     */
    private String cleanRuleCode;

    /**
     * 清洁规则名称
     */
    private String cleanRuleName;

    /**
     * 是否允许提醒，0：不允许，1：允许
     */
    private Integer permitRemind;

    /**
     * 是否允许批量，0：不允许，1：允许
     */
    private Integer permitBatch;

    /**
     * 排除物料编码列表
     */
    private List<String> exceptToppingCodeList;

    /**
     * 清洁步骤列表
     */
    private List<CleanRuleStepDTO> cleanRuleStepList;
}
