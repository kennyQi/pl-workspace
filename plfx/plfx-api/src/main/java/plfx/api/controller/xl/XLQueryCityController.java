package plfx.api.controller.xl;

import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.xl.request.qo.XLCityApiQO;
import plfx.api.client.api.v1.xl.response.XLQueryCityResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.xl.pojo.dto.CityOfCountryDTO;
import plfx.xl.pojo.qo.LineCityQo;
import plfx.xl.spi.inter.CityOfCountryService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/****
 * 
 * @类功能说明：国家城市查询Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月18日下午12:57:43
 * @版本：V1.0
 *
 */
@Component("xlQueryCityController")
public class XLQueryCityController implements SLFXAction {
	
	@Autowired
	private CityOfCountryService cityOfCountryService;
	

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		XLCityApiQO qo = JSON.toJavaObject(jsonObject, XLCityApiQO.class);
		
		return xlQueryCity(qo);
	}

	/****
	 * 
	 * @方法功能说明：城市查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月18日下午12:59:49
	 * @修改内容：
	 * @参数：@param apiQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String xlQueryCity(XLCityApiQO apiQO){
		HgLogger.getInstance().info("yaosanfeng","XLQueryCityController->xlQueryCity->[线路查询开始]:"+ JSON.toJSONString(apiQO));
		List<CityOfCountryDTO> countryCityList = null;
        //CityOfCountryDTO cityOfCountryDTO = null;
      
		try{
			//"apiQO"转换"分销QO"
			LineCityQo lineCityQo = new LineCityQo();
			BeanUtils.copyProperties(apiQO,lineCityQo);
			countryCityList = cityOfCountryService.queryLineCityList(lineCityQo);

		}catch(Exception e){
			HgLogger.getInstance().error("yaosanfeng","XLQueryCityController->xlQueryCity->[城市查询异常]:"+HgLogger.getStackTrace(e));
		}
		
		XLQueryCityResponse xLQueryCityResponse = new XLQueryCityResponse();
		
		if(countryCityList != null && countryCityList.size() > 0){
			xLQueryCityResponse.setMessage("成功");
			xLQueryCityResponse.setResult(ApiResponse.RESULT_CODE_OK);
			xLQueryCityResponse.setCountryCityList(countryCityList);
		}else{
			xLQueryCityResponse.setMessage("失败");
			xLQueryCityResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("yaosanfeng","XLQueryCityController->xlQueryCity->[城市查询结束]:"+ JSON.toJSONString(xLQueryCityResponse));
		return JSON.toJSONString(xLQueryCityResponse);
	}

}
