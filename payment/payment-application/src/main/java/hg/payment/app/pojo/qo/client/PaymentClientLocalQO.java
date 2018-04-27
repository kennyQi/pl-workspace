package hg.payment.app.pojo.qo.client;


import hg.common.component.BaseQo;

/**
 * 支付客户端QO
 * @author luoyun
 *
 */
@SuppressWarnings("serial")
public class PaymentClientLocalQO extends BaseQo{
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 名称
	 */
	private String name;
	private Boolean isNameLike = true;

	/**
	 * 是否有效，已启用
	 */
	private Boolean valid;
	
	/**
	 * 可用渠道
	 */
	private String validPayChannel;
	

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


	public Boolean getValid() {
		return valid;
	}


	public void setValid(Boolean valid) {
		this.valid = valid;
	}


	public Boolean getIsNameLike() {
		return isNameLike;
	}


	public void setIsNameLike(Boolean isNameLike) {
		this.isNameLike = isNameLike;
	}


	public String getValidPayChannel() {
		return validPayChannel;
	}


	public void setValidPayChannel(String validPayChannel) {
		this.validPayChannel = validPayChannel;
	}
	
	
	
	


	
	
	

}
