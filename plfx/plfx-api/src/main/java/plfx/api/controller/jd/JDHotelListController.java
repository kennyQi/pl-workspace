package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.qo.JDHotelListApiQO;
import plfx.api.client.api.v1.jd.response.JDHotelListResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.dto.ylclient.hotel.GiftDTO;
import plfx.jd.pojo.dto.ylclient.hotel.HotelDTO;
import plfx.jd.pojo.dto.ylclient.hotel.HotelListResultDTO;
import plfx.jd.pojo.qo.ylclient.JDHotelListQO;
import plfx.jd.pojo.system.enumConstants.HotelGiftDateTypeEnum;
import plfx.jd.spi.inter.HotelService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @类功能说明：酒店列表api
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
@Component("jdHotelListController")
public class JDHotelListController implements SLFXAction {

	@Autowired
	private HotelService hotelService;
		
	@Override
	public String execute(ApiRequest apiRequest) {

		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDHotelListApiQO apiQo = JSON.toJavaObject(jsonObject,
				JDHotelListApiQO.class);

		return JDHotelList(apiQo);
	}

	/**
	 * 
	 * @方法功能说明：酒店列表
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String JDHotelList(JDHotelListApiQO apiQo) {

		HgLogger.getInstance().info(
				"caizhenghao",
				"JDHotelListController->[酒店列表开始]:"
						+ JSON.toJSONString(apiQo));
		HotelListResultDTO result = null;
		String message = "查询酒店列表失败";
		try {

			// "apiCommand"转换"分销Command"
			JDHotelListQO qo = new JDHotelListQO();
			BeanUtils.copyProperties(apiQo, qo);
			result = hotelService.queryHotelList(qo);
			HgLogger.getInstance().info("yaosanfeng", "JDHotelListController->[查询列表结果]:" + JSON.toJSONString(result));
			//处理枚举反序列化异常，改变dto类型并设置
			List<HotelDTO> hotels = result.getHotels();
			for(HotelDTO hotel : hotels){
				if(!hotel.getGifts().equals("")||hotel.getGifts() != null){
					List<GiftDTO> gifts = hotel.getGifts();
					for(GiftDTO gift : gifts){
						if(StringUtils.isNotBlank(gift.getDateType())){
							if(gift.getDateType().equals(HotelGiftDateTypeEnum.CheckinDate)){
								gift.setDateType(HotelGiftDateTypeEnum.CheckinDate.toString());
							}
							if(gift.getDateType().equals(HotelGiftDateTypeEnum.BookingDate)){
								gift.setDateType(HotelGiftDateTypeEnum.BookingDate.toString());
							}
						}
					}
				}	
			}
			
		} catch (Exception e) {
			HgLogger.getInstance().info(
					"caizhenghao",
					"JDHotelListController->[查询酒店列表异常]:"
							+ HgLogger.getStackTrace(e));
		}

		JDHotelListResponse jdHotelListResponse = new JDHotelListResponse();
		if (result != null) {
			jdHotelListResponse.setMessage("查询酒店列表成功");
			jdHotelListResponse.setHotelListResultDTO(result);
			jdHotelListResponse.setResult(ApiResponse.RESULT_CODE_OK);
		} else {
			jdHotelListResponse.setMessage(message);
			jdHotelListResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}

		HgLogger.getInstance().info(
				"caizhenghao",
				"JDHotelListController->[查询酒店列表结束]:"
						+ JSON.toJSONString(jdHotelListResponse));
		return JSON.toJSONString(jdHotelListResponse,SerializerFeature.DisableCircularReferenceDetect);
	}

}
