import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.GroupTicketQO;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.CreateTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.GroupTicketResponse;
import hg.dzpw.dealer.client.api.v1.response.PayToTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
//import hg.dzpw.dealer.client.api.v1.request.GroupTicketQO;

/**
 * @类功能说明：面向经销商API测试
 * @类修改者：
 * @修改日期：2014-12-18下午2:49:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-18下午2:49:49
 */
public class TestApi {
	
	DealerApiClient client = new DealerApiClient(
			"http://127.0.0.1:8081/dzpw-dealer-api/api", "ASCXDF",
			"ASCXDF");

	/**
	 * @方法功能说明：测试景区查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-18下午2:36:30
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void testQueryScenicSpot() {
		ScenicSpotQO qo = new ScenicSpotQO();
		long time = System.currentTimeMillis();
		ScenicSpotResponse response = client.send(qo, ScenicSpotResponse.class);
		System.out.println("耗时:" + (System.currentTimeMillis() - time));
		System.out.println(JSON.toJSONString(response, true));
	}
	
	/**
	 * @方法功能说明：测试门票政策查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-18下午2:36:35
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void testQueryTicketPolicy() {
		TicketPolicyQO qo = new TicketPolicyQO();
		qo.setType(1);
		qo.setCalendarFetch(true);
		long time = System.currentTimeMillis();
		TicketPolicyResponse response = client.send(qo, TicketPolicyResponse.class);
		System.out.println("耗时:" + (System.currentTimeMillis() - time));
		System.out.println(JSON.toJSONString(response, true));
	}

	/**
	 * @方法功能说明：测试门票订单查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-18下午2:43:43
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void testQueryTicketOrder() {
		TicketOrderQO qo = new TicketOrderQO();
		long time = System.currentTimeMillis();
		TicketOrderResponse response = client.send(qo, TicketOrderResponse.class);
		System.out.println("耗时:" + (System.currentTimeMillis() - time));
		System.out.println(JSON.toJSONString(response, true));
	}

	/**
	 * @方法功能说明：查询门票订单中的门票
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-18下午2:48:19
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void testQueryGroupTicket() {
		GroupTicketQO qo = new GroupTicketQO();
		qo.setTicketNos(new String[]{"LP2015042700004"});
		qo.setOrderId("PL2015042700004");
		long time = System.currentTimeMillis();
		GroupTicketResponse response = client.send(qo, GroupTicketResponse.class);
		System.out.println("耗时:" + (System.currentTimeMillis() - time));
		System.out.println(JSON.toJSONString(response, true));
	}

	/**
	 * @方法功能说明：测试行政区查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-18下午2:52:40
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void testQueryRegion() {
		RegionQO qo = new RegionQO();
		// qo.setType(RegionQO.TYPE_PROVINCE);
		qo.setType(RegionQO.TYPE_CITY);
		long time = System.currentTimeMillis();
		RegionResponse response = client.send(qo, RegionResponse.class);
		System.out.println("耗时:" + (System.currentTimeMillis() - time));
		System.out.println(JSON.toJSONString(response, true));
	}
	
	/**
	 * @throws ParseException 
	 * @方法功能说明：测试创建门票订单
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-18下午2:58:06
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void testCreateTicketOrder() throws ParseException {
		CreateTicketOrderCommand command = new CreateTicketOrderCommand();
		command.setDealerOrderId(UUID.randomUUID().toString());
		command.setPrice(288d);
		command.setTicketPolicyId("65cc5301b2a54639998f16291d81b29b");
		List<TouristDTO> list=new ArrayList<TouristDTO>();
		TouristDTO tourist = new TouristDTO();
		tourist.setName("李四");
		tourist.setAddress("xxx");
		tourist.setIdType(1);
		tourist.setIdNo("123456");
		list.add(tourist);
		command.setTravelDate(new SimpleDateFormat("yyyyMMdd").parse("20150522"));
		command.setTourists(list);
		long time = System.currentTimeMillis();
		CreateTicketOrderResponse response = client.send(command, CreateTicketOrderResponse.class);
		System.out.println("耗时:" + (System.currentTimeMillis() - time));
		System.out.println(JSON.toJSONString(response, true));
	}
	
	@Test
	public void testPayToOrder(){
		PayToTicketOrderCommand command = new PayToTicketOrderCommand();
		command.setDealerOrderId("1119a698b82-1938-4812-9f8b-c8f990b2c36e");
		PayToTicketOrderResponse response = client.send(command, PayToTicketOrderResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}
	
	@Test
	public void testQueryOrder() {
		TicketOrderQO qo = new TicketOrderQO();
		TicketOrderResponse response = client.send(qo,
				TicketOrderResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}
}
