package slfx.api.controller.jp;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import slfx.api.v1.response.jp.JPQueryFlightResponse;
import slfx.jp.pojo.dto.flight.PlatformQueryWebFlightsDTO;
import slfx.jp.pojo.exception.JPException;
import slfx.jp.qo.api.JPFlightSpiQO;
import slfx.jp.spi.inter.WebFlightService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：查询航班信息Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:17:38
 * @版本：V1.0
 *
 */
@Component("jpQueryFlightController")
public class JPQueryFlightController implements SLFXAction {
	
	@Autowired
	private WebFlightService webFlightService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JPFlightQO qo = JSON.toJavaObject(jsonObject, JPFlightQO.class);
		
		return jpQueryFlight(qo);
	}
	
	/**
	 * 
	 * @方法功能说明：查询航班信息
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月6日上午10:27:04
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String jpQueryFlight(JPFlightQO qo){
		HgLogger.getInstance().info("tandeng","JPQueryFlightController->[查询航班信息开始]:"+ JSON.toJSONString(qo));
		
		PlatformQueryWebFlightsDTO dto = null;
		JPFlightSpiQO spiQO = new JPFlightSpiQO();
		String message = "失败";
		try{
			BeanUtils.copyProperties(qo, spiQO);
			if(qo != null && qo.getFlightNo() != null && !"".equals(qo.getFlightNo())){
				dto = webFlightService.queryFlightsByFlightNo(spiQO);
			}else{
				dto = webFlightService.queryFlights(spiQO);
			}
		}catch(JPException e){
			message = e.getMessage();
			HgLogger.getInstance().error("tandeng", "JPQueryFlightController->[查询航班信息失败]:"+HgLogger.getStackTrace(e));
		}
		
		JPQueryFlightResponse flightResponse = null;
		
		if(dto != null && dto.getFlightList() != null 
				&& dto.getFlightList().size() > 0){
			
			flightResponse = new JPQueryFlightResponse();
			flightResponse.setMessage("成功");
			flightResponse.setResult(ApiResponse.RESULT_CODE_OK);
			flightResponse.setTotalCount(dto.getFlightList().size());
			flightResponse.setFlightList(dto.getFlightList());
			flightResponse.setTgNoteMap(dto.getTgNoteMap());
		}else{
			flightResponse = new JPQueryFlightResponse();
			flightResponse.setMessage(message);
			flightResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
			flightResponse.setFlightList(null);
			flightResponse.setTotalCount(null);
			flightResponse.setTgNoteMap(null);
		}
		
		HgLogger.getInstance().info("tandeng","JPQueryFlightController->[查询航班信息结束]:"+ JSON.toJSONString(flightResponse.getResult()));
		return JSON.toJSONString(flightResponse);
	}
	

}
