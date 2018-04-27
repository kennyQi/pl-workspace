package hsl.app.dao.line;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hsl.domain.model.xl.DayRoute;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.LineRouteInfo;
import hsl.domain.model.xl.order.LineOrder;
import hsl.domain.model.xl.order.LineOrderTraveler;
import hsl.pojo.qo.line.HslLineOrderQO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LineOrderDAO extends BaseDao<LineOrder, HslLineOrderQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslLineOrderQO qo) {
		// 快照查询明细
		Criteria lineSnapshotCriteria = null;
		
		if (qo != null) {
			// 订单号查询
			if (StringUtils.isNotBlank(qo.getOrderId())) {
				criteria.add(Restrictions.eq("id",
						qo.getOrderId()));
			}
			//经销商订单号
			if (StringUtils.isNotBlank(qo.getDealerOrderNo())) {
				criteria.add(Restrictions.eq("baseInfo.dealerOrderNo",
						qo.getDealerOrderNo()));
			}
			if (StringUtils.isNotBlank(qo.getLineNumber())) {
				Criteria lineCriteria = null;
				if (lineSnapshotCriteria == null) {
					lineSnapshotCriteria = criteria.createCriteria("lineSnapshot");
					lineCriteria = lineSnapshotCriteria.createCriteria("line");
				}
				lineCriteria.add(Restrictions.eq("baseInfo.number", qo.getLineNumber()));
			}
			// 线路快照关联
			if (StringUtils.isNotBlank(qo.getLineId())) {
				if (lineSnapshotCriteria == null) {
					lineSnapshotCriteria = criteria.createCriteria("lineSnapshot");
				}
				lineSnapshotCriteria.add(Restrictions.eq("line.id", qo.getLineId()));
			}
			// 线路名称
			if (StringUtils.isNotBlank(qo.getLineName())) {
				if (lineSnapshotCriteria == null) {
					lineSnapshotCriteria = criteria
							.createCriteria("lineSnapshot");
				}
				lineSnapshotCriteria.add(Restrictions.like("lineName",
						qo.getLineName(), MatchMode.ANYWHERE));
				
				/*criteria.add(Restrictions.eq("lineSnapshot.lineName",
						qo.getLineName()));*/
			}

			// 预订人
			if (StringUtils.isNotBlank(qo.getBooker())) {
				criteria.add(Restrictions.eq("lineOrderUser.loginName",
						qo.getBooker()));
			}

			// 下单人ID
			if (StringUtils.isNotBlank(qo.getUserId())) {
				criteria.add(Restrictions.eq("lineOrderUser.userId",
						qo.getUserId()));
			}

			// 下单时间
			if (StringUtils.isNotBlank(qo.getStartTime()) && StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("baseInfo.createDate", DateUtil.dateStr2BeginDate(qo.getStartTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			} else if (StringUtils.isNotBlank(qo.getStartTime())) {
				criteria.add(Restrictions.between("baseInfo.createDate", DateUtil.dateStr2BeginDate(qo.getStartTime()), DateUtil.dateStr2EndDate(qo.getStartTime())));
			} else if (StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("baseInfo.createDate", DateUtil.dateStr2BeginDate(qo.getEndTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			Criteria criteria2 = criteria.createCriteria("travelers");
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			// 订单状态
			if (StringUtils.isNotBlank(qo.getOrderStatus())) {

				criteria2.add(Restrictions.eq("lineOrderStatus.orderStatus",
						Integer.parseInt(qo.getOrderStatus())));
			}

			// 支付状态
			if (StringUtils.isNotBlank(qo.getPayStatus())) {
				
				criteria2.add(Restrictions.eq("lineOrderStatus.payStatus",
						Integer.parseInt(qo.getPayStatus())));
			}

		}
		
		//按上架时间倒序排序
		criteria.addOrder(Order.desc("baseInfo.createDate"));
		return criteria;
	}

	@Override
	protected Class<LineOrder> getEntityClass() {
		return LineOrder.class;
	}

	/**
	 * 根据用户ID查询，不联表查询，创建时间倒序
	 *
	 * @param userId
	 * @param offset
	 * @param fetchSize
	 * @return
	 */
	public List<LineOrder> queryListNoJoinOrderByCreateDateDesc(String userId , Integer offset, Integer fetchSize) {
		offset = offset == null ? 0 : offset;
		fetchSize = fetchSize == null ? 20 : fetchSize;
		return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("lineOrderUser.userId", userId))
				.addOrder(Order.desc("baseInfo.createDate"))
				.setFirstResult(offset).setMaxResults(fetchSize).list();
	}


	@Override
	public LineOrder queryUnique(HslLineOrderQO qo) {
		LineOrder lineOrder = super.queryUnique(qo);
		
		//加载线路主图
		Hibernate.initialize(lineOrder.getLineSnapshot().getLine().getLineImageList());
		
		//加载日行程图片
		LineRouteInfo info = lineOrder.getLineSnapshot().getLine().getRouteInfo();
		if (info != null) {
			Hibernate.initialize(info.getDayRouteList());
			for (DayRoute dayRoute : info.getDayRouteList()) {
				Hibernate.initialize(dayRoute.getLineImageList());
			}
		}
		//加载订单游客
		Hibernate.initialize(lineOrder.getTravelers());
		lineOrder.setTravelerList(new ArrayList<LineOrderTraveler>(lineOrder
				.getTravelers()));
		return lineOrder;
	}

	@Override
	public List<LineOrder> queryList(HslLineOrderQO qo) {
		List<LineOrder> lineOrders = super.queryList(qo);
		for (LineOrder lineOrder : lineOrders) {
			
			//加载线路主图
			Hibernate.initialize(lineOrder.getLineSnapshot().getLine().getLineImageList());
			
			//加载日行程图片
			LineRouteInfo info = lineOrder.getLineSnapshot().getLine().getRouteInfo();
			if (info != null) {
				Hibernate.initialize(info.getDayRouteList());
				for (DayRoute dayRoute : info.getDayRouteList()) {
					Hibernate.initialize(dayRoute.getLineImageList());
				}
			}
			//加载订单游客
			Hibernate.initialize(lineOrder.getTravelers());
			lineOrder.setTravelerList(new ArrayList<LineOrderTraveler>(lineOrder.getTravelers()));
		}
		return lineOrders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = super.queryPagination(pagination);
		List<LineOrder> lineOrders = (List<LineOrder>) pagination2.getList();
		for (LineOrder lineOrder : lineOrders) {
			//加载线路主图
			Hibernate.initialize(lineOrder.getLineSnapshot().getLine().getLineImageList());
			
			//加载日行程图片
			LineRouteInfo info = lineOrder.getLineSnapshot().getLine().getRouteInfo();
			if (info != null) {
				Hibernate.initialize(info.getDayRouteList());
				for (DayRoute dayRoute : info.getDayRouteList()) {
					Hibernate.initialize(dayRoute.getLineImageList());
				}
			}
			//加载订单游客
			
			Hibernate.initialize(lineOrder.getTravelers());
			lineOrder.setTravelerList(new ArrayList<LineOrderTraveler>(lineOrder.getTravelers()));
		}
		return pagination2;
	}
}
