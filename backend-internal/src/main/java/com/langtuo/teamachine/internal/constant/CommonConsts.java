package com.langtuo.teamachine.internal.constant;

import com.google.common.collect.Lists;

import java.util.List;

public class CommonConsts {
    /**
     * 最小页码
     */
    public static final int MIN_PAGE_NUM = 1;

    /**
     * 最小每页展示数
     */
    public static final int MIN_PAGE_SIZE = 1;

    /**
     * 字符串长度为 0
     */
    public static final int STR_LENGTH_ZERO = 0;

    /**
     * 集合长度为 0
     */
    public static final int COLLECTION_LENGTH_ZERO = 0;

    /**
     * 数量 0
     */
    public static final int AMOUNT_ZERO = 0;

    /**
     * 列表首个元素
     */
    public static final int LIST_INDEX_FIRST = 0;

    /**
     * 数字 1
     */
    public static final int NUM_ONE = 1;

    /**
     * 横杠
     */
    public static final String HORIZONTAL_BAR = "-";

    /**
     * 部署状态字段通用说明
     */
    public static final int DEPLOY_STATE_DISABLED = 0;
    public static final String DEPLOY_STATE_DISABLED_LABEL = "未部署";
    public static final int DEPLOY_STATE_ENABLED = 1;
    public static final String DEPLOY_STATE_ENABLED_LABEL = "已部署";

    /**
     * 状态字段通用说明
     */
    public static final int STATE_DISABLED = 0;
    public static final String STATE_DISABLED_LABEL = "禁用";
    public static final int STATE_ENABLED = 1;
    public static final String STATE_ENABLED_LABEL = "启用";

    /**
     * 茶品调整规则toppingAdjustType字段说明
     */
    public static final int TOPPING_ADJUST_TYPE_REDUCE = 0;
    public static final String TOPPING_ADJUST_TYPE_REDUCE_LABEL = "减少";
    public static final int TOPPING_ADJUST_TYPE_INCRESE = 1;
    public static final String TOPPING_ADJUST_TYPE_INCRESE_LABEL = "添加";

    /**
     * 茶品调整规则toppingAdjustMode字段说明
     */
    public static final int TOPPING_ADJUST_MODE_FIX = 0;
    public static final String TOPPING_ADJUST_MODE_FIX_LABEL = "固定值";
    public static final int TOPPING_ADJUST_MODE_PER = 1;
    public static final String TOPPING_ADJUST_MODE_PER_LABEL = "百分比";

    /**
     * 是否初始化触发，0：不是，1：是
     */
    public static final int MENU_DISPATCH_LIST_FALSE = 0;
    public static final int MENU_DISPATCH_LIST_TRUE = 1;

    /**
     * 菜单分发输出文件路径前缀，和扩展名
     */
    public static final String MENU_OUTPUT_PATH = "dispatch/";
    public static final String MENU_INIT_OUTPUT_PATH = "dispatchInit/";
    public static final String MENU_OUTPUT_FILENAME_PREFIX = "menu-";
    public static final String MENU_OUTPUT_PATH_EXT = ".json";

    /**
     * 茶品导出 - sheet名称
     */
    public static final String SHEET_NAME_4_TEA_EXPORT = "menuExportSheetName";
    /**
     * sheet索引
     */
    public static final int SHEET_NUM_4_DATA = 0;
    /**
     * 标题行索引
     */
    public static final int ROW_NUM_4_TITLE = 0;
    /**
     * 数据行索引
     */
    public static final int ROW_START_NUM_4_DATA = 1;
    /**
     * 茶品导出 - 列标题
     */
    public static final String TITLE_TEA_CODE = "menuExportTitleTeaCode";
    public static final int TITLE_TEA_CODE_INDEX = 0;
    public static final String TITLE_TEA_NAME = "menuExportTitleTeaName";
    public static final int TITLE_TEA_NAME_INDEX = 1;
    public static final String TITLE_STATE = "menuExportTitleState";
    public static final int TITLE_STATE_INDEX = 2;
    public static final String TITLE_TEA_TYPE_CODE = "menuExportTitleTeaTypeCode";
    public static final int TITLE_TEA_TYPE_CODE_INDEX = 3;
    public static final String TITLE_IMG_LINK = "menuExportTitleImgLink";
    public static final int TITLE_IMG_LINK_INDEX = 4;
    public static final String TITLE_TEA_UNIT_NAME = "menuExportTitleTeaUnitName";
    public static final int TITLE_TEA_UNIT_NAME_INDEX = 5;
    public static final String TITLE_STEP_INDEX = "menuExportTitleStepIndex";
    public static final int TITLE_STEP_INDEX_INDEX = 6;
    public static final String TITLE_TOPPING_CODE = "menuExportTitleToppingCode";
    public static final int TITLE_TOPPING_CODE_INDEX = 7;
    public static final String TITLE_TOPPING_NAME = "menuExportTitleToppingName";
    public static final int TITLE_TOPPING_NAME_INDEX = 8;
    public static final String TITLE_ACTUAL_AMOUNT = "menuExportTitleActualAmount";
    public static final int TITLE_ACTUAL_AMOUNT_INDEX = 9;
    /**
     * 茶品导出 - 标题行
     */
    public static List<String> TITLE_LIST_4_TEA_EXPORT = Lists.newArrayList(
            TITLE_TEA_CODE,
            TITLE_TEA_NAME,
            TITLE_STATE,
            TITLE_TEA_TYPE_CODE,
            TITLE_IMG_LINK,
            TITLE_TEA_UNIT_NAME,
            TITLE_STEP_INDEX,
            TITLE_TOPPING_CODE,
            TITLE_TOPPING_NAME,
            TITLE_ACTUAL_AMOUNT);
    /**
     * 茶品 - 起始行num
     */
    public static final int ROW_START_NUM_4_TEA = 1;
    /**
     * 茶品 - 基础信息部分起始列num
     */
    public static final int COL_START_NUM_4_TEA_INFO = 0;
    /**
     * 茶品 - 规格部分起始列num
     */
    public static final int COL_START_NUM_4_TEA_UNIT = 5;
    /**
     * 茶品 - 调整规则部分起始列num
     */
    public static final int COL_START_NUM_4_ADJUST_RULE = 6;

    /**
     * 部署导出 - sheet名称
     */
    public static final String SHEET_NAME_4_DEPLOY_EXPORT = "deployExportSheetName";
    /**
     * 部署导出 - 标题行
     */
    public static List<String> TITLE_LIST_4_DEPLOY_EXPORT = Lists.newArrayList(
            "deployExportTitleGmtCreated",
            "deployExportTitleGmtModified",
            "deployExportTitleTenantCode",
            "deployExportTitleTenantName",
            "deployExportTitleDeployCode",
            "deployExportTitleMachineCode",
            "deployExportTitleModelCode",
            "deployExportTitleShopCode",
            "deployExportTitleShopName",
            "deployExportTitleState");
    /**
     * 部署导出 - 起始行num
     */
    public static final int ROW_START_NUM_4_DEPLOY = 1;
    /**
     * 部署导出 - 起始列num
     */
    public static final int COL_START_NUM_4_DEPLOY = 0;

    /**
     * 部署码生成用 char_set
     */
    public static final String DEPLOY_CHAR_SET = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 部署码长度
     */
    public static final int DEPLOY_CODE_LENGTH = 6;

    /**
     * 部署码补位用数字
     */
    public static final String DEPLOY_CODE_COVERING_NUM = "0";

    /**
     * 日期格式相关常量
     */
    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_FORMAT_SIMPLE = "yyyyMMdd";

    /**
     * 系统超级管理员角色
     */
    public static final String ROLE_CODE_SYS_SUPER = "SYS_SUPER_ROLE";
    public static final String ROLE_NAME_SYS_SUPER = "系统超级管理员";

    /**
     * 租户超级管理员角色
     */
    public static final String ROLE_CODE_TENANT_SUPER = "TENANT_SUPER_ROLE";
    public static final String ROLE_NAME_TENANT_SUPER = "租户超级管理员";

    /**
     * JSON 消息中的 key 关键字
     */
    public static final String JSON_KEY_BIZ_CODE = "bizCode";
    public static final String JSON_KEY_GMT_MODIFIED = "gmtModified";
    // userSet
    public static final String JSON_KEY_TENANT_CODE = "tenantCode";
    public static final String JSON_KEY_LOGIN_NAME = "loginName";
    // deviceSet
    public static final String JSON_KEY_MODEL_CODE = "modelCode";
    public static final String JSON_KEY_MACHINE_CODE = "machineCode";
    public static final String JSON_KEY_MODEL = "model";
    public static final String JSON_KEY_MACHINE = "machine";
    public static final String JSON_KEY_VERSION = "version";
    // shopSet
    public static final String JSON_KEY_SHOP_GROUP_CODE = "shopGroupCode";
    public static final String JSON_KEY_SHOP_CODE = "shopCode";
    // drinkSet
    public static final String JSON_KEY_TEMPLATE_CODE = "templateCode";
    public static final String JSON_KEY_ACCURACY_TPL = "accuracyTpl";
    // menuSet
    public static final String JSON_KEY_MENU_CODE = "menuCode";
    public static final String JSON_KEY_MENU_GMTMODIFIED_YMDHMS = "menuGmtModifiedYMDHMS";
    public static final String JSON_KEY_MD5_AS_HEX = "md5AsHex";
    public static final String JSON_KEY_OSS_PATH = "ossPath";
    // ruleSet
    public static final String JSON_KEY_DRAIN_RULE_CODE = "drainRuleCode";
    public static final String JSON_KEY_CLEAN_RULE_CODE = "cleanRuleCode";
    public static final String JSON_KEY_WARNING_RULE_CODE = "warningRuleCode";
    public static final String JSON_KEY_DRAIN_RULE = "drainRule";
    public static final String JSON_KEY_CLEAN_RULE = "cleanRule";
    public static final String JSON_KEY_WARNING_RULE = "warningRule";


    /**
     * console 用的消息 bizCode
     */
    public static final String BIZ_CODE_MODEL_UPDATED = "modelUpdated";
    public static final String BIZ_CODE_MACHINE_UPDATED = "machineUpdated";
    public static final String BIZ_CODE_ANDROID_APP_DISPATCHED = "androidAppDispatched";
    public static final String BIZ_CODE_TENANT_UPDATED = "tenantUpdated";
    public static final String BIZ_CODE_ACCURACY_TPL_UPDATED = "accuracyTplUpdated";
    public static final String BIZ_CODE_ACCURACY_TPL_DELETED = "accuracyTplDeleted";
    public static final String BIZ_CODE_MENU_DISPATCH_REQUESTED = "menuDispatchRequested";
    public static final String BIZ_CODE_MENU_LIST_REQUESTED = "menuListRequested";
    public static final String BIZ_CODE_DRAIN_RULE_DISPATCH_REQUESTED = "drainRuleDispatchRequested";
    public static final String BIZ_CODE_DRAIN_RULE_DISPATCH_NEW_REQUESTED = "drainRuleDispatchNewRequested";
    public static final String BIZ_CODE_CLEAN_RULE_DISPATCH_REQUESTED = "cleanRuleDispatchRequested";
    public static final String BIZ_CODE_WARNING_RULE_DISPATCH_REQUESTED = "warningRuleDispatchRequested";

    /**
     * dispatch 用的消息 bizCode
     */
    public static final String BIZ_CODE_DISPATCH_ACCURACY = "dispatchAccuracy";
    public static final String BIZ_CODE_DELETE_ACCURACY = "deleteAccuracy";
    public static final String BIZ_CODE_DISPATCH_CLEAN_RULE = "dispatchCleanRule";
    public static final String BIZ_CODE_DISPATCH_MACHINE = "dispatchMachine";
    public static final String BIZ_CODE_DISPATCH_MENU = "dispatchMenu";
    public static final String BIZ_CODE_DISPATCH_MENU_LIST = "dispatchMenuList";
    public static final String BIZ_CODE_DISPATCH_MODEL = "dispatchModel";
    public static final String BIZ_CODE_DISPATCH_ANDROID_APP = "dispatchAndroidApp";
    public static final String BIZ_CODE_DISPATCH_DRAIN_RULE = "dispatchDrainRule";
    public static final String BIZ_CODE_DISPATCH_DRAIN_RULE_NEW = "dispatchDrainRuleNew";
    public static final String BIZ_CODE_DISPATCH_WARNING_RULE = "dispatchWarningRule";


    /**
     * 数据库访问相关常量
     */
    public static final int DB_COUNT_RESULT_ZERO = 0;
    public static final int DB_INSERTED_ZERO_ROW = 0;
    public static final int DB_INSERTED_ONE_ROW = 1;
    public static final int DB_UPDATED_ZERO_ROW = 0;
    public static final int DB_UPDATED_ONE_ROW = 1;
    public static final int DB_DELETED_ZERO_ROW = 0;
    public static final int DB_DELETED_ONE_ROW = 1;
    public static final int DB_SELECT_ZERO_ROW = 0;


    /**
     * 系统超级管理员登录名
     */
    public static final String ADMIN_SYS_SUPER_LOGIN_NAME = "SYS_SUPER_ADMIN";

    /**
     * 系统超级管理员登录密码
     * 经过md5加密后的密码，原始密码是SYS_SUPER_ADMIN
     */
    public static final String ADMIN_SYS_SUPER_PASSWORD = "5505b50f5f0ec77b27a0ea270b21e7f0";

    /**
     *  系统保留角色
     */
    public static final int ROLE_SYS_RESERVED = 1;

    /**
     * 组织架构中最高层次的名称
     */
    public static final String ORG_NAME_TOP = "总公司";

    /**
     * 租户管理的权限点，单独配置，不会在列表中出现
     */
    public static final String PERMIT_ACT_CODE_TENANT = "tenantMgt";
    public static final String PERMIT_ACT_CODE_ANDROID_APP_MGT = "androidAppMgt";

    /**
     * Map 中的 key 常量
     */
    public static final String MAP_KEY_PO_LIST = "poList";
}
