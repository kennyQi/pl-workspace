package plfx.yeexing.pojo.dto.order;

import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：机票下单人信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月30日下午2:51:38
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderUserInfoDTO extends BaseJpDTO{
	
	/**
	 * 联系人只留手机号
	 */
	private String mobile;
	
	/**
	 * 用户登录名
	 */
	private String loginName;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	/**
	 * 是否电子机票  Y或N 必填 设为Y
	private String isETiket;
	 */
	
	/**
	 * 支付方式   不设置
	private String payType;
	 */
	
	/**
	 * 地址  不设置
	private String address;
	 */
	
	/**
	 * 联系人     必填
	private String linkerName;
	 */
	
	/**
	 * 邮编    不设置
	private String zip;
	 */
	
	/**
	 * 电话    不设置
	private String telphone="";
	 */
	
	/**
	 * 手机
	private String mobilePhone;
	 */

	/**
	 * 送票时间   不设置
	private String sendTime;
	 */
	
	/**
	 * 联系人邮箱  不设置
	private String  linkerEmail;
	 */
	
	/**
	 * 是否需要发票 Y/N   不设置
	private String needInvoices;
	 */
	
	/**
	 * 发票发送方式 A邮寄 B配送    不设置
	private String invoicesSendType;
	 */
	
	/**
	 * 备注  不设置
	private String remark;
	 */
	
	/**
	 * 订单配送方式（By：不配送 ZQ:自取 SP:送票 YJ:邮寄 BZ:不制单）
	 * 不设置
	private String sendTktsTypeCode;
	 */
	
	/**
	 * 是否需要行程单   Y/N  必填
	private String isPrintSerial;
	 */

}