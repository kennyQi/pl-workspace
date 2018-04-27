package jxc.domain.model.warehouse;

import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateWarehouseTypeCommand;
import hg.pojo.command.ModifyWarehouseTypeCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;

import org.hibernate.annotations.Type;

/**
 * 仓库类型
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_WAREHOUSE + "WAREHOUSE_TYPE")
public class WarehouseType extends JxcBaseModel {

	/**
	 * 类型名称
	 */
	@Column(name = "WAREHOUSE_TYPE_NAME", length = 30)
	private String name;

	/**
	 * 物流接口推送地址
	 */
	@Column(name = "WAREHOUSE_TYPE_POST_URL", length = 100)
	private String portUrl;

	/**
	 * 加密串
	 */
	@Column(name = "WAREHOUSE_TYPE_KEY", length = 64)
	private String key;

	/**
	 * 操作员
	 */
	@Column(name = "WAREHOUSE_TYPE_OPERATOR", length = 20)
	private String operator;

	/**
	 * 保价比例
	 */
	@Column(name = "WAREHOUSE_TYPE_INSURED_RATE", columnDefinition = M.DOUBLE_COLUM)
	private Double insuredRate;

	/**
	 * 接口是否开启
	 */
	@Type(type = "yes_no")
	@Column(name = "WAREHOUSE_TYPE_USING")
	private Boolean using;
	/**
	 * 接口是否开启
	 */
	@Column(name = "CHANNEL_ID", length = 255)
	private String channelId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPortUrl() {
		return portUrl;
	}

	public void setPortUrl(String portUrl) {
		this.portUrl = portUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Double getInsuredRate() {
		return insuredRate;
	}

	public void setInsuredRate(Double insuredRate) {
		this.insuredRate = insuredRate;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public void createWarehouseType(CreateWarehouseTypeCommand command) {
		setId(UUIDGenerator.getUUID());

		setInsuredRate(command.getInsuredRate());
		setKey(command.getKey());
		setName(command.getName());
		setOperator(command.getOperator());
		setPortUrl(command.getPortUrl());
		setUsing(command.getUsing());
		
		setChannelId(command.getChannelId());
		
		setStatusRemoved(false);
	}

	public void modifyWarehouseType(ModifyWarehouseTypeCommand command) {
		setInsuredRate(command.getInsuredRate());
		setKey(command.getKey());
//		setName(command.getName());
		setOperator(command.getOperator());
		setPortUrl(command.getPortUrl());
		setUsing(command.getUsing());
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
