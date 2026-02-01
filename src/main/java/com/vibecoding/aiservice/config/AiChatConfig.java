package com.vibecoding.aiservice.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AiChatConfig {

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    @Bean
    public ChatClient chatClient() {
        // Create OpenAiApi client with Groq config
        OpenAiApi openAiApi = new OpenAiApi(baseUrl);

        OpenAiChatOptions options=new OpenAiChatOptions();
        options.setModel(model
        );
        // Create the chat model
        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, options);

        // Build and return ChatClient
        return ChatClient.builder(chatModel).build();
    }
}