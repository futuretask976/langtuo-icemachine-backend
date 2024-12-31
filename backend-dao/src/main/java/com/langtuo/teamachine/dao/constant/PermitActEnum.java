package com.langtuo.teamachine.dao.constant;

public enum PermitActEnum {
    // 设备
    MODEL_MGT("modelMgt", "型号管理", "deviceSet"),
    DEPLOY_MGT("deployMgt", "预部署管理", "deviceSet"),
    MACHINE_MGT("machineMgt", "设备管理", "deviceSet"),

    // 用户
    // TENANT_MGT("tenantMgt", "商户管理", "userSet"),
    ORG_MGT("orgMgt", "组织管理", "userSet"),
    ROLE_MGT("roleMgt", "角色管理", "userSet"),
    // PERMIT_ACT_MGT("permitActMgt", "权限管理", "userSet"),
    ADMIN_MGT("adminMgt", "管理员管理", "userSet"),

    // 店铺
    SHOP_GROUP_MGT("shopGroupMgt", "店铺组管理", "shopSet"),
    SHOP_MGT("shopMgt", "店铺管理", "shopSet"),

    // 饮品生产
    TOPPING_TYPE_MGT("toppingTypeMgt", "物料管理", "drinkSet"),
    TOPPING_MGT("toppingMgt", "物料管理", "drinkSet"),
    SPEC_MGT("specMgt", "规格管理", "drinkSet"),
    TEA_TYPE_MGT("teaTypeMgt", "茶品类型管理", "drinkSet"),
    TEA_MGT("teaMgt", "茶品管理", "drinkSet"),
    ACCURACY_TPL_MGT("accuracyTplMgt", "物料精度模板管理", "drinkSet"),

    // 菜单
    SERIES_MGT("seriesMgt", "系列管理", "menuSet"),
    MENU_MGT("menuMgt", "菜单管理", "menuSet"),

    // 食安规则
    DRAIN_RULE_RULE_MGT("drainRuleMgt", "排空管理", "ruleSet"),
    CLEAN_RULE_MGT("cleanRuleMgt", "清洗规则管理", "ruleSet"),
    WARNING_RULE_MGT("warningRuleMgt", "预警规则管理", "ruleSet"),

    // 记录
    INVALID_REC_MGT("invalidRecMgt", "废料记录管理", "recordSet"),
    SUPPLY_REC_MGT("supplyRecMgt", "补料记录管理", "recordSet"),
    DRAIN_REC_MGT("drainRecMgt", "排空记录管理", "recordSet"),
    CLEAN_REC_MGT("cleanRecMgt", "清洗记录管理", "recordSet"),
    ORDER_REC_MGT("orderRecMgt", "订单记录管理", "recordSet"),

    // 报表
    ORDER_REPORT_MGT("orderReportMgt", "订单报表管理", "reportSet"),
    ;

    /**
     *
     */
    private String permitActCode;

    /**
     *
     */
    private String permitActName;

    /**
     *
     */
    private String permitActGroupCode;

    public String getPermitActCode() {
        return permitActCode;
    }

    public String getPermitActName() {
        return permitActName;
    }

    public String getPermitActGroupCode() {
        return permitActGroupCode;
    }

    PermitActEnum(String permitActCode, String permitActName, String permitActGroupCode) {
        this.permitActCode = permitActCode;
        this.permitActName = permitActName;
        this.permitActGroupCode = permitActGroupCode;
    }

    public PermitActEnum valueOfByCode(String inputCode) {
        for (PermitActEnum permitActEnum : PermitActEnum.values()) {
            if (inputCode.equals(permitActEnum.getPermitActGroupCode())) {
                return permitActEnum;
            }
        }
        return null;
    }

    public PermitActEnum valueOfByName(String inputName) {
        for (PermitActEnum permitActEnum : PermitActEnum.values()) {
            if (inputName.equals(permitActEnum.getPermitActCode())) {
                return permitActEnum;
            }
        }
        return null;
    }

    public PermitActEnum valueOfByGroupName(String inputGroupCode) {
        for (PermitActEnum permitActEnum : PermitActEnum.values()) {
            if (inputGroupCode.equals(permitActEnum.getPermitActGroupCode())) {
                return permitActEnum;
            }
        }
        return null;
    }
}
