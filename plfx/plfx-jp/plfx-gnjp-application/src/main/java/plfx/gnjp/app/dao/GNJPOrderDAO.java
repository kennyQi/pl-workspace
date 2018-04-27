package plfx.gnjp.app.dao;


import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import plfx.gnjp.domain.model.order.GNJPOrder;
import plfx.gnjp.domain.model.order.GNJPPassenger;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.qo.admin.PlatformOrderQO;

/**
 * 
 * @类功能说明：平台订单DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午5:56:03
 * @版本：V1.0
 *
 */
@Repository
public class GNJPOrderDAO extends BaseDao<GNJPOrder, PlatformOrderQO> {
	
	@Autowired
	private PassengerDAO passengerDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, PlatformOrderQO qo) {
		
		if (qo != null) {
			//乘机人查询
			if(StringUtils.isNotBlank(qo.getPassengerName())) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GNJPPassenger.class, "p");
				detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("p.jpOrder.id"));
				detachedCriteria.setProjection(Projections.property("id"));
				detachedCriteria.add(Restrictions.eq("name", qo.getPassengerName()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}
			//按时间排序查询
			if(qo.getCreateDateAsc()!=null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createTime"):Order.desc("createTime"));
			}
			
			//易行天下订单号
			if(qo.getYeeXingOrderId()!=null&&qo.getYeeXingOrderId().length()!=0){
				criteria.add(Restrictions.eq("yeeXingOrderId", qo.getYeeXingOrderId()));
			}
			//平台订单号
			if(qo.getOrderNo()!=null&&qo.getOrderNo().length()!=0){
				criteria.add(Restrictions.eq("orderNo", qo.getOrderNo()));
			}
			//订单类型 0-正常 1-异常 
			if(StringUtils.isNotBlank(qo.getType())){
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
			//是否是退废票 或者取消的订单记录
			if(StringUtils.isNotBlank(qo.getRefundType()) && (qo.getRefundType().equals("T") || qo.getRefundType().equals("C"))){
				criteria.add(Restrictions.eq("refundType", qo.getRefundType()));
			}
			//排除取消和退废票的订单
			if(StringUtils.isNotBlank(qo.getRefundType()) && qo.getRefundType().equals("A")){
				criteria.add(Restrictions.isNull("refundType"));
			}
			
			
			//经销商订单编号
			if(StringUtils.isNotBlank(qo.getDealerOrderCode())){
				criteria.add(Restrictions.eq("dealerOrderCode", qo.getDealerOrderCode()));
			}
			
            //下单开始时间
			if(qo.getCreateDateFrom()!=null){
				criteria.add(Restrictions.ge("createTime", DateUtil.formatDate(qo.getCreateDateFrom())));
			}
			
			//下单结束时间
			if(qo.getCreateDateTo()!=null){
				criteria.add(Restrictions.le("createTime", DateUtil.formatDate(qo.getCreateDateTo())));
			}
			
			//航班查询
			if(StringUtils.isNotBlank(qo.getFlightNo())){
				criteria.add(Restrictions.eq("flightNo", qo.getFlightNo()));
			}
			
			//订单状态查询
			if(qo.getStatus()!=null&&qo.getStatus().length!=0){
				criteria.add(Restrictions.in("orderStatus.status", qo.getStatus()));
			}
			//支付状态查询
			if(qo.getPayStatus()!=null){
				criteria.add(Restrictions.eq("orderStatus.payStatus", qo.getPayStatus()));
			}
			//过滤取消的订单
			if (qo.isFilterCancel()) {
				criteria.add(Restrictions.ne("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_CANCEL)));
			}
			//只显示支付成功的订单
			if(qo.isJustDisPaySucc()){
				Restrictions.not(
						Restrictions.or(
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_DEALING)),
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.PLFX_PAY_WAIT)),
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_FAIL)),
							Restrictions.ge("orderStatus.status", Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_CANCEL))
						)
					);
			}
			
			if(qo.getPassengerQO() != null && StringUtils.isNotBlank(qo.getPassengerQO().getName())) {
				criteria.createAlias("passengerList", "p");
				criteria.add(Restrictions.eq("p.name", qo.getPassengerQO().getName()));
			}
			
			if(qo.getPassengerQO() != null && StringUtils.isNotBlank(qo.getPassengerQO().getCardNo())) {
				criteria.createAlias("passengerList", "p");
				criteria.add(Restrictions.eq("p.cardNo", qo.getPassengerQO().getCardNo()));
			}
			
			//下单人查询
			if(StringUtils.isNotBlank(qo.getLoginName())){
				criteria.add(Restrictions.eq("loginName", qo.getLoginName()));
			}
			
			//航空公司查询
			if(StringUtils.isNotBlank(qo.getAirCompName())){
				criteria.add(Restrictions.eq("airCompName", qo.getAirCompName()));
			}
			
		}
		return criteria;
	}

	@Override
	protected Class<GNJPOrder> getEntityClass() {
		return GNJPOrder.class;
	}

	@Override
	public GNJPOrder queryUnique(PlatformOrderQO qo) {
		GNJPOrder jpOrder = super.queryUnique(qo);
		if (jpOrder != null) {
			Hibernate.initialize(jpOrder.getPassengerList());
			for(GNJPPassenger passenger : jpOrder.getPassengerList()){
				Hibernate.initialize(passenger.getTicket());
			}
		}
		return jpOrder;
	}

	@Override
	public List<GNJPOrder> queryList(PlatformOrderQO qo) {
		List<GNJPOrder> list = super.queryList(qo);
		for(GNJPOrder jpOrder : list){
			if (jpOrder != null) {
				Hibernate.initialize(jpOrder.getPassengerList());
				for(GNJPPassenger passenger : jpOrder.getPassengerList()){
					Hibernate.initialize(passenger.getTicket());
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = super.queryPagination(pagination);
		List<GNJPOrder> list = (List<GNJPOrder>) pagination2.getList();
		for(GNJPOrder jpOrder:list){
			Hibernate.initialize(jpOrder.getPassengerList());
			for(GNJPPassenger passenger : jpOrder.getPassengerList()){
				Hibernate.initialize(passenger.getTicket());
			}
		}
		return pagination2;
		
	}
	
	/**
	 * 
	 * @方法功能说明：重写get加载,能懒加载出子表
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月17日下午5:35:41
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:GNJPOrder
	 * @throws
	 */
	public GNJPOrder get(String id) {
		GNJPOrder jpOrder = super.get(id);
		if (jpOrder != null) {
			Hibernate.initialize(jpOrder.getPassengerList());
			for(GNJPPassenger passenger : jpOrder.getPassengerList()){
				Hibernate.initialize(passenger.getTicket());
			}
		}
		return jpOrder;
	}
	
}
