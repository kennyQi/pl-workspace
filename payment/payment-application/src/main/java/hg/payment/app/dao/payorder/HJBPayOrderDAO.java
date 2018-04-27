package hg.payment.app.dao.payorder;

import hg.common.component.BaseDao;
import hg.payment.app.pojo.qo.payorder.HJBPayOrderLocalQO;
import hg.payment.domain.model.payorder.hjbPay.HJBPayOrder;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class HJBPayOrderDAO extends BaseDao<HJBPayOrder, HJBPayOrderLocalQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HJBPayOrderLocalQO qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

	@Override
	protected Class<HJBPayOrder> getEntityClass() {
		// TODO Auto-generated method stub
		return HJBPayOrder.class;
	}

}
