package hsl.app.dao.dzp.order;

import hg.common.component.BaseDao;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.pojo.qo.dzp.order.DZPTicketOrderQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * 电子票订单
 *
 * @author zhurz
 * @since 1.8
 */
@Repository("dzpTicketOrderDAO")
public class DZPTicketOrderDAO extends BaseDao<DZPTicketOrder, DZPTicketOrderQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPTicketOrderQO qo) {
		return criteria;
	}

	@Override
	protected Class<DZPTicketOrder> getEntityClass() {
		return DZPTicketOrder.class;
	}

}
