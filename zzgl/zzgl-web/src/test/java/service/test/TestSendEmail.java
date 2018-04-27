/*
package service.test;
import hsl.app.service.local.account.AccountLocalService;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.AccountConsumeSnapshotCommand;
import hsl.pojo.exception.AccountException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestSendEmail {
	@Resource
	AccountLocalService accountLocalService;
	@Test
	public void testSendMail(){
		AccountCommand accountCommand = new AccountCommand();
		accountCommand.setCommandId("5ff973a071654cb1ad2f4aba0371bde7");//余额账户ID
		accountCommand.setCurrentMoney(99.99);//本次消费金额
		accountCommand.setConsumption(false);
		AccountConsumeSnapshotCommand snapshotCommand = new AccountConsumeSnapshotCommand();
		snapshotCommand.setOrderType(AccountConsumeSnapshot.ORDERTYPE_XL);
		snapshotCommand.setOrderId("BB23180159010008");
		snapshotCommand.setStatus(1);
		snapshotCommand.setPayPrice(0.01);
		//snapshotCommand.setDetail("暂无详细");
		accountCommand.setAccountConsumeSnapshotCommand(snapshotCommand);
		try {
//			accountLocalService.test(accountCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testAccount(){
		AccountCommand accountCommand = new AccountCommand();
		accountCommand.setCommandId("5ff973a071654cb1ad2f4aba0371bde7");//余额账户ID
		accountCommand.setCurrentMoney(99.99);//本次消费金额
		accountCommand.setConsumption(false);
		AccountConsumeSnapshotCommand snapshotCommand = new AccountConsumeSnapshotCommand();
		snapshotCommand.setOrderType(AccountConsumeSnapshot.ORDERTYPE_XL);
		snapshotCommand.setOrderId("BB23180159010007");
		snapshotCommand.setStatus(1);
		snapshotCommand.setPayPrice(0.01);
		//snapshotCommand.setDetail("暂无详细");
		accountCommand.setAccountConsumeSnapshotCommand(snapshotCommand);
		try {
//			accountLocalService.test(accountCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
*/
