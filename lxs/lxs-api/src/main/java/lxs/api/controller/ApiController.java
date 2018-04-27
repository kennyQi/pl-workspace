package lxs.api.controller;

import hg.common.util.Md5Util;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lxs.api.action.ActionContext;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
public class ApiController extends BaseController {

	@Resource
	private ActionContext actionContext;
	
	@Resource
	private SMSUtils smsUtils;
	
	@RequestMapping(value = "/api")
	@ResponseBody
	public String api(HttpServletRequest request) {
		HgLogger.getInstance().error("lxs_dev", "------------------------------------------------------------------------------------------------------------------------------------------------");
		ApiResponse response = null;
		try {
			HgLogger.getInstance().info("lxs_dev", "开始请求接口:" );
			String sign = request.getParameter("sign");
//			System.out.println(request.getRequestURL());
			HgLogger.getInstance().info("lxs_dev", "客户端传入sign:" +sign);
			String headmsg = request.getParameter("headmsg");
			HgLogger.getInstance().info("lxs_dev", "客户端传入headmsg:" +headmsg);
			String bodymsg = (String)request.getParameter("bodymsg");
			HgLogger.getInstance().info("lxs_dev", "客户端传入bodeymsg:" +bodymsg);
			String msg="{\"body\":"+bodymsg+",\"head\":"+headmsg+"}";
			// 请求信息转ApiRequest类
			ApiRequest apiRequest = JSON.parseObject(msg, ApiRequest.class);
			HgLogger.getInstance().info("lxs_dev", "开始验证密匙");
			if (checkSign(apiRequest,headmsg,bodymsg, sign)) {
				HgLogger.getInstance().info("lxs_dev", "密匙验证成功，开始转向对应action");
				String actionName = apiRequest.getHead().getActionName();
				HgLogger.getInstance().info("lxs_dev", "获取action的name");
				LxsAction action = actionContext.get(actionName);
				return action.execute(apiRequest);
			} else {
				response = new ApiResponse();
				response.setMessage("非法请求，请检查你的客户端密钥！");
				response.setResult(ApiResponse.RESULT_CODE_FAIL);
				HgLogger.getInstance().info("lxs_dev", "ApiController->api->headmsg:" + headmsg+"->bodymsg:"+bodymsg + "->非法请求，请检查你的客户端密钥！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("lxs_dev", "ApiController->api->exception:"+HgLogger.getStackTrace(e));
		}
		
		return JSON.toJSONString(response);
	}
	
	/**
	 * 检验sign是否正确
	 * @param apiRequest
	 * @return
	 */
	private boolean checkSign(ApiRequest apiRequest,String headmsg,String bodymsg,String clientSign) {

		// 客户端key
		String clientKey = apiRequest.getHead().getClientKey();
		String secret_key = SysProperties.getInstance().get(clientKey);
//		
//		String headmsg = JsonUtil.parseObject(apiRequest.getHead(), false);
//		String bodymsg = JsonUtil.parseObject(apiRequest.getBody(), false);
		
		String sign = Md5Util.MD5(clientKey + secret_key + headmsg+bodymsg);
		HgLogger.getInstance().info("lxs_dev", "生成MD5校验码:" +sign);
		return sign.equalsIgnoreCase(clientSign) ? true : false;
		
	}

}
