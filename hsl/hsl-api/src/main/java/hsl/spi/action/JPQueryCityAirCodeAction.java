package hsl.spi.action;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.response.jp.JPCityAirCodeResponse;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.spi.inter.api.jp.JPService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import slfx.api.v1.request.qo.jp.JPAirCodeQO;

import com.alibaba.fastjson.JSON;

@Component("jpQueryCityAirCodeAction")
public class JPQueryCityAirCodeAction implements HSLAction {
	@Resource
	private JPService jpService;
	
	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		JPAirCodeQO jpAirCodeQO = JSON.parseObject(apiRequest.getBody().getPayload(), JPAirCodeQO.class);
		
		HgLogger.getInstance().info("zhangka", "JPQueryCityAirCodeAction->execute->jpAirCodeQO[查询全部城市三字码]:"+ JSON.toJSONString(jpAirCodeQO));
		
		List<CityAirCodeDTO> cityAirCodeDTOs = jpService.queryCityAirCode(jpAirCodeQO);
		
		JPCityAirCodeResponse response = new JPCityAirCodeResponse();
		
		if (cityAirCodeDTOs == null) {
			response.setMessage("失败！");
			response.setResult(JPCityAirCodeResponse.RESULT_CODE_FAIL);
		} else {
			response.setCityAirCodeList(cityAirCodeDTOs);
		}
		
		HgLogger.getInstance().info("zhangka", "JPQueryCityAirCodeAction->execute->response[查询全部城市三字码]:" +JSON.toJSONString(response.getResult()));
		
		return JSON.toJSONString(response);
	}

}
