package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import hg.demo.member.service.qo.hibernate.fx.FixedPriceReportQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.fixedprice.FixedPriceReport;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("fixedPriceReportDAO")
public class FixedPriceReportDAO extends BaseHibernateDAO<FixedPriceReport, FixedPriceReportQO>{

	@Override
	protected Class<FixedPriceReport> getEntityClass() {
		// TODO Auto-generated method stub
		return FixedPriceReport.class;
	}

	@Override
	protected void queryEntityComplete(FixedPriceReportQO qo,
			List<FixedPriceReport> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, FixedPriceReportQO qo) {
		if(qo.getStartDate()!=null&&qo.getEndDate()!=null){
			criteria.add(Restrictions.between("createDate", qo.getStartDate(), qo.getEndDate()));
		}
		return criteria;
	}

}
