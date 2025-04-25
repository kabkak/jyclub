package com.jiangying.controller;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Ai")
public class AiController {
    private final OpenAiChatModel openAiChatModel;

    public AiController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }


    @GetMapping("/test")
    public String test() {
        return openAiChatModel.chat("你好");
    }
}
