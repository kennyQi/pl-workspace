package hsl.app.dao.jp;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hsl.domain.model.jp.FlightOrder;
import hsl.domain.model.jp.Passenger;
import hsl.pojo.qo.jp.FlightOrderQO;
@Repository
public class FlightOrderDAO extends BaseDao<FlightOrder, FlightOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, FlightOrderQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getUserID())){
				criteria.add(Restrictions.eq("jpOrderUser.userId", qo.getUserID()));
			}
			if (StringUtils.isNotBlank(qo.getBeginDateTime()) && StringUtils.isNotBlank(qo.getEndDateTime())) {
				criteria.add(Restrictions.between("createTime", DateUtil.dateStr2BeginDate(qo.getBeginDateTime()), DateUtil.dateStr2EndDate(qo.getEndDateTime())));
			} else if (StringUtils.isNotBlank(qo.getBeginDateTime())) {
				criteria.add(Restrictions.ge("createTime", DateUtil.dateStr2BeginDate(qo.getBeginDateTime())));
			} else if (StringUtils.isNotBlank(qo.getEndDateTime())) {
				criteria.add(Restrictions.lt("createTime", DateUtil.dateStr2EndDate(qo.getEndDateTime())));
			}
			if(StringUtils.isNotBlank(qo.getOrderNO())){
				criteria.add(Restrictions.eq("orderNO", qo.getOrderNO()));
			}
			if(StringUtils.isNotBlank(qo.getLoginName())){
				criteria.add(Restrictions.eq("jpOrderUser.loginName", qo.getLoginName()));//下单用户
			}
			if(StringUtils.isNotBlank(qo.getActName())){
				criteria.createAlias("passengers", "passengers");
				criteria.add(Restrictions.ilike("passengers.passengerName", qo.getActName(),MatchMode.ANYWHERE));//乘机人
			}
			if(qo.getStatus()!=null&&qo.getStatus()>0){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			if(qo.getPayStatus()!=null&&qo.getPayStatus()>0){
				criteria.add(Restrictions.eq("payStatus", qo.getPayStatus()));
			}
			if(StringUtils.isNotBlank(qo.getFlightNo())){
				criteria.add(Restrictions.eq("flightBaseInfo.flightNO", qo.getFlightNo()));
			}

			if (qo.getPaySts() != null && qo.getPaySts().length > 0) {
				criteria.add(Restrictions.in("payStatus", qo.getPaySts()));
			}
			if (qo.getSts() != null && qo.getSts().length > 0) {
				criteria.add(Restrictions.in("status", qo.getSts()));
			}
			//			if(qo.getIsFetchPassenger()){
			//				criteria.setFetchMode("passengers", FetchMode.JOIN);
			//			}
			if(StringUtils.isNotBlank(qo.getIdNo())){
				criteria.createAlias("passengers", "p");
				criteria.add(Restrictions.eq("p.idNo",qo.getIdNo()));
			}
			if(StringUtils.isNotBlank(qo.getPayTradeNo())){
				criteria.add(Restrictions.eq("payTradeNo",qo.getPayTradeNo()));
			}
			if (StringUtils.isNotBlank(qo.getMemeberId())) {
				criteria.createAlias("passengers", "passengers");
				criteria.add(Restrictions.like("passengers.memeberId", qo.getMemeberId()));
			}

			if (qo.getStartTime()!=null && qo.getEndTime()!=null) {

				criteria.add(Restrictions.between("flightBaseInfo.startTime", qo.getStartTime(), qo.getEndTime()));
			} else if (qo.getStartTime()!=null) {
				criteria.add(Restrictions.gt("flightBaseInfo.startTime", qo.getStartTime()));
			} else if (qo.getEndTime()!=null) {
				criteria.add(Restrictions.lt("flightBaseInfo.startTime", qo.getEndTime()));
			}
			if(qo.getStartDate()!=null){
				criteria.add(Restrictions.eq("flightBaseInfo.startTime",qo.getStartDate()));
			}

			
			
			//卡券使用订单查询，订单类型为 正常订单或记录订单
			if(qo.getIsCouponOrderQuery()){
				criteria.add(Restrictions.in("orderType",new String[]{"1","4"}));
			}else{
				if(StringUtils.isNotBlank(qo.getOrderType())){
					criteria.add(Restrictions.eq("orderType",qo.getOrderType()));
				}else{
					criteria.add(Restrictions.in("orderType",new String[]{"1","2","3"}));
				}
			}
			
			criteria.addOrder(Order.desc("createTime"));

		}
		return criteria;
	}

	@Override
	protected Class<FlightOrder> getEntityClass() {
		return FlightOrder.class;
	}

	@Override
	public FlightOrder queryUnique(FlightOrderQO qo) {
		FlightOrder flightOrder=super.queryUnique(qo);
		if(flightOrder!=null){
			for(Passenger passenger:flightOrder.getPassengers()){
				Hibernate.initialize(passenger);
			}
		}
		return flightOrder;
	}

	@Override
	public List<FlightOrder> queryList(FlightOrderQO qo) {
		List<FlightOrder> jpOrders=super.queryList(qo);
		for(FlightOrder jpOrder:jpOrders){
			for(Passenger passenger:jpOrder.getPassengers()){
				Hibernate.initialize(passenger);
			}
		}
		return jpOrders;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2=super.queryPagination(pagination);
		@SuppressWarnings("unchecked")
		List<FlightOrder> jpOrders=(List<FlightOrder>) pagination2.getList();
		for(FlightOrder jpOrder:jpOrders){
			for(Passenger passenger:jpOrder.getPassengers()){
				Hibernate.initialize(passenger);
			}
		}
		return pagination2;
	}

}
