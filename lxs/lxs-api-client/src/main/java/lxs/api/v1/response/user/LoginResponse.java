package lxs.api.v1.response.user;

import java.util.Date;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.ImageDTO;
import lxs.api.v1.dto.user.UserAuthInfoDTO;
import lxs.api.v1.dto.user.UserBaseInfoDTO;
import lxs.api.v1.dto.user.UserContactInfoDTO;
import lxs.api.v1.dto.user.UserDTO;
import lxs.api.v1.dto.user.UserStatusDTO;

import com.alibaba.fastjson.JSON;

/**
 * 发送手机验证码响应
 * 
 * @author yuxx
 * 
 */
public class LoginResponse extends ApiResponse {

	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	/*public static void main(String[] arg){
		LoginResponse l = new LoginResponse();
		UserDTO user = new UserDTO(); 
		UserAuthInfoDTO a = new UserAuthInfoDTO();
		a.setLoginName("admin");
		a.setPassword("admin");
		user.setAuthInfo(a);
		UserBaseInfoDTO b = new UserBaseInfoDTO();
		b.setName("马云");
		b.setNickName("ali");
		b.setCreateTime(new Date());
		b.setBirthday("1990-01-01");
		b.setGender(1);
		b.setIdCardNo("230000199001010000");
		user.setBaseInfo(b);
		UserContactInfoDTO c = new UserContactInfoDTO();
		c.setMobile("18888888888");
		c.setEmail("888@pyl365.com");
		c.setIm("im");
		c.setProvinceId("23");
		c.setCityId("9");
		user.setContactInfo(c);
		ImageDTO d = new ImageDTO();
		d.setImageId("123");
		d.setTitle("touxiang");
		d.setUrl("D:/apache-tomcat-7.0.40/webapps/hsl-api/upload/head/2014/10/23e1938a15-e3a0-4a41-a09f-eb4a054b4224.jpg");
		d.setFileInfoJSON("aabbcc");
		user.setTitleImage(d);
		UserStatusDTO e = new UserStatusDTO();
		e.setLastLoginTime(new Date());
		e.setIsEmailChecked(true);
		
		e.setIsTelChecked(true);
		e.setActivated(true);
		user.setStatus(e);
		l.setUser(user);
		System.out.println(JSON.toJSON(l));
	}*/
}
