import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import plfx.api.client.api.v1.gj.dto.GJJPOrderContacterInfoDTO;
import plfx.api.client.api.v1.gj.dto.GJPassengerBaseInfoDTO;
import plfx.api.client.api.v1.gj.request.ApplyCancelNoPayOrderGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyCancelOrderGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand.RefundPassengerInfo;
import plfx.api.client.api.v1.gj.request.CreateJPOrderGJCommand;
import plfx.api.client.api.v1.gj.request.FlightGJQO;
import plfx.api.client.api.v1.gj.request.FlightMoreCabinsGJQO;
import plfx.api.client.api.v1.gj.request.FlightPolicyGJQO;
import plfx.api.client.api.v1.gj.request.JPOrderGJQO;
import plfx.api.client.api.v1.gj.request.PayToJPOrderGJCommand;
import plfx.api.client.api.v1.gj.response.ApplyCancelNoPayOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyCancelOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyRefundTicketGJResponse;
import plfx.api.client.api.v1.gj.response.CreateJPOrderGJResponse;
import plfx.api.client.api.v1.gj.response.FlightGJResponse;
import plfx.api.client.api.v1.gj.response.FlightMoreCabinsGJResponse;
import plfx.api.client.api.v1.gj.response.FlightPolicyGJResponse;
import plfx.api.client.api.v1.gj.response.JPOrderGJResponse;
import plfx.api.client.api.v1.gj.response.PayToJPOrderGJResponse;
import plfx.api.client.common.util.PlfxApiClient;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：票量分销国际机票接口测试
 * @类修改者：
 * @修改日期：2015-7-2下午5:30:13
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午5:30:13
 */
public class TestGJJP {

	public static Date parseDate(String dateStr, String format) {
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (Exception e) {
		}
		return null;
	}

	PlfxApiClient client = new PlfxApiClient(
			"http://127.0.0.1:8081/plfx-api/api", "F1003", "123456");

	// 查询航班
	@Test
	public void testQueryFlight() {
		FlightGJQO QO = new FlightGJQO();
		QO.setOrgCity("SHA");
		QO.setDstCity("JFK");
		QO.setOrgDate(parseDate("2015-08-15", "yyyy-MM-dd"));
		// QO.setDstDate(parseDate("2015-08-29", "yyyy-MM-dd"));
		FlightGJResponse response = client.send(QO, FlightGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	// 更多舱位
	@Test
	public void testQueryMoreFlight() {
		FlightMoreCabinsGJQO QO = new FlightMoreCabinsGJQO();
		QO.setFlightCabinGroupToken("e5c7cd9869e8529bac56fffa61242cff");
		FlightMoreCabinsGJResponse response = client.send(QO,
				FlightMoreCabinsGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	// 查询政策
	@Test
	public void testQueryPolicy() {
		FlightPolicyGJQO QO = new FlightPolicyGJQO();
		QO.setFlightCabinGroupToken("18a6af1b8be8ccf0f59fe32bdd060668");
		FlightPolicyGJResponse response = client.send(QO,
				FlightPolicyGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	// 查询国际机票订单
	@Test
	public void testQueryGJJPOrder() {
		JPOrderGJQO QO = new JPOrderGJQO();
		QO.setPlatformOrderIds(new ArrayList<String>());
		QO.getPlatformOrderIds().add("F1003A808071647170401");
		JPOrderGJResponse response = client.send(QO, JPOrderGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	// 测试下单
	@Test
	public void testCreateOrder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		CreateJPOrderGJCommand command = new CreateJPOrderGJCommand();
		command.setFromClientType(0);
		command.setDealerOrderId("TEST_" + sdf.format(new Date()));
		command.setRemark("测试订单TX");
		command.setFlightAndPolicyToken("a78236c19c6f3eea3b91bd127d7aacaa");
		GJJPOrderContacterInfoDTO contacterInfo = new GJJPOrderContacterInfoDTO();
		contacterInfo.setContactAddress("地球上");
		
		contacterInfo.setContactMail("zhurz@ply365.com");
		contacterInfo.setContactMobile("18626890576");
		contacterInfo.setContactName("LIAN_XI_REN");
		command.setContacterInfo(contacterInfo);
		List<GJPassengerBaseInfoDTO> passengers = new ArrayList<GJPassengerBaseInfoDTO>();
		command.setPassengerInfos(passengers);
		GJPassengerBaseInfoDTO passenger = new GJPassengerBaseInfoDTO();
		passengers.add(passenger);
		passenger.setPassengerType(1);
		passenger.setSex(1);
		passenger.setResiAddr("American,fds,fds,fg");
		passenger.setResiPost("154566");
		passenger.setDstAddr("China,dfd,fyrt,rt");
		passenger.setDstPost("156748");
		try {
			passenger.setBirthday(sdf2.parse("19850102"));
			passenger.setExpiryDate(sdf2.parse("20151010"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		passenger.setNationality("CN");
		passenger.setMobile("110");
		passenger.setPassengerName("LI/SI");
		passenger.setIdType(1);
		passenger.setIdNo("123123123");

		CreateJPOrderGJResponse response = client.send(command,
				CreateJPOrderGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	// 测试取消未支付的订单
	@Test
	public void testCancelNoPayOrder() {
		ApplyCancelNoPayOrderGJCommand command = new ApplyCancelNoPayOrderGJCommand();
		command.setPlatformOrderId("F1003A707311603010402");
		command.setCancelReasonType(5);

		ApplyCancelNoPayOrderGJResponse response = client.send(command,
				ApplyCancelNoPayOrderGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	// 测试支付
	@Test
	public void testPay() {

		PayToJPOrderGJCommand command = new PayToJPOrderGJCommand();
		command.setPlatformOrderId("F1003A808071647170401");
		command.setTotalPrice(9281d);

		PayToJPOrderGJResponse response = client.send(command,
				PayToJPOrderGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));

	}

	// 测试取消已支付未出票的订单
	@Test
	public void testCancelOrder() {

		ApplyCancelOrderGJCommand command = new ApplyCancelOrderGJCommand();
		command.setPlatformOrderId("F1003A808071647170401");

		ApplyCancelOrderGJResponse response = client.send(command,
				ApplyCancelOrderGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));

	}

	// 测试退废票
	@Test
	public void testRefundTicket() {
		
		ApplyRefundTicketGJCommand command = new ApplyRefundTicketGJCommand();
		command.setPlatformOrderId("F1003A808071647170401");
		// 1. 当日作废 2. 自愿退票 3. 非自愿退票 4. 其他
		command.setRefundType(2);
		command.setRefundMemo("自愿废票");
		command.setRefundPassengerInfos(new ArrayList<RefundPassengerInfo>());
		RefundPassengerInfo passengerInfo = new RefundPassengerInfo();
		command.getRefundPassengerInfos().add(passengerInfo);
		passengerInfo.setPassengerName("LI/SI");
		passengerInfo.setIdType(1);
		passengerInfo.setIdNo("123123123");

		ApplyRefundTicketGJResponse response = client.send(command,
				ApplyRefundTicketGJResponse.class);
		System.out.println(JSON.toJSONString(response, true));

	}
	
}
