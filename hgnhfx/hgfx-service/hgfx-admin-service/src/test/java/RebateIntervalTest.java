import hg.fx.domain.rebate.RebateInterval;
import hg.fx.spi.RebateIntervalSPI;
import hg.fx.spi.qo.RebateIntervalSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class RebateIntervalTest {

	@Autowired
	private RebateIntervalSPI service;
	
	@Test
	public void queryPaging(){
		RebateIntervalSQO sqo = new RebateIntervalSQO();
		sqo.setProductId("337cc75220a24d81b0f53c996b8e7854");
		RebateInterval inter = service.queryUnique(sqo);
		//System.out.println(result.getList().size());
		System.out.println("aa");
	}
}
