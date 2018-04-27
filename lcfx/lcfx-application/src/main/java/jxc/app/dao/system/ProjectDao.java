package jxc.app.dao.system;

import hg.common.component.BaseDao;
import hg.pojo.qo.ProjectQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.system.Project;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class ProjectDao extends BaseDao<Project,ProjectQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, ProjectQO qo) {

		if(StringUtils.isNotBlank(qo.getName())){
			criteria.add(Restrictions.eq("name",qo.getName()));
		}
		criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.isRemoved()));
		criteria.addOrder(Order.desc("createDate"));
		return criteria;
	}

	@Override
	protected Class<Project> getEntityClass() {
		return Project.class;
	}
	
}
