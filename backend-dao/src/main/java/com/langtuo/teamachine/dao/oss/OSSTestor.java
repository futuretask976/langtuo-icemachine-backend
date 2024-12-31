package com.langtuo.teamachine.dao.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.langtuo.teamachine.dao.po.security.OssToken;
import com.langtuo.teamachine.internal.constant.AliyunConsts;

import java.io.*;
import java.net.URL;
import java.util.Date;

public class OSSTestor {
    public static void main(String args[]) {
        OSSTestor.genAccessFileURLBySTS();
    }

    public static void genAccessFileURLBySTS() {
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "miya-bucket2";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "teamachine/osstest01.jpg";

        // 创建OSSClient实例
        OssToken stsPO = OssUtils.getSTS();
        OSS ossClient = new OSSClientBuilder().build(AliyunConsts.OSS_ENDPOINT,
                stsPO.getAccessKeyId(), stsPO.getAccessKeySecret(), stsPO.getSecurityToken());

        try {
            Date expiration = new Date(System.currentTimeMillis() + AliyunConsts.OSS_ACCESS_EXPIRATION_TIME);
            URL result = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
            System.out.println(result);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static void uploadFileBySTS() {
        File file = new File("backend-dao/src/main/resources/naicha_01.jpg");
        System.out.println(file.getAbsolutePath() + "=" + file.exists());

        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称
        String objectName = "teamachine/osstest01.jpg";

        // 创建OSSClient实例
        OssToken stsPO = OssUtils.getSTS();
        OSS ossClient = new OSSClientBuilder().build(AliyunConsts.OSS_ENDPOINT,
                stsPO.getAccessKeyId(), stsPO.getAccessKeySecret(), stsPO.getSecurityToken());

        PutObjectRequest putObjectRequest = new PutObjectRequest(AliyunConsts.OSS_BUCKET_NAME, objectName,
                new File("backend-dao/src/main/resources/naicha_01.jpg"));
        try {
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            System.out.println(result);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static void getSTS() {
        // STS服务接入点，例如sts.cn-hangzhou.aliyuncs.com。您可以通过公网或者VPC接入STS服务。
        String endpoint = "sts.cn-hangzhou.aliyuncs.com";
        // 从环境变量中获取步骤1生成的RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = "LTAI5t6hg6snjTBEddAP8tz8";
        String accessKeySecret = "MammwnIOPrHe9AAO4CnaUJwmIG96Kc";
        // 从环境变量中获取步骤3生成的RAM角色的RamRoleArn。
        String roleArn = "acs:ram::1079138807996471:role/ramosstest";
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "miyaRoleSession";
        // 临时访问凭证将获得角色拥有的所有权限。
        String policy = null;
        // 临时访问凭证的有效时间，单位为秒。最小值为900，最大值以当前角色设定的最大会话时间为准。当前角色最大会话时间取值范围为3600秒~43200秒，默认值为3600秒。
        // 在上传大文件或者其他较耗时的使用场景中，建议合理设置临时访问凭证的有效时间，确保在完成目标任务前无需反复调用STS服务以获取临时访问凭证。
        Long durationSeconds = 3600L;
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
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("Error message: " + e.getErrorMessage());
            System.out.println("RequestId: " + e.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }
    }

    public static void createBucket() {
        // 创建OSSClient实例
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(
                AliyunConsts.RAM_ACCESS_KEY, AliyunConsts.RAM_ACCESS_KEY_SECRET);
        OSS ossClient = new OSSClientBuilder().build(AliyunConsts.OSS_ENDPOINT, credentialsProvider);

        try {
            // 创建存储空间
            Bucket bucket = ossClient.createBucket(AliyunConsts.OSS_BUCKET_NAME);
            System.out.println("bucket created=" + bucket);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static void uploadFile() {
        File file = new File("backend-dao/src/main/resources/naicha_01.jpg");
        System.out.println(file.getAbsolutePath() + "=" + file.exists());

        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称
        String objectName = "osstestor/osstest01.jpg";

        // 创建OSSClient实例
        OssToken OssToken = OssUtils.getSTS();
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(
                AliyunConsts.RAM_ACCESS_KEY, AliyunConsts.RAM_ACCESS_KEY_SECRET);
        OSS ossClient = new OSSClientBuilder().build(AliyunConsts.OSS_ENDPOINT, credentialsProvider);

        try {
            PutObjectResult result = ossClient.putObject(AliyunConsts.OSS_BUCKET_NAME, objectName,
                    new ByteArrayInputStream(readImgFile()));
            System.out.println(result);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static byte[] readImgFile() {
        File file = new File("backend-dao/src/main/resources/naicha_01.jpg");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            // Create a byte array the size of the file
            byte[] imageBytes = new byte[(int) file.length()];
            // Read in the entire file
            fileInputStream.read(imageBytes);
            return imageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void genAccessFileURL() {
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "miya-bucket2";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "osstestor/osstest01.jpg";

        // 创建OSSClient实例
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(
                AliyunConsts.RAM_ACCESS_KEY, AliyunConsts.RAM_ACCESS_KEY_SECRET);
        OSS ossClient = new OSSClientBuilder().build(AliyunConsts.OSS_ENDPOINT, credentialsProvider);

        try {
            Date expiration = new Date(System.currentTimeMillis() + AliyunConsts.OSS_ACCESS_EXPIRATION_TIME);
            URL result = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
            System.out.println(result);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static void downloadFile() {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(
                AliyunConsts.RAM_ACCESS_KEY, AliyunConsts.RAM_ACCESS_KEY_SECRET);
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "miya-bucket2";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "osstestor/osstest01.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元数据。
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
            InputStream content = ossObject.getObjectContent();
            if (content != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    System.out.println("\n" + line);
                }
                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                content.close();
            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
