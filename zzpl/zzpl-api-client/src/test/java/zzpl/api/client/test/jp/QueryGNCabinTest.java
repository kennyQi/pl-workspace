package zzpl.api.client.test.jp;

import java.text.ParseException;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.jp.GNCabinQO;
import zzpl.api.client.response.jp.GNCabinResponse;

public class QueryGNCabinTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios","0ca80e796fb94d46bfd500adddd0f7de");
		GNCabinQO gnCabinQO = new GNCabinQO();
		gnCabinQO.setFlightID("2");
		ApiRequest request = new ApiRequest("QueryGNCabin",	"981bd6cf2c764345b9a360d5f4c8cad3", gnCabinQO, "1.0");
		GNCabinResponse response = new GNCabinResponse();
		response = client.send(request, GNCabinResponse.class);
		System.out.println(JSON.toJSON(response));
	}
}
