package zzpl.app.dao.user;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.Company;
import zzpl.pojo.dto.user.status.CompanyStatus;
import zzpl.pojo.qo.user.CompanyQO;
import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

@Repository
public class CompanyDAO extends BaseDao<Company, CompanyQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CompanyQO qo) {
		if(qo!=null){
			if(qo.getId()!=null&&StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getCompanyID())){
				criteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
			}
			if(qo.getCompanyName()!=null&&StringUtils.isNotBlank(qo.getCompanyName())){
				criteria.add(Restrictions.like("companyName", qo.getCompanyName(),MatchMode.ANYWHERE));
			}
		}
		criteria.add(Restrictions.eq("status", CompanyStatus.NORMAL));
		criteria.addOrder(Order.asc("sort"));
		return criteria;
	}

	@Override
	protected Class<Company> getEntityClass() {
		return Company.class;
	}

	/*@Override
	public Pagination queryPagination(Pagination pagination) {
		pagination = super.queryPagination(pagination);
		List<Company> companies = (List<Company>) pagination.getList();
		pagination.setList(companies);
		return pagination;
	}*/
	
	public int maxProperty(String propertyName, CompanyQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
}
