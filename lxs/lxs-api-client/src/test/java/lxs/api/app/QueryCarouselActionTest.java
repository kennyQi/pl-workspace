package lxs.api.app;

import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.app.QueryCarouselQO;
import lxs.api.v1.response.app.QueryCarouselResponse;

import com.alibaba.fastjson.JSON;

public class QueryCarouselActionTest {

	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		QueryCarouselQO queryCarouselQO= new QueryCarouselQO();
		queryCarouselQO.setCarouselLevel(3);
		ApiRequest request = new ApiRequest("QueryCarousel", UUID.randomUUID()
				.toString(), queryCarouselQO, "1.0");
		QueryCarouselResponse queryCarouselResponse = client.send(request,QueryCarouselResponse.class);

		System.out.println(JSON.toJSONString(queryCarouselResponse));
	}

}
