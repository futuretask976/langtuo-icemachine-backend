package com.langtuo.teamachine.dao.util;

import cn.hutool.extra.spring.SpringUtil;
import com.langtuo.teamachine.dao.accessor.device.*;
import com.langtuo.teamachine.dao.accessor.drink.*;
import com.langtuo.teamachine.dao.accessor.menu.*;
import com.langtuo.teamachine.dao.accessor.record.*;
import com.langtuo.teamachine.dao.accessor.rule.*;
import com.langtuo.teamachine.dao.accessor.shop.ShopAccessor;
import com.langtuo.teamachine.dao.accessor.device.MachineGroupAccessor;
import com.langtuo.teamachine.dao.accessor.user.*;
import org.springframework.context.ApplicationContext;

public class SpringAccessorUtils {
    public static OrderToppingActRecordAccessor getOrderToppingActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        OrderToppingActRecordAccessor accessor = appContext.getBean(OrderToppingActRecordAccessor.class);
        return accessor;
    }

    public static MenuAccessor getMenuAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MenuAccessor accessor = appContext.getBean(MenuAccessor.class);
        return accessor;
    }

    public static MenuSeriesRelAccessor getMenuSeriesRelAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MenuSeriesRelAccessor accessor = appContext.getBean(MenuSeriesRelAccessor.class);
        return accessor;
    }

    public static SeriesAccessor getSeriesAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        SeriesAccessor accessor = appContext.getBean(SeriesAccessor.class);
        return accessor;
    }

    public static SeriesTeaRelAccessor getSeriesTeaRelAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        SeriesTeaRelAccessor accessor = appContext.getBean(SeriesTeaRelAccessor.class);
        return accessor;
    }

    public static MenuDispatchAccessor getMenuDispatchAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MenuDispatchAccessor accessor = appContext.getBean(MenuDispatchAccessor.class);
        return accessor;
    }

    public static ConfigRuleDispatchAccessor getCleanRuleDispatchAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ConfigRuleDispatchAccessor accessor = appContext.getBean(ConfigRuleDispatchAccessor.class);
        return accessor;
    }

    public static CleanRuleExceptAccessor getCleanRuleExceptAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        CleanRuleExceptAccessor accessor = appContext.getBean(CleanRuleExceptAccessor.class);
        return accessor;
    }

    public static CleanRuleStepAccessor getCleanRuleStepAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        CleanRuleStepAccessor accessor = appContext.getBean(CleanRuleStepAccessor.class);
        return accessor;
    }

    public static DrainRuleDispatchAccessor getDrainRuleDispatchNewAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        DrainRuleDispatchAccessor accessor = appContext.getBean(DrainRuleDispatchAccessor.class);
        return accessor;
    }

    public static WarningRuleAccessor getWarningRuleAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        WarningRuleAccessor accessor = appContext.getBean(WarningRuleAccessor.class);
        return accessor;
    }

    public static WarningRuleDispatchAccessor getWarningRuleDispatchAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        WarningRuleDispatchAccessor accessor = appContext.getBean(WarningRuleDispatchAccessor.class);
        return accessor;
    }

    public static OrderSpecItemActRecordAccessor getOrderSpecItemActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        OrderSpecItemActRecordAccessor accessor = appContext.getBean(OrderSpecItemActRecordAccessor.class);
        return accessor;
    }

    public static OrderActRecordAccessor getOrderActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        OrderActRecordAccessor accessor = appContext.getBean(OrderActRecordAccessor.class);
        return accessor;
    }

    public static SupplyActRecordAccessor getSupplyActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        SupplyActRecordAccessor accessor = appContext.getBean(SupplyActRecordAccessor.class);
        return accessor;
    }

    public static InvalidActRecordAccessor getInvalidActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        InvalidActRecordAccessor accessor = appContext.getBean(InvalidActRecordAccessor.class);
        return accessor;
    }

    public static DrainActRecordAccessor getDrainActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        DrainActRecordAccessor accessor = appContext.getBean(DrainActRecordAccessor.class);
        return accessor;
    }

    public static ActRecordAccessor getCleanActRecordAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ActRecordAccessor accessor = appContext.getBean(ActRecordAccessor.class);
        return accessor;
    }

    public static AdminAccessor getAdminAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AdminAccessor accessor = appContext.getBean(AdminAccessor.class);
        return accessor;
    }

    public static ModelAccessor getModelAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ModelAccessor accessor = appContext.getBean(ModelAccessor.class);
        return accessor;
    }

    public static ModelPipelineAccessor getModelPipelineAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ModelPipelineAccessor accessor = appContext.getBean(ModelPipelineAccessor.class);
        return accessor;
    }

    public static AndroidAppAccessor getAndroidAppAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AndroidAppAccessor accessor = appContext.getBean(AndroidAppAccessor.class);
        return accessor;
    }

    public static OrgAccessor getOrgAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        OrgAccessor accessor = appContext.getBean(OrgAccessor.class);
        return accessor;
    }

    public static MachineGroupAccessor getShopGroupAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MachineGroupAccessor accessor = appContext.getBean(MachineGroupAccessor.class);
        return accessor;
    }

    public static ShopAccessor getShopAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ShopAccessor accessor = appContext.getBean(ShopAccessor.class);
        return accessor;
    }

    public static TenantAccessor getTenantAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TenantAccessor accessor = appContext.getBean(TenantAccessor.class);
        return accessor;
    }

    public static RoleAccessor getRoleAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        RoleAccessor accessor = appContext.getBean(RoleAccessor.class);
        return accessor;
    }

    public static RoleActRelAccessor getRoleActRelAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        RoleActRelAccessor accessor = appContext.getBean(RoleActRelAccessor.class);
        return accessor;
    }

    public static PermitActAccessor getPermitActAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        PermitActAccessor accessor = appContext.getBean(PermitActAccessor.class);
        return accessor;
    }

    public static DeployAccessor getDeployAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        DeployAccessor accessor = appContext.getBean(DeployAccessor.class);
        return accessor;
    }

    public static TeaAccessor getTeaAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TeaAccessor accessor = appContext.getBean(TeaAccessor.class);
        return accessor;
    }

    public static TeaTypeAccessor getTeaTypeAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TeaTypeAccessor accessor = appContext.getBean(TeaTypeAccessor.class);
        return accessor;
    }

    public static ToppingBaseRuleAccessor getToppingBaseRuleAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ToppingBaseRuleAccessor accessor = appContext.getBean(ToppingBaseRuleAccessor.class);
        return accessor;
    }

    public static ToppingAdjustRuleAccessor getToppingAdjustRuleAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ToppingAdjustRuleAccessor accessor = appContext.getBean(ToppingAdjustRuleAccessor.class);
        return accessor;
    }

    public static TeaUnitAccessor getTeaUnitAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TeaUnitAccessor accessor = appContext.getBean(TeaUnitAccessor.class);
        return accessor;
    }

    public static ToppingAccessor getToppingAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ToppingAccessor accessor = appContext.getBean(ToppingAccessor.class);
        return accessor;
    }

    public static DrainRuleAccessor getDrainRuleNewAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        DrainRuleAccessor accessor = appContext.getBean(DrainRuleAccessor.class);
        return accessor;
    }

    public static ConfigRuleAccessor getCleanRuleAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        ConfigRuleAccessor accessor = appContext.getBean(ConfigRuleAccessor.class);
        return accessor;
    }

    public static DrainRuleExceptAccessor getDrainRuleExceptNewAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        DrainRuleExceptAccessor accessor = appContext.getBean(DrainRuleExceptAccessor.class);
        return accessor;
    }

    public static AccuracyTplToppingAccessor getAccuracyTplToppingAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AccuracyTplToppingAccessor accessor = appContext.getBean(AccuracyTplToppingAccessor.class);
        return accessor;
    }

    public static AccuracyTplAccessor getAccuracyTplAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        AccuracyTplAccessor accessor = appContext.getBean(AccuracyTplAccessor.class);
        return accessor;
    }

    public static SpecItemAccessor getSpecItemAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        SpecItemAccessor accessor = appContext.getBean(SpecItemAccessor.class);
        return accessor;
    }

    public static SpecItemRuleAccessor getTeaSpecItemAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        SpecItemRuleAccessor accessor = appContext.getBean(SpecItemRuleAccessor.class);
        return accessor;
    }

    public static SpecAccessor getSpecAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        SpecAccessor accessor = appContext.getBean(SpecAccessor.class);
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

    public static MenuDispatchCacheAccessor getMenuDispatchHistoryAccessor() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MenuDispatchCacheAccessor accessor = appContext.getBean(MenuDispatchCacheAccessor.class);
        return accessor;
    }
}
