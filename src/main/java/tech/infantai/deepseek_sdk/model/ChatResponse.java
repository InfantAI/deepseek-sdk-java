package tech.infantai.deepseek_sdk.model;

public class ChatResponse {
    private String content;
    private ChatCompletionResponse rawResponse;
    private ErrorResponse errorResponse;
    private boolean success;

    public ChatResponse(String content, ChatCompletionResponse rawResponse) {
        this.content = content;
        this.rawResponse = rawResponse;
        this.success = true;
    }

    public ChatResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        this.success = false;
    }

    public String getContent() {
        return content;
    }

    public ChatCompletionResponse getRawResponse() {
        return rawResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 获取错误信息
     * @return 如果是错误响应则返回错误信息，否则返回null
     */
    public String getErrorMessage() {
        if (!success && errorResponse != null && errorResponse.getError() != null) {
            return errorResponse.getError().getMessage();
        }
        return null;
    }
} 