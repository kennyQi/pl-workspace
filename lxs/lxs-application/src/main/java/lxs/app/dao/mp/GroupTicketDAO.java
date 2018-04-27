package lxs.app.dao.mp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import lxs.domain.model.mp.GroupTicket;
import lxs.pojo.qo.mp.GroupTicketQO;

@Repository
public class GroupTicketDAO extends BaseDao<GroupTicket, GroupTicketQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, GroupTicketQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("baseInfo.name", qo.getName(),MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	protected Class<GroupTicket> getEntityClass() {
		return GroupTicket.class;
	}

}
