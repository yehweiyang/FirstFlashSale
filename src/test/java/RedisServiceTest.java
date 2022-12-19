import com.weiyang.FirstFlashSaleApplication;
import com.weiyang.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = FirstFlashSaleApplication.class)
public class RedisServiceTest {
    @Resource
    private RedisService redisService;

    @Test
    public void stockTest() {
        redisService.setValue("stock:19", 1L);
    }

    @Test
    public void getStockTest() {
        String stock = redisService.getValue("stock:19");
        System.out.println(stock);
    }

    @Test
    public void stockDeductValidatorTest(){
        boolean result = redisService.stockDeductValidator("stock:19");
        System.out.println("result:"+result);
        String stock = redisService.getValue("stock:19");
        System.out.println("stock:"+stock);
    }

}
