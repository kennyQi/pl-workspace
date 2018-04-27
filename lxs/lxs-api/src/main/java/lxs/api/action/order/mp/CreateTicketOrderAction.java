package lxs.api.action.order.mp;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.TouristDTO;
import lxs.api.v1.request.command.order.mp.CreateTicketOrderCommand;
import lxs.api.v1.response.order.mp.CreateTicketOrderResponse;
import lxs.app.service.mp.AppService;
import lxs.app.service.mp.TicketOrderService;
import lxs.pojo.exception.mp.CreateTicketOrderException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("CreateTicketOrderAction")
public class CreateTicketOrderAction implements LxsAction {

	@Autowired
	private AppService appService;
	@Autowired
	private TicketOrderService ticketOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【CreateTicketOrderAction】");
		CreateTicketOrderCommand createTicketOrderCommand = JSON.parseObject(apiRequest.getBody().getPayload(),CreateTicketOrderCommand.class);
		HgLogger.getInstance().info("lxs_dev", "【CreateTicketOrderAction】【createTicketOrderCommand】"+JSON.toJSONString(createTicketOrderCommand));
		CreateTicketOrderResponse createTicketOrderResponse = new CreateTicketOrderResponse();
		try {
			// 验证请求参数
			if (StringUtils.isBlank(createTicketOrderCommand.getTicketPolicyId())
					|| StringUtils.isBlank(createTicketOrderCommand.getBookMan())
					|| StringUtils.isBlank(createTicketOrderCommand.getBookManMobile())
					|| createTicketOrderCommand.getTourists() == null) {
				throw (new CreateTicketOrderException(CreateTicketOrderException.PARAMETER_ILLEGAL));
			}
			//验证游客参数
			for (TouristDTO touristDTO : createTicketOrderCommand.getTourists()) {
				if (StringUtils.isBlank(touristDTO.getName())
						|| touristDTO.getIdType()==null
						|| StringUtils.isBlank(touristDTO.getIdNo())) {
					throw (new CreateTicketOrderException(CreateTicketOrderException.PARAMETER_ILLEGAL));
				}
			}
			lxs.pojo.command.mp.CreateTicketOrderCommand command = JSON.parseObject(JSON.toJSONString(createTicketOrderCommand), lxs.pojo.command.mp.CreateTicketOrderCommand.class);
			String orderID = appService.createTicketOrder(command);
			String orderNO = ticketOrderService.get(orderID).getOrderNO();
			createTicketOrderResponse.setOrderID(orderID);
			createTicketOrderResponse.setOrderNO(orderNO);
			createTicketOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
		} catch (CreateTicketOrderException createTicketOrderException) {
			createTicketOrderResponse.setResult(createTicketOrderException.getCode());
			createTicketOrderResponse.setMessage(createTicketOrderException.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【CreateTicketOrderAction】【createTicketOrderResponse】"+JSON.toJSONString(createTicketOrderResponse));
		return JSON.toJSONString(createTicketOrderResponse);
	}

}
