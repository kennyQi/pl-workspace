package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.ScenicSpotQO;
import lxs.api.v1.response.mp.ScenicSpotResponse;

import com.alibaba.fastjson.JSON;

public class QueryScenicSpotTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ScenicSpotQO scenicSpotQO = new ScenicSpotQO();
		scenicSpotQO.setPageNO("1");
		scenicSpotQO.setPageSize("5");
//		scenicSpotQO.setLevel("AAAAA级");
//		scenicSpotQO.setOrderBy("sales");
//		scenicSpotQO.setSort("h2l");
//		scenicSpotQO.setSubjectID("a384b328d2714ae59ab9bad3c11e811a");
//		scenicSpotQO.setProvinceId("21:27");
		scenicSpotQO.setCityId("173");
//		scenicSpotQO.setAreaId("424:634");
		scenicSpotQO.setPlayPriceHigh(100.0);
		scenicSpotQO.setPlayPriceLow(1.0);
		scenicSpotQO.setName("名称");
		
		ApiRequest request = new ApiRequest("QueryScenicSpot", UUID.randomUUID().toString(), scenicSpotQO, "1.0");
		ScenicSpotResponse scenicSpotResponse = client.send(request,
				ScenicSpotResponse.class);
		System.out.println(JSON.toJSONString(scenicSpotQO));
		System.out.println(JSON.toJSONString(scenicSpotResponse));
	}

}
