import hg.common.util.DateUtil;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.pojo.command.merchant.policy.DatePrice;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotChangeSingleTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotCreateSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotDeleteSingleTicketPolicyPriceCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotSetSingleTicketPolicyPriceCommand;
import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRunSingleTicketPolicy {

	@Autowired
	private TicketPolicyLocalService service;
	
	@Autowired
	private TicketPolicyPriceCalendarLocalService calendarService;
	
	private void setScenicspotId(DZPWMerchantBaseCommand command){
		command.setScenicSpotId("389738ea0a7543d0b0391206e9d0f15d");
	}

	@Test
	public void testCreate() throws DZPWException {
		ScenicspotCreateSingleTicketPolicyCommand command = new ScenicspotCreateSingleTicketPolicyCommand();
		setScenicspotId(command);
		command.setKey("0002");
		command.setName("杭州乐园测试门票20150326");
		command.setNotice("杭州乐园测试门票20150326杭州乐园测试门票20150326杭州乐园测试门票20150326");
		command.setRackRate(1000d);
//		command.setPrice(998d);
		command.setValidDays(3);
		command.setReturnRule(1);
		System.out.println("独立单票ID--->"+service.scenicspotCreateSingleTicketPolicy(command));
	}

	@Test
	public void testAddCalendar() throws DZPWException{
		ScenicspotSetSingleTicketPolicyPriceCommand command = new ScenicspotSetSingleTicketPolicyPriceCommand();
		setScenicspotId(command);
		command.setTicketPolicyId("188d4f4762204a80bfe9845132017270");
		command.setStandardPrice(true);
		List<DatePrice> list=new ArrayList<DatePrice>();
		for(int i=0;i<365;i++){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			DatePrice dp = new DatePrice();
			dp.setDate(DateUtil.formatDateTime(c.getTime(), "yyyyMMdd"));
			dp.setPrice(new Random().nextInt(100)+100d);
			list.add(dp);
		}
		command.setDatePrices(list);
		calendarService.scenicspotSetSingleTicketPolicyPrice(command);
	}
	
	@Test
	public void testDeleteCalendar() throws DZPWException{
		ScenicspotDeleteSingleTicketPolicyPriceCommand command = new ScenicspotDeleteSingleTicketPolicyPriceCommand();
		setScenicspotId(command);
		command.setCalendarId("188d4f4762204a80bfe9845132017270");
		calendarService.scenicspotDeleteSingleTicketPolicyPrice(command);
	}

	@Test
	public void testChangeStatus() throws DZPWException {
		ScenicspotChangeSingleTicketPolicyStatusCommand command = new ScenicspotChangeSingleTicketPolicyStatusCommand();
		setScenicspotId(command);
		command.setActive(true);
		command.setTicketPolicyId("188d4f4762204a80bfe9845132017270");
		service.scenicspotChangeSingleTicketPolicyStatus(command);
	}

	public static void main(String[] args) {

	}
	
	
}
