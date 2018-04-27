package lxs.app.dao.app;

import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;

import lxs.domain.model.app.Recommend;
import lxs.pojo.qo.app.RecommendQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;
@Repository
public class RecommendDAO extends BaseDao<Recommend, RecommendQO> {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, RecommendQO qo) {
		
		if(qo!=null){
			if (qo.getRecommendID() != null && StringUtils.isNotBlank(qo.getRecommendID())) {
				criteria.add(Restrictions.eq("id",qo.getRecommendID()));
				return criteria;
			}
			if (qo.getRecommendName() != null && StringUtils.isNotBlank(qo.getRecommendName())) {
				criteria.add(Restrictions.like("recommendName",qo.getRecommendName(),MatchMode.ANYWHERE));
			}
			if (qo.getStatus() != null && qo.getStatus() !=0) {
				criteria.add(Restrictions.eq("status",qo.getStatus()));
			}
			if (qo.getInCreateDate() != null) {
				criteria.add(Restrictions.ge("createDate",qo.getInCreateDate()));
			}
			if (qo.getToCreateDate() != null) {
				criteria.add(Restrictions.le("createDate",qo.getToCreateDate()));
			}
			if (qo.getRecommendAction() != null && StringUtils.isNotBlank(qo.getRecommendAction())) {
				criteria.add(Restrictions.eq("recommendAction",qo.getRecommendAction()));
			}
		}
		criteria.addOrder(Order.asc("sort"));
		return criteria;
	}

	@Override
	protected Class<Recommend> getEntityClass() {
		return Recommend.class;
	}
	
	/**
	 * @Title: maxProperty 
	 * @author guok
	 * @Description: 排序查询
	 * @Time 2015年9月14日上午10:29:29
	 * @param propertyName
	 * @param qo
	 * @return int 设定文件
	 * @throws
	 */
	public int maxProperty(String propertyName, RecommendQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
}
