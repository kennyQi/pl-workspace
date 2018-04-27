package hsl.account;
import hg.common.page.Pagination;
import hsl.app.service.local.account.AccountLocalService;
import hsl.pojo.command.account.BusinessPartnersCommand;
import hsl.pojo.command.account.RefundCommand;
import hsl.pojo.dto.account.BusinessPartnersDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.qo.account.BusinessPartnersQO;
import hsl.spi.inter.account.BusinessPartnersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class BusinessPartnersTest {
	@Autowired
	private BusinessPartnersService businessPartnersService;
	@Autowired
	private AccountLocalService accountLocalService;
	@Test
	public void test(){
		RefundCommand command=new RefundCommand();
		command.setAccountId("9778e9b6621046759890dc01435b659b");
		command.setAccountConsumeSnapshotId("0ce89ad8436446fba2b70bfa18230c0b");
		command.setOrderID("B925141626000000");
		command.setPayPrice(2750.00);
		command.setRefundMoney(2750.00);
		command.setRefundOrderId("cef8aa20a19a4e6a80bfb19991c6a7f0");
		try {
			accountLocalService.refundAccount(command);
		} catch (AccountException e) {
			e.printStackTrace();
		}
	}
//	@Test
//	public void queryBusinessPartners(){
//		try {
//			BusinessPartnersQO businessPartnersQO=new BusinessPartnersQO();
//			businessPartnersQO.setCompanyName("票量科技");
//			Pagination pagination=new Pagination();
//			pagination.setPageNo(1);
//			pagination.setPageSize(2);
//			pagination.setCondition(businessPartnersQO);
//			pagination.setCheckPassLastPageNo(false);
//			pagination=businessPartnersService.queryPagination(pagination);
//			System.out.println(JSON.toJSONString(pagination));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	@Test
//	public void updateBusinessPartners(){
//		try {
//			BusinessPartnersCommand businessPartnersCommand=new BusinessPartnersCommand();
//			businessPartnersCommand.setCommandId("8fcd8e48479b496789324f6edce17c9b");
//			businessPartnersCommand.setCompanyName("汇购");
//			String msg=businessPartnersService.updateBusinessPartners(businessPartnersCommand);
//			System.out.println(msg);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
