package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.domain.fixedprice.FixedPriceInterval;

import java.util.Date;

@SuppressWarnings("serial")
public class FixedPriceIntervalQO extends BaseHibernateQO<FixedPriceInterval> {

	/**
	 * 关联的商品
	 */
	@QOAttr(name = "product", type = QOAttrType.LEFT_JOIN)
	private ProductQO productQO;

	// 1：等于 2 :小于
	private int createDateYYYYMMType;

	private int createDateYYYYMM;

	/**
	 * 失效时间
	 */
	@QOAttr(name = "invalidDate", type = QOAttrType.EQ)
	private Date invalidDate;

	/**
	 * 区间是否有效 Y-有效 N-无效
	 * */
	@QOAttr(name = "isImplement", type = QOAttrType.EQ)
	private Boolean isImplement;

	public Boolean getIsImplement() {
		return isImplement;
	}

	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}

	public ProductQO getProductQO() {
		return productQO;
	}

	public void setProductQO(ProductQO productQO) {
		this.productQO = productQO;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public int getCreateDateYYYYMM() {
		return createDateYYYYMM;
	}

	public void setCreateDateYYYYMM(int createDateYYYYMM) {
		this.createDateYYYYMM = createDateYYYYMM;
	}

	public int getCreateDateYYYYMMType() {
		return createDateYYYYMMType;
	}

	public void setCreateDateYYYYMMType(int createDateYYYYMMType) {
		this.createDateYYYYMMType = createDateYYYYMMType;
	}

}
