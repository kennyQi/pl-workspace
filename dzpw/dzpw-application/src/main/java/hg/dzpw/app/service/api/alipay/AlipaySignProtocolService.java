package hg.dzpw.app.service.api.alipay;

import hg.dzpw.app.common.AlipayConfig;
import hg.dzpw.app.common.util.alipay.AlipayCore;
import hg.dzpw.app.common.util.alipay.AlipaySubmitUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;


/**
 * @类功能说明：支付宝签约服务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-3-2上午9:33:16
 * @版本：V1.0
 *
 */
@Service
public class AlipaySignProtocolService {
	
	/**
	 * @方法功能说明：生成请求支付宝签约接口的URL
	 * @修改者名字：yangkang
	 * @修改时间：2016-3-2上午10:52:10
	 * @参数：@return
	 * @return:String
	 */
	public String buildSignProtocolURL(){
		// 设置签约接口需要的参数
		Map<String, String> sPara = new HashMap<String, String>(){
			{
				put("service", "sign_protocol_with_partner");
				put("partner", AlipayConfig.partner);
				put("_input_charset", AlipayConfig.input_charset);
			}
		};
		
		// 调用返回MAP中包含了签名结果sign、签名方式sign_type
		sPara = AlipaySubmitUtil.buildRequestPara(sPara);
		
		return AlipayConfig.ALIPAY_GATEWAY_NEW + AlipayCore.createLinkString(sPara);
		
	}
	
	
	/**
	 * @方法功能说明：支付宝代扣签约查询
	 * @修改者名字：yangkang
	 * @修改时间：2016-3-7下午3:28:43
	 * @参数：@param email 需要签约的经销商支付宝账号
	 * @参数：@return
	 * @return:String
	 */
	public String queryCustomerProtocol(String email){
		
		// 设置签约查询接口需要的参数
		Map<String, String> sPara = new HashMap<String, String>(){
			{
				put("service", "query_customer_protocol");
				put("partner", AlipayConfig.partner);
				put("_input_charset", AlipayConfig.input_charset);
				put("biz_type", "10004");
			}
		};
		
		// 需要签约的经销商支付宝账号
		sPara.put("user_email", email);
		
		try {
			return AlipaySubmitUtil.buildRequest("", "", sPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
