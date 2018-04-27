import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.common.util.MD5HashUtil;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.pojo.api.appv1.base.ApiRequest;
import hg.dzpw.pojo.api.appv1.request.LoginRequestBody;
import hg.dzpw.pojo.api.appv1.request.UseTicketRequestBody;
import hg.dzpw.pojo.api.appv1.request.ValiTicketByCerRequestBody;
import hg.dzpw.pojo.api.appv1.request.ValiTicketByTicketNoRequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.security.auth.login.LoginContext;

import org.openxmlformats.schemas.drawingml.x2006.chart.STTickLblPos;

import com.alibaba.fastjson.JSON;


public class ValiTicketByTicketNoTest {
	private static String url = "http://127.0.0.1:8088/dzpw-app-api/api";
	/**
	 * 
	 * @描述：登录测试 
	 * @author: guotx 
	 * @version: 2015-12-18 下午3:56:24
	 */
	public static void login() throws UnsupportedEncodingException{
		ApiRequest<LoginRequestBody> request=new ApiRequest<LoginRequestBody>();
		request.getHeader().setActionName("Login");
		request.getHeader().setTimestamp(System.currentTimeMillis());
		if (request.getBody()==null) {
			request.setBody(new LoginRequestBody());
		}
		request.getBody().setLoginName("ABCDEF");
		request.getBody().setPassword("123456");
		String json=JSON.toJSONString(request);
		StringBuilder builder=new StringBuilder();
		String msg=URLEncoder.encode(json,"UTF-8");
		System.out.println("msg>>>>>>>"+json);
		builder.append("msg=").append(msg);
		builder.append("&sign=").append(MD5HashUtil.toMD5(SystemConfig.scenicSpotPublicKey + json));
		HttpResponse response=HttpUtil.reqForPost(url, builder.toString(), 5000);
		System.out.println(response.getResult());
	}
	/**
	 * 
	 * @描述：根据票号验票 
	 * @author: guotx 
	 * @version: 2015-12-18 下午3:56:01
	 */
	public static void validTicketByTicketNo() throws UnsupportedEncodingException{

		ApiRequest<ValiTicketByTicketNoRequestBody> request = new ApiRequest<ValiTicketByTicketNoRequestBody>();
		request.getHeader().setActionName("ValiTicketByTicketNo");
		request.getHeader().setTimestamp(System.currentTimeMillis());
		ValiTicketByTicketNoRequestBody body = new ValiTicketByTicketNoRequestBody();
		request.setBody(body);
		//设置景区id
		request.getBody().setScenicSpotId("6f66ea1dc339446e874a0b3d5f9b946c");
		body.setTicketNo("100523007990");
		String json = JSON.toJSONString(request, true);
		StringBuilder params = new StringBuilder();
		String msg=URLEncoder.encode(json, "UTF-8");
		params.append("msg=").append(msg);
		params.append("&sign=").append(MD5HashUtil.toMD5(SystemConfig.scenicSpotPublicKey + json));

		System.out.println(json);
		System.out.println("params:");
		System.out.println(params);
		
		HttpResponse response = HttpUtil.reqForPost(url, params.toString(), 5000);

		System.out.println(response.getResult());
	}
	/**
	 * 
	 * @描述：根据证件验票 
	 * @author: guotx 
	 * @version: 2015-12-18 下午3:57:06
	 * @throws UnsupportedEncodingException 
	 */
	public static void validTicketByCer() throws UnsupportedEncodingException{
		ApiRequest<ValiTicketByCerRequestBody> request=new ApiRequest<ValiTicketByCerRequestBody>();
		request.getHeader().setActionName("ValiTicketByCer");
		request.getHeader().setTimestamp(System.currentTimeMillis());
		ValiTicketByCerRequestBody body = new ValiTicketByCerRequestBody();
		request.setBody(body);
		//手工输入身份证号
		body.setCerType(ValiTicketByCerRequestBody.CER_TYPE_IDENTITY);
		body.setIdNo("341202198802292332");
		body.setCheckWay(ValiTicketByCerRequestBody.CHECK_WAY_MANUAL);
		request.getBody().setScenicSpotId("389738ea0a7543d0b0391206e9d0f15d");
		
		String json = JSON.toJSONString(request, true);
		StringBuilder params = new StringBuilder();
		String msg=URLEncoder.encode(json, "UTF-8");
		System.out.println(json);
		params.append("msg=").append(msg);
		params.append("&sign=").append(MD5HashUtil.toMD5(SystemConfig.scenicSpotPublicKey + json));

		HttpResponse response = HttpUtil.reqForPost(url, params.toString(), 5000);
		System.out.println(response.getResult());
	}
	/**
	 * 
	 * @描述：核销 
	 * @author: guotx 
	 * @version: 2015-12-18 下午4:40:58
	 * @throws UnsupportedEncodingException 
	 */
	public static void useTicket() throws UnsupportedEncodingException{
		ApiRequest<UseTicketRequestBody> request=new ApiRequest<UseTicketRequestBody>();
		request.getHeader().setActionName("UseTicket");
		request.getHeader().setTimestamp(System.currentTimeMillis());
		request.setBody(new UseTicketRequestBody());
		request.getBody().setScenicSpotId("6f66ea1dc339446e874a0b3d5f9b946c");
		request.getBody().setCheckWay("2");
		request.getBody().setTicketNo("100523007990");
		
		String json = JSON.toJSONString(request, true);
		StringBuilder params = new StringBuilder();
		String msg=URLEncoder.encode(json, "UTF-8");
		System.out.println(json);
		params.append("msg=").append(msg);
		params.append("&sign=").append(MD5HashUtil.toMD5(SystemConfig.scenicSpotPublicKey + json));

		HttpResponse response = HttpUtil.reqForPost(url, params.toString(), 5000);
		System.out.println(response.getResult());
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
//		SystemConfig.scenicSpotPublicKey="1f9d394251434a4081618d2b4103bd17";
//		login();
		SystemConfig.scenicSpotPublicKey="2f0c68786b8d4135b5b461c08b15e2fb";
//		validTicketByTicketNo();
		validTicketByCer();
		
//		useTicket();
	}
	
	
}
