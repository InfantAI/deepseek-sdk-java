package tech.infantai.deepseek_sdk.service;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import tech.infantai.deepseek_sdk.config.GPTConfig;
import tech.infantai.deepseek_sdk.model.ChatRequest;
import tech.infantai.deepseek_sdk.model.ChatResponse;
import tech.infantai.deepseek_sdk.model.Message;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractAIService {
    protected GPTConfig config;
    protected OkHttpClient httpClient;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    protected static final String CHAT_COMPLETION_PATH = "/chat/completions";

    public AbstractAIService(GPTConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("GPTConfig cannot be null");
        }
        this.config = config;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * 使用自定义消息列表调用API
     * Call API with custom message list
     *
     * @param messages 自定义消息列表
     *                List of messages to send
     * @return ChatResponse
     * @throws IOException 如果API调用失败
     *                     if the API call fails
     */
    public ChatResponse chatWithMessages(List<Message> messages) throws IOException {
        if (messages == null || messages.isEmpty()) {
            throw new IllegalArgumentException("Messages cannot be null or empty");
        }
        ChatRequest chatRequest = createChatRequestWithMessages(messages);
        return executeRequest(chatRequest, buildUrl());
    }

    /**
     * 使用默认的system message和user message调用chat completions API
     * Call chat completions API with default system message and user message
     *
     * @param userMessage 用户消息
     *                    The message from user
     * @param systemMessage 系统消息
     *                      The system instruction message
     * @return ChatResponse
     * @throws IOException 如果API调用失败
     *                     if the API call fails
     */
    public ChatResponse chat(String userMessage, String systemMessage) throws IOException {
        if (StringUtils.isBlank(userMessage)) {
            throw new IllegalArgumentException("User message cannot be null or empty");
        }
        if (StringUtils.isBlank(systemMessage)) {
            throw new IllegalArgumentException("System message cannot be null or empty");
        }
        ChatRequest chatRequest = createChatRequest(userMessage, systemMessage);
        return executeRequest(chatRequest, buildUrl());
    }

    /**
     * 使用指定的模型进行聊天
     * Chat with a specified model (temporary model switch)
     *
     * @param userMessage 用户消息
     *                    The message from user
     * @param systemMessage 系统消息
     *                      The system instruction message
     * @param model 模型名称
     *              The model name to use for this chat
     * @return ChatResponse
     * @throws IOException 如果API调用失败
     *                     if the API call fails
     */
    public ChatResponse chatWithModel(String userMessage, String systemMessage, String model) throws IOException {
        if (StringUtils.isBlank(model)) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        
        // 保存原始模型
        String originalModel = config.getModel();
        try {
            // 临时切换模型
            config.setModel(model);
            return chat(userMessage, systemMessage);
        } finally {
            // 恢复原始模型
            config.setModel(originalModel);
        }
    }

    /**
     * 使用指定的模型和自定义消息列表进行聊天
     * Chat with custom messages using a specified model (temporary model switch)
     *
     * @param messages 消息列表
     *                List of messages to send
     * @param model 模型名称
     *              The model name to use for this chat
     * @return ChatResponse
     * @throws IOException 如果API调用失败
     *                     if the API call fails
     */
    public ChatResponse chatWithMessagesAndModel(List<Message> messages, String model) throws IOException {
        if (StringUtils.isBlank(model)) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        
        // 保存原始模型
        String originalModel = config.getModel();
        try {
            // 临时切换模型
            config.setModel(model);
            return chatWithMessages(messages);
        } finally {
            // 恢复原始模型
            config.setModel(originalModel);
        }
    }

    /**
     * 执行API请求
     * Execute the API request
     *
     * @param chatRequest 聊天请求对象
     *                    The chat request object
     * @param url 完整的API URL
     *            The complete API URL
     * @return ChatResponse
     * @throws IOException 如果API调用失败
     *                     if the API call fails
     */
    private ChatResponse executeRequest(ChatRequest chatRequest, String url) throws IOException {
        if (chatRequest == null) {
            throw new IllegalArgumentException("ChatRequest cannot be null");
        }
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        String requestBody = JSONObject.toJSONString(chatRequest);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(getHeaders()))
                .post(RequestBody.create(requestBody, JSON))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected response " + response);
            }
            
            String responseBody = response.body().string();
            if (StringUtils.isBlank(responseBody)) {
                throw new IOException("Empty response body");
            }
            
            JSONObject jsonResponse = JSONObject.parseObject(responseBody);
            return parseResponse(jsonResponse);
        }
    }

    /**
     * 构建完整的 API URL
     * Build the complete API URL by combining endpoint and chat completion path
     *
     * @return 完整的 API URL
     *         The complete API URL
     */
    protected String buildUrl() {
        String baseUrl = config.getEndpoint();
        // 如果 baseUrl 以 / 结尾，则移除
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl + CHAT_COMPLETION_PATH;
    }

    /**
     * 获取API请求所需的headers
     * Get the headers required for the API request
     *
     * @return headers键值对
     *         Map of header key-value pairs
     */
    protected Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + config.getApiKey());
        headers.put("Content-Type", "application/json");
        return headers;
    }

    /**
     * 使用自定义消息列表创建聊天请求
     * Create a chat request with custom message list
     *
     * @param messages 消息列表
     *                List of messages to include in the request
     * @return ChatRequest
     */
    protected abstract ChatRequest createChatRequestWithMessages(List<Message> messages);

    /**
     * 使用系统消息和用户消息创建聊天请求
     * Create a chat request with system and user messages
     *
     * @param userMessage 用户消息
     *                    The message from user
     * @param systemMessage 系统消息
     *                      The system instruction message
     * @return ChatRequest
     */
    protected abstract ChatRequest createChatRequest(String userMessage, String systemMessage);

    /**
     * 解析API响应
     * Parse the API response
     *
     * @param response JSON响应
     *                 JSON response from the API
     * @return ChatResponse
     */
    protected abstract ChatResponse parseResponse(JSONObject response);
} 