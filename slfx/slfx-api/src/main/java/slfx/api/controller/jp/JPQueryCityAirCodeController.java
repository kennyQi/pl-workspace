package slfx.api.controller.jp;

import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.response.jp.JPCityAirCodeResponse;
import slfx.jp.pojo.dto.flight.CityAirCodeDTO;
import slfx.jp.spi.inter.CityAirCodeService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：查询城市机场三字码（查询全部数据）Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:56:26
 * @版本：V1.0
 *
 */
@Component("jpQueryCityAirCodeController")
public class JPQueryCityAirCodeController implements SLFXAction {
	
	@Autowired
	private CityAirCodeService cityAirCodeService;

	@Override
	public String execute(ApiRequest apiRequest) {
		/*String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);*/
		//城市机场三字码查询（返回所有数据）
		return jpQueryCityAirCode();
	}

	/**
	 * 
	 * @方法功能说明：查询城市机场三字码（查询全部数据）
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月6日上午10:28:33
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String jpQueryCityAirCode(){
		HgLogger.getInstance().info("tandeng","JPQueryCityAirCodeController->[查询三字码开始]");
		List<CityAirCodeDTO> cityAirCodeDTOList = null;
		try{
			cityAirCodeDTOList = cityAirCodeService.queryCityAirCodeList();
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng", "JPQueryCityAirCodeController->[查询城市机场三字码失败]:"+HgLogger.getStackTrace(e));
		}
		
		JPCityAirCodeResponse jpCityAirCodeResponse = null;
		if(cityAirCodeDTOList != null && cityAirCodeDTOList.size() > 0){
			jpCityAirCodeResponse = new JPCityAirCodeResponse();
			jpCityAirCodeResponse.setMessage("成功");
			jpCityAirCodeResponse.setResult(ApiResponse.RESULT_CODE_OK);
			jpCityAirCodeResponse.setCityAirCodeList(cityAirCodeDTOList);
		}else{
			jpCityAirCodeResponse = new JPCityAirCodeResponse();
			jpCityAirCodeResponse.setMessage("失败");
			jpCityAirCodeResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
			jpCityAirCodeResponse.setCityAirCodeList(null);
		}
		HgLogger.getInstance().info("tandeng","JPQueryCityAirCodeController->[查询三字码结束]:"+ JSON.toJSONString(jpCityAirCodeResponse));
		return JSON.toJSONString(jpCityAirCodeResponse);
	}
}
