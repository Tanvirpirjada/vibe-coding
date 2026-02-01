package com.vibecoding.aiservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    private final RagService ragService;
    public ChatService(ChatClient.Builder chatClientBuilde, RagService ragService){
        this.chatClient =chatClientBuilde.build();
        this.ragService = ragService;
    }

    @Cacheable(value = "aiResponses", key = "#p0")
    public String newChat(String propmt) throws Exception {

        String context = ragService.getRelevantContext(propmt, 3);
        System.out.println("context :"+context);
        String fullPrompt = context.isEmpty()
                ? propmt
                : "Use this context to answer accurately:\n" + context + "\n\nQuestion: " + propmt;

        System.out.println("fullPromt : "+fullPrompt);
        String response=chatClient.prompt(fullPrompt).call().content();
        return response;
    }
}
