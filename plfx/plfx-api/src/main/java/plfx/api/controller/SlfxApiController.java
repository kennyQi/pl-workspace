package plfx.api.controller;

import hg.common.util.JsonUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.client.util.Md5Util;
import plfx.api.controller.base.ActionContext;
import plfx.api.controller.base.BaseController;
import plfx.api.controller.base.SLFXAction;
import plfx.jp.spi.inter.CityAirCodeService;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.yeexing.pojo.command.order.JPOrderCommand;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：SLFX-API开放http请求接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月6日上午11:17:12
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value = "/slfx")
public class SlfxApiController extends BaseController {
	
	@Resource
	private ActionContext actionContext;
	
//	@Autowired
//	private WebFlightService webFlightService;
//	@Autowired
//	private FlightPolicyService flightPolicyService;
//	@Autowired
//	private CityAirCodeService cityAirCodeService;
//	@Autowired
//	private JPPlatformOrderService jpPlatformOrderService;
//	
//	@Autowired
//	private ApiMPDatePriceService apiMPDatePriceService;
//	@Autowired
//	private ApiMPOrderService apiMPOrderService;
//	@Autowired
//	private ApiMPPolicyService apiMPPolicyService;
//	@Autowired
//	private ApiMPScenicSpotsService apiMPScenicSpotsService;
	
	/**
	 * 
	 * @方法功能说明：分销接口入口
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日上午9:29:03
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/api")
	public String api(HttpServletRequest request) {
		HgLogger.getInstance().info("tandeng","SlfxApiController->api->开始执行");

		ApiResponse response = null;
		try {
			// 请求信息转ApiRequest类
			String msg = request.getParameter("msg");

			// 请求信息转ApiRequest类
			ApiRequest apiRequest = JSON.parseObject(msg, ApiRequest.class);
			//if (checkSign(apiRequest)) {
			if (apiRequest != null) {
						
				String controllerName = apiRequest.getHead().getActionName();
				
				SLFXAction action = actionContext.get(controllerName);
				
				return action.execute(apiRequest);
				
			} else {
				response = new ApiResponse();
				response.setMessage("非法请求！");
				response.setResult(ApiResponse.RESULT_CODE_FAILE);
				HgLogger.getInstance().info("tandeng", "SlfxApiController->api->msg:" + msg + "->非法请求，请检查你的客户端密钥！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("tandeng", "SlfxApiController->api->exception:"+HgLogger.getStackTrace(e));
		}
		//System.out.println("--------------"+JSON.toJSONString(response));
		return JSON.toJSONString(response);
	}

	/**
	 * 检验sign是否正确
	 * @param apiRequest
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkSign(ApiRequest apiRequest) {

		// 获取客户端sign
		String clientSign = apiRequest.getHead().getSign();
		
		// 去掉客户端msg中的sign值
		apiRequest.getHead().setSign(null);
		String msg = JsonUtil.parseObject(apiRequest, false);
		
		// 客户端key
		String clientKey = apiRequest.getHead().getFromClientKey();
		
		// 从数据库获取密匙key
		String secretKey = "";
		String sign = Md5Util.MD5(clientKey + secretKey + msg);
		
		return sign.equals(clientSign) ? true : false;
	}

}
