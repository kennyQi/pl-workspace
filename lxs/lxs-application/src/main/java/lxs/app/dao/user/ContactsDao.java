package lxs.app.dao.user;

import hg.common.component.BaseDao;
import lxs.domain.model.user.Contacts;
import lxs.pojo.qo.user.contacts.ContactsQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ContactsDao extends BaseDao<Contacts, ContactsQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, ContactsQO qo) {
		if (qo != null) {
			// userid
			if (StringUtils.isNotBlank(qo.getUserId())) {
				criteria.add(Restrictions.eq("user.id", qo.getUserId()));
//				Criteria criteria2 = criteria.createCriteria("user");
			}
			if (StringUtils.isNotBlank(qo.getUserId())&&StringUtils.isNotBlank(qo.getContactsIDCardNO())) {
				criteria.add(Restrictions.eq("user.id", qo.getUserId()));
				criteria.add(Restrictions.eq("iDCardNO", qo.getContactsIDCardNO()));
			}
		}
		criteria.addOrder(Order.desc("createDate"));
		return criteria;
	}

	@Override
	protected Class<Contacts> getEntityClass() {
		return Contacts.class;
	}

}
