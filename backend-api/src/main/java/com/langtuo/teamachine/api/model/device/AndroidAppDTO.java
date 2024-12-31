package com.langtuo.teamachine.api.model.device;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class AndroidAppDTO implements Serializable {
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
}
