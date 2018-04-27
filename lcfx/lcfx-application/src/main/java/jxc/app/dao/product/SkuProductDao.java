package jxc.app.dao.product;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.SkuProductQO;
import jxc.domain.model.product.SkuProduct;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class SkuProductDao extends BaseDao<SkuProduct, SkuProductQO> {

	@Autowired
	SpecDetailDao specDetailDao;
	@Autowired
	ProductDao productDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, SkuProductQO qo) {
		
		if (qo != null) {
			if(StringUtils.isNotBlank(qo.getProductId())){
				criteria.add(Restrictions.eq("product.id",qo.getProductId()));
			}
		}
		criteria.addOrder(Order.asc("id"));
		return criteria;
	}

	@Override
	protected Class<SkuProduct> getEntityClass() {
		return SkuProduct.class;
	}

}
