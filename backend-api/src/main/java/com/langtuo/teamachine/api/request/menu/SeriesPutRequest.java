package com.langtuo.teamachine.api.request.menu;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SeriesPutRequest {
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
     * 系列编码
     */
    private String seriesCode;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 系列-茶品关系
     */
    private List<SeriesTeaRelPutRequest> seriesTeaRelList;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidComment(comment, false)) {
            return false;
        }
        if (!RegexUtils.isValidCode(seriesCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidName(seriesName, true)) {
            return false;
        }
        if (!isValidPipelineList()) {
            return false;
        }
        return true;
    }

    private boolean isValidPipelineList() {
        if (CollectionUtils.isEmpty(seriesTeaRelList)) {
            return false;
        }
        for (SeriesTeaRelPutRequest m : seriesTeaRelList) {
            if (!m.isValid()) {
                return false;
            }
        }
        return true;
    }
}
