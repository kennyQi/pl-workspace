package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.DZPWCityQO;
import lxs.api.v1.response.mp.DZPWCityResponse;

import com.alibaba.fastjson.JSON;

public class QueryDZPWCityTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		DZPWCityQO dzpwCityQO = new DZPWCityQO();
		dzpwCityQO.setCode("11");
		ApiRequest request = new ApiRequest("QueryDZPWCity", UUID.randomUUID().toString(), dzpwCityQO, "1.0");
		DZPWCityResponse dzpwCityResponse = client.send(request,
				DZPWCityResponse.class);
		System.out.println(JSON.toJSONString(dzpwCityQO));
		System.out.println(JSON.toJSONString(dzpwCityResponse));
	}

}
