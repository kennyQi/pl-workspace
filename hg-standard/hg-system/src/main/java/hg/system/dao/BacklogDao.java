package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.backlog.Backlog;
import hg.system.qo.BacklogQo;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BacklogDao extends BaseDao<Backlog, BacklogQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BacklogQo qo) {
		if(qo != null){
			//类型
			if(StringUtils.isNotBlank(qo.getType())){
				criteria.add(Restrictions.eq("backlogInfo.type", qo.getType()));
			}
			//时间排序
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("backlogInfo.createDate"):Order.desc("backlogInfo.createDate"));
			}
			//执行是否成功
			if(qo.getSuccess() != null){
				criteria.add(Restrictions.eq("backlogStatus.success", qo.getSuccess()));
			}
			//待办事项描述
			if(StringUtils.isNotBlank(qo.getDescription())){
				criteria.add(qo.getDescriptionLike()?Restrictions.like("backlogInfo.description", qo.getDescription(),MatchMode.ANYWHERE):
					Restrictions.eq("backlogInfo.description", qo.getDescription()));
			}
			
			//执行次数小于限制执行次数
			if(qo.getExecuteCountValid()){
				criteria.add(Restrictions.ltProperty("backlogStatus.executeCount", "backlogInfo.executeNum"));
			}
			
		}
		return criteria;
	}

	@Override
	protected Class<Backlog> getEntityClass() {
		// TODO Auto-generated method stub
		return Backlog.class;
	}

}
