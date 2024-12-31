package com.langtuo.teamachine.api.model.drink;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class TeaDTO implements Serializable {
    /**
     * 数据表记录插入时间
     */
    private Date gmtCreated;

    /**
     * 数据表记录最近修改时间
     */
    private Date gmtModified;

    /**
     * 备注
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 茶编码
     */
    private String teaCode;

    /**
     * 茶名称
     */
    private String teaName;

    /**
     * 状态，0：禁用，1：启用
     */
    private int state;

    /**
     * 茶类型编码
     */
    private String teaTypeCode;

    /**
     * 茶品图片链接
     */
    private String imgLink;

    /**
     *
     */
    private List<ToppingBaseRuleDTO> toppingBaseRuleList;

    /**
     *
     */
    private List<SpecRuleDTO> specRuleList;

    /**
     * 茶-物料关系列表
     */
    private List<TeaUnitDTO> teaUnitList;
}
