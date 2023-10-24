package com.meta.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliAutoRoute {
    public static final String AUDIT_BY_REJECT = "reject";  //审核拒绝
    public static final String AUDIT_BY_PASS = "pass";  //审核通过
    private String accessKeyId;
    private String secret;

    public  Map<String,String> imgAutoRoute(String imgUrl){
        Config config = new Config();
        /**
         * 阿里云账号AccessKey拥有所有API的访问权限，建议您使用RAM用户进行API访问或日常运维。
         * 常见获取环境变量方式：
         * 方式一：
         *     获取RAM用户AccessKey ID：System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
         *     获取RAM用户AccessKey Secret：System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
         * 方式二：
         *     获取RAM用户AccessKey ID：System.getProperty("ALIBABA_CLOUD_ACCESS_KEY_ID");
         *     获取RAM用户AccessKey Secret：System.getProperty("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
         */
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);
        // 接入区域和地址请根据实际情况修改。
        config.setRegionId("cn-shanghai");
        config.setEndpoint("green-cip.cn-shanghai.aliyuncs.com");
        // 连接时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        // 读取时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);
        // 设置http代理。
        // config.setHttpProxy("http://10.10.xx.xx:xxxx");
        // 设置https代理。
        //config.setHttpsProxy("https://10.10.xx.xx:xxxx");
        // 注意，此处实例化的client请尽可能重复使用，避免重复建立连接，提升检测性能。
        Client client = null;
        try {
            client = new Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 创建RuntimeObject实例并设置运行参数。
        RuntimeOptions runtime = new RuntimeOptions();
        runtime.readTimeout = 10000;
        runtime.connectTimeout = 10000;

        // 检测参数构造。
        Map<String, String> serviceParameters = new HashMap<>();
        //公网可访问的URL。
        serviceParameters.put("imageUrl",imgUrl);
        serviceParameters.put("dataId", UUID.randomUUID().toString());

        ImageModerationRequest request = new ImageModerationRequest();
        // 图片检测service: baselineCheck通用基线检测。
        request.setService("baselineCheck");
        request.setServiceParameters(JSON.toJSONString(serviceParameters));

        try {
            ImageModerationResponse response = client.imageModerationWithOptions(request, runtime);
            // 自动路由。
            if (response != null) {
                // 服务端错误，区域切换到cn-beijing。
                if (500 == response.getStatusCode() || (response.getBody() != null && 500 == (response.getBody().getCode()))) {
                    // 接入区域和地址请根据实际情况修改。
                    config.setRegionId("cn-beijing");
                    config.setEndpoint("green-cip.cn-beijing.aliyuncs.com");
                    client = new Client(config);
                    response = client.imageModerationWithOptions(request, runtime);
                }
            }
            // 打印检测结果。
            if (response != null) {
                if (response.getStatusCode() == 200) {
                    ImageModerationResponseBody body = response.getBody();
                    System.out.println("requestId=" + body.getRequestId());
                    System.out.println("code=" + body.getCode());
                    System.out.println("msg=" + body.getMsg());
                    if (body.getCode() == 200) {
                        ImageModerationResponseBody.ImageModerationResponseBodyData data = body.getData();
                        System.out.println("dataId=" + data.getDataId());
                        List<ImageModerationResponseBody.ImageModerationResponseBodyDataResult> results = data.getResult();
                        for (ImageModerationResponseBody.ImageModerationResponseBodyDataResult result : results) {
                            System.out.println("label=" + result.getLabel());
                            System.out.println("confidence=" + result.getConfidence());
                            return new AliAutoRoute().checkPhotoStatus(result.getLabel());
                        }
                    } else {
                        System.out.println("image moderation not success. code:" + body.getCode());
                        return null;
                    }


                } else {
                    System.out.println("response not success. status:" + response.getStatusCode());
                    return null;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public  Map<String,String> textAutoRoute(String text){
        Config config = new Config();
        /**
         * 阿里云账号AccessKey拥有所有API的访问权限，建议您使用RAM用户进行API访问或日常运维。
         * 常见获取环境变量方式：
         * 方式一：
         *     获取RAM用户AccessKey ID：System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
         *     获取RAM用户AccessKey Secret：System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
         * 方式二：
         *     获取RAM用户AccessKey ID：System.getProperty("ALIBABA_CLOUD_ACCESS_KEY_ID");
         *     获取RAM用户AccessKey Secret：System.getProperty("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
         */
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);
        //接入区域和地址请根据实际情况修改
        config.setRegionId("cn-shanghai");
        config.setEndpoint("green-cip.cn-shanghai.aliyuncs.com");
        //连接时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        //读取时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);
        //设置http代理。
        //config.setHttpProxy("http://10.10.xx.xx:xxxx");
        //设置https代理。
        //config.setHttpsProxy("https://10.10.xx.xx:xxxx");
        // 注意，此处实例化的client请尽可能重复使用，避免重复建立连接，提升检测性能
        Client client = null;
        try {
            client = new Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 创建RuntimeObject实例并设置运行参数。
        RuntimeOptions runtime = new RuntimeOptions();
        runtime.readTimeout = 10000;
        runtime.connectTimeout = 10000;

        //检测参数构造
        JSONObject serviceParameters = new JSONObject();
        serviceParameters.put("content", text);

        if (serviceParameters.get("content") == null || serviceParameters.getString("content").trim().length() == 0) {
            System.out.println("text moderation content is empty");
            return null;
        }

        TextModerationRequest textModerationRequest = new TextModerationRequest();
        /*
        文本检测服务 service code
        */
        textModerationRequest.setService("comment_detection");
        textModerationRequest.setServiceParameters(serviceParameters.toJSONString());
        try {
            // 调用方法获取检测结果。
            TextModerationResponse response = client.textModerationWithOptions(textModerationRequest, runtime);

            // 自动路由。
            if (response != null) {
                // 服务端错误，区域切换到cn-beijing。
                if (500 == response.getStatusCode() || (response.getBody() != null && 500 == (response.getBody().getCode()))) {
                    // 接入区域和地址请根据实际情况修改。
                    config.setRegionId("cn-beijing");
                    config.setEndpoint("green-cip.cn-beijing.aliyuncs.com");
                    client = new Client(config);
                    response = client.textModerationWithOptions(textModerationRequest, runtime);
                }
            }
            if (response != null){
                // 打印检测结果。
                if (response.getStatusCode() == 200) {
                    TextModerationResponseBody result = response.getBody();
                    System.out.println(JSON.toJSONString(result));
                    Integer code = result.getCode();
                    if (code != null && code == 200) {
                        TextModerationResponseBody.TextModerationResponseBodyData data = result.getData();
                        System.out.println("labels = [" + data.getLabels() + "]");
                        System.out.println("reason = [" + data.getReason() + "]");
                        return new AliAutoRoute().checkTextStatus(data.getLabels());
                    } else {
                        System.out.println("text moderation not success. code:" + code);
                        return null;
                    }
                } else {
                    System.out.println("response not success. status:" + response.getStatusCode());
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<String,String> checkTextStatus(String labels){
        HashMap<String, String> map = new HashMap<>();
        if (labels == null || labels.equals("") ){
            map.put("status",AliAutoRoute.AUDIT_BY_PASS);
            return map;
        }
        map.put("status",AliAutoRoute.AUDIT_BY_REJECT);
        String content = "审核拒绝原因：";
        List<String> collect = Arrays.stream(labels.split(",")).collect(Collectors.toList());
        if (!collect.isEmpty()){
            for (String s : collect) {
                switch (s) {
                    case "political_content":
                        System.out.println("涉政内容");
                        content = content + "涉政内容";
                        break;
                    case "sexual_content":
                        System.out.println("色情内容");
                        content = content + "色情内容";
                        break;
                    case "violence":
                        System.out.println("暴恐内容");
                        content = content + "暴恐内容";
                        break;
                    case "contraband":
                        System.out.println("违禁内容");
                        content = content + "违禁内容";
                        break;
                    case "ad":
                        System.out.println("广告引流内容");
                        content = content + "广告引流内容";
                        break;
                    case "religion":
                        System.out.println("宗教内容");
                        content = content + "宗教内容";
                        break;
                    case "profanity":
                        System.out.println("辱骂内容");
                        content = content + "辱骂内容";
                        break;
                    case "negative_content":
                        System.out.println("不良内容");
                        content = content + "不良内容";
                        break;
                    case "nonsense":
                        System.out.println("无意义内容");
                        content = content + "无意义内容";
                        break;
                    default:
                        System.out.println("无效的输入");
                        content = content + "无效的输入";
                }
            }
        }
        map.put("content",content);
        return map;
    }

    //由于返回的检测内容标签过多此处不做处理直接判断结果
    public Map<String,String> checkPhotoStatus(String labels){
        HashMap<String, String> map = new HashMap<>();
        if (labels.equals("nonLabel") ){
            map.put("status",AliAutoRoute.AUDIT_BY_PASS);
            return map;
        }
        map.put("status",AliAutoRoute.AUDIT_BY_REJECT);
        return map;
    }
}

