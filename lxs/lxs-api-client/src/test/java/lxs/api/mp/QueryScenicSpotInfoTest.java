package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.ScenicSpotInfoQO;
import lxs.api.v1.response.mp.ScenicSpotInfoResponse;

import com.alibaba.fastjson.JSON;

public class QueryScenicSpotInfoTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ScenicSpotInfoQO scenicSpotInfoQO = new ScenicSpotInfoQO();
		scenicSpotInfoQO.setScenicSpotID("6bf8be3804c04211a9d0f0e8d4951763");
		
		ApiRequest request = new ApiRequest("QueryScenicSpotInfo", UUID.randomUUID().toString(), scenicSpotInfoQO, "1.0");
		ScenicSpotInfoResponse scenicSpotInfoResponse = client.send(request,
				ScenicSpotInfoResponse.class);
		System.out.println(JSON.toJSONString(scenicSpotInfoQO));
		System.out.println(JSON.toJSONString(scenicSpotInfoResponse));
	}

}
