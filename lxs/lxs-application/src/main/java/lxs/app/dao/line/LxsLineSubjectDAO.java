package lxs.app.dao.line;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;

import java.util.List;

import lxs.domain.model.line.LineSubject;
import lxs.pojo.qo.line.LineSubjectQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LxsLineSubjectDAO extends BaseDao<LineSubject, LineSubjectQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineSubjectQO qo) {
		if (qo != null) {
			
			if (StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if (StringUtils.isNotBlank(qo.getSubjectID())) {
				criteria.add(Restrictions.eq("subjectID", qo.getSubjectID()));
			}
			if (StringUtils.isNotBlank(qo.getLineID())) {
				criteria.add(Restrictions.eq("lineID", qo.getLineID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<LineSubject> getEntityClass() {
		return LineSubject.class;
	}

	@Override
	public Pagination hqlQueryPagination(Class<?> alias, String hql,
			String countHql, Integer pageNo, Integer pageSize,
			Object condition, List<Object> params) {
		// TODO Auto-generated method stub
		return super.hqlQueryPagination(alias, hql, countHql, pageNo, pageSize,
				condition, params);
	}

}
