package com.websocket.chatapp.chat;

import com.websocket.chatapp.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessages save(ChatMessages chatMessages) {
        var chatId= chatRoomService.getChatRoomId(chatMessages.getSenderId(),chatMessages.getReceiverId(),true)
                .orElseThrow();
        chatMessages.setChatId(chatId);
        return chatMessageRepository.save(chatMessages);
    }

    public List<ChatMessages> findChatMessages(String senderId, String receiverId) {
        var chatId= chatRoomService.getChatRoomId(senderId,receiverId,false);
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }
}
