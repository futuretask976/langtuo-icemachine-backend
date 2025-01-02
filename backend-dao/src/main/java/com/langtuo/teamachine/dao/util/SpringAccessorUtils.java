package com.langtuo.teamachine.dao.util;

import cn.hutool.extra.spring.SpringUtil;
import com.langtuo.teamachine.dao.accessor.device.AndroidAppAccessor;
import com.langtuo.teamachine.dao.accessor.device.AndroidAppDispatchAccessor;
import com.langtuo.teamachine.dao.accessor.device.MachineAccessor;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.accessor.record.ActRecordAccessor;
import com.langtuo.teamachine.dao.accessor.rule.ConfigRuleAccessor;
import com.langtuo.teamachine.dao.accessor.rule.ConfigRuleDispatchAccessor;
import com.langtuo.teamachine.dao.accessor.user.AdminAccessor;
import com.langtuo.teamachine.dao.accessor.user.TenantAccessor;
import org.springframework.context.ApplicationContext;

public class SpringAccessorUtils {
    public static ConfigRuleDispatchAccessor getConfigRuleDispatchAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ConfigRuleDispatchAccessor accessor = appContext.getBean(ConfigRuleDispatchAccessor.class);
        return accessor;
    }

    public static ActRecordAccessor getActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ActRecordAccessor accessor = appContext.getBean(ActRecordAccessor.class);
        return accessor;
    }

    public static AdminAccessor getAdminAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AdminAccessor accessor = appContext.getBean(AdminAccessor.class);
        return accessor;
    }

    public static AndroidAppAccessor getAndroidAppAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AndroidAppAccessor accessor = appContext.getBean(AndroidAppAccessor.class);
        return accessor;
    }

    public static MachineGroupAccessor getMachineGroupAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MachineGroupAccessor accessor = appContext.getBean(MachineGroupAccessor.class);
        return accessor;
    }

    public static TenantAccessor getTenantAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TenantAccessor accessor = appContext.getBean(TenantAccessor.class);
        return accessor;
    }

    public static ConfigRuleAccessor getConfigRuleAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ConfigRuleAccessor accessor = appContext.getBean(ConfigRuleAccessor.class);
        return accessor;
    }

    public static MachineAccessor getMachineAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MachineAccessor accessor = appContext.getBean(MachineAccessor.class);
        return accessor;
    }

    public static AndroidAppDispatchAccessor getAndroidAppDispatchAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AndroidAppDispatchAccessor accessor = appContext.getBean(AndroidAppDispatchAccessor.class);
        return accessor;
    }
}
