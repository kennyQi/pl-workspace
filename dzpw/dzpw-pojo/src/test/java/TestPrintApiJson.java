import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;

import hg.dzpw.pojo.api.appv1.base.ApiRequest;
import hg.dzpw.pojo.api.appv1.base.ApiRequestHead;
import hg.dzpw.pojo.api.appv1.dto.TicketDto;
import hg.dzpw.pojo.api.appv1.request.LoginRequestBody;
import hg.dzpw.pojo.api.appv1.request.SettingRequestBody;
import hg.dzpw.pojo.api.appv1.request.UseTicketRequestBody;
import hg.dzpw.pojo.api.appv1.request.ValiTicketByCerRequestBody;
import hg.dzpw.pojo.api.appv1.request.ValiTicketByTicketNoRequestBody;
import hg.dzpw.pojo.api.appv1.response.LoginResponse;
import hg.dzpw.pojo.api.appv1.response.SettingResponse;
import hg.dzpw.pojo.api.appv1.response.UseTicketResponse;
import hg.dzpw.pojo.api.appv1.response.ValiTicketByCerResponse;
import hg.dzpw.pojo.api.appv1.response.ValiTicketByTicketNoResponse;


public class TestPrintApiJson {
	public static void main(String[] args) {
		
		ApiRequestHead header = new ApiRequestHead();
		header.setActionName("Login");
		header.setDeviceId("DeviceId-xxxxxxx");
		header.setSessionId("sessionId-xxxxxx");
		header.setTimestamp(System.currentTimeMillis());

		ApiRequest<LoginRequestBody> loginRequest = new ApiRequest<LoginRequestBody>();
		LoginRequestBody login = new LoginRequestBody();
		loginRequest.setBody(login);
		loginRequest.setHeader(header);
		login.setLoginName("admin");
		login.setPassword("123456");

		ApiRequest<SettingRequestBody> settingRequest = new ApiRequest<SettingRequestBody>();
		SettingRequestBody setting = new SettingRequestBody();
		settingRequest.setBody(setting);
		settingRequest.setHeader(header);
		setting.setManualCheckCertificate(true);

		ApiRequest<ValiTicketByCerRequestBody> valiTicketByCerRequest = new ApiRequest<ValiTicketByCerRequestBody>();
		ValiTicketByCerRequestBody valiTicketByCer = new ValiTicketByCerRequestBody();
		valiTicketByCerRequest.setBody(valiTicketByCer);
		valiTicketByCerRequest.setHeader(header);
		valiTicketByCer.setCheckWay("1");
		valiTicketByCer.setCerType("1");
		valiTicketByCer.setIdNo("110110110");

		ApiRequest<ValiTicketByTicketNoRequestBody> valiTicketByTicketNoRequest = new ApiRequest<ValiTicketByTicketNoRequestBody>();
		ValiTicketByTicketNoRequestBody valiTicketByTicketNo = new ValiTicketByTicketNoRequestBody();
		valiTicketByTicketNoRequest.setBody(valiTicketByTicketNo);
		valiTicketByTicketNoRequest.setHeader(header);
		valiTicketByTicketNo.setTicketNo("T110");

		ApiRequest<UseTicketRequestBody> useTicketRequest = new ApiRequest<UseTicketRequestBody>();
		UseTicketRequestBody useTicket = new UseTicketRequestBody();
		useTicketRequest.setBody(useTicket);
		useTicketRequest.setHeader(header);
		useTicket.setTicketNo("T120");
		
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setMessage("登录成功");
		loginResponse.setResult("1");
		
		SettingResponse settingResponse = new SettingResponse();
		settingResponse.setMessage("设置成功");
		settingResponse.setResult("1");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ValiTicketByCerResponse valiTicketByCerResponse = new ValiTicketByCerResponse();
		valiTicketByCerResponse.setMessage("查询成功");
		valiTicketByCerResponse.setResult("1");
		TicketDto ticketDto1 = new TicketDto();
		ticketDto1.setTicketNo("T110");
		ticketDto1.setTicketPolicyName("五岳联票");
		ticketDto1.setRemark("包含景点：华山、衡山、恒山、嵩山、泰山");
		TicketDto ticketDto2 = new TicketDto();
		ticketDto2.setTicketNo("T120");
		ticketDto2.setTicketPolicyName("道教联盟联票");
		ticketDto2.setRemark("包含景点：华山、泰山、嵩山、衡山、恒山");
		valiTicketByCerResponse.getTickets().add(ticketDto1);
		valiTicketByCerResponse.getTickets().add(ticketDto2);
		
		ValiTicketByTicketNoResponse valiTicketByTicketNoResponse = new ValiTicketByTicketNoResponse();
		valiTicketByTicketNoResponse.setMessage("查询成功");
		valiTicketByTicketNoResponse.setResult("1");
		TicketDto detailDto = new TicketDto();
		detailDto.setTicketNo("T110");
		detailDto.setTicketPolicyName("五岳联票");
		detailDto.setRemark("包含景点：华山、衡山、恒山、嵩山、泰山");
		detailDto.setCerType("1");
		detailDto.setIdNo("110110110");
		detailDto.setTouristName("张三");
		valiTicketByTicketNoResponse.setTicket(detailDto);
		
		UseTicketResponse useTicketResponse = new UseTicketResponse();
		useTicketResponse.setResult("1");
		useTicketResponse.setMessage("核销成功");

		System.out.println("=================登录");
		System.out.println("请求");
		header.setActionName("Login");
		System.out.println(JSON.toJSONString(loginRequest, true));
		System.out.println("响应");
		System.out.println(JSON.toJSONString(loginResponse, true));
		
		System.out.println("=================设置");
		System.out.println("请求");
		header.setActionName("Setting");
		System.out.println(JSON.toJSONString(settingRequest, true));
		System.out.println("响应");
		System.out.println(JSON.toJSONString(settingResponse, true));
		
		System.out.println("=================身份证件验票");
		System.out.println("请求");
		header.setActionName("ValiTicketByCer");
		System.out.println(JSON.toJSONString(valiTicketByCerRequest, true));
		System.out.println("响应");
		System.out.println(JSON.toJSONString(valiTicketByCerResponse, true));
		
		System.out.println("=================票号验票");
		System.out.println("请求");
		header.setActionName("ValiTicketByTicketNo");
		System.out.println(JSON.toJSONString(valiTicketByTicketNoRequest, true));
		System.out.println("响应");
		System.out.println(JSON.toJSONString(valiTicketByTicketNoResponse, true));
		
		System.out.println("=================确认核销门票");
		System.out.println("请求");
		header.setActionName("UseTicket");
		System.out.println(JSON.toJSONString(useTicketRequest, true));
		System.out.println("响应");
		System.out.println(JSON.toJSONString(useTicketResponse, true));
	}
}
