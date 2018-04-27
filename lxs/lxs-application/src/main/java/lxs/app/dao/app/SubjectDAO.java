package lxs.app.dao.app;

import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;

import lxs.domain.model.app.Subject;
import lxs.pojo.qo.app.SubjectQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

/**
 * 
 * @类功能说明：主题 DAO
 * @类修改者：
 * @修改日期：2015年9月18日上午10:48:54
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午10:48:54
 */
@Repository
public class SubjectDAO extends BaseDao<Subject, SubjectQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, SubjectQO qo) {
		if (qo != null) {
			if (qo.getSubjectID() != null && StringUtils.isNotBlank(qo.getSubjectID())) {
				criteria.add(Restrictions.eq("id",qo.getSubjectID()));
				return criteria;
			}
			if (qo.getSubjectName() != null && StringUtils.isNotBlank(qo.getSubjectName())) {
				criteria.add(Restrictions.like("subjectName",qo.getSubjectName(),MatchMode.ANYWHERE));
			}
			if (qo.getSubjectType() != null && qo.getSubjectType()!= 0) {
				criteria.add(Restrictions.eq("subjectType",qo.getSubjectType()));
			}
			if (qo.getSort() != null && qo.getSort()!= 0) {
				criteria.add(Restrictions.eq("sort",qo.getSort()));
			}
			if (qo.getSortProductSum()!=null) {
				if(qo.getSortProductSum()){
					criteria.add(Restrictions.gt("productSUM","0"));
				}
			}
		}
		criteria.addOrder(Order.desc("sort"));
		return criteria;
	}

	public int maxProperty(String propertyName, SubjectQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
	
	@Override
	protected Class<Subject> getEntityClass() {
		return Subject.class;
	}

}
