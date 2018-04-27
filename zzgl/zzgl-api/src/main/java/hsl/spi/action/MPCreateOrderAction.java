package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.command.mp.MPOrderCreateCommand;
import hsl.api.v1.response.mp.MPOrderCreateResponse;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.spi.inter.mp.MPOrderService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("mpCreateOrderAction")
@SuppressWarnings("rawtypes")
public class MPCreateOrderAction implements HSLAction {
	@Autowired
	private MPOrderService mpOrderService;
	@Autowired
	private HgLogger hgLogger;
	
	

	@Override
	public String execute(ApiRequest apiRequest) {
		MPOrderCreateCommand mpOrderCreateCommand = JSON.parseObject(apiRequest.getBody().getPayload(), MPOrderCreateCommand.class);
		hsl.pojo.command.MPOrderCreateCommand command=BeanMapperUtils.map(mpOrderCreateCommand, hsl.pojo.command.MPOrderCreateCommand.class);
		command.setSource(mpOrderCreateCommand.getFromClientKey());
		hgLogger.info("liujz", "门票预定请求"+JSON.toJSONString(mpOrderCreateCommand));
		Map map = mpOrderService.createMPOrder(command);
		MPOrderCreateResponse orderCreateResponse = new MPOrderCreateResponse();
		orderCreateResponse.setOrderId(((MPOrderDTO)map.get("dto")).getDealerOrderCode());
		orderCreateResponse.setResult(map.get("code").toString());
		
		if (map.get("msg") != null) {
			orderCreateResponse.setMessage(map.get("msg").toString());
		}
		hgLogger.info("liujz", "门票预定结果"+JSON.toJSONString(orderCreateResponse));
		return JSON.toJSONString(orderCreateResponse);
	}
	
}
