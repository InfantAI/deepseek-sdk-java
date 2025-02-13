package tech.infantai.deepseek_sdk.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ChatRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer maxTokens;
    private Integer promptCacheHitTokens;
    private Integer promptCacheMissTokens;
    private String systemFingerprint;
    private Message message;

    public ChatRequest(String model) {
        this.model = model;
        this.messages = new ArrayList<>();
        
    }

    public void addUserMessage(String content) {
        this.messages.add(new Message("user", content));
    }
    
    public void addSystemMessage(String content) {
        this.messages.add(new Message("system", content));
    }

    /**
     * 添加任意类型的消息
     * @param message 消息对象
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Integer getPromptCacheHitTokens() {
        return promptCacheHitTokens;
    }

    public void setPromptCacheHitTokens(Integer promptCacheHitTokens) {
        this.promptCacheHitTokens = promptCacheHitTokens;
    }

    public Integer getPromptCacheMissTokens() {
        return promptCacheMissTokens;
    }

    public void setPromptCacheMissTokens(Integer promptCacheMissTokens) {
        this.promptCacheMissTokens = promptCacheMissTokens;
    }

    public String getSystemFingerprint() {
        return systemFingerprint;
    }

    public void setSystemFingerprint(String systemFingerprint) {
        this.systemFingerprint = systemFingerprint;
    }

    public Message getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for ChatRequest
     * ChatRequest的构建器类
     */
    public static class Builder {
        private String model;
        private Message message;
        private Double temperature;    // Temperature for response generation / 响应生成的温度参数
        private Integer maxTokens;     // Maximum tokens in response / 响应中的最大标记数
        private Integer promptCacheHitTokens;    // Cache hit tokens / 缓存命中的标记数
        private Integer promptCacheMissTokens;   // Cache miss tokens / 缓存未命中的标记数
        private String systemFingerprint;        // System fingerprint / 系统指纹
        
        /**
         * Sets the message
         * 设置消息
         * @param message The message object / 消息对象
         * @throws IllegalArgumentException if message is null / 如果消息为空则抛出异常
         */
        public Builder message(Message message) {
            if (message == null) {
                throw new IllegalArgumentException("Message cannot be null");
            }
            this.message = message;
            return this;
        }
        
        /**
         * Sets the model name
         * 设置模型名称
         * @param model The model name / 模型名称
         * @throws IllegalArgumentException if model is null or empty / 如果模型为空则抛出异常
         */
        public Builder model(String model) {
            if (StringUtils.isBlank(model)) {
                throw new IllegalArgumentException("Model cannot be null or empty");
            }
            this.model = model;
            return this;
        }
        
        /**
         * Sets the temperature parameter
         * 设置温度参数
         * @param temperature The temperature value / 温度值
         */
        public Builder temperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }
        
        /**
         * Sets the maximum tokens
         * 设置最大标记数
         * @param maxTokens The maximum number of tokens / 最大标记数
         * @throws IllegalArgumentException if maxTokens is less than or equal to 0 / 如果最大标记数小于等于0则抛出异常
         */
        public Builder maxTokens(Integer maxTokens) {
            if (maxTokens != null && maxTokens <= 0) {
                throw new IllegalArgumentException("Max tokens must be greater than 0");
            }
            this.maxTokens = maxTokens;
            return this;
        }
        
        /**
         * Builds the ChatRequest instance
         * 构建ChatRequest实例
         * @throws IllegalArgumentException if required parameters are missing / 如果缺少必要参数则抛出异常
         */
        public ChatRequest build() {
            if (message == null) {
                throw new IllegalArgumentException("Message must be set");
            }
            if (StringUtils.isBlank(model)) {
                throw new IllegalArgumentException("Model must be set");
            }
            
            ChatRequest request = new ChatRequest(model);
            request.message = message;
            request.temperature = temperature;
            request.maxTokens = maxTokens;
            request.promptCacheHitTokens = promptCacheHitTokens;
            request.promptCacheMissTokens = promptCacheMissTokens;
            request.systemFingerprint = systemFingerprint;
            request.messages = new ArrayList<>();
            request.messages.add(message);
            
            return request;
        }
    }
} 