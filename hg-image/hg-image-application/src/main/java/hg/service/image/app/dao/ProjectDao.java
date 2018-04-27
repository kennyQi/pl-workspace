package hg.service.image.app.dao;

import hg.common.component.BaseDao;
import hg.service.image.domain.model.Project;
import hg.service.image.domain.qo.ProjectLocalQo;
import hg.system.dao.AuthStaffDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：项目Dao类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzb
 * @创建时间：2014年12月21日 上午10:58:26
 */
@Repository
public class ProjectDao extends BaseDao<Project, ProjectLocalQo> {
	@Autowired
	private AuthStaffDao staffDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ProjectLocalQo qo) {
		if (qo != null) {
			// 关联的操作员
			if (qo.getStaffQo() != null) {
				Criteria staffCriteria = criteria.createCriteria(
						"staff", qo.getStaffQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
				staffDao.buildCriteriaOut(staffCriteria, qo.getStaffQo());
			}
			// 工程名
			if (StringUtils.isNotBlank(qo.getName())) {
				criteria.add(Restrictions.eq("name", qo.getName()));
			}
			// 工程环境
			if (StringUtils.isNotBlank(qo.getEnvName())){
				criteria.add(Restrictions.eq("envName", qo.getEnvName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Project> getEntityClass() {
		return Project.class;
	}
}