package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.QueryFrequentFlyerQO;
import zzpl.api.client.response.user.QueryFrequentFlyerResponse;

import com.alibaba.fastjson.JSON;





public class QueryFrequentFlyerTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "04a57166920a4b28a87c54d8c858c0e5");
		QueryFrequentFlyerQO queryFrequentFlyerQO = new QueryFrequentFlyerQO();
		queryFrequentFlyerQO.setUserID("222222");
		ApiRequest request = new ApiRequest("QueryFrequentFlyer", "1ec70190069d47f78963f0ebbcb15103",queryFrequentFlyerQO , "1.0");
		QueryFrequentFlyerResponse response = client.send(request,QueryFrequentFlyerResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
