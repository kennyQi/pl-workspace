package lxs.app.dao.line;


import hg.common.component.BaseDao;

import java.util.List;

import lxs.domain.model.line.LineActivity;
import lxs.pojo.qo.line.LineActivityQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LxsLineActivityDAO extends BaseDao<LineActivity, LineActivityQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineActivityQO qo) {
		Criteria criteria2=criteria.createCriteria("line");
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			if(StringUtils.isNotBlank(qo.getActivityType())){
				criteria.add(Restrictions.eq("activityType", qo.getActivityType()));
				if(StringUtils.equals(qo.getActivityType(), "2")){
					//查询人数活动，结果按照线路名称，游玩人数排序
					Criteria lineCriteria= criteria.createCriteria("line");
					lineCriteria.addOrder(Order.asc("baseInfo.name"));
					criteria.addOrder(Order.asc("travlerNO"));
				}else{
					//查询最低价格活动
					Criteria lineCriteria= criteria.createCriteria("line");
					lineCriteria.add(Restrictions.eq("forSale", 1));
					if(StringUtils.isNotBlank(qo.getStartingCity())){
						//查询最低价格活动，按照出发地查询
						lineCriteria.add(Restrictions.eq("baseInfo.starting", qo.getStartingCity()));
					}
					if(qo.getStartingProvince()!=null&&qo.getStartingProvince().size()!=0){
						lineCriteria.add(Restrictions.in("baseInfo.starting", qo.getStartingProvince()));
					}
					criteria.addOrder(Order.desc("createDate"));
				}
			}
		}
		criteria2.add(Restrictions.eq("forSale", 1));
		return criteria;
	}

	@Override
	protected Class<LineActivity> getEntityClass() {
		return LineActivity.class;
	}
	@Override
	public List<LineActivity> queryList(LineActivityQO activityQO) {
		List<LineActivity> lineActivities = super.queryList(activityQO);
		for (LineActivity l : lineActivities) {
			Hibernate.initialize(l.getLine());
		}
		return lineActivities;
	}

}
