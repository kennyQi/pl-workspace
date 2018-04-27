package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.ScenicSpotSelectiveQO;
import lxs.api.v1.response.mp.ScenicSpotResponse;

import com.alibaba.fastjson.JSON;

public class QueryScenicSpotSelectiveTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ScenicSpotSelectiveQO scenicSpotSelectiveQO = new ScenicSpotSelectiveQO();
		ApiRequest request = new ApiRequest("QueryScenicSpotSelective", UUID.randomUUID().toString(), scenicSpotSelectiveQO, "1.0");
		ScenicSpotResponse scenicSpotResponse = client.send(request,
				ScenicSpotResponse.class);
		System.out.println(JSON.toJSONString(scenicSpotSelectiveQO));
		System.out.println(JSON.toJSONString(scenicSpotResponse));
	}

}
