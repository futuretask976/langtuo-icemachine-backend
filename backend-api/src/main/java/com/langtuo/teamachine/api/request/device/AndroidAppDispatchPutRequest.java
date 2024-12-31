package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.CollectionUtils;
import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;

import java.util.List;

@Data
public class AndroidAppDispatchPutRequest {
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 版本号
     */
    private String version;

    /**
     * 店铺编码
     */
    private List<String> shopGroupCodeList;

    /**
     *
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidCode(tenantCode, true)) {
            return false;
        }
        if (!RegexUtils.isValidVersion(version, true)) {
            return false;
        }
        if (!isValidShopGroupList()) {
            return false;
        }
        return true;
    }

    private boolean isValidShopGroupList() {
        if (CollectionUtils.isEmpty(shopGroupCodeList)) {
            return false;
        }
        for (String m : shopGroupCodeList) {
            if (!RegexUtils.isValidCode(m, true)) {
                return false;
            }
        }
        return true;
    }
}
