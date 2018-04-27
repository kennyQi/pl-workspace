package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.system.Attention;
import hg.common.component.BaseDao;
import hg.pojo.qo.AttentionQo;

@Repository
public class AttentionDao extends BaseDao<Attention, AttentionQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, AttentionQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
			if (StringUtils.isNotBlank(qo.getAttentionName())) {
				criteria.add(Restrictions.eq("attentionName", qo.getAttentionName()));
			}
		}
		
		criteria.addOrder(Order.desc("createDate"));
		return criteria;
	}

	@Override
	protected Class<Attention> getEntityClass() {
		return Attention.class;
	}

}
