package hg.demo.member.service.dao.hibernate.fx;

import hg.demo.member.service.qo.hibernate.fx.FixedPriceSetQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.fixedprice.FixedPriceSet;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("fixedPriceSetDAO")
public class FixedPriceSetDAO extends BaseHibernateDAO<FixedPriceSet, FixedPriceSetQO>{

	@Override
	protected Class<FixedPriceSet> getEntityClass() {
		// TODO Auto-generated method stub
		return FixedPriceSet.class;
	}

	@Override
	protected void queryEntityComplete(FixedPriceSetQO qo,
			List<FixedPriceSet> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, FixedPriceSetQO qo) {
		if(qo!=null){
			if(qo.getImplementYYYYMMType()==1){
				criteria.add(Restrictions.eq("implementYYYYMM", qo.getImplementYYYYMM()));
			}else if(qo.getImplementYYYYMMType()==2){
				criteria.add(Restrictions.le("implementYYYYMM", qo.getImplementYYYYMM()));
			}
			List<Integer> l = new ArrayList<>();
			l.add(0);
			l.add(1);
			l.add(2);
			if(qo.isStatusflag()){
					criteria.add(Restrictions.in("checkStatus", l));
			}
			if(qo.isSortflag()){
				criteria.addOrder(Order.asc("checkStatus"));
			}
			if(qo.getInvalidDate()==null){
				criteria.add(Restrictions.isNull("invalidDate"));
			}
		}
		return criteria;
	}

}
