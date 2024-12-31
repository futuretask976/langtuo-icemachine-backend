package com.langtuo.teamachine.mqtt.produce;

import com.alibaba.mqtt.server.ServerProducer;
import com.alibaba.mqtt.server.callback.SendCallback;
import com.alibaba.mqtt.server.config.ChannelConfig;
import com.alibaba.mqtt.server.config.ProducerConfig;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import com.langtuo.teamachine.mqtt.util.MqttUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

/**
 * @author Jiaqing
 */
@Component
@Slf4j
public class MqttProducer implements InitializingBean {
    /**
     * MQTT 发送者实例
     */
    private ServerProducer serverProducer;

    @Override
    public void afterPropertiesSet() throws IOException, TimeoutException {
        if (serverProducer == null) {
            synchronized (MqttProducer.class) {
                if (serverProducer == null) {
                    init();
                }
            }
        }
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("stopServerProducer|entering");
        if (serverProducer == null) {
            return;
        }

        try {
            serverProducer.stop();

            Field schedulerField = ServerProducer.class.getDeclaredField("scheduler");
            schedulerField.setAccessible(true);
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) schedulerField.get(
                    serverProducer);
            scheduledThreadPoolExecutor.shutdownNow();

            serverProducer = null;
        } catch (Exception e) {
            System.out.println("stopServerProducer|fatal|e=" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("stopServerProducer|exiting");
    }

    public void init() throws IOException, TimeoutException {
        ChannelConfig channelConfig = MqttUtils.getChannelConfig();
        serverProducer = new ServerProducer(channelConfig, new ProducerConfig());
        serverProducer.start();
    }

    public void sendP2PMsgByTenant(String tenantCode, String machineCode, String payload) {
        String topic = tenantCode + AliyunConsts.MQTT_TENANT_PARENT_P2P_TOPIC_POSTFIX + machineCode;
        try {
            serverProducer.sendMessage(topic, payload.getBytes(StandardCharsets.UTF_8), new SendCallback() {
                @Override
                public void onSuccess(String s) {
                    log.info("sendP2PMsg|onSuccess|topic=" + topic + ";payload=" + payload);
                }

                @Override
                public void onFail() {
                    log.error("sendP2PMsg|onFail|topic=" + topic + ";payload=" + payload);
                }
            });
        } catch (Throwable e) {
            log.error("sendP2PMsg|fatal|e=" + e.getMessage(), e);
        }
    }

    public void sendBroadcastMsgByTenant(String tenantCode, String payload) {
        String topic = tenantCode + AliyunConsts.MQTT_TENANT_PARENT_TOPIC_POSTFIX + AliyunConsts.MQTT_TOPIC_SEPERATOR + "broadcast";
        try {
            serverProducer.sendMessage(topic, payload.getBytes(StandardCharsets.UTF_8), new SendCallback() {
                @Override
                public void onSuccess(String s) {
                    log.info("sendBroadcastMsg|onSuccess|topic=" + topic + ";payload=" + payload);
                }

                @Override
                public void onFail() {
                    log.error("sendBroadcastMsg|onFail|topic=" + topic + ";payload=" + payload);
                }
            });
        } catch (Throwable e) {
            log.error("sendBroadcastMsg|fatal|e=" + e.getMessage(), e);
        }
    }
}
