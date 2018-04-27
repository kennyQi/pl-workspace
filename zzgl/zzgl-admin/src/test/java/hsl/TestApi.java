package hsl;

import hsl.admin.controller.api.ApiController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @类功能说明：测试api的测试类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/8 14:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestApi {
	@Autowired
	private ApiController apiController;
	@Test
	public void testGetBatchNo(){
		String sq=apiController.getBatchNo("jp");
		System.out.println(sq);
	}
}
