package tech.infantai.deepseek_sdk.service;

import com.alibaba.fastjson.JSONObject;
import tech.infantai.deepseek_sdk.config.GPTConfig;
import tech.infantai.deepseek_sdk.model.ChatRequest;
import tech.infantai.deepseek_sdk.model.ChatResponse;
import tech.infantai.deepseek_sdk.model.ChatCompletionResponse;
import tech.infantai.deepseek_sdk.model.ErrorResponse;
import tech.infantai.deepseek_sdk.model.Message;

import java.util.List;

public class OpenAIService extends AbstractAIService {
    
    public OpenAIService(GPTConfig config) {
        super(config);
    }

    @Override
    protected ChatRequest createChatRequestWithMessages(List<Message> messages) {
        ChatRequest request = new ChatRequest(config.getModel());
        messages.forEach(request::addMessage);
        //request.setTemperature(0.7);
        //request.setMaxTokens(2000);
        return request;
    }

    @Override
    protected ChatRequest createChatRequest(String userMessage, String systemMessage) {
        ChatRequest request = new ChatRequest(config.getModel());
        request.addSystemMessage(systemMessage);
        request.addUserMessage(userMessage);
//        request.setTemperature(0.7);
//        request.setMaxTokens(2000);
        return request;
    }

    @Override
    protected ChatResponse parseResponse(JSONObject response) {
        // 检查是否存在错误响应
        if (response.containsKey("error")) {
            ErrorResponse errorResponse = JSONObject.toJavaObject(response, ErrorResponse.class);
            return new ChatResponse(errorResponse);
        }
        
        // 正常响应处理
        ChatCompletionResponse completionResponse = JSONObject.toJavaObject(response, ChatCompletionResponse.class);
        String content = completionResponse.getChoices().get(0).getMessage().getContent();
        return new ChatResponse(content, completionResponse);
    }
} 