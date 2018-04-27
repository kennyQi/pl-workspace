package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.CheckSMSValidCommand;
import zzpl.api.client.response.user.CheckSMSValidResponse;

import com.alibaba.fastjson.JSON;





public class CheckSMSValidTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api",  "ios", "ios");
		CheckSMSValidCommand command = new CheckSMSValidCommand();
		command.setSagaID("f49e046cd2ec4490bef4d114f972dd44");
		command.setSmsValid("222621");
		ApiRequest request = new ApiRequest("CheckSMSValid", "", command, "1.0");
		CheckSMSValidResponse response = client.send(request,CheckSMSValidResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
