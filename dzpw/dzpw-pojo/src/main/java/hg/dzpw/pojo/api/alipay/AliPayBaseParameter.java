package hg.dzpw.pojo.api.alipay;
/**
 * 
 * @类功能说明：支付宝接口基本参数
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-2-29下午2:33:25
 * @版本：
 */
public class AliPayBaseParameter {
	/** 参数编码，不可空*/
	private String _input_charset;
	/** 接口名称，不可空，代扣接口传cae_charge_agent,退款传refund_fastpay_by_platform_nopwd*/
	private String service; 
	
	/** 合作者身份Id，以 2088 开头的 16 位纯数字组成,不可空*/
	private String partner;
	
	/** 签名，不可空*/
	private String sign;
	
	/** 签名方式，签名方式只支持 DSA、RSA、 MD5不可空*/
	private String sign_type;
	
	/** 服务器异步通知页面路径，可空*/
	private String notify_url;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	
}
