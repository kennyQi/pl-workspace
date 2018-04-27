package lxs.api.app;

import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.app.QueryCarouselQO;
import lxs.api.v1.response.app.AlipayConfigResponse;

import com.alibaba.fastjson.JSON;

public class GetAlipayConfigActionTest {

	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://dev.ply365.com:9080/lxs-api/api", "ios", "ios");
		ApiRequest request = new ApiRequest("GetAlipayConfig", UUID.randomUUID()
				.toString(), new QueryCarouselQO(), "1.0");
		AlipayConfigResponse alipayConfigResponse = client.send(request,AlipayConfigResponse.class);
		
		System.out.println(JSON.toJSON(alipayConfigResponse));
	}

}
