import com.alibaba.fastjson.JSON;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import hsl.pojo.dto.yxjp.YXFlightDTO;
import hsl.pojo.dto.yxjp.YXResultCabinDTO;
import hsl.pojo.dto.yxjp.YXResultFlightDTO;
import hsl.pojo.dto.yxjp.YXResultQueryFlightDTO;
import org.junit.Before;
import org.junit.Test;
import plfx.api.client.api.v1.gn.request.JPFlightGNQO;
import plfx.api.client.api.v1.gn.request.JPPolicyGNQO;
import plfx.api.client.api.v1.gn.response.JPQueryFlightListGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryHighPolicyGNResponse;
import plfx.api.client.common.util.PlfxApiClient;

/**
 * @author zhurz
 */
public class TestSlfxApi2 {

	PlfxApiClient client;

	@Before
	public void init() {
		client = new PlfxApiClient("http://127.0.0.1:8084/slfx-api/api", "F1001", "123456");
	}

	/**
	 * 测试查询航班
	 */
	@Test
	public void testQueryFlightList() {
		JPFlightGNQO qo = new JPFlightGNQO();
		qo.setOrgCity("SHA");
		qo.setDstCity("CAN");
		qo.setStartDate(DateUtil.parseDateTime("2015-12-12", "yyyy-MM-dd"));

		JPQueryFlightListGNResponse response = client.send(qo, JPQueryFlightListGNResponse.class);
		System.out.println(JSON.toJSONString(response, true));

	}

	/**
	 * 测试查询航班
	 */
	@Test
	public void testQueryFlightList2() {
		JPFlightGNQO qo = new JPFlightGNQO();
		qo.setOrgCity("SHA");
		qo.setDstCity("CAN");
		qo.setStartDate(DateUtil.parseDateTime("2015-12-12", "yyyy-MM-dd"));

		YXResultQueryFlightDTO result = client.send(qo, YXResultQueryFlightDTO.class);

		long a = System.currentTimeMillis();
		if ("T".equals(result.getIs_success()) && result.getFlightList() != null) {
			for (YXResultFlightDTO resultFlightDTO : result.getFlightList()) {
				YXFlightDTO.BaseInfo baseInfo = BeanMapperUtils.map(resultFlightDTO, YXFlightDTO.BaseInfo.class);
				for (YXResultCabinDTO resultCabinDTO : resultFlightDTO.getCabinList()) {
					YXFlightDTO.CabinInfo cabinInfo = BeanMapperUtils.map(resultCabinDTO, YXFlightDTO.CabinInfo.class);
					YXFlightDTO flightDTO = new YXFlightDTO();
					flightDTO.setBaseInfo(baseInfo);
					flightDTO.setCabinInfo(cabinInfo);
					resultCabinDTO.setOrderEncryptString(flightDTO.toEncryptString().replaceAll("[\r\n]+", ""));
				}
			}
		}


		System.out.println(System.currentTimeMillis() - a);

		System.out.println(JSON.toJSONString(result, true));
	}

	@Test
	public void test() {

		YXFlightDTO flightDTO = new YXFlightDTO();

		JPPolicyGNQO qo = new JPPolicyGNQO();
		qo.setEncryptString("79fccac98f164687dca80708da85c12f55b2e6cbe12f015e56de1d3350b399fc433b8ce864d8efc3efac994a59c2d44e9dce9205f1d23671ca30f63e4b381bab6e11c3448419ecfadcab78f0517ab50b284f263c3fd8c11a");
		qo.setTickType(3);

		JPQueryHighPolicyGNResponse response = client.send(qo, JPQueryHighPolicyGNResponse.class);

		YXFlightDTO.PolicyInfo policyInfo = BeanMapperUtils.map(response, YXFlightDTO.PolicyInfo.class);
		BeanMapperUtils.getMapper().map(response.getPricesGNDTO(), policyInfo);
		flightDTO.setPolicyInfo(policyInfo);

		System.out.println(JSON.toJSONString(flightDTO, true));
	}

	/**
	 * 测试查询政策
	 */
	@Test
	public void testQueryAirPolicy() {

		JPPolicyGNQO qo = new JPPolicyGNQO();
		qo.setTickType(3);
		qo.setEncryptString("79fccac98f164687dca80708da85c12f55b2e6cbe12f015e56de1d3350b399fc433b8ce864d8efc3efac994a59c2d44e9dce9205f1d23671ca30f63e4b381bab6e11c3448419ecfadcab78f0517ab50b284f263c3fd8c11a");

		JPQueryHighPolicyGNResponse response = client.send(qo, JPQueryHighPolicyGNResponse.class);
		System.out.println(JSON.toJSONString(response, true));

	}


}
