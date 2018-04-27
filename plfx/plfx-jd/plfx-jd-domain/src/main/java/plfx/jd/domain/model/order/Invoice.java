package plfx.jd.domain.model.order;

import hg.common.component.BaseModel;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jd.domain.model.M;

/**
 * 
 * @类功能说明：发票
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月3日下午2:09:20
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "INVOICE")
public class Invoice extends BaseModel {
	
	/**
	 * 名称
	 */
	@Column(name = "ITEM_NAME", length = 100)
	private String itemName;
	/**
	 * 金额
	 */
	@Column(name = "AMOUNT",columnDefinition = M.MONEY_COLUM )
	private BigDecimal amount;
	/**
	 * 发票状态 false--未处理、true--已开票
	 */
	@Column(name = "STATUS",columnDefinition = M.CHAR_COLUM)
	private Boolean status;
	/**
	 * 邮寄状态 false--未邮寄、true--已邮寄
	 */
	@Column(name = "DELIVERY_STATUS",columnDefinition = M.CHAR_COLUM)
	private Boolean deliveryStatus;
	/**
	 * 收件人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECIPIENT_ID")
	private Recipient recipient;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(Boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Recipient getRecipient() {
		return recipient;
	}
	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}
	
	
}
