package jxc.app.dao.system;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import jxc.domain.model.system.ProjectMode;
@Repository
public class ProjectModeDao extends BaseDao<ProjectMode,BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<ProjectMode> getEntityClass() {
		return ProjectMode.class;
	}

}
