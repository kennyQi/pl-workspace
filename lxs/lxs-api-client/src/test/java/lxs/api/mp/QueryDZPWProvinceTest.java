package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.DZPWProvinceQO;
import lxs.api.v1.response.mp.DZPWProvinceResponse;

import com.alibaba.fastjson.JSON;

public class QueryDZPWProvinceTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		DZPWProvinceQO dzpwProvinceQO = new DZPWProvinceQO();
		ApiRequest request = new ApiRequest("QueryDZPWProvince", UUID.randomUUID().toString(), dzpwProvinceQO, "1.0");
		DZPWProvinceResponse dzpwProvinceResponse = client.send(request,
				DZPWProvinceResponse.class);
		System.out.println(JSON.toJSONString(dzpwProvinceQO));
		System.out.println(JSON.toJSONString(dzpwProvinceResponse));
	}

}
