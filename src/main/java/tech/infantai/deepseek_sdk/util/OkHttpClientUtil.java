package tech.infantai.deepseek_sdk.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class OkHttpClientUtil {
    private static final Logger logger = Logger.getLogger(OkHttpClientUtil.class.getName());
    // 单例实例
    private static final OkHttpClientUtil INSTANCE = new OkHttpClientUtil();

    // OkHttpClient 实例
    private  volatile OkHttpClient client;

    // 私有构造函数，初始化 OkHttpClient
    private OkHttpClientUtil() {
        if (client == null) {
            synchronized (OkHttpClientUtil.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS) // 连接超时 60 秒
                            .readTimeout(60, TimeUnit.SECONDS)    // 读取超时 30 秒
                            .writeTimeout(60, TimeUnit.SECONDS)  // 写入超时 30 秒
                            .build();
                }
            }
        }
    }

    // 获取单例实例
    public static OkHttpClientUtil getInstance() {
        return INSTANCE;
    }

    // 发送 POST 请求
    public String post(String url, String json, Map<String, String> headers)   {
        // 创建 MediaType
        MediaType mediaType = MediaType.parse("application/json");
        // 创建 RequestBody
        RequestBody body = RequestBody.create(mediaType, json);

        // 创建 Request.Builder
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method("POST", body);

        // 添加 Header
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 构建 Request
        Request request = requestBuilder.build();

        // 发送请求并获取响应
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("error is not successful: " + response.code());
            }
        }catch (Exception e) {
            logger.severe("error: " + e.getMessage());
        }
        return null;
    }

    public  JSONObject getObjToJSONPost(String url, String json, Map<String, String> header) throws Exception {
        JSONObject responseObject = new JSONObject();
        if (header == null) {
            header = new HashMap<String, String>();
        }
        if (StringUtils.isEmpty(url)){
            throw new Exception("url is null");
        }
        String body = post(url, json, header);
        if (StringUtils.isNotEmpty(body)) {
            responseObject = JSONArray.parseObject(body);
            return responseObject;
        }
        return null;
    }
}
