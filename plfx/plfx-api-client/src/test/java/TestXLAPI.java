

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import plfx.api.client.api.v1.xl.request.command.XLCancelLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.command.XLCreateLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.command.XLModifyLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.command.XLPayLineOrderApiCommand;
import plfx.api.client.api.v1.xl.request.qo.XLCityApiQO;
import plfx.api.client.api.v1.xl.request.qo.XLCountryApiQO;
import plfx.api.client.api.v1.xl.request.qo.XLLineApiQO;
import plfx.api.client.api.v1.xl.request.qo.XLLineOrderApiQO;
import plfx.api.client.api.v1.xl.response.XLCancelLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLCreateLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLModifyLineOrderTravelerResponse;
import plfx.api.client.api.v1.xl.response.XLPayLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLQueryCityResponse;
import plfx.api.client.api.v1.xl.response.XLQueryCountryResponse;
import plfx.api.client.api.v1.xl.response.XLQueryLineOrderResponse;
import plfx.api.client.api.v1.xl.response.XLQueryLineResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.SlfxApiClient;
import plfx.xl.pojo.dto.order.LineOrderBaseInfoDTO;
import plfx.xl.pojo.dto.order.LineOrderLinkInfoDTO;
import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import plfx.xl.pojo.system.XLOrderStatusConstant;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路api单元测试
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午3:53:08
 * @版本：V1.0
 *
 */
public class TestXLAPI {
	
	private final static String httpUrl = "http://192.168.10.59:18081/plfx-api/slfx/api";
	
	/**
	 * 
	 * @方法功能说明：线路订单支付
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月29日上午10:06:46
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
//	@Test
	public void testXLLineOrderPay(){
		
		SlfxApiClient client = new SlfxApiClient(httpUrl,"XLPayLineOrder","123456");
		
		XLPayLineOrderApiCommand apiCommand = new XLPayLineOrderApiCommand();
		apiCommand.setLineOrderID("45b5096d885940388608b3eabbb15b44");
		apiCommand.setLineOrderTravelers("6e38e47ddffd41cab2a793b93369cc9e,a430fcc8367547e389cea7de8c2c248a,bcef6babe61249889ca4ee21d92031df");
		apiCommand.setPaymentAccount("aaa@163.com");
		apiCommand.setPaymentAmount(2760.00);
		apiCommand.setPaymentName("AAA");
		apiCommand.setPaymentType(1);
		apiCommand.setSerialNumber("2014123110345600040450");
		

		//		创建要发送的请求对象
		ApiRequest request = new ApiRequest("XLPayLineOrder","hsl","192.168.1.1",UUID.randomUUID().toString(), apiCommand);
		
		//	发送请求
		XLPayLineOrderResponse response = client.send(request, XLPayLineOrderResponse.class);
		
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
	}
	
	/**
	 * 
	 * @方法功能说明：线路订单取消
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月26日下午4:20:07
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
//	@Test
	public void testXLLineOrderCancle(){
		SlfxApiClient client = new SlfxApiClient(httpUrl,"XLCancleLineOrder","123");
		
		XLCancelLineOrderApiCommand apiCommand = new XLCancelLineOrderApiCommand();
		apiCommand.setLineOrderID("a418e8cb5cec4bed9b003796e1a32088");
		apiCommand.setLineOrderTravelers("09c9f94a40644b63a7246a98d13638f5,0e2b19a5aaac46269c0c63ffc2b28539");
		
		//		创建要发送的请求对象
		ApiRequest request = new ApiRequest("XLCancleLineOrder","hsl","192.168.1.1",UUID.randomUUID().toString(), apiCommand);
		
		//	发送请求
		XLCancelLineOrderResponse response = client.send(request, XLCancelLineOrderResponse.class);
		
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
		
	}
	
	/**
	 * 线路查询
	 */
	//@Test
	public void testXLLineQO(){
		
		SlfxApiClient client = new SlfxApiClient(httpUrl,"XLQueryLine","123");
		
		XLLineApiQO qo = new XLLineApiQO();
		//qo.setId("44dbfe0299f44bafba02ff2022ecc45c");
		qo.setPageNo(3);
		qo.setPageSize(2);
		//	创建要发送的请求对象
		ApiRequest request = new ApiRequest("XLQueryLine","hsl","192.168.1.1",UUID.randomUUID().toString(), qo);
		
		//	发送请求
		XLQueryLineResponse response = client.send(request, XLQueryLineResponse.class);
		
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
	}
	
	/**
	 * 线路订单查询
	 */
//	@Test
	public void testXLLineOrderQO(){
		
		SlfxApiClient client = new SlfxApiClient(httpUrl,"XLQueryLineOrder","123");
		
		XLLineOrderApiQO qo = new XLLineOrderApiQO();
		qo.setId("1");
		
		//	创建要发送的请求对象
		ApiRequest request = new ApiRequest("XLQueryLineOrder","hsl","192.168.1.1",UUID.randomUUID().toString(), qo);
		
		//	发送请求
		XLQueryLineOrderResponse response = client.send(request, XLQueryLineOrderResponse.class);
		
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
	}
	
	/**
	 * 创建线路订单
	 */
//	@Test
	public void testXLCreateLineOrderApiCommand(){
		
		SlfxApiClient client = new SlfxApiClient(httpUrl,"XLCreateLineOrder","123");
		
		XLCreateLineOrderApiCommand command = new XLCreateLineOrderApiCommand();
		command.setLineID("d6e1d56fe53f41fa8f355022826978e4");
		command.setLineDealerID("F1001");
		
			//商城订单基本信息
			LineOrderBaseInfoDTO baseInfo = new LineOrderBaseInfoDTO();
				baseInfo.setDealerOrderNo("2");
				baseInfo.setAdultNo(2);
				baseInfo.setChildNo(1);
//				baseInfo.setAdultUnitPrice(2.00d);
//				baseInfo.setChildUnitPrice(3.00d);
//				baseInfo.setSalePrice(4.00);
//				baseInfo.setBargainMoney(1.00);
//				baseInfo.setSupplierAdultUnitPrice(200.00d);
//				baseInfo.setSupplierUnitChildPrice(110.00d);
//				baseInfo.setSupplierPrice(13.00d);
//				baseInfo.setCreateDate(new Date());
				baseInfo.setTravelDate(new Date());
		command.setBaseInfo(baseInfo);
		
			//商城联系人信息
			LineOrderLinkInfoDTO linkInfo = new LineOrderLinkInfoDTO();
				linkInfo.setLinkName("火星一号");
				linkInfo.setLinkMobile("13100000000");
		command.setLinkInfo(linkInfo);
		
			//商城游客信息列表
			List<LineOrderTravelerDTO> travelerList = new ArrayList<LineOrderTravelerDTO>();
				LineOrderTravelerDTO dto1 = new LineOrderTravelerDTO();
				//dto1.setLineOrder(lineOrder);
				dto1.setName("火星003");
				dto1.setMobile("13100000001");
				dto1.setType(1);
				dto1.setIdType(1);
				dto1.setIdNo("1234567890abcdefgh");
			travelerList.add(dto1);
			
				LineOrderTravelerDTO dto2 = new LineOrderTravelerDTO();
				//dto1.setLineOrder(lineOrder);
				dto2.setName("火星004");
				dto2.setMobile("13100000002");
				dto2.setType(2);
				dto2.setIdType(2);
				dto2.setIdNo("2234567890abcdefgh");
			travelerList.add(dto2);
			
			LineOrderTravelerDTO dto3 = new LineOrderTravelerDTO();
			//dto1.setLineOrder(lineOrder);
			dto3.setName("火星005");
			dto3.setMobile("13100000001");
			dto3.setType(1);
			dto3.setIdType(1);
			dto3.setIdNo("1234567890abcdefgh");
		travelerList.add(dto3);
				
		command.setTravelerList(travelerList);
			
			//成人类销售政策快照
//			SalePolicySnapshotDTO adultSalePolicySnapshot = new SalePolicySnapshotDTO();
//			adultSalePolicySnapshot.setId("1");
//		command.setAdultSalePolicySnapshot(adultSalePolicySnapshot);
		
			//儿童类销售政策快照
//			SalePolicySnapshotDTO childSalePolicySnapshot = new SalePolicySnapshotDTO();
//			childSalePolicySnapshot.setId("2");
//		command.setChildSalePolicySnapshot(childSalePolicySnapshot);
			
		//线路快照
//		LineSnapshotDTO lineSnapshotDTO = new LineSnapshotDTO();
//			lineSnapshotDTO.setId("1b4d7ca709e0462a949bc96fb6ae15f9");
//		command.setLineSnapshot(lineSnapshotDTO);

		//	创建要发送的请求对象
		ApiRequest request = new ApiRequest("XLCreateLineOrder","hsl","192.168.1.1",UUID.randomUUID().toString(), command);
		
		//	发送请求
		XLCreateLineOrderResponse response = client.send(request, XLCreateLineOrderResponse.class);
		
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
	}
	/**
	 * 
	 * @方法功能说明：测试修改订单状态
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年4月13日下午2:45:26
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	
	//@Test
	public void testXLModifyLineOrderTraveler(){
	    SlfxApiClient client = new SlfxApiClient(httpUrl,"XLModifyLineOrder","123");
		XLModifyLineOrderApiCommand command = new XLModifyLineOrderApiCommand();
		//线路订单id
//		command.setLineOrderID("a418e8cb5cec4bed9b003796e1a32088");
		//游玩人id
		command.setLineOrderTravelers("09c9f94a40644b63a7246a98d13638f5,0e2b19a5aaac46269c0c63ffc2b28539");
		//游玩人状态
		command.setStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_RESERVE_SUCCESS));//3003
		//游玩人支付状态
		command.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_WAIT_PAY_BALANCE_MONEY));	//3103
		
        ApiRequest request = new ApiRequest("XLModifyLineOrder","hsl","192.168.1.1",UUID.randomUUID().toString(), command);
		//	发送请求
		XLModifyLineOrderTravelerResponse response = client.send(request, XLModifyLineOrderTravelerResponse.class);
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
	}
	
	/**
	 * 国家查询
	 */
	//@Test
	public void testXLQueryCountry(){
		
		SlfxApiClient client = new SlfxApiClient(httpUrl,"XLQueryLine","123");
		
		XLCountryApiQO qo = new XLCountryApiQO();
		qo.setCode("10");
		//	创建要发送的请求对象
		ApiRequest request = new ApiRequest("XLQueryCountryController","hsl","192.168.1.1",UUID.randomUUID().toString(), qo);
		
		//	发送请求
		XLQueryCountryResponse response = client.send(request, XLQueryCountryResponse.class);
		
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
	}
	
	/**
	 * 国家城市查询
	 */
	//@Test
	public void testXLCity(){
		
		SlfxApiClient client = new SlfxApiClient(httpUrl,"XLQueryLine","123");
		
		XLCityApiQO qo = new XLCityApiQO();
		qo.setParentCode("10");
		//qo.setCode("101");
		//qo.setName("香港");
		//	创建要发送的请求对象
		ApiRequest request = new ApiRequest("XLQueryCityController","hsl","192.168.1.1",UUID.randomUUID().toString(), qo);
		
		//	发送请求
		XLQueryCityResponse response = client.send(request, XLQueryCityResponse.class);
		
		//	查看返回内容
		System.out.println("response=========="+JSON.toJSONString(response));
		
	}
}
