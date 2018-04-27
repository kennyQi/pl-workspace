package hsl.spi.action;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.response.jp.JPQueryFlightResponse;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.spi.inter.api.jp.JPService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import slfx.api.v1.request.qo.jp.JPFlightQO;

import com.alibaba.fastjson.JSON;

@Component("jpQueryFlightAction")
public class JPQueryFlightAction implements HSLAction {
	@Resource
	private JPService jpService;
	
	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		JPFlightQO jpFlightQO = JSON.parseObject(apiRequest.getBody().getPayload(), JPFlightQO.class);
		
		HgLogger.getInstance().info("zhangka", "JPQueryFlightAction->execute->jpFlightQO[航班查询]:" + JSON.toJSONString(jpFlightQO));
		
		List<FlightDTO> flightDTOs = jpService.queryFlight(jpFlightQO);
		
		JPQueryFlightResponse response = new JPQueryFlightResponse();
		
		if (flightDTOs == null) {
			response.setMessage("失败！");
			response.setResult(JPQueryFlightResponse.RESULT_CODE_FAIL);
		} else {
			response.setFlightList(flightDTOs);
			response.setTotalCount(flightDTOs.size());
		}
		
		HgLogger.getInstance().info("zhangka", "JPQueryFlightAction->execute->response[航班查询]:" +JSON.toJSONString(response.getResult()));
		
		return JSON.toJSONString(response);
	}

}
