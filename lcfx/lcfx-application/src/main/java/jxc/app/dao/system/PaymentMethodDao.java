package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.system.PaymentMethod;
import hg.common.component.BaseDao;
import hg.pojo.qo.PaymentMethodQo;

@Repository
public class PaymentMethodDao extends BaseDao<PaymentMethod, PaymentMethodQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, PaymentMethodQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
			if (StringUtils.isNotBlank(qo.getPaymentMethodName())) {
				criteria.add(Restrictions.eq("paymentMethodName", qo.getPaymentMethodName()));
			}
		}
		
		
		criteria.addOrder(Order.desc("createDate"));

		return criteria;
	}

	@Override
	protected Class<PaymentMethod> getEntityClass() {
		return PaymentMethod.class;
	}

}
