package slfx.mp.app.dao;

import hg.common.component.BaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import slfx.mp.app.pojo.qo.TCOrderQO;
import slfx.mp.domain.model.order.TCOrder;

@Repository
public class TCOrderDAO extends BaseDao<TCOrder, TCOrderQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, TCOrderQO qo) {
		return criteria;
	}

	@Override
	protected Class<TCOrder> getEntityClass() {
		return TCOrder.class;
	}

}
