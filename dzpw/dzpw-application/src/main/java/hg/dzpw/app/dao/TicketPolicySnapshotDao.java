package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.TicketPolicySnapshotQo;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：门票政策快照Dao
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-14下午3:32:28
 * @版本：V1.0
 */
@Repository
public class TicketPolicySnapshotDao extends BaseDao<TicketPolicySnapshot, TicketPolicySnapshotQo>{
	
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	@Override
	protected Class<TicketPolicySnapshot> getEntityClass() {
		return TicketPolicySnapshot.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria,
			TicketPolicySnapshotQo qo) {
		if(qo!=null)
		{
			if(StringUtils.isNotBlank(qo.getId()))
			{
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if(!StringUtils.isBlank(qo.getName()))
			{
				criteria.add(Restrictions.like("baseInfo.name", qo.getName(), MatchMode.ANYWHERE));
			}
			
			if(qo.getType()!=null)
			{
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
			if(qo.getFixedUseDateStart()!=null)
			{
				criteria.add(Restrictions.ge("useInfo.fixedUseDateStart", qo.getFixedUseDateStart()));
			}
			if(qo.getFixedUseDateEnd()!=null)
			{
				criteria.add(Restrictions.le("useInfo.fixedUseDateEnd", qo.getFixedUseDateEnd()));
			}
			if(qo.getScenicSpotQo()!=null)
			{
				Criteria scenicSpotCriteria = criteria.createCriteria("scenicSpot", qo.getScenicSpotQo().getAlias(),JoinType.LEFT_OUTER_JOIN);
				scenicSpotDao.buildCriteriaOut(scenicSpotCriteria, qo.getScenicSpotQo());
			}
		}
		return criteria;
	}


}