package hg.fx.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @类功能说明：可欠费里程变更记录
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午8:03:52
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "ARREARS_RECORD")
public class ArrearsRecord extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	/** -1--已拒绝 */
	public static final int CHECK_STATUS_REFUSE = -1;
	/** 1--待审核   */
	public static final int CHECK_STATUS_WAITTING = 1;
	/** 0--已通过 */
	public static final int CHECK_STATUS_PASS = 0;
	
	/**
	 * 关联商户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	
	/**
	 * 原可欠费里程（变更前）
	 */
	@Column(name = "PRE_ARREARS", columnDefinition = O.NUM_COLUM)
	private Integer preArrears;
	
	
	/**
	 * 申请可欠费里程
	 */
	@Column(name = "APPLY_ARREARS", columnDefinition = O.NUM_COLUM)
	private Integer applyArrears;
	
	/**
	 * 申请理由
	 */
	@Column(name = "REASON", length = 256)
	private String reason;
	
	/**
	 * 申请时间
	 */
	@Column(name = "APPLY_DATE", columnDefinition = O.DATE_COLUM)
	private Date applyDate;
	
	/**
	 * 审核状态  
	 * -1--已拒绝  
	 * 1--待审核  
	 * 0--已通过
	 */
	@Column(name = "CHECK_STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer checkStatus;
	
	/**
	 * 申请人  添加申请的操作人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATOR_ID")
	private AuthUser operator;

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Integer getPreArrears() {
		return preArrears;
	}

	public void setPreArrears(Integer preArrears) {
		this.preArrears = preArrears;
	}

	public Integer getApplyArrears() {
		return applyArrears;
	}

	public void setApplyArrears(Integer applyArrears) {
		this.applyArrears = applyArrears;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckStatusName(){
		switch (checkStatus) {
		case CHECK_STATUS_REFUSE:
			return "已拒绝";
		case CHECK_STATUS_PASS:
			return "已通过";
		case CHECK_STATUS_WAITTING:
			return "待审核";
		default:
			break;
		}
		return "";
	}
	public AuthUser getOperator() {
		return operator;
	}

	public void setOperator(AuthUser operator) {
		this.operator = operator;
	}
	
}
