package com.langtuo.teamachine.internal.constant;

public class AliyunConsts {
    /**
     * 账号 accesskey，从账号系统控制台获取
     * 阿里云账号AccessKey拥有所有API的访问权限，建议您使用RAM用户进行API访问或日常运维。
     * 强烈建议不要把AccessKey ID和AccessKey Secret保存到工程代码里，否则可能导致AccessKey泄露，威胁您账号下所有资源的安全。
     * 本示例以把AccessKey ID和AccessKey Secret保存在环境变量为例说明。运行本代码示例之前，请先配置环境变量MQTT_AK_ENV和MQTT_SK_ENV
     * 例如：export MQTT_AK_ENV=<access_key_id>
     *      export MQTT_SK_ENV=<access_key_secret>
     * 需要将<access_key_id>替换为已准备好的AccessKey ID，<access_key_secret>替换为AccessKey Secret。
     */
    public static final String RAM_ACCESS_KEY = System.getenv("RAM_ACCESS_KEY");

    /**
     * 账号 secretKey，从账号系统控制台获取，仅在Signature鉴权模式下需要设置
     */
    public static final String RAM_ACCESS_KEY_SECRET = System.getenv("RAM_ACCESS_SECRET");

    /**
     * MQ4IOT 实例 ID，购买后控制台获取
     */
    public static final String MQTT_INSTANCE_ID = "post-cn-kvw3w72ds01";
    /**
     * 接入点地址，购买 MQ4IOT 实例，且配置完成后即可获取，接入点地址必须填写分配的域名，不得使用 IP 地址直接连接，否则可能会导致客户端异常。
     */
    public static final String MQTT_ENDPOINT = "post-cn-kvw3w72ds01.mqtt.aliyuncs.com";

    /**
     * MQTT 管控接口 ENDPOINT
     */
    public static final String MQTT_MANAGER_ENDPOINT = "onsmqtt.cn-beijing.aliyuncs.com";

    /**
     * topic 相关常量
     */
    public static final String MQTT_GROUP_ID = "GID_TEAMACHINE";
    public static final String MQTT_CONSOLE_PARENT_TOPIC = "teamachine";
    public static final String MQTT_TENANT_PARENT_TOPIC_POSTFIX = "-teamachine";
    public static final String MQTT_TENANT_PARENT_P2P_TOPIC_POSTFIX = "-teamachine/p2p/" + MQTT_GROUP_ID + "@@@";

    /**
     * topic 分隔符
     */
    public static final String MQTT_TOPIC_SEPERATOR = "/";

    /**
     * 收到的消息中的 key 关键字
     */
    public static final String MQTT_RECEIVE_KEY_BIZ_CODE = "bizCode";
    public static final String MQTT_RECEIVE_KEY_LIST = "list";

    /**
     * 接受设备端消息用的 bizCode
     */
    public static final String MQTT_BIZ_CODE_INVALID_ACT_RECORD = "invalidActRecord";
    public static final String MQTT_BIZ_CODE_SUPPLY_ACT_RECORD = "supplyActRecord";
    public static final String MQTT_BIZ_CODE_DRAIN_ACT_RECORD = "drainActRecord";
    public static final String MQTT_BIZ_CODE_CLEAN_ACT_RECORD = "cleanActRecord";
    public static final String MQTT_BIZ_CODE_ORDER_ACT_RECORD = "orderActRecord";

    /**
     *
     */
    public static final String OSS_ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com";

    /**
     *
     */
    public static final String OSS_REGION = "oss-cn-hangzhou";

    /**
     *
     */
    public static final String OSS_BUCKET_NAME = "miya-bucket2";

    /**
     * 令牌过期时间 7 天
     */
    public static final int OSS_ACCESS_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    /**
     *
     */
    public static final String OSS_MENU_PATH = "menu";

    /**
     *
     */
    public static final String OSS_PATH_SEPARATOR = "/";

    /**
     *
     */
    public static final String OSS_STS_ENDPOINT = "sts.cn-hangzhou.aliyuncs.com";

    /**
     * RAM角色的 RamRoleArn
     */
    public static final String OSS_STS_ROLE_ARN = "acs:ram::1079138807996471:role/ramosstest";

    /**
     * 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest
     */
    public static final String OSS_STS_SESSION_NAME = "langtuo_sts_sesson";

    /**
     * 临时访问凭证的有效时间，单位为秒。最小值为900，最大值以当前角色设定的最大会话时间为准
     * 当前角色最大会话时间取值范围为3600秒~43200秒，默认值为3600秒
     * 在上传大文件或者其他较耗时的使用场景中，建议合理设置临时访问凭证的有效时间，确保在完成目标任务前无需反复调用STS服务以获取临时访问凭证
     */
    public static final long OSS_STS_DURATION_SECONDS = 3600L;
}
