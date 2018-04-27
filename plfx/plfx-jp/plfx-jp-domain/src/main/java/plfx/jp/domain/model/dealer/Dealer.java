package plfx.jp.domain.model.dealer;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import plfx.jp.command.DealerCommand;
import plfx.jp.domain.model.J;
import plfx.jp.pojo.system.DealerConstants;

/**
 * 
 * @类功能说明：经销商的model类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午1:41:45
 * @版本：V1.0
 * 
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX + "DEALER")
public class Dealer extends BaseModel {

	/**
	 * 经销商名称
	 */
	@Column(name = "DEALER_NAME", length = 64)
	private String name;

	/**
	 * 经销商代码
	 */
	@Column(name = "DEALER_CODE", length = 64)
	private String code;

	/**
	 * 密钥
	 */
	@Column(name = "SECRET_KEY", length = 128)
	private String secretKey;

	/**
	 * 启用状态
	 */
	@Column(name = "SUPPLIER_STATUS", columnDefinition = J.CHAR_COLUM)
	private String status;

	/**
	 * 创建日期
	 */
	@Column(name = "SUPPLIER_CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;

	/**
	 * 经销商通知地址
	 */
	@Column(name = "DEALER_NOTIFYURL", length = 512)
	private String notifyUrl;
	
	/**
	 * 经销商通知字段名称
	 * 通知地址用来接收参数的名称
	 */
	@Column(name = "DEALER_NOTIFYVALUE", length = 12)
	private String notifyValue;
	
	/**
	 * 经销商类型，现在做生成订单时返回价格字段值的处理与是否显示
	 */
	@Column(name = "DEALER_TYPE", length = 2)
	private Integer type;
	

	public Dealer() {
		super();
	}

	public Dealer(DealerCommand command) {
		setId(UUIDGenerator.getUUID());
//		setId(command.getCode());
		setName(command.getName());
		setCode(command.getCode());
		setCreateDate(new Date());
		setStatus(DealerConstants.PRE_USE);
		setNotifyUrl(command.getNotifyUrl());
		setNotifyValue(command.getNotifyValue());
		setSecretKey(command.getSecretKey());
	}

	public void update(DealerCommand command) {
		if (StringUtils.isNotBlank(command.getName())) {
			setName(command.getName());
		}
		if (StringUtils.isNotBlank(command.getCode())) {
			setCode(command.getCode());
		}
		if (StringUtils.isNotBlank(command.getNotifyUrl())) {
			setNotifyUrl(command.getNotifyUrl());
		}
		if (StringUtils.isNotBlank(command.getSecretKey())) {
			setSecretKey(command.getSecretKey());
		}
		if (StringUtils.isNotBlank(command.getNotifyValue())) {
			setNotifyValue(command.getNotifyValue());
		}
	}

	public void updateStatus(DealerCommand command) {
		setStatus(command.getStatus());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getNotifyValue() {
		return notifyValue;
	}

	public void setNotifyValue(String notifyValue) {
		this.notifyValue = notifyValue;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
