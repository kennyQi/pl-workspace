package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.common.util.XStreamUtil;
import plfx.yxgjclient.pojo.param.ApplyCancelNoPayResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 未支付申请取消applyCancelOrderNoPay返回实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("applyCancelNoPayRS")
public class ApplyCancelNoPayRS extends BaseResult{
	private ApplyCancelNoPayResult applyCancelNoPayResult;

	public ApplyCancelNoPayResult getApplyCancelNoPayResult() {
		return applyCancelNoPayResult;
	}

	public void setApplyCancelNoPayResult(
			ApplyCancelNoPayResult applyCancelNoPayResult) {
		this.applyCancelNoPayResult = applyCancelNoPayResult;
	}
	public static void main(String[] args) {
//		ApplyCancelNoPayRS rs=new ApplyCancelNoPayRS();
//		String xml=XStreamUtil.objectToXML(rs);
//		System.out.println(xml);
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
		"<applyCancelNoPayRS>"+
		"<isSuccess>T</isSuccess>"+
		"<sign>0cf73fc63aac6aa5dfd72a46b4d3af2a</sign>"+
		"<applyCancelNoPayResult>"+
		"<orderId>I201405211847310018</orderId>"+
		"</applyCancelNoPayResult>"+
		"</applyCancelNoPayRS>";
		ApplyCancelNoPayRS rs=XStreamUtil.xmlToObject(ApplyCancelNoPayRS.class, xml);
		//System.out.println(rs.getIsSuccess());
	}
}
