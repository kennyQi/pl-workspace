package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.PayAutoResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 自动扣款接口payAuto返回结果实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("payAutoRS")
public class PayAutoRS extends BaseResult{
	private PayAutoResult payAutoResult;

	public PayAutoResult getPayAutoResult() {
		return payAutoResult;
	}

	public void setPayAutoResult(PayAutoResult payAutoResult) {
		this.payAutoResult = payAutoResult;
	}
}
