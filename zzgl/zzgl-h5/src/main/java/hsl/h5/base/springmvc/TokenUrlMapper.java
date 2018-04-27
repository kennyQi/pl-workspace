package hsl.h5.base.springmvc;

import java.util.LinkedList;
import java.util.List;

public class TokenUrlMapper {
	//表单路径映射表
	public static final List<String> MAPPER = new LinkedList<String>();
	
	static{
		MAPPER.add("/jpo/settle");
		MAPPER.add("/jpo/confirm");
		MAPPER.add("/jpo/pay");
		MAPPER.add("/hslH5/line/lineOrderPage");
		MAPPER.add("/hslH5/line/orderPay");
		MAPPER.add("/hslH5/line/detailToPayPage");
		
		MAPPER.add("/lineSalesPlan/toFillOrders");
		MAPPER.add("/lineSalesPlan/payOrder");
	}
	
	//表单提交路径映射表
	public static final List<String> VMAPPER = new LinkedList<String>();
	
	static{
		VMAPPER.add("/jpo/confirm");
		VMAPPER.add("/alipay/pay");
		VMAPPER.add("/hslH5/line/creatLineOrder");
		VMAPPER.add("/hslH5/line/payLineOrder");
		
		VMAPPER.add("/lineSalesPlan/createLSPOrder");
		VMAPPER.add("/lineSalesPlan/payLSPOrder");
		
	}

}
