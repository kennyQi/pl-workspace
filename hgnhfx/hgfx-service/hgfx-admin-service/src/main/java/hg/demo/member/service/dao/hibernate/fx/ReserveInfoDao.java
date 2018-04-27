package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.ReserveInfoQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.ReserveInfo;
import hg.fx.spi.qo.ReserveInfoSQO;

/**
 * 
 * @author xinglj
 *
 */
@Repository
public class ReserveInfoDao extends BaseHibernateDAO<ReserveInfo, ReserveInfoQO>{

	@Override
	protected Class<ReserveInfo> getEntityClass() {
		return ReserveInfo.class;
	}

	@Override
	protected void queryEntityComplete(ReserveInfoQO qo, List<ReserveInfo> list) {
		 
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, ReserveInfoQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
