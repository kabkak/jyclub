import com.jiangying.AiApplication;
import com.jiangying.service.MockInterviewsAgent;
import com.jiangying.service.MockInterviewsAssistant;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AiApplication.class)
public class Tests {
    @Resource
    MockInterviewsAssistant mockInterviewsAssistant;
    @Resource
    MockInterviewsAgent mockInterviewsAgent;

    @Test
    public void test() {
        String chat = mockInterviewsAssistant.chat(2, "475695037565 的平方根是多少？");
        String chat1 = mockInterviewsAgent.chat(3, "475695037565 的平方根是多少？");
        System.out.println(chat);
        System.out.println(chat1);
    }
}