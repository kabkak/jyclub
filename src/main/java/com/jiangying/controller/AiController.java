package com.jiangying.controller;


import com.jiangying.service.MockInterviewsAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController("Ai")
public class AiController {


    @Resource
    MockInterviewsAgent mockInterviewsAgent;

    @GetMapping("/test")
    public String test() {
        String chat = mockInterviewsAgent.chat(1, "475695037565 的平方根是多少？");
        return chat;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("memoryId") int memoryId, @RequestParam("message") String message) {
        long useId = 1;
        //去查数据库是否有memoryId 为空则新建
        //memoryId = UUID.randomUUID();

        String chat = mockInterviewsAgent.chat(memoryId, message);
        return chat;
    }

}
