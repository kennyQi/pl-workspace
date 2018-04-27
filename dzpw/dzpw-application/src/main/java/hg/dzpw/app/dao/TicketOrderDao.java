package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.pojo.qo.TicketOrderListQo;
import hg.dzpw.pojo.vo.TicketOrderListVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
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
public class TicketOrderDao extends BaseDao<TicketOrder,TicketOrderQo> {
	
	@Autowired
	private TicketDao ticketDao;
	
//	@Autowired
//	private TicketPolicyDao ticketPolicyDao;
	
	
	@Autowired
	private TicketPolicySnapshotDao ticketPolicySnapshotDao;
	
	@Autowired
	private DealerDao dealerDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, TicketOrderQo qo) {
		if(null == qo)
			return criteria;
		//排序
		if (qo.getCreateDateSort()!=null && qo.getCreateDateSort() != 0) {
			criteria.addOrder(qo.getCreateDateSort() > 0 ? Order.asc("baseInfo.createDate") : Order.desc("baseInfo.createDate"));
		}
		//订单编号
		if(StringUtils.isNotBlank(qo.getOrderId()))
			criteria.add(Restrictions.like("id",qo.getOrderId(),MatchMode.ANYWHERE));
		//经销商订单编号
		if(StringUtils.isNotBlank(qo.getDealerOrderId()))
			criteria.add(Restrictions.like("baseInfo.dealerOrderId",qo.getDealerOrderId(),MatchMode.ANYWHERE));
		//经销商
		if(null != qo.getDealerQo())
		{
			//criteria.createCriteria("baseInfo.fromDealer",JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("id",qo.getDealerId()));
			Criteria dealerCriteria =  criteria.createCriteria("baseInfo.fromDealer", qo.getDealerQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			dealerDao.buildCriteria(dealerCriteria, qo.getDealerQo());
		}
		//经销商
		if (StringUtils.isNotBlank(qo.getDealerId())) {
			criteria.createCriteria("baseInfo.fromDealer",JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("id",qo.getDealerId()));
		}
		//下单时间
		if(null != qo.getCreateBeginDate())
			criteria.add(Restrictions.ge("baseInfo.createDate",qo.getCreateBeginDate()));
		if(null != qo.getCreateEndDate())
			criteria.add(Restrictions.le("baseInfo.createDate",qo.getCreateEndDate()));
		//订单状态
		if(null != qo.getStatus())
			criteria.add(Restrictions.eq("status.currentValue",qo.getStatus()));
		//支付状态
		if(null != qo.getPaid())
			criteria.add(Restrictions.eq("payInfo.paid",qo.getPaid()));
//		if(null != qo.getTicketQo()){
//			
//			Criteria ticketCriteria =  criteria.createCriteria("groupTickets", qo.getTicketQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
//			ticketDao.buildCriteria(ticketCriteria, qo.getTicketQo());
////			//构建离线查询，这里表示子查询
////			DetachedCriteria detach = DetachedCriteria.forClass(GroupTicket.class,"my");
////			detach.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("my.tickets.id"));
////			detach.setProjection(Projections.property("id"));
////			
////			TicketQo ticketQo = qo.getTicketQo();
////			Criteria criteria2 = (Criteria) MyBeanUtils.getFieldValue(detach,"criteria");
////			//票号
////			if(StringUtils.isNotBlank(ticketQo.getTicketNo()))
////				criteria2.add(Restrictions.like("ticketNo",ticketQo.getTicketNo(),MatchMode.ANYWHERE));
////			//游客
////			if(null != ticketQo.getTourQo()){
////				TouristQo tourQo = ticketQo.getTourQo();
////				Criteria criteria3 = criteria2.createCriteria("tourist",JoinType.LEFT_OUTER_JOIN);
////				
////				//姓名是否模糊查询
////				if(tourQo.getNameLike())
////					criteria3.add(Restrictions.like("name",tourQo.getName(),MatchMode.ANYWHERE));
////				else
////					criteria3.add(Restrictions.eq("name",tourQo.getName()));
////				//证件类型
////				if(null != tourQo.getIdType())
////					criteria3.add(Restrictions.eq("idType",tourQo.getIdType()));
////				//证件号是否模糊查询
////				if(tourQo.getIdNoLike())
////					criteria3.add(Restrictions.like("idNo",tourQo.getIdNo(),MatchMode.ANYWHERE));
////				else
////					criteria3.add(Restrictions.eq("idNo",tourQo.getIdNo()));
////			}
////			
////			//将Criteria条件添加到当前Criteria环境中
////			criteria.add(Subqueries.exists(detach));
//		}
		//预定人
		if(StringUtils.isNotBlank(qo.getLinkMan()))
		{
			criteria.add(Restrictions.like("baseInfo.linkMan",qo.getLinkMan(),MatchMode.ANYWHERE));
		}
			
		if(null!=qo.getTicketPolicySnapshotQo()){
			Criteria tpSnapshotCriteria = criteria.createCriteria("ticketPolicySnapshot", qo.getTicketPolicySnapshotQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			ticketPolicySnapshotDao.buildCriteria(tpSnapshotCriteria, qo.getTicketPolicySnapshotQo());
		}
		criteria.addOrder(Order.desc("baseInfo.createDate"));
		return criteria;
	}

	@Override
	protected Class<TicketOrder> getEntityClass() {
		return TicketOrder.class;
	}

	
	@Override
	public List<TicketOrder> queryList(TicketOrderQo qo, Integer offset,
			Integer maxSize) {
		List<TicketOrder> list = super.queryList(qo, offset, maxSize);
			for (TicketOrder o : list) {
				Hibernate.initialize(o.getTicketPolicy());
				Hibernate.initialize(o.getBaseInfo().getFromDealer());
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		List<TicketOrder> list = (List<TicketOrder>) resultPagination.getList();
		for (TicketOrder o : list) {
			Hibernate.initialize(o.getTicketPolicy());
			Hibernate.initialize(o.getBaseInfo().getFromDealer());
		/*	Hibernate.initialize(o.getGroupTickets());
			Hibernate.initialize(o.getTicketPolicySnapshot());*/
		}
		return resultPagination;
	}
	
	/**
	 * @方法功能说明：门票订单列表视图分页查询
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-28下午4:49:15
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryListVoPagination(TicketOrderListQo qo) {

		
		List<Object> params = new ArrayList<Object>();
		StringBuilder pub = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		StringBuilder qsb = new StringBuilder();

		qsb.append("select ");
		qsb.append("	to.id as orderId, ");
		qsb.append("	tp.baseInfo.name as ticketPolicyName, ");
		qsb.append("	tp.type as ticketPolicyType, ");
		qsb.append("	to.baseInfo.dealerOrderId as dealerOrderId, ");
		qsb.append("	dealer.id as fromDealerId, ");
		qsb.append("	dealer.baseInfo.name as fromDealerName, ");
		qsb.append("	to.status.currentValue as orderStatus, ");
		qsb.append("	to.baseInfo.createDate as orderDate, ");
		qsb.append("	to.payInfo.price as pay, ");
		qsb.append("	to.payInfo.paid as paid ");
		pub.append("from ");
		pub.append("	TicketOrder to ");
		pub.append("	left join to.baseInfo.fromDealer dealer ");
		pub.append("	left join to.ticketPolicy tp ");
		pub.append("where 1=1 ");
		if (StringUtils.isNotBlank(qo.getTicketPolicyName())) {
			pub.append("	and (tp.baseInfo.name like ? or tp.baseInfo.shortName like ?) ");
			params.add("%" + qo.getTicketPolicyName() + "%");
			params.add("%" + qo.getTicketPolicyName() + "%");
		}
		if (qo.getTicketPolicyType() != null) {
			pub.append("	and tp.type = ? ");
			params.add(qo.getTicketPolicyType());
		}
		if (StringUtils.isNotBlank(qo.getFromDealerId())) {
			pub.append("	and dealer.id = ? ");
			params.add(qo.getFromDealerId());
		}
		if (qo.getOrderDateBegin() != null) {
			pub.append("	and to.baseInfo.createDate >= ? ");
			params.add(qo.getOrderDateBegin());
		}
		if (qo.getOrderDateEnd() != null) {
			pub.append("	and to.baseInfo.createDate <= ? ");
			params.add(qo.getOrderDateEnd());
		}
		if (StringUtils.isNotBlank(qo.getTouristName())) {
			pub.append("	and exists (");
			pub.append("		select gt.id ");
			pub.append("		from GroupTicket gt join gt.tourist t ");
			pub.append("		where gt.ticketOrder.id = to.id and t.name like ? ");
			pub.append("	) ");
			params.add("%" + qo.getTouristName() + "%");
		}
		if (StringUtils.isNotBlank(qo.getOrderId())) {
			pub.append("	and to.id like ? ");
			params.add("%" + qo.getOrderId() + "%");
		}
		if (StringUtils.isNotBlank(qo.getDealerOrderId())) {
			pub.append("	and to.baseInfo.dealerOrderId like ? ");
			params.add("%" + qo.getDealerOrderId() + "%");
		}
		if (qo.getOrderStatus() != null) {
			pub.append("	and to.status.currentValue = ? ");
			params.add(qo.getOrderStatus());
		}
		if (StringUtils.isNotBlank(qo.getScenicspotId())) {
			pub.append("	and (");
			pub.append("		tp.scenicSpot.id = ? or exists (");
			pub.append("			select st.id ");
			pub.append("			from SingleTicket st join st.groupTicket sgt ");
			pub.append("			where sgt.ticketOrder.id = to.id and st.scenicSpot.id = ? ");
			pub.append("		)");
			pub.append("	) ");
			params.add(qo.getScenicspotId());
			params.add(qo.getScenicspotId());
		}
		qsb.append(pub);
		qsb.append("order by to.baseInfo.createDate desc");
		
		csb.append("select count(*) ");
		csb.append(pub);

		Pagination pagination = hqlQueryPagination(TicketOrderListVo.class,
				qsb.toString(), csb.toString(), qo.getPageNo(),
				qo.getPageSize(), qo, params);
		
		// 查询订单下的门票
		if (pagination.getTotalCount() > 0) {
			
			List<TicketOrderListVo> list = (List<TicketOrderListVo>) pagination.getList();
			List<String> orderIdList = new ArrayList<String>();
			
			for (TicketOrderListVo vo : list)
				orderIdList.add(vo.getOrderId());
			
			String ticketHql = "select " +
					"	gt.ticketOrder.id as orderId, " +
					"	gt.ticketPolicy.id as ticketPolicyId, " +
					"	tps.baseInfo.name as ticketPolicyName, " +
					"	gt.type as ticketPolicyType, " +
					"	t.id as touristId, " +
					"	t.name as touristName, " +
					"	gt.price as price " +
					"from " +
					"	GroupTicket gt " +
					"	left join gt.ticketPolicySnapshot tps " +
					"	left join gt.tourist t " +
					"where " +
					"	gt.ticketOrder.id in :ids";
			Query ticketQuery = getSession().createQuery(ticketHql)
					.setParameterList("ids", orderIdList)
					.setResultTransformer(new AliasToBeanResultTransformer(TicketOrderListVo.Ticket.class));

			List<TicketOrderListVo.Ticket> tickets = ticketQuery.list();
			
			Map<String, List<TicketOrderListVo.Ticket>> map = new HashMap<String, List<TicketOrderListVo.Ticket>>();
			for (TicketOrderListVo.Ticket ticket : tickets) {
				if (!map.containsKey(ticket.getOrderId()))
					map.put(ticket.getOrderId(), new ArrayList<TicketOrderListVo.Ticket>());
				map.get(ticket.getOrderId()).add(ticket);
			}
			for (TicketOrderListVo vo : list)
				vo.setTickets(map.get(vo.getOrderId()));
		}
		
		return pagination;
	}
}