package hsl.admin.controller.gate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 电子票订单通知控制器
 *
 * @author zhurz
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/gate/dzp")
public class DZPTicketOrderGateController {

	/**
	 * 电子票务通知入口
	 *
	 * @param event   事件 {@link hg.dzpw.dealer.client.common.publish.PublishEventRequest}
	 * @param message 消息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dzpw-notify")
	public String fxNotify(
			@RequestParam("event") final String event,
			@RequestParam("message") final String message
	) {

		return "success";
	}
}
