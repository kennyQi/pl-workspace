package zzpl.api.client.test.user;

import hg.common.util.Md5Util;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.ChangePasswordCommand;
import zzpl.api.client.response.user.LoginResponse;

import com.alibaba.fastjson.JSON;





public class ChangePasswordTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "047a9c772bf14bc1b37e037f67b0e0ad");
		ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand();
		changePasswordCommand.setUserID("8e13a5148dce49c19b2d572c9b32f492");
		changePasswordCommand.setPassword(Md5Util.MD5("qcr"));
		changePasswordCommand.setNewPassword(Md5Util.MD5("123456"));
		ApiRequest request = new ApiRequest("ChangePassword", "3387c8e2ec3a477595a2266d2799ff1d",changePasswordCommand , "1.0");
		LoginResponse response = client.send(request,LoginResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
