package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 文件政策信息
 * @author guotx
 * 2015-07-06
 */
@XStreamAlias("filePolicyInfo")
public class FilePolicyInfo{
	private String encryptString;
	
		/**
	    * 是否换编码出票
	    */
	   private String isChangePnr;
	   /**
	    * 出票类型
	    */
	   private String outTicketType;
	   /**
	    * 基础返点
	    */
	   private String saleBasicDisc;
	   /**
	    * 奖励返点
	    */
	   private String saleExtraDisc;
	   /**
	    * 开票费
	    */
	   private String outTicketMoney;
	   /**
	    * 购票限制
	    */
	   private String saleLimit;
	   /**
	    * 乘客类型
	    */
	   private String passengerType;
	   /**
	    * 政策ID
	    */
	   private String policyId;
	   /**
	    * 出票速度
	    */
	   private String gsqAvrOutTime;
	   /**
	    * 可支付类型
	    */
	   private String payplatform;
	   /**
	    * 修改时间
	    */
	   private String modiTime;
	   /**
	    * 文件价格
	    */
	   private String filePrice;
	   /**
	    * 儿童价格
	    */
	   private String childFilePrice;
	   /**
	    * 使用人数
	    */
	   private String peopleNum;
	   /**
	    * 成人最终结算价
	    */
	   private String ordFinalPirce;
	   /**
	    * 儿童最终结算价
	    */
	   private String childFinalPirce;
	   /**
	    * 成人税金
	    */
	   private String ordTax;
	   /**
	    * 儿童税金
	    */
	   private String childTax;
	   /**
	    * 营业时间
	    */
	   private String openTime;
	   /**
	    * 退票时间
	    */
	   private String refundTime;
	   /**
	    * 加密串
	    */
	public String getIsChangePnr() {
		return isChangePnr;
	}
	public void setIsChangePnr(String isChangePnr) {
		this.isChangePnr = isChangePnr;
	}
	public String getOutTicketType() {
		return outTicketType;
	}
	public void setOutTicketType(String outTicketType) {
		this.outTicketType = outTicketType;
	}
	public String getSaleBasicDisc() {
		return saleBasicDisc;
	}
	public void setSaleBasicDisc(String saleBasicDisc) {
		this.saleBasicDisc = saleBasicDisc;
	}
	public String getSaleExtraDisc() {
		return saleExtraDisc;
	}
	public void setSaleExtraDisc(String saleExtraDisc) {
		this.saleExtraDisc = saleExtraDisc;
	}
	public String getOutTicketMoney() {
		return outTicketMoney;
	}
	public void setOutTicketMoney(String outTicketMoney) {
		this.outTicketMoney = outTicketMoney;
	}
	public String getSaleLimit() {
		return saleLimit;
	}
	public void setSaleLimit(String saleLimit) {
		this.saleLimit = saleLimit;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getGsqAvrOutTime() {
		return gsqAvrOutTime;
	}
	public void setGsqAvrOutTime(String gsqAvrOutTime) {
		this.gsqAvrOutTime = gsqAvrOutTime;
	}
	public String getPayplatform() {
		return payplatform;
	}
	public void setPayplatform(String payplatform) {
		this.payplatform = payplatform;
	}
	public String getModiTime() {
		return modiTime;
	}
	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}
	public String getFilePrice() {
		return filePrice;
	}
	public void setFilePrice(String filePrice) {
		this.filePrice = filePrice;
	}
	public String getChildFilePrice() {
		return childFilePrice;
	}
	public void setChildFilePrice(String childFilePrice) {
		this.childFilePrice = childFilePrice;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getOrdFinalPirce() {
		return ordFinalPirce;
	}
	public void setOrdFinalPirce(String ordFinalPirce) {
		this.ordFinalPirce = ordFinalPirce;
	}
	public String getChildFinalPirce() {
		return childFinalPirce;
	}
	public void setChildFinalPirce(String childFinalPirce) {
		this.childFinalPirce = childFinalPirce;
	}
	public String getOrdTax() {
		return ordTax;
	}
	public void setOrdTax(String ordTax) {
		this.ordTax = ordTax;
	}
	public String getChildTax() {
		return childTax;
	}
	public void setChildTax(String childTax) {
		this.childTax = childTax;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
	
}
