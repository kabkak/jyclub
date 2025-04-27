package com.jiangying.store;

import com.jiangying.pojo.entry.InterviewsMemory;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        InterviewsMemory interviewsMemory = mongoTemplate.findOne(new Query(Criteria.where("memoryId").is(memoryId)), InterviewsMemory.class);
        if (interviewsMemory == null) {
            return new LinkedList<>();
        }
        String memoriesString = interviewsMemory.getContent();
        if (memoriesString == null) {
            return new LinkedList<>();
        }
        return ChatMessageDeserializer.messagesFromJson(memoriesString);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memoryId").is(memoryId));
        Update update = new Update();
        update.set("content", ChatMessageSerializer.messagesToJson(messages));
        mongoTemplate.upsert(query, update, InterviewsMemory.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        mongoTemplate.remove(new Query(Criteria.where("memoryId").is(memoryId)), InterviewsMemory.class);
    }
}
