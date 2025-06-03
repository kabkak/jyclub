package com.jiangying.controller;


import com.jiangying.service.MockInterviewsFluxAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author jiangying
 * 流式对话接口
 */
@RestController()
@RequestMapping("/AiFlux")
public class AiFluxController {


    @Resource
    MockInterviewsFluxAgent mockInterviewsAgent;

    @GetMapping("/test")
    public Flux<String> test() {
        Flux<String> chat = mockInterviewsAgent.chat(1, "475695037565 的平方根是多少？");
        return chat;
    }

    /**
     * 模拟面试
     *
     * @param memoryId
     * @param message
     * @return
     */
    @GetMapping("/chatFlux")
    public Flux<String> chat(@RequestParam("memoryId") int memoryId, @RequestParam("message") String message) {
        long useId = 1;
        //去查数据库是否有memoryId 为空则新建
        //memoryId = UUID.randomUUID();

        Flux<String> chat = mockInterviewsAgent.chat(memoryId, message);
        return chat;
    }

}
