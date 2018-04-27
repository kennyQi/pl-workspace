package plfx.jp.domain.model.pay;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.jp.command.pay.CreatePayRecordCommand;
import plfx.jp.domain.model.J;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

/**
 * 
 * @类功能说明：支付成功后的支付订单记录表
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月16日下午2:48:56
 * @版本：V1.0
 *
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX + "PAYRECORD")
public class PayRecord extends BaseModel{

	/**
	 * 经销商订单号
	 */
	@Column(name = "DEALERORDERID", length = 64)
	private String dealerOrderId;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATEDATETIME", columnDefinition = J.DATE_COLUM)
	private Date createDateTime;
	
	/**
	 * 来源经销商的ip地址 客户端调用时无需带上
	 */
	@Column(name = "FROMDEALERIP", length = 32)
	private String fromDealerIp;
	
	/**
	 * 订单类型
	 * 1：支付易行成功
	 * 2：支付易行失败
	 */
	@Column(name = "PAYTYPE")
	private Integer payType;

	public PayRecord() {
		super();
	}
	
	/**
	 * 创建订单
	 * @param orderid
	 */
	public PayRecord(CreatePayRecordCommand createPayRecordCommand) {
		setId(UUIDGenerator.getUUID());
		setDealerOrderId(createPayRecordCommand.getDealerOrderId());
		setCreateDateTime(new Date());
		setFromDealerIp(createPayRecordCommand.getFromDealerIp());
		setPayType(createPayRecordCommand.getPayType());
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}
	
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getFromDealerIp() {
		return fromDealerIp;
	}

	public void setFromDealerIp(String fromDealerIp) {
		this.fromDealerIp = fromDealerIp;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
}
