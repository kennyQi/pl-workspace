import hg.common.util.DateUtil;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import slfx.xl.app.service.local.SalePolicyLineLocalService;
import slfx.xl.pojo.command.salepolicy.CreateSalePolicyCommand;
import slfx.xl.pojo.dto.salepolicy.SalePolicyLineDTO;

/**
 * @类功能说明：测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-xl-test.xml" })
public class JunitTestRun {
	@Autowired
	private SalePolicyLineLocalService policySer;
	
	@Test
	public void hello(){
		System.out.println("hello begin\n\n\n\n\n\n\n\n\n\n");
		
		String[] ids = {
			"98fc2a9610a94b86976179e8d46c56b4",
			"44dbfe0299f44bafba02ff2022ecc45c",
			"62ea9aa0324048ebbc66f23ce41c0e30",
			"8df17450568f42d9ac4e66ad2cd3845a",
			"c72f798388a84a2b9821b292433018fb",
			"dd996cd5a2914b6388e24dab19ba0422"
		};
		
		CreateSalePolicyCommand command = new CreateSalePolicyCommand();
		command.setName("AAAAA");
		command.setLineIds(Arrays.asList(ids));
		command.setDealerId("3fe6a93885124d24b23dd5bcf769013c");
		command.setStartDate(new Date());
		command.setEndDate(DateUtil.parseDate3("2015-01-01"));
		command.setPriceType(1);
		command.setRise(false);
		command.setUnit(2);
		command.setImprovePrice(10.0);
		command.setCreateName("admin");
		
		try {
			
			SalePolicyLineDTO policyDto = policySer.create(command);
			
			System.out.println("\n\n\n\n\n\n\n\n\n\n打印\n\n\n\n\n\n\n\n\n\n");
			System.out.println(JSON.toJSONString(policyDto));
//			SalePolicyLineQO qo = new SalePolicyLineQO();
//			List<SalePolicy> policyList = policySer.queryList(qo);
//			System.out.println(JSON.toJSONString(policyList));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\n\n\n\n\n\n\n\n\nhello end\n\n\n\n\n\n\n\n\n\n");
	}
}