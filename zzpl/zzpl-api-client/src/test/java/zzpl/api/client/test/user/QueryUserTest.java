package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.QueryUserQO;
import zzpl.api.client.response.user.QueryUserResponse;

import com.alibaba.fastjson.JSON;





public class QueryUserTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "523988aa030f4714841b7201836dddcd");
		QueryUserQO queryUserQO = new QueryUserQO();
		queryUserQO.setUserID("5f21e0cd7b8f4510ba17b6a4f037f6d9");
		ApiRequest request = new ApiRequest("QueryUser", "5edf4b6ca2fa4e1c9aa21bd40ec0ad31", queryUserQO, "1.0");
		QueryUserResponse response = client.send(request,QueryUserResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
