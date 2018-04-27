import com.alibaba.fastjson.JSON;
import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import org.junit.Before;
import org.junit.Test;

/**
 * 电子票务测试类
 *
 * @author zhurz
 * @since 1.8
 */
public class TestDzpwApi {

	private DealerApiClient client;

	@Before
	public void init() {
		client = new DealerApiClient(
				"http://192.168.2.56:8080/api",
				"JX0019",
				"e86faf87d740469aab499bac642a3fc5"
		);
	}

	/**
	 * 测试查询行政区
	 */
//	@Test
	public void testQueryRegion() {

		RegionQO qo = new RegionQO();

		// 查询省
		qo.setType(RegionQO.TYPE_PROVINCE);
		RegionResponse response = client.send(qo, RegionResponse.class);
		System.out.println(JSON.toJSONString(response, true));

		// 查询市
		qo.setType(RegionQO.TYPE_CITY);
		response = client.send(qo, RegionResponse.class);
		System.out.println(JSON.toJSONString(response, true));

		// 查询区
		qo.setType(RegionQO.TYPE_AREA);
		response = client.send(qo, RegionResponse.class);
		System.out.println(JSON.toJSONString(response, true));

	}

	/**
	 * 测试查询景区
	 */
//	@Test
	public void testQueryScenicSpot() {
		ScenicSpotQO qo = new ScenicSpotQO();
		ScenicSpotResponse response = client.send(qo, ScenicSpotResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	/**
	 * 查询门票政策
	 */
    @Test
	public void testQueryTicketPolicy() {
		TicketPolicyQO qo = new TicketPolicyQO();
		TicketPolicyResponse response = client.send(qo, TicketPolicyResponse.class);
		System.out.println(JSON.toJSONString(response, true));
	}

	/**
	 * 测试下单
	 */
	public void testCreateOrder() {

	}

	/**
	 * 测试关闭订单
	 */
	public void testCloseOrder() {

	}

	/**
	 * 测试查询订单
	 */
	public void testQueryOrder() {

	}

}
