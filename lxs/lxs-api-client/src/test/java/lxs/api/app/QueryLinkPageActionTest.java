package lxs.api.app;

import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.app.QueryCarouselQO;
import lxs.api.v1.response.app.QueryLinkPageResponse;

import com.alibaba.fastjson.JSON;

public class QueryLinkPageActionTest {

	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		QueryCarouselQO qo = new QueryCarouselQO();
		ApiRequest request = new ApiRequest("QueryLinkPage", UUID.randomUUID()
				.toString(), qo, "1.0");
		QueryLinkPageResponse carouselResponse = client.send(request,
				QueryLinkPageResponse.class);

		System.out.println(JSON.toJSONString(carouselResponse));
	}

}
