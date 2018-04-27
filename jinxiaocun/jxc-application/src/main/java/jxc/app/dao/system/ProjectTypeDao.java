package jxc.app.dao.system;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import jxc.domain.model.system.ProjectType;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
@Repository
public class ProjectTypeDao extends BaseDao<ProjectType,BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		
		return criteria;
	}

	@Override
	protected Class<ProjectType> getEntityClass() {
		return ProjectType.class;
	}

}
