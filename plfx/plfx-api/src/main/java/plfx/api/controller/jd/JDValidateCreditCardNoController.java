package plfx.api.controller.jd;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.command.JDValidateCreditCardApiCommand;
import plfx.api.client.api.v1.jd.response.JDValidateCreditCardResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.command.plfx.ylclient.ValidateCreditCardNoCommand;
import plfx.jd.pojo.dto.ylclient.order.ValidateCreditCardNoResultDTO;
import plfx.jd.spi.inter.HotelOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@Component("jdValidateCreditCardNoController")
public class JDValidateCreditCardNoController implements SLFXAction{
	@Autowired
	private HotelOrderService hotelOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDValidateCreditCardApiCommand apiCommand = JSON.toJavaObject(jsonObject, JDValidateCreditCardApiCommand.class);
		
		return validateCreditCardNo(apiCommand);
	}
	
	public String validateCreditCardNo(JDValidateCreditCardApiCommand apiCommand){
		HgLogger.getInstance().info("renfeng", "JDValidateCreditCardNoController->[酒店订单信用卡验证开始]:" + JSON.toJSONString(apiCommand));
		ValidateCreditCardNoResultDTO resultDto = null;
		String message = "酒店订单信用卡验证失败";
		try{
			
			//"apiCommand"转换"分销Command"
		ValidateCreditCardNoCommand command=BeanMapperUtils.map(apiCommand, ValidateCreditCardNoCommand.class);
		resultDto=this.hotelOrderService.validateCreditCardNo(command);
		
		
		
		}catch(Exception e){
			HgLogger.getInstance().info("renfeng", "JDValidateCreditCardNoController->[酒店订单验证信用卡异常]:" + HgLogger.getStackTrace(e));
		}
		
		JDValidateCreditCardResponse jdValidateCreditCardResponse = new JDValidateCreditCardResponse();
		if(resultDto != null){
			jdValidateCreditCardResponse.setMessage("酒店订单信用卡验证成功");
			jdValidateCreditCardResponse.setResult(ApiResponse.RESULT_CODE_OK);
			jdValidateCreditCardResponse.setResultDto(resultDto);
		}else{
			jdValidateCreditCardResponse.setMessage(message);
			jdValidateCreditCardResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("renfeng", "JDValidateCreditCardNoController->[酒店订单信用卡验证结束]:" + JSON.toJSONString(jdValidateCreditCardResponse));
		return JSON.toJSONString(jdValidateCreditCardResponse);
	}
	
}
