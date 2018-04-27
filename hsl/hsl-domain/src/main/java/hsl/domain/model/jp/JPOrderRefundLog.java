package hsl.domain.model.jp;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 订单退废记录
 * @author zhangka
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_JP_ORDER_REFUND_LOG")
public class JPOrderRefundLog extends BaseModel {
	/**
	 * 操作时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/**
	 * 旅客姓名,如: 中文(张三) 英文(Jesse/W)
	 */
	@Column(name = "NAME", length = 64)
	private String name;
	
	/** 航信接口证件类型 */
	@Column(name = "CARD_TYPE", length = 64)
	private String cardType;
	
	/**
	 * 乘客证件号
	 */
	@Column(name = "ID_CARD_NO", length = 64)
	private String idCardNo;
	
	/**
	 * 票号
	 */
	@Column(name = "TICKET_NO", length = 64)
	private String ticketNo;
	
	/**
	 * 退废状态
	 */
	@Column(name = "STATUS")
	private Integer status;
	
	/**
	 * 所属乘客
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private JPPassanger passanger;

	///////////////////

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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public JPPassanger getPassanger() {
		return passanger;
	}

	public void setPassanger(JPPassanger passanger) {
		this.passanger = passanger;
	}
}
