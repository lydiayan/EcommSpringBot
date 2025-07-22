package com.example.mallordermcpclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;

public class MallOrderMcpClient {

    private final ChatClient chatClient;

    public MallOrderMcpClient(ChatClient.Builder chatClientBuilder, List<ToolCallback> tools) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(tools)
                .build();
    }

    public String chat(String message) {
        return chatClient.prompt(message).call().content();
        //return chatClient.chat(message, sessionId).content().toString();
    }
}
