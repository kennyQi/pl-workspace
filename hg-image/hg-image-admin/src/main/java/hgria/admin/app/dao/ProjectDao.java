package hgria.admin.app.dao;

import hg.common.component.BaseDao;
import hg.system.dao.AuthStaffDao;
import hgria.admin.app.pojo.qo.ProjectQo;
import hgria.domain.model.project.Project;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao extends BaseDao<Project, ProjectQo> {
	
	@Autowired
	private AuthStaffDao staffDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ProjectQo qo) {
		if (qo != null) {
			// 所属景区
			if (qo.getStaffQo() != null) {
				Criteria staffCriteria = criteria.createCriteria(
						"staff", qo.getStaffQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
				staffDao.buildCriteriaOut(staffCriteria, qo.getStaffQo());
			}
			// 景点名称
			if (StringUtils.isNotBlank(qo.getName())) {
				if (qo.isNameLike())
					criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
				else
					criteria.add(Restrictions.eq("name", qo.getName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Project> getEntityClass() {
		return Project.class;
	}
	
}
