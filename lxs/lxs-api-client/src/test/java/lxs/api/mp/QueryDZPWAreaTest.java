package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.DZPWAreaQO;
import lxs.api.v1.response.mp.DZPWAreaResponse;

import com.alibaba.fastjson.JSON;

public class QueryDZPWAreaTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		DZPWAreaQO dzpwAreaQO = new DZPWAreaQO();
		dzpwAreaQO.setCode("150");
		ApiRequest request = new ApiRequest("QueryDZPWArea", UUID.randomUUID().toString(), dzpwAreaQO, "1.0");
		DZPWAreaResponse dzpwAreaResponse = client.send(request,
				DZPWAreaResponse.class);
		System.out.println(JSON.toJSONString(dzpwAreaQO));
		System.out.println(JSON.toJSONString(dzpwAreaResponse));
	}

}
