package hsl.app.service.local.jp;
import hg.common.util.BeanMapperUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;
import hsl.domain.model.jp.FlightOrder;
import hsl.pojo.command.jp.plfx.CancelTicketGNCommand;
import hsl.pojo.command.jp.plfx.JPBookTicketGNCommand;
import hsl.pojo.command.jp.plfx.JPPayOrderGNCommand;
import hsl.pojo.command.jp.plfx.RefundTicketGNCommand;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.plfx.JPBookOrderGNDTO;
import hsl.pojo.dto.jp.plfx.JPPayOrderGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryFlightListGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryHighPolicyGNDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.dto.jp.RefundTicketGNDTO;
import hsl.pojo.exception.GNFlightException;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.pojo.qo.jp.plfx.JPFlightGNQO;
import hsl.pojo.qo.jp.plfx.JPPolicyGNQO;
import hsl.spi.qo.sys.CityAirCodeQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.gn.response.JPBookOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPPayOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryFlightListGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryHighPolicyGNResponse;
import plfx.api.client.api.v1.gn.response.RefundTicketGNResponse;
import plfx.api.client.common.util.PlfxApiClient;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @类功能说明：调用远程分销接口
 * @类修改者：
 * @修改日期：2015年7月13日上午10:28:33
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月13日上午10:28:33
 */
@Service
public class GNFlightService{
	@Autowired
	private PlfxApiClient plfxApiClient;
	@Autowired 
	private FlightOrderService flightOrderService;
	@Autowired
	private CityService cityService;

	private String apiUrl =SysProperties.getInstance().get("apiUrl") == null ? "http://127.0.0.1:8080/plfx-api/api": SysProperties.getInstance().get("apiUrl");
	private String dealerKey=SysProperties.getInstance().get("dealerKey") == null ? "F1002": SysProperties.getInstance().get("dealerKey");
	private String secretKey=SysProperties.getInstance().get("fx_secretKey") == null ? "123456": SysProperties.getInstance().get("fx_secretKey");;
	/**
	 * @throws GNFlightException 
	 * @方法功能说明：航班查询
	 * @修改者名字：cangs
	 * @修改时间：2015年7月13日上午9:12:13
	 * @修改内容：
	 * @参数：@param jpFlightGNQO
	 * @参数：@return
	 * @return:JPQueryFlightListGNDTO
	 * @throws
	 */
	public JPQueryFlightListGNDTO queryGNFlight(JPFlightGNQO jpFlightGNQO) throws GNFlightException{
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryGNFlight】>>>jpFlightGNQO:"+JSON.toJSONString(jpFlightGNQO));
		plfx.api.client.api.v1.gn.request.JPFlightGNQO plfxFlightGNQO = JSON.parseObject(JSON.toJSONString(jpFlightGNQO), plfx.api.client.api.v1.gn.request.JPFlightGNQO.class);
		HgLogger.getInstance().info("chenxy", "【GNFlightService】【queryGNFlight】>>>apiUrl:"+apiUrl+">>>dealerKey:"+dealerKey+">>>secretKey:"+secretKey);
		PlfxApiClient client = new PlfxApiClient(apiUrl, dealerKey, secretKey);
		JPQueryFlightListGNResponse jpQueryFlightListGNResponse =  client.send(plfxFlightGNQO, JPQueryFlightListGNResponse.class);
		if(StringUtils.isNotBlank(jpQueryFlightListGNResponse.getIs_success())){
			if(StringUtils.equals(jpQueryFlightListGNResponse.getIs_success(),"T")){
				HgLogger.getInstance().info("cs", "【GNFlightService】【queryGNFlight】>>>jpQueryFlightListGNResponse:"+JSON.toJSONString(jpQueryFlightListGNResponse.getTotalCount()));
				return JSON.parseObject(JSON.toJSONString(jpQueryFlightListGNResponse), JPQueryFlightListGNDTO.class);
			}else{
				throw new GNFlightException(Integer.parseInt(jpQueryFlightListGNResponse.getError().split("\\^")[0]),jpQueryFlightListGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}

	/**
	 * @throws GNFlightException 
	 * @方法功能说明：政策查询
	 * @修改者名字：cangs
	 * @修改时间：2015年7月13日上午9:22:02
	 * @修改内容：
	 * @参数：@param jpPolicyGNQO
	 * @参数：@return
	 * @return:JPQueryHighPolicyGNDTO
	 * @throws
	 */
	public JPQueryHighPolicyGNDTO queryHighPolicyGN(JPPolicyGNQO jpPolicyGNQO) throws GNFlightException{
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryHighPolicyGN】>>>jpPolicyGNQO:"+JSON.toJSONString(jpPolicyGNQO));
		plfx.api.client.api.v1.gn.request.JPPolicyGNQO plfxPolicyGNQO = JSON.parseObject(JSON.toJSONString(jpPolicyGNQO),plfx.api.client.api.v1.gn.request.JPPolicyGNQO.class);
		PlfxApiClient client = new PlfxApiClient(apiUrl, dealerKey, secretKey);
		JPQueryHighPolicyGNResponse jpQueryHighPolicyGNResponse = client.send(plfxPolicyGNQO, JPQueryHighPolicyGNResponse.class);
		if(StringUtils.isNotBlank(jpQueryHighPolicyGNResponse.getIs_success())){
			HgLogger.getInstance().info("cs", "【GNFlightService】【queryHighPolicyGN】>>>jpQueryHighPolicyGNResponse:"+jpQueryHighPolicyGNResponse.getPricesGNDTO().getSinglePlatTotalPrice());
			if(StringUtils.equals(jpQueryHighPolicyGNResponse.getIs_success(), "T")){
				return JSON.parseObject(JSON.toJSONString(jpQueryHighPolicyGNResponse), JPQueryHighPolicyGNDTO.class);
			}else{
				throw new GNFlightException(Integer.parseInt(jpQueryHighPolicyGNResponse.getError().split("\\^")[0]),jpQueryHighPolicyGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	/**
	 * @方法功能说明：创建订单
	 * @修改者名字：chenxy
	 * @修改时间：2015年7月21日下午3:32:27
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public JPBookOrderGNDTO createGNFightOrder(JPBookTicketGNCommand jPBookTicketGNCommand)throws GNFlightException{
	/*	Date nowDate = new Date();
		String dealerOrderCode = OrderUtil.createOrderNo(nowDate, this.getOrderSequence(), 0, 0);//订单号
		jPBookTicketGNCommand.setDealerOrderId(dealerOrderCode);*/
		HgLogger.getInstance().info("cs", "【GNFlightService】【createGNFightOrder】>>>jpBookTicketGNCommand:"+JSON.toJSONString(jPBookTicketGNCommand));
		plfx.api.client.api.v1.gn.request.JPBookTicketGNCommand plfxBookTicketGNCommand = JSON.parseObject(JSON.toJSONString(jPBookTicketGNCommand),plfx.api.client.api.v1.gn.request.JPBookTicketGNCommand.class);
		//plfxBookTicketGNCommand.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		PlfxApiClient client = new PlfxApiClient(apiUrl, dealerKey, secretKey);
		JPBookOrderGNResponse jpBookOrderGNResponse = client.send(plfxBookTicketGNCommand, JPBookOrderGNResponse.class);
		if(StringUtils.isNotBlank(jpBookOrderGNResponse.getIs_success())){
			if(StringUtils.equals(jpBookOrderGNResponse.getIs_success(), "T")){
				HgLogger.getInstance().info("cs", "【GNFlightService】【createGNFightOrder】>>>jpBookOrderGNResponse:"+JSON.toJSONString(jpBookOrderGNResponse.getDealerOrderId()));
				return JSON.parseObject(JSON.toJSONString(jpBookOrderGNResponse), JPBookOrderGNDTO.class);
			}else{
				throw new GNFlightException(Integer.parseInt(jpBookOrderGNResponse.getError().split("\\^")[0]),jpBookOrderGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}

	/**
	 * @方法功能说明：请求出票 
	 * @修改者名字：chenxy
	 * @修改时间：2015年7月21日下午3:32:27
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public JPPayOrderGNDTO askOrderTicket(JPPayOrderGNCommand jpPayOrderGNCommand)throws GNFlightException{
		HgLogger.getInstance().info("cs", "【GNFlightService】【askOrderTicket】>jpPayOrderGNCommand:"+JSON.toJSONString(jpPayOrderGNCommand));
		plfx.api.client.api.v1.gn.request.JPPayOrderGNCommand plfxBookTicketGNCommand = JSON.parseObject(JSON.toJSONString(jpPayOrderGNCommand),plfx.api.client.api.v1.gn.request.JPPayOrderGNCommand.class);
		//plfxBookTicketGNCommand.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		PlfxApiClient client = new PlfxApiClient(apiUrl, dealerKey, secretKey);
		JPPayOrderGNResponse jpPayOrderGNResponse = client.send(plfxBookTicketGNCommand, JPPayOrderGNResponse.class);
		if(StringUtils.isNotBlank(jpPayOrderGNResponse.getIs_success())){
			if(StringUtils.equals(jpPayOrderGNResponse.getIs_success(), "T")){
				HgLogger.getInstance().info("cs", "【GNFlightService】【askOrderTicket】>>>jpPayOrderGNResponse:"+JSON.toJSONString(jpPayOrderGNResponse.getDealerOrderCode()));
				flightOrderService.askOrderTicket(jpPayOrderGNCommand, true);
				return JSON.parseObject(JSON.toJSONString(jpPayOrderGNResponse), JPPayOrderGNDTO.class);
			}else{
				flightOrderService.askOrderTicket(jpPayOrderGNCommand, false);
				throw new GNFlightException(Integer.parseInt(jpPayOrderGNResponse.getError().split("\\^")[0]),jpPayOrderGNResponse.getError().split("\\^")[1]);
			}
		}else{
			flightOrderService.askOrderTicket(jpPayOrderGNCommand, false);
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}


	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：cangs
	 * @修改时间：2015年7月15日上午10:41:10
	 * @修改内容：
	 * @参数：@param cancelTicketGNCommand
	 * @参数：@return
	 * @参数：@throws GNFlightException
	 * @return:CancelTicketGNDTO
	 * @throws
	 */
	public FlightOrderDTO cancelTicketGN(CancelTicketGNCommand cancelTicketGNCommand) throws Exception{
		HgLogger.getInstance().info("renfeng", "【GNFlightService】【cancelTicketGN】>>>cancelTicketGNCommand:"+JSON.toJSONString(cancelTicketGNCommand));
		plfx.api.client.api.v1.gn.request.CancelTicketGNCommand plfxCancelTicketGNCommand = JSON.parseObject(JSON.toJSONString(cancelTicketGNCommand), plfx.api.client.api.v1.gn.request.CancelTicketGNCommand.class);
		HgLogger.getInstance().info("renfeng", "【GNFlightService】【cancelTicketGN】>>>plfxCancelTicketGNCommand:"+JSON.toJSONString(plfxCancelTicketGNCommand));

		//原始订单
		FlightOrderQO qo=new FlightOrderQO();
		qo.setOrderNO(cancelTicketGNCommand.getDealerOrderId());
		qo.setOrderType(FlightOrder.ORDERTYPE_NORMAL);
		FlightOrder order=this.flightOrderService.queryUnique(qo);
		HgLogger.getInstance().info("chenxy","取消订单查询出订单:"+JSON.toJSONString(order));
		String[] names=cancelTicketGNCommand.getPassengerName().split("\\^");
		//生成旅客取消订单
		
		//分销在直销支付时才下单，所以未支付状态的订单取消时，还没有经销商返回信息，不必计算票面价
		//Double tickPrice=order.getDealerReturnInfo().getTickPrice();
		//Double buildFee=order.getFlightPriceInfo().getBuildFee();//机建费
		//Double oilFee=order.getFlightPriceInfo().getOilFee();//燃油费
		
		//单人支付总价
		//double onePsgPrice=tickPrice+(buildFee+oilFee);
		//使用订单中 单人支付总价：包括机建费+燃油费
		//double onePsgMoney=order.getFlightPriceInfo().getSinglePlatTotalPrice();
		
		//int cancelNum=names.length;
		FlightOrder cancelOrder=new FlightOrder();
		cancelOrder.cancelFlightOrder(order, names);
		//cancelOrder.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney*cancelNum));
		//取消订单的支付金额为一个乘客,注：经销商返回信息
		//cancelOrder.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice*cancelNum));
		this.flightOrderService.save(cancelOrder);
		//将取消订单的乘客从原订单中去除
		order.getPassengers().removeAll(cancelOrder.getPassengers());
		//设置取消订单后的支付总价:将取消订单的乘客从原订单中去除
		//乘客人数
		int num=order.getPassengers().size();
		//order.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney*num));
		//order.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice*num));
		HgLogger.getInstance().info("renfeng", "【GNFlightService】【cancelTicketGN】>>>更新后原订单order:"+JSON.toJSONString(order));
		//原始订单没有乘客的时候，修改原始订单为记录订单，orderType为“4”
		if(num==0){
			order.setOrderType(FlightOrder.ORDERTYPE_RECORD);
		}
		this.flightOrderService.update(order);				
		return JSON.parseObject(JSON.toJSONString(order), FlightOrderDTO.class);

	}

	/**
	 * 
	 * @方法功能说明：退费
	 * @修改者名字：cangs
	 * @修改时间：2015年7月15日上午10:41:23
	 * @修改内容：
	 * @参数：@param refundTicketGNCommand
	 * @参数：@return
	 * @参数：@throws GNFlightException
	 * @return:RefundTicketGNDTO
	 * @throws
	 */
	public RefundTicketGNDTO refundTicketGN(RefundTicketGNCommand refundTicketGNCommand)throws GNFlightException{
		HgLogger.getInstance().info("renfeng", "【GNFlightService】【refundTicketGN】"+"refundTicketGNCommand:"+JSON.toJSONString(refundTicketGNCommand));
		plfx.api.client.api.v1.gn.request.RefundTicketGNCommand plfxRefundTicketGNCommand = JSON.parseObject(JSON.toJSONString(refundTicketGNCommand),plfx.api.client.api.v1.gn.request.RefundTicketGNCommand.class);
		PlfxApiClient client = new PlfxApiClient(apiUrl, dealerKey, secretKey);
		RefundTicketGNResponse refundTicketGNResponse= client.send(plfxRefundTicketGNCommand, RefundTicketGNResponse.class);
		HgLogger.getInstance().info("renfeng", "【GNFlightService】【refundTicketGN】"+"refundTicketGNResponse:"+JSON.toJSONString(refundTicketGNResponse));
		if(StringUtils.isNotBlank(refundTicketGNResponse.getIs_success())){
			if(StringUtils.equals(refundTicketGNResponse.getIs_success(), "T")){
				//申请退费的订单，可能是原始订单 也可能是 退费失败的订单，根据orderType 判断
				FlightOrderQO qo=new FlightOrderQO();
				qo.setOrderNO(refundTicketGNCommand.getDealerOrderId());
				qo.setOrderType(refundTicketGNCommand.getOrderType()); //可能是退费中订单再次申请退费     也可能是原始订单申请退费
				FlightOrder order=this.flightOrderService.queryUnique(qo);	
				
				if(FlightOrder.ORDERTYPE_NORMAL.equals(refundTicketGNCommand.getOrderType())){//原始订单申请退费

					//生成乘客退费订单
					String[] names=refundTicketGNCommand.getPassengerName().split("\\^");
					//生成旅客取消订单
					Double tickPrice=order.getDealerReturnInfo().getTickPrice();//单张票价
					Double buildFee=order.getFlightPriceInfo().getBuildFee();//机建费
					Double oilFee=order.getFlightPriceInfo().getOilFee();//燃油费
					int refundNum=names.length;
					
					double onePsgPrice=tickPrice+(buildFee+oilFee);
					//使用订单中 单人支付总价：包括机建费+燃油费
					double onePsgMoney=order.getFlightPriceInfo().getSinglePlatTotalPrice();
					//for(String name:names){ 一次性退费多个乘客，生成一个退费订单
						FlightOrder refundOrder=new FlightOrder();
						refundOrder.refundFlightOrder(order, names);
						refundOrder.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney*refundNum));
						
						refundOrder.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice*refundNum));//退费订单支付总价
						
						this.flightOrderService.save(refundOrder);

						//将退费订单的乘客从原订单中去除
						order.getPassengers().removeAll(refundOrder.getPassengers());
					//}										

					//设置退费订单后的支付总价:将退费订单的乘客从原订单中去除
					//乘客人数
					int num=order.getPassengers().size();
					order.getFlightPriceInfo().setPayAmount(Math.ceil(onePsgMoney*num));
					order.getDealerReturnInfo().setTotalPrice(Math.ceil(onePsgPrice*num));
					
					HgLogger.getInstance().info("renfeng", "【GNFlightService】【refundTicketGN】"+"更新后原订单order:"+JSON.toJSONString(order));
					//原始订单没有乘客的时候，修改原始订单为记录订单，orderType为“4”
					if(num==0){
						order.setOrderType(FlightOrder.ORDERTYPE_RECORD);
					}
					this.flightOrderService.update(order);	
					
				}else if(FlightOrder.ORDERTYPE_REFUND.equals(refundTicketGNCommand.getOrderType())){
					//退费失败的订单 再次发起申请退费，只需要修改订单状态为“退票处理中”
					order.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REFUND_DEALING));
					this.flightOrderService.update(order);
				}
				
				return JSON.parseObject(JSON.toJSONString(refundTicketGNResponse), RefundTicketGNDTO.class);
			}else{
				throw new GNFlightException(Integer.parseInt(refundTicketGNResponse.getError().split("\\^")[0]),refundTicketGNResponse.getError().split("\\^")[1]);
			}


		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	//城市机场三字码查询（返回所有数据）
	public List<CityAirCodeDTO> queryCityAirCode(CityAirCodeQO jpAirCodeQO) {
		// 创建要发送的请求对象
		List<CityAirCodeDTO> cityAirCodeDTOs = new ArrayList<CityAirCodeDTO>();
		try {
			HgLogger.getInstance().info("zhangka", "HSL-APP(三字码)->JPFlightLocalService->queryCityAirCode->request[城市机场三字码查询]:" + JSON.toJSONString(jpAirCodeQO));
			CityQo cityQo = new CityQo();
			List<City> cityList = cityService.queryList(cityQo);
			HgLogger.getInstance().info("zhangka", "HSL-APP(三字码)->JPFlightLocalService->queryCityAirCode->response[城市机场三字码查询]:" +JSON.toJSONString(cityList.size()));
			if (cityList!=null&&cityList.size()>0){
				for(City c:cityList){
					cityAirCodeDTOs.add(BeanMapperUtils.map(c,CityAirCodeDTO.class));
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "HSL-APP(三字码)->JPFlightLocalService->queryCityAirCode->exception[城市机场三字码查询]:" + HgLogger.getStackTrace(e));
			return null;
		}

		return cityAirCodeDTOs;
	}
	
}
