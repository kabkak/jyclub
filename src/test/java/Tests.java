import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.jiangying.AiApplication;
import com.jiangying.constant.KeyConstant;
import com.jiangying.pojo.result.Result;
import com.jiangying.service.MockInterviewsFluxAgent;
import com.jiangying.service.MockInterviewsAssistant;

import jakarta.annotation.Resource;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.ProcessEngines;
//import org.activiti.engine.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Flux;

@SpringBootTest(classes = AiApplication.class)
public class Tests {
    @Resource
    RedisTemplate redisTemplate;

//    @Resource
//    private RepositoryService repositoryService;
    @Test
    public void test() {
//        repositoryService.createDeployment().addClasspathResource("processes/mock-interviews.bpmn").deploy();

    }
}