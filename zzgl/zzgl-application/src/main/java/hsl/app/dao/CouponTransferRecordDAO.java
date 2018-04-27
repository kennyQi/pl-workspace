package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.coupon.CouponTransferRecord;
import hsl.pojo.qo.coupon.CouponTransferRecordQO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * @author zhurz
 */
@Repository
public class CouponTransferRecordDAO extends BaseDao<CouponTransferRecord, CouponTransferRecordQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CouponTransferRecordQO qo) {
		criteria.addOrder(Order.desc("transferDate"));
		return criteria;
	}

	@Override
	protected Class<CouponTransferRecord> getEntityClass() {
		return CouponTransferRecord.class;
	}
}
