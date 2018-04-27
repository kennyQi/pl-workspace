package lxs.app.dao.app;

import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;

import lxs.domain.model.app.Carousel;
import lxs.pojo.qo.app.LineCarouselQO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;
@Repository
public class LineCarouselDAO extends BaseDao<Carousel, LineCarouselQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineCarouselQO qo) {
		criteria.addOrder(Order.asc("sort"));
		if (qo != null) {
			if (qo.getCarouselLevel() != null) {
				criteria.add(Restrictions.eq("carouselLevel",qo.getCarouselLevel()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Carousel> getEntityClass() {
		// TODO Auto-generated method stub
		return Carousel.class;
	}
	
	public int maxProperty(String propertyName, LineCarouselQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}

}
