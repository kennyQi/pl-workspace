package hsl.app.dao.account;

import hg.common.component.BaseDao;
import hsl.domain.model.user.account.HoldUserSnapshot;
import hsl.pojo.qo.account.HoldUserSnapshotQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class HoldUserSnapshotDao extends BaseDao<HoldUserSnapshot, HoldUserSnapshotQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HoldUserSnapshotQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getUserId())){
				criteria.add(Restrictions.eq("userId", qo.getUserId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<HoldUserSnapshot> getEntityClass() {
		return HoldUserSnapshot.class;
	}

}
