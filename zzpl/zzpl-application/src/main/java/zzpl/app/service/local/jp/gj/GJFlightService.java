package zzpl.app.service.local.jp.gj;

import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.api.client.api.v1.gj.request.FlightGJQO;
import plfx.api.client.api.v1.gj.request.FlightMoreCabinsGJQO;
import plfx.api.client.api.v1.gj.request.FlightPolicyGJQO;
import plfx.api.client.api.v1.gj.response.ApplyCancelNoPayOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyCancelOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyRefundTicketGJResponse;
import plfx.api.client.api.v1.gj.response.CreateJPOrderGJResponse;
import plfx.api.client.api.v1.gj.response.FlightGJResponse;
import plfx.api.client.api.v1.gj.response.FlightMoreCabinsGJResponse;
import plfx.api.client.api.v1.gj.response.FlightPolicyGJResponse;
import plfx.api.client.api.v1.gj.response.PayToJPOrderGJResponse;
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.util.PlfxApiClient;
import zzpl.pojo.command.jp.plfx.gj.ApplyCancelNoPayOrderGJCommand;
import zzpl.pojo.command.jp.plfx.gj.ApplyCancelOrderGJCommand;
import zzpl.pojo.command.jp.plfx.gj.ApplyRefundTicketGJCommand;
import zzpl.pojo.command.jp.plfx.gj.CreateJPOrderGJCommand;
import zzpl.pojo.command.jp.plfx.gj.PayToJPOrderGJCommand;
import zzpl.pojo.dto.jp.plfx.gj.ApplyCancelNoPayOrderGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.ApplyCancelOrderGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.ApplyRefundTicketGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.CreateJPOrderGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.FlightGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.FlightMoreCabinsGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.FlightPolicyGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.PayToJPOrderGJDTO;
import zzpl.pojo.exception.jp.GJFlightException;
import zzpl.pojo.qo.jp.plfx.gj.JPFlightGJQO;
import zzpl.pojo.qo.jp.plfx.gj.JPMoreCabinsGJQO;
import zzpl.pojo.qo.jp.plfx.gj.JPPolicyGJQO;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class GJFlightService {

	@Autowired
	private PlfxApiClient plfxApiClient;
	
	/**
	 * 
	 * @方法功能说明：航班查询
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:22:29
	 * @修改内容：
	 * @参数：@param jpFlightGJQO
	 * @参数：@return
	 * @参数：@throws GNFlightException
	 * @return:FlightGJDTO
	 * @throws
	 */
	public FlightGJDTO queryGJFlight(JPFlightGJQO jpFlightGJQO) throws GJFlightException{
		HgLogger.getInstance().info("cs", "【GJFlightService】【queryGJFlight】"+"jpFlightGJQO:"+JSON.toJSONString(jpFlightGJQO));
		FlightGJQO flightGJQO= JSON.parseObject(JSON.toJSONString(jpFlightGJQO), FlightGJQO.class);
		FlightGJResponse flightGJResponse =  plfxApiClient.send(flightGJQO, FlightGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【queryGJFlight】"+"flightGJResponse:"+JSON.toJSONString(flightGJResponse));
		if(StringUtils.isNotBlank(flightGJResponse.getResult())&&StringUtils.equals(flightGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(flightGJResponse), FlightGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：舱位查询
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:23:50
	 * @修改内容：
	 * @参数：@param jpMoreCabinsGJQO
	 * @参数：@return
	 * @参数：@throws GNFlightException
	 * @return:FlightMoreCabinsGJDTO
	 * @throws
	 */
	public FlightMoreCabinsGJDTO queryGJMoreCabins(JPMoreCabinsGJQO jpMoreCabinsGJQO) throws GJFlightException{
		HgLogger.getInstance().info("cs", "【GJFlightService】【queryGJMoreCabins】"+"jpMoreCabinsGJQO:"+JSON.toJSONString(jpMoreCabinsGJQO));
		FlightMoreCabinsGJQO flightMoreCabinsGJQO= JSON.parseObject(JSON.toJSONString(jpMoreCabinsGJQO), FlightMoreCabinsGJQO.class);
		FlightMoreCabinsGJResponse flightMoreCabinsGJResponse =  plfxApiClient.send(flightMoreCabinsGJQO, FlightMoreCabinsGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【queryGJMoreCabins】"+"flightMoreCabinsGJResponse:"+JSON.toJSONString(flightMoreCabinsGJResponse));
		if(StringUtils.isNotBlank(flightMoreCabinsGJResponse.getResult())&&StringUtils.equals(flightMoreCabinsGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(flightMoreCabinsGJResponse), FlightMoreCabinsGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：查询政策
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:26:45
	 * @修改内容：
	 * @参数：@param jpPolicyGJQO
	 * @参数：@return
	 * @参数：@throws GJFlightException
	 * @return:FlightPolicyGJDTO
	 * @throws
	 */
	public FlightPolicyGJDTO queryGJPolicy(JPPolicyGJQO jpPolicyGJQO) throws GJFlightException{
		HgLogger.getInstance().info("cs", "【GJFlightService】【queryGJPolicy】"+"jpPolicyGJQO:"+JSON.toJSONString(jpPolicyGJQO));
		FlightPolicyGJQO flightPolicyGJQO= JSON.parseObject(JSON.toJSONString(jpPolicyGJQO), FlightPolicyGJQO.class);
		FlightPolicyGJResponse flightPolicyGJResponse =  plfxApiClient.send(flightPolicyGJQO, FlightPolicyGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【queryGJPolicy】"+"flightPolicyGJResponse:"+JSON.toJSONString(flightPolicyGJResponse));
		if(StringUtils.isNotBlank(flightPolicyGJResponse.getResult())&&StringUtils.equals(flightPolicyGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(flightPolicyGJResponse), FlightPolicyGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：创建订单
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:32:09
	 * @修改内容：
	 * @参数：@param createJPOrderGJCommand
	 * @参数：@return
	 * @参数：@throws GJFlightException
	 * @return:CreateJPOrderGJDTO
	 * @throws
	 */
	public CreateJPOrderGJDTO createOrderGJ(CreateJPOrderGJCommand createJPOrderGJCommand)  throws GJFlightException{
		HgLogger.getInstance().info("cs", "【GJFlightService】【createOrderGJ】"+"createJPOrderGJCommand:"+JSON.toJSONString(createJPOrderGJCommand));
		plfx.api.client.api.v1.gj.request.CreateJPOrderGJCommand plfxCreateJPOrderGJCommand= JSON.parseObject(JSON.toJSONString(createJPOrderGJCommand), plfx.api.client.api.v1.gj.request.CreateJPOrderGJCommand.class);
		CreateJPOrderGJResponse createJPOrderGJResponse =  plfxApiClient.send(plfxCreateJPOrderGJCommand, CreateJPOrderGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【createOrderGJ】"+"createJPOrderGJResponse:"+JSON.toJSONString(createJPOrderGJResponse));
		if(StringUtils.isNotBlank(createJPOrderGJResponse.getResult())&&StringUtils.equals(createJPOrderGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(createJPOrderGJResponse), CreateJPOrderGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：支付
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:32:30
	 * @修改内容：
	 * @参数：@param payToJPOrderGJCommand
	 * @参数：@return
	 * @参数：@throws GJFlightException
	 * @return:PayToJPOrderGJDTO
	 * @throws
	 */
	public PayToJPOrderGJDTO payToOrderGJ(PayToJPOrderGJCommand payToJPOrderGJCommand) throws GJFlightException{
		HgLogger.getInstance().info("cs", "【GJFlightService】【payToOrderGJ】"+"payToJPOrderGJCommand:"+JSON.toJSONString(payToJPOrderGJCommand));
		plfx.api.client.api.v1.gj.request.PayToJPOrderGJCommand plfxPayToJPOrderGJCommand= JSON.parseObject(JSON.toJSONString(payToJPOrderGJCommand), plfx.api.client.api.v1.gj.request.PayToJPOrderGJCommand.class);
		PayToJPOrderGJResponse payToJPOrderGJResponse =  plfxApiClient.send(plfxPayToJPOrderGJCommand, PayToJPOrderGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【payToOrderGJ】"+"payToJPOrderGJResponse:"+JSON.toJSONString(payToJPOrderGJResponse));
		if(StringUtils.isNotBlank(payToJPOrderGJResponse.getResult())&&StringUtils.equals(payToJPOrderGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(payToJPOrderGJResponse), PayToJPOrderGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：取消未支付订单
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:33:40
	 * @修改内容：
	 * @参数：@param applyCancelNoPayOrderGJCommand
	 * @参数：@return
	 * @参数：@throws GJFlightException
	 * @return:ApplyCancelNoPayOrderGJDTO
	 * @throws
	 */
	public ApplyCancelNoPayOrderGJDTO applyCancelNoPayOrderGJ(ApplyCancelNoPayOrderGJCommand applyCancelNoPayOrderGJCommand) throws GJFlightException{
		HgLogger.getInstance().info("cs", "【GJFlightService】【applyCancelNoPayOrderGJ】"+"applyCancelNoPayOrderGJCommand:"+JSON.toJSONString(applyCancelNoPayOrderGJCommand));
		plfx.api.client.api.v1.gj.request.ApplyCancelNoPayOrderGJCommand plfxApplyCancelNoPayOrderGJCommand= JSON.parseObject(JSON.toJSONString(applyCancelNoPayOrderGJCommand), plfx.api.client.api.v1.gj.request.ApplyCancelNoPayOrderGJCommand.class);
		ApplyCancelNoPayOrderGJResponse applyCancelNoPayOrderGJResponse =  plfxApiClient.send(plfxApplyCancelNoPayOrderGJCommand, ApplyCancelNoPayOrderGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【applyCancelNoPayOrderGJ】"+"applyCancelNoPayOrderGJResponse:"+JSON.toJSONString(applyCancelNoPayOrderGJResponse));
		if(StringUtils.isNotBlank(applyCancelNoPayOrderGJResponse.getResult())&&StringUtils.equals(applyCancelNoPayOrderGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(applyCancelNoPayOrderGJResponse), ApplyCancelNoPayOrderGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:34:12
	 * @修改内容：
	 * @参数：@param applyCancelOrderGJCommand
	 * @参数：@return
	 * @参数：@throws GJFlightException
	 * @return:ApplyCancelOrderGJDTO
	 * @throws
	 */
	public ApplyCancelOrderGJDTO applyCancelOrderGJ(ApplyCancelOrderGJCommand applyCancelOrderGJCommand) throws GJFlightException{
		HgLogger.getInstance().info("cs", "【GJFlightService】【applyCancelOrderGJ】"+"applyCancelOrderGJCommand:"+JSON.toJSONString(applyCancelOrderGJCommand));
		plfx.api.client.api.v1.gj.request.ApplyCancelOrderGJCommand plfxApplyCancelOrderGJCommand= JSON.parseObject(JSON.toJSONString(applyCancelOrderGJCommand), plfx.api.client.api.v1.gj.request.ApplyCancelOrderGJCommand.class);
		ApplyCancelOrderGJResponse applyCancelOrderGJResponse =  plfxApiClient.send(plfxApplyCancelOrderGJCommand, ApplyCancelOrderGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【applyCancelOrderGJ】"+"applyCancelOrderGJResponse:"+JSON.toJSONString(applyCancelOrderGJResponse));
		if(StringUtils.isNotBlank(applyCancelOrderGJResponse.getResult())&&StringUtils.equals(applyCancelOrderGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(applyCancelOrderGJResponse), ApplyCancelOrderGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：退废票
	 * @修改者名字：cangs
	 * @修改时间：2015年7月21日下午2:34:46
	 * @修改内容：
	 * @参数：@param applyRefundTicketGJCommand
	 * @参数：@return
	 * @参数：@throws GJFlightException
	 * @return:ApplyRefundTicketGJDTO
	 * @throws
	 */
	public ApplyRefundTicketGJDTO applyRefundTicketGJ(ApplyRefundTicketGJCommand applyRefundTicketGJCommand) throws GJFlightException {
		HgLogger.getInstance().info("cs", "【GJFlightService】【applyRefundTicketGJ】"+"applyRefundTicketGJCommand:"+JSON.toJSONString(applyRefundTicketGJCommand));
		plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand plfxApplyRefundTicketGJCommand = JSON.parseObject(JSON.toJSONString(applyRefundTicketGJCommand), plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand.class);
		ApplyRefundTicketGJResponse applyRefundTicketGJResponse =  plfxApiClient.send(plfxApplyRefundTicketGJCommand, ApplyRefundTicketGJResponse.class);
		HgLogger.getInstance().info("cs", "【GJFlightService】【applyRefundTicketGJ】"+"applyRefundTicketGJResponse:"+JSON.toJSONString(applyRefundTicketGJResponse));
		if(StringUtils.isNotBlank(applyRefundTicketGJResponse.getResult())&&StringUtils.equals(applyRefundTicketGJResponse.getResult(), ApiResponse.RESULT_SUCCESS)){
			return JSON.parseObject(JSON.toJSONString(applyRefundTicketGJResponse), ApplyRefundTicketGJDTO.class);
		}else{
			throw new GJFlightException(GJFlightException.PLFX_ERROR_CODE,GJFlightException.PLFX_ERROR_MESSAGE);
		}
	}
}
