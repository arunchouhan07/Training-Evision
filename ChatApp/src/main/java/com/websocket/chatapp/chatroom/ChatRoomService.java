package com.websocket.chatapp.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    public Optional<String> getChatRoomId(String senderId, String receiverId, boolean createNewRoomIfNotExists) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderId,receiverId)
                .map(ChatRoom::getChatId)
                .or(()->{
                    if (createNewRoomIfNotExists) {
                        var chatId= createChatId(senderId,receiverId);
                        return Optional.of(chatId);
                    }return Optional.empty();
                });
    }

    private String createChatId(String senderId, String receiverId) {
        var chatId=String.format("%s_%s", senderId, receiverId);
        ChatRoom senderReceiver=ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        ChatRoom receiverSender=ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();

        chatRoomRepository.save(senderReceiver);
        chatRoomRepository.save(receiverSender);

        return chatId;
    }
}
