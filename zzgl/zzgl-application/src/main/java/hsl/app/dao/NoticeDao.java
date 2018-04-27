package hsl.app.dao;
import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hsl.domain.model.notice.Notice;
import hsl.pojo.qo.notice.HslNoticeQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class NoticeDao extends BaseDao<Notice, HslNoticeQO>{
	@Override
	protected Criteria buildCriteria(Criteria criteria, HslNoticeQO qo) {
		if(null!=qo){
			//根据公告的标题的查询
			if(StringUtils.isNotBlank(qo.getTitle())){
				criteria.add(Restrictions.like("baseInfo.title", qo.getTitle(),MatchMode.ANYWHERE));
			}
			//根据开始时间和结束时间查询截至时间
			if (StringUtils.isNotBlank(qo.getBeginTime()) && StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("baseInfo.createTime", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			} else if (StringUtils.isNotBlank(qo.getBeginTime())) {
				criteria.add(Restrictions.between("baseInfo.createTime", DateUtil.dateStr2BeginDate(qo.getBeginTime()),DateUtil.dateStr2EndDate(qo.getBeginTime())));
			} else if (StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("baseInfo.createTime", DateUtil.dateStr2BeginDate(qo.getEndTime()),DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			//根据截至日期查询
			if (StringUtils.isNotBlank(qo.getCutOffTime())) {
				criteria.add(Restrictions.ge("baseInfo.cutOffTime", DateUtil.parseDateTime(qo.getCutOffTime())));
			}
			//查询是否审核
			if(qo.getCheckedStatus()!=null){
				criteria.add(Restrictions.eq("status.checkedStatus",qo.getCheckedStatus()));
			}
		}
		criteria.addOrder(Order.desc("baseInfo.createTime"));
		return criteria;
	}
	@Override
	protected Class<Notice> getEntityClass() {
		return Notice.class;
	}
}
