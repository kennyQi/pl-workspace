package hg.demo.test;

import hg.framework.common.model.Pagination;
import hg.fx.domain.MileOrder;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.qo.MileOrderSQO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author zqq
 * @date 2016-6-2上午10:15:54
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class MileOrderlSPIServiceTest {

	@Autowired
	private MileOrderSPI mileOrderService;

	@Test
	public void testQueryPagination() {
		MileOrderSQO sqo = new MileOrderSQO();
		Pagination<MileOrder> p = new Pagination<>();
		p = mileOrderService.queryPagination(sqo);
//		System.out.println(p.getList().size());
		for(int i= 0;i<p.getList().size();i++){
//			System.out.println(p.getList().get(i).getId()+"**"+p.getList().get(i).getProduct().getId());
		}
	}


}
