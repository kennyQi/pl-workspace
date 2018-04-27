package lxs.api.app;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.app.QueryIntroductionQO;
import lxs.api.v1.response.app.QueryIntroductionResponse;

public class QueryIntroductionActionTest {

	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		QueryIntroductionQO qo = new QueryIntroductionQO();
		ApiRequest request = new ApiRequest("QueryIntroduction", UUID
				.randomUUID().toString(), qo, "1.0");
		QueryIntroductionResponse queryintroductionresponse = client.send(
				request, QueryIntroductionResponse.class);

		System.out.println(JSON.toJSONString(qo));
		System.out.println(JSON.toJSONString(queryintroductionresponse));
	}

}
