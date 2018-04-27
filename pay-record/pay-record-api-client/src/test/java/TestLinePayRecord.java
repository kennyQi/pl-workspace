
import org.junit.Test;

import pay.record.api.client.api.v1.pay.record.request.line.CreateLineUTJPayReocrdCommand;
import pay.record.api.client.common.ApiResponse;
import pay.record.api.client.common.exception.PayRecordApiException;
import pay.record.api.client.common.util.PayRecordApiClient;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路支付记录测试
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月8日上午11:14:31
 * @版本：V1.0
 *
 */
public class TestLinePayRecord {
	
	private String httpUrl = "http://127.0.0.1:8080/pay-record-api/api";
	
	/**
	 * RSA模
	 */
	private String modulus = "124772110688646963986080649555964103539422188358187828168937575305950354245761868431506632387775213795975959903801685687213725513485979867335122668713621985468566123166884763277431607358336804344573792493151032125487645367798247528849276077786351681288665334101843452187400145922866688656369859774560299619129";
	
	/**
	 * RSA指数
	 */
	private String public_exponent = "65537";

	/**
	 * 
	 */
    @Test
	public void testJPQueryFlight() {
		// 创建api客户端类
		PayRecordApiClient client;
		try {
			client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
			CreateLineUTJPayReocrdCommand command = new CreateLineUTJPayReocrdCommand();
			command.setFromProjectCode(1000);
			ApiResponse response = client.send(command, ApiResponse.class);
			System.out.println(JSON.toJSONString(response));
		} catch (PayRecordApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
