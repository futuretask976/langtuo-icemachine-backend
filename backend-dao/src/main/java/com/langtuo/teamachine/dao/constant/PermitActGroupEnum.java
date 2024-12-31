package com.langtuo.teamachine.dao.constant;

public enum PermitActGroupEnum {
    USER_SET("userSet", "用户"),
    SHOP_SET("shopSet", "店铺"),
    DEVICE_SET("deviceSet", "设备"),
    DRINK_SET("drinkSet", "饮品生产"),
    MENU_SET("menuSet", "菜单"),
    RULE_SET("ruleSet", "食安规则"),
    RECORD_SET("recordSet", "日常记录"),
    REPORT_SET("reportSet", "日常报表"),
    ;

    /**
     *
     */
    private String permitActGroupCode;

    /**
     *
     */
    private String permitActGroupName;

    public String getPermitActGroupCode() {
        return permitActGroupCode;
    }

    public String getPermitActGroupName() {
        return permitActGroupName;
    }

    PermitActGroupEnum(String permitActGroupCode, String permitActGroupName) {
        this.permitActGroupCode = permitActGroupCode;
        this.permitActGroupName = permitActGroupName;
    }

    public static PermitActGroupEnum valueOfByCode(String inputCode) {
        for (PermitActGroupEnum permitActGroupEnum : PermitActGroupEnum.values()) {
            if (inputCode.equals(permitActGroupEnum.getPermitActGroupCode())) {
                return permitActGroupEnum;
            }
        }
        return null;
    }

    public static PermitActGroupEnum valueOfByName(String inputName) {
        for (PermitActGroupEnum permitActGroupEnum : PermitActGroupEnum.values()) {
            if (inputName.equals(permitActGroupEnum.getPermitActGroupCode())) {
                return permitActGroupEnum;
            }
        }
        return null;
    }
}
