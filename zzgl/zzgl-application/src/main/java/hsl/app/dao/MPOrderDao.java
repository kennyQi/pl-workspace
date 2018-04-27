package hsl.app.dao;
import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hsl.domain.model.mp.order.MPOrder;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.pojo.qo.mp.HslMPOrderUserQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class MPOrderDao extends BaseDao<MPOrder, HslMPOrderQO>{

	@Autowired
	private MPOrderUserDao orderUserDao;
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, HslMPOrderQO qo) {
		
		if(qo!=null){
			
			Criteria orderUserC = criteria.createCriteria("orderUser", JoinType.LEFT_OUTER_JOIN);
			Criteria takeTicketUserC = criteria.createCriteria("takeTicketUser", JoinType.LEFT_OUTER_JOIN);
			Criteria scenicSpotC= null;
			
			//单个订单号
			if(StringUtils.isNotBlank(qo.getDealerOrderCode())){
				criteria.add(Restrictions.eq("id", qo.getDealerOrderCode()));
			}
			
			
			if(StringUtils.isNotBlank(qo.getPlatformOrderCode())){
				criteria.add(Restrictions.eq("platformOrderCode", qo.getPlatformOrderCode()));
			}
			
			//用户
			if(StringUtils.isNotBlank(qo.getUserId())){
				HslMPOrderUserQO orderUserQO=new HslMPOrderUserQO();
				orderUserQO.setUserId(qo.getUserId());
//				orderUserQO.setOnlyMember(true);
				if(orderUserC==null){
					orderUserC = criteria.createCriteria("orderUser", JoinType.LEFT_OUTER_JOIN);
				}
				orderUserDao.buildCriteriaOut(orderUserC, orderUserQO);
//				if(qo.isShowMember())//查询成员
//					orderUserC.add(Restrictions.isNotNull("userId"));
			}
			
			//预定人
			if(StringUtils.isNotBlank(qo.getBookMan())){
				HslMPOrderUserQO hslMPOrderUserQO=new HslMPOrderUserQO();
				hslMPOrderUserQO.setName(qo.getBookMan());
				if(orderUserC==null){
					orderUserC = criteria.createCriteria("orderUser", JoinType.LEFT_OUTER_JOIN);
				}
				orderUserDao.buildCriteriaOut(orderUserC, hslMPOrderUserQO);
			}
			//成员
			if(StringUtils.isNotBlank(qo.getMemberId())){
				HslMPOrderUserQO orderUserQO=new HslMPOrderUserQO();
				orderUserQO.setUserId(qo.getMemberId());
				orderUserQO.setOnlyMember(true);
				if(takeTicketUserC==null){
					takeTicketUserC = criteria.createCriteria("takeTicketUser", JoinType.LEFT_OUTER_JOIN);
				}
				orderUserDao.buildCriteriaOut(takeTicketUserC, orderUserQO);
			}
			
			//只查询member
			if(qo.isShowMember()){
				HslMPOrderUserQO orderUserQO=new HslMPOrderUserQO();
				orderUserQO.setOnlyMember(true);
				if(takeTicketUserC==null){
					takeTicketUserC = criteria.createCriteria("takeTicketUser", JoinType.LEFT_OUTER_JOIN);
				}
				orderUserDao.buildCriteriaOut(takeTicketUserC, orderUserQO);
			}
			//是否加载政策信息
			if(qo.getWithPolicy()){
				criteria.createCriteria("mpPolicy", JoinType.LEFT_OUTER_JOIN);
			}
			//是否加载景点信息
			if(qo.getWithScenicSpot()){
				if(scenicSpotC==null){
					scenicSpotC=criteria.createCriteria("scenicSpot", JoinType.LEFT_OUTER_JOIN);
				}
				if(qo.getScenicSpotsNameLike()){
					HslMPScenicSpotQO hslMPScenicSpotQO=new HslMPScenicSpotQO();
					hslMPScenicSpotQO.setName(qo.getScenicSpotsName());
					scenicSpotDao.buildCriteriaOut(scenicSpotC, hslMPScenicSpotQO);
				}
			}
			
			// 订单状态
			if (qo.getOrderStatus() != null) {
				if (1 == qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.cancel", Boolean.TRUE));
				} else if (2 == qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.outOfDate", Boolean.TRUE));
				} else if (3== qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.prepared", Boolean.TRUE));
				} else if (4 == qo.getOrderStatus().intValue()) {
					criteria.add(Restrictions.eq("status.used", Boolean.TRUE));
				}
			}
			
			//如果有两个值则查询范围，否则查询当天订单
			if (StringUtils.isNotBlank(qo.getBeginTime()) && StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			} else if (StringUtils.isNotBlank(qo.getBeginTime())) {
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()),DateUtil.dateStr2EndDate(qo.getBeginTime())));
			} else if (StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getEndTime()),DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			
//			//游玩时间
//			if(StringUtils.isNotBlank(qo.getStartTravelTime())&&StringUtils.isNotBlank(qo.getEndTravelTime())){
//				criteria.add(Restrictions.ge("travelDate",DateUtil.dateStr2BeginDate(qo.getStartTravelTime())));
//				criteria.add(Restrictions.le("travelDate", DateUtil.dateStr2EndDate(qo.getEndTravelTime())));
//			}else{
//				//只有一个代表查询当天而不是范围
//				if(!StringUtils.isBlank(qo.getStartTravelTime())){
//					criteria.add(Restrictions.eq("travelDate", DateUtil.dateStr2BeginDate(qo.getStartTravelTime())));
//				}
//				if(!StringUtils.isBlank(qo.getEndTravelTime())){
//					criteria.add(Restrictions.eq("travelDate", DateUtil.dateStr2EndDate(qo.getEndTravelTime())));
//				}
//			}
			
			
			//游玩时间
			if(StringUtils.isNotBlank(qo.getStartTravelTime())&&StringUtils.isNotBlank(qo.getEndTravelTime())){
				criteria.add(Restrictions.ge("travelDate",qo.getStartTravelTime()));
				criteria.add(Restrictions.le("travelDate", qo.getEndTravelTime()));
			}else{
				//只有一个代表查询当天而不是范围
				if(!StringUtils.isBlank(qo.getStartTravelTime())){
					criteria.add(Restrictions.eq("travelDate", qo.getStartTravelTime()));
				}
				if(!StringUtils.isBlank(qo.getEndTravelTime())){
					criteria.add(Restrictions.eq("travelDate", qo.getEndTravelTime()));
				}
			}
			
			criteria.addOrder(Order.desc("createDate"));
			
		}
		return criteria;
	}

	@Override
	protected Class<MPOrder> getEntityClass() {
		return MPOrder.class	;
	}

//	@Override
//	public List<MPOrder> queryList(HslMPOrderQO qo, Integer offset,
//			Integer maxSize) {
//
//		List<MPOrder> list=	super.queryList(qo, offset, maxSize);
//		for(MPOrder o:list){
//			
//			Hibernate.initialize(o.getScenicSpot().getImages());
//			
//		}
//		return list;
//	}
//
//	@Override
//	public Pagination queryPagination(Pagination pagination) {
//		// TODO Auto-generated method stub
//		return super.queryPagination(pagination);
//	}

}
