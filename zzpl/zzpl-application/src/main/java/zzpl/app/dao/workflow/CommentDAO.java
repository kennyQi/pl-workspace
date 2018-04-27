package zzpl.app.dao.workflow;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.Comment;
import zzpl.pojo.qo.workflow.CommentQO;
import hg.common.component.BaseDao;

@Repository
public class CommentDAO extends BaseDao<Comment, CommentQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CommentQO qo) {
		if (qo != null) {
			if (qo.getId() != null&& StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if (qo.getTasklistID() != null&& StringUtils.isNotBlank(qo.getTasklistID())) {
				criteria.add(Restrictions.eq("tasklist.id", qo.getTasklistID()));
			}
			if (qo.getStepNO() != null) {
				criteria.add(Restrictions.eq("stepNO", qo.getStepNO()));
			}
			if (qo.getCurrentUserID() != null&& StringUtils.isNotBlank(qo.getCurrentUserID())) {
				criteria.add(Restrictions.eq("currentUserID", qo.getCurrentUserID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Comment> getEntityClass() {
		return Comment.class;
	}

}
