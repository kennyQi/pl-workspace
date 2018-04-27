package hsl.app.dao.yxjp;

import hg.common.component.BaseDao;
import hsl.domain.model.yxjp.YXJPOrderPassenger;
import hsl.pojo.qo.yxjp.YXJPOrderPassengerQO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author zhurz
 */
@Repository("yxjpOrderPassengerDAO")
public class YXJPOrderPassengerDAO extends BaseDao<YXJPOrderPassenger, YXJPOrderPassengerQO> {
	@Override
	protected Criteria buildCriteria(Criteria criteria, YXJPOrderPassengerQO qo) {
		if (qo != null) {
			if (qo.getHasCompany() != null) {
				if (qo.getHasCompany())
					criteria.add(Restrictions.isNotNull("companyInfo.companyId"));
				else
					criteria.add(Restrictions.isNull("companyInfo.companyId"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<YXJPOrderPassenger> getEntityClass() {
		return YXJPOrderPassenger.class;
	}
}
