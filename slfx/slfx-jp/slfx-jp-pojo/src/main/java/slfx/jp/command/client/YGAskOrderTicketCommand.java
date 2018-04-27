package slfx.jp.command.client;

import java.io.Serializable;

/**
 * 
 * @类功能说明：易购出票command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:06:46
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGAskOrderTicketCommand implements Serializable {
	
	/**
	 * 订单号，必填
	 */
	private String			orderNo = "";
	
	/**
	 * 支付方式, ZH:账户或代扣（自动支付）; WZ:在线支付（手动支付）; BP:后结算（自动支付）, 必填
	 */
	private String			payType = "";
	
	/**
	 * 只能传一个支付方式,必填
	 */
	private String			supplierPayType = "";
	
	/**
	 * 供应商OFFICE授权, 0:手工授权; 1:自动授权, 必填
	 */
	private int				authoffice = 1;
	
	/**
	 * 出票完成通知地址, 必填
	 */
	private String			notifyUrl = "";
	
	/**
	 * 支付完成回调地址, 必填
	 */
	private String 			payReturnUrl = "";
	
	/**
	 * 订单备注, 非必填
	 */
	private String 			remark = null;
	
	/**
	 * 打票机序号, 非必填
	 */
	private String 			printerNo = null;
	
	/**
	 * 签注项, 非必填
	 */
	private String 			EI = null;
	
	/**
	 * 旅游编码, 非必填
	 */
	private String 			TC = null;
	
	/**
	 * 支持大客户编码, 格式: FP: + 大客户编码, 例如: FP: *BJS123
	 */
	private String 			FP = null;
	
	/**
	 * 需要在PAT前执行的指令, 多条指令以 | 分隔, 非必填
	 */
	private String 			otherCommand = null;
	
	/**
	 * 航段摘要, 用于验证 PNR 航班号+出发机场+到达机场+航班日期+舱位. 例如：CA8888PEKSHA2010-10-12Y, 非必填
	 */
	private String 			flightVerifyStr = null;
	
	/**
	 * 票款合计-1 ：以PAT 中最低价格出票0 ：以SFC:01 第一项价格1 ：以PAT 中最高价格出票>10 以指定价格出票，如果PAT 报价中没有指定的价格，则报错, 非必填
	 */
	private String 			price = null;
	
	/**
	 * 出票OFFICE（用于ＢＳＰ票用哪个配置出票）, 非必填
	 */
	private String 			tktOffice = null;
	
	/**
	 * 第三方支付单号
	 */
	private String payOrderId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSupplierPayType() {
		return supplierPayType;
	}

	public void setSupplierPayType(String supplierPayType) {
		this.supplierPayType = supplierPayType;
	}

	public int getAuthoffice() {
		return authoffice;
	}

	public void setAuthoffice(int authoffice) {
		this.authoffice = authoffice;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPayReturnUrl() {
		return payReturnUrl;
	}

	public void setPayReturnUrl(String payReturnUrl) {
		this.payReturnUrl = payReturnUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPrinterNo() {
		return printerNo;
	}

	public void setPrinterNo(String printerNo) {
		this.printerNo = printerNo;
	}

	public String getEI() {
		return EI;
	}

	public void setEI(String eI) {
		EI = eI;
	}

	public String getTC() {
		return TC;
	}

	public void setTC(String tC) {
		TC = tC;
	}

	public String getFP() {
		return FP;
	}

	public void setFP(String fP) {
		FP = fP;
	}

	public String getOtherCommand() {
		return otherCommand;
	}

	public void setOtherCommand(String otherCommand) {
		this.otherCommand = otherCommand;
	}

	public String getFlightVerifyStr() {
		return flightVerifyStr;
	}

	public void setFlightVerifyStr(String flightVerifyStr) {
		this.flightVerifyStr = flightVerifyStr;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTktOffice() {
		return tktOffice;
	}

	public void setTktOffice(String tktOffice) {
		this.tktOffice = tktOffice;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	
}
