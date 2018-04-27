import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.service.api.dealer.ProvinceCityDealerApiService;
import hg.dzpw.app.service.api.dealer.ScenicSpotDealerApiService;
import hg.dzpw.app.service.api.dealer.TicketOrderDealerApiService;
import hg.dzpw.app.service.api.dealer.TicketPolicyDealerApiService;
import hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.CloseTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.CreateTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.PayToTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRunDealerApiService {
	
	
	@Autowired
	private ProvinceCityDealerApiService provinceCityDealerApiService;
	
	@Autowired
	private TicketPolicyDealerApiService ticketPolicyDealerApiService;
	
	@Autowired
	private ScenicSpotDealerApiService scenicSpotDealerApiService;
	
	@Autowired
	private TicketOrderDealerApiService ticketOrderDealerApiService;
	
	//行政区查询测试
	@Test
	public void testQueryRegion() {

		RegionQO QO = new RegionQO();
		QO.setType(RegionQO.TYPE_PROVINCE);
		
		ApiRequest<RegionQO> request = new ApiRequest<RegionQO>();
		request.getHeader().setDealerKey("ASCXDF");
		request.setBody(QO);

		RegionResponse response = provinceCityDealerApiService.queryRegion(request);
		
		System.out.println(JSON.toJSONString(response, true));
	}

	// 门票政策查询
	@Test
	public void testQueryTicketPolicy(){
		
		TicketPolicyQO QO = new TicketPolicyQO();
//		QO.setType(2);
		QO.setCalendarFetch(true);
//		QO.setSingleTicketPoliciesFetch(true);
		
		ApiRequest<TicketPolicyQO> request = new ApiRequest<TicketPolicyQO>();
		request.getHeader().setDealerKey("ASCXDF");
		request.setBody(QO);

		TicketPolicyResponse response = ticketPolicyDealerApiService
				.queryTicketPolicys(request);
		
		System.out.println(JSON.toJSONString(response, true));
	}

	//景区查询
	@Test
	public void testQueryScenicSpot() {

		ScenicSpotQO QO = new ScenicSpotQO();

		ApiRequest<ScenicSpotQO> request = new ApiRequest<ScenicSpotQO>();
		request.getHeader().setDealerKey("ASCXDF");
		request.setBody(QO);

		ScenicSpotResponse response = scenicSpotDealerApiService
				.queryScenicSpots(request);

		System.out.println(JSON.toJSONString(response, true));
	}

	
	private TouristDTO generateTouristDTO(){
		TouristDTO dto = new TouristDTO();
		dto.setName("张三"+DateUtil.formatDateTime(new Date(), "yyMMddHHmm"));
		dto.setIdType(1);
		dto.setIdNo(UUIDGenerator.getUUID());
		return dto;
		
	}

	// 创建订单
	@Test
	public void testCreateTicketOrder(){
		CreateTicketOrderCommand command = new CreateTicketOrderCommand();
		command.setDealerOrderId(UUIDGenerator.getUUID());
//		command.setDealerOrderId("57fca0cc1c46425897aa504c659cc58b");
		command.setTicketPolicyId("fc4e758c6eec4d13a9954e052c0e3f4a");
		command.setTicketPolicyVersion(3);
		command.setTourists(new ArrayList<TouristDTO>());
		command.getTourists().add(generateTouristDTO());
		command.setPrice(234d);
		
		System.out.println("订单ID");
		System.out.println(command.getDealerOrderId());

		ApiRequest<CreateTicketOrderCommand> request = new ApiRequest<CreateTicketOrderCommand>();
		request.getHeader().setDealerKey("ASCXDF");
		request.setBody(command);

		CreateTicketOrderResponse response = ticketOrderDealerApiService
				.createTicketOrder(request);

		System.out.println(JSON.toJSONString(response, true));
		
	}
	
	// 关闭订单
	@Test
	public void testCloseTicketOrder(){
		CloseTicketOrderCommand command = new CloseTicketOrderCommand();
		command.setDealerOrderId("57fca0cc1c46425897aa504c659cc58b");

		ApiRequest<CloseTicketOrderCommand> request = new ApiRequest<CloseTicketOrderCommand>();
		request.getHeader().setDealerKey("ASCXDF");
		request.setBody(command);

		CloseTicketOrderResponse response = ticketOrderDealerApiService
				.closeTicketOrder(request);

		System.out.println(JSON.toJSONString(response, true));
		
	}

	// 支付订单
	@Test
	public void testPayToTicketOrder(){
		PayToTicketOrderCommand command = new PayToTicketOrderCommand();
		command.setDealerOrderId("59f5bc88e4944729addc371f5fb73354");

		ApiRequest<PayToTicketOrderCommand> request = new ApiRequest<PayToTicketOrderCommand>();
		request.getHeader().setDealerKey("ASCXDF");
		request.setBody(command);

		PayToTicketOrderResponse response = ticketOrderDealerApiService
				.payToTicketOrder(request);

		System.out.println(JSON.toJSONString(response, true));
		
	}
	
	//订单查询
	@Test
	public void testQueryTicketOrder(){
		TicketOrderQO QO = new TicketOrderQO();
		QO.setGroupTicketsFetch(true);
		ApiRequest<TicketOrderQO> request = new ApiRequest<TicketOrderQO>();
		request.getHeader().setDealerKey("JX0006");
		request.setBody(QO);

		TicketOrderResponse response = ticketOrderDealerApiService
				.queryTicketOrder(request);

		System.out.println(JSON.toJSONString(response, true));
		
	}
	
}
