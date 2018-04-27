package plfx.api.controller.xl;

import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.xl.request.qo.XLCountryApiQO;
import plfx.api.client.api.v1.xl.response.XLQueryCountryResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.xl.pojo.dto.CountryDTO;
import plfx.xl.pojo.qo.LineCountryQo;
import plfx.xl.spi.inter.CountryService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/****
 * 
 * @类功能说明：线路国家查询Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月18日下午12:41:52
 * @版本：V1.0
 *
 */
@Component("xlQueryCountryController")
public class XLQueryCountryController implements SLFXAction {
	
	@Autowired
	private CountryService countryService;
	

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		XLCountryApiQO qo = JSON.toJavaObject(jsonObject, XLCountryApiQO.class);
		
		return xlQueryCountry(qo);
	}
	
	/****
	 * 
	 * @方法功能说明：国家查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月18日下午12:40:30
	 * @修改内容：
	 * @参数：@param apiQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String xlQueryCountry(XLCountryApiQO apiQO){
		HgLogger.getInstance().info("yaosanfeng","XLQueryCountryController->xlQueryCountry->[国家查询开始]:"+ JSON.toJSONString(apiQO));
		List<CountryDTO> countryList = null; 
		//CountryDTO countryDTO = null;
		try{
			//"apiQO"转换"分销QO"
			LineCountryQo lineCountryQo = new LineCountryQo();
			BeanUtils.copyProperties(apiQO,lineCountryQo);
			countryList = countryService.queryList(lineCountryQo);
			
		}catch(Exception e){
			HgLogger.getInstance().error("yaosanfeng","XLQueryCountryController->xlQueryCountry->[国家查询异常]:"+HgLogger.getStackTrace(e));
		}
		
		XLQueryCountryResponse xLQueryCountryResponse = new XLQueryCountryResponse();
		
		if(countryList != null && countryList.size() > 0){
			xLQueryCountryResponse.setMessage("成功");
			xLQueryCountryResponse.setResult(ApiResponse.RESULT_CODE_OK);
			xLQueryCountryResponse.setCountryList(countryList);
		}else{
			xLQueryCountryResponse.setMessage("失败");
			xLQueryCountryResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("yaosanfeng","XLQueryCountryController->xlQueryCountry->[国家查询返回结果]:"+ JSON.toJSONString(xLQueryCountryResponse));
		return JSON.toJSONString(xLQueryCountryResponse);
	}

}
