package hsl.spi.controller;

import hg.common.util.JsonUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.util.Md5Util;
import hsl.spi.action.ActionContext;
import hsl.spi.action.HSLAction;
import hsl.spi.inter.Coupon.CouponService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

@Controller
public class ApiController extends BaseController {

	@Resource
	private ActionContext actionContext;
	
	@Resource
	private CouponService couponService;
	
	@ResponseBody
	@RequestMapping(value = "/api")
	public String api(HttpServletRequest request) {

		ApiResponse response = null;
		try {
			// 请求信息转ApiRequest类
			String msg = request.getParameter("msg");
			HgLogger.getInstance().info("zhangka", "ApiController->api->msg:" + msg);

			ApiRequest apiRequest = JSON.parseObject(msg, ApiRequest.class);
			
			if (checkSign(apiRequest)) {
				String actionName = apiRequest.getHead().getActionName();
				HSLAction action = actionContext.get(actionName);
				return action.execute(apiRequest);
			} else {
				response = new ApiResponse();
				response.setMessage("非法请求，请检查你的客户端密钥！");
				response.setResult(ApiResponse.RESULT_CODE_FAIL);
				HgLogger.getInstance().info("zhangka", "ApiController->api->msg:" + msg + "->非法请求，请检查你的客户端密钥！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhangka", "ApiController->api->exception:"+HgLogger.getStackTrace(e));
		}
		
		return JSON.toJSONString(response);
	}
	
	
	/**
	 * 检验sign是否正确
	 * @param apiRequest
	 * @return
	 */
	private boolean checkSign(ApiRequest apiRequest) {

		// 客户端sign
		String clientSign = apiRequest.getHead().getSign();
		// 客户端key
		String clientKey = apiRequest.getHead().getClientKey();

		// 去掉客户端msg中的sign值
		apiRequest.getHead().setSign(null);
		
		String msg = JsonUtil.parseObject(apiRequest, false);
		
		String secret_key = SysProperties.getInstance().get(clientKey);
		
		String sign = Md5Util.MD5(clientKey + secret_key + msg);
		return sign.equalsIgnoreCase(clientSign) ? true : false;
		
	}

}
