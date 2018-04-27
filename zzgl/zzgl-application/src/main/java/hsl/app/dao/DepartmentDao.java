package hsl.app.dao;
import hg.common.component.BaseDao;
import hsl.domain.model.company.Department;
import hsl.pojo.qo.company.HslDepartmentQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class DepartmentDao extends BaseDao<Department, HslDepartmentQO>{

	/**
	 * 
	 */
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, HslDepartmentQO qo) {
		if (qo != null) {
			// 公司
			if (qo.getCompanyQO() != null) {
				Criteria companyCriteria = criteria.createCriteria(
						"company", qo.getCompanyQO().getAlias(), JoinType.LEFT_OUTER_JOIN);
				companyDao.buildCriteriaOut(companyCriteria, qo.getCompanyQO());
			}
			/*if(StringUtils.isNotBlank(qo.getCompanyId())){
				criteria.add(Restrictions.eq("company.id", qo.getCompanyId()));
			}*/
			if(StringUtils.isNotBlank(qo.getSearchName())){
				criteria.add(Restrictions.like("deptName", qo.getSearchName(),MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Department> getEntityClass() {
		return Department.class;
	}
	
}
