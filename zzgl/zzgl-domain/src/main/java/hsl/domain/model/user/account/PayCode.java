package hsl.domain.model.user.account;
import hg.common.component.BaseModel;
import hsl.domain.model.M;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * @类功能说明：充值码
 * @类修改者：
 * @修改日期：2015年7月18日下午12:09:18
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月18日下午12:09:18
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_ACCOUNT+"PAYCODE")
public class PayCode extends BaseModel{
	private static final long serialVersionUID = 1L;
	/**
	 * 所属的方法纪录
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "GRANTCODERECORD_ID")
	private GrantCodeRecord grantCodeRecord;
	/**
	 * 充值码
	 */
	@Column(name="CODE",length=64)
	private String code;
	/**
	 * 价格
	 */
	@Column(name="PRICE",columnDefinition=M.DOUBLE_COLUM)
	private Double price;
	/**
	 * 来源
	 */
	@Column(name="SOURCE",length=64)
	private String source;
	/**
	 * 创建日期
	 */
	@Column(name="CREATEDATE",columnDefinition=M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 充值日期
	 */
	@Column(name="RECHARGEDATE",columnDefinition=M.DATE_COLUM)
	private Date rechargeDate;
	/**
	 * 持用用户快照
	 */
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "USER_SNAPSHOT_ID")
	private HoldUserSnapshot holdUserSnapshot;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public GrantCodeRecord getGrantCodeRecord() {
		return grantCodeRecord;
	}
	public void setGrantCodeRecord(GrantCodeRecord grantCodeRecord) {
		this.grantCodeRecord = grantCodeRecord;
	}
	public HoldUserSnapshot getHoldUserSnapshot() {
		return holdUserSnapshot;
	}
	public void setHoldUserSnapshot(HoldUserSnapshot holdUserSnapshot) {
		this.holdUserSnapshot = holdUserSnapshot;
	}
	public Date getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	
}
