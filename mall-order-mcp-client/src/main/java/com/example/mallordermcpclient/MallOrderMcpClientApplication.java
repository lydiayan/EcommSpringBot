package com.example.mallordermcpclient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MallOrderMcpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderMcpClientApplication.class, args);
    }


    // 直接硬编码中文问题，避免配置文件编码问题
    // @Value("${ai.user.input}")
    // private String userInput;
    private String userInput = "获取所有订单信息";

    @Bean
    public CommandLineRunner predefinedQuestions(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools,
                                                 ConfigurableApplicationContext context) {

        return args -> {

            var chatClient = chatClientBuilder
                    .defaultToolCallbacks(tools)
                    .build();

            System.out.println("\n>>> QUESTION: " + userInput);
            System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());

           System.out.println("\n>>> QUESTION: " + "获取用户id为USER1040的订单");
            System.out.println("\n>>> ASSISTANT: " + chatClient.prompt("获取用户id为USER1040的订单").call().content());

//            context.close();
        };
    }

}
