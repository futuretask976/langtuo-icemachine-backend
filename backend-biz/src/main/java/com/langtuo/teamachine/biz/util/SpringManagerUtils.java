package com.langtuo.teamachine.biz.util;

import cn.hutool.extra.spring.SpringUtil;
import com.langtuo.teamachine.biz.manager.*;
import org.springframework.context.ApplicationContext;

public class SpringManagerUtils {
    public static AdminManager getAdminManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AdminManager manager = appContext.getBean(AdminManager.class);
        return manager;
    }

    public static MachineManager getMachineManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MachineManager manager = appContext.getBean(MachineManager.class);
        return manager;
    }

    public static MachineGroupManager getMachineGroupManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MachineGroupManager manager = appContext.getBean(MachineGroupManager.class);
        return manager;
    }

    public static TenantManager getTenantManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TenantManager manager = appContext.getBean(TenantManager.class);
        return manager;
    }
}
