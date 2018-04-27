package jxc.app.dao.product;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hg.pojo.qo.BrandQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.product.Brand;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BrandDao extends BaseDao<Brand, BrandQO> {

	@Override
	protected Class<Brand> getEntityClass() {
		return Brand.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, BrandQO qo) {
		if (qo != null) {

			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));

			if (StringUtils.isNotBlank(qo.getChineseName())) {
				if (qo.getChineseNameLike() != null && qo.getChineseNameLike()) {
					criteria.add(Restrictions.like("chineseName", qo.getChineseName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("chineseName", qo.getChineseName()));
				}
			}
			if (StringUtils.isNotBlank(qo.getEnglishName())) {
				if (qo.getEnglishNameLike() != null && qo.getEnglishNameLike()) {
					criteria.add(Restrictions.like("englishName", qo.getEnglishName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("englishName", qo.getEnglishName()));
				}
			}

			criteria.addOrder(Order.desc("createDate"));

		}
		return criteria;
	}

}
