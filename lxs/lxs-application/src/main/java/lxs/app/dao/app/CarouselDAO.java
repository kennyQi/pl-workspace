package lxs.app.dao.app;

import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;

import lxs.domain.model.app.Carousel;
import lxs.pojo.qo.app.CarouselQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;
@Repository
public class CarouselDAO extends BaseDao<Carousel, CarouselQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CarouselQO qo) {
		criteria.addOrder(Order.asc("sort"));
		if (qo != null) {
			if (qo.getCarouselLevel() != null) {
				criteria.add(Restrictions.eq("carouselLevel",qo.getCarouselLevel()));
			}
			if (qo.getCarouselAction() != null&&StringUtils.isNotBlank(qo.getCarouselAction())) {
				criteria.add(Restrictions.eq("carouselAction",qo.getCarouselAction()));
			}
			if (qo.getStatus() != null && qo.getStatus() !=0) {
				criteria.add(Restrictions.eq("status",qo.getStatus()));
			}
		}
			
		
		
		return criteria;
	}

	@Override
	protected Class<Carousel> getEntityClass() {
		// TODO Auto-generated method stub
		return Carousel.class;
	}
	
	public int maxProperty(String propertyName, CarouselQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}

}
