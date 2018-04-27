package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.user.UserBindAccount;
import hsl.pojo.qo.user.HslUserBindAccountQO;

@Repository
public class UserBindAccountDao extends BaseDao<UserBindAccount, HslUserBindAccountQO>{

	@Autowired
	private UserDao userDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, HslUserBindAccountQO qo) {
		
		
		
		if(qo!=null){
			//帐号类型 1微信
			if(qo.getAccountType()!=null){
				criteria.add(Restrictions.eq("accountType", qo.getAccountType()));
			}
			
			//如果绑定外部账户ID和用户信息同时存在  绑定外部账户ID优先
			if(StringUtils.isNotBlank(qo.getBindAccountId()) || (StringUtils.isNotBlank(qo.getBindAccountId()) && qo.getUserQO()!=null)){
				criteria.add(Restrictions.eq("bindAccountId", qo.getBindAccountId()));
				criteria.createCriteria("user", JoinType.LEFT_OUTER_JOIN);
			}
			//绑定的用户
			else if(qo.getUserQO()!=null){
				Criteria criteria2 = criteria.createCriteria("user", JoinType.LEFT_OUTER_JOIN);
				userDao.buildCriteriaOut(criteria2, qo.getUserQO());
			}
			
			criteria.add(Restrictions.eq("binding", true));
						
			if(StringUtils.isNotBlank(qo.getBindAccountName())){
				criteria.add(Restrictions.eq("bindAccountName", qo.getBindAccountName()));
			}
			

		}
		
		return criteria;
	}

	@Override
	protected Class<UserBindAccount> getEntityClass() {

		return UserBindAccount.class;
	}

}
