package tech.infantai.deepseek_sdk.config;

import org.apache.commons.lang3.StringUtils;

public class GPTConfig {
    private String apiKey;
    private String endpoint;
    private String model;
    private int connectTimeout = 60; // 连接超时时间（秒）
    private int readTimeout = 60;    // 读取超时时间（秒）
    private int writeTimeout = 60;   // 写入超时时间（秒）
    
    // 私有构造函数
    private GPTConfig(Builder builder) {
        this.apiKey = builder.apiKey;
        this.endpoint = builder.endpoint;
        this.model = builder.model;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public String getApiKey() {
        return apiKey;
    }
    
    public String getEndpoint() {
        return endpoint;
    }
    
    public String getModel() {
        return model;
    }
    
    public int getConnectTimeout() {
        return connectTimeout;
    }
    
    public void setConnectTimeout(int connectTimeout) {
        if (connectTimeout <= 0) {
            throw new IllegalArgumentException("Connect timeout must be greater than 0");
        }
        this.connectTimeout = connectTimeout;
    }
    
    public int getReadTimeout() {
        return readTimeout;
    }
    
    public void setReadTimeout(int readTimeout) {
        if (readTimeout <= 0) {
            throw new IllegalArgumentException("Read timeout must be greater than 0");
        }
        this.readTimeout = readTimeout;
    }
    
    public int getWriteTimeout() {
        return writeTimeout;
    }
    
    public void setWriteTimeout(int writeTimeout) {
        if (writeTimeout <= 0) {
            throw new IllegalArgumentException("Write timeout must be greater than 0");
        }
        this.writeTimeout = writeTimeout;
    }
    
    public void setModel(String model) {
        if (StringUtils.isBlank(model)) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        this.model = model;
    }
    
    // 链式调用方法
    public GPTConfig withConnectTimeout(int connectTimeout) {
        setConnectTimeout(connectTimeout);
        return this;
    }
    
    public GPTConfig withReadTimeout(int readTimeout) {
        setReadTimeout(readTimeout);
        return this;
    }
    
    public GPTConfig withWriteTimeout(int writeTimeout) {
        setWriteTimeout(writeTimeout);
        return this;
    }
    
    public GPTConfig withModel(String model) {
        setModel(model);
        return this;
    }

    /**
     * Builder class for GPTConfig
     * GPTConfig的构建器类
     */
    public static class Builder {
        private String apiKey;
        private String endpoint;
        private String model;
        private int connectTimeout = 60;  // Default connection timeout (seconds) / 默认连接超时时间（秒）
        private int readTimeout = 60;     // Default read timeout (seconds) / 默认读取超时时间（秒）
        private int writeTimeout = 60;    // Default write timeout (seconds) / 默认写入超时时间（秒）
        
        /**
         * Sets the API key
         * 设置API密钥
         * @param apiKey The API key / API密钥
         * @throws IllegalArgumentException if apiKey is null or empty / 如果apiKey为空则抛出异常
         */
        public Builder apiKey(String apiKey) {
            if (StringUtils.isBlank(apiKey)) {
                throw new IllegalArgumentException("API key cannot be null or empty");
            }
            this.apiKey = apiKey;
            return this;
        }
        
        /**
         * Sets the API endpoint
         * 设置API端点
         * @param endpoint The API endpoint URL / API端点URL
         * @throws IllegalArgumentException if endpoint is null or empty / 如果endpoint为空则抛出异常
         */
        public Builder endpoint(String endpoint) {
            if (StringUtils.isBlank(endpoint)) {
                throw new IllegalArgumentException("Endpoint cannot be null or empty");
            }
            this.endpoint = endpoint;
            return this;
        }
        
        /**
         * Sets the model name
         * 设置模型名称
         * @param model The model name / 模型名称
         * @throws IllegalArgumentException if model is null or empty / 如果model为空则抛出异常
         */
        public Builder model(String model) {
            if (StringUtils.isBlank(model)) {
                throw new IllegalArgumentException("Model cannot be null or empty");
            }
            this.model = model;
            return this;
        }
        
        /**
         * Sets the connection timeout
         * 设置连接超时时间
         * @param connectTimeout timeout in seconds / 超时时间（秒）
         * @throws IllegalArgumentException if timeout is less than or equal to 0 / 如果超时时间小于等于0则抛出异常
         */
        public Builder connectTimeout(int connectTimeout) {
            if (connectTimeout <= 0) {
                throw new IllegalArgumentException("Connect timeout must be greater than 0");
            }
            this.connectTimeout = connectTimeout;
            return this;
        }
        
        /**
         * Sets the read timeout
         * 设置读取超时时间
         * @param readTimeout timeout in seconds / 超时时间（秒）
         * @throws IllegalArgumentException if timeout is less than or equal to 0 / 如果超时时间小于等于0则抛出异常
         */
        public Builder readTimeout(int readTimeout) {
            if (readTimeout <= 0) {
                throw new IllegalArgumentException("Read timeout must be greater than 0");
            }
            this.readTimeout = readTimeout;
            return this;
        }
        
        /**
         * Sets the write timeout
         * 设置写入超时时间
         * @param writeTimeout timeout in seconds / 超时时间（秒）
         * @throws IllegalArgumentException if timeout is less than or equal to 0 / 如果超时时间小于等于0则抛出异常
         */
        public Builder writeTimeout(int writeTimeout) {
            if (writeTimeout <= 0) {
                throw new IllegalArgumentException("Write timeout must be greater than 0");
            }
            this.writeTimeout = writeTimeout;
            return this;
        }
        
        /**
         * Builds the GPTConfig instance
         * 构建GPTConfig实例
         * @throws IllegalArgumentException if required parameters are missing / 如果缺少必要参数则抛出异常
         */
        public GPTConfig build() {
            if (StringUtils.isBlank(apiKey)) {
                throw new IllegalArgumentException("API key must be set");
            }
            if (StringUtils.isBlank(endpoint)) {
                throw new IllegalArgumentException("Endpoint must be set");
            }
            if (StringUtils.isBlank(model)) {
                throw new IllegalArgumentException("Model must be set");
            }
            return new GPTConfig(this);
        }
    }
} 