package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import hg.common.component.BaseDao;
import hsl.domain.model.mp.order.MPOrderUser;
import hsl.pojo.qo.mp.HslMPOrderUserQO;

@Repository
public class MPOrderUserDao extends BaseDao<MPOrderUser, HslMPOrderUserQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslMPOrderUserQO qo) {
		
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getUserId())){
				criteria.add(Restrictions.eq("userId", qo.getUserId()));
			}
			
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("name", "%"+qo.getName()+"%"));
			}
			if(qo.isOnlyMember()){//如果不是从部门员工通讯录里面选的，userid为空
				criteria.add(Restrictions.isNotNull("userId"));
			}
		}

		return criteria;
	}

	@Override
	protected Class<MPOrderUser> getEntityClass() {
		// TODO Auto-generated method stub
		return MPOrderUser.class;
	}

}
