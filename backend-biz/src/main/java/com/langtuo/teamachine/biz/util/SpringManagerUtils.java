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

    public static MenuManager getMenuManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MenuManager manager = appContext.getBean(MenuManager.class);
        return manager;
    }

    public static OrgManager getOrgManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        OrgManager manager = appContext.getBean(OrgManager.class);
        return manager;
    }

    public static ShopGroupManager getShopGroupManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ShopGroupManager manager = appContext.getBean(ShopGroupManager.class);
        return manager;
    }

    public static ShopManager getShopManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ShopManager manager = appContext.getBean(ShopManager.class);
        return manager;
    }

    public static TenantManager getTenantManager() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TenantManager manager = appContext.getBean(TenantManager.class);
        return manager;
    }
}
