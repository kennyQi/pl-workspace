package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.DistributorQO;
import hg.demo.member.service.qo.hibernate.fx.DistributorUserQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;

/**
 * 
 * @author Caihuan
 * @date   2016年6月1日
 */
@Repository("distributorUserDAO")
public class DistributorUserDAO extends BaseHibernateDAO<DistributorUser, DistributorUserQO> {

	@Autowired
	private DistributorDAO distributorDAO;
	
	@Override
	protected Class<DistributorUser> getEntityClass() {
		// TODO Auto-generated method stub
		return DistributorUser.class;
	}

	@Override
	protected void queryEntityComplete(DistributorUserQO qo, List<DistributorUser> list) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, DistributorUserQO qo) {
		
		if(qo.getDistributorQo()!=null)
		{
			Criteria dc = criteria.createCriteria("distributor", qo.getDistributorQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			distributorDAO.buildCriteriaOut(dc, qo.getDistributorQo());
		}
		
		criteria.addOrder(Order.desc("createDate"));
		return criteria;
	}

}
