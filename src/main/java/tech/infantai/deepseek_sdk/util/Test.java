package tech.infantai.deepseek_sdk.util;

import tech.infantai.deepseek_sdk.config.GPTConfig;
import tech.infantai.deepseek_sdk.model.ChatRequest;
import tech.infantai.deepseek_sdk.model.Message;
import tech.infantai.deepseek_sdk.service.DeepseekAIService;

public class Test {
    public static void main(String[] args) {
        GPTConfig config = GPTConfig.builder()
                .apiKey("your-api-key")
                .build();
        // 创建服务实例
        DeepseekAIService service = new DeepseekAIService(config);
        // 创建对话请求
        ChatRequest request = ChatRequest.builder()
                .message(new Message("user", "你好，请介绍一下你自己"))
                .build();
        // 发送请求
        String response = service.chat(request);
        System.out.println(response);
    }
}
