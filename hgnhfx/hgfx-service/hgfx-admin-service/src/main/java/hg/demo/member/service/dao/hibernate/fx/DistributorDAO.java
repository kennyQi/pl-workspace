package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.DistributorQO;
import hg.demo.member.service.qo.hibernate.fx.ReserveInfoQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.Distributor;
import hg.fx.domain.ReserveInfo;

/**
 * @author cangs
 */
@Repository("distributorDAO")
public class DistributorDAO extends BaseHibernateDAO<Distributor, DistributorQO> {

	@Autowired
	private ReserveInfoDao reserveInfoDao;
	
	@Override
	protected Class<Distributor> getEntityClass() {
		// TODO Auto-generated method stub
		return Distributor.class;
	}

	@Override
	protected void queryEntityComplete(DistributorQO qo, List<Distributor> list) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, DistributorQO qo) {
		
		if(qo.isQueryReserveInfo())
		{
			ReserveInfoQO reserveInfoQo = new ReserveInfoQO();
			Criteria dc = criteria.createCriteria("reserveInfo", reserveInfoQo.getAlias(), JoinType.LEFT_OUTER_JOIN);
			reserveInfoDao.buildCriteriaOut(dc, reserveInfoQo);
		}
		return criteria;
	}

}
