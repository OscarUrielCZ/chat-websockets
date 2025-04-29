package com.osq.websockets.chat.controllers;

import com.osq.websockets.chat.models.Message;
import com.osq.websockets.chat.models.MessageType;
import com.osq.websockets.chat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {
    private static final String[] colors = new String[] {
            "red",
            "blue",
            "magenta",
            "orange",
            "green",
            "yellow",
            "cyan",
            "purple",
            "brown",
            "black"
    };

    private final MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // inject message mongo service, we must specify in case there are more than 1 MessageService implementations
    public ChatController(@Qualifier("messageMongoService") MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/message") // where user calls request to send message
    @SendTo("/roomA/message") // where response method goes to
    public Message receiveMessage(Message gossip) {
        System.out.println("Received: " + gossip.getType());

        gossip.setDate(new Date().getTime());

        if (gossip.getType() == MessageType.join) {
            String randomColor = colors[(int) (Math.random() * colors.length)];
            gossip.setUsername("@" + gossip.getUsername());
            gossip.setTextColor(randomColor);
            gossip.setContent(gossip.getUsername());
        } else if (gossip.getType() == MessageType.message) {
            messageService.save(gossip);
        }

        return gossip;
    }

    @MessageMapping("/writing")
    @SendTo("/roomA/message")
    public Message writing(String username) {
        Message message = new Message();
        message.setType(MessageType.writing);
        message.setUsername(username);
        message.setContent(username.concat(" está escribiendo..."));

        return message;
    }

    @MessageMapping("/get-history")
    public void getHistory(String userId) {
        messagingTemplate.convertAndSend("/roomA/history/".concat(userId), messageService.findAll());
    }
}
