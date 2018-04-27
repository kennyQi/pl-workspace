package zzpl.api.action.jp.gn;

import hg.log.util.HgLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.order.GetGNFlightOrderTotalPriceCommand;
import zzpl.api.client.response.order.GetGNFlightOrderTotalPriceResponse;
import zzpl.app.service.local.jp.PassengerService;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.jp.gn.GNFlightService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.pojo.dto.jp.plfx.gn.JPQueryHighPolicyGNDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.plfx.gn.JPPolicyGNQO;

import com.alibaba.fastjson.JSON;

@Component("GetGNFlightOrderTotalPriceAction")
public class GetGNFlightOrderTotalPriceAction implements CommonAction{

	@Autowired
	private FlightOrderService flightOrderService;
	@Autowired
	private PassengerService passengerService;
	@Autowired
	private GNFlightService gnFlightService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		GetGNFlightOrderTotalPriceResponse getGNFlightOrderTotalPriceResponse = new GetGNFlightOrderTotalPriceResponse();
		try{
			GetGNFlightOrderTotalPriceCommand getGNFlightOrderTotalPriceCommand = JSON.parseObject(apiRequest.getBody().getPayload(), GetGNFlightOrderTotalPriceCommand.class);
			HgLogger.getInstance().info("cs", "【GetGNFlightOrderTotalPriceAction】"+"getGNFlightOrderTotalPriceCommand:"+JSON.toJSONString(getGNFlightOrderTotalPriceCommand));
			List<FlightOrder> flightOrders = new ArrayList<FlightOrder>();
			if(getGNFlightOrderTotalPriceCommand.getOrderNO().startsWith("UN_")){
				//如果是多订单合并付款
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setUnionOrderNO(getGNFlightOrderTotalPriceCommand.getOrderNO());
				flightOrders = flightOrderService.queryList(flightOrderQO);
			}else{
				//只是单独提交
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setOrderNO(getGNFlightOrderTotalPriceCommand.getOrderNO());
				FlightOrder flightOrder = flightOrderService.queryUnique(flightOrderQO);
				flightOrders.add(flightOrder);
			}
			Double commitPrice = 0.0;
			Double totalPrice = 0.0;
			Double platTotalPrice = 0.0;
			boolean executeFlag = false;
			int i=0;
			for(FlightOrder flightOrder:flightOrders){
				if(i==0){
					getGNFlightOrderTotalPriceResponse.setIbePrice(flightOrder.getIbePrice());
					getGNFlightOrderTotalPriceResponse.setBuildFee(flightOrder.getBuildFee());
					getGNFlightOrderTotalPriceResponse.setOilFee(flightOrder.getOilFee());
				}
				i++;
				executeFlag = false;
				/*PassengerQO passengerQO = new PassengerQO();
				passengerQO.setFlightOrderID(flightOrder.getId());
				List<Passenger> passengers = passengerService.queryList(passengerQO);*/
				//根据订单信息组装查询订单所有的政策信息，进行验证
				JPPolicyGNQO jpPolicyGNQO = new JPPolicyGNQO();
				jpPolicyGNQO.setEncryptString(flightOrder.getEncryptString());
				jpPolicyGNQO.setTickType(flightOrder.getTickType());
				JPQueryHighPolicyGNDTO jqHighPolicyGNDTO = new JPQueryHighPolicyGNDTO();
				try {
					jqHighPolicyGNDTO = gnFlightService.queryHighPolicyGN(jpPolicyGNQO);	
					BigDecimal sum = new BigDecimal(0);
					BigDecimal ibePrice = new BigDecimal(jqHighPolicyGNDTO.getPricesGNDTO().getIbePrice());
					sum=sum.add(ibePrice);
					BigDecimal oilFee = new BigDecimal(jqHighPolicyGNDTO.getOilFee());
					sum=sum.add(oilFee);
					BigDecimal buildFee = new BigDecimal(jqHighPolicyGNDTO.getBuildFee());
					sum=sum.add(buildFee);
					flightOrder.setIbePrice(ibePrice.toString());
					flightOrder.setOilFee(jqHighPolicyGNDTO.getOilFee());
					flightOrder.setBuildFee(jqHighPolicyGNDTO.getBuildFee());
					flightOrder.setCommitPrice(sum.doubleValue());
					flightOrder.setPassengers(null);
					HgLogger.getInstance().info("gk", "【GetGNFlightOrderTotalPriceAction】【去查询政策成功】flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderService.update(flightOrder);
				} catch (GNFlightException e) {
					HgLogger.getInstance().error("gk", "【GetGNFlightOrderTotalPriceAction】政策查询失败:" + HgLogger.getStackTrace(e));
					flightOrder.setPayType(FlightOrder.PAY_BY_SELF.toString());
					flightOrder.setStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS);
					flightOrder.setPayStatus(FlightPayStatus.WAIT_USER_TO_PAY);
					flightOrder.setTotalPrice(-1.0);
					flightOrder.setUserPayPrice(flightOrder.getCommitPrice());
					flightOrder.setPassengers(null);
					HgLogger.getInstance().info("gk", "【GetGNFlightOrderTotalPriceAction】【去差政策失败】flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderService.update(flightOrder);
					
					/*getGNFlightOrderTotalPriceResponse.setCommitPrice(flightOrder.getCommitPrice());
					getGNFlightOrderTotalPriceResponse.setTotalPrice(flightOrder.getTotalPrice());
					getGNFlightOrderTotalPriceResponse.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
					getGNFlightOrderTotalPriceResponse.setOrderID(getGNFlightOrderTotalPriceCommand.getOrderID());
					getGNFlightOrderTotalPriceResponse.setOrderNO(getGNFlightOrderTotalPriceCommand.getOrderNO());
					
					getGNFlightOrderTotalPriceResponse.setResult(ApiResponse.RESULT_CODE_OK);
					getGNFlightOrderTotalPriceResponse.setMessage("获取政策失败，让用户按照票面价去付款");
					HgLogger.getInstance().info("cs", "【GetGNFlightOrderTotalPriceAction】【去分销下单失败给用户票面价让其支付】"+"getGNFlightOrderTotalPriceResponse:"+JSON.toJSONString(getGNFlightOrderTotalPriceResponse));
					return JSON.toJSONString(getGNFlightOrderTotalPriceResponse);*/
					
					executeFlag = true;
				}
				if(executeFlag){
					commitPrice = commitPrice + flightOrder.getCommitPrice();
					totalPrice = totalPrice + flightOrder.getTotalPrice();
					platTotalPrice = platTotalPrice + flightOrder.getCommitPrice();
				}else{
					//组装下单Command
				/*	JPBookTicketGNCommand jpBookTicketGNCommand = new JPBookTicketGNCommand(); 
					jpBookTicketGNCommand.setEncryptString(jqHighPolicyGNDTO.getPricesGNDTO().getEncryptString());
					jpBookTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
					jpBookTicketGNCommand.setFlightNo(flightOrder.getFlightNO());
					jpBookTicketGNCommand.setStartDate(flightOrder.getStartTime());
					
					if (StringUtils.isNotBlank(flightOrder.getCabinCode())) {
						jpBookTicketGNCommand.setCabinCode(flightOrder.getCabinCode());
					}
					if (StringUtils.isNotBlank(flightOrder.getCabinName())) {
						jpBookTicketGNCommand.setCabinName(flightOrder.getCabinName());
					}
					//联系人及乘机人信息
					PassengerInfoGNDTO passengerInfoGNDTO = new PassengerInfoGNDTO();
					
					passengerInfoGNDTO.setName(flightOrder.getLinkName());
					passengerInfoGNDTO.setTelephone(flightOrder.getLinkTelephone());
					List<PassengerGNDTO> passengerGNDTOs = new ArrayList<PassengerGNDTO>();
					for (Passenger passenger : passengers) {
						PassengerGNDTO passengerGNDTO = new PassengerGNDTO();
						passengerGNDTO.setPassengerName(passenger.getPassengerName());
						passengerGNDTO.setIdType(passenger.getIdType());
						passengerGNDTO.setIdNo(passenger.getIdNO());
						passengerGNDTO.setPassengerType(passenger.getPassengerType());
						passengerGNDTOs.add(passengerGNDTO);
					}
					passengerInfoGNDTO.setPassengerList(passengerGNDTOs);
					jpBookTicketGNCommand.setPassengerInfoGNDTO(passengerInfoGNDTO);
					//去分销下订单
					JPBookOrderGNDTO jpBookOrderGNDTO = new JPBookOrderGNDTO();
					try {
						jpBookOrderGNDTO = gnFlightService.bookOrderGN(jpBookTicketGNCommand);
					} catch (GNFlightException e) {
						HgLogger.getInstance().error("gk", "【GetGNFlightOrderTotalPriceAction】下单失败:" + HgLogger.getStackTrace(e));
						flightOrder.setPayType(FlightOrder.PAY_BY_SELF.toString());
						flightOrder.setStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS);
						flightOrder.setPayStatus(FlightPayStatus.WAIT_USER_TO_PAY);
						flightOrder.setTotalPrice(-1.0);
						flightOrder.setPassengers(null);
						HgLogger.getInstance().info("gk", "【GetGNFlightOrderTotalPriceAction】【去分销下单失败】flightOrder:"+JSON.toJSONString(flightOrder));
						flightOrderService.update(flightOrder);
						
						getGNFlightOrderTotalPriceResponse.setCommitPrice(flightOrder.getCommitPrice());
					getGNFlightOrderTotalPriceResponse.setTotalPrice(flightOrder.getTotalPrice());
					getGNFlightOrderTotalPriceResponse.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
					getGNFlightOrderTotalPriceResponse.setOrderID(getGNFlightOrderTotalPriceCommand.getOrderID());
					getGNFlightOrderTotalPriceResponse.setOrderNO(getGNFlightOrderTotalPriceCommand.getOrderNO());
					
					getGNFlightOrderTotalPriceResponse.setResult(ApiResponse.RESULT_CODE_OK);
					getGNFlightOrderTotalPriceResponse.setMessage("提交分销订单失败，让用户按照票面价去付款");
					HgLogger.getInstance().info("cs", "【GetGNFlightOrderTotalPriceAction】【去分销下单失败给用户票面价让其支付】"+"getGNFlightOrderTotalPriceResponse:"+JSON.toJSONString(getGNFlightOrderTotalPriceResponse));
					return JSON.toJSONString(getGNFlightOrderTotalPriceResponse);
						executeFlag = true;
					}*/
					/*if(executeFlag){
						commitPrice = commitPrice + flightOrder.getCommitPrice();
						totalPrice = totalPrice + flightOrder.getTotalPrice();
						platTotalPrice = platTotalPrice + flightOrder.getCommitPrice();
					}else{
						HgLogger.getInstance().info("gk", "【GetGNFlightOrderTotalPriceAction】下单成功:"+JSON.toJSONString(jpBookOrderGNDTO));
					}*/
					flightOrder.setEncryptString(jqHighPolicyGNDTO.getPricesGNDTO().getEncryptString());
					flightOrder.setTotalPrice(jqHighPolicyGNDTO.getPricesGNDTO().getSingleTotalPrice());
					flightOrder.setPlatTotalPrice(flightOrder.getCommitPrice());
					flightOrder.setUserPayPrice(flightOrder.getCommitPrice());
					flightOrder.setMemo(jqHighPolicyGNDTO.getPricesGNDTO().getMemo());
					flightOrder.setPlcid(String.valueOf(jqHighPolicyGNDTO.getPricesGNDTO().getPlcid()));
//					flightOrder.setPayPlatform(jpBookOrderGNDTO.getPriceGNDTO().getPayType());
					flightOrder.setPayType(FlightOrder.PAY_BY_SELF.toString());
					flightOrder.setStatus(FlightOrderStatus.GET_POLICY_SUCCESS);
					flightOrder.setPayStatus(FlightPayStatus.WAIT_USER_TO_PAY);
					flightOrder.setPassengers(null);
					flightOrderService.update(flightOrder);
					
					commitPrice = commitPrice + flightOrder.getCommitPrice();
					totalPrice = totalPrice + flightOrder.getTotalPrice();
					platTotalPrice = platTotalPrice + flightOrder.getCommitPrice();
				}
			}
			getGNFlightOrderTotalPriceResponse.setCommitPrice(commitPrice);
			getGNFlightOrderTotalPriceResponse.setTotalPrice(totalPrice);
			getGNFlightOrderTotalPriceResponse.setPlatTotalPrice(platTotalPrice);
			getGNFlightOrderTotalPriceResponse.setOrderID(getGNFlightOrderTotalPriceCommand.getOrderID());
			getGNFlightOrderTotalPriceResponse.setOrderNO(getGNFlightOrderTotalPriceCommand.getOrderNO());
			
			getGNFlightOrderTotalPriceResponse.setResult(ApiResponse.RESULT_CODE_OK);
			HgLogger.getInstance().info("cs", "【GetGNFlightOrderTotalPriceAction】【去分销下单成功给用户票面价让其支付】"+"getGNFlightOrderTotalPriceResponse:"+JSON.toJSONString(getGNFlightOrderTotalPriceResponse));
			return JSON.toJSONString(getGNFlightOrderTotalPriceResponse);
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【GetGNFlightOrderTotalPriceAction】"+"异常，"+HgLogger.getStackTrace(e));
			getGNFlightOrderTotalPriceResponse.setMessage("获取价格失败");
			getGNFlightOrderTotalPriceResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【GetGNFlightOrderTotalPriceAction】"+"getGNFlightOrderTotalPriceResponse:"+JSON.toJSONString(getGNFlightOrderTotalPriceResponse));
		return JSON.toJSONString(getGNFlightOrderTotalPriceResponse);
	}

}
