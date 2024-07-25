package com.websocket.chatapp.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessages chatMessages){
         ChatMessages savedMessage = chatMessageService.save(chatMessages);
         messagingTemplate.convertAndSendToUser(chatMessages.getReceiverId(),"/queue/messages",
                 ChatNotification.builder()
                         .id(savedMessage.getId())
                         .senderId(savedMessage.getSenderId())
                         .receiverId(savedMessage.getReceiverId())
                         .content(savedMessage.getContent())
                         .build());
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessages>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("receiverId") String receiverId
    ){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, receiverId));
    }
}
