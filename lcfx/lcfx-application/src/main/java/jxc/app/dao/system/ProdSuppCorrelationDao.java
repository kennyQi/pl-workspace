package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.product.ProductSupplierCorrelation;
import jxc.domain.model.system.PaymentMethod;
import hg.common.component.BaseDao;
import hg.pojo.qo.PaymentMethodQo;
import hg.pojo.qo.ProdSuppCorrelationQo;

@Repository
public class ProdSuppCorrelationDao extends BaseDao<ProductSupplierCorrelation, ProdSuppCorrelationQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, ProdSuppCorrelationQo qo) {
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getProductId())) {
				criteria.add(Restrictions.eq("product.id", qo.getProductId()));
			}
			if (StringUtils.isNotBlank(qo.getSupplierId())) {
				criteria.add(Restrictions.eq("supplier.id", qo.getSupplierId()));
			}
		}
		
		
//		criteria.addOrder(Order.desc("createDate"));

		return criteria;
	}

	@Override
	protected Class<ProductSupplierCorrelation> getEntityClass() {
		return ProductSupplierCorrelation.class;
	}

}
