package com.example.mallordermcpclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MallOrderMcpClientConfig {

    @Bean
    public MallOrderMcpClient mallOrderMcpClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        return new MallOrderMcpClient(chatClientBuilder, Arrays.asList(toolCallbackProvider.getToolCallbacks()));
    }
}