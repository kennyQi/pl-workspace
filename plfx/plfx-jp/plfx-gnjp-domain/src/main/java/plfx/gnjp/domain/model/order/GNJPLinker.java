package plfx.gnjp.domain.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @类功能说明：联系人信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:28:24
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Embeddable
public class GNJPLinker implements Serializable{
	
	/**
	 * 手机
	 */
	@Column(name = "MOBILE_PHONE", length = 64)
	private String mobilePhone;
	
	/**
	 * 联系人姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	/**
	 * 是否电子机票  Y或N 必填 设为Y
	@Column(name = "IS_ETICKET", length = 16)
	private String isETiket;
	 */
	
	
	/**
	 * 地址  不设置
	@Column(name = "ADDRESS", length = 256)
	private String address;
	 */
	
	/**
	 * 联系人     必填
	@Column(name = "LINKER_NAME", length = 64)
	private String linkerName;
	 */
	
	/**
	 * 邮编    不设置
	@Column(name = "ZIP", length = 16)
	private String zip;
	 */
	
	/**
	 * 电话    不设置
	@Column(name = "TELPHONE", length = 64)
	private String telphone="";
	 */

	/**
	 * 送票时间   不设置
	@Column(name = "SENDT_IME", length = 64)
	private String sendTime;
	 */
	
	/**
	 * 联系人邮箱  不设置
	@Column(name = "LINKER_EMAIL", length = 256)
	private String  linkerEmail;
	 */
	
	/**
	 * 是否需要发票 Y/N   不设置
	@Column(name = "NEED_INVOICES", length = 8)
	private String needInvoices;
	 */
	
	/**
	 * 发票发送方式 A邮寄 B配送    不设置
	@Column(name = "INVOICES_SEND_TYPE", length = 8)
	private String invoicesSendType;
	 */
	
	/**
	 * 订单配送方式（By：不配送 ZQ:自取 SP:送票 YJ:邮寄 BZ:不制单）
	 * 不设置
	@Column(name = "SEND_TKTS_TYPE_CODE", length = 8)
	private String sendTktsTypeCode;
	 */
	
	/**
	 * 是否需要行程单   Y/N  必填
	@Column(name = "IS_PRINTSERIAL", length = 8)
	private String isPrintSerial;
	 */

}