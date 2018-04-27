package hsl.app.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hsl.domain.model.mp.scenic.MPPolicy;

@Repository
public class MPPolicyDao extends BaseDao<MPPolicy, BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

	@Override
	protected Class<MPPolicy> getEntityClass() {
		
		return MPPolicy.class;
	}

}
