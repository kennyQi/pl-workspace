package jxc.app.dao.image;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hg.pojo.qo.ProductImageQO;
import jxc.domain.model.image.ProductImage;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
@Repository
public class ProductImageDao extends BaseDao<ProductImage,ProductImageQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, ProductImageQO qo) {
		criteria.createCriteria("product",JoinType.LEFT_OUTER_JOIN);
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getProductId())){
				criteria.add(Restrictions.eq("product.id",qo.getProductId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<ProductImage> getEntityClass() {
		return ProductImage.class;
	}

}
