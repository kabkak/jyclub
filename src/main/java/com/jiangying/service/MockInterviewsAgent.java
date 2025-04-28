package com.jiangying.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "mathCalculator"
)
public interface MockInterviewsAgent {

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

}