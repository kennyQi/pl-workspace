package hg.fx.domain.fixedprice;

import java.util.Date;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;
import hg.fx.domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author yangkang
 * @date 2016-07-22 定价模式区间表
 * */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "FIXED_PRICE_INTERVAL")
public class FixedPriceInterval extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 区间关联的商品
	 * */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROD_ID")
	private Product product;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date createDate;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE_YYYYMM", columnDefinition = O.TYPE_NUM_COLUM)
	private int createDateYYYYMM;

	/**
	 * 失效时间
	 */
	@Column(name = "INVALID_DATE", columnDefinition = O.DATE_COLUM)
	private Date invalidDate;

	/**
	 * 区间建值串
	 * */
	@Column(name = "INTERVAL_STR", length = 512)
	private String intervalStr;

	/**
	 * 区间是否有效 Y-有效 N-无效
	 * */
	@Type(type = "yes_no")
	@Column(name = "IS_IMPLEMENT")
	private Boolean isImplement;

	/**
	 * 操作人
	 * */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATOR_ID")
	private AuthUser operator;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getIntervalStr() {
		return intervalStr;
	}

	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}

	public Boolean getIsImplement() {
		return isImplement;
	}

	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}

	public AuthUser getOperator() {
		return operator;
	}

	public void setOperator(AuthUser operator) {
		this.operator = operator;
	}

	public int getCreateDateYYYYMM() {
		return createDateYYYYMM;
	}

	public void setCreateDateYYYYMM(int createDateYYYYMM) {
		this.createDateYYYYMM = createDateYYYYMM;
	}

}
