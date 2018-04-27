package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.ApplyRefundParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 申请退废票applyRefundTicket查询实体
 * 
 * @author guotx 2015-06-30
 */
@XStreamAlias("applyRefundRQ")
public class ApplyRefundRQ {
	public ApplyRefundRQ() {
		applyRefundParams = new ApplyRefundParams();
	}

	private String sign;
	private ApplyRefundParams applyRefundParams;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public ApplyRefundParams getApplyRefundParams() {
		return applyRefundParams;
	}

	public void setApplyRefundParams(ApplyRefundParams applyRefundParams) {
		this.applyRefundParams = applyRefundParams;
	}

}
