package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.qo.JDInventoryApiQO;
import plfx.api.client.api.v1.jd.response.JDHotelInventoryResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.dto.ylclient.hotel.HotelDataInventoryResultDTO;
import plfx.jd.pojo.qo.ylclient.JDInventoryQO;
import plfx.jd.spi.inter.HotelService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：酒店库存验证api(本接口属于高级功能接口，通常开发使用不到，不建议使用。)
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
@Component("jdHotelInventoryController")
public class JDHotelInventoryController implements SLFXAction{

	@Autowired
	private HotelService hotelService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDInventoryApiQO apiQo = JSON.toJavaObject(jsonObject, JDInventoryApiQO.class);
		
		return jdHotelInventory(apiQo);
	}
	/**
	 * 
	 * @方法功能说明：酒店库存验证
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String jdHotelInventory(JDInventoryApiQO apiQo){
		
		HgLogger.getInstance().info("caizhenghao", "JDHotelInventoryController->[查询酒店库存开始]:" + JSON.toJSONString(apiQo));
		HotelDataInventoryResultDTO result = null;
		String message = "查询酒店库存失败";
		try{
			
			//"apiCommand"转换"分销Command"
			JDInventoryQO qo = new JDInventoryQO();
			BeanUtils.copyProperties(apiQo,qo);
			result = hotelService.queryHotelInventory(qo);
			
		}catch(Exception e){
			HgLogger.getInstance().info("caizhenghao", "JDHotelInventoryController->[查询酒店库存异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		JDHotelInventoryResponse jdHotelInventoryResponse = new JDHotelInventoryResponse();
		if(result != null){
			jdHotelInventoryResponse.setMessage("查询酒店库存成功");
			jdHotelInventoryResponse.setResult(ApiResponse.RESULT_CODE_OK);
			jdHotelInventoryResponse.setInventories(result.getInventories());
		}else{
			jdHotelInventoryResponse.setMessage(message);
			jdHotelInventoryResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("caizhenghao", "JDHotelInventoryController->[查询酒店库存结束]:" + JSON.toJSONString(jdHotelInventoryResponse));
		return JSON.toJSONString(jdHotelInventoryResponse);
	}
	
	
	

}
