package pl.app.dao;
 
import hg.common.component.BaseDao;
import hg.service.ad.domain.model.ad.Ad;
import hg.service.ad.pojo.qo.ad.AdQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AdDao extends BaseDao<Ad, AdQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, AdQO qo) {

		if (qo != null) {
			
			//	查询大于目标的优先级，升序排列的第一条
			if (qo.getLastOrNextPriority() != null) {
				
				criteria.add(Restrictions.gt("priority", qo.getTargetPriority()));
				
				if (qo.getLastOrNextPriority()) {
					criteria.addOrder(Order.asc("priority"));
				} else {
					criteria.addOrder(Order.desc("priority"));
				}
				
				return criteria;
			}
		}
		return criteria;
	}

	@Override
	protected Class<Ad> getEntityClass() {
		return Ad.class;
	}

}
