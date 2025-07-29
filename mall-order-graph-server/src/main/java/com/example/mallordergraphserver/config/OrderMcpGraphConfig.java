package com.example.mallordergraphserver.config;


import com.alibaba.cloud.ai.graph.GraphRepresentation;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;

import com.example.mallordergraphserver.node.McpNode;
import com.example.mallordergraphserver.tools.MallMcpClientToolCallbackProvider;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static com.alibaba.cloud.ai.graph.action.AsyncNodeAction.node_async;

/*
*  执行图
* */
@Configuration
@EnableConfigurationProperties({OrderMcpNodeProperties.class})
public class OrderMcpGraphConfig {

   @Autowired
   private MallMcpClientToolCallbackProvider mallMcpClientToolCallbackProvider;

   @Bean
    public StateGraph mcpGraph(ChatClient.Builder chatClientBuilder) throws GraphStateException {
      KeyStrategyFactory keyStrategyFactory=()->{
         HashMap<String, KeyStrategy> keyStrategyMap=new HashMap<>();
         //用户输入
         keyStrategyMap.put("query",new ReplaceStrategy());
         keyStrategyMap.put("mcpcontent",new ReplaceStrategy());
         return keyStrategyMap;
      };
      StateGraph stateGraph=new StateGraph(keyStrategyFactory)
              .addNode("mcp", node_async(new McpNode(chatClientBuilder, mallMcpClientToolCallbackProvider)))
              .addEdge(StateGraph.START,"mcp")
              .addEdge("mcp",StateGraph.END);
      //添加打印PlantUML打印信息
      GraphRepresentation representation=stateGraph.getGraph(GraphRepresentation.Type.PLANTUML,"mcp flow");

      return stateGraph;

   }

}
