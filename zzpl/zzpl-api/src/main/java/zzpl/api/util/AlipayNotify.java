package zzpl.api.util;




import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;




import com.alibaba.fastjson.JSON;

/**
 *类名：AlipayNotify
 *功能：支付宝通知处理类
 *详细：处理支付宝各接口通知返回
 *************************注意*************************
 *调试通知返回时，可查看或改写log日志的写入TXT里的数据，来检查通知返回是否正常
 */
public class AlipayNotify {

	
	private String signType=SysProperties.getInstance().get("signType");
	
	private String inputCharset=SysProperties.getInstance().get("inputCharset");
	
	private String alipayVerifyUrl=SysProperties.getInstance().get("alipayVerifyUrl");
	
	private String keys=SysProperties.getInstance().get("keys");
	
	private String partner=SysProperties.getInstance().get("partner");
	
	/**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public  boolean verify(Map<String, String> params) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
    	HgLogger.getInstance().info("lxs_dev", "【支付宝校验】1.请求参数MAP："+JSON.toJSONString(params));
    	String responseTxt = "true";
		if(params.get("notify_id") != null) {
			String notify_id = params.get("notify_id");
			HgLogger.getInstance().info("lxs_dev", "【支付宝校验】2.notify_id不为空："+notify_id);
			responseTxt = verifyResponse(notify_id);
		}
	    String sign = "";
	    if(params.get("sign") != null) {
	    	sign = params.get("sign");
	    	HgLogger.getInstance().info("lxs_dev", "【支付宝校验】5.sign不为空："+sign);
	    }
	    boolean isSign = getSignVeryfy(params, sign);

        //写日志记录（若要调试，请取消下面两行注释）
        //String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
	    //AlipayCore.logResult(sWord);

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	private  boolean getSignVeryfy(Map<String, String> Params, String sign) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
    	HgLogger.getInstance().info("lxs_dev", "【支付宝校验】6.sParaNew："+JSON.toJSONString(sParaNew));
        //获取待签名字符串
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        HgLogger.getInstance().info("lxs_dev", "【支付宝校验】7.preSignStr："+preSignStr);
        //获得签名验证结果
        boolean isSign = false;
        if(signType.equals("RSA") ) {
        	isSign = RSA.verify(preSignStr, sign, keys, "utf-8");
        	HgLogger.getInstance().info("lxs_dev", "【支付宝校验】MD5验证：preSignStr="+preSignStr+"结束，sign="+sign+"结束，keys="+keys+"结束，alipay.charset="+inputCharset+"结束");
        	HgLogger.getInstance().info("lxs_dev", "【支付宝校验】8.isSign："+isSign);
        }
        return isSign;
    }
	 /**
	    * 获取远程服务器ATN结果,验证返回URL
	    * @param notify_id 通知校验ID
	    * @return 服务器ATN结果
	    * 验证结果集：
	    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	    * true 返回正确信息
	    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	    */
	    private  String verifyResponse(String notify_id) {
	        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

	        String veryfy_url = alipayVerifyUrl + "partner=" + partner + "&notify_id=" + notify_id;
	        HgLogger.getInstance().info("lxs_dev", "【支付宝校验】3.veryfy_url："+veryfy_url);
	        return checkUrl(veryfy_url);
	    }
	    /**
	     * 获取远程服务器ATN结果
	     * @param urlvalue 指定URL路径地址
	     * @return 服务器ATN结果
	     * 验证结果集：
	     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	     * true 返回正确信息
	     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	     */
	     private  String checkUrl(String urlvalue) {
	         String inputLine = "";

	         try {
	             URL url = new URL(urlvalue);
	             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	             BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
	                 .getInputStream()));
	             inputLine = in.readLine().toString();
	         } catch (Exception e) {
	             e.printStackTrace();
	             inputLine = "";
	         }
	         HgLogger.getInstance().info("lxs_dev", "【支付宝校验】4.inputLine："+inputLine);
	         return inputLine;
	     }
	     
	     public String getKeys() {
	 		return keys;
	 	}
	 	public void setKeys(String keys) {
	 		this.keys = keys;
	 	}
	 	public String getPartner() {
	 		return partner;
	 	}
	 	public void setPartner(String partner) {
	 		this.partner = partner;
	 	}
	 	public String getSignType() {
			return signType;
		}
		public void setSignType(String signType) {
			this.signType = signType;
		}
		public String getInputCharset() {
			return inputCharset;
		}
		public void setInputCharset(String inputCharset) {
			this.inputCharset = inputCharset;
		}
		public String getAlipayVerifyUrl() {
			return alipayVerifyUrl;
		}
		public void setAlipayVerifyUrl(String alipayVerifyUrl) {
			this.alipayVerifyUrl = alipayVerifyUrl;
		}
	 	public static void main(String[] args) {
			String preSignStr="body=线路测试描述&buyer_email=15868814112&buyer_id=2088612866244443&discount=0.00&gmt_create=2015-05-22 16:59:06&gmt_payment=2015-05-22 16:59:06&is_total_fee_adjust=N&notify_id=13e898f3a662c2d93280a79761f75aae4g&notify_time=2015-05-22 16:59:06&notify_type=trade_status_sync&out_trade_no=B522165602110000&payment_type=1&price=0.01&quantity=1&seller_email=payment@ply365.com&seller_id=2088911645517621&subject=线路测试&total_fee=0.01&trade_no=2015052200001000440060320783&trade_status=TRADE_SUCCESS&use_coupon=N";
			String sign="Hh7GreIhSZp/1JK8qWTlyXegLM4DREioZ8QAALCPy90WUliWAtykrWDaZuawXIf6JPCw4FqTrIUJpvMzqvo4Wss25JLK84Z9mryKAB43TBJi4yfHqIU4JvzuSJZpR6rXKOl+p/bwwaH6zzUztlNI/A/2+brGwoaSbWGA8MHvXrw=";
			String ali_public_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
			if(RSA.verify(preSignStr, sign, ali_public_key, "utf-8")){
				System.out.println("fuck");
			}
		}
}