package com.langtuo.teamachine.mqtt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.mqtt.server.config.ChannelConfig;
import com.aliyun.onsmqtt20200420.Client;
import com.aliyun.onsmqtt20200420.models.ApplyTokenRequest;
import com.aliyun.onsmqtt20200420.models.ApplyTokenResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import com.langtuo.teamachine.mqtt.model.MqttToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Date;

/**
 * @author jiaqing
 */
@Slf4j
public class MqttUtils {
    private static final String ACTIONS = "R,W";
    private static final String RESOURCES_PREFIX = "teamachine/#,";
    private static final String RESOURCES_POSTFIX = "-teamachine/#";

    /**
     * <b>description</b> :
     * <p>使用AK&amp;SK初始化账号Client</p>
     * @return Client
     *
     * @throws Exception
     */
    private static Client createClient() throws Exception {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考。
        // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。
        Config config = new Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(AliyunConsts.RAM_ACCESS_KEY)
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(AliyunConsts.RAM_ACCESS_KEY_SECRET);
        // Endpoint 请参考 https://api.aliyun.com/product/OnsMqtt
        config.endpoint = AliyunConsts.MQTT_MANAGER_ENDPOINT;
        return new Client(config);
    }

    public static MqttToken getMqttToken(String tenantCode) {
        if (StringUtils.isBlank(tenantCode)) {
            return null;
        }
        StringBuffer resources = new StringBuffer();
        resources.append(RESOURCES_PREFIX).append(tenantCode).append(RESOURCES_POSTFIX);

        long expiration = System.currentTimeMillis() + 1000 * 60 * 60 * 72;
        try {
            Client client = createClient();
            ApplyTokenRequest applyTokenRequest = new ApplyTokenRequest()
                    .setActions(ACTIONS)
                    .setExpireTime(expiration)
                    .setInstanceId(AliyunConsts.MQTT_INSTANCE_ID)
                    .setResources(resources.toString());
            RuntimeOptions runtime = new RuntimeOptions();

            ApplyTokenResponse resp = client.applyTokenWithOptions(applyTokenRequest, runtime);
            log.debug("getMqttToken|succ|resp=" + JSON.toJSONString(resp));
            MqttToken mqttToken = new MqttToken();
            mqttToken.setAccessKey(AliyunConsts.RAM_ACCESS_KEY);
            mqttToken.setAccessToken(resp.getBody().getToken());
            mqttToken.setExpiration(new Date(expiration));
            return mqttToken;
        } catch (TeaException error) {
            log.error("getMqttToken|fatal|e=" + error.getMessage() + ";recommend=" + error.getData().get("Recommend"));
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            log.error("getMqttToken|fatal|e=" + error.getMessage() + ";recommend=" + error.getData().get("Recommend"));
        }
        return null;
    }

    /**
     * @param text 要签名的文本
     * @param secretKey 阿里云MQ secretKey
     * @return 加密后的字符串
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String macSignature(String text,
            String secretKey) throws InvalidKeyException, NoSuchAlgorithmException {
        Charset charset = Charset.forName("UTF-8");
        String algorithm = "HmacSHA1";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(secretKey.getBytes(charset), algorithm));
        byte[] bytes = mac.doFinal(text.getBytes(charset));
        return new String(Base64.encodeBase64(bytes), charset);
    }

    public static ChannelConfig getChannelConfig() {
        // 此处参数内容为示意。接入点地址，购买实例，且配置完成后即可获取，接入点地址必须填写分配的域名，
        // 不得使用 IP 地址直接连接，否则可能会导致客户端异常
        String domain = AliyunConsts.MQTT_ENDPOINT;

        // 使用的协议和端口必须匹配，为5672
        int port = 5672;

        // 此处参数内容为示意。MQTT 实例 ID，购买后控制台获取
        String instanceId = AliyunConsts.MQTT_INSTANCE_ID;

        // 此处参数内容为示意。账号 accesskey，从账号系统控制台获取
        String accessKey = AliyunConsts.RAM_ACCESS_KEY; // "accessKey";

        // 此处参数内容为示意。账号 secretKey，从账号系统控制台获取，仅在Signature鉴权模式下需要设置
        String secretKey = AliyunConsts.RAM_ACCESS_KEY_SECRET; // "secretKey";

        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.setDomain(domain);
        channelConfig.setPort(port);
        channelConfig.setInstanceId(instanceId);
        channelConfig.setAccessKey(accessKey);
        channelConfig.setSecretKey(secretKey);
        return channelConfig;
    }
}
