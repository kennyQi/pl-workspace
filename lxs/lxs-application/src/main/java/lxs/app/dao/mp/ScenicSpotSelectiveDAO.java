package lxs.app.dao.mp;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import lxs.domain.model.mp.ScenicSpotSelective;
import lxs.pojo.qo.mp.ScenicSpotSelectiveQO;
import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hg.common.util.MyBeanUtils;

@Repository
public class ScenicSpotSelectiveDAO extends BaseDao<ScenicSpotSelective, ScenicSpotSelectiveQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, ScenicSpotSelectiveQO qo) {
		
		if(qo!=null){
			
			if (StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if(StringUtils.isNotBlank(qo.getScenicSpotID())){
				criteria.add(Restrictions.eq("scenicSpotID", qo.getScenicSpotID()));
			}
			if(qo.getSort()!=0){
				criteria.add(Restrictions.eq("sort", qo.getSort()));
			}
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
			}
			
			if(StringUtils.isNotBlank(qo.getType())){
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
			//注册时间 两个都有值查询区间,否则查询当天
			if(StringUtils.isNotBlank(qo.getBeginTime())&&StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}else if(StringUtils.isNotBlank(qo.getBeginTime())){
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getBeginTime())));
			}else if(StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getEndTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
		}
		criteria.addOrder(Order.desc("sort"));
		return criteria;
	}

	@Override
	protected Class<ScenicSpotSelective> getEntityClass() {
		return ScenicSpotSelective.class;
	}
	
	public int maxProperty(String propertyName, ScenicSpotSelectiveQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}

}
