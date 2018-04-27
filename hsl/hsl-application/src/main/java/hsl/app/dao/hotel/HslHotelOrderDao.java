package hsl.app.dao.hotel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hsl.domain.model.hotel.order.HotelOrder;
import hsl.pojo.qo.hotel.HotelOrderQO;

@Repository
public class HslHotelOrderDao extends BaseDao<HotelOrder, HotelOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HotelOrderQO qo) {
		
		if(qo!=null){
			
			/**
			 * 经销商订单号
			 */
			if(StringUtils.isNotBlank(qo.getDealerOrderNo())){
				criteria.add(Restrictions.eq("dealerOrderNo",qo.getDealerOrderNo()));
			}
			
			/**
			 * 酒店名称
			 */
			if(StringUtils.isNotBlank(qo.getHotelName())){
				criteria.add(Restrictions.like("hotelName",qo.getHotelName(),MatchMode.ANYWHERE));
						
			}
			/**
			 * 订单状态
			 */
			if(StringUtils.isNotBlank(qo.getShowStatus())){
				criteria.add(Restrictions.eq("showStatus",Long.parseLong(qo.getShowStatus())));
			}
			
			//如果有两个值则查询范围，否则查询当天订单
			if (StringUtils.isNotBlank(qo.getBeginDateTime()) && StringUtils.isNotBlank(qo.getEndDateTime())) {
				criteria.add(Restrictions.between("creationDate", DateUtil.dateStr2BeginDate(qo.getBeginDateTime()), DateUtil.dateStr2EndDate(qo.getEndDateTime())));
			} else if (StringUtils.isNotBlank(qo.getBeginDateTime())) {
				criteria.add(Restrictions.between("creationDate", DateUtil.dateStr2BeginDate(qo.getBeginDateTime()),DateUtil.dateStr2EndDate(qo.getBeginDateTime())));
			} else if (StringUtils.isNotBlank(qo.getEndDateTime())) {
				criteria.add(Restrictions.between("creationDate", DateUtil.dateStr2BeginDate(qo.getEndDateTime()),DateUtil.dateStr2EndDate(qo.getEndDateTime())));
			}
			
			if(StringUtils.isNotBlank(qo.getUserId())){
				criteria.add(Restrictions.eq("hotelOrderUser.userId",qo.getUserId()));
			}
			
		}
		
		criteria.addOrder(Order.desc("creationDate"));
		return criteria;
	}

	@Override
	protected Class<HotelOrder> getEntityClass() {
		return HotelOrder.class;
	}

}
