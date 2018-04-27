package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.RebateIntervalSQO;
import hg.fx.util.DateUtil;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

@QOConfig(daoBeanId = "rebateIntervalDAO")
public class RebateIntervalQO extends BaseHibernateQO<String> {

	/**  */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品
	 */
	@QOAttr(name = "product", type = QOAttrType.LEFT_JOIN)
	private ProductQO productQO;
	
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	@QOAttr(name = "isImplement", type = QOAttrType.EQ)
	private Boolean isImplement;
	/***
	 * 排序字段申请时间
	 */
	@QOAttr(name = "isImplement", type = QOAttrType.ORDER)
	private Integer orderApplyDate;

	public static RebateIntervalQO build(RebateIntervalSQO sqo) {
		RebateIntervalQO qo = new RebateIntervalQO();
		qo.setId(sqo.getId());
		qo.setLimit(sqo.getLimit());
		if(!StringUtils.isBlank(sqo.getProductId())){
			ProductQO pqo = new ProductQO();
			pqo.setId(sqo.getProductId());
			qo.setProductQO(pqo);
		}
		qo.setIsImplement(sqo.getIsImplement());
		return qo ;
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

	public Integer getOrderApplyDate() {
		return orderApplyDate;
	}

	public void setOrderApplyDate(Integer orderApplyDate) {
		this.orderApplyDate = orderApplyDate;
	}

	
}
