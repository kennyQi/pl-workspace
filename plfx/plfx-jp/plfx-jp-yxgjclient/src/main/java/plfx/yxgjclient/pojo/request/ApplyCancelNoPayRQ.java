package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.common.util.XStreamUtil;
import plfx.yxgjclient.pojo.param.ApplyCancelNoPayParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 未支付申请取消applyCancelOrderNoPay请求实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("applyCancelNoPayRQ")
public class ApplyCancelNoPayRQ {
	public ApplyCancelNoPayRQ(){
		applyCancelNoPayParams=new ApplyCancelNoPayParams();
	}
	private String sign;
	/**
	 * 申请取消订单参数
	 */
	private ApplyCancelNoPayParams applyCancelNoPayParams;
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	public ApplyCancelNoPayParams getApplyCancelNoPayParams() {
		return applyCancelNoPayParams;
	}

	public void setApplyCancelNoPayParams(ApplyCancelNoPayParams applyCancelNoPayParams) {
		this.applyCancelNoPayParams = applyCancelNoPayParams;
	}

	public static void main(String[] args) {
		ApplyCancelNoPayRQ rq=new ApplyCancelNoPayRQ();
//		rq.applyCancelNoPayParams=new ApplyCancelNoPayParams();
		String xml=XStreamUtil.objectToXML(rq);
		//System.out.println(xml);
//		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//		"<applyCancelNoPayRQ>"+
//		"<sign>728a0aae93d22031660fce4bb1784008</sign>"+
//		"<applyCancelNoPayParams>"+
//		"<signUserName>caigouis</signUserName>"+
//		"<serviceName>applyCancelOrderNoPay</serviceName>"+
//		"<orderId>I201405211847310018</orderId>"+
//		"<extOrderId></extOrderId>"+
//		"<cancelReasonType>1</cancelReasonType>"+
//		"<cancelOtherReason></cancelOtherReason>"+
//		"</applyCancelNoPayParams>"+
//		"</applyCancelNoPayRQ>";
//		ApplyCancelNoPayRQ rq=XStreamUtil.xmlToObject(ApplyCancelNoPayRQ.class, xml);
//		System.out.println(rq.getApplyCancelNoPayParams().getServiceName());
	}

}
