import com.weiyang.FirstFlashSaleApplication;
import com.weiyang.component.MyMessageConsumer;
import com.weiyang.component.RocketMQService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FirstFlashSaleApplication.class)
public class MyTest {

    @Autowired
    private RocketMQService messageProducer;

    @Autowired
    private MyMessageConsumer messageConsumer;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testProducerAndConsumer() throws Exception {
        // Send a message
        messageProducer.sendMessage("my-topic", "Hello, RocketMQ!");

        //rocketMQService.sendMessage("test-jiuzhang", "Hello World!" + new
        //Date().toString())

        // Wait for the message to be received
        Thread.sleep(1000);



        // Verify that the message was received
//        assertEquals("Received message: Hello, RocketMQ!", messageConsumer.onMessage("my-topic"));

    }
}
