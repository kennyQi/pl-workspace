package hg.payment.pojo.command.admin;

import hg.common.component.BaseCommand;

/**
 * 修改支付客户端信息
 * @author luoyun
 *
 */
@SuppressWarnings("serial")
public class ModifyPaymentClientCommand extends BaseCommand{
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否有效，已启用
	 */
	private Boolean valid;

	/**
	 * api密钥
	 */
	private String secretKey;

	/**
	 * 该客户端可用的支付渠道
	 */
//	private Set<PayChannelDTO> validPayChannels;
	private String validPayChannels;
	
	/**
	 * 商城消息地址
	 */
	private String clientMessageURL;
	
	/**
	 * 商城重定向地址
	 */
	private String clientReturnURL;
	
	/**
	 * 商城域名
	 */
	private String clientShopURL;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getValidPayChannels() {
		return validPayChannels;
	}

	public void setValidPayChannels(String validPayChannels) {
		this.validPayChannels = validPayChannels;
	}

	public String getClientMessageURL() {
		return clientMessageURL;
	}

	public void setClientMessageURL(String clientMessageURL) {
		this.clientMessageURL = clientMessageURL;
	}

	public String getClientReturnURL() {
		return clientReturnURL;
	}

	public void setClientReturnURL(String clientReturnURL) {
		this.clientReturnURL = clientReturnURL;
	}

	public String getClientShopURL() {
		return clientShopURL;
	}

	public void setClientShopURL(String clientShopURL) {
		this.clientShopURL = clientShopURL;
	}

	
	
	

}
