package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.company.Company;
import hsl.pojo.qo.company.HslCompanyQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class CompanyDao extends BaseDao<Company,HslCompanyQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslCompanyQO qo) {
		if(StringUtils.isNotBlank(((HslCompanyQO) qo).getUserId())){
			criteria.add(Restrictions.eq("user.id", ((HslCompanyQO) qo).getUserId()));
		}
		return criteria;
	}

	@Override
	protected Class<Company> getEntityClass() {
		return Company.class;
	}




}
