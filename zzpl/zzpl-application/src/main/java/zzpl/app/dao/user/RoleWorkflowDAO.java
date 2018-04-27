package zzpl.app.dao.user;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.RoleWorkflow;
import zzpl.pojo.qo.user.RoleWorkflowQO;
import hg.common.component.BaseDao;

@Repository
public class RoleWorkflowDAO extends BaseDao<RoleWorkflow, RoleWorkflowQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, RoleWorkflowQO qo) {
		if (qo != null) {
			if (qo.getCompanyID() != null && StringUtils.isNotBlank(qo.getCompanyID())) {
				Criteria workCriteria=criteria.createCriteria("workflow");
				workCriteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
			}
			if (qo.getRoleID()!= null && StringUtils.isNotBlank(qo.getRoleID())) {
				Criteria roleCriteria=criteria.createCriteria("role");
				roleCriteria.add(Restrictions.eq("id", qo.getRoleID()));
			}
			if (qo.getWorkflowID() != null && StringUtils.isNotBlank(qo.getWorkflowID())) {
				Criteria workCriteria=criteria.createCriteria("workflow");
				workCriteria.add(Restrictions.eq("id", qo.getWorkflowID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<RoleWorkflow> getEntityClass() {
		return RoleWorkflow.class;
	}

}
