package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.common.util.XStreamUtil;
import plfx.yxgjclient.pojo.param.ApplyCancelResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 申请取消接口applyCancelOrder结果实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("applyCancelRS")
public class ApplyCancelRS extends BaseResult{
	private ApplyCancelResult applyCancelResult;

	public ApplyCancelResult getApplyCancelResult() {
		return applyCancelResult;
	}

	public void setApplyCancelResult(ApplyCancelResult applyCancelResult) {
		this.applyCancelResult = applyCancelResult;
	}

	public static void main(String[] args) {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
"<applyCancelRS><isSuccess>T</isSuccess><sign>7f3633046d85027c8d364bddd6ad8a86</sign>"+
"<applyCancelResult><orderId>I201312061655604270</orderId>"+
"<extOrderId>11111111</extOrderId></applyCancelResult></applyCancelRS>";
		ApplyCancelRS rs=XStreamUtil.xmlToObject(ApplyCancelRS.class, xml);
		//System.out.println(rs.getIsSuccess());
		//System.out.println(rs.getSign());
	}
}
