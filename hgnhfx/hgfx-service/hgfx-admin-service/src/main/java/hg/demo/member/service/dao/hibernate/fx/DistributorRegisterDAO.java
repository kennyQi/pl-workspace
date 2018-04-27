package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;


import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.DistributorRegisterQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.DistributorRegister;
/**
 * 
 * @author zqq
 * @date 2016-7-14下午2:59:34
 * @since
 */
@Repository("distributorRegisterDAO")
public class DistributorRegisterDAO extends BaseHibernateDAO<DistributorRegister, DistributorRegisterQO>{

	@Override
	protected Class<DistributorRegister> getEntityClass() {
		// TODO Auto-generated method stub
		return DistributorRegister.class;
	}

	@Override
	protected void queryEntityComplete(DistributorRegisterQO qo,
			List<DistributorRegister> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, DistributorRegisterQO qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

}
