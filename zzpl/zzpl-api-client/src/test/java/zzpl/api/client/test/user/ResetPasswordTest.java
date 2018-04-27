package zzpl.api.client.test.user;

import hg.common.util.Md5Util;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.ResetPasswordCommand;
import zzpl.api.client.response.user.ResetPasswordResponse;

import com.alibaba.fastjson.JSON;





public class ResetPasswordTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api",  "ios", "ios");
		ResetPasswordCommand command = new ResetPasswordCommand();
		command.setNewPassword(Md5Util.MD5("123456"));
		command.setSagaID("f49e046cd2ec4490bef4d114f972dd44");
		ApiRequest request = new ApiRequest("ResetPassword", "", command, "1.0");
		ResetPasswordResponse response = client.send(request,ResetPasswordResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
