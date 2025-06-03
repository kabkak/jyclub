package com.jiangying.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
//        chatModel = "openAiChatModel",
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "mathCalculator",
        contentRetriever = "createContentRetriever"
)
public interface MockInterviewsFluxAgent {

    @SystemMessage(fromResource = "mock-interviews.text")
    Flux<String> chat(@MemoryId int memoryId, @UserMessage String userMessage);

}