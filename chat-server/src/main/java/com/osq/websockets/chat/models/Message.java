package com.osq.websockets.chat.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
@Setter @Getter
public class Message {
    @Id
    private String id;

    private Long date;
    private MessageType type;
    private String content;
    private String username;
    private String textColor;
}
