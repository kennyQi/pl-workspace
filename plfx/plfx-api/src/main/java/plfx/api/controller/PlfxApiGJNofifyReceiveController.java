package plfx.api.controller;

import hg.log.util.HgLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import plfx.gjjp.app.common.message.YXGJJPAmqpMessage;

/**
 * @类功能说明：国际机票接口通知接收控制器
 * @类修改者：
 * @修改日期：2015-7-16下午2:17:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16下午2:17:35
 */
@Controller
public class PlfxApiGJNofifyReceiveController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/notify-receive", method = RequestMethod.POST)
	public String receive(HttpServletRequest request) {
		// TODO 检查签名...
		Map<String, String[]> map = request.getParameterMap();
		Map<String, String> paramMap = new HashMap<String, String>();
		for (Entry<String, String[]> entry : map.entrySet())
			paramMap.put(entry.getKey(), entry.getValue()[0]);
		String json = JSON.toJSONString(paramMap);
		//System.out.println("接收到易行国际通知-->>");
		//System.out.println(json);
		HgLogger.getInstance().info("zhurz", "接收到易行国际通知-->>\n" + json);
		rabbitTemplate.convertAndSend("plfx.gjjp.supplier.notify", new YXGJJPAmqpMessage(paramMap));
		return "RECV_ORDID_" + paramMap.get("orderId");
	}

}
