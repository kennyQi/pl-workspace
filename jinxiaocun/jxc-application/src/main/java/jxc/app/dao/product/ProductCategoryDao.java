package jxc.app.dao.product;

import hg.common.component.BaseDao;
import hg.pojo.qo.CategoryQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.product.ProductCategory;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryDao extends BaseDao<ProductCategory, CategoryQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CategoryQO qo) {

		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));

			if (StringUtils.isNotBlank(qo.getName())) {
				criteria.add(Restrictions.eq("name", qo.getName()));
			}
			if (StringUtils.isNotBlank(qo.getParentCategoryId())) {
				criteria.add(Restrictions.eq("parentCategory.id", qo.getParentCategoryId()));
			}
			if (qo.getUsing() != null) {
				criteria.add(Restrictions.eq("status.using", qo.getUsing()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<ProductCategory> getEntityClass() {
		return ProductCategory.class;
	}

}
