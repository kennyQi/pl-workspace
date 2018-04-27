package hsl.app.dao.account;

import hg.common.component.BaseDao;
import hsl.domain.model.user.account.Account;
import hsl.pojo.qo.account.AccountQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
/**
 * 
 * @类功能说明：个人账户
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-9-2下午1:51:21
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@Repository
public class AccountDao extends BaseDao<Account,AccountQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, AccountQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if(StringUtils.isNotBlank(qo.getUserID())){
				criteria.createAlias("user", "user");
				criteria.add(Restrictions.eq("user.id", qo.getUserID()));
			}
			if(qo.isLock()){
				criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
			}
		}
		return criteria;
	}
	public Account queryAccount(){
		return null;
	}
	@Override
	protected Class<Account> getEntityClass() {
		return Account.class;
	}

}
