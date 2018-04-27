package lxs.api.user;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.user.UserQO;
import lxs.api.v1.response.user.QueryUserResponse;

public class QueryUserTest {

	public static void main(String[] args) throws ParseException {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		UserQO qo = new UserQO();
		// qo.setLoginName("18646292336");
		//qo.setMobile("18646292336");
		 qo.setUserId("92ab1c459edd472199980d6eb21956ca");
		ApiRequest request = new ApiRequest("QueryUser", UUID.randomUUID()
				.toString(), qo, "1.0");
		QueryUserResponse response = client.send(request,
				QueryUserResponse.class);

		System.out.println(JSON.toJSONString(qo));
		System.out.println(JSON.toJSONString(response));


	}

}
