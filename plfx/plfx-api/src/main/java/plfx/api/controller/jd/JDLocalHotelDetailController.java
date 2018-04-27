package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.qo.JDLocalHotelDetailApiQO;
import plfx.api.client.api.v1.jd.response.JDLocalHotelDetailResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.dto.plfx.hotel.YLHotelDTO;
import plfx.jd.pojo.qo.YLHotelQO;
import plfx.jd.spi.inter.HotelDetailLocalService;
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
@Component("jdLocalHotelDetailController")
public class JDLocalHotelDetailController implements SLFXAction{

	@Autowired
	private HotelDetailLocalService hotelDetailLocalService;
	
	

	@Autowired
	private HotelService hotelService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDLocalHotelDetailApiQO apiQo = JSON.toJavaObject(jsonObject, JDLocalHotelDetailApiQO.class);
		
		return JDHotelDetail(apiQo);
	}
	/**
	 * 
	 * @方法功能说明：酒店查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String JDHotelDetail(JDLocalHotelDetailApiQO apiQo){
		
		HgLogger.getInstance().info("yaosanfeng", "JDLocalHotelDetailController->[查询酒店详情开始]:" + JSON.toJSONString(apiQo));
		YLHotelDTO result = null;
		String message = "查询本地酒店详情失败";
		try{
			
		
			YLHotelQO qo = new YLHotelQO();
			BeanUtils.copyProperties(apiQo,qo);
			//本地酒店查询
			result = hotelDetailLocalService.searchByHotelId(qo);	
			
			HgLogger.getInstance().info("yaosanfeng", "JDLocalHotelDetailController->[查询酒店详情结果]:" + JSON.toJSONString(result));
		}catch(Exception e){
			HgLogger.getInstance().info("yaosanfeng", "JDLocalHotelDetailController->[查询酒店详情异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		JDLocalHotelDetailResponse jdHotelListResponse = new JDLocalHotelDetailResponse();
		if(result != null){
			jdHotelListResponse.setMessage("查询本地酒店详情成功");
			jdHotelListResponse.setYlHotelDTO(result);
			jdHotelListResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jdHotelListResponse.setMessage(message);
			jdHotelListResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("yaosanfeng", "JDLocalHotelDetailController->[查询酒店详情结束]:" + JSON.toJSONString(jdHotelListResponse));
		return JSON.toJSONString(jdHotelListResponse,SerializerFeature.DisableCircularReferenceDetect);
	}

}
