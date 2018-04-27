//package hsl.api;
//
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
////package hsl.api;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//
//import com.alibaba.fastjson.JSON;
//
//import hsl.api.base.ApiRequest;
//import hsl.api.base.ApiResponse;
//import hsl.api.base.HslApiClient;
//import hsl.api.v1.request.command.jp.JPOrderCreateCommand;
//import hsl.api.v1.request.command.mp.MPOrderCreateCommand;
//import hsl.api.v1.request.command.user.BindWXCommand;
//import hsl.api.v1.request.command.user.SendValidCodeCommand;
//import hsl.api.v1.request.command.user.UserRegisterCommand;
//import hsl.api.v1.request.command.user.UserUpdatePasswordCommand;
//import hsl.api.v1.request.qo.mp.MPDatePriceQO;
//import hsl.api.v1.request.qo.mp.MPOrderQO;
//import hsl.api.v1.request.qo.mp.MPPolicyQO;
//import hsl.api.v1.request.qo.mp.MPScenicSpotsQO;
//import hsl.api.v1.request.qo.user.UserCheckQO;
//import hsl.api.v1.request.qo.user.UserQO;
//import hsl.api.v1.response.jp.JPCreateOrderResponse;
//import hsl.api.v1.response.mp.MPOrderCreateResponse;
//import hsl.api.v1.response.mp.MPQueryDatePriceResponse;
//import hsl.api.v1.response.mp.MPQueryOrderResponse;
//import hsl.api.v1.response.mp.MPQueryPolicyResponse;
//import hsl.api.v1.response.mp.MPQueryScenicSpotsResponse;
//import hsl.api.v1.response.user.BindWXResponse;
//import hsl.api.v1.response.user.QueryUserResponse;
//import hsl.api.v1.response.user.SendValidCodeResponse;
//import hsl.api.v1.response.user.UserCheckResponse;
//import hsl.api.v1.response.user.UserRegisterResponse;
//import hsl.api.v1.response.user.UserUpdatePasswordResponse;
//import hsl.pojo.dto.user.UserAuthInfoDTO;
//import hsl.pojo.dto.user.UserBaseInfoDTO;
//import hsl.pojo.dto.user.UserBindAccountDTO;
//import hsl.pojo.dto.user.UserContactInfoDTO;
//import hsl.pojo.dto.user.UserDTO;
//
//public class Demo {
//
//	public static void main(String[] args) throws ParseException {
//
//		//	创建api客户端类，可复用
////		HslApiClient client = new HslApiClient("http://192.168.2.211:9080/hsl-api/api","weixin", "c196266f837d14e0b693f961bee37b66");
//		HslApiClient client = new HslApiClient("http://127.0.0.1:8080/hsl-api/api","weixin", "c196266f837d14e0b693f961bee37b66");
//		//HslApiClient client = new HslApiClient("http://192.168.10.21:8080/hsl-api/api","wx", "ghfdkgjs");
//		
////		//获取验证码
//////		SendValidCodeCommand command =new SendValidCodeCommand();
//////		command.setMobile("18606536339");
//////		command.setScene(2);
//////		ApiRequest request=new ApiRequest("SendValidCode", "127.0.0.1", UUID.randomUUID().toString(), command, "1.0");
//////		SendValidCodeResponse response=client.send(request, SendValidCodeResponse.class);
//////		System.out.println(response.getMessage());
//////		//用户注册
//////		UserRegisterCommand registerCommand=new UserRegisterCommand();
//////		registerCommand.setLoginName("ljz880912");
//////		registerCommand.setMobile("18606536339");
//////		registerCommand.setPassword("ljz880912");
//////		registerCommand.setValidCode("701439");
//////		registerCommand.setValidToken("018489cf6b754e9796a9aab6b1cbdc16");
//////		ApiRequest registerRequest=new ApiRequest("UserRegister", "127.0.0.1", UUID.randomUUID().toString(), registerCommand, "1.0");
//////		UserRegisterResponse registerResponse=client.send(registerRequest, UserRegisterResponse.class);
//////		System.out.println(JSON.toJSONString(registerResponse));
////		
//////		//用户绑定
//////		BindWXCommand bindWXCommand=new BindWXCommand();
//////		bindWXCommand.setUserId("d3de7a7e5e4b4fa7ba1bb28fd065a617");
//////		bindWXCommand.setPassword("ljz880912");
//////		bindWXCommand.setWxAccountName("忧伤");
//////		bindWXCommand.setWxAccountId("asdfghjkl");
//////		ApiRequest bindRequest=new ApiRequest("BindWX", "127.0.0.1", UUID.randomUUID().toString(), bindWXCommand, "1.0");
//////		BindWXResponse bindWXResponse=client.send(bindRequest, BindWXResponse.class);
//////		System.out.println(JSON.toJSON(bindWXResponse));
//////		
//////		//更新密码
//////		UserUpdatePasswordCommand updatePasswordCommand=new UserUpdatePasswordCommand();
//////		updatePasswordCommand.setUserId("d3de7a7e5e4b4fa7ba1bb28fd065a617");
//////		updatePasswordCommand.setNewPwd("880912ljz");
//////		updatePasswordCommand.setIdentify("666975");
//////		updatePasswordCommand.setValidToken("5f3887e104794425a81c8195fec9e40c");
//////		ApiRequest updatePasswordRequest=new ApiRequest("UserUpdatePassword", "127.0.0.1", UUID.randomUUID().toString(), updatePasswordCommand, "1.0");
//////		UserUpdatePasswordResponse updatePasswordResponse=client.send(updatePasswordRequest, UserUpdatePasswordResponse.class);
//////		System.out.println(JSON.toJSONString(updatePasswordResponse));
//////		
//////		//用户登录
//////		UserCheckQO userCheckQO=new UserCheckQO();
//////		userCheckQO.setLoginName("ljz880912");
//////		userCheckQO.setPassword("ljz880912");
//////		ApiRequest userCheckApiRequest=new ApiRequest("UserCheck", "127.0.0.1", UUID.randomUUID().toString(), userCheckQO, "1.0");
//////		UserCheckResponse userCheckResponse=client.send(userCheckApiRequest, UserCheckResponse.class);
//////		System.out.println(JSON.toJSONString(userCheckResponse));
//////		
//////		//门票订单
//////		MPOrderCreateCommand createCommand=new MPOrderCreateCommand();
//////		createCommand.setNumber(1);
//////		createCommand.setPolicyId('1231231231321');
//////		createCommand.setPrice(200.00);
//////		createCommand.setTravelDate(new Date());
//////		
//////		UserDTO userDTO=new UserDTO();
//////		UserBaseInfoDTO baseInfoDTO=new UserBaseInfoDTO();
//////		UserContactInfoDTO contactInfoDTO =new UserContactInfoDTO();
//////		baseInfoDTO.setIdCardNo('410102198809120054');
//////		baseInfoDTO.setName('刘伯承');
//////		
//////		contactInfoDTO.setMobile('18606536339');
//////		userDTO.setBaseInfo(baseInfoDTO);
//////		userDTO.setContactInfo(contactInfoDTO);
//////		createCommand.setOrderUserInfo(userDTO);
//////		
//////		createCommand.setTakeTicketUserInfo(userDTO);
//////		List<UserDTO>list=new ArrayList<UserDTO>();
//////		list.add(userDTO);
//////		createCommand.setTraveler(list);
//////		ApiRequest createOrderRequest=new ApiRequest('MPCreateOrder', '127.0.0.1', UUID.randomUUID().toString(), createCommand, '1.0');
//////		MPOrderCreateResponse createResponse=client.send(createOrderRequest, MPOrderCreateResponse.class);
//////		System.out.println(JSON.toJSONString(createResponse));
//////		
//		//景点查询
//		MPScenicSpotsQO scenicSpotsQO=new MPScenicSpotsQO();
//		scenicSpotsQO.setScenicSpotId("d2960a35d5e74535b3205b1777419c70");
//////		scenicSpotsQO.setContent("水上乐园");
////		scenicSpotsQO.setByName(false);
//////		scenicSpotsQO.setArea("浙江");
////		scenicSpotsQO.setByArea(false);
////		scenicSpotsQO.setHot(true);
//		scenicSpotsQO.setPageNo(1);
//		scenicSpotsQO.setPageSize(20);
//		scenicSpotsQO.setTcPolicyNoticeFetchAble(false);
//		scenicSpotsQO.setPageSize(6);
//		scenicSpotsQO.setIsOpen(true);
//		scenicSpotsQO.setContent(null);
//		ApiRequest createOrderRequest=new ApiRequest("MPQueryScenicSpots", "127.0.0.1", UUID.randomUUID().toString(), scenicSpotsQO, "1.0");
//		MPQueryScenicSpotsResponse scenicSpotsResponse=client.send(createOrderRequest, MPQueryScenicSpotsResponse.class);
//		System.out.println(JSON.toJSONString(scenicSpotsResponse));
////		//价格日历查询
//////		MPDatePriceQO datePriceQO=new MPDatePriceQO();
//////		datePriceQO.setPolicyId("84177");
//////		ApiRequest datePriceRequest=new ApiRequest("MPQueryDatePrice", "127.0.0.1", UUID.randomUUID().toString(), datePriceQO, "1.0");
//////		MPQueryDatePriceResponse datePriceResponse=client.send(datePriceRequest, MPQueryDatePriceResponse.class);
//////		System.out.println(JSON.toJSONString(datePriceResponse));
////
//		//景点政策查询
//		MPPolicyQO mpPolicyQO=new MPPolicyQO();
//		mpPolicyQO.setScenicSpotId("55f8f1da91cd46cf9056abad55460256");
//		ApiRequest policyRequest =new ApiRequest("MPQueryPolicy", "127.0.0.1", UUID.randomUUID().toString(), mpPolicyQO, "1.0");
//		MPQueryPolicyResponse policyResponse=client.send(policyRequest, MPQueryPolicyResponse.class);
//		System.out.println(JSON.toJSONString(policyResponse));
//		//门票预订
////		MPOrderCreateCommand command=new MPOrderCreateCommand();
////		command.setNumber(1);
////		command.setPolicyId("19889");
////		command.setPrice(50.00);
////		UserDTO userDTO=new UserDTO();
////		UserAuthInfoDTO userAuthInfoDTO=new UserAuthInfoDTO();
////		UserBaseInfoDTO userBaseInfoDTO=new UserBaseInfoDTO();
////		UserContactInfoDTO userContactInfoDTO =new UserContactInfoDTO();
////		userAuthInfoDTO.setLoginName("ljz880912");
////		userAuthInfoDTO.setPassword("880912ljz");
////		userBaseInfoDTO.setName("刘剑钊");
////		
////		userContactInfoDTO.setMobile("18606536339");
////		userDTO.setId("d3de7a7e5e4b4fa7ba1bb28fd065a617");
////		userDTO.setAuthInfo(userAuthInfoDTO);
////		userDTO.setBaseInfo(userBaseInfoDTO);
////		userDTO.setContactInfo(userContactInfoDTO);
////		command.setOrderUserInfo(userDTO);
////		command.setTakeTicketUserInfo(userDTO);
////		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
////		command.setTravelDate("2014-08-29");
////		command.setBookManIP("127.0.0.1");
////		List<UserDTO> list=new ArrayList<UserDTO>();
////		list.add(userDTO);
////		command.setTraveler(list);
////		ApiRequest policyRequest =new ApiRequest("MPCreateOrder", "127.0.0.1", UUID.randomUUID().toString(), command, "1.0");
////		MPOrderCreateResponse response=client.send(policyRequest, MPOrderCreateResponse.class);
////		System.out.println(JSON.toJSONString(response));
////		//订单查询
////		MPOrderQO qo=new MPOrderQO();
////		qo.setOrderId("cb58a2ac2bdc480a8bee870cb459116b");
////		//qo.setUserId("6400978b56cf4f87a2a2a5bb64c5b8e1");
////		qo.setWithPolicy(false);
////		qo.setWithScenicSpot(true);
////		ApiRequest policyRequest =new ApiRequest("MPQueryOrder", "127.0.0.1", UUID.randomUUID().toString(), qo,"1.0");
////		MPQueryOrderResponse response=client.send(policyRequest, MPQueryOrderResponse.class);
////		System.out.println(JSON.toJSONString(response));
////
////		
//////		Random random=new Random();
//////		System.out.println((int)(random.nextDouble()*10000000/10));
//////		
////		//用户查询
//////		UserQO userQO=new UserQO();
//////		userQO.setUserId("edcc737f75d94efdaec027bbe69c2ba2");
//////		//userQO.setBindAccountId("asdfghjkl");
//////		ApiRequest userRequest =new ApiRequest("QueryUser", "127.0.0.1", UUID.randomUUID().toString(), userQO,"1.0");
//////		QueryUserResponse response=client.send(userRequest, QueryUserResponse.class);
//////		System.out.println(response.getMessage());
//	}
//}
