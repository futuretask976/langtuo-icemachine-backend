package com.langtuo.teamachine.dao.oss;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.VoidResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.langtuo.teamachine.dao.po.security.OssToken;
import com.langtuo.teamachine.internal.constant.AliyunConsts;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Date;

@Slf4j
public class OssUtils {
    public static OssToken getSTS() {
        // STS服务接入点，例如sts.cn-hangzhou.aliyuncs.com。您可以通过公网或者VPC接入STS服务。
        String endpoint = AliyunConsts.OSS_STS_ENDPOINT;
        // 从环境变量中获取步骤1生成的RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = AliyunConsts.RAM_ACCESS_KEY;
        String accessKeySecret = AliyunConsts.RAM_ACCESS_KEY_SECRET;
        // 从环境变量中获取步骤3生成的RAM角色的RamRoleArn。
        String roleArn = AliyunConsts.OSS_STS_ROLE_ARN;
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = AliyunConsts.OSS_STS_SESSION_NAME;
        // 临时访问凭证将获得角色拥有的所有权限。
        String policy = null;
        // 临时访问凭证的有效时间，单位为秒。最小值为900，最大值以当前角色设定的最大会话时间为准。当前角色最大会话时间取值范围为3600秒~43200秒，默认值为3600秒。
        // 在上传大文件或者其他较耗时的使用场景中，建议合理设置临时访问凭证的有效时间，确保在完成目标任务前无需反复调用STS服务以获取临时访问凭证。
        Long durationSeconds = AliyunConsts.OSS_STS_DURATION_SECONDS;

        OssToken stsPO = null;
        try {
            // 发起STS请求所在的地域。建议保留默认值，默认值为空字符串（""）。
            String regionId = "";
            // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            // 添加endpoint。适用于Java SDK 3.12.0以下版本。
            // DefaultProfile.addEndpoint("",regionId, "Sts", endpoint);
            // 构造default profile。
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            // 构造client。
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            // 适用于Java SDK 3.12.0及以上版本。
            request.setSysMethod(MethodType.POST);
            // 适用于Java SDK 3.12.0以下版本。
            // request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            AssumeRoleResponse.Credentials credentials = response.getCredentials();

            stsPO = new OssToken();
            stsPO.setRegion(AliyunConsts.OSS_REGION);
            stsPO.setBucketName(AliyunConsts.OSS_BUCKET_NAME);
            stsPO.setExpiration(credentials.getExpiration());
            stsPO.setAccessKeyId(credentials.getAccessKeyId());
            stsPO.setAccessKeySecret(credentials.getAccessKeySecret());
            stsPO.setSecurityToken(credentials.getSecurityToken());
            stsPO.setRequestId(response.getRequestId());
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }
        return stsPO;
    }

    public static String uploadFile(File file, String ossPath) throws OSSException, ClientException, FileNotFoundException {
        if (!file.exists()) {
            throw new IllegalArgumentException(file.getName());
        }

        // 获取文件名
        String fileName = file.getName();
//        // 获取文件后缀名
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        // 最后上传生成的文件名
//        String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400)
//                + suffixName;
        // OSS中的文件夹名
        String objectName = ossPath + AliyunConsts.OSS_PATH_SEPARATOR +  fileName; // finalFileName;

        // 创建OSSClient实例
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(
                AliyunConsts.RAM_ACCESS_KEY, AliyunConsts.RAM_ACCESS_KEY_SECRET);
        OSS ossClient = new OSSClientBuilder().build(AliyunConsts.OSS_ENDPOINT, credentialsProvider);
        // 创建file输入流
        FileInputStream fileInputStream = null;
        try {
            VoidResult deleteResult = ossClient.deleteObject(AliyunConsts.OSS_BUCKET_NAME, objectName);
            log.error("deleteObject|showResult|putResult=" + (deleteResult == null ? null : JSON.toJSONString(deleteResult)));

            fileInputStream = new FileInputStream(file);
            PutObjectResult putResult = ossClient.putObject(AliyunConsts.OSS_BUCKET_NAME, objectName,
                    fileInputStream);
            log.error("putResult|showResult|putResult=" + (putResult == null ? null : JSON.toJSONString(putResult)));
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return objectName;
    }

    public static String genAccessObjectURL(String objectName) throws OSSException, ClientException {
        // 创建OSSClient实例
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(
                AliyunConsts.RAM_ACCESS_KEY, AliyunConsts.RAM_ACCESS_KEY_SECRET);
        OSS ossClient = new OSSClientBuilder().build(AliyunConsts.OSS_ENDPOINT, credentialsProvider);

        try {
            Date expiration = new Date(System.currentTimeMillis() + AliyunConsts.OSS_ACCESS_EXPIRATION_TIME);
            URL result = ossClient.generatePresignedUrl(AliyunConsts.OSS_BUCKET_NAME, objectName, expiration);
            return result.toString();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static void main(String args[]) {
        OssToken po = OssUtils.getSTS();
        System.out.println(po);
    }
}
