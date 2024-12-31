package com.langtuo.teamachine.mqtt.util;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

public class SpringTemplateUtils {
    public static TransactionTemplate getTransactionTemplate() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        TransactionTemplate template = appContext.getBean(TransactionTemplate.class);
        return template;
    }
}
