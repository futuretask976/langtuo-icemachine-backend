package com.langtuo.teamachine.api.request.device;

import com.langtuo.teamachine.api.utils.RegexUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
public class AndroidAppPutRequest {
    /**
     * 额外信息，格式：a:b;c:d
     */
    private Map<String, String> extraInfo;

    /**
     * 版本号
     */
    private String version;

    /**
     * OSS 路径
     */
    private String ossPath;

    /**
     * 备注信息
     */
    private String comment;

    /**
     * 是否新建
     */
    private boolean putNew;

    /**
     * 参数校验
     * @return
     */
    public boolean isValid() {
        if (!RegexUtils.isValidVersion(version, true)) {
            return false;
        }
        if (StringUtils.isBlank(ossPath)) {
            return false;
        }
        if (!RegexUtils.isValidComment(comment, false)) {
            return false;
        }
        return true;
    }
}
