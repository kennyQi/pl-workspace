package hg.fx.domain.fixedprice;

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
 * 商户定价区间设置
 * */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "FIXED_PRICE_SET")
public class FixedPriceSet extends BaseStringIdModel{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 关联的商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROD_ID")
	private Product product;
	
	/**
	 * 关联的商户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	
	/**
	 * 商户用户名
	 */
	@Column(name = "LOGIN_NAME", length = 32)
	private String loginName;
	
	/**
	 * 定价区间ID
	 * */
	@Column(name="FIXEDPRICEINTERVAL_ID", length = 64)
	private String fixedPriceIntervalID;
	
	/**
	 * 修改前定价区间
	 * */
	@Column(name="CURRENT_INTERVAL", length = 64)
	private String currentInterval;
	
	/**
	 * 修改前折扣
	 * */
	@Column(name = "CURRENT_PERCENT", columnDefinition = O.MONEY_COLUM)
	private Double currentPercent;
	
	/**
	 * 修改后定价区间
	 * */
	@Column(name="MODIFIED_INTERVAL", length = 64)
	private String modifiedInterval;
	
	/**
	 * 修改后折扣
	 * */
	@Column(name = "MODIFIED_PERCENT", columnDefinition = O.MONEY_COLUM)
	private Double modifiedPercent;
	
	/**
	 * 审核状态
	 * 0-待审核     1-已通过 2-已拒绝
	 * */
	@Column(name = "CHECK_STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private int checkStatus;
	
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
	 *  生效月份
	 * */
	@Column(name = "IMPLEMENT_YYYYMM", columnDefinition = O.TYPE_NUM_COLUM)
	private int implementYYYYMM;
	
	/**
	 *  失效时间
	 * */
	@Column(name = "INVALID_DATE", columnDefinition = O.DATE_COLUM)
	private Date invalidDate;
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	@Type(type = "yes_no")
	@Column(name = "IS_IMPLEMENT")
	private Boolean isImplement;
	
	/**
	 * 申请人
	 * */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLY_USER_ID")
	private AuthUser applyUser;
	
	/**
	 * 审核人
	 * */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECK_USER_ID")
	private AuthUser checkUser;

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCurrentInterval() {
		return currentInterval;
	}

	public void setCurrentInterval(String currentInterval) {
		this.currentInterval = currentInterval;
	}

	public Double getCurrentPercent() {
		return currentPercent;
	}

	public void setCurrentPercent(Double currentPercent) {
		this.currentPercent = currentPercent;
	}

	public String getModifiedInterval() {
		return modifiedInterval;
	}

	public void setModifiedInterval(String modifiedInterval) {
		this.modifiedInterval = modifiedInterval;
	}

	public Double getModifiedPercent() {
		return modifiedPercent;
	}

	public void setModifiedPercent(Double modifiedPercent) {
		this.modifiedPercent = modifiedPercent;
	}

	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
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

	public int getImplementYYYYMM() {
		return implementYYYYMM;
	}

	public void setImplementYYYYMM(int implementYYYYMM) {
		this.implementYYYYMM = implementYYYYMM;
	}

	public String getFixedPriceIntervalID() {
		return fixedPriceIntervalID;
	}

	public void setFixedPriceIntervalID(String fixedPriceIntervalID) {
		this.fixedPriceIntervalID = fixedPriceIntervalID;
	}
	
	
}
