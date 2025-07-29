package com.example.mallordercmp_ssoclient.controller;

import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/agent")
public class MallAgentController {

    private final ChatClient chatClient;
    private final int MAXMESSAGES=100;
    private final MessageWindowChatMemory messageWindowChatMemory;

    private final ElasticsearchVectorStore vectorStore;


    public MallAgentController(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools, RedisChatMemoryRepository redisChatMemoryRepository,@Qualifier(value = "esVectorStore") ElasticsearchVectorStore vectorStore) {
        this.messageWindowChatMemory= MessageWindowChatMemory.builder().
        chatMemoryRepository(redisChatMemoryRepository)
                .maxMessages(MAXMESSAGES).build();
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(tools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build())
                .build();
    this.vectorStore = vectorStore;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        //return chatClient.prompt(message).call().content();
        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .build())
                .build();

        return chatClient.prompt(message)
                .advisors(retrievalAugmentationAdvisor)
                .call().content();
    }

    @GetMapping("call")
    public String call(@RequestParam(value = "query",defaultValue = "你好，我是商城助手") String query,@RequestParam(value = "conversationId",defaultValue = "zhushou") String conversationId) {
        return chatClient.prompt(query).advisors(a->a.param("CONVERSATIONID", conversationId)).call().content();
    }
}