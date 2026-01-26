package com.vibecoding.aiservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Service
public class ChatService {

    private final ChatClient chatClient;
    public ChatService(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }
    public void debugParameterNames() throws Exception {
        Method method = ChatService.class.getMethod("newChat", String.class);
        Parameter param = method.getParameters()[0];
        System.out.println("Parameter name: " + param.getName());  // should print "prompt"
    }
    @Cacheable(value = "aiResponses", key = "#p0")
    public String newChat(String propmt) throws Exception {
//        debugParameterNames();
        System.out.println("Groq called for prompt: " + propmt + " at " + java.time.LocalDateTime.now());
        String response=chatClient.prompt(propmt).call().content();
        return response;
    }
}
