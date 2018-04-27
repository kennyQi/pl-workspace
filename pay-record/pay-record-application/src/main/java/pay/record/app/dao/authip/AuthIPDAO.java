package pay.record.app.dao.authip;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pay.record.domain.model.authip.AuthIP;
import pay.record.pojo.qo.authip.AuthIPQO;

@Repository
public class AuthIPDAO extends BaseDao<AuthIP, AuthIPQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, AuthIPQO qo) {
		if(qo != null){
			if(qo.getFromProjectIP() != null){
				criteria.add(Restrictions.eq("fromProjectIP", qo.getFromProjectIP()));
			}
			
			if(qo.getStatus() != null){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
		}
		
		return criteria;
	}

	@Override
	protected Class<AuthIP> getEntityClass() {
		return AuthIP.class;
	}

}
