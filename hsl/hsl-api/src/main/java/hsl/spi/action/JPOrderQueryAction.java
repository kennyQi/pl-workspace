package hsl.spi.action;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.response.jp.JPQueryOrderResponse;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.inter.api.jp.JPService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component("jpOrderQueryAction")
public class JPOrderQueryAction implements HSLAction {
	@Resource
	private JPService jpService;
	
	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HslJPOrderQO hslJPOrderQO = JSON.parseObject(apiRequest.getBody().getPayload(), HslJPOrderQO.class);
		
		HgLogger.getInstance().info("zhangka", "JPOrderQueryAction->execute->hslJPOrderQO[订单查询]:" + JSON.toJSONString(hslJPOrderQO));
		
		List<JPOrderDTO> jpOrderDTOs = jpService.queryOrder(hslJPOrderQO);
		
		JPQueryOrderResponse response = new JPQueryOrderResponse();
		
		if (jpOrderDTOs == null) {
			response.setMessage("失败！");
			response.setResult(JPQueryOrderResponse.RESULT_CODE_FAIL);
		} else {
			response.setJpOrders(jpOrderDTOs);
			response.setTotalCount(jpOrderDTOs.size());
		}
		
		HgLogger.getInstance().info("zhangka", "JPOrderQueryAction->execute->response[订单查询]:" +JSON.toJSONString(response.getResult()));
		
		return JSON.toJSONString(response);
	}

}
