package hsl.app.dao.lineSalesPlan.order;
import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hsl.domain.model.lineSalesPlan.order.LSPOrder;
import hsl.domain.model.lineSalesPlan.order.LSPOrderTraveler;
import hsl.domain.model.xl.order.LineOrderTraveler;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @类功能说明：线路销售方案订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 9:22
 */
@Repository
public class LSPOrderDao extends BaseDao<LSPOrder,LSPOrderQO> {
	@Override
	protected Criteria buildCriteria(Criteria criteria, LSPOrderQO qo) {
		if(qo!=null){
			criteria.setFetchMode("line", FetchMode.JOIN);
			Criteria lspcCriteria1=criteria.createCriteria("lineSalesPlan");
			if(StringUtils.isNotBlank(qo.getLspName())){
				lspcCriteria1.add(Restrictions.like("baseInfo.planName", qo.getLspName(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getPlanId())){
				lspcCriteria1.add(Restrictions.eq("id", qo.getPlanId()));
			}
			if(StringUtils.isNotBlank(qo.getPayTradeNo())){
				criteria.add(Restrictions.eq("orderPayInfo.payTradeNo",qo.getPayTradeNo()));
			}
			if(qo.isFetchTraveler()){
				Criteria criteria1=criteria.createCriteria("travelers", JoinType.LEFT_OUTER_JOIN);
				if(StringUtils.isNotBlank( qo.getTravelerIdNo()))
					criteria1.add(Restrictions.eq("idNo", qo.getTravelerIdNo()));
				criteria1.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
//				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LSPOrderTraveler.class, "t");
//				criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("t.id"))));
//				if(StringUtils.isNotBlank( qo.getTravelerIdNo())){
//					detachedCriteria.add(Restrictions.eq("t.idNo", qo.getTravelerIdNo()));
//				}
			}
			if(qo.getOrderType()!=null){
				criteria.add(Restrictions.eq("orderBaseInfo.orderType",qo.getOrderType()));
			}
			if(StringUtils.isNotBlank(qo.getDealerOrderNo())){
				criteria.add(Restrictions.eq("orderBaseInfo.dealerOrderNo",qo.getDealerOrderNo()));
			}

			if(qo.getOrderStatus()!=null){
				criteria.add(Restrictions.eq("orderStatus.orderStatus", qo.getOrderStatus()));
			}
			if(qo.getOrderStatusArray()!=null&&qo.getOrderStatusArray().length>0){
				criteria.add(Restrictions.in("orderStatus.orderStatus", qo.getOrderStatusArray()));
			}
			if(qo.getPayStatus()!=null){
				criteria.add(Restrictions.eq("orderStatus.payStatus", qo.getPayStatus()));
			}
			if(StringUtils.isNotBlank(qo.getUserId())){
				criteria.add(Restrictions.eq("orderUser.userId",qo.getUserId()));
			}
			criteria.addOrder(Order.desc("orderBaseInfo.createDate"));
		}
		return criteria;
	}

	@Override
	protected Class<LSPOrder> getEntityClass() {
		return LSPOrder.class;
	}

	@Override
	public List<LSPOrder> queryList(LSPOrderQO qo) {
		List<LSPOrder> lspOrders=super.queryList(qo);
		for (LSPOrder lspOrder:lspOrders){
			if(lspOrder!=null){
				lspOrder.setTravelerList(new ArrayList<LSPOrderTraveler>(lspOrder.getTravelers()));
			}
		}
		return lspOrders;
	}

	@Override
	public LSPOrder queryUnique(LSPOrderQO qo) {
		LSPOrder lspOrder=super.queryUnique(qo);
		if(lspOrder!=null){
			lspOrder.setTravelerList(new ArrayList<LSPOrderTraveler>(lspOrder.getTravelers()));
		}
		return lspOrder;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination1=super.queryPagination(pagination);
		List<LSPOrder> lspOrders= (List<LSPOrder>) pagination1.getList();
		for (LSPOrder lspOrder:lspOrders){
			if(lspOrder!=null){
				lspOrder.setTravelerList(new ArrayList<LSPOrderTraveler>(lspOrder.getTravelers()));
			}
		}
		return pagination1;
	}
}
