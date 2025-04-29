package com.osq.websockets.chat.services;

import com.osq.websockets.chat.models.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private List<Message> messages = new ArrayList<>();

    @Override
    public List<Message> findAll() {
        return this.messages;
    }

    @Override
    public void save(Message message) {
        this.messages.add(message);
    }
}
