import com.jiangying.AiApplication;
import com.jiangying.service.MockInterviewsFluxAgent;
import com.jiangying.service.MockInterviewsAssistant;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest(classes = AiApplication.class)
public class Tests {
    @Resource
    MockInterviewsAssistant mockInterviewsAssistant;
    @Resource
    MockInterviewsFluxAgent mockInterviewsAgent;

    @Test
    public void test() {
        String chat = mockInterviewsAssistant.chat(2, "475695037565 的平方根是多少？");
        Flux<String> chat1 = mockInterviewsAgent.chat(3, "475695037565 的平方根是多少？");
        System.out.println("没有使用函数调用:  " + chat);
        System.out.println("使用函数调用:  " + chat1);
    }
}