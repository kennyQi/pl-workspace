
import org.junit.Test;

import pay.record.api.client.api.v1.pay.record.request.air.CreateAirFTGPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.request.air.CreateAirGTFPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.request.air.CreateAirJTUPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.request.air.CreateAirUTJPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.response.air.CreateAirUTJPayReocrdResponse;
import pay.record.api.client.common.ApiResponse;
import pay.record.api.client.common.exception.PayRecordApiException;
import pay.record.api.client.common.util.PayRecordApiClient;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：机票支付记录测试
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月30日下午1:51:41
 * @版本：V1.0
 *
 */
public class TestAirPayRecord {
	
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
	 * 用户->直销
	 */
//    @Test
	public void testAirUTJ() {
		// 创建api客户端类
		PayRecordApiClient client;
		try {
			client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
			CreateAirUTJPayReocrdCommand command = new CreateAirUTJPayReocrdCommand();
			command.setFromProjectCode(0);
//			command.setPayPlatform(0);
//			command.setRecordType(1);
//			command.setBooker("余其壮");
//			command.setPlatOrderNo("F1001123456");
//			command.setDealerOrderNo("123456");
//			command.setSupplierNo("T20151203");
//			command.setPaySerialNumber("2015120301564");
//			command.setStartCity("上海");
//			command.setDestCity("广州");
//			command.setPayAccountNo("1111111111111111");
//			command.setIncomeMoney(124.00);
//			command.setFromClientType(1);
//			command.setPayMoney();
//			command.setRebate(spiCommand.getRebate());
//			command.setBrokerage(spiCommand.getBrokerage());
//			command.setOrderStatus(spiCommand.getOrderStatus());
//			command.setPayStatus(spiCommand.getPayStatus());
//			command.setPayTime(spiCommand.getPayTime());
//			command.setRecordType(spiCommand.getRecordType());
//			command.setCreateTime(spiCommand.getCreateTime());
//			command.setRemarks(spiCommand.getRemarks());
//			command.setPayPlatform(spiCommand.getPayPlatform());
//			command.setPayAccountNo(spiCommand.getPayAccountNo());
//			command.setPassengers(spiCommand.getPassengers());
//			command.setAirCompName(spiCommand.getAirCompName());
//			command.setFlightNo(spiCommand.getFlightNo());
//			command.setClassCode(spiCommand.getClassCode());
//			command.setCabinName(spiCommand.getCabinName());
//			command.setPnr(spiCommand.getPnr());
			ApiResponse response = client.send(command, ApiResponse.class);
			System.out.println(JSON.toJSONString(response));
		} catch (PayRecordApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @方法功能说明：分销->供应商
	 * @修改者名字：yuqz
	 * @修改时间：2016年1月15日下午4:25:56
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
//	@Test
	public void testAirFTG() {
		// 创建api客户端类
		PayRecordApiClient client;
		try {
			client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
			CreateAirFTGPayReocrdCommand command = new CreateAirFTGPayReocrdCommand();
			command.setFromProjectCode(0);
//				command.setPayPlatform(0);
//				command.setRecordType(1);
//				command.setBooker("余其壮");
//				command.setPlatOrderNo("F1001123456");
//				command.setDealerOrderNo("123456");
//				command.setSupplierNo("T20151203");
//				command.setPaySerialNumber("2015120301564");
//				command.setStartCity("上海");
//				command.setDestCity("广州");
//				command.setPayAccountNo("1111111111111111");
//				command.setIncomeMoney(124.00);
//				command.setFromClientType(1);
//				command.setPayMoney();
//				command.setRebate(spiCommand.getRebate());
//				command.setBrokerage(spiCommand.getBrokerage());
//				command.setOrderStatus(spiCommand.getOrderStatus());
//				command.setPayStatus(spiCommand.getPayStatus());
//				command.setPayTime(spiCommand.getPayTime());
//				command.setRecordType(spiCommand.getRecordType());
//				command.setCreateTime(spiCommand.getCreateTime());
//				command.setRemarks(spiCommand.getRemarks());
//				command.setPayPlatform(spiCommand.getPayPlatform());
//				command.setPayAccountNo(spiCommand.getPayAccountNo());
//				command.setPassengers(spiCommand.getPassengers());
//				command.setAirCompName(spiCommand.getAirCompName());
//				command.setFlightNo(spiCommand.getFlightNo());
//				command.setClassCode(spiCommand.getClassCode());
//				command.setCabinName(spiCommand.getCabinName());
//				command.setPnr(spiCommand.getPnr());
			ApiResponse response = client.send(command, ApiResponse.class);
			System.out.println(JSON.toJSONString(response));
		} catch (PayRecordApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testAirGTF() {
		// 创建api客户端类
		PayRecordApiClient client;
		try {
			client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
			CreateAirGTFPayReocrdCommand command = new CreateAirGTFPayReocrdCommand();
			command.setFromProjectCode(0);
			ApiResponse response = client.send(command, ApiResponse.class);
			System.out.println(JSON.toJSONString(response));
		} catch (PayRecordApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testAirJTU() {
		// 创建api客户端类
		PayRecordApiClient client;
		try {
			client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
			CreateAirJTUPayReocrdCommand command = new CreateAirJTUPayReocrdCommand();
			command.setFromProjectCode(0);
			ApiResponse response = client.send(command, ApiResponse.class);
			System.out.println(JSON.toJSONString(response));
		} catch (PayRecordApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
