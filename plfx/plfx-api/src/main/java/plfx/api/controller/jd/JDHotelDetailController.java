package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.qo.JDHotelDetailApiQO;
import plfx.api.client.api.v1.jd.response.JDHotelListResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.dto.ylclient.hotel.HotelListResultDTO;
import plfx.jd.pojo.qo.ylclient.JDHotelDetailQO;
import plfx.jd.spi.inter.HotelService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @类功能说明：酒店详情api
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
@Component("jdHotelDetailController")
public class JDHotelDetailController implements SLFXAction{

	@Autowired
	private HotelService hotelService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDHotelDetailApiQO apiQo = JSON.toJavaObject(jsonObject, JDHotelDetailApiQO.class);
		
		return JDHotelDetail(apiQo);
	}
	/**
	 * 
	 * @方法功能说明：酒店查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String JDHotelDetail(JDHotelDetailApiQO apiQo){
		
		HgLogger.getInstance().info("caizhenghao", "JDHotelDetailController->[查询酒店详情开始]:" + JSON.toJSONString(apiQo));
		HotelListResultDTO result = null;
		String message = "查询酒店详情失败";
		try{
			
			//"apiCommand"转换"分销Command"
			JDHotelDetailQO qo = new JDHotelDetailQO();
			BeanUtils.copyProperties(apiQo,qo);
			
			result = hotelService.queryHotelDetail(qo);
			HgLogger.getInstance().info("yaosanfeng", "JDHotelDetailController->[查询酒店详情结果]:" + JSON.toJSONString(result));
		}catch(Exception e){
			HgLogger.getInstance().error("caizhenghao", "JDHotelDetailController->[查询酒店详情异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		JDHotelListResponse jdHotelListResponse = new JDHotelListResponse();
		if(result != null){
			jdHotelListResponse.setMessage("查询酒店详情成功");
			jdHotelListResponse.setHotelListResultDTO(result);
			jdHotelListResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jdHotelListResponse.setMessage(message);
			jdHotelListResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("caizhenghao", "JDHotelDetailController->[查询酒店详情结束]:" + JSON.toJSONString(jdHotelListResponse));
		return JSON.toJSONString(jdHotelListResponse,SerializerFeature.DisableCircularReferenceDetect);
	}

}
