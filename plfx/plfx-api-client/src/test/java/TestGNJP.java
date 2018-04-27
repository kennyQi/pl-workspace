
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import plfx.api.client.api.v1.gn.dto.PassengerGNDTO;
import plfx.api.client.api.v1.gn.dto.PassengerInfoGNDTO;
import plfx.api.client.api.v1.gn.request.CancelTicketGNCommand;
import plfx.api.client.api.v1.gn.request.JPAutoOrderGNCommand;
import plfx.api.client.api.v1.gn.request.JPBookTicketGNCommand;
import plfx.api.client.api.v1.gn.request.JPFlightGNQO;
import plfx.api.client.api.v1.gn.request.JPPayOrderGNCommand;
import plfx.api.client.api.v1.gn.request.JPPolicyGNQO;
import plfx.api.client.api.v1.gn.request.RefundTicketGNCommand;
import plfx.api.client.api.v1.gn.response.CancelTicketGNResponse;
import plfx.api.client.api.v1.gn.response.JPAutoOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPBookOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPPayOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryFlightListGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryHighPolicyGNResponse;
import plfx.api.client.api.v1.gn.response.RefundTicketGNResponse;
import plfx.api.client.common.util.PlfxApiClient;

import com.alibaba.fastjson.JSON;

/****
 * 
 * @类功能说明：国内机票测试
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午1:16:25
 * @版本：V1.0
 * 
 */
public class TestGNJP {
	
	private String httpUrl = "http://127.0.0.1:8080/plfx-api/api";
	
	private String dealerKey = "F4001";//经销商代码
	
	private String secretKey = "123456";//经销商key

	/**
	 * 航班查询
	 */
//    @Test
	public void testJPQueryFlight() {
		// 创建api客户端类
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		// 创建要发送的业务内容qo
		JPFlightGNQO qo = new JPFlightGNQO();
		// 出发地三字码(必填字段)
		//qo.setOrgCity("HGH");// 上海
		// 目的地三字码(必填字段)
	//	qo.setDstCity("HRB");//天津 
		
		qo.setOrgCity("PVG");// 上海
		// 目的地三字码(必填字段)
		qo.setDstCity("CGQ");//天津 
//		qo.setAirCompany("中国东方航空公司");
		qo.setAirCompanyShotName("吉祥");
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd=new SimpleDateFormat("HH:mm");
//		String startTime="12:50";
		String startDate="2016-02-17";
		try {
			// 起飞日期(必填字段)
			qo.setStartDate(sp.parse(startDate));
//			 起飞时间(非必填字段)
//			qo.setStartTime(sd.parse(startTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 发送请求
		JPQueryFlightListGNResponse response = client.send(qo,
				JPQueryFlightListGNResponse.class);
		System.out.println(JSON.toJSON(response));
//		System.out.println(JSON.toJSONString(response));

	}

	/**
	 * 政策查询
	 */
//	@Test
	public void testJPQueryFlightPolicy() {
		// 创建api客户端类
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		// 创建要发送的业务内容qo
		JPPolicyGNQO qo = new JPPolicyGNQO();
		// 政策取向(非必填字段)
		qo.setAirpGet("");
		// 政策来源(非必填字段)
		qo.setAirpSource("");
		// 票号类型(必填字段)
		qo.setTickType(1);
		// 上一步查询航班返回的加密字符串(必填字段)
		qo.setEncryptString("1fdeaeba64b1cfd691f503d148393c73fc5d0267392cea31c96e197f374277bc3abc13018fe8d851e4e4d9df0e054487e8622af138ab48bd1fc038c10761f4115278c57a4f75ae0541971af7b8d1e194284f263c3fd8c11a");
		// 创建要发送的请求对象
		JPQueryHighPolicyGNResponse response = client.send(qo,
				JPQueryHighPolicyGNResponse.class);
//		System.out.println(JSON.toJSON(response));
		System.out.println(JSON.toJSONString(response));
	}

	/**
	 * 订单创建
	 */
	@Test
	public void testJPOrderCreate() {
		// 创建api客户端类
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		// 创建要发送的业务内容command
		JPBookTicketGNCommand command = new JPBookTicketGNCommand();
		// 航班(必填字段)
		command.setFromDealerId(dealerKey);
		command.setDealerOrderId(UUID.randomUUID().toString().replace("-", ""));
		command.setFlightNo("HO1189");
		// 起飞时间(必填字段)
		command.setCabinCode("A");
		command.setCabinName("经济舱");
		command.setUserPayAmount(1479.0);
	//	command.setStartDate("2015-07-28 08:05");
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		String startDate="2016-02-17";
		try {
			command.setStartDate(sp.parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 加密字符串(必填字段)
		command.setEncryptString("1fdeaeba64b1cfd691f503d148393c73fc5d0267392cea31c96e197f374277bc3abc13018fe8d8510a8a3338004685ac8f6bb637821637f2f31951e14f2098d2f5c4d1d00b27674794279556e32b65783e1f6b26b73fb35a4f2c90b4e9a8ad5e7f64f31d7af5c37f2c4237565595f85528d5a024fafb334dad618f4ac34053e14d3b8c6134e00575ad6a88b4fa37833d");
		PassengerInfoGNDTO passengerInfoDTO = new PassengerInfoGNDTO();
		// 乘客信息集合(必填字段)
		List<PassengerGNDTO> passengerList = new ArrayList<PassengerGNDTO>();
		// 联系人姓名(必填字段)
		passengerInfoDTO.setName("李艳勇");
		// 联系人手机(必填字段,且只能填一个)
		passengerInfoDTO.setTelephone("18458109377");
		PassengerGNDTO psg = new PassengerGNDTO();
		// 姓名(必填字段)
		psg.setPassengerName("胡小晴");
		// 乘客类型(必填字段)
		psg.setPassengerType("1");
		// 身份证号(必填字段)
		psg.setIdNo("330327199501013031");
		// 证件类型(必填字段)
		psg.setIdType("0");
		passengerList.add(psg);
//
		PassengerGNDTO psgg = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg.setPassengerName("赵啥勇");
		// 乘客类型(必填字段)
		psgg.setPassengerType("1");
		// 身份证号(必填字段)
		psgg.setIdNo("897644198501231657");
		// 证件类型(必填字段)
		psgg.setIdType("0");
		//passengerList.add(psgg);
		
		PassengerGNDTO psggg = new PassengerGNDTO();
		// 姓名(必填字段)
		psggg.setPassengerName("张安勇");
		// 乘客类型(必填字段)
		psggg.setPassengerType("1");
		// 身份证号(必填字段)
		psggg.setIdNo("331023199102060534");
		// 证件类型(必填字段)
		psggg.setIdType("0");
//		passengerList.add(psggg);
		
		PassengerGNDTO psgg1 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg1.setPassengerName("阿勇");
		// 乘客类型(必填字段)
		psgg1.setPassengerType("1");
		// 身份证号(必填字段)
		psgg1.setIdNo("897644198501231657");
		// 证件类型(必填字段)
		psgg1.setIdType("0");
	//	passengerList.add(psgg1);
		
		PassengerGNDTO psgg2 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg2.setPassengerName("李连勇");
		// 乘客类型(必填字段)
		psgg2.setPassengerType("1");
		// 身份证号(必填字段)
		psgg2.setIdNo("897644198501231657");
		// 证件类型(必填字段)
		psgg2.setIdType("0");
	//	passengerList.add(psgg2);
		
		PassengerGNDTO psgg3 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg3.setPassengerName("张宝勇");
		// 乘客类型(必填字段)
		psgg3.setPassengerType("1");
		// 身份证号(必填字段)
		psgg3.setIdNo("411023198904102519");
		// 证件类型(必填字段)
		psgg3.setIdType("0");
	//	passengerList.add(psgg3);
		
		PassengerGNDTO psgg4 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg4.setPassengerName("李青勇");
		// 乘客类型(必填字段)
		psgg4.setPassengerType("1");
		// 身份证号(必填字段)
		psgg4.setIdNo("510824199108015796");
		// 证件类型(必填字段)
		psgg4.setIdType("0");
	//	passengerList.add(psgg4);

		passengerInfoDTO.setPassengerList(passengerList);
		command.setPassengerInfoGNDTO(passengerInfoDTO);
//		System.out.println(JSON.toJSONString(command));
		// 发送请求
		JPBookOrderGNResponse response = client.send(command,
				JPBookOrderGNResponse.class);
		// 查看返回内容
		System.out.println(JSON.toJSONString(response));

	}

	/*****
	 * 
	 * @方法功能说明：申请取消
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月6日下午4:22:09
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
//	@Test
	public void testJPOrderCancel() {
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		CancelTicketGNCommand command = new CancelTicketGNCommand();
		// 易行天下订单号
		command.setDealerOrderId("fb83616c9e084cd3b1ac23ad3ba83feb");
		// 乘客信息/多人用^隔开
		command.setPassengerName("胡小晴");
		// 经销商
		command.setFromDealerId("F4001");
		CancelTicketGNResponse response = client.send(command,
				CancelTicketGNResponse.class);
		System.out.println(JSON.toJSONString(response));
	}

	/***
	 * 
	 * @方法功能说明：申请退废
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月6日下午4:22:35
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */

	// @Test
	public void testJPOrderRefund() {
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		RefundTicketGNCommand command = new RefundTicketGNCommand();
		// 易行天下订单号
		command.setDealerOrderId("T134513215341");
		// 乘客信息/多人用^隔开
		command.setPassengerName("姚三丰");
		// 机票票号 ,票号之间用 ^分隔，并与姓名相对应
		command.setAirId("8416848641651");
		// 申请理由
		command.setRefundMemo("2");
		// 要退的航段
		command.setRefundSegment("");
		// 申请种类
		command.setRefundType("2");
		// 经销商
		command.setFromDealerId("F001");
		RefundTicketGNResponse response = client.send(command,
				RefundTicketGNResponse.class);

		System.out.println(JSON.toJSONString(response));
	}
	
	/**
	 * 
	 * @方法功能说明：自动扣款
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月22日下午5:47:01
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
//	@Test
	public void testPayJPOrder(){
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		JPPayOrderGNCommand command = new JPPayOrderGNCommand();
		command.setDealerOrderId("2121fac282d54d76a7a13018744c1ae5");
		command.setTotalPrice(7410.00);
		JPPayOrderGNResponse response = client.send(command,
				JPPayOrderGNResponse.class);

		System.out.println(JSON.toJSONString(response));
	}
	
	/****
	 * 
	 * @方法功能说明：生成订单并自动扣款
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月8日上午11:00:47
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
//	@Test
	public void testAutoOrder(){
		// 创建api客户端类
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		// 创建要发送的业务内容command
		JPAutoOrderGNCommand command = new JPAutoOrderGNCommand();
		// 航班(必填字段)
		command.setFromDealerId(dealerKey);
		command.setDealerOrderId(UUID.randomUUID().toString().replace("-", ""));
		command.setFlightNo("MU2514");
		// 起飞时间(必填字段)
		command.setCabinCode("V");
		command.setCabinName("经济舱");
	//	command.setStartDate("2015-07-28 08:05");
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		String startDate="2015-10-16";
		try {
			command.setStartDate(sp.parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 加密字符串(必填字段)
		command.setEncryptString("b7bdc7549101fca979c250d7e82d3441c731b89a5db93a425d3f12988c6daa9f301641b69bc4b6850a8a3338004685ac58d016c948a873c9347611202949692ce6585777e784ddaac1483be77bf7ea5187c52acc4730b83b4fe2a96c6ad4301266bb081022eb844e088bc7805865f1bdf44755bda4d1f6e524914b97daee72d45bc9c161b6f11d77");
		PassengerInfoGNDTO passengerInfoDTO = new PassengerInfoGNDTO();
		// 乘客信息集合(必填字段)
		List<PassengerGNDTO> passengerList = new ArrayList<PassengerGNDTO>();
		// 联系人姓名(必填字段)
		passengerInfoDTO.setName("李艳勇");
		// 联系人手机(必填字段,且只能填一个)
		passengerInfoDTO.setTelephone("18458109377");
		PassengerGNDTO psg = new PassengerGNDTO();
		// 姓名(必填字段)
		psg.setPassengerName("胡雪晴");
		// 乘客类型(必填字段)
		psg.setPassengerType("1");
		// 身份证号(必填字段)
		psg.setIdNo("330327199501013031");
		// 证件类型(必填字段)
		psg.setIdType("0");
		passengerList.add(psg);
		passengerInfoDTO.setPassengerList(passengerList);
		command.setPassengerInfoGNDTO(passengerInfoDTO);
//				System.out.println(JSON.toJSONString(command));
		// 发送请求
		JPAutoOrderGNResponse response = client.send(command,
				JPAutoOrderGNResponse.class);
		// 查看返回内容
		System.out.println(JSON.toJSONString(response));

	}

}
