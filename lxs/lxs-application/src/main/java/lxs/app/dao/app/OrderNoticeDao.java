package lxs.app.dao.app;

import hg.common.component.BaseDao;
import lxs.domain.model.app.OrderNotice;
import lxs.pojo.qo.app.OrderNoticeQO;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class OrderNoticeDao extends BaseDao<OrderNotice, OrderNoticeQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, OrderNoticeQO qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

	@Override
	protected Class<OrderNotice> getEntityClass() {
		// TODO Auto-generated method stub
		return OrderNotice.class;
	}

}
