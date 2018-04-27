package hg.payment.domain.model.client;

import hg.common.component.BaseModel;
import hg.payment.domain.Pay;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.pojo.command.admin.CreatePaymentClientCommand;
import hg.payment.pojo.command.admin.ModifyPaymentClientCommand;
import hg.payment.pojo.command.admin.StartPaymentClientCommand;
import hg.payment.pojo.command.admin.StopPaymentClientCommand;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @类功能说明：接入该支付平台的客户端系统 主键使用手工输入的key
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月22日下午3:44:40
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Pay.TABLE_PREFIX + "CLIENT")
public class PaymentClient extends BaseModel {

	/**
	 * 名称
	 */
	@Column(name="NAME")
	private String name;

	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;

	/**
	 * 是否有效，已启用
	 */
	@Column(name="VALID")
	private Boolean valid;

	/**
	 * api密钥
	 */
	@Column(name="SECRET_KEY")
	private String secretKey;

	/**
	 * 1,2
	 */
	@Column(name="VALID_PAYCHANNELS")
	private String validPayChannels;
	
	/**
	 * 该客户端可用的支付渠道
	 */
	@Transient
	private Set<PayChannel> validPayChannelSet;
	
	/**
	 * 商城消息地址
	 */
	@Column(name="CLIENT_MESSAGE_URL")
	private String clientMessageURL;
	
	/**
	 * 商城重定向地址
	 */
	@Column(name="CLIENT_RETURN_URL")
	private String clientReturnURL;
	
	/**
	 * 商城域名
	 */
	@Column(name="CLIENT_SHOP_URL")
	private String clientShopURL;
	
	
	/**
	 * 添加支付客户端
	 * @param command
	 */
	public void createPaymentClient(CreatePaymentClientCommand command){
		setId(command.getId());
		setName(command.getName());
		setRemark(command.getRemark());
		setSecretKey(command.getSecretKey());
		setValid(command.getValid());
		setValidPayChannels(command.getValidPayChannels());
		setClientMessageURL(command.getClientMessageURL());
		setClientReturnURL(command.getClientReturnURL());
		setClientShopURL(command.getClientShopURL());
	}
	
	/**
	 * 修改支付客户端信息
	 * @param command
	 */
	public void modifyPaymentClient(ModifyPaymentClientCommand command){
		setId(command.getId());
		if(command.getName() != null){
			setName(command.getName());
		}
		if(command.getRemark() != null){
			setRemark(command.getRemark());
		}
		if(command.getSecretKey() != null){
			setSecretKey(command.getSecretKey());
		}
		if(command.getValid() != null){
			setValid(command.getValid());
		}
		if(StringUtils.isNotBlank(command.getValidPayChannels())){
			setValidPayChannels(command.getValidPayChannels());
		}
		if(StringUtils.isNotBlank(command.getClientMessageURL())){
			setClientMessageURL(command.getClientMessageURL());
		}
		if(StringUtils.isNotBlank(command.getClientReturnURL())){
			setClientReturnURL(command.getClientReturnURL());
		}
		if(StringUtils.isNotBlank(command.getClientShopURL())){
			setClientShopURL(command.getClientShopURL());
		}
		
	}
	
	/**
	 * 禁用支付客户端
	 * @param command
	 */
	public void stopPaymentClient(StopPaymentClientCommand command){
		setId(command.getId());
		setValid(false);
	}
	
	/**
	 * 启用支付客户端
	 * @param command
	 */
	public void startPaymentClient(StartPaymentClientCommand command){
		setId(command.getId());
		setValid(true);
	}
	
	/**
	 * 判断支付渠道是否是客户端可用的支付渠道
	 * @param type 支付渠道类型
	 * @return
	 */
	public Boolean isValidPayChannel(Integer type){
		if(type == null){
			return false;
		}
		String[] validPayChannelArr = validPayChannels.split(",");
		for(String payChannel:validPayChannelArr){
			if(payChannel.equals(type.toString())){
				return true;
			}
		}
		return false;
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

	public Set<PayChannel> getValidPayChannelSet() {
		return validPayChannelSet;
	}

	public void setValidPayChannelSet(Set<PayChannel> validPayChannelSet) {
		this.validPayChannelSet = validPayChannelSet;
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
