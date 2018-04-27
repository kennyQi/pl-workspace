package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.common.util.XStreamUtil;
import plfx.yxgjclient.pojo.param.ApplyCancelParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 申请取消接口applyCancelOrder查寻实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("applyCancelRQ")
public class ApplyCancelRQ {
	public ApplyCancelRQ(){
		applyCancelParams=new ApplyCancelParams();
	}
	private String sign;
	/**
	 * 申请取消订单参数
	 */
	private ApplyCancelParams applyCancelParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public ApplyCancelParams getApplyCancelParams() {
		return applyCancelParams;
	}

	public void setApplyCancelParams(ApplyCancelParams applyCancelParams) {
		this.applyCancelParams = applyCancelParams;
	}

	public static void main(String[] args) {
		ApplyCancelRQ rq=new ApplyCancelRQ();
		ApplyCancelParams applyCancelParams=new ApplyCancelParams();
		applyCancelParams.setServiceName("applyCancelOrder");
		applyCancelParams.setUserName("PL");
		rq.setApplyCancelParams(applyCancelParams);
		String xString=XStreamUtil.objectToXML(rq);
		//System.out.println(xString);
	}
}
