//package com.langtuo.teamachine.dao.config;
//
//public class OssConfig {
//    /**
//     *
//     */
//    public static final String OSS_ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com";
//
//    /**
//     *
//     */
//    public static final String OSS_REGION = "oss-cn-hangzhou";
//
//    /**
//     *
//     */
//    public static final String OSS_BUCKET_NAME = "miya-bucket2";
//
//    /**
//     *
//     */
//    public static final String ACCESS_KEY_ID = "LTAI5tNqF1bav1mtzdUSGwkQ";
//
//    /**
//     *
//     */
//    public static final String ACCESS_KEY_SECRET = "njNRe0aL6MNrWXI4TUYYWRolx1uYOq";
//
//    /**
//     * 令牌过期时间 7 天
//     */
//    public static final int OSS_ACCESS_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
//
//    /**
//     *
//     */
//    public static final String OSS_MENU_PATH = "menu";
//
//    /**
//     *
//     */
//    public static final String OSS_PATH_SEPARATOR = "/";
//
//    /**
//     *
//     */
//    public static final String OSS_STS_ENDPOINT = "sts.cn-hangzhou.aliyuncs.com";
//
//    /**
//     * RAM角色的 RamRoleArn
//     */
//    public static final String OSS_STS_ROLE_ARN = "acs:ram::1079138807996471:role/ramosstest";
//
//    /**
//     * 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest
//     */
//    public static final String OSS_STS_SESSION_NAME = "langtuo_sts_sesson";
//
//    /**
//     * 临时访问凭证的有效时间，单位为秒。最小值为900，最大值以当前角色设定的最大会话时间为准
//     * 当前角色最大会话时间取值范围为3600秒~43200秒，默认值为3600秒
//     * 在上传大文件或者其他较耗时的使用场景中，建议合理设置临时访问凭证的有效时间，确保在完成目标任务前无需反复调用STS服务以获取临时访问凭证
//     */
//    public static final long OSS_STS_DURATION_SECONDS = 3600L;
//}
