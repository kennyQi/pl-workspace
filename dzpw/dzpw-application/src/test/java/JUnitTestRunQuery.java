import hg.common.page.Pagination;
import hg.dzpw.app.service.local.FinanceManagementLocalService;
import hg.dzpw.pojo.exception.DZPWException;
import hg.dzpw.pojo.qo.ReconciliationCollectOrderQo;
import hg.dzpw.pojo.qo.ReconciliationOrderQo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRunQuery {

	@Autowired
	private FinanceManagementLocalService financeManagementLocalService;
	
	@Test
	public void testQueryReconciliationCollectOrder() throws DZPWException {
		ReconciliationCollectOrderQo qo = new ReconciliationCollectOrderQo();
		Pagination pagination = financeManagementLocalService
				.queryReconciliationCollectOrder(qo);
		System.out.println(JSON.toJSONString(pagination, true));
	}

	@Test
	public void testQueryReconciliationOrder() throws DZPWException {
		ReconciliationOrderQo qo = new ReconciliationOrderQo();
		Pagination pagination = financeManagementLocalService
				.queryReconciliationOrder(qo);
		System.out.println(JSON.toJSONString(pagination, true));
	}
	
}
