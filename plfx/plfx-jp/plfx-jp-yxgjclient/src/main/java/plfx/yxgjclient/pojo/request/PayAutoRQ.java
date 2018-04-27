package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.PayAutoParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 自动扣款payAuto查询实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("payAutoRQ")
public class PayAutoRQ {
	public PayAutoRQ(){
		payAutoParams=new PayAutoParams();
	}
	private String sign;
	private PayAutoParams payAutoParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public PayAutoParams getPayAutoParams() {
		return payAutoParams;
	}

	public void setPayAutoParams(PayAutoParams payAutoParams) {
		this.payAutoParams = payAutoParams;
	}
}
