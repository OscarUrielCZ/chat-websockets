package com.osq.websockets.chat.services;

import com.osq.websockets.chat.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> findAll();
    void save(Message message);
}
