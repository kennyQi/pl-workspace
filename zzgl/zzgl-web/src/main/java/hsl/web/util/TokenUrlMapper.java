package hsl.web.util;

import java.util.LinkedList;
import java.util.List;

public class TokenUrlMapper {
	//表单路径映射表
	public static final List<String> MAPPER = new LinkedList<String>();
	
	static{
		MAPPER.add("/mp/scart");
		MAPPER.add("/user/register");
		MAPPER.add("/comRegister/main");
		MAPPER.add("/jp/price");
		MAPPER.add("/jp/createOrder");
		MAPPER.add("/jp/toPayOrderInfo");
		MAPPER.add("/line/detail");
	}
	
	//表单提交路径映射表
	public static final List<String> VMAPPER = new LinkedList<String>();
	
	static{
		VMAPPER.add("/mp/handle");
		VMAPPER.add("/user/register/regUser");
		VMAPPER.add("/comRegister/register");
		VMAPPER.add("/jp/createOrder");
		VMAPPER.add("/jp/payOrder");
		VMAPPER.add("/line/saveOrder");
	}

}
