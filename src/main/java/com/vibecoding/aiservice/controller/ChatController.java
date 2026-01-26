package com.vibecoding.aiservice.controller;

import com.vibecoding.aiservice.model.ChatRequest;
import com.vibecoding.aiservice.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ChatController {

    private final ChatService chatService;


    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping("chat/new")
   public org.springframework.http.ResponseEntity<String> newChat(@RequestBody ChatRequest model) throws Exception {
        return ResponseEntity.ok(chatService.newChat(model.propmt()));
   }
}
