package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.ApplyRefundResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 申请退废票applyRefundTicket返回结果实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("applyRefundRS")
public class ApplyRefundRS extends BaseResult{
	private ApplyRefundResult applyRefundResult;

	public ApplyRefundResult getApplyRefundResult() {
		return applyRefundResult;
	}

	public void setApplyRefundResult(ApplyRefundResult applyRefundResult) {
		this.applyRefundResult = applyRefundResult;
	}

}
