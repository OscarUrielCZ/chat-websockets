package com.osq.websockets.chat.repositories;

import com.osq.websockets.chat.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageMongoRepository extends MongoRepository<Message, String> {
    public List<Message> findFirst10ByOrderByDateAsc();
}
