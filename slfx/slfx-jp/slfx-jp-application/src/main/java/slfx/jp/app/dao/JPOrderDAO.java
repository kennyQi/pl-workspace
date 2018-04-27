package slfx.jp.app.dao;


import hg.common.component.BaseDao;
import hg.common.page.Pagination;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.domain.model.order.Passenger;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.qo.admin.PlatformOrderQO;

/**
 * 
 * @类功能说明：平台订单DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:41:04
 * @版本：V1.0
 *
 */
@Repository
public class JPOrderDAO extends BaseDao<JPOrder, PlatformOrderQO> {
	
	@Autowired
	private PassengerDAO passengerDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, PlatformOrderQO qo) {
		
		if (qo != null) {
			
			criteria.setFetchMode("comparePrice", qo.getComparePriceLazy()?FetchMode.SELECT: FetchMode.JOIN);
//			criteria.setFetchMode("passangerList", FetchMode.JOIN);
//			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			// ------------------排序条件------------------
			
			if(StringUtils.isNotBlank(qo.getPassenger())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Passenger.class, "p");
				detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("p.jpOrder.id"));
				detachedCriteria.setProjection(Projections.property("id"));
				detachedCriteria.add(Restrictions.eq("name", qo.getPassenger()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}
			if(qo.getCreateDateAsc()!=null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
			
			//易购订单号
			if(qo.getYgOrderNo()!=null&&qo.getYgOrderNo().length()!=0){
				criteria.add(Restrictions.like("ygOrderNo", qo.getYgOrderNo(), MatchMode.ANYWHERE));
			}
			//平台订单号
			if(qo.getOrderNo()!=null&&qo.getOrderNo().length()!=0){
				criteria.add(Restrictions.eq("orderNo", qo.getOrderNo()));
			}
			
			//经销商代码
			if(qo.getDealerCode()!=null){
				criteria.add(Restrictions.eq("dealerCode", qo.getDealerCode()));
			}
			
			//经销商订单编号
			if(StringUtils.isNotBlank(qo.getDealerOrderCode())){
				criteria.add(Restrictions.eq("dealerOrderCode", qo.getDealerOrderCode()));
			}
			
			//供应商订单号
			if(qo.getSupplierOrderNo()!=null&&qo.getSupplierOrderNo().length()!=0){
				criteria.add(Restrictions.eq("supplierOrderNo", qo.getSupplierOrderNo()));
			}
			//供应商
			if(qo.getSuppShortName()!=null&&qo.getSuppShortName().length()!=0){
				criteria.add(Restrictions.eq("suppShortName", qo.getSuppShortName()));
			}
			//下单人
			if(qo.getUserId()!=null&&qo.getUserId().length()!=0){
				criteria.add(Restrictions.like("userId", qo.getUserId(), MatchMode.ANYWHERE));
			}
			
			// 下单登录名
			if(StringUtils.isNotBlank(qo.getLoginName())) {
				criteria.add(Restrictions.like("loginName", qo.getLoginName(), MatchMode.ANYWHERE));
			}
			
			if(qo.getCreateDateFrom()!=null){
				criteria.add(Restrictions.ge("createDate", qo.getCreateDateFrom()));
			}
			
			if(qo.getCreateDateTo()!=null){
				criteria.add(Restrictions.le("createDate", qo.getCreateDateTo()));
			}
			
			if(StringUtils.isNotBlank(qo.getStartDepartureTime())){
				criteria.add(Restrictions.ge("departureTime", qo.getStartDepartureTime()));
			}
			
			if(StringUtils.isNotBlank(qo.getEndDepartureTime())){
				criteria.add(Restrictions.le("departureTime", qo.getEndDepartureTime()));
			}
			if(StringUtils.isNotBlank(qo.getFlightNo())){
				criteria.add(Restrictions.eq("flightNo", qo.getFlightNo()));
			}
			
			if(qo.getFromCityCode()!=null&&qo.getFromCityCode().length()!=0){
				criteria.add(Restrictions.eq("startCityCode", qo.getFromCityCode()));
			}
			if(qo.getToCityCode()!=null&&qo.getToCityCode().length()!=0){
				criteria.add(Restrictions.eq("endCityCode", qo.getToCityCode()));
			}
			
			
			if(qo.getPnr()!=null&&qo.getPnr().length()!=0){
				criteria.add(Restrictions.eq("pnr", qo.getPnr()));
			}
			if(qo.getStatus()!=null&&qo.getStatus().length!=0){
				criteria.add(Restrictions.in("orderStatus.status", qo.getStatus()));
			}
			if(qo.getPayStatus()!=null){
				criteria.add(Restrictions.eq("orderStatus.payStatus", qo.getPayStatus()));
			}
			//过滤取消的订单
			if (qo.getIsFilterCancel()) {
				criteria.add(Restrictions.ne("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_CANCEL)));
			}
			//只显示支付成功的订单
			if(qo.isJustDisPaySucc()){
				Restrictions.not(
						Restrictions.or(
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_DEALING)),
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.SLFX_PAY_WAIT)),
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_FAIL)),
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_CANCEL))
						)
					);
			}
			
			if(qo.getType()!=null&&qo.getType().length()!=0){
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
						
			if(qo.getAirCompName()!=null&&qo.getAirCompName().length()!=0){
				criteria.add(Restrictions.like("airCompName", qo.getAirCompName(), MatchMode.ANYWHERE));
			}
			
			if(qo.getPassengerQO() != null && StringUtils.isNotBlank(qo.getPassengerQO().getName())) {
				criteria.createAlias("passangerList", "p");
				criteria.add(Restrictions.eq("p.name", qo.getPassengerQO().getName()));
			}
			
			if(qo.getPassengerQO() != null && StringUtils.isNotBlank(qo.getPassengerQO().getCardNo())) {
				criteria.createAlias("passangerList", "p");
				criteria.add(Restrictions.eq("p.cardNo", qo.getPassengerQO().getCardNo()));
			}
			
			if (StringUtils.isNotBlank(qo.getPayTradeNo())) {
				criteria.add(Restrictions.eq("payTradeNo", qo.getPayTradeNo()));
			}
			
			if(null != qo.getIsPay()){//该订单是否支付
				if (qo.getIsPay()) {
					criteria.add(Restrictions.isNotNull("customerPaySerialNumber"));
				}
			}
		}
		return criteria;
	}

	@Override
	protected Class<JPOrder> getEntityClass() {
		return JPOrder.class;
	}

	@Override
	public JPOrder queryUnique(PlatformOrderQO qo) {
		JPOrder jpOrder = super.queryUnique(qo);
		if (jpOrder != null && jpOrder.getPassangerList() != null) {
			Hibernate.initialize(jpOrder.getPassangerList());
		}
		return jpOrder;
	}

//	@Override
//	public List<JPOrder> queryList(PlatformOrderQO qo, Integer offset, Integer maxSize) {
//		List<JPOrder> list = super.queryList(qo, offset, maxSize);
//		return list;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = super.queryPagination(pagination);
		List<JPOrder> list = (List<JPOrder>) pagination2.getList();
		for(JPOrder jpOrder:list){
			Hibernate.initialize(jpOrder.getPassangerList());
		}
		return pagination2;
	}
	
	
}
