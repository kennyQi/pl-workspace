package hsl.api;


import hsl.api.base.ApiRequest;
import hsl.api.base.HslApiClient;
import hsl.api.v1.request.command.coupon.ConsumeCouponListCommand;
import hsl.api.v1.response.coupon.ConsumeCouponResponse;
import hsl.pojo.command.coupon.ConsumeCouponCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
//package hsl.api;

public class Demo {

	public static void main(String[] args) throws ParseException {

		//	创建api客户端类，可复用
//		HslApiClient client = new HslApiClient("http://192.168.2.211:9080/hsl-api/api","weixin", "c196266f837d14e0b693f961bee37b66");
		HslApiClient client = new HslApiClient("http://127.0.0.1:8085/hsl-api/api","weixin", "c196266f837d14e0b693f961bee37b66");
		//HslApiClient client = new HslApiClient("http://192.168.10.21:8080/hsl-api/api","wx", "ghfdkgjs");
		
//		//获取验证码
//		SendValidCodeCommand command =new SendValidCodeCommand();
//		command.setMobile("18606536339");
//		command.setScene(1);
//		ApiRequest request=new ApiRequest("SendValidCode", "127.0.0.1", UUID.randomUUID().toString(), command, "1.0");
//		SendValidCodeResponse response=client.send(request, SendValidCodeResponse.class);
//		System.out.println(response.getMessage());
////		//用户注册
//		UserRegisterCommand registerCommand=new UserRegisterCommand();
//		registerCommand.setLoginName("yuqz");
//		registerCommand.setMobile("13754326300");
//		registerCommand.setPassword("123456");
//		registerCommand.setValidCode("701439");
//		registerCommand.setValidToken("018489cf6b754e9796a9aab6b1cbdc16");
//		registerCommand.setType(1);//个人1  企业2
//		ApiRequest registerRequest=new ApiRequest("UserRegister", "127.0.0.1", UUID.randomUUID().toString(), registerCommand, "1.0");
//		UserRegisterResponse registerResponse=client.send(registerRequest, UserRegisterResponse.class);
//		System.out.println(JSON.toJSONString(registerResponse));
//		
////		//用户绑定
//		BindWXCommand bindWXCommand=new BindWXCommand();
//		bindWXCommand.setUserId("61b58b97d5b441e9bd5cbd5f66b436aa");
//		bindWXCommand.setPassword("123456");
//		bindWXCommand.setWxAccountName("壮子");
//		bindWXCommand.setWxAccountId("lionlion");
//		ApiRequest bindRequest=new ApiRequest("BindWX", "127.0.0.1", UUID.randomUUID().toString(), bindWXCommand, "1.0");
//		BindWXResponse bindWXResponse=client.send(bindRequest, BindWXResponse.class);
//		System.out.println(JSON.toJSON(bindWXResponse));
////		
////		//更新密码
//		UserUpdatePasswordCommand updatePasswordCommand=new UserUpdatePasswordCommand();
//		updatePasswordCommand.setUserId("61b58b97d5b441e9bd5cbd5f66b436aa");
//		updatePasswordCommand.setNewPwd("123456a");
//		updatePasswordCommand.setIdentify("666975");
//		updatePasswordCommand.setValidToken("5f3887e104794425a81c8195fec9e40c");
//		ApiRequest updatePasswordRequest=new ApiRequest("UserUpdatePassword", "127.0.0.1", UUID.randomUUID().toString(), updatePasswordCommand, "1.0");
//		UserUpdatePasswordResponse updatePasswordResponse=client.send(updatePasswordRequest, UserUpdatePasswordResponse.class);
//		System.out.println(JSON.toJSONString(updatePasswordResponse));
////		
////		//用户登录
//		UserCheckQO userCheckQO=new UserCheckQO();
//		userCheckQO.setLoginName("yuqz");
//		userCheckQO.setPassword("123456");
//		ApiRequest userCheckApiRequest=new ApiRequest("UserCheck", "127.0.0.1", UUID.randomUUID().toString(), userCheckQO, "1.0");
//		UserCheckResponse userCheckResponse=client.send(userCheckApiRequest, UserCheckResponse.class);
//		System.out.println(JSON.toJSONString(userCheckResponse));
////		
////		//门票订单
////		MPOrderCreateCommand createCommand=new MPOrderCreateCommand();
////		createCommand.setNumber(1);
////		createCommand.setPolicyId('1231231231321');
////		createCommand.setPrice(200.00);
////		createCommand.setTravelDate(new Date());
////		
////		UserDTO userDTO=new UserDTO();
////		UserBaseInfoDTO baseInfoDTO=new UserBaseInfoDTO();
////		UserContactInfoDTO contactInfoDTO =new UserContactInfoDTO();
////		baseInfoDTO.setIdCardNo('410102198809120054');
////		baseInfoDTO.setName('刘伯承');
////		
////		contactInfoDTO.setMobile('18606536339');
////		userDTO.setBaseInfo(baseInfoDTO);
////		userDTO.setContactInfo(contactInfoDTO);
////		createCommand.setOrderUserInfo(userDTO);
////		
////		createCommand.setTakeTicketUserInfo(userDTO);
////		List<UserDTO>list=new ArrayList<UserDTO>();
////		list.add(userDTO);
////		createCommand.setTraveler(list);
////		ApiRequest createOrderRequest=new ApiRequest('MPCreateOrder', '127.0.0.1', UUID.randomUUID().toString(), createCommand, '1.0');
////		MPOrderCreateResponse createResponse=client.send(createOrderRequest, MPOrderCreateResponse.class);
////		System.out.println(JSON.toJSONString(createResponse));
////		
		//景点查询
//		MPScenicSpotsQO scenicSpotsQO=new MPScenicSpotsQO();
//		scenicSpotsQO.setScenicSpotId("d2960a35d5e74535b3205b1777419c70");
////		scenicSpotsQO.setContent("水上乐园");
//		scenicSpotsQO.setByName(false);
////		scenicSpotsQO.setArea("浙江");
//		scenicSpotsQO.setByArea(false);
//		scenicSpotsQO.setHot(true);
//		scenicSpotsQO.setPageNo(1);
//		scenicSpotsQO.setPageSize(20);
//		scenicSpotsQO.setTcPolicyNoticeFetchAble(false);
//		scenicSpotsQO.setPageSize(6);
//		scenicSpotsQO.setIsOpen(true);
//		scenicSpotsQO.setContent(null);
//		ApiRequest createOrderRequest=new ApiRequest("MPQueryScenicSpots", "127.0.0.1", UUID.randomUUID().toString(), scenicSpotsQO, "1.0");
//		MPQueryScenicSpotsResponse scenicSpotsResponse=client.send(createOrderRequest, MPQueryScenicSpotsResponse.class);
//		System.out.println(JSON.toJSONString(scenicSpotsResponse));
//		//价格日历查询
////		MPDatePriceQO datePriceQO=new MPDatePriceQO();
////		datePriceQO.setPolicyId("84177");
////		ApiRequest datePriceRequest=new ApiRequest("MPQueryDatePrice", "127.0.0.1", UUID.randomUUID().toString(), datePriceQO, "1.0");
////		MPQueryDatePriceResponse datePriceResponse=client.send(datePriceRequest, MPQueryDatePriceResponse.class);
////		System.out.println(JSON.toJSONString(datePriceResponse));
//
//		//景点政策查询
////		MPPolicyQO mpPolicyQO=new MPPolicyQO();
////		mpPolicyQO.setScenicSpotId("55f8f1da91cd46cf9056abad55460256");
////		ApiRequest policyRequest =new ApiRequest("MPQueryPolicy", "127.0.0.1", UUID.randomUUID().toString(), mpPolicyQO, "1.0");
////		MPQueryPolicyResponse policyResponse=client.send(policyRequest, MPQueryPolicyResponse.class);
////		System.out.println(JSON.toJSONString(policyResponse));
//		//门票预订
//		MPOrderCreateCommand command=new MPOrderCreateCommand();
//		command.setNumber(1);
//		command.setPolicyId("19889");
//		command.setPrice(50.00);
//		UserDTO userDTO=new UserDTO();
//		UserAuthInfoDTO userAuthInfoDTO=new UserAuthInfoDTO();
//		UserBaseInfoDTO userBaseInfoDTO=new UserBaseInfoDTO();
//		UserContactInfoDTO userContactInfoDTO =new UserContactInfoDTO();
//		userAuthInfoDTO.setLoginName("ljz880912");
//		userAuthInfoDTO.setPassword("880912ljz");
//		userBaseInfoDTO.setName("刘剑钊");
//		
//		userContactInfoDTO.setMobile("18606536339");
//		userDTO.setId("d3de7a7e5e4b4fa7ba1bb28fd065a617");
//		userDTO.setAuthInfo(userAuthInfoDTO);
//		userDTO.setBaseInfo(userBaseInfoDTO);
//		userDTO.setContactInfo(userContactInfoDTO);
//		command.setOrderUserInfo(userDTO);
//		command.setTakeTicketUserInfo(userDTO);
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		command.setTravelDate("2014-08-29");
//		command.setBookManIP("127.0.0.1");
//		List<UserDTO> list=new ArrayList<UserDTO>();
//		list.add(userDTO);
//		command.setTraveler(list);
//		ApiRequest policyRequest =new ApiRequest("MPCreateOrder", "127.0.0.1", UUID.randomUUID().toString(), command, "1.0");
//		MPOrderCreateResponse response=client.send(policyRequest, MPOrderCreateResponse.class);
//		System.out.println(JSON.toJSONString(response));
//		//订单查询
//		MPOrderQO qo=new MPOrderQO();
//		qo.setOrderId("cb58a2ac2bdc480a8bee870cb459116b");
//		//qo.setUserId("6400978b56cf4f87a2a2a5bb64c5b8e1");
//		qo.setWithPolicy(false);
//		qo.setWithScenicSpot(true);
//		ApiRequest policyRequest =new ApiRequest("MPQueryOrder", "127.0.0.1", UUID.randomUUID().toString(), qo,"1.0");
//		MPQueryOrderResponse response=client.send(policyRequest, MPQueryOrderResponse.class);
//		System.out.println(JSON.toJSONString(response));
//
//		
////		Random random=new Random();
////		System.out.println((int)(random.nextDouble()*10000000/10));
////		
//		//用户查询
//		UserQO userQO=new UserQO();
//		userQO.setUserId("61b58b97d5b441e9bd5cbd5f66b436aa");
//		//userQO.setBindAccountId("asdfghjkl");
//		ApiRequest userRequest =new ApiRequest("QueryUser", "127.0.0.1", UUID.randomUUID().toString(), userQO,"1.0");
//		QueryUserResponse response=client.send(userRequest, QueryUserResponse.class);
//		System.out.println(response.getMessage());
//		System.out.println(JSON.toJSONString(response));
		
		//编辑个人资料
//		UserEditCommand command = new UserEditCommand();
//		command.setUserId("61b58b97d5b441e9bd5cbd5f66b436aa");
//		command.setNickName("狮子");
//		command.setImage("23e1938a15-e3a0-4a41-a09f-eb4a054b4224.jpg");
//		command.setEmail("573749507@qq.com");
//		command.setProvince("31");
//		command.setCity("383");
//		ApiRequest userEidtRequest=new ApiRequest("UserEdit", "127.0.0.1", UUID.randomUUID().toString(), command, "1.0");
//		UserEditResponse userEditResponse=client.send(userEidtRequest, UserEditResponse.class);
//		System.out.println(JSON.toJSONString(userEditResponse));
		
		//绑定手机修改
//		UserEditMobileCommand userEditMobileCommand = new UserEditMobileCommand();
//		userEditMobileCommand.setUserId("61b58b97d5b441e9bd5cbd5f66b436aa");
//		userEditMobileCommand.setMobile("13754326300");
//		ApiRequest userEidtMobileRequest=new ApiRequest("UserEditMobile", "127.0.0.1", UUID.randomUUID().toString(), userEditMobileCommand, "1.0");
//		UserEditResponse userEditMobileResponse=client.send(userEidtMobileRequest, UserEditResponse.class);
//		System.out.println(JSON.toJSONString(userEditMobileResponse));
		
		//组织架构
//		CompanyQO companyQO = new CompanyQO();
//		companyQO.setUserId("c1a36bba7ea7487aadbd83fb679cc21e");
//		ApiRequest companyListRequest=new ApiRequest("CompanyList", "127.0.0.1", UUID.randomUUID().toString(), companyQO, "1.0");
//		CompanyListResponse companyListResponse=client.send(companyListRequest, CompanyListResponse.class);
//		System.out.println(JSON.toJSONString(companyListResponse));
		
		//组织架构搜索
//		CompanySearchQO companySearchQO = new CompanySearchQO();
//		companySearchQO.setUserId("2");
//		companySearchQO.setSearchName("部门");
//		ApiRequest companySearchRequest=new ApiRequest("CompanySearch", "127.0.0.1", UUID.randomUUID().toString(), companySearchQO, "1.0");
//		CompanySearchResponse companySearchResponse=client.send(companySearchRequest, CompanySearchResponse.class);
//		System.out.println(JSON.toJSONString(companySearchResponse));
		
		//根据部门id查询成员
//		MemberQO memberQO = new MemberQO();
//		memberQO.setDepartmentId("22");
//		ApiRequest memberListRequest=new ApiRequest("CoQueryMembers", "127.0.0.1", UUID.randomUUID().toString(), memberQO, "1.0");
//		MemberListResponse memberListResponse=client.send(memberListRequest, MemberListResponse.class);
//		System.out.println(JSON.toJSONString(memberListResponse));
		
		//根据成员id获取成员详情
//		MemberQO memberQO = new MemberQO();
//		memberQO.setId("222");//成员id
//		ApiRequest memberRequest=new ApiRequest("CoMemberDetail", "127.0.0.1", UUID.randomUUID().toString(), memberQO, "1.0");
//		MemberResponse memberResponse=client.send(memberRequest, MemberResponse.class);
//		System.out.println(JSON.toJSONString(memberResponse));
		
		//机票搜索
//		JPFlightQO jPFlightQO = new JPFlightQO();
//		jPFlightQO.setFrom("PEK");
//		jPFlightQO.setArrive("SZX");
//		jPFlightQO.setDate("2014-10-23");
//		ApiRequest apiRequest=new ApiRequest("JPQueryFlight", "127.0.0.1", UUID.randomUUID().toString(), jPFlightQO, "1.0");
//		JPQueryFlightResponse jPQueryFlightResponse=client.send(apiRequest, JPQueryFlightResponse.class);
//		System.out.println(JSON.toJSONString(jPQueryFlightResponse));
		
		//上传图片buff2Image
//		UploadImageCommand uploadImageCommand = new UploadImageCommand();
//		byte[] b = null;
//		try {
//			b = image2Buff("D:/1.jpg");
//		} catch (IOException e) {
//			//  Auto-generated catch block
//			e.printStackTrace();
//		}
//		uploadImageCommand.setBytes(b);
//		uploadImageCommand.setImageType("jpg");
//		ApiRequest apiRequest=new ApiRequest("UploadImage", "127.0.0.1", UUID.randomUUID().toString(), uploadImageCommand, "1.0");
//		UploadImageResponse uploadImageResponse=client.send(apiRequest, UploadImageResponse.class);
//		System.out.println(JSON.toJSONString(uploadImageResponse));
		
//		File file = new File("D:\\test2.jpg");
//		System.out.println(file.delete());
		//卡劵查询（已使用未使用）
//		CouponQueryQO couponQueryQO = new CouponQueryQO();
//		couponQueryQO.setUserId("c1a36bba7ea7487aadbd83fb679cc21e");
//		couponQueryQO.setIsNoCanUsed(true);
//		ApiRequest apiRequest=new ApiRequest("CouponQuery", "127.0.0.1", UUID.randomUUID().toString(), couponQueryQO, "1.0");
//		CouponQueryResponse couponQueryResponse=client.send(apiRequest, CouponQueryResponse.class);
//		System.out.println(JSON.toJSONString(couponQueryResponse));

		//消费卡劵
		ConsumeCouponListCommand consumeCouponListCommand = new ConsumeCouponListCommand();
		List<ConsumeCouponCommand> commands = new ArrayList<ConsumeCouponCommand>();
		ConsumeCouponCommand command = new ConsumeCouponCommand();
		command.setCouponId("csec39f6cbf543b47dcb12a7d64abff9e9a");
//		command.setDetail("detail");
		command.setOrderId("11");
//		command.setPayPrice(22.0);
		commands.add(command);
//		ConsumeCouponCommand command1 = new ConsumeCouponCommand();
//		command1.setCouponId("cse9e6b71c9eed1496fb34411b2ff1798e0");
//		command1.setDetail("detail");
//		command1.setOrderId("11");
//		command1.setPayPrice(22.0);
//		commands.add(command);
		consumeCouponListCommand.setCommandlist(commands);
		ApiRequest apiRequest=new ApiRequest("ConsumeCoupon", "127.0.0.1", UUID.randomUUID().toString(), consumeCouponListCommand, "1.0");
		ConsumeCouponResponse consumeCouponResponse=client.send(apiRequest, ConsumeCouponResponse.class);
		System.out.println(JSON.toJSONString(consumeCouponResponse));
	}
	@SuppressWarnings("unused")
	private static  byte[] image2Buff(String imgSrc) throws IOException{ 
		FileInputStream fin = new FileInputStream(new File(imgSrc));
		byte[] bytes = new byte[fin.available()];
		fin.read(bytes);
		fin.close();
		return bytes;
	}

}
