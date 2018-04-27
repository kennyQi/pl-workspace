package hg.fx.domain.rebate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;
import hg.fx.domain.Distributor;
import hg.fx.domain.Product;

/**
 * @author yangkang
 * @date 2016-07-22
 * 商户返利区间设置
 * */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "REBATE_SET")
public class RebateSet extends BaseStringIdModel{
	
	private static final long serialVersionUID = 1L;
	
	/** 2--已拒绝 */
	public static final int CHECK_STATUS_REFUSE = 2;
	/** 0-待审核   */
	public static final int CHECK_STATUS_WAITTING = 0;
	/** 1-已通过 */
	public static final int CHECK_STATUS_PASS = 1;
	
	/**
	 * 返利关联的商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROD_ID")
	private Product product;
	/**
	 * 商品名称
	 */
	@Column(name = "PRODUCT_NAME", length = 32)
	private String productName;
	
	/**
	 * 返利关联的商户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	@Column(name = "DISTRIBUTOR_NAME", length = 32)
	private String distributorName;
	
	/**
	 * 商户用户名
	 */
	@Column(name = "LOGIN_NAME", length = 32)
	private String loginName;
	
	/**
	 * 区间建值串
	 * */
	@Column(name = "INTERVAL_STR", length = 512)
	private String intervalStr;
	
	/**
	 * 审核状态
	 * 0-待审核     1-已通过 2-已拒绝
	 * */
	@Column(name = "CHECK_STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer checkStatus;
	
	/**
	 * 申请审核时间
	 * */
	@Column(name = "APPLY_DATE", columnDefinition = O.DATE_COLUM)
	private Date applyDate;
	
	/**
	 *  审核时间
	 * */
	@Column(name = "CHECK_DATE", columnDefinition = O.DATE_COLUM)
	private Date checkDate;
	
	/**
	 *  生效时间
	 * */
	@Column(name = "IMPLEMENT_DATE", columnDefinition = O.DATE_COLUM)
	private Date implementDate;
	
	/**
	 *  失效时间
	 * */
	@Column(name = "INVALID_DATE", columnDefinition = O.DATE_COLUM)
	private Date invalidDate;
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	@Type(type = "yes_no" )
	@Column(name = "IS_IMPLEMENT")
	private Boolean isImplement;
	     
	/**
	 * 申请人
	 * */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLY_USER_ID")
	private AuthUser applyUser;
	
	@Column(name = "APPLY_USER_NAME", length = 32)
	private String applyUserName;
	
	/**
	 * 审核人
	 * */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECK_USER_ID")
	private AuthUser checkUser;
	
	@Column(name = "CHKECK_USER_NAME", length = 32)
	private String checkUserName;
	/**
	 * 当前返现设置id
	 */
	@Column(name = "RUNNING_SET_ID", length = 32)
	private String runningSetId;
	
	/**
	 * 是否存在待审核的数据
	 * Y-有  N-无
	 * */
	@Type(type = "yes_no" )
	@Column(name = "IS_CHKECK")
	private Boolean isCheck;
	
	/**
	 * 是否默认创建的规则
	 * Y-是  N-否
	 * */
	@Type(type = "yes_no" )
	@Column(name = "IS_DEFAULT")
	private Boolean isDefault;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getIntervalStr() {
		return intervalStr;
	}

	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getImplementDate() {
		return implementDate;
	}

	public void setImplementDate(Date implementDate) {
		this.implementDate = implementDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public Boolean getIsImplement() {
		return isImplement;
	}

	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}

	public AuthUser getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(AuthUser applyUser) {
		this.applyUser = applyUser;
	}

	public AuthUser getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(AuthUser checkUser) {
		this.checkUser = checkUser;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getRunningSetId() {
		return runningSetId;
	}

	public void setRunningSetId(String runningSetId) {
		this.runningSetId = runningSetId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
	
}
