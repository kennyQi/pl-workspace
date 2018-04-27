package zzpl.app.dao.jp;

import hg.common.component.BaseDao;
import hg.common.util.SysProperties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.order.FlightOrder;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.qo.jp.FlightOrderQO;

@Repository
public class FlightOrderDAO extends BaseDao<FlightOrder, FlightOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, FlightOrderQO qo) {
		if(qo!=null){
			if (qo.getId()!=null&&StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
				return criteria;
			}
			if(qo.getUserID()!=null&&StringUtils.isNotBlank(qo.getUserID())){
				criteria.add(Restrictions.eq("userID", qo.getUserID()));
			}
			if(qo.getOrderNO()!=null&&StringUtils.isNotBlank(qo.getOrderNO())){
				criteria.add(Restrictions.eq("orderNO", qo.getOrderNO()));
			}
			if(qo.getUnionOrderNO()!=null&&StringUtils.isNotBlank(qo.getUnionOrderNO())){
				criteria.add(Restrictions.eq("unionOrderNO", qo.getUnionOrderNO()));
			}
			if(qo.getTradeno()!=null&&StringUtils.isNotBlank(qo.getTradeno())){
				criteria.add(Restrictions.eq("trade_no", qo.getTradeno()));
			}
			if(qo.getStatus()!=null){
				
				if (qo.getStatus() == FlightOrderStatus.CONFIRM_ORDER_SUCCESS) {
					Integer[] status = {FlightOrderStatus.CONFIRM_ORDER_SUCCESS,FlightOrderStatus.APPROVAL_SUCCESS,FlightOrderStatus.GET_POLICY_SUCCESS};
					criteria.add(Restrictions.in("status", status));
				}else if (qo.getStatus() == FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS) {
					Integer[] status = {FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS,FlightOrderStatus.CANCEL_APPROVAL_SUCCESS};
					criteria.add(Restrictions.in("status", status));
				}else {
					criteria.add(Restrictions.eq("status", qo.getStatus()));
				}
			}
			
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getCompanyID())){
				if (qo.getRoleID()!=null&&StringUtils.isNotBlank(qo.getRoleID()) && qo.getRoleID().contains(SysProperties.getInstance().get("travleAdminID"))) {
					String[] companys = qo.getCompanyID().split(",");
					criteria.add(Restrictions.in("companyID", companys));
				}else {
					criteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
				}
			}
			
			if (qo.getFlightNO() != null && StringUtils.isNotBlank(qo.getFlightNO())) {
				criteria.add(Restrictions.eq("flightNO", qo.getFlightNO()));
			}
			
			if (qo.getReplacePerson() != null && StringUtils.isNotBlank(qo.getReplacePerson())) {
				criteria.add(Restrictions.eq("replacePerson", qo.getReplacePerson()));
			}
			
			if ((qo.getPassengerName() != null && StringUtils.isNotBlank(qo.getPassengerName())) || (qo.getAirID() != null && StringUtils.isNotBlank(qo.getAirID()))) {
				Criteria passengerCriteria = criteria.createCriteria("passengers");
				if (qo.getPassengerName() != null && StringUtils.isNotBlank(qo.getPassengerName())) {
					passengerCriteria.add(Restrictions.like("passengerName", qo.getPassengerName(),MatchMode.ANYWHERE));
				}
				if (qo.getAirID() != null && StringUtils.isNotBlank(qo.getAirID())) {
					passengerCriteria.add(Restrictions.like("airID", qo.getAirID(),MatchMode.ANYWHERE));
				}
			}
			
			if (qo.getStartCreateTime() != null ) {
				criteria.add(Restrictions.ge("createTime", qo.getStartCreateTime()));
			}
			if (qo.getEndCreateTime() != null) {
				criteria.add(Restrictions.le("createTime", qo.getEndCreateTime()));
			}
			
			if (qo.getStartTime() != null ) {
				criteria.add(Restrictions.ge("startTime", qo.getStartTime()));
			}
			if (qo.getEndTime() != null) {
				criteria.add(Restrictions.le("startTime", qo.getEndTime()));
			}
			if(qo.getOrderByCreatTime()!=null&&StringUtils.isNotBlank(qo.getOrderByCreatTime())){
				criteria.addOrder(Order.desc("createTime"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<FlightOrder> getEntityClass() {
		return FlightOrder.class;
	}
	
	@Override
	public FlightOrder queryUnique(FlightOrderQO qo) {
		FlightOrder flightOrder = super.queryUnique(qo);
		if (flightOrder!=null) {
			Hibernate.initialize(flightOrder.getPassengers());
		}
		return flightOrder;
	}

}
