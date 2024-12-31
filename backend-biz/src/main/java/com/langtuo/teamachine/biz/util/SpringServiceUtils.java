package com.langtuo.teamachine.biz.util;

import cn.hutool.extra.spring.SpringUtil;
import com.langtuo.teamachine.mqtt.produce.MqttProducer;
import org.springframework.context.ApplicationContext;

public class SpringServiceUtils {
    public static MqttProducer getMqttProducer() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MqttProducer service = appContext.getBean(MqttProducer.class);
        return service;
    }
}
