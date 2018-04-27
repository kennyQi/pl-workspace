package zzpl.api.client.test.user;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.stream.FileImageInputStream;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.ModifyUserCommand;
import zzpl.api.client.response.user.ModifyUserResponse;
import zzpl.api.client.util.Base64;

import com.alibaba.fastjson.JSON;





public class ModifyUserTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "7ad27993e0494d8a94a8f9c21cc7808e");
		ModifyUserCommand modifyUserCommand = new ModifyUserCommand();
		modifyUserCommand.setUserID("8e13a5148dce49c19b2d572c9b32f492");
		modifyUserCommand.setGender(1);
		modifyUserCommand.setLinkEmail("123@123.com");
		modifyUserCommand.setLinkMobile("18888888888");
		modifyUserCommand.setName("您好");
		modifyUserCommand.setImageInfo(Base64.encode(image2byte("F://doge.png")));
		
		ApiRequest request = new ApiRequest("ModifyUser", "87e3a47b91ab48fbb7df262ce8c2afd6",modifyUserCommand , "1.0");
		ModifyUserResponse response = client.send(request,ModifyUserResponse.class);
		System.out.println(JSON.toJSON(response));

	}
	
	public static byte[] image2byte(String path) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}

}
