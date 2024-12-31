package com.langtuo.teamachine.web.constant;

public class WebConsts {
    /**
     * 导出文件使用，指定disposition的name
     */
    public static final String HTTP_HEADER_DISPOSITION_NAME = "attachment";

    /**
     * 导出文件使用，指定disposition的fileName
     */
    public static final String HTTP_HEADER_DISPOSITION_FILE_NAME_4_DEPLOY_EXPORT = "deploy_export.xlsx";

    /**
     * 导出文件使用，指定disposition的fileName
     */
    public static final String HTTP_HEADER_DISPOSITION_FILE_NAME_4_TEA_EXPORT = "tea_export.xlsx";

    /**
     * HTTP Request 头部字段
     */
    public static final String REQ_HEADER_MACHINE_CODE = "Machine-Code";
    public static final String REQ_HEADER_TENANT_CODE = "Tenant-Code";
    public static final String REQ_HEADER_DEPLOY_CODE = "Deploy-Code";

    /**
     * HTTP Response 头部字段
     */
    public static final String RESP_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String RESP_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String RESP_HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String RESP_HEADER_VAL_ALL = "*";
    public static final String RESP_HEADER_VAL_TRUE = "true";
    public static final String RESP_HEADER_VAL_NO_CACHE = "no-cache";
    public static final String RESP_HEADER_VAL_ENCODING_UTF8 = "UTF-8";
    public static final String RESP_HEADER_VAL_CONT_TYPE_APPLICATIONJSON = "application/json";

    /**
     * Cookie 相关字段
     */
    public static final String COOKIE_NAME_JSESSION_ID = "JSESSIONID";
    public static final int COOKIE_MAX_AGE = 0;
    public static final String COOKIE_PATH = "/";

    /**
     * Session 相关字段
     */
    public static final String SESSION_ATTR_USER = "user";
    public static final String SESSION_ATTR_USERNAME = "username";
    public static final String SESSION_ATTR_AUTHORITIES = "authorities";

    /**
     * JSON 字段相关
     */
    public static final String JSON_KEY_LOGIN_SUCCESS = "loginSuccess";
    public static final String JSON_KEY_ACCESS_SUCCESS = "accessSuccess";
    public static final String JSON_VAL_TRUE = "true";
    public static final String JSON_VAL_FALSE = "false";

    /**
     * 授权处理路径
     */
    public static final String ANT_PATTERN_LOGIN_PATH = "/login";
    public static final String ANT_PATTERN_LOGOUT_PATH = "/logout";
    public static final String ANT_PATTERN_LOGIN_PROCESSING_PATH = "/login-processing";
    public static final String ANT_PATTERN_SUMMARY_CHART_PATH = "/summarychart/**";
    public static final String ANT_PATTERN_OSS_PATH = "/securityset/oss/**";
    public static final String ANT_PATTERN_MQTT_PATH = "/securityset/mqtt/**";
    // 设备
    public static final String ANT_PATTERN_MODEL_PATH = "/deviceset/model/**";
    public static final String ANT_PATTERN_DEPLOY_PATH = "/deviceset/deploy/**";
    public static final String ANT_PATTERN_MACHINE_PATH = "/deviceset/machine/**";
    public static final String ANT_PATTERN_ANDROID_PATH = "/deviceset/android/**";
    // 用户
    public static final String ANT_PATTERN_ROLE_LIST_PATH = "/userset/role/list/**";
    public static final String ANT_PATTERN_PERMITACT_LIST_PATH = "/userset/permitact/list/**";
    public static final String ANT_PATTERN_ORG_LIST_PATH = "/userset/org/list/**";
    public static final String ANT_PATTERN_TENANT_PATH = "/userset/tenant/**";
    public static final String ANT_PATTERN_ORG_PATH = "/userset/org/**";
    // public static final String ANT_PATTERN_PERMITACT_PATH = "/userset/permitact/**";
    public static final String ANT_PATTERN_ROLE_PATH = "/userset/role/**";
    public static final String ANT_PATTERN_ADMIN_PATH = "/userset/admin/**";
    // 店铺
    public static final String ANT_PATTERN_SHOP_GROUP_LIST_PATH = "/shopset/group/list/**";
    public static final String ANT_PATTERN_SHOP_GROUP_PATH = "/shopset/group/**";
    public static final String ANT_PATTERN_SHOP_PATH = "/shopset/shop/**";
    // 饮品生产
    public static final String ANT_PATTERN_TOPPING_TYPE_LIST_PATH = "/drinkset/topping/type/list/**";
    public static final String ANT_PATTERN_TEA_TYPE_LIST_PATH = "/drinkset/tea/type/list/**";
    public static final String ANT_PATTERN_TEA_LIST_PATH = "/drinkset/tea/list/**";
    public static final String ANT_PATTERN_TOPPING_TYPE_PATH = "/drinkset/topping/type/**";
    public static final String ANT_PATTERN_TOPPING_PATH = "/drinkset/topping/**";
    public static final String ANT_PATTERN_SEPC_PATH = "/drinkset/spec/**";
    public static final String ANT_PATTERN_TEA_TYPE_PATH = "/drinkset/tea/type/**";
    public static final String ANT_PATTERN_TEA_PATH = "/drinkset/tea/**";
    public static final String ANT_PATTERN_ACCURACY_PATH = "/drinkset/accuracy/**";
    // 菜单
    public static final String ANT_PATTERN_SERIES_LIST_PATH = "/menuset/series/list/**";
    public static final String ANT_PATTERN_SERIES_PATH = "/menuset/series/**";
    public static final String ANT_PATTERN_MENU_PATH = "/menuset/menu/**";
    // 食安规则
    public static final String ANT_PATTERN_DRAIN_PATH = "/ruleset/drain/**";
    public static final String ANT_PATTERN_CLEAN_PATH = "/ruleset/clean/**";
    public static final String ANT_PATTERN_WARNING_PATH = "/ruleset/warning/**";
    // 日常记录
    public static final String ANT_PATTERN_DRAIN_ACT_PATH = "/recordset/drain/**";
    public static final String ANT_PATTERN_CLEAN_ACT_PATH = "/recordset/clean/**";
    public static final String ANT_PATTERN_INVALID_ACT_PATH = "/recordset/invalid/**";
    public static final String ANT_PATTERN_SUPPLY_ACT_PATH = "/recordset/supply/**";
    public static final String ANT_PATTERN_ORDER_ACT_PATH = "/recordset/order/**";
    // 报表记录
    public static final String ANT_PATTERN_ORDER_REPORT_PATH = "/reportset/order/**";

    /**
     * 特殊角色-权限点
     */
    public static final String ROLE_MATCH_PREFIX = "ROLE_";
    public static final String SPECIAL_PERMIT_ACT_CODE_MACHINE = "machine";
    public static final String SPECIAL_PERMIT_ACT_CODE_TENANT_MGT = "tenantMgt";
    public static final String SPECIAL_PERMIT_ACT_CODE_ANDROID_APP_MGT = "androidAppMgt";

    /**
     * Cookie 常量
     */
    public static final String COOKIE_KEY_JSESSIONID = "JSESSIONID";

    /**
     * 请求参数常量
     */
    public static final String PARAM_KEY_USERNAME = "username";
    public static final String PARAM_KEY_PASSWORD = "password";
}
