package com.example.chat.controller;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatMessage.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ChatController}.
 */
class ChatControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage_ShouldBroadcastMessage() {
        ChatMessage inbound = ChatMessage.builder()
                .type(MessageType.CHAT)
                .content("Hello")
                .sender("alice")
                .build();

        chatController.sendMessage(inbound, mock(SimpMessageHeaderAccessor.class));

        // Verify that timestamp is set and message is sent to the correct destination
        verify(messagingTemplate, times(1))
                .convertAndSend(eq("/topic/public"), argThat((ChatMessage msg) ->
                        msg.getSender().equals("alice") &&
                        msg.getContent().equals("Hello") &&
                        msg.getTimestamp() != null));
    }

// kept it simple here
    @Test
    void addUser_ShouldRegisterAndBroadcastJoin() {
        SimpMessageHeaderAccessor accessor = mock(SimpMessageHeaderAccessor.class);
        when(accessor.getSessionAttributes()).thenReturn(new java.util.HashMap<>());

        ChatMessage inbound = ChatMessage.builder()
                .sender("bob")
                .build();

        chatController.addUser(inbound, accessor);

        verify(accessor.getSessionAttributes(), times(1)).put(eq("username"), eq("bob"));
        verify(messagingTemplate, times(1))
                .convertAndSend(eq("/topic/public"), argThat((ChatMessage msg) ->
                        msg.getSender().equals("bob") &&
                        msg.getType() == MessageType.JOIN &&
                        msg.getTimestamp() != null));
    }
}