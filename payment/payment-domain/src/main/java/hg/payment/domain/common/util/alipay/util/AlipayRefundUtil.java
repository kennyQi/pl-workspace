package hg.payment.domain.common.util.alipay.util;

import hg.payment.domain.common.util.alipay.sign.MD5;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.domain.model.refund.AlipayRefund;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * 
 *@类功能说明：支付宝工具类
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月26日下午3:51:49
 *
 */
@Component
public class AlipayRefundUtil {

	private AlipayRefund alipayRefund;
	
	
	public  AlipayRefund getAlipayRefund() {
		return alipayRefund;
	}

	public  void setAlipayRefund(AlipayRefund alipayRefund) {
		this.alipayRefund = alipayRefund;
	}
	
	
	
    
    
	  public String buildRefundRequest(Map<String, String> sParaTemp) {
	        //待请求参数数组
	        Map<String, String> sPara = buildRequestPara(sParaTemp);
	        AlipayPayChannel alipay = (AlipayPayChannel)alipayRefund.getPayOrder().getPayChannel();
	        StringBuffer url = new StringBuffer(alipay.getAlipayUrl());
	        
	        List<String> keys = new ArrayList<String>(sPara.keySet());
	        
	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = sPara.get(key);

	            if("notify_url".equals(key) || "refund_date".equals(key) || "detail_data".equals(key)){ //值含有特殊字符和中文的需要进行URLEncodeing
	            	try {
						value = URLEncoder.encode(value, alipay.getInputCharset());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
	            }
	            
	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                url.append(key + "=" + value);
	            } else {
	                url.append(key + "=" + value + "&");
	            }
	        }
	        
	        return url.toString();

	    }
	  
	   public String buildRefundRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
	        //待请求参数数组
	        Map<String, String> sPara = buildRequestPara(sParaTemp);
	        List<String> keys = new ArrayList<String>(sPara.keySet());

	        StringBuffer sbHtml = new StringBuffer();

	        AlipayPayChannel alipayPayChannel = (AlipayPayChannel)alipayRefund.getPayOrder().getPayChannel();
	        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + alipayPayChannel.getAlipayUrl()
	                      + "_input_charset=" + alipayPayChannel.getInputCharset() + "\" method=\"" + strMethod
	                      + "\">");

	        for (int i = 0; i < keys.size(); i++) {
	            String name = (String) keys.get(i);
	            String value = (String) sPara.get(name);

	            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
	        }

	        //submit按钮控件请不要含有name属性
	        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
	        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

	        return sbHtml.toString();
	    }
	    
	    /**
	     * 生成要请求给支付宝的参数数组
	     * @param sParaTemp 请求前的参数数组
	     * @return 要请求的参数数组
	     */
	    private  Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
	        //除去数组中的空值和签名参数
	        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
	        //生成签名结果
	        String mysign = buildRequestMysign(sPara);

	        //签名结果与签名方式加入请求提交参数组中
	        sPara.put("sign", mysign);
	        AlipayPayChannel alipayPayChannel = (AlipayPayChannel)alipayRefund.getPayOrder().getPayChannel();
	        sPara.put("sign_type", alipayPayChannel.getSignType());

	        return sPara;
	    }
	    
	    
	    /**
	     * 生成签名结果
	     * @param sPara 要签名的数组
	     * @return 签名结果字符串
	     */
		public  String buildRequestMysign(Map<String, String> sPara) {
	    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	        String mysign = "";
	        AlipayPayChannel alipay = (AlipayPayChannel)alipayRefund.getPayOrder().getPayChannel();
	        if(alipay.getSignType().equals("MD5") ) {
	        	mysign = MD5.sign(prestr, alipayRefund.getKeys(), alipay.getInputCharset());
	        }
	        return mysign;
	    }
	
	
}
