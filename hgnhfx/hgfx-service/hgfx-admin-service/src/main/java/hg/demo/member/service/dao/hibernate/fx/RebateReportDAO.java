package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.RebateReportQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.rebate.RebateReport;

@Repository("rebateReportDAO")
public class RebateReportDAO extends BaseHibernateDAO<RebateReport, RebateReportQO>{

	@Override
	protected Class<RebateReport> getEntityClass() {
		// TODO Auto-generated method stub
		return RebateReport.class;
	}

	@Override
	protected void queryEntityComplete(RebateReportQO qo,
			List<RebateReport> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, RebateReportQO qo) {
		if(qo.getStartDate()!=null&&qo.getEndDate()!=null){
			criteria.add(Restrictions.between("createDate", qo.getStartDate(), qo.getEndDate()));
		}
		return criteria;
	}

}
