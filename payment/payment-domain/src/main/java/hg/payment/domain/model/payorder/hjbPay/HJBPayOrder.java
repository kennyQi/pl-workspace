package hg.payment.domain.model.payorder.hjbPay;

import hg.common.util.DateUtil;
import hg.log.util.HgLogger;
import hg.payment.domain.Pay;
import hg.payment.domain.common.util.hjb.DesUtil;
import hg.payment.domain.common.util.hjb.HJBUtils;
import hg.payment.domain.model.channel.hjbPay.HJBPayChannel;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.pojo.command.spi.payorder.hjb.CreateHJBPayOrderCommand;

import java.io.StringWriter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


/**
 * 
 *@类功能说明：汇金宝渠道订单
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月17日上午9:57:04
 *
 */
@Entity
@Table(name = Pay.TABLE_PREFIX + "HJBPAY_ORDER")
@PrimaryKeyJoinColumn(name="HJBPAY_ORDER_ID")
public class HJBPayOrder extends PayOrder{

	private static final long serialVersionUID = 1L;

	
	/**
	 * 交易名称
	 */
	@Column(name = "TRADE_NAME")
	private String tradeName;

	/**
	 * 商品名称
	 */
	@Column(name = "SUBJECT")
	private String subject;
	
	/**
	 * 商品描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * 商城在汇金宝唯一标识
	 */
	@Column(name = "CHANNEL_ID")
	private String channelId;
	
	/**
	 * 商城在汇金宝注册的商户编号
	 */
	@Column(name = "MER_CODE")
	private String merCode;
	
	/**
	 * 商城服务器IP
	 */
	@Column(name = "CALLER_IP")
	private String callerIp;
	
	/**
	 * 收款方开户行银行类型
	 */
	@Column(name = "PAYEE_BANK_TYPE")
	private String payeeBankType;
	
	/**
	 * 收款方银行开户名
	 */
	@Column(name = "PAYEE_BANK_ACCOUNT_NAME")
	private String payeeBankAccountName;
	
	/**
	 * 付款方用户类型 
	 * 2:企业
	 * 1:个人
	 */
	@Column(name="CUSTOMER_TYPE")
	private String customerType;
	
	/**
     * 付款方银行类型
     */
	@Column(name="PAYER_BANK_TYPE")
	private String payerBankType;
	

	/**支付成功*/
	public static final String paySuccess = "1";
	/**支付失败*/
	public static final String payFail = "2";
	
	
	public void createhjbPayOrder(CreateHJBPayOrderCommand command){
		tradeName = command.getTradeName();
		subject = command.getSubject();
		description = command.getDescription();
		channelId = command.getChannelId();
		merCode = command.getMerCode();
		callerIp = command.getCallerIp();
		payeeBankAccountName = command.getPayeeBankAccountName();
		payeeBankType= command.getPayeeBankType();
		customerType = command.getCustomerType();
		payerBankType = command.getPayerBankType();
	}
	
	
	/**
	 * 组装汇金宝支付请求报文
	 * @return
	 */
	@Override
	public String buildRequestParam(){
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("HJBEB");
		Element message = root.addElement("Message");
		message.addAttribute("id",HJBUtils.buildMessageId());
		Element cashierPay = message.addElement("CashierPay");
		
		cashierPay.addAttribute("id", "CashierPay");
		HJBPayChannel payChannel = (HJBPayChannel)getPayChannel();
		cashierPay.addElement("version").addText(payChannel.getVersion());
		cashierPay.addElement("signatureAlgorithm").addText(payChannel.getSignatureAlgorithm());
		cashierPay.addElement("channelId").addText(getChannelId());
		cashierPay.addElement("callerIp").addText(getCallerIp());
		cashierPay.addElement("returnURL").addText(getPaymentClient().getClientShopURL() + "/hjb/return");
		cashierPay.addElement("notifyAddr").addText(payChannel.getNotifyAddr());
		cashierPay.addElement("orderNo").addText(getId());
		cashierPay.addElement("merCode").addText(getMerCode());
		cashierPay.addElement("merBankType").addText(getPayeeBankType());
		cashierPay.addElement("merBankAccountName").addText(getPayeeBankAccountName());
		cashierPay.addElement("merBankAccountNo").addText(getPayeeInfo().getPayeeAccount());
		cashierPay.addElement("payeeCustName").addText(getPayeeInfo().getPayeeName());	
		cashierPay.addElement("customerType").addText(getCustomerType());
		cashierPay.addElement("bankType").addText(getPayerBankType());
		cashierPay.addElement("payerCustName").addText(getPayerInfo().getName() == null ? "":getPayerInfo().getName());
		cashierPay.addElement("currency").addText(payChannel.getCurrency());
		cashierPay.addElement("tradeName").addText(getTradeName());
		cashierPay.addElement("subject").addText(getSubject());
		cashierPay.addElement("description").addText(getDescription());
		Date tradeTime = getCreateDate();
		String tradeTimeStr = DateUtil.formatDateTime(tradeTime,"yyyyMMddHHmmsss");
		cashierPay.addElement("tradeTime").addText(tradeTimeStr);
		
		cashierPay.addElement("amount").addText((Math.round(getAmount() * 100)) + "");
		
		
	    StringWriter writer = new StringWriter();
	    XMLWriter output = new XMLWriter(writer);
	    try{
	    	output.write(document);
	    	writer.close();
	    	output.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    String requestXml = writer.toString();
	    HgLogger.getInstance().debug("luoyun", "【汇金宝】汇金宝支付请求参数：" + requestXml);
	    String signXml = HJBUtils.singXml(requestXml, "CashierPay");
		String submitHtml = HJBUtils.buildRequest(payChannel.getHjbUrl(), signXml);
		return submitHtml;
	}
	
	@Override
	public String notifyClientParam() {
		return null;
	}
	
	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getChannelId() {
		return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public String getPayerBankType() {
		return payerBankType;
	}


	public void setPayerBankType(String payerBankType) {
		this.payerBankType = payerBankType;
	}


	public String getMerCode() {
		return merCode;
	}


	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getCallerIp() {
		return callerIp;
	}


	public void setCallerIp(String callerIp) {
		this.callerIp = callerIp;
	}


	public String getCustomerType() {
		return customerType;
	}


	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	public String getPayeeBankType() {
		return payeeBankType;
	}


	public void setPayeeBankType(String payeeBankType) {
		this.payeeBankType = payeeBankType;
	}


	public String getPayeeBankAccountName() {
		return payeeBankAccountName;
	}


	public void setPayeeBankAccountName(String payeeBankAccountName) {
		this.payeeBankAccountName = payeeBankAccountName;
	}


	

	

	
	
	
	

}
