package hg.demo.member.service.qo.hibernate.fx;

import hg.demo.member.service.qo.hibernate.AuthUserQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.domain.fixedprice.FixedPriceSet;

import java.util.Date;

@SuppressWarnings("serial")
public class FixedPriceSetQO extends BaseHibernateQO<FixedPriceSet> {

	/**
	 * 区间ID
	 */
	@QOAttr(name = "fixedPriceIntervalID", type = QOAttrType.EQ)
	private String fixedPriceIntervalID;
	
	/**
	 * 商户
	 */
	@QOAttr(name = "distributor", type = QOAttrType.LEFT_JOIN)
	private DistributorQO distributorQO;

	/**
	 * 关联的商品
	 */
	@QOAttr(name = "product", type = QOAttrType.LEFT_JOIN)
	private ProductQO productQO;
	
	@QOAttr(name = "applyUser", type = QOAttrType.LEFT_JOIN)
	private AuthUserQO authUserQO;

	/**
	 * 生效月份类型 1 等于 2 小于
	 */
	private int implementYYYYMMType;

	/**
	 * 生效月份
	 */
	private int implementYYYYMM;

	/**
	 * 失效时间
	 * */
//	@QOAttr(name = "invalidDate", type = QOAttrType.EQ)x
	private Date invalidDate;

	/**
	 * 是否有效 Y-有效 N-无效
	 * */
	@QOAttr(name = "isImplement", type = QOAttrType.EQ)
	private Boolean isImplement;

	/**
	 * 0-待审核 1-已通过 2-已拒绝
	 */
	@QOAttr(name = "checkStatus", type = QOAttrType.EQ)
	private Integer checkStatus;
	
	
	/**
	 * 排序规则
	 */
	private boolean sortflag;
	
	public boolean statusflag;
	
	public boolean isStatusflag() {
		return statusflag;
	}

	public void setStatusflag(boolean statusflag) {
		this.statusflag = statusflag;
	}

	public boolean isSortflag() {
		return sortflag;
	}

	public void setSortflag(boolean sortflag) {
		this.sortflag = sortflag;
	}

	public DistributorQO getDistributorQO() {
		return distributorQO;
	}

	public void setDistributorQO(DistributorQO distributorQO) {
		this.distributorQO = distributorQO;
	}

	public ProductQO getProductQO() {
		return productQO;
	}

	public void setProductQO(ProductQO productQO) {
		this.productQO = productQO;
	}

	public Boolean getIsImplement() {
		return isImplement;
	}

	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public int getImplementYYYYMMType() {
		return implementYYYYMMType;
	}

	public void setImplementYYYYMMType(int implementYYYYMMType) {
		this.implementYYYYMMType = implementYYYYMMType;
	}

	public int getImplementYYYYMM() {
		return implementYYYYMM;
	}

	public void setImplementYYYYMM(int implementYYYYMM) {
		this.implementYYYYMM = implementYYYYMM;
	}

	public AuthUserQO getAuthUserQO() {
		return authUserQO;
	}

	public void setAuthUserQO(AuthUserQO authUserQO) {
		this.authUserQO = authUserQO;
	}

	public String getFixedPriceIntervalID() {
		return fixedPriceIntervalID;
	}

	public void setFixedPriceIntervalID(String fixedPriceIntervalID) {
		this.fixedPriceIntervalID = fixedPriceIntervalID;
	}
	
	

}
