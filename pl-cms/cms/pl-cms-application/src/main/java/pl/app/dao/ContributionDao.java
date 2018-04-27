package pl.app.dao;
import hg.common.component.BaseDao;
import hg.common.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pl.cms.domain.entity.contribution.Contribution;
import pl.cms.pojo.qo.ContributionQO;
@Repository
public class ContributionDao extends BaseDao<Contribution, ContributionQO> {

	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ContributionQO qo) {
		
		if (qo != null) {
			criteria.addOrder(Order.desc("createDate"));
			if(StringUtils.isNotBlank(qo.getTitle())){
				if (qo.getTitleLike()) {
					criteria.add(Restrictions.like("baseInfo.title", qo.getTitle(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("baseInfo.title",qo.getTitle()));
				}
			}
			if(StringUtils.isNotBlank(qo.getAuthor())){
				if (qo.getTitleLike()) {
					criteria.add(Restrictions.like("baseInfo.author", qo.getAuthor(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("baseInfo.author",qo.getAuthor()));
				}
			}
			if(qo.getCheckStatus()!=null){
					criteria.add(Restrictions.eq("checkStatus",qo.getCheckStatus()));
			}
			//如果有两个值则查询范围，否则查询当天订单
			if (StringUtils.isNotBlank(qo.getBeginTime()) && StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			} else if (StringUtils.isNotBlank(qo.getBeginTime())) {
				criteria.add(Restrictions.ge("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime())));
			} else if (StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.le("createDate",  DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
		}
		
		return criteria;
	}

	@Override
	protected Class<Contribution> getEntityClass() {
		return Contribution.class;
	}
	
}
