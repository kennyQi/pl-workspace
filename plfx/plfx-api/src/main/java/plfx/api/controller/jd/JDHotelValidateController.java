package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.qo.JDValidateApiQO;
import plfx.api.client.api.v1.jd.response.JDHotelDataValidateResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.dto.ylclient.hotel.HotelDataValidateResultDTO;
import plfx.jd.pojo.qo.ylclient.JDValidateQO;
import plfx.jd.spi.inter.HotelService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：酒店订单验证api(建议在提交订单前调用。当返回的Code为0并且ResultCode为OK的情况下表示可以正常提交订单。)
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月11日下午2:03:25
 * @版本：V1.0
 *
 */
@Component("jdHotelValidateController")
public class JDHotelValidateController implements SLFXAction{

	@Autowired
	private HotelService hotelService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDValidateApiQO apiQo = JSON.toJavaObject(jsonObject, JDValidateApiQO.class);
		
		return jdHotelValidate(apiQo);
	}
	/**
	 * 
	 * @方法功能说明：酒店订单验证
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String jdHotelValidate(JDValidateApiQO apiQo){
		
		HgLogger.getInstance().info("caizhenghao", "JDOrderCancelController->[酒店订单验证开始]:" + JSON.toJSONString(apiQo));
		HotelDataValidateResultDTO result = null;
		String message = "酒店订单验证失败";
		try{
			
			//"apiCommand"转换"分销Command"
			JDValidateQO qo = new JDValidateQO();
			BeanUtils.copyProperties(apiQo,qo);
			
			result = hotelService.queryHotelValidate(qo);
			
		}catch(Exception e){
			HgLogger.getInstance().info("caizhenghao", "JDOrderCancelController->[酒店订单验证异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		JDHotelDataValidateResponse jdHotelDataValidateResponse = new JDHotelDataValidateResponse();
		if(result != null){
			jdHotelDataValidateResponse.setMessage("酒店订单验证成功");
			jdHotelDataValidateResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jdHotelDataValidateResponse.setMessage(message);
			jdHotelDataValidateResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("caizhenghao", "JDOrderCancelController->[酒店订单验证结束]:" + JSON.toJSONString(jdHotelDataValidateResponse));
		return JSON.toJSONString(jdHotelDataValidateResponse);
	}
	
	
	

}
