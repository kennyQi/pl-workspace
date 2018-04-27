package zzpl.api.client.test.user;

import hg.common.util.Md5Util;

import java.text.ParseException;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.user.UserDTO;
import zzpl.api.client.request.user.LoginCommand;
import zzpl.api.client.response.user.LoginResponse;





public class LoginTest {

	public static void main(String[] args) throws ParseException {

//		ApiClient client = new ApiClient(	"http://192.168.10.65:8080/zzpl-api/api", "ios", "524ed6a3317840d2979d3d0e68bb7d18");
		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "ios");
		LoginCommand command = new LoginCommand();
		UserDTO userDTO = new UserDTO();
		userDTO.setUserNO("hgkj");
		userDTO.setLoginName("cangsong");
		userDTO.setPassword(Md5Util.MD5("cangsong"));
		command.setUserDTO(userDTO);
//		ApiRequest request = new ApiRequest("hahahahah", "ee5866089a55485cacd6fc531890032e", command, "1.0");
		ApiRequest request = new ApiRequest("Login", "", command, "1.0");
		LoginResponse response = client.send(request,LoginResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
