package plfx.gnjp.app.service.api;

import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.gn.dto.CabinGNDTO;
import plfx.api.client.api.v1.gn.dto.FlightGNDTO;
import plfx.api.client.api.v1.gn.dto.PassengerGNDTO;
import plfx.api.client.api.v1.gn.dto.PriceGNDTO;
import plfx.api.client.api.v1.gn.dto.PriceInfoGNDTO;
import plfx.api.client.api.v1.gn.request.CancelTicketGNCommand;
import plfx.api.client.api.v1.gn.request.JPAutoOrderGNCommand;
import plfx.api.client.api.v1.gn.request.JPBookTicketGNCommand;
import plfx.api.client.api.v1.gn.request.JPCreatePayRecordCommand;
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
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.api.PlfxApiAction;
import plfx.api.client.common.api.PlfxApiService;
import plfx.jp.app.service.local.pay.PayRecordLocalService;
import plfx.jp.app.service.local.pay.balances.PayBalancesLocalService;
import plfx.jp.command.pay.CreatePayRecordCommand;
import plfx.jp.domain.model.pay.PayRecord;
import plfx.jp.pojo.dto.pay.balances.PayBalancesDTO;
import plfx.jp.pojo.system.PayRecordConstants;
import plfx.jp.qo.pay.PayRecordQO;
import plfx.jp.qo.pay.balances.PayBalancesQO;
import plfx.jp.spi.inter.JPWebService;
import plfx.jp.spi.inter.pay.balances.PayBalancesService;
import plfx.yeexing.pojo.command.order.JPAutoOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPBookTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPCancelTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPPayOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundTicketSpiCommand;
import plfx.yeexing.pojo.dto.flight.YeeXingCabinDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingCancelTicketDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightPolicyDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerInfoDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPriceDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPriceInfoDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingQueryWebFlightsDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingRefundTicketDTO;
import plfx.yeexing.pojo.dto.order.JPPlatPriceDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPAutoOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingPayJPOrderDTO;
import plfx.yeexing.qo.api.JPFlightSpiQO;
import plfx.yeexing.qo.api.JPPolicySpiQO;

import com.alibaba.fastjson.JSON;
/****
 * 
 * @类功能说明：国内机票接口服务(航班,政策,下单,取消,退废)
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月2日下午7:09:16
 * @版本：V1.0
 *
 */
@Service
public class GnJpAPIService implements PlfxApiService{

	@Autowired
	private JPWebService jPWebService;
	
	@Autowired
	private PayRecordLocalService payRecordLocalService;
	
	@Autowired
	private PayBalancesService payBalancesService;
	
	@Autowired
	private PayBalancesLocalService payBalancesLocalService;
	
	@Autowired
	private  LogPaymentApi logPaymentApi;
	
	/***
	 * 
	 * @方法功能说明：国内航班查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月2日下午6:35:07
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CreateJPOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GN_QueryFlightList)
	public JPQueryFlightListGNResponse queryFlightList(JPFlightGNQO qo) {
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->queryFlightList->[查询航班信息开始]:"+ JSON.toJSONString(qo));
		YeeXingQueryWebFlightsDTO yeeXingQueryWebFlightsDTO = null;
		JPFlightSpiQO spiQO = new JPFlightSpiQO();
		String message = "失败";
		try{
			//BeanUtils.copyProperties(qo, spiQO);
			spiQO = this.setValueJPFlightSpiQO(qo);
			yeeXingQueryWebFlightsDTO = jPWebService.queryFlightList(spiQO);
			//易行dto转化成差旅dto
		}catch(Exception e){
			message = e.getMessage();
			HgLogger.getInstance().error("yaosanfeng", "GnJpAPIService->queryFlightList->[查询航班信息失败]:"+HgLogger.getStackTrace(e));
		}	
		JPQueryFlightListGNResponse jPQueryFlightListGNResponse = null;
		if(yeeXingQueryWebFlightsDTO != null && yeeXingQueryWebFlightsDTO.getIs_success().equals("T")){
			jPQueryFlightListGNResponse = new JPQueryFlightListGNResponse();
			//易行航班dto转化成差旅航班dto
			jPQueryFlightListGNResponse = this.yeeXingFlightDTOConvertToTravelFlightDTO(yeeXingQueryWebFlightsDTO);
			jPQueryFlightListGNResponse.setMessage("成功");
			jPQueryFlightListGNResponse.setResult(ApiResponse.RESULT_SUCCESS);
		}else{
			jPQueryFlightListGNResponse = new JPQueryFlightListGNResponse();
			jPQueryFlightListGNResponse.setMessage(message);
			jPQueryFlightListGNResponse.setResult(ApiResponse.RESULT_ERROR);
			jPQueryFlightListGNResponse.setFlightList(null);
			jPQueryFlightListGNResponse.setTotalCount(null);
			if(yeeXingQueryWebFlightsDTO != null && StringUtils.isNotBlank(yeeXingQueryWebFlightsDTO.getError())){//如果请求出错会返回错误信息
				jPQueryFlightListGNResponse.setError(yeeXingQueryWebFlightsDTO.getError());
				jPQueryFlightListGNResponse.setIs_success(yeeXingQueryWebFlightsDTO.getIs_success());//失败为F
			}
		}
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->queryFlightList->[查询航班信息结束]:"+ JSON.toJSONString(jPQueryFlightListGNResponse));
		return jPQueryFlightListGNResponse;
	}
	
	
	/***
	 * 
	 * @方法功能说明：国内政策查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月2日下午6:35:12
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CreateJPOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GN_QueryAirPolicy)
	public JPQueryHighPolicyGNResponse queryAirPolicy(JPPolicyGNQO qo) {
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->queryAirPolicy->[查询航班政策开始]:"+ JSON.toJSONString(qo));
		YeeXingFlightPolicyDTO yeeXingFlightPolicyDTO = null;
		JPPolicySpiQO jpPolicySpiQO = new JPPolicySpiQO();
		BeanUtils.copyProperties(qo,jpPolicySpiQO);
		String message = "失败";
		try{
			yeeXingFlightPolicyDTO = jPWebService.queryPolicy(jpPolicySpiQO);
		}catch(Exception e){
			message = e.getMessage();
			HgLogger.getInstance().error("yaosanfeng", "GnJpAPIService->queryAirPolicy->[查询航班政策失败]"+HgLogger.getStackTrace(e));
		}
		JPQueryHighPolicyGNResponse jPQueryHighPolicyGNResponse = null;
		if(yeeXingFlightPolicyDTO != null && yeeXingFlightPolicyDTO.getIs_success().equals("T")){
			jPQueryHighPolicyGNResponse = new JPQueryHighPolicyGNResponse();
			//易行政策dto转化成差旅政策dto
			jPQueryHighPolicyGNResponse = this.yeeXingPolicyDTOConvertToTravelPolicyDTO(yeeXingFlightPolicyDTO, qo.getFromDealerId());
			jPQueryHighPolicyGNResponse.setMessage("成功");
			jPQueryHighPolicyGNResponse.setResult(ApiResponse.RESULT_SUCCESS);
		}else{
			jPQueryHighPolicyGNResponse = new JPQueryHighPolicyGNResponse();
			jPQueryHighPolicyGNResponse.setMessage(message);
			jPQueryHighPolicyGNResponse.setResult(ApiResponse.RESULT_ERROR);
			if(yeeXingFlightPolicyDTO != null && StringUtils.isNotBlank(yeeXingFlightPolicyDTO.getError())){//如果请求出错会返回错误信息
				jPQueryHighPolicyGNResponse.setError(yeeXingFlightPolicyDTO.getError());
				jPQueryHighPolicyGNResponse.setIs_success(yeeXingFlightPolicyDTO.getIs_success());//失败为F
			}
		
		}
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->queryAirPolicy->[查询航班政策结束]:"+ JSON.toJSONString(jPQueryHighPolicyGNResponse));
		return jPQueryHighPolicyGNResponse;
	}
	
	/***
	 * 
	 * @方法功能说明：国内机票创建订单
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年11月30日上午10:35:18
	 * @修改内容：订单创建成功后调支付记录系统记录和钱相关的信息
	 * @参数：@param command
	 * @参数：@return
	 * @return:CreateJPOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GN_BookJPOrder)
	public JPBookOrderGNResponse bookJPOrder(JPBookTicketGNCommand command) {
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->bookJPOrder->[机票订单创建开始]:"+ JSON.toJSONString(command));
		YeeXingJPOrderDTO yeeXingJPOrderDTO = null;
		JPBookTicketSpiCommand spiCommand = new JPBookTicketSpiCommand();
		String messsage = "失败";
		try{
//			//判读同一订单是否已存在记录
//			PayBalancesQO payRecordQO = new PayBalancesQO();
//			payRecordQO.setDealerOrderId(command.getDealerOrderId());
//			PayRecord payRecord = payRecordLocalService.queryUnique(payRecordQO);
//			HgLogger.getInstance().info("yuqz", "GnJpAPIService->bookJPOrder->查询是否已存在支付记录:payRecord="
//					+ JSON.toJSONString(payRecord));
//			if(payRecord == null){
//				//保存支付记录
//				CreatePayBalancesCommand createPayRecordCommand = new CreatePayBalancesCommand();
//				createPayRecordCommand.setDealerOrderId(command.getDealerOrderId());
//				createPayRecordCommand.setFromDealerIp(command.getFromDealerIp());
//				createPayRecordCommand.setPayType(PayRecordConstants.PAYRECORD_FAIL);
//				PayRecord createPayRecord = new PayRecord(createPayRecordCommand);
//				HgLogger.getInstance().info("yuqz", "GnJpAPIService->bookJPOrder->保存支付记录:createPayRecord="
//						+ JSON.toJSONString(createPayRecord));
//				payRecordLocalService.save(createPayRecord);
//			}
			
			//由于command类型不一样,用这种方法复制,有些参数不能复制值
			BeanUtils.copyProperties(command, spiCommand);
			spiCommand = this.setValueBookTicketSpiCommand(command);
			yeeXingJPOrderDTO = jPWebService.shopCreateJPOrder(spiCommand);
		}catch(Exception e){
			messsage = e.getMessage();
			HgLogger.getInstance().error("yaosanfeng", "GnJpAPIService->bookJPOrder->[机票订单创建异常]:" + HgLogger.getStackTrace(e));
		}
		JPBookOrderGNResponse jPBookOrderGNResponse = null;
		//1订单已经创建成功但支付失败 2订单创建成功支付也成功(创建,支付只要有一步失败,全部认为整个下单过程失败)
		if(yeeXingJPOrderDTO != null && yeeXingJPOrderDTO.getIs_success().equals("T")){
			jPBookOrderGNResponse = new JPBookOrderGNResponse();
			//易行下单dto转化成差旅下单dto
			jPBookOrderGNResponse = this.yeeXingOrderDTOConvertToTravelOrderDTO(yeeXingJPOrderDTO);
			jPBookOrderGNResponse.setMessage("成功");
			jPBookOrderGNResponse.setResult(ApiResponse.RESULT_SUCCESS);
		}else{
			jPBookOrderGNResponse = new JPBookOrderGNResponse();
			jPBookOrderGNResponse.setMessage(messsage);
			jPBookOrderGNResponse.setResult(ApiResponse.RESULT_ERROR);
			if(yeeXingJPOrderDTO != null && StringUtils.isNotBlank(yeeXingJPOrderDTO.getError())){//如果请求出错会返回错误信息
				jPBookOrderGNResponse.setError(yeeXingJPOrderDTO.getError());
				jPBookOrderGNResponse.setIs_success(yeeXingJPOrderDTO.getIs_success());//失败为F
				HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->bookJPOrder->[机票订单创建失败原因]:"+ yeeXingJPOrderDTO.getError());
			}
		}
		
		//订单创建成功后调支付记录系统记录和钱相关的信息
		//将支付记录信息command的补充完成	
//		jPCreatePayRecordCommand = logPaymentApi.getJPCreatePayRecordCommand(command,yeeXingJPOrderDTO);
//		boolean flag = logPaymentApi.savePaymentInfo(jPCreatePayRecordCommand);
//		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->bookJPOrder->[调用支付记录系统保存支付信息成功]:"+ flag);
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->bookJPOrder->[机票订单创建结束]:"+ JSON.toJSONString(jPBookOrderGNResponse));
		//支付宝余额进行计算
		PayBalancesQO payBalancesQO = new PayBalancesQO();
		payBalancesQO.setType(0);
		PayBalancesDTO payBalancesDTO = payBalancesService.queryUnique(payBalancesQO);
		payBalancesDTO.setBalances(payBalancesDTO.getBalances() + command.getUserPayCash());
		boolean bool = payBalancesService.updatePayBalances(payBalancesDTO);
		HgLogger.getInstance().info("yuqz", "国内机票生成订单更新支付宝余额返回值=" + bool);
		return jPBookOrderGNResponse;
	}
	
	/**
	 * 
	 * @方法功能说明：国内机票自动扣款
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年11月30日上午10:42:35
	 * @修改内容：自动扣款完成调支付记录系统记录和钱相关的信息
	 * @参数：@param command
	 * @参数：@return
	 * @return:JPBookOrderGNResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GN_PayJPOrder)
	public JPPayOrderGNResponse payJPOrder(JPPayOrderGNCommand command) {
		HgLogger.getInstance().info("yuqz","GnJpAPIService->payJPOrder->[机票订单自动扣款开始]command:"+ JSON.toJSONString(command));
		YeeXingPayJPOrderDTO yeeXingPayJPOrderDTO = null;
		String messsage = "失败";
		JPPayOrderSpiCommand spiCommand = new JPPayOrderSpiCommand();
		JPPayOrderGNResponse jpPayOrderGNResponse = new JPPayOrderGNResponse();
		try{
			BeanUtils.copyProperties(command, spiCommand);
			HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->payJPOrder->[机票订单自动扣款开始]spiCommand:"+ JSON.toJSONString(spiCommand));
			
			if(spiCommand != null){
				if(StringUtils.isBlank(spiCommand.getDealerOrderId())){
					jpPayOrderGNResponse.setError("经销商id不存在");
					jpPayOrderGNResponse.setIs_success("F");
					jpPayOrderGNResponse.setMessage(messsage);
					jpPayOrderGNResponse.setResult(ApiResponse.RESULT_ERROR);
					return jpPayOrderGNResponse;
				}
				if(spiCommand.getPayPlatform() == null){
					jpPayOrderGNResponse.setError("支付方式为空");
					jpPayOrderGNResponse.setIs_success("F");
					jpPayOrderGNResponse.setMessage(messsage);
					jpPayOrderGNResponse.setResult(ApiResponse.RESULT_ERROR);
					jPWebService.ticketFail(spiCommand.getDealerOrderId());
					return jpPayOrderGNResponse;
				}
				if(spiCommand.getTotalPrice() == null){
					jpPayOrderGNResponse.setError("经销商支付总价为空");
					jpPayOrderGNResponse.setIs_success("F");
					jpPayOrderGNResponse.setMessage(messsage);
					jpPayOrderGNResponse.setResult(ApiResponse.RESULT_ERROR);
					jPWebService.ticketFail(spiCommand.getDealerOrderId());
					return jpPayOrderGNResponse;
				}
				String payTypeTemp = spiCommand.getPayPlatform().toString();
//				//截取第一位判断是不是1
//				String payType = payTypeTemp.substring(0, 1);
				HgLogger.getInstance().info("yaosanfeng", "GNJPOrderLocalService->payJPOrder->[自动扣款支付方式(公司只支持支付宝支付)1代表支付宝支付]:"+payTypeTemp);
				//对创建订单易行返回的支付方式进行判断(1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267)
				//公司只支持支付宝支付,机票支付方式返回含1的都能支付,非1支付失败
				if(!payTypeTemp.contains("1")){
					HgLogger.getInstance().error("yaosanfeng", "GNJPOrderLocalService->autoPayOut->[自动扣款失败(公司只支持支付宝支付)]:"+JSON.toJSONString(spiCommand));
					jpPayOrderGNResponse.setError("该订单不支持支付宝支付");
					jpPayOrderGNResponse.setIs_success("F");
					jpPayOrderGNResponse.setMessage(messsage);
					jpPayOrderGNResponse.setResult(ApiResponse.RESULT_ERROR);
					jPWebService.ticketFail(spiCommand.getDealerOrderId());
					return jpPayOrderGNResponse;
				}else{
					yeeXingPayJPOrderDTO = jPWebService.apiPayJPOrder(spiCommand);
					HgLogger.getInstance().error("yuqz", "GNJPOrderLocalService->autoPayOut->[自动扣款返回yeeXingPayJPOrderDTO]:"
							+JSON.toJSONString(yeeXingPayJPOrderDTO));
					if(yeeXingPayJPOrderDTO != null && yeeXingPayJPOrderDTO.getIs_success().equals("T")){
						//更新支付记录
//						PayBalancesQO payRecordQO = new PayBalancesQO();
//						payRecordQO.setDealerOrderId(spiCommand.getDealerOrderId());
//						PayRecord payRecord = payRecordLocalService.queryUnique(payRecordQO);
//						payRecord.setPayType(PayRecordConstants.PAYRECORD_SUCCESS);
//						HgLogger.getInstance().info("yuqz", "GnJpAPIService->payJPOrder->更新支付记录:payRecord="
//								+ JSON.toJSONString(payRecord));
//						payRecordLocalService.update(payRecord);
						
						jpPayOrderGNResponse.setIs_success(yeeXingPayJPOrderDTO.getIs_success());
						jpPayOrderGNResponse.setDealerOrderCode(yeeXingPayJPOrderDTO.getDealerOrderCode());
						jpPayOrderGNResponse.setTotalPrice(yeeXingPayJPOrderDTO.getTotalPrice());
						jpPayOrderGNResponse.setPayid(yeeXingPayJPOrderDTO.getPayid());
						jpPayOrderGNResponse.setPay_status(yeeXingPayJPOrderDTO.getPay_status());
						jpPayOrderGNResponse.setMessage("成功");
						jpPayOrderGNResponse.setResult(ApiResponse.RESULT_SUCCESS);
						
						//自动扣款完成调支付记录系统记录和钱相关的信息
						//将自动扣款的支付信息command的补充完成
						JPCreatePayRecordCommand jPCreatePayRecordCommand = logPaymentApi.getJPCreatePayRecordCommand(yeeXingPayJPOrderDTO,command);
						boolean flag = logPaymentApi.savePaymentInfo(jPCreatePayRecordCommand);
						HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->bookJPOrder->[调用支付记录系统保存支付信息是否成功]:"+ flag);
					
						
					}else{
						jpPayOrderGNResponse.setMessage(messsage);
						jpPayOrderGNResponse.setResult(ApiResponse.RESULT_ERROR);
						if(yeeXingPayJPOrderDTO != null && StringUtils.isNotBlank(yeeXingPayJPOrderDTO.getError())){
							jpPayOrderGNResponse.setIs_success(yeeXingPayJPOrderDTO.getIs_success());
							jpPayOrderGNResponse.setError(yeeXingPayJPOrderDTO.getError());
						}
					}
				}
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz", "GnJpAPIService->payJPOrder->[机票订单自动扣款异常]:" + HgLogger.getStackTrace(e));
			jpPayOrderGNResponse.setIs_success("F");
			jpPayOrderGNResponse.setError("自动扣款异常");
			return jpPayOrderGNResponse;
		}
		//支付宝余额进行计算
		PayBalancesQO payBalancesQO = new PayBalancesQO();
		payBalancesQO.setType(0);
		PayBalancesDTO payBalancesDTO = payBalancesService.queryUnique(payBalancesQO);
		payBalancesDTO.setBalances(payBalancesDTO.getBalances() - yeeXingPayJPOrderDTO.getTotalPrice());
		boolean bool = payBalancesService.warnPayBalances(payBalancesDTO);
		HgLogger.getInstance().info("yuqz", "国内机票自动扣款更新支付宝余额返回值=" + bool);
		
		HgLogger.getInstance().info("yuqz","GnJpAPIService->payJPOrder->[机票订单自动扣款结束]:"+ JSON.toJSONString(jpPayOrderGNResponse));
		return jpPayOrderGNResponse;
	}

	/****
	 * 
	 * @方法功能说明：机票取消申请
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月4日下午1:55:32
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CancelTicketGNResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GN_CancelTicket)
	public CancelTicketGNResponse cancelTicket(CancelTicketGNCommand command) {
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->cancelTicket->[机票订单取消开始]:"+ JSON.toJSONString(command));
		YeeXingCancelTicketDTO yeeXingCancelTicketDTO = null;
		JPCancelTicketSpiCommand spiCommand = new JPCancelTicketSpiCommand();
		String messsage = "失败";
		try{
			BeanUtils.copyProperties(command, spiCommand);
			yeeXingCancelTicketDTO = jPWebService.apiCancelTicket(spiCommand);
		}catch(Exception e){
			messsage = e.getMessage();
			HgLogger.getInstance().error("tandeng", "GnJpAPIService->cancelTicket->[机票订单取消异常]:" + HgLogger.getStackTrace(e));
		}
		CancelTicketGNResponse cancelTicketGNResponse = null;
		if(yeeXingCancelTicketDTO != null && yeeXingCancelTicketDTO.getIs_success().equals("T")){
			cancelTicketGNResponse = new CancelTicketGNResponse();
			cancelTicketGNResponse.setMessage("成功");
			cancelTicketGNResponse.setResult(ApiResponse.RESULT_SUCCESS);
			cancelTicketGNResponse.setIs_success(yeeXingCancelTicketDTO.getIs_success());
		}else{
			cancelTicketGNResponse = new CancelTicketGNResponse();
			cancelTicketGNResponse.setMessage(messsage);
			cancelTicketGNResponse.setResult(ApiResponse.RESULT_ERROR);
			if(yeeXingCancelTicketDTO != null && StringUtils.isNotBlank(yeeXingCancelTicketDTO.getError())){
				cancelTicketGNResponse.setError(yeeXingCancelTicketDTO.getError());
				cancelTicketGNResponse.setIs_success(yeeXingCancelTicketDTO.getIs_success());
			}
		}
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->cancelTicket->[机票取消申请结束]:"+ JSON.toJSONString(cancelTicketGNResponse));
		return cancelTicketGNResponse;
	}
	
	/****
	 * 
	 * @方法功能说明：机票退废申请
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月4日下午1:56:33
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CancelTicketGNResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GN_RefundTicket)
	public RefundTicketGNResponse refundTicket(RefundTicketGNCommand command) {
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->refundTicket->[机票退废申请开始]:"+ JSON.toJSONString(command));
		YeeXingRefundTicketDTO yeeXingRefundTicketDTO = null;
		JPRefundTicketSpiCommand spiCommand = new JPRefundTicketSpiCommand();
		String messsage = "失败";
		try{
			BeanUtils.copyProperties(command, spiCommand);
			yeeXingRefundTicketDTO = jPWebService.apiRefundTicket(spiCommand);
		}catch(Exception e){
			messsage = e.getMessage();
			HgLogger.getInstance().error("yaosanfeng", "GnJpAPIService->refundTicket->[机票退废申请异常]:" + HgLogger.getStackTrace(e));
		}
		RefundTicketGNResponse refundTicketGNResponse = null;
		if(yeeXingRefundTicketDTO != null && yeeXingRefundTicketDTO.getIs_success().equals("T")){
			refundTicketGNResponse = new RefundTicketGNResponse();
			refundTicketGNResponse.setMessage("成功");
			refundTicketGNResponse.setResult(ApiResponse.RESULT_SUCCESS);
			refundTicketGNResponse.setIs_success(yeeXingRefundTicketDTO.getIs_success());
		}else{
			refundTicketGNResponse = new RefundTicketGNResponse();
			refundTicketGNResponse.setMessage(messsage);
			refundTicketGNResponse.setResult(ApiResponse.RESULT_ERROR);
			if(yeeXingRefundTicketDTO != null && StringUtils.isNotBlank(refundTicketGNResponse.getError())){
				refundTicketGNResponse.setError(refundTicketGNResponse.getError());
				refundTicketGNResponse.setIs_success(yeeXingRefundTicketDTO.getIs_success());
			}
		}
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->cancelTicket->[机票退废申请结束]:"+ JSON.toJSONString(refundTicketGNResponse));
		return refundTicketGNResponse;
	}
	
	/****
	 * 
	 * @方法功能说明：易行航班dto转化成差旅航班dto
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月4日下午5:05:02
	 * @修改内容：
	 * @参数：@param dto
	 * @参数：@return
	 * @return:JPQueryFlightListGNResponse
	 * @throws
	 */
	public  JPQueryFlightListGNResponse yeeXingFlightDTOConvertToTravelFlightDTO(YeeXingQueryWebFlightsDTO dto){
		JPQueryFlightListGNResponse jPQueryFlightListGNResponse = new JPQueryFlightListGNResponse();	
		
		List<FlightGNDTO> flightList = new ArrayList<FlightGNDTO>();
		for(YeeXingFlightDTO yxFlightDTO:dto.getFlightList()){
			FlightGNDTO flightGNDTO = new FlightGNDTO();
			flightGNDTO.setAirComp(yxFlightDTO.getAirComp());
			flightGNDTO.setAirCompanyName(yxFlightDTO.getAirCompanyName());
			flightGNDTO.setAirCompanyShotName(yxFlightDTO.getAirCompanyShotName());
			flightGNDTO.setArrivalTerm(yxFlightDTO.getArrivalTerm());
			flightGNDTO.setDepartTerm(yxFlightDTO.getDepartTerm());
			flightGNDTO.setDstCity(yxFlightDTO.getDstCity());
			flightGNDTO.setDstCityName(yxFlightDTO.getDstCityName());
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				flightGNDTO.setEndTime(sd.parse(yxFlightDTO.getEndTime()));
				flightGNDTO.setStartTime(sd.parse(yxFlightDTO.getStartTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			flightGNDTO.setFlightno(yxFlightDTO.getFlightno());
			flightGNDTO.setMealCode(yxFlightDTO.getMealCode());
			flightGNDTO.setOrgCity(yxFlightDTO.getOrgCity());
			flightGNDTO.setOrgCityName(yxFlightDTO.getOrgCityName());
			flightGNDTO.setMealCode(yxFlightDTO.getMealCode());
			flightGNDTO.setPlaneType(yxFlightDTO.getPlaneType());
			flightGNDTO.setStopNumber(yxFlightDTO.getStopNumber());
			List<CabinGNDTO> cabinList = new ArrayList<CabinGNDTO>();
			for(YeeXingCabinDTO yxCabinDTO:yxFlightDTO.getCabinList()){
				CabinGNDTO cabinGNDTO = new CabinGNDTO();
				cabinGNDTO.setCabinCode(yxCabinDTO.getCabinCode());
				cabinGNDTO.setCabinDiscount(yxCabinDTO.getCabinDiscount());
				cabinGNDTO.setCabinName(yxCabinDTO.getCabinName());
				cabinGNDTO.setCabinSales(yxCabinDTO.getCabinSales());
				cabinGNDTO.setCabinType(yxCabinDTO.getCabinType());
				cabinGNDTO.setEncryptString(yxCabinDTO.getEncryptString());
				cabinGNDTO.setIbePrice(yxCabinDTO.getIbePrice());
				cabinList.add(cabinGNDTO);
			}
			flightGNDTO.setCabinList(cabinList);
			flightList.add(flightGNDTO);
		}
		jPQueryFlightListGNResponse.setFlightList(flightList);
		jPQueryFlightListGNResponse.setBuildFee(dto.getBuildFee());
		jPQueryFlightListGNResponse.setIs_success(dto.getIs_success());
		jPQueryFlightListGNResponse.setOilFee(dto.getOilFee());
		jPQueryFlightListGNResponse.setTotalCount(dto.getFlightList().size());
		return jPQueryFlightListGNResponse;		
	}
	

	/****
	 * 
	 * @方法功能说明：易行政策dto转化成差旅政策dto
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月4日下午5:04:34
	 * @修改内容：
	 * @参数：@param dto
	 * @参数：@return
	 * @return:JPQueryHighPolicyGNResponse
	 * @throws
	 */
	public JPQueryHighPolicyGNResponse yeeXingPolicyDTOConvertToTravelPolicyDTO(YeeXingFlightPolicyDTO dto, String fromDealerCode){
		JPQueryHighPolicyGNResponse jPQueryHighPolicyGNResponse = new JPQueryHighPolicyGNResponse();
		jPQueryHighPolicyGNResponse.setBuildFee(dto.getBuildFee());
		jPQueryHighPolicyGNResponse.setIs_success(dto.getIs_success());
		jPQueryHighPolicyGNResponse.setOilFee(dto.getOilFee());
		//平台筛选第一条返点最高的政策
		if(dto.getPricesList() != null){
			YeeXingPriceInfoDTO yeeXingPriceInfoDTO = dto.getPricesList().get(0);
			PriceInfoGNDTO priceInfoGNDTO = new PriceInfoGNDTO();
			//加密字符串
			priceInfoGNDTO.setEncryptString(yeeXingPriceInfoDTO.getEncryptString());
			//票面价
			priceInfoGNDTO.setIbePrice(yeeXingPriceInfoDTO.getIbePrice());
			//备注
			priceInfoGNDTO.setMemo(yeeXingPriceInfoDTO.getMemo());
			//政策ID
			priceInfoGNDTO.setPlcid(yeeXingPriceInfoDTO.getPlcid());
			//单人支付总价   单张机票价格 + 机建 +　燃油
			Double singleTotalPrice = dto.getBuildFee() + dto.getOilFee() + yeeXingPriceInfoDTO.getTickPrice();
			priceInfoGNDTO.setSingleTotalPrice(singleTotalPrice);
			YeeXingPriceDTO yeeXingPriceDTO = new YeeXingPriceDTO();
			yeeXingPriceDTO.setIbePrice(yeeXingPriceInfoDTO.getIbePrice());
			yeeXingPriceDTO.setTotalPrice(singleTotalPrice);
			yeeXingPriceDTO.setBuildFee(dto.getBuildFee());
			yeeXingPriceDTO.setOilFee(dto.getOilFee());
			JPPlatPriceDTO jpPlatPriceDTO = jPWebService.dealPlatPrice(yeeXingPriceDTO, fromDealerCode);
			//设置平台价格
			priceInfoGNDTO.setSinglePlatTotalPrice(jpPlatPriceDTO.getPlatTotalPrice());
			jPQueryHighPolicyGNResponse.setPricesGNDTO(priceInfoGNDTO);
		}
		return jPQueryHighPolicyGNResponse;
	}
	

	/****
	 * 
	 * @方法功能说明：易行下单dto转化成差旅下单dto
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月4日下午5:04:45
	 * @修改内容：
	 * @参数：@param dto
	 * @参数：@return
	 * @return:JPBookOrderGNResponse
	 * @throws
	 */
	public  JPBookOrderGNResponse yeeXingOrderDTOConvertToTravelOrderDTO(YeeXingJPOrderDTO dto){
		JPBookOrderGNResponse jPBookOrderGNResponse = new JPBookOrderGNResponse();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			jPBookOrderGNResponse.setCreateTime(sd.parse(dto.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		jPBookOrderGNResponse.setIs_success(dto.getIs_success());
		//经销商订单号
		jPBookOrderGNResponse.setDealerOrderId(dto.getDealerOrderId());
		
		    List<PassengerGNDTO> PassengerList = new ArrayList<PassengerGNDTO>();
		    for(YeeXingPassengerDTO psg:dto.getPassengerList()){
		    	PassengerGNDTO passengerGNDTO = new PassengerGNDTO();
		    	passengerGNDTO.setIdNo(psg.getIdNo());//证件号
		    	passengerGNDTO.setIdType(psg.getIdType());//证件类型
		    	passengerGNDTO.setPassengerName(psg.getPassengerName());//姓名
		    	passengerGNDTO.setPassengerType(psg.getPassengerType());//乘客类型
		    	PassengerList.add(passengerGNDTO);
		    }
		    jPBookOrderGNResponse.setPassengerList(PassengerList);
		    PriceGNDTO priceGNDTO = new PriceGNDTO();
		    priceGNDTO.setIbePrice(dto.getPriceDTO().getIbePrice());
		    priceGNDTO.setMemo(dto.getPriceDTO().getMemo());
		    priceGNDTO.setPlcid(dto.getPriceDTO().getPlcid());
		    priceGNDTO.setTickType(dto.getPriceDTO().getTickType());
		    priceGNDTO.setOilFee(dto.getPriceDTO().getOilFee());
		    priceGNDTO.setBuildFee(dto.getPriceDTO().getBuildFee());
		    priceGNDTO.setTotalPrice(dto.getPriceDTO().getTotalPrice());
		    priceGNDTO.setPayType(dto.getPriceDTO().getPayType());//支付方式
		    priceGNDTO.setTickPrice(dto.getPriceDTO().getTickPrice());//单张票价
//		    priceGNDTO.setPlatPolicyPirce(dto.getPriceDTO().getPlatPolicyPirce());
		    priceGNDTO.setPlatTotalPirce(dto.getPriceDTO().getPlatTotalPrice());
	    	jPBookOrderGNResponse.setPriceGNDTO(priceGNDTO);
			return jPBookOrderGNResponse;
		
	}
	
	/****
	 * 
	 * @方法功能说明：差旅command转化成易行command
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月6日上午9:20:17
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:JPBookTicketSpiCommand
	 * @throws
	 */
	public JPBookTicketSpiCommand setValueBookTicketSpiCommand(JPBookTicketGNCommand command){
		List<YeeXingPassengerDTO> passengerList = new ArrayList<YeeXingPassengerDTO>();
		JPBookTicketSpiCommand jPBookTicketSpiCommand = new JPBookTicketSpiCommand();
		YeeXingPassengerInfoDTO yeeXingPassengerInfoDTO = new YeeXingPassengerInfoDTO();
		for(PassengerGNDTO passenger:command.getPassengerInfoGNDTO().getPassengerList()){
			YeeXingPassengerDTO yeeXingPassengerDTO = new YeeXingPassengerDTO();
			yeeXingPassengerDTO.setPassengerName(passenger.getPassengerName());
			yeeXingPassengerDTO.setPassengerType(passenger.getPassengerType());
			yeeXingPassengerDTO.setIdNo(passenger.getIdNo());
			yeeXingPassengerDTO.setIdType(passenger.getIdType());
			passengerList.add(yeeXingPassengerDTO);
		}
		yeeXingPassengerInfoDTO.setName(command.getPassengerInfoGNDTO().getName());
		yeeXingPassengerInfoDTO.setPassengerList(passengerList);
		yeeXingPassengerInfoDTO.setTelephone(command.getPassengerInfoGNDTO().getTelephone());
		jPBookTicketSpiCommand.setYeeXingpassengerInfoDTO(yeeXingPassengerInfoDTO);
		jPBookTicketSpiCommand.setDealerOrderId(command.getDealerOrderId());
		jPBookTicketSpiCommand.setFlightNo(command.getFlightNo());
		//时间转换
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if(command.getStartDate() != null){
			jPBookTicketSpiCommand.setStartDate(sd.format(command.getStartDate()));	
		}else{//设置默认当前时间
			jPBookTicketSpiCommand.setStartDate(sd.format(new Date()));
		}
		
		jPBookTicketSpiCommand.setOutOrderId(null);;//平台订单号 
		jPBookTicketSpiCommand.setDealerOrderId(command.getDealerOrderId());
		jPBookTicketSpiCommand.setEncryptString(command.getEncryptString());
		jPBookTicketSpiCommand.setFromDealerId(command.getFromDealerId());
		//舱位代码
		jPBookTicketSpiCommand.setCabinCode(command.getCabinCode());
		//舱位名称
		jPBookTicketSpiCommand.setCabinName(command.getCabinName());
		//用户支付总价
		jPBookTicketSpiCommand.setUserPayAmount(command.getUserPayAmount());
		//用户支付现金
		jPBookTicketSpiCommand.setUserPayCash(command.getUserPayCash());
		return jPBookTicketSpiCommand;
	}
	/*****
	 * 
	 * @方法功能说明：qo的转化
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月13日下午3:30:09
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:JPFlightSpiQO
	 * @throws
	 */
	private JPFlightSpiQO setValueJPFlightSpiQO(JPFlightGNQO qo) {
		JPFlightSpiQO spiQO = new JPFlightSpiQO();
		spiQO.setAirCompany(qo.getAirCompany());
		spiQO.setAirCompanyShotName(qo.getAirCompanyShotName());
		spiQO.setDstCity(qo.getDstCity());
		spiQO.setOrgCity(qo.getOrgCity());
		spiQO.setStartDate(DateUtil.formatDate(qo.getStartDate()));
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		if(qo.getStartTime() != null){
			spiQO.setStartTime(sd.format(qo.getStartTime()));
		}else{
			spiQO.setStartTime("");
		}
		return spiQO;
	}

	/*****
	 * 
	 * @方法功能说明：生成订单并自动扣款接口
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月8日上午10:44:30
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:JPAutoOrderGNResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GN_AutoOrder)
	public JPAutoOrderGNResponse autoOrder(JPAutoOrderGNCommand command) {
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->autoOrder->[机票订单生成并自动扣款开始]:"+ JSON.toJSONString(command));
		YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO = null;
		JPAutoOrderSpiCommand spiCommand = new JPAutoOrderSpiCommand();
		String messsage = "失败";
		try{
			spiCommand = this.travelCommandToYeeXingJPAutoOrderCommand(command);
			yeeXingJPAutoOrderDTO = jPWebService.apiAutoOrder(spiCommand);
		}catch(Exception e){
			messsage = e.getMessage();
			HgLogger.getInstance().error("yaosanfeng", "GnJpAPIService->autoOrder->[机票订单生成并自动扣款异常]:" + HgLogger.getStackTrace(e));
		}
		JPAutoOrderGNResponse jPAutoOrderGNResponse = null;
		
		//判读同一订单是否已存在记录
//		PayRecordQO payRecordQO = new PayRecordQO();
//		payRecordQO.setDealerOrderId(spiCommand.getDealerOrderId());
//		PayRecord payRecord = payRecordLocalService.queryUnique(payRecordQO);
//		HgLogger.getInstance().info("yuqz", "GnJpAPIService->bookJPOrder->查询是否已存在支付记录:payRecord="
//				+ JSON.toJSONString(payRecord));
//		if(payRecord == null){
//			//保存支付记录
//			CreatePayBalancesCommand createPayRecordCommand = new CreatePayBalancesCommand();
//			createPayRecordCommand.setDealerOrderId(spiCommand.getDealerOrderId());
//			createPayRecordCommand.setFromDealerIp(command.getFromDealerIp());
//			createPayRecordCommand.setPayType(PayRecordConstants.PAYRECORD_FAIL);
//			payRecord = new PayRecord(createPayRecordCommand);
//		}
		
		//1订单已经创建成功但支付失败 2订单创建成功支付也成功(创建,支付只要有一步失败,全部认为整个下单过程失败)
		if(yeeXingJPAutoOrderDTO != null && yeeXingJPAutoOrderDTO.getIs_success().equals("T")){
			//设置支付记录类型
//			payRecord.setPayType(PayRecordConstants.PAYRECORD_SUCCESS);
			jPAutoOrderGNResponse = new JPAutoOrderGNResponse();
			//易行下单并自动扣款dto转化成差旅下单dto
			jPAutoOrderGNResponse = this.yeeXingAutoOrderDTOConvertToTravelOrderDTO(yeeXingJPAutoOrderDTO);
			jPAutoOrderGNResponse.setMessage("成功");
			jPAutoOrderGNResponse.setResult(ApiResponse.RESULT_SUCCESS);
		}else{
			//设置支付记录类型
//			payRecord.setPayType(PayRecordConstants.PAYRECORD_FAIL);
			jPAutoOrderGNResponse = new JPAutoOrderGNResponse();
			jPAutoOrderGNResponse.setMessage(messsage);
			jPAutoOrderGNResponse.setResult(ApiResponse.RESULT_ERROR);
			if(yeeXingJPAutoOrderDTO != null && StringUtils.isNotBlank(yeeXingJPAutoOrderDTO.getError())){//如果请求出错会返回错误信息
				jPAutoOrderGNResponse.setError(yeeXingJPAutoOrderDTO.getError());
				jPAutoOrderGNResponse.setIs_success(yeeXingJPAutoOrderDTO.getIs_success());//失败为F
				HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->bookJPOrder->[机票订单生成并自动扣款失败原因]:"+ yeeXingJPAutoOrderDTO.getError());
			}
		}
//		HgLogger.getInstance().info("yuqz", "GnJpAPIService->bookJPOrder->保存支付记录:payRecord="
//				+ JSON.toJSONString(payRecord));
//		payRecordLocalService.save(payRecord);
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->bookJPOrder->[机票订单生成并自动扣款结束]:"+ JSON.toJSONString(jPAutoOrderGNResponse));
		return jPAutoOrderGNResponse;
	}


	/*****
	 * 
	 * @方法功能说明：易行生成订单并自动扣款DTO转化成差旅需要的DTO
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月8日上午10:03:39
	 * @修改内容：
	 * @参数：@param yeeXingJPAutoOrderDTO
	 * @参数：@return
	 * @return:JPAutoOrderGNResponse
	 * @throws
	 */
	private JPAutoOrderGNResponse yeeXingAutoOrderDTOConvertToTravelOrderDTO(YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO) {
		JPAutoOrderGNResponse jPAutoOrderGNResponse = new JPAutoOrderGNResponse();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			jPAutoOrderGNResponse.setCreateTime(sd.parse(yeeXingJPAutoOrderDTO.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		jPAutoOrderGNResponse.setIs_success(yeeXingJPAutoOrderDTO.getIs_success());
		//经销商订单号
		jPAutoOrderGNResponse.setDealerOrderId(yeeXingJPAutoOrderDTO.getDealerOrderId());
		//代扣状态T代表扣款成功
		jPAutoOrderGNResponse.setPay_status(yeeXingJPAutoOrderDTO.getPay_status());
		//支付流水号
		jPAutoOrderGNResponse.setPayid(yeeXingJPAutoOrderDTO.getPayid());
		//支付易行的订单金额
		jPAutoOrderGNResponse.setTotalPrice(yeeXingJPAutoOrderDTO.getTotalPrice());
		
		    List<PassengerGNDTO> PassengerList = new ArrayList<PassengerGNDTO>();
		    for(YeeXingPassengerDTO psg:yeeXingJPAutoOrderDTO.getPassengerList()){
		    	PassengerGNDTO passengerGNDTO = new PassengerGNDTO();
		    	passengerGNDTO.setIdNo(psg.getIdNo());//证件号
		    	passengerGNDTO.setIdType(psg.getIdType());//证件类型
		    	passengerGNDTO.setPassengerName(psg.getPassengerName());//姓名
		    	passengerGNDTO.setPassengerType(psg.getPassengerType());//乘客类型
		    	PassengerList.add(passengerGNDTO);
		    }
		    jPAutoOrderGNResponse.setPassengerList(PassengerList);
		    PriceGNDTO priceGNDTO = new PriceGNDTO();
		    priceGNDTO.setIbePrice(yeeXingJPAutoOrderDTO.getPriceDTO().getIbePrice());
		    priceGNDTO.setMemo(yeeXingJPAutoOrderDTO.getPriceDTO().getMemo());
		    priceGNDTO.setPlcid(yeeXingJPAutoOrderDTO.getPriceDTO().getPlcid());
		    priceGNDTO.setTickType(yeeXingJPAutoOrderDTO.getPriceDTO().getTickType());
		    priceGNDTO.setOilFee(yeeXingJPAutoOrderDTO.getPriceDTO().getOilFee());
		    priceGNDTO.setBuildFee(yeeXingJPAutoOrderDTO.getPriceDTO().getBuildFee());
		    priceGNDTO.setTotalPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getTotalPrice());
		    priceGNDTO.setPayType(yeeXingJPAutoOrderDTO.getPriceDTO().getPayType());//支付方式
		    priceGNDTO.setTickPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getTickPrice());//单张票价
//		    priceGNDTO.setPlatPolicyPirce(yeeXingJPAutoOrderDTO.getPriceDTO().getPlatPolicyPirce());
		    priceGNDTO.setPlatTotalPirce(yeeXingJPAutoOrderDTO.getPriceDTO().getPlatTotalPrice());
		    jPAutoOrderGNResponse.setPriceGNDTO(priceGNDTO);
		    
			return jPAutoOrderGNResponse;	
	}
	
	
	/****
	 * 
	 * @方法功能说明：差旅生成订单command转化成易行command
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月6日上午9:20:17
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:JPBookTicketSpiCommand
	 * @throws
	 */
	public JPAutoOrderSpiCommand travelCommandToYeeXingJPAutoOrderCommand(JPAutoOrderGNCommand command){
		List<YeeXingPassengerDTO> passengerList = new ArrayList<YeeXingPassengerDTO>();
		JPAutoOrderSpiCommand jPAutoOrderSpiCommand = new JPAutoOrderSpiCommand();
		YeeXingPassengerInfoDTO yeeXingPassengerInfoDTO = new YeeXingPassengerInfoDTO();
		for(PassengerGNDTO passenger:command.getPassengerInfoGNDTO().getPassengerList()){
			YeeXingPassengerDTO yeeXingPassengerDTO = new YeeXingPassengerDTO();
			yeeXingPassengerDTO.setPassengerName(passenger.getPassengerName());
			yeeXingPassengerDTO.setPassengerType(passenger.getPassengerType());
			yeeXingPassengerDTO.setIdNo(passenger.getIdNo());
			yeeXingPassengerDTO.setIdType(passenger.getIdType());
			passengerList.add(yeeXingPassengerDTO);
		}
		yeeXingPassengerInfoDTO.setName(command.getPassengerInfoGNDTO().getName());
		yeeXingPassengerInfoDTO.setPassengerList(passengerList);
		yeeXingPassengerInfoDTO.setTelephone(command.getPassengerInfoGNDTO().getTelephone());
		jPAutoOrderSpiCommand.setYeeXingpassengerInfoDTO(yeeXingPassengerInfoDTO);
		jPAutoOrderSpiCommand.setDealerOrderId(command.getDealerOrderId());
		jPAutoOrderSpiCommand.setFlightNo(command.getFlightNo());
		//时间转换
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if(command.getStartDate() != null){
			jPAutoOrderSpiCommand.setStartDate(sd.format(command.getStartDate()));	
		}else{//设置默认当前时间
			jPAutoOrderSpiCommand.setStartDate(sd.format(new Date()));
		}
		
		jPAutoOrderSpiCommand.setOutOrderId(null);;//平台订单号 
		jPAutoOrderSpiCommand.setDealerOrderId(command.getDealerOrderId());
		jPAutoOrderSpiCommand.setEncryptString(command.getEncryptString());
		jPAutoOrderSpiCommand.setFromDealerId(command.getFromDealerId());
		//舱位代码
		jPAutoOrderSpiCommand.setCabinCode(command.getCabinCode());
		//舱位名称
		jPAutoOrderSpiCommand.setCabinName(command.getCabinName());
		jPAutoOrderSpiCommand.setUserPayAmount(command.getUserPayAmount());
		//用户支付现金
		jPAutoOrderSpiCommand.setUserPayCash(command.getUserPayCash());
		return jPAutoOrderSpiCommand;
	}
	
}
