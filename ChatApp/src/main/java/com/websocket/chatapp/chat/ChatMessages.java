package com.websocket.chatapp.chat;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatMessages {
    private String id;
    private String senderId;
    private String receiverId;
    private String chatId;
    private String content;
    private Date timeStamp;
}
