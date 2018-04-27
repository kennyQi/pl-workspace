package zzpl.app.service.local.jp.gn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.api.client.api.v1.gn.request.JPPayOrderGNCommand;
import plfx.api.client.api.v1.gn.response.CancelTicketGNResponse;
import plfx.api.client.api.v1.gn.response.JPBookOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPPayOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryFlightListGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryHighPolicyGNResponse;
import plfx.api.client.api.v1.gn.response.RefundTicketGNResponse;
import plfx.api.client.common.util.PlfxApiClient;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.service.local.log.LogService;
import zzpl.pojo.command.jp.plfx.gn.CancelTicketGNCommand;
import zzpl.pojo.command.jp.plfx.gn.JPBookTicketGNCommand;
import zzpl.pojo.command.jp.plfx.gn.PayToJPOrderGNCommand;
import zzpl.pojo.command.jp.plfx.gn.RefundTicketGNCommand;
import zzpl.pojo.command.log.SystemCommunicationLogHTTPCommand;
import zzpl.pojo.dto.jp.plfx.gn.CancelTicketGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.JPBookOrderGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.JPQueryFlightListGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.JPQueryHighPolicyGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.PayToJPOrderGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.RefundTicketGNDTO;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.plfx.gn.JPFlightGNQO;
import zzpl.pojo.qo.jp.plfx.gn.JPPolicyGNQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：调用远程分销接口
 * @类修改者：
 * @修改日期：2015年7月13日上午10:28:33
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月13日上午10:28:33
 */
@Service
@Transactional
public class GNFlightService{
	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private PlfxApiClient plfxApiClient;
	@Autowired
	private LogService logService;

	
	/**
	 * @throws GNFlightException 
	 * 
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
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryGNFlight】"+"jpFlightGNQO:"+JSON.toJSONString(jpFlightGNQO));
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date =sFormat.parse(jpFlightGNQO.getStartDate());
			jpFlightGNQO.setStartDate(sFormat.format(date));
		} catch (ParseException e) {
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		} 
		plfx.api.client.api.v1.gn.request.JPFlightGNQO plfxFlightGNQO = JSON.parseObject(JSON.toJSONString(jpFlightGNQO), plfx.api.client.api.v1.gn.request.JPFlightGNQO.class);
		plfxFlightGNQO.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryGNFlight】"+"plfxFlightGNQO:"+JSON.toJSONString(plfxFlightGNQO));
		long startTime=System.currentTimeMillis(); 
		JPQueryFlightListGNResponse jpQueryFlightListGNResponse =  plfxApiClient.send(plfxFlightGNQO, JPQueryFlightListGNResponse.class);
		long endTime=System.currentTimeMillis(); 
		HgLogger.getInstance().info("cs", "【GNFlightService】【运行时间】"+"查询航班调用分销接口时间： "+(endTime-startTime)+"ms");
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryGNFlight】"+"jpQueryFlightListGNResponse:"+JSON.toJSONString(jpQueryFlightListGNResponse));
		if(jpQueryFlightListGNResponse.getIs_success()!=null){
			if(StringUtils.equals(jpQueryFlightListGNResponse.getIs_success(),"T"))
				return JSON.parseObject(JSON.toJSONString(jpQueryFlightListGNResponse), JPQueryFlightListGNDTO.class);
			if(jpQueryFlightListGNResponse.getError().split("\\^").length==1){
				throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
			}else{
				throw new GNFlightException(Integer.parseInt(jpQueryFlightListGNResponse.getError().split("\\^")[0]),jpQueryFlightListGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * @throws GNFlightException 
	 * 
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
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryHighPolicyGN】"+"jpPolicyGNQO:"+JSON.toJSONString(jpPolicyGNQO));
		plfx.api.client.api.v1.gn.request.JPPolicyGNQO plfxPolicyGNQO = JSON.parseObject(JSON.toJSONString(jpPolicyGNQO),plfx.api.client.api.v1.gn.request.JPPolicyGNQO.class);
		plfxPolicyGNQO.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryHighPolicyGN】"+"plfxPolicyGNQO:"+JSON.toJSONString(plfxPolicyGNQO));
		JPQueryHighPolicyGNResponse jpQueryHighPolicyGNResponse = plfxApiClient.send(plfxPolicyGNQO, JPQueryHighPolicyGNResponse.class);
		HgLogger.getInstance().info("cs", "【GNFlightService】【queryHighPolicyGN】"+"jpQueryHighPolicyGNResponse:"+JSON.toJSONString(jpQueryHighPolicyGNResponse));
		if(jpQueryHighPolicyGNResponse.getIs_success()!=null){
			SystemCommunicationLogHTTPCommand command = new SystemCommunicationLogHTTPCommand();
			command.setDealerKey(SysProperties.getInstance().get("dealerKey"));
			command.setSecretKey(SysProperties.getInstance().get("fx_secretKey"));
			command.setUrl(SysProperties.getInstance().get("apiUrl"));
			command.setRequset(JSON.toJSONString(plfxPolicyGNQO));
			command.setResponse(JSON.toJSONString(jpQueryHighPolicyGNResponse));
			logService.saveLog(command);
			if(StringUtils.equals(jpQueryHighPolicyGNResponse.getIs_success(), "T"))
				return JSON.parseObject(JSON.toJSONString(jpQueryHighPolicyGNResponse), JPQueryHighPolicyGNDTO.class);
			if(jpQueryHighPolicyGNResponse.getError().split("\\^").length==1){
				throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
			}else{
				throw new GNFlightException(Integer.parseInt(jpQueryHighPolicyGNResponse.getError().split("\\^")[0]),jpQueryHighPolicyGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * @throws GNFlightException 
	 * 
	 * @方法功能说明：定国内机票
	 * @修改者名字：cangs
	 * @修改时间：2015年7月13日上午9:31:27
	 * @修改内容：
	 * @参数：@param bookGNFlightCommand
	 * @参数：@return
	 * @return:JPBookOrderGNDTO
	 * @throws
	 */
	public JPBookOrderGNDTO bookOrderGN (JPBookTicketGNCommand jpBookTicketGNCommand) throws GNFlightException{
		HgLogger.getInstance().info("cs", "【GNFlightService】【bookOrderGN】"+"jpBookTicketGNCommand:"+JSON.toJSONString(jpBookTicketGNCommand));
		plfx.api.client.api.v1.gn.request.JPBookTicketGNCommand plfxBookTicketGNCommand = JSON.parseObject(JSON.toJSONString(jpBookTicketGNCommand),plfx.api.client.api.v1.gn.request.JPBookTicketGNCommand.class);
		plfxBookTicketGNCommand.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		HgLogger.getInstance().info("cs", "【GNFlightService】【bookOrderGN】"+"plfxBookTicketGNCommand:"+JSON.toJSONString(plfxBookTicketGNCommand));
		JPBookOrderGNResponse jpBookOrderGNResponse = plfxApiClient.send(plfxBookTicketGNCommand, JPBookOrderGNResponse.class);
		HgLogger.getInstance().info("cs", "【GNFlightService】【bookOrderGN】"+"jpBookOrderGNResponse:"+JSON.toJSONString(jpBookOrderGNResponse));
		if(jpBookOrderGNResponse.getIs_success()!=null){
			SystemCommunicationLogHTTPCommand command = new SystemCommunicationLogHTTPCommand();
			command.setDealerKey(SysProperties.getInstance().get("dealerKey"));
			command.setSecretKey(SysProperties.getInstance().get("fx_secretKey"));
			command.setUrl(SysProperties.getInstance().get("apiUrl"));
			command.setRequset(JSON.toJSONString(plfxBookTicketGNCommand));
			command.setResponse(JSON.toJSONString(jpBookOrderGNResponse));
			logService.saveLog(command);
			if(StringUtils.equals(jpBookOrderGNResponse.getIs_success(), "T"))
				return JSON.parseObject(JSON.toJSONString(jpBookOrderGNResponse), JPBookOrderGNDTO.class);
			if(jpBookOrderGNResponse.getError().split("\\^").length==1){
				throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
			}else{
				throw new GNFlightException(Integer.parseInt(jpBookOrderGNResponse.getError().split("\\^")[0]),jpBookOrderGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：支付
	 * @修改者名字：guok
	 * @修改时间：2015年7月27日 13:58:06
	 * @修改内容：
	 * @参数：@param payToJPOrderGNCommand
	 * @参数：@return
	 * @参数：@throws GNFlightException{
	 * @return:PayToJPOrderGNDTO
	 * @throws
	 */
	public PayToJPOrderGNDTO payToOrderGN(PayToJPOrderGNCommand payToJPOrderGNCommand) throws GNFlightException{
		HgLogger.getInstance().info("cs", "【GNFlightService】【payToOrderGN】"+"payToJPOrderGNCommand:"+JSON.toJSONString(payToJPOrderGNCommand));
		JPPayOrderGNCommand plfxPayToJPOrderGNCommand= JSON.parseObject(JSON.toJSONString(payToJPOrderGNCommand), JPPayOrderGNCommand.class);
		plfxPayToJPOrderGNCommand.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		HgLogger.getInstance().info("cs", "【GNFlightService】【payToOrderGN】"+"plfxPayToJPOrderGNCommand:"+JSON.toJSONString(plfxPayToJPOrderGNCommand));
		JPPayOrderGNResponse jpPayOrderGNResponse =  plfxApiClient.send(plfxPayToJPOrderGNCommand, JPPayOrderGNResponse.class);
		HgLogger.getInstance().info("cs", "【GNFlightService】【payToOrderGN】"+"jpPayOrderGNResponse:"+JSON.toJSONString(jpPayOrderGNResponse));
		if(jpPayOrderGNResponse.getIs_success()!=null){
			SystemCommunicationLogHTTPCommand command = new SystemCommunicationLogHTTPCommand();
			command.setDealerKey(SysProperties.getInstance().get("dealerKey"));
			command.setSecretKey(SysProperties.getInstance().get("fx_secretKey"));
			command.setUrl(SysProperties.getInstance().get("apiUrl"));
			command.setRequset(JSON.toJSONString(plfxPayToJPOrderGNCommand));
			command.setResponse(JSON.toJSONString(jpPayOrderGNResponse));
			logService.saveLog(command);
			if(StringUtils.isNotBlank(jpPayOrderGNResponse.getIs_success())&&StringUtils.equals(jpPayOrderGNResponse.getIs_success(), "T"))
				return JSON.parseObject(JSON.toJSONString(jpPayOrderGNResponse), PayToJPOrderGNDTO.class);
			if(jpPayOrderGNResponse.getError().split("\\^").length==1){
				throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
			}else{
				throw new GNFlightException(Integer.parseInt(jpPayOrderGNResponse.getError().split("\\^")[0]),jpPayOrderGNResponse.getError().split("\\^")[1]);
			}
		}else{
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
	public CancelTicketGNDTO cancelTicketGN(CancelTicketGNCommand cancelTicketGNCommand) throws GNFlightException{
		HgLogger.getInstance().info("cs", "【GNFlightService】【cancelTicketGN】"+"cancelTicketGNCommand:"+JSON.toJSONString(cancelTicketGNCommand));
		plfx.api.client.api.v1.gn.request.CancelTicketGNCommand plfxCancelTicketGNCommand = JSON.parseObject(JSON.toJSONString(cancelTicketGNCommand), plfx.api.client.api.v1.gn.request.CancelTicketGNCommand.class);
		plfxCancelTicketGNCommand.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		HgLogger.getInstance().info("cs", "【GNFlightService】【cancelTicketGN】"+"plfxCancelTicketGNCommand:"+JSON.toJSONString(plfxCancelTicketGNCommand));
		CancelTicketGNResponse cancelTicketGNResponse = plfxApiClient.send(plfxCancelTicketGNCommand, CancelTicketGNResponse.class);
		HgLogger.getInstance().info("cs", "【GNFlightService】【cancelTicketGN】"+"cancelTicketGNResponse:"+JSON.toJSONString(cancelTicketGNResponse));
		if(cancelTicketGNResponse.getIs_success()!=null){
			SystemCommunicationLogHTTPCommand command = new SystemCommunicationLogHTTPCommand();
			command.setDealerKey(SysProperties.getInstance().get("dealerKey"));
			command.setSecretKey(SysProperties.getInstance().get("fx_secretKey"));
			command.setUrl(SysProperties.getInstance().get("apiUrl"));
			command.setRequset(JSON.toJSONString(plfxCancelTicketGNCommand));
			command.setResponse(JSON.toJSONString(cancelTicketGNResponse));
			logService.saveLog(command);
			if(StringUtils.equals(cancelTicketGNResponse.getIs_success(), "T"))
				return JSON.parseObject(JSON.toJSONString(cancelTicketGNResponse), CancelTicketGNDTO.class);
			if(cancelTicketGNResponse.getError().split("\\^").length==1){
				throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
			}else{
				throw new GNFlightException(Integer.parseInt(cancelTicketGNResponse.getError().split("\\^")[0]),cancelTicketGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
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
		HgLogger.getInstance().info("cs", "【GNFlightService】【refundTicketGN】"+"refundTicketGNCommand:"+JSON.toJSONString(refundTicketGNCommand));
		plfx.api.client.api.v1.gn.request.RefundTicketGNCommand plfxRefundTicketGNCommand = JSON.parseObject(JSON.toJSONString(refundTicketGNCommand),plfx.api.client.api.v1.gn.request.RefundTicketGNCommand.class);
		plfxRefundTicketGNCommand.setFromDealerId(SysProperties.getInstance().get("dealerKey"));
		HgLogger.getInstance().info("cs", "【GNFlightService】【refundTicketGN】"+"plfxRefundTicketGNCommand:"+JSON.toJSONString(plfxRefundTicketGNCommand));
		RefundTicketGNResponse refundTicketGNResponse = plfxApiClient.send(plfxRefundTicketGNCommand, RefundTicketGNResponse.class);
		HgLogger.getInstance().info("cs", "【GNFlightService】【refundTicketGN】"+"refundTicketGNResponse:"+JSON.toJSONString(refundTicketGNResponse));
		if(refundTicketGNResponse.getIs_success()!=null){
			SystemCommunicationLogHTTPCommand command = new SystemCommunicationLogHTTPCommand();
			command.setDealerKey(SysProperties.getInstance().get("dealerKey"));
			command.setSecretKey(SysProperties.getInstance().get("fx_secretKey"));
			command.setUrl(SysProperties.getInstance().get("apiUrl"));
			command.setRequset(JSON.toJSONString(plfxRefundTicketGNCommand));
			command.setResponse(JSON.toJSONString(refundTicketGNResponse));
			logService.saveLog(command);
			if(StringUtils.equals(refundTicketGNResponse.getIs_success(), "T"))
				return JSON.parseObject(JSON.toJSONString(refundTicketGNResponse), RefundTicketGNDTO.class);
			if(refundTicketGNResponse.getError().split("\\^").length==1){
				throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
			}else{
				throw new GNFlightException(Integer.parseInt(refundTicketGNResponse.getError().split("\\^")[0]),refundTicketGNResponse.getError().split("\\^")[1]);
			}
		}else{
			throw new GNFlightException(GNFlightException.PLFX_ERROR_CODE,GNFlightException.PLFX_ERROR_MESSAGE);
		}
	}
	
}
