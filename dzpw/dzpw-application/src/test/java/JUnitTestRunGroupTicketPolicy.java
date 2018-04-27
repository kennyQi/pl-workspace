import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.domain.model.policy.TicketPolicySellInfo;
import hg.dzpw.domain.model.policy.TicketPolicyUseCondition;
import hg.dzpw.pojo.command.platform.policy.PlatformChangeGroupTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformCreateGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformModifyGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.SingleTicketPolicy;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRunGroupTicketPolicy {

	@Autowired
	private TicketPolicyLocalService service;
	
	@Autowired
	private TicketPolicyPriceCalendarLocalService calendarService;

	@Test
	public void testCreate() throws DZPWException {
		PlatformCreateGroupTicketPolicyCommand command = new PlatformCreateGroupTicketPolicyCommand();
		command.setKey("2333");
		command.setName("杭州联票");
		command.setIntro("可以在杭州任何一个景点游玩");
		command.setRackRate(998d);
		command.setRemainingQty(999);
		command.setValidUseDateType(TicketPolicyUseCondition.VALID_USE_DATE_TYPE_DAYS);
		command.setValidDays(30);
//		command.setSellDateStart(new Date());
//		command.setSellDateEnd(new Date(new Date().getTime() + 3600 * 1000 * 24 * 30));
		command.setReturnRule(TicketPolicySellInfo.RETURN_RULE_DISABLE);

		SingleTicketPolicy stp1 = new SingleTicketPolicy();
//		stp1.setPrice(233d);
		stp1.setScenicSpotId("b5bd9481652745eca34f46e925d90567");
		stp1.setValidDays(3);
		SingleTicketPolicy stp2 = new SingleTicketPolicy();
//		stp2.setPrice(456d);
		stp2.setScenicSpotId("389738ea0a7543d0b0391206e9d0f15d");
		stp2.setValidDays(1);
		SingleTicketPolicy stp3 = new SingleTicketPolicy();
//		stp3.setPrice(311d);
		stp3.setScenicSpotId("c601efe0962e4b4f926e7928792af8eb");
		stp3.setValidDays(2);
		command.getSingleTicketPolicies().add(stp1);
		command.getSingleTicketPolicies().add(stp2);
		command.getSingleTicketPolicies().add(stp3);
		System.out.println("联票ID----->>"+service.platformCreateGroupTicketPolicy(command));
		
	}
	
	@Test
	public void testModify() throws DZPWException {
		PlatformModifyGroupTicketPolicyCommand command = new PlatformModifyGroupTicketPolicyCommand();
		command.setTicketPolicyId("e033ee32ac7043fbbfe104c8e724b48a");
		command.setKey("2333xg");
		command.setName("杭州联票xg");
		command.setIntro("可以在杭州任何一个景点游玩xg");
		command.setRackRate(1998d);
		command.setRemainingQty(1999);
		command.setValidUseDateType(TicketPolicyUseCondition.VALID_USE_DATE_TYPE_DAYS);
		command.setValidDays(30);
//		command.setSellDateStart(new Date());
//		command.setSellDateEnd(new Date(new Date().getTime() + 3600 * 1000 * 24 * 30));
		command.setReturnRule(TicketPolicySellInfo.RETURN_RULE_DISABLE);

		SingleTicketPolicy stp1 = new SingleTicketPolicy();
//		stp1.setPrice(333d);
		stp1.setScenicSpotId("1c1f21089d654214865a9b3c50b16d37");
		stp1.setValidDays(3);
		SingleTicketPolicy stp2 = new SingleTicketPolicy();
//		stp2.setPrice(456d);
		stp2.setScenicSpotId("389738ea0a7543d0b0391206e9d0f15d");
		stp2.setValidDays(1);
		SingleTicketPolicy stp3 = new SingleTicketPolicy();
//		stp3.setPrice(311d);
		stp3.setScenicSpotId("c601efe0962e4b4f926e7928792af8eb");
		stp3.setValidDays(2);
		command.getSingleTicketPolicies().add(stp1);
		command.getSingleTicketPolicies().add(stp2);
		command.getSingleTicketPolicies().add(stp3);
		service.platformModifyGroupTicketPolicy(command);
	}

	@Test
	public void testChangeStatus() throws DZPWException {
		PlatformChangeGroupTicketPolicyStatusCommand command = new PlatformChangeGroupTicketPolicyStatusCommand();
		command.setActive(true);
		command.setTicketPolicyId("322bf920ae264199adae671513a5359c");
		service.platformChangeGroupTicketPolicyStatus(command);
	}

	public static void main(String[] args) {

	}
	
	
}
