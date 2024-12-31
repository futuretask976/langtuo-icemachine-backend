package com.langtuo.teamachine.api.request.drink;

import com.google.common.collect.Lists;
import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TeaPutRequest {
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
     * 租户编码
     */
    private String tenantCode;

    /**
     * 备注
     */
    private String comment;

    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     *
     */
    private List<ToppingBaseRulePutRequest> toppingBaseRuleList;

    /**
     *
     */
    private List<SpecRulePutRequest> specRuleList;

    /**
     * 茶-物料关系列表
     */
    private List<TeaUnitPutRequest> teaUnitList;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     * 是否导入
     */
    private boolean putImport;

    public void addTeaUnit(TeaUnitPutRequest request) {
        if (request == null) {
            return;
        }
        if (this.teaUnitList == null) {
            this.teaUnitList = Lists.newArrayList();
        }
        this.teaUnitList.add(request);
    }

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidComment(comment, false)) {
            return false;
        }
        if (!RegexUtils.isValidCode(teaCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(teaName, true)) {
            return false;
        }
        if (!RegexUtils.isValidCode(teaTypeCode, true)) {
            return false;
        }
        if (!isValidToppingBaseRuleList()) {
            return false;
        }
        if (!isValidSpecRuleList()) {
            return false;
        }
        if (!isValidTeaUnitList()) {
            return false;
        }
        return true;
    }

    private boolean isValidTeaUnitList() {
        if (CollectionUtils.isEmpty(teaUnitList)) {
            return false;
        }
        for (TeaUnitPutRequest s : teaUnitList) {
            if (!s.isValid()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidSpecRuleList() {
        if (CollectionUtils.isEmpty(specRuleList)) {
            return false;
        }
        for (SpecRulePutRequest s : specRuleList) {
            if (!s.isValid()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidToppingBaseRuleList() {
        if (CollectionUtils.isEmpty(toppingBaseRuleList)) {
            return false;
        }
        for (ToppingBaseRulePutRequest m : toppingBaseRuleList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
