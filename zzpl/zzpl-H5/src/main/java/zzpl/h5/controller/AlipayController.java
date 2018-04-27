package zzpl.h5.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zzpl.h5.util.alipay.AlipayNotify;
import zzpl.h5.util.alipay.entity.AlipayWapTradeCreateDirectRes;

@Controller
@RequestMapping("alipay")
public class AlipayController {

	/**
	 * 通知
	 * @param request
	 * @param id
	 * @param type
	 * @param pw
	 * @return
	 * {sign=QPhOG5Q+XweORBK5wM+ITe9O3YEqzIrjpNsztb8njIaNA3RKn5u3XOxU/5u4mFas+7zYAYjfDnLqNo/NOQUL0juGsGpvZeTj7V0KlTSNsR/h1hvtVZK/lF+262yatwGvT7o4+9h7alYQnQE6aApPKW9pT0QoXtLRbWrc5xR//5I=, 
	 * result=success, 
	 * trade_no=2014011352228584, 
	 * sign_type=0001, 
	 * out_trade_no=1, 
	 * request_token=requestToken}
	 * http://mobiren.gicp.net:7000/ylz/pay/alipayApi/notify?out_trade_no=123456813&request_token=requestToken&result=success&trade_no=2014011456024384&sign=JuUYPH4SSp36JCAwpXt5ECQbsXVkdFUaoYgYLzhVl%2BY0hb7yhKJl3TozI4QBUud3CqfOLdXV9u14lsPVoWl%2BXdVpdylR%2B3fDQ%2FHGOmpNrrllAoQO9g9TCiK8bRYKe4lJ0aw0JYqmt5NcZGmZ5XXiYdzyqKzWyi%2FOEKioWICXH6k%3D&sign_type=0001
	 * @throws Exception
	 */
	@RequestMapping(value="callback")
	public String callback(HttpServletRequest request,PrintWriter out) throws Exception{
		Map<String, String> params = getAllReqParam(request);
		AlipayWapTradeCreateDirectRes obj = new AlipayWapTradeCreateDirectRes();
		obj.setSign(params.get("sign"));
		obj.setTradeStatus(params.get("result"));
		obj.setTradeNo(params.get("trade_no"));
		obj.setSecId(params.get("sign_type"));
		obj.setOutTradeNo(params.get("out_trade_no"));
		obj.setRequestToken(params.get("request_token"));
		if(AlipayNotify.verifyReturn(params)){//验证成功
			return "alipay/pay_success_redirect.html";
		}else{//危险
			return "alipay/pay_fail_redirect.html";
		}
	}

	@RequestMapping("payerror")
	public String error() {
		return "alipay/pay_fail_redirect.html";
	}
	
	//获取请求的数据组合成map
	private Map<String, String> getAllReqParam(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}
}
