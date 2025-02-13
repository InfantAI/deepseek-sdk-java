package tech.infantai.deepseek_sdk.model;

import org.apache.commons.lang3.StringUtils;

public class Message {
    private String role;
    private String content;

    /**
     * Creates a new Message instance
     * 创建新的Message实例
     * @param role The role of the message sender (e.g., "user", "system") / 消息发送者的角色（如"user"、"system"）
     * @param content The message content / 消息内容
     * @throws IllegalArgumentException if role or content is null or empty / 如果角色或内容为空则抛出异常
     */
    public Message(String role, String content) {
        if (StringUtils.isBlank(role)) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        if (StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.role = role;
        this.content = content;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
} 