import hg.common.util.DateUtil;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;
import hg.dzpw.pojo.api.appv1.exception.DZPWAppApiException;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.exception.DZPWException;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRunGroupTicketOrder {

	@Autowired
	private TicketOrderLocalService orderService;
	
	// 联票
	@Test
	public void testCreate() throws DZPWException, DZPWAppApiException,
			ParseException, DZPWDealerApiException {

		String dealerId = "df2d21c6e4b34aeb92f217a2d44859d3";
		CreateTicketOrderCommand command = new CreateTicketOrderCommand();
		command.setDealerOrderId(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS"));
		command.setTicketPolicyId("749263652031430ea662b082889d57d3");
		command.setPrice(276d*2);
		TouristDTO tourist1 = new TouristDTO();
		tourist1.setName("张三");
		tourist1.setAge(22);
		tourist1.setIdType(Integer.valueOf(DZPWConstants.CER_TYPE_IDENTITY));
		tourist1.setIdNo("110110");
		tourist1.setGender(Integer.valueOf(DZPWConstants.GENDER_F));
		tourist1.setBirthday(new Date());
		tourist1.setTelephone("110");
		tourist1.setAddress("杭州");
		tourist1.setNation("汉");

		TouristDTO tourist2 = new TouristDTO();
		tourist2.setName("李四");
		tourist2.setAge(22);
		tourist2.setIdType(Integer.valueOf(DZPWConstants.CER_TYPE_IDENTITY));
		tourist2.setIdNo("120120");
		tourist2.setGender(Integer.valueOf(DZPWConstants.GENDER_F));
		tourist2.setBirthday(new Date());
		tourist2.setTelephone("120");
		tourist2.setAddress("杭州");
		tourist2.setNation("汉");
		
		command.getTourists().add(tourist1);
		command.getTourists().add(tourist2);
		

		String orderId = orderService.createTicketOrder(command, dealerId).getId();
		
		System.out.println("订单编号：" + orderId);
	}
	
	// 单票
	@Test
	public void testCreate2() throws DZPWException, DZPWAppApiException,
			ParseException, DZPWDealerApiException {

		String dealerId = "512866a1741742dabf2e1ff1833f5684";
		CreateTicketOrderCommand command = new CreateTicketOrderCommand();
		command.setTravelDate(DateUtils.addDays(new Date(), 2));
		command.setDealerOrderId(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS"));
		command.setTicketPolicyId("188d4f4762204a80bfe9845132017270");
		command.setPrice(99d*2);
		TouristDTO tourist1 = new TouristDTO();
		tourist1.setName("张三");
		tourist1.setAge(22);
		tourist1.setIdType(Integer.valueOf(DZPWConstants.CER_TYPE_IDENTITY));
		tourist1.setIdNo("110110");
		tourist1.setGender(Integer.valueOf(DZPWConstants.GENDER_F));
		tourist1.setBirthday(new Date());
		tourist1.setTelephone("110");
		tourist1.setAddress("杭州");
		tourist1.setNation("汉");

		TouristDTO tourist2 = new TouristDTO();
		tourist2.setName("李四");
		tourist2.setAge(22);
		tourist2.setIdType(Integer.valueOf(DZPWConstants.CER_TYPE_IDENTITY));
		tourist2.setIdNo("120120");
		tourist2.setGender(Integer.valueOf(DZPWConstants.GENDER_F));
		tourist2.setBirthday(new Date());
		tourist2.setTelephone("120");
		tourist2.setAddress("杭州");
		tourist2.setNation("汉");
		
		command.getTourists().add(tourist1);
		command.getTourists().add(tourist2);

		String orderId = orderService.createTicketOrder(command, dealerId).getId();
		
		System.out.println("订单编号：" + orderId);
	}

}
