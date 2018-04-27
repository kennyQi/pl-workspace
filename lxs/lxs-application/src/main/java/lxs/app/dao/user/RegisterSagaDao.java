package lxs.app.dao.user;

import hg.common.component.BaseDao;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.qo.user.user.RegisterSagaQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class RegisterSagaDao extends BaseDao<RegisterSaga,RegisterSagaQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, RegisterSagaQO qo) {
		if(qo!=null){
			//手机号
			if(StringUtils.isNotBlank(qo.getMobile())){
				criteria.add(Restrictions.eq("mobile", qo.getMobile()));
				criteria.addOrder(Order.desc("createDate"));
			}
			//手机号
			if(StringUtils.isNotBlank(qo.getSagaid())){
				criteria.add(Restrictions.eq("sagaid", qo.getSagaid()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<RegisterSaga> getEntityClass() {
		// TODO Auto-generated method stub
		return RegisterSaga.class;
	}

	
}
