package hg.payment.domain.model.payorder.alipay;

import hg.log.util.HgLogger;
import hg.payment.domain.Pay;
import hg.payment.domain.common.util.alipay.util.AlipaySubmit;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONArray;


/**
 * 
 * @类功能说明：支付宝渠道支付单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年9月3日下午3:00:52
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Pay.TABLE_PREFIX + "ALIPAY_ORDER")
@PrimaryKeyJoinColumn(name="ALIPAY_ORDER_ID")
public class AlipayPayOrder extends PayOrder {
	
	//这里记录保存支付宝所有的请求参数
	/**
	 * 卖家支付宝账户（收款账号，必填）
	 */
//	@Column(name = "SELLER_EMAIL")
//	private String sellerEmail;
	
	/**
	 * 合作商户唯一ID(必填)
	 */
	@Column(name = "PARTNER")
	private String partner;

	/**
	 * 支付宝商户唯一密钥(必填)
	 */
	@Column(name = "ALIPAY_KEYS")
	private String keys;

	/**
	 * 订单名称（必填）
	 */
	@Column(name = "SUBJECT")
	private String subject;

	/**
	 * 订单描述
	 */
	@Column(name = "BODY")
	private String body;

	/**
	 * 商品展示地址(必填)
	 */
	@Column(name = "SHOW_URL")
	private String showUrl;
	/**
	 * 支付类型（默认值）：商品购买
	 */
	@Column(name = "PAYMENT_TYPE")
	private String paymentType = "1";
	
//	/**
//	 * 商户订单编号(可以不用)
//	 */
//	@Column(name = "OUT_TRADE_NO")
//	private String outTradeNo;
	
	/**
	 * 即时支付服务名称
	 */
	@Column(name = "SERVICE_NAME")
	private String serviceName;
	
	
	/**
	 * 字符编码（默认：utf-8）
	 */
	@Column(name = "INPUT_CHARSET")
	private String inputCharset ;
	
	/**
	 * 签名方式
	 */
	@Column(name = "SIGN_TYPE")
	private  String signType = "MD5";
	
	/**
	 * 日志文件夹路径
	 */
	@Column(name = "LOG_PATH")
	private  String logPath ;
	
	/**
	 * 支付宝请求地址(发起)
	 */
	@Column(name = "ALIPAY_URL")
	private String alipayUrl;
	
	/**
	 * 支付宝回调消息验证地址
	 */
	@Column(name = "ALIPAY_VERIFY_URL")
	private String alipayVerifyUrl;
	
	
	@Override
	public String notifyClientParam() {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(getPayOrderProcessor().isPaySuccess()){
			returnMap.put("paySuccess",true);
		}else{
			returnMap.put("paySuccess",false);
		}
		returnMap.put("buyer_email", getPayerInfo().getPayerAccount());
		returnMap.put("trade_no", getThirdPartyTradeNo());
		String json = JSONArray.toJSONString(returnMap);
		return json;
	}

	/**
	 * 设置支付订单支付宝参数
	 * @param command
	 */
	public void createAlipayPayOrder(CreateAlipayPayOrderCommand command){
		AlipayPayChannel payChannel = (AlipayPayChannel) this.getPayChannel();
		setPartner(command.getPartner());
		setKeys(command.getKeys());
		setSubject(command.getSubject());
		setBody(command.getBody());
		setShowUrl(command.getShowUrl());
		setPayChannelType(PayChannel.PAY_TYPE_ALIPAY);
		setLogPath(payChannel.getLogPath());
		setInputCharset(payChannel.getInputCharset());
		setServiceName(payChannel.getServiceName());
		setAlipayUrl(payChannel.getAlipayUrl());
		setAlipayVerifyUrl(payChannel.getAlipayVerifyUrl());
	}
	
	/**
	 * 组装支付宝请求地址
	 */
	@Override
	public String buildRequestParam(){
		
		AlipayPayChannel payChannel = (AlipayPayChannel) this.getPayChannel();
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", payChannel.getServiceName());
		sParaTemp.put("partner", getPartner());
        sParaTemp.put("_input_charset", payChannel.getInputCharset());
		sParaTemp.put("payment_type", payChannel.getPaymentType());
		sParaTemp.put("notify_url", payChannel.getNotifyUrl());
//		sParaTemp.put("return_url", payChannel.getReturnUrl());//商城的URL+支付渠道的同步的URL，例如：http://www.ply365.com/payment/alipay/return
																					   // IP http://支付平台ip+端口+alipay/return		
		sParaTemp.put("return_url", getPaymentClient().getClientShopURL() + "/alipay/return");
		sParaTemp.put("seller_email", getPayeeInfo().getPayeeAccount());
		//支付宝参数中的订单编号是支付平台订单编号，不是商城订单编号。
		sParaTemp.put("out_trade_no", getId());
		sParaTemp.put("subject", getSubject());
		sParaTemp.put("total_fee", getAmount().toString());
		sParaTemp.put("body", getBody());
		sParaTemp.put("show_url", getShowUrl());
		sParaTemp.put("anti_phishing_key", "");
		sParaTemp.put("exter_invoke_ip", "");
		
		AlipaySubmit alipaySubmit = new AlipaySubmit();
		alipaySubmit.setAlipayPayOrder(this);
		alipaySubmit.setAlipay(payChannel);
		String submitHtml = alipaySubmit.buildRequest(sParaTemp,"get","确认");
		HgLogger.getInstance().debug("luoyun", "【支付宝】支付宝支付请求参数：" + submitHtml);
		return submitHtml;
		
	}
	


	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getAlipayUrl() {
		return alipayUrl;
	}

	public void setAlipayUrl(String alipayUrl) {
		this.alipayUrl = alipayUrl;
	}

	public String getAlipayVerifyUrl() {
		return alipayVerifyUrl;
	}

	public void setAlipayVerifyUrl(String alipayVerifyUrl) {
		this.alipayVerifyUrl = alipayVerifyUrl;
	}


	
	
}
