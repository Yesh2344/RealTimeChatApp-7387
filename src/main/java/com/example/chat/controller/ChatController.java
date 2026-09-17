package com.example.chat.controller;

import com.example.chat.model.ChatMessage;
import com.example.chat.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Handles incoming WebSocket messages and broadcasts them to subscribed clients.
 */
@Slf4j
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Receives a chat message from a client and forwards it to the public topic.
     *
     * @param chatMessage The incoming message payload.
     * @param headerAccessor Provides access to session attributes.
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage,
                            SimpMessageHeaderAccessor headerAccessor) {
        try {
            chatMessage.setTimestamp(MessageUtils.now());
            log.info("Received message from {}: {}", chatMessage.getSender(), chatMessage.getContent());
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        } catch (Exception ex) {
            log.error("Failed to process chat message", ex);
            // In a production system you might send an error frame back to the client.
        }
    }

    /**
     * Registers a new user when they join the chat.
     *
     * @param chatMessage The message containing the username.
     * @param headerAccessor Session accessor to store the username.
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        try {
            String username = chatMessage.getSender();
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            headerAccessor.getSessionAttributes().put("username", username);
            chatMessage.setTimestamp(MessageUtils.now());
            chatMessage.setType(ChatMessage.MessageType.JOIN);
            log.info("User joined: {}", username);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        } catch (Exception ex) {
            log.error("Error adding user", ex);
        }
    }
}