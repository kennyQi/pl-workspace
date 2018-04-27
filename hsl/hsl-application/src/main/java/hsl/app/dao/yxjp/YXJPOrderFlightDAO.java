package hsl.app.dao.yxjp;

import hg.common.component.BaseDao;
import hsl.domain.model.yxjp.YXJPOrderFlight;
import hsl.pojo.qo.yxjp.YXJPOrderFlightQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * @author zhurz
 */
@Repository("yxjpOrderFlightDAO")
public class YXJPOrderFlightDAO extends BaseDao<YXJPOrderFlight, YXJPOrderFlightQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, YXJPOrderFlightQO qo) {
		return criteria;
	}

	@Override
	protected Class<YXJPOrderFlight> getEntityClass() {
		return YXJPOrderFlight.class;
	}
}
