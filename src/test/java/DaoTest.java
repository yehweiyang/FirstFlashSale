import com.weiyang.FirstFlashSaleApplication;
import com.weiyang.db.mappers.FlashSaleActivityMapper;
import com.weiyang.db.po.FlashSaleActivity;
import com.weiyang.db.dao.FlashSaleActivityDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest(classes = FirstFlashSaleApplication.class)
public class DaoTest {
    @Resource
    private FlashSaleActivityMapper flashSaleActivityMapper;
    @Autowired
    private FlashSaleActivityDao flashSaleActivityDao;

    @Test
    void FlashSaleActivityTest() {
        FlashSaleActivity flashSaleActivity = new FlashSaleActivity();
        flashSaleActivity.setName("测试");
        flashSaleActivity.setCommodityId(999L);
        flashSaleActivity.setTotalStock(100L);
        flashSaleActivity.setFlashSalePrice(new BigDecimal(99));
        flashSaleActivity.setActivityStatus(16);
        flashSaleActivity.setOldPrice(new BigDecimal(99));
        flashSaleActivity.setAvailableStock(100);
        flashSaleActivity.setLockStock(0L);
        flashSaleActivityMapper.insert(flashSaleActivity);
        System.out.println("====>>>>" +
                flashSaleActivityMapper.selectByPrimaryKey(1L));
    }

//    @Test
//    void setFlashSaleActivityQuery() {
//        List<FlashSaleActivity> flashSaleActivitys =
//                flashSaleActivityDao.queryFlashSaleActivitysByStatus(0);
//        System.out.println(flashSaleActivitys.size());
//        flashSaleActivitys.forEach(flashSaleActivity ->
//                System.out.println(flashSaleActivity.toString()));
//    }
}
