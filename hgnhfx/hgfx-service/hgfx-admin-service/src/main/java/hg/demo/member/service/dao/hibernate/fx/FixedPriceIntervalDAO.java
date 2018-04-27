package hg.demo.member.service.dao.hibernate.fx;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.FixedPriceIntervalQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.fixedprice.FixedPriceInterval;
import hg.fx.util.DateUtil;

@Repository("fixedPriceIntervalDAO")
public class FixedPriceIntervalDAO extends BaseHibernateDAO<FixedPriceInterval, FixedPriceIntervalQO>{

	@Override
	protected Class<FixedPriceInterval> getEntityClass() {
		// TODO Auto-generated method stub
		return FixedPriceInterval.class;
	}

	@Override
	protected void queryEntityComplete(FixedPriceIntervalQO qo,
			List<FixedPriceInterval> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, FixedPriceIntervalQO qo) {
		if(qo!=null){
			if(qo.getCreateDateYYYYMMType()==1){
				criteria.add(Restrictions.eq("createDateYYYYMM", qo.getCreateDateYYYYMM()));
			}else if(qo.getCreateDateYYYYMMType()==2){
				qo.setInvalidDate(DateUtil.parseDateTime1(DateUtil.forDatelast()+" 23:59:59"));
				criteria.add(Restrictions.le("createDateYYYYMM", qo.getCreateDateYYYYMM()));
				criteria.add(Restrictions.or(Restrictions.eq("invalidDate", qo.getInvalidDate()),Restrictions.isNull("invalidDate")));
			}else if(qo.getCreateDateYYYYMMType()==3){//查询上个月
				qo.setInvalidDate(DateUtil.parseDateTime1(DateUtil.getLastMonthEndDate()+" 23:59:59"));
				criteria.add(Restrictions.le("createDateYYYYMM", qo.getCreateDateYYYYMM()));
				criteria.add(Restrictions.or(Restrictions.eq("invalidDate", qo.getInvalidDate()),Restrictions.isNull("invalidDate")));
				criteria.addOrder(Order.desc("createDateYYYYMM"));
			}
			if(qo.getInvalidDate()==null){
				criteria.add(Restrictions.isNull("invalidDate"));
			}
		}
		return criteria;
	}

}
