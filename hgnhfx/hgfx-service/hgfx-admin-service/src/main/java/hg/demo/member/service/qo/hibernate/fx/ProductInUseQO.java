package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.ProductInUseSQO;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "productInUseDAO")
public class ProductInUseQO extends BaseHibernateQO {

	/**
	 * 商品使用状态
	 */
	@QOAttr(name = "status", type = QOAttrType.EQ_OR_NULL)
	private Integer status;
	
	private DistributorQO distributorQo;
	
	private ProductQO productQo;
	
	/**
	 * 状态集合
	 * */
	@QOAttr(name = "status", type = QOAttrType.IN)
	private Integer[] statusArray;
	
	public DistributorQO getDistributorQo() {
		return distributorQo;
	}

	public void setDistributorQo(DistributorQO distributorQo) {
		this.distributorQo = distributorQo;
	}


	public ProductQO getProductQo() {
		return productQo;
	}

	public void setProductQo(ProductQO productQo) {
		this.productQo = productQo;
	}

	public static ProductInUseQO build(ProductInUseSQO sqo)
	{
		ProductInUseQO qo = new ProductInUseQO();
		qo.setLimit(sqo.getLimit());
		qo.setStatus(sqo.getStatus());
		DistributorQO distributorQo = new DistributorQO();
		distributorQo.setId(sqo.getDistributorId());
		qo.setDistributorQo(distributorQo);
		qo.setProductQo(new ProductQO());
		if (sqo.getStatusArray()!=null && sqo.getStatusArray().length>0)
			qo.setStatusArray(sqo.getStatusArray());
		return qo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(Integer[] statusArray) {
		this.statusArray = statusArray;
	}
	
}
