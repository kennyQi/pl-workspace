package lxs.api.app;

import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.line.HotDestinationCityQO;
import lxs.api.v1.response.line.HotDestinationCityResponse;

import com.alibaba.fastjson.JSON;

public class QueryHotDestinationCityActionTest {

	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://101.71.39.36:15004/api", "ios", "ios");
		HotDestinationCityQO qo = new HotDestinationCityQO();
		qo.setType(2);
		ApiRequest request = new ApiRequest("QueryHotDestinationCity", UUID.randomUUID()
				.toString(), qo, "1.0");
		HotDestinationCityResponse recommendResponse = client.send(request,HotDestinationCityResponse.class);
		System.out.println(JSON.toJSONString(qo));
		System.out.println(JSON.toJSON(recommendResponse));
	}

}
