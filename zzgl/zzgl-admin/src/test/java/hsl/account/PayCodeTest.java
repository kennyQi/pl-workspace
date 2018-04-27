package hsl.account;
import hg.common.page.Pagination;
import hsl.app.service.local.account.AccountLocalService;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.AccountConsumeSnapshotCommand;
import hsl.pojo.command.account.HoldUserSnapshotCommand;
import hsl.pojo.command.account.PayCodeCommand;
import hsl.pojo.command.account.RefundCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.account.AccountQO;
import hsl.pojo.qo.account.PayCodeQO;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.account.PayCodeService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class PayCodeTest {
	@Autowired
	private PayCodeService payCodeService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLocalService accountLocalService;
	@Autowired
	private AccountConsumeSnapshotService accountConsumeSnapshotService;
	
	@Test
	public void queryPagination(){//分页查询充值记录
		try {
			Pagination pagination=new Pagination();
			pagination.setPageNo(1);
			pagination.setPageSize(10);
			PayCodeQO payCodeQO=new PayCodeQO();
			//payCodeQO.setCode("13");
			payCodeQO.setType(PayCodeQO.PAYCODE_FF);
			pagination.setCondition(payCodeQO);
			pagination=payCodeService.queryPagination(pagination);
			System.out.println("queryPagination");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void recharge(){//用户充值
		try {
			PayCodeCommand payCodeCommand=new PayCodeCommand();
			payCodeCommand.setCode("1A88177246918C3C");
			HoldUserSnapshotCommand holdUserSnapshotCommand=new HoldUserSnapshotCommand();
			holdUserSnapshotCommand.setUserId("4b9920d6fe98477da81ce7485878a9de");
			holdUserSnapshotCommand.setLoginName("zhaowenshu");
			holdUserSnapshotCommand.setRealName("票量科技");
			holdUserSnapshotCommand.setMobile("18648697197");
			holdUserSnapshotCommand.setEmail("595035525@qq.com");
			payCodeCommand.setHoldUserSnapshotCommand(holdUserSnapshotCommand);
			
			
			String msg=payCodeService.recharge(payCodeCommand);
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void queryAccount(){//查询用户账户
		try {
			AccountQO account=new AccountQO();
			account.setUserID("edcaa54d670042ec8663bba3ae10e5f2");
			account.setConsumeOrderSnapshots(true);
			AccountDTO accountDTO=accountService.queryUnique(account);
			System.out.println(JSON.toJSONString(accountDTO));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void consumptionAccount(){//用户消费
		try {
			AccountCommand accountCommand=new AccountCommand();
			accountCommand.setCommandId("edcaa54d670042ec8663bba3ae10e5f2");//余额账户ID
			accountCommand.setCurrentMoney(20.00);//本次消费金额
			accountCommand.setConsumption(true);
			AccountConsumeSnapshotCommand snapshotCommand=new AccountConsumeSnapshotCommand();
			snapshotCommand.setOrderType(AccountConsumeSnapshot.ORDERTYPE_JP);
			snapshotCommand.setOrderId("111111111");
			snapshotCommand.setStatus(AccountConsumeSnapshot.STATUS_SY);
			snapshotCommand.setPayPrice(20.00);
			//snapshotCommand.setDetail("暂无详细");
			accountCommand.setAccountConsumeSnapshotCommand(snapshotCommand);
			AccountDTO accountDTO=accountService.consumptionAccount(accountCommand);
			System.out.println(JSON.toJSONString(accountDTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void updateAccount(){//退款
		try {
			AccountConsumeSnapshotQO account=new AccountConsumeSnapshotQO();
			account.setOrderId("B910112430000000");
			AccountConsumeSnapshotDTO accountConsumeSnapshot=accountConsumeSnapshotService.queryUnique(account);
			String acountId=accountConsumeSnapshot.getAccount().getId();
			RefundCommand command=new RefundCommand();
			command.setAccountId(acountId);
			command.setConsumeOrderSnapshot(accountConsumeSnapshot);
			command.setRefundMoney(500.00);
			accountService.refundAccount(command);
			System.out.println(JSON.toJSONString(accountConsumeSnapshot));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void queryStatus(){//根据订单号和状态查询使用金额
		try {
			AccountConsumeSnapshotQO account=new AccountConsumeSnapshotQO();
			account.setOrderId("B910112430000000");
			account.setStatus(AccountConsumeSnapshot.STATUS_SY);
			AccountConsumeSnapshotDTO accountConsumeSnapshot=accountConsumeSnapshotService.queryUnique(account);
			System.out.println(JSON.toJSONString(accountConsumeSnapshot));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}