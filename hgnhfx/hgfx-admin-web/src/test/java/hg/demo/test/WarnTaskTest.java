package hg.demo.test;

import hg.demo.web.task.WarnTask;
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
 * @author xinglj
 * @date 2016-6-2上午10:15:54
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class WarnTaskTest {

	@Autowired
	WarnTask task;
	
	@Test
	public void testWarnSms(){
		task.doWarn();
	}


}
