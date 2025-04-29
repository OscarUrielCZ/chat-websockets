package com.osq.websockets.chat.services;

import com.osq.websockets.chat.models.Message;
import com.osq.websockets.chat.repositories.MessageMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageMongoService implements MessageService {

    private final MessageMongoRepository messageRepository;

    public MessageMongoService(MessageMongoRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findFirst10ByOrderByDateAsc();
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
