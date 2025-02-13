package tech.infantai.deepseek_sdk.client;

import tech.infantai.deepseek_sdk.config.GPTConfig;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GPTClient {
    private final GPTConfig config;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public GPTClient(GPTConfig config) {
        this.config = config;
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
            .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
            .writeTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
            .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public String chat(String prompt) throws IOException {
        RequestBody body = RequestBody.create(
            MediaType.parse("application/json"),
            createRequestBody(prompt)
        );
        
        Request request = new Request.Builder()
            .url(config.getEndpoint())
            .addHeader("Authorization", "Bearer " + config.getApiKey())
            .post(body)
            .build();
            
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response " + response);
            }
            return parseResponse(response.body().string());
        }
    }
    
    private String createRequestBody(String prompt) throws IOException {
        return objectMapper.writeValueAsString(new ChatRequest(config.getModel(), prompt));
    }
    
    private String parseResponse(String responseBody) throws IOException {
        // 根据不同的 API 响应格式进行解析
        // 这里需要根据具体使用的 API 来实现
        return responseBody;
    }
}

class ChatRequest {
    private String model;
    private String prompt;
    
    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }
    
    // Getters
    public String getModel() {
        return model;
    }
    
    public String getPrompt() {
        return prompt;
    }
} 