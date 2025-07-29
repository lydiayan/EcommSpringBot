package com.example.mallordergraphserver.node;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.example.mallordergraphserver.tools.MallMcpClientToolCallbackProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;

import java.util.Map;
import java.util.Set;

public class McpNode implements NodeAction {
    private static Logger logger = LoggerFactory.getLogger(McpNode.class);

    private static final String NODE_NAME = "mcp-node";

    private final ChatClient chatClient;

    public McpNode(ChatClient.Builder chatClientBuilder, MallMcpClientToolCallbackProvider mallMcpClientToolCallbackProvider) {
        Set<ToolCallback> toolCallbacks = mallMcpClientToolCallbackProvider.getToolCallbacks(NODE_NAME);
        for (ToolCallback toolCallback : toolCallbacks) {
            logger.info("Mcp Node load ToolCallback: " + toolCallback.getToolDefinition().name());
        }
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        return Map.of();
    }
}
