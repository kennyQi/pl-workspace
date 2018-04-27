package hg.payment.pojo.command.spi.payorder.alipay;

import hg.common.component.BaseCommand;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;

/**
 * 
 * @类功能说明：商城请求创建支付订单参数
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：lixx
 * @创建时间：2014年9月2日下午4:57:31
 * 
 */
public class CreateAlipayPayOrderCommand extends BaseCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 支付请求基本参数
	 */
	private PayOrderRequestDTO payOrderCreateDTO;

	/** =============支付请求 支付宝参数================= **/

	/**
	 * 卖家支付宝账户（收款账号，必填）
	 */
	private String sellerEmail; //弃用
	
	/**
	 * 合作商户唯一ID(必填)
	 */
	private String partner;

	/**
	 * 支付宝商户唯一密钥(必填)
	 */
	private String keys;

	/**
	 * 订单名称（必填）
	 */
	private String subject;

	/**
	 * 订单描述
	 */
	private String body;

	/**
	 * 商品展示地址
	 */
	private String showUrl;

	public PayOrderRequestDTO getPayOrderCreateDTO() {
		return payOrderCreateDTO;
	}

	public void setPayOrderCreateDTO(PayOrderRequestDTO payOrderCreateDTO) {
		this.payOrderCreateDTO = payOrderCreateDTO;
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

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	
	
	

}
