package service.test;

import com.alibaba.fastjson.JSON;
import hg.common.util.UUIDGenerator;
import hsl.app.common.util.ClientKeyUtil;
import org.apache.commons.lang.math.RandomUtils;
import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.command.xl.XLPayLineOrderApiCommand;
import slfx.api.v1.response.xl.XLPayLineOrderResponse;

import java.util.Date;

/**
 * @author zhurz
 */
public class TestSlfxApi {


	public static void main(String[] args) {
//		http://127.0.0.1:8082/slfx-admin/traveline/order/detail?id=b706467e81a64f3799a0bc262fd47ffb
		XLPayLineOrderApiCommand command = new XLPayLineOrderApiCommand();
		command.setDealerOrderNo("BA30151427010000");
		command.setPaymentType(1);
		command.setPaymentAccount("test@qq.com");
		command.setPaymentName("测试姓名");
		command.setSerialNumber("11111222");
//		command.setPaymentAmount(randomutils.nextInt(100) * 1d + 1);
		command.setPaymentAmount(77d);
		command.setPaymentTime(new Date());
		// 付款类型：1:定金；2：尾款；3全款
		command.setShopPayType(3);
		ApiRequest apiRequest = new ApiRequest("XLPayLineOrder",
				ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), command);
//		SlfxApiClient slfxApiClient = new SlfxApiClient("http://192.168.2.211:17080/slfx-api/slfx/api", "hsl", "abc");
		SlfxApiClient slfxApiClient = new SlfxApiClient("http://127.0.0.1:8084/slfx-api/slfx/api", "hsl", "abc");
		ApiResponse response = slfxApiClient.send(apiRequest, XLPayLineOrderResponse.class);
		System.out.println(JSON.toJSONString(response, true));

	}
}
