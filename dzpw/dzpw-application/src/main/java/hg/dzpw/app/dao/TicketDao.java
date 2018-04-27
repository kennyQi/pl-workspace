package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.pojo.qo.TicketQo;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.domain.model.ticket.Ticket;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明: 联票订单Dao
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 下午5:45:57 
 * @版本：V1.0
 */
@Repository
public class TicketDao extends BaseDao<Ticket,TicketQo> {
	
	@Autowired
	private TicketPolicySnapshotDao ticketPolicySnapshotDao;
	@Override
	protected Class<Ticket> getEntityClass() {
		return Ticket.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, TicketQo qo) {
		if(null == qo)
			return criteria;
		
		//票号
		if(StringUtils.isNotBlank(qo.getTicketNo()))
			if(qo.isTicketNoLike())
				criteria.add(Restrictions.like("ticketNo",qo.getTicketNo(),MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("ticketNo",qo.getTicketNo()));
		//入园时间
		if(null != qo.getFirstTimeUseDate())
			criteria.add(Restrictions.eq("firstTimeUseDate",qo.getFirstTimeUseDate()));
		//有效期
		if(null != qo.getUseDateStart())
			criteria.add(Restrictions.ge("useDateStart",qo.getUseDateStart()));
		
		if(null != qo.getUseDateEnd())
			criteria.add(Restrictions.le("useDateEnd",qo.getUseDateEnd()));
		
		//查询结束时间等于
		if(null!=qo.getEqUseDateEnd())
			criteria.add(Restrictions.eq("useDateEnd",qo.getUseDateEnd()));
		
		//实际价格
		if(null != qo.getPrice())
			criteria.add(Restrictions.eq("price",qo.getPrice()));
		Criteria criteria2 = null;
		//门票政策
		if(null != qo.getPolicyQo()){
			TicketPolicyQo policyQo = qo.getPolicyQo(); 
			criteria2 = criteria.createCriteria("ticketPolicy",JoinType.LEFT_OUTER_JOIN);
			
			if (StringUtils.isNotBlank(policyQo.getId()))
				criteria2.add(Restrictions.eq("id", policyQo.getId()));
			
			//联票名称
			if(StringUtils.isNotBlank(policyQo.getTicketPolicyName())){
				if(policyQo.getTicketPolicyNameLike())
					criteria2.add(Restrictions.like("baseInfo.name",policyQo.getTicketPolicyName(),MatchMode.ANYWHERE));
				else
					criteria2.add(Restrictions.eq("baseInfo.name",policyQo.getTicketPolicyName()));
			}
			// 景区
			if (policyQo.getScenicSpotQo()!= null) {
				// Criteria webPortalCriteria = 
				criteria2.createCriteria("scenicSpot", policyQo.getScenicSpotQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			}
			//状态
			if(policyQo.getStatus()!=null)
				criteria2.add(Restrictions.ge("status.current",policyQo.getStatus()));
			
		}
		//快照
		if(null!=qo.getTicketPolicySnapshotQo())
		{
			Criteria tpsCriteria = criteria.createCriteria("ticketPolicySnapshot", qo.getTicketPolicySnapshotQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			ticketPolicySnapshotDao.buildCriteriaOut(tpsCriteria, qo.getTicketPolicySnapshotQo());
		}
		//游客
		if(null != qo.getTourQo()){
			TouristQo tourQo = qo.getTourQo();
			criteria2 = criteria.createCriteria("tourist",JoinType.LEFT_OUTER_JOIN);
			
			//姓名是否模糊查询
			if(StringUtils.isNotBlank(tourQo.getName())){
				if(tourQo.getNameLike())
					criteria2.add(Restrictions.like("name",tourQo.getName(),MatchMode.ANYWHERE));
				else
					criteria2.add(Restrictions.eq("name",tourQo.getName()));
			}
			//证件类型
			if(null != tourQo.getIdType())
				criteria2.add(Restrictions.eq("idType",tourQo.getIdType()));
			
			//年龄查询
			if(null != tourQo.getAgeStart())
				criteria2.add(Restrictions.ge("age",tourQo.getAgeStart()));
			
			if(null != tourQo.getAgeEnd())
				criteria2.add(Restrictions.le("age",tourQo.getAgeEnd()));
			
			if(StringUtils.isNotBlank(tourQo.getIdNo())){
				//证件号是否模糊查询
				if(tourQo.getIdNoLike())
					criteria2.add(Restrictions.like("idNo",tourQo.getIdNo(),MatchMode.ANYWHERE));
				else
					criteria2.add(Restrictions.eq("idNo",tourQo.getIdNo()));
			}
			
			//性别查询
			if(null != tourQo.getGender())
				criteria2.add(Restrictions.eq("gender",tourQo.getGender()));
			//首次购买查询
			if(null != tourQo.getFirstBuyDateStart())
				criteria2.add(Restrictions.ge("firstBuyDate",tourQo.getFirstBuyDateStart()));
			if(null != tourQo.getFirstBuyDateEnd())
				criteria2.add(Restrictions.le("firstBuyDate",tourQo.getFirstBuyDateEnd()));
			//首次购买时间排序
			if(null != tourQo.getFirstBuyDateSort() && tourQo.getFirstBuyDateSort()>0){
				criteria2.addOrder(Order.asc("firstBuyDate"));
			}
			if(null != tourQo.getFirstBuyDateSort() && tourQo.getFirstBuyDateSort()<0){
				criteria2.addOrder(Order.desc("firstBuyDate"));
			}
		}
		return criteria;
	}
}