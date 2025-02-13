# deepseek-sdk-java
A sdk for deepseek tools powerd by java

# deepseek-sdk-java

一个基于 Java 实现的 Deepseek AI 工具 SDK，支持与 Deepseek AI 模型进行交互。

## 功能特性

- 支持与 Deepseek AI 模型进行对话
- 简单易用的 API 接口
- 支持自定义配置参数
- 异步处理请求

## 快速开始

### Maven 依赖
xml
<dependency>
    <groupId>tech.infantai</groupId>
    <artifactId>deepseek-sdk</artifactId>
    <version>1.0.0</version>
</dependency>

### 使用示例
java
// 初始化配置
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