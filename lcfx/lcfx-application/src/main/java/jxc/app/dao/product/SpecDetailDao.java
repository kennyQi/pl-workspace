package jxc.app.dao.product;

import hg.common.component.BaseDao;
import hg.pojo.qo.SkuProductQO;
import hg.pojo.qo.SpecDetailQO;
import hg.pojo.qo.SpecValueQO;
import hg.pojo.qo.SpecificationQO;
import jxc.domain.model.product.SpecDetail;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class SpecDetailDao extends BaseDao<SpecDetail, SpecDetailQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, SpecDetailQO qo) {
		criteria.createCriteria("skuProduct",JoinType.LEFT_OUTER_JOIN); 
		criteria.createCriteria("specValue",JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("specification","spec",JoinType.LEFT_OUTER_JOIN);
		if (qo != null) {
			
			if(StringUtils.isNotBlank(qo.getSkuProductId())){
				criteria.add(Restrictions.eq("skuProduct.id",qo.getSkuProductId()));
			}
			if(StringUtils.isNotBlank(qo.getSpecificationId())){
				criteria.add(Restrictions.eq("spec.id",qo.getSpecificationId()));
			}
			if(StringUtils.isNotBlank(qo.getSpecValueId())){
				criteria.add(Restrictions.eq("specValue.id",qo.getSpecValueId()));
			}
			if(StringUtils.isNotBlank(qo.getProductId())){
				criteria.createAlias("skuProduct","sku");
				criteria.add(Restrictions.eq("sku.product.id",qo.getProductId()));
			}
		}
		criteria.addOrder(Order.asc("skuProduct.id"));
		criteria.addOrder(Order.asc("spec.specCode"));
		return criteria;
	}

	@Override
	protected Class<SpecDetail> getEntityClass() {
		return SpecDetail.class;
	}

}
