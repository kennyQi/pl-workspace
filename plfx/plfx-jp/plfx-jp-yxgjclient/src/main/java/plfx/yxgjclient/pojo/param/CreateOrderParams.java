package plfx.yxgjclient.pojo.param;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 创建订单请求参数列表
 * @author guotx
 * 2015-07-07
 */
@XStreamAlias("createOrderParams")
public class CreateOrderParams extends BaseParam{
	/**
	 * 乘客信息，必填
	 */
	private List<PassengerInfo> passengerInfos;
	/**
	 * 联系人信息，非必填
	 */
	private ContacterInfo contacterInfo;
	/**
	 * 外部定单号，必填
	 */
	private String extOrderId;
	/**
	 * 客服审核通知地址，必填
	 */
	private String auditNotifyUrl;
	/**
	 * 加密字符串，必填
	 */
	private String encryptString;
	/**
	 * 采购商订单备注信息，非必填
	 */
	private String ordMemo;
	
	public List<PassengerInfo> getPassengerInfos() {
		return passengerInfos;
	}
	public void setPassengerInfos(List<PassengerInfo> passengerInfos) {
		this.passengerInfos = passengerInfos;
	}
	public ContacterInfo getContacterInfo() {
		return contacterInfo;
	}
	public void setContacterInfo(ContacterInfo contacterInfo) {
		this.contacterInfo = contacterInfo;
	}
	public String getExtOrderId() {
		return extOrderId;
	}
	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}
	public String getAuditNotifyUrl() {
		return auditNotifyUrl;
	}
	public void setAuditNotifyUrl(String auditNotifyUrl) {
		this.auditNotifyUrl = auditNotifyUrl;
	}
	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
	public String getOrdMemo() {
		return ordMemo;
	}
	public void setOrdMemo(String ordMemo) {
		this.ordMemo = ordMemo;
	}
}
