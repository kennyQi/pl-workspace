package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.GetResetPasswordSMSValidCommand;
import zzpl.api.client.response.user.GetResetPasswordSMSValidResponse;

import com.alibaba.fastjson.JSON;





public class GetResetPasswordSMSValidTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://zhixingapi.ply365.com/api",  "ios", "ios");
		GetResetPasswordSMSValidCommand getResetPasswordSMSValidCommand = new GetResetPasswordSMSValidCommand();
		getResetPasswordSMSValidCommand.setCompanyID("zjply");
		getResetPasswordSMSValidCommand.setLoginName("cs");
		ApiRequest request = new ApiRequest("GetResetPasswordSMSValid", "", getResetPasswordSMSValidCommand, "1.0");
		GetResetPasswordSMSValidResponse response = client.send(request,GetResetPasswordSMSValidResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
