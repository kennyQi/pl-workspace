package plfx.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：测试接收通知
 * @类修改者：
 * @修改日期：2015-7-30下午5:59:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-30下午5:59:09
 */
@Controller
public class TestReceiveController {

	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/test-receive")
	public String receive(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		String json = JSON.toJSONString(map);
		//System.out.println("测试接收通知-->>");
		//System.out.println(request.getRemoteAddr());
		//System.out.println(json);
		return json;
	}

}
