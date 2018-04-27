package hg.payment.app.dao.payorder;

import hg.common.component.BaseDao;
import hg.payment.app.pojo.qo.payorder.AlipayPayOrderLocalQO;
import hg.payment.domain.model.payorder.alipay.AlipayPayOrder;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class AlipayPayOrderDAO extends BaseDao<AlipayPayOrder,AlipayPayOrderLocalQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, AlipayPayOrderLocalQO qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

	@Override
	protected Class<AlipayPayOrder> getEntityClass() {
		// TODO Auto-generated method stub
		return AlipayPayOrder.class;
	}

}
