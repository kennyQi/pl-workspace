	package lxs.api.user;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

import javax.imageio.stream.FileImageInputStream;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.util.Base64;
import lxs.api.v1.dto.ImageDTO;
import lxs.api.v1.dto.user.UserAuthInfoDTO;
import lxs.api.v1.dto.user.UserBaseInfoDTO;
import lxs.api.v1.dto.user.UserContactInfoDTO;
import lxs.api.v1.dto.user.UserDTO;
import lxs.api.v1.request.command.user.ModifyUserInfoCommand;
import lxs.api.v1.response.user.ModifyUserInfoResponse;

public class ModifyUserInfoTest {

	public static void main(String[] args) throws ParseException {
		LxsApiClient client = new LxsApiClient(
				"http://115.238.43.242:60000/lxs-api/api", "ios", "ios");
		ModifyUserInfoCommand command = new ModifyUserInfoCommand();
		UserDTO userDTO = new UserDTO();
		UserAuthInfoDTO authInfoDTO = new UserAuthInfoDTO();
		authInfoDTO.setUserId("92ab1c459edd472199980d6eb21956ca");
//		authInfoDTO.setLoginName("18646292336");
		userDTO.setAuthInfo(authInfoDTO);
		UserBaseInfoDTO baseInfoDTO = new UserBaseInfoDTO();
		baseInfoDTO.setNickName("Song");
		baseInfoDTO.setGender(1);
		baseInfoDTO.setBirthday("1991-06-16");
		userDTO.setBaseInfo(baseInfoDTO);
		UserContactInfoDTO userContactInfoDTO= new UserContactInfoDTO();
		userContactInfoDTO.setEmail("cangsong@ply365.com");
		userContactInfoDTO.setProvinceId("12");
		userContactInfoDTO.setCityId("170");
		userDTO.setContactInfo(userContactInfoDTO);
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setImageId("");
		imageDTO.setTitle("");
		imageDTO.setImg_image_bytearry(Base64.encode(image2byte("F://doge.png")));
		userDTO.setTitleImage(imageDTO);
		command.setUserDTO(userDTO);
		ApiRequest request = new ApiRequest("ModifyUserInfo", UUID.randomUUID()
				.toString(), command, "1.0");
		ModifyUserInfoResponse response = client.send(request,
				ModifyUserInfoResponse.class);
		System.out.println(response.getMessage());
		// System.out.println(JSON.toJSON(response.getUser()));

		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));
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
