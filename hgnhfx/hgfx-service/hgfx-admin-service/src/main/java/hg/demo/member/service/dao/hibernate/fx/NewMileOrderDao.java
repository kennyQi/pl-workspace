package hg.demo.member.service.dao.hibernate.fx;

import hg.demo.member.service.qo.hibernate.fx.NewMileOrderQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.MileOrder;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author zqq
 * @date 2016-6-2下午3:43:46
 * @since
 */
@Repository
public class NewMileOrderDao extends BaseHibernateDAO<MileOrder, NewMileOrderQO> {

	@Override
	protected Class<MileOrder> getEntityClass() {
		// TODO Auto-generated method stub
		return MileOrder.class;
	}

	@Override
	protected void queryEntityComplete(NewMileOrderQO qo, List<MileOrder> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, NewMileOrderQO qo) {
		// TODO Auto-generated method stub
		return criteria;
	}
	

}
