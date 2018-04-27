package lxs.api.app;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.app.QueryRecommendQO;
import lxs.api.v1.response.app.QueryRecommendResponse;

public class QueryRecommendActionTest {

	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://101.71.39.36:15004/api", "ios", "ios");
		QueryRecommendQO qo = new QueryRecommendQO();
		ApiRequest request = new ApiRequest("QueryRecommend", UUID.randomUUID()
				.toString(), qo, "1.0");
		QueryRecommendResponse recommendResponse = client.send(request,QueryRecommendResponse.class);
		
		System.out.println(JSON.toJSONString(recommendResponse));
	}

}
