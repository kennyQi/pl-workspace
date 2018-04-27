package hg.dzpw.app.dao;

import hg.common.component.hibernate.HibernateSimpleDao;
import hg.common.page.Pagination;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.pojo.common.BasePojoQo;
import hg.dzpw.pojo.qo.ReconciliationCollectOrderQo;
import hg.dzpw.pojo.qo.ReconciliationOrderQo;
import hg.dzpw.pojo.vo.ReconciliationCollectOrderVo;
import hg.dzpw.pojo.vo.ReconciliationOrderVo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：财务管理DAO
 * @类修改者：
 * @修改日期：2014-11-14下午4:25:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-14下午4:25:54
 *
 */
@Repository
public class FinanceManagementDao extends HibernateSimpleDao {
	
	private Pagination checkPagination(Pagination pagination, BasePojoQo qo) {
		if (pagination == null)
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
		if (pagination.getList() == null)
			pagination.setList(new ArrayList<Object>());
		if (pagination.getCondition() == null)
			pagination.setCondition(qo);
		return pagination;
	}
	
	/**
	 * @方法功能说明：汇总对账单查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-14下午4:21:31
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see ReconciliationCollectOrderVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryReconciliationCollectOrder(ReconciliationCollectOrderQo qo, boolean selectAll) {
		
		// 景区端查询时未带景区ID时直接返回空结果
		if (qo.getScenicSpotSelect() != null && qo.getScenicSpotSelect()
				&& StringUtils.isBlank(qo.getScenicSpotId())) {
			return checkPagination(null, qo);
		}
		
		StringBuilder pub = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		StringBuilder qsb = new StringBuilder();
		
		Pagination pagination = null;
		Integer totalCount = 0;
		List<Object> params = new ArrayList<Object>();
		
		//查询记录数
		csb.append("SELECT COUNT(DISTINCT st.SCENIC_SPOT_ID) ");
		
		//查询明细
		qsb.append("SELECT ");
		qsb.append("	ss.ID AS scenicSpotId, ");
		qsb.append("	ss.NAME AS scenicSpotName, ");
		qsb.append("	SUM(st.PRICE) AS price, ");
		qsb.append("	SUM(CASE gt.CURRENT WHEN -1 THEN 0 ELSE st.price END) AS paidAmount, ");
		qsb.append("	SUM(CASE st.SETTLEMENT WHEN 'Y' THEN st.SETTLEMENT_AMOUNT ELSE 0 END) AS settlementAmount, ");
		qsb.append("	COUNT(DISTINCT gt.id) AS groupTicketTotalNumber ");
		pub.append("FROM ");
		pub.append("	dzpw_single_ticket st ");
		pub.append("	LEFT JOIN dzpw_group_ticket gt ON st.GROUP_TICKET_ID = gt.ID ");
		pub.append("	LEFT JOIN dzpw_scenic_spot ss ON st.SCENIC_SPOT_ID = ss.id ");
		pub.append("	LEFT JOIN dzpw_ticket_order o ON o.ID = gt.TICKET_ORDER_ID ");
		pub.append("WHERE gt.TYPE = ? ");
		params.add(GroupTicket.GROUP_TICKET_TYPE_SINGLE_SCENIC_SPOT);

		// 景区端查询范围：门票非未激活状态和已结算过的
		if (qo.getScenicSpotSelect() != null && qo.getScenicSpotSelect()) {
			pub.append("AND gt.CURRENT <> ? ");
			params.add(GroupTicketStatus.GROUP_TICKET_STATUS_UNACTIVE);
			pub.append("AND st.SETTLEMENT = 'Y' ");
		}
		
		if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
			pub.append("AND st.SCENIC_SPOT_ID = ? ");
			params.add(qo.getScenicSpotId());
		}
		if (qo.getOrderDateBegin() != null) {
			pub.append("AND o.CREATE_DATE >= ? ");
			params.add(qo.getOrderDateBegin());
		}
		if (qo.getOrderDateEnd() != null) {
			pub.append("AND o.CREATE_DATE <= ? ");
			params.add(qo.getOrderDateEnd());
		}
		
		
		qsb.append(pub);
		qsb.append("GROUP BY st.SCENIC_SPOT_ID");
		
		csb.append(pub);
				
		// 分页查询
		if (!selectAll) {
			Query countQuery = getSession().createSQLQuery(csb.toString());
			for (int i = 0; i < params.size(); i++)
				countQuery.setParameter(i, params.get(i));
			totalCount = ((Number) countQuery.uniqueResult()).intValue();
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), totalCount.intValue());
		}
		
		// 查询全部或查询总数大于0的
		if (selectAll || (!selectAll && totalCount > 0)) {

			Query query = getSession().createSQLQuery(qsb.toString());
			
			for (int i = 0; i < params.size(); i++)
				query.setParameter(i, params.get(i));
			
			query.setResultTransformer(new AliasToBeanResultTransformer(ReconciliationCollectOrderVo.class));

			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}

			@SuppressWarnings("unchecked")
			List<ReconciliationCollectOrderVo> list = query.list();

			if (selectAll)
				pagination = new Pagination(1, list.size(), list.size());

			pagination.setList(list);
		}
	
		return checkPagination(pagination, qo);
	}
	
	/**
	 * @方法功能说明：支付对账单查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-14下午4:22:23
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see ReconciliationOrderVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryReconciliationOrder(ReconciliationOrderQo qo, boolean selectAll) {
		
		// 景区端查询时未带景区ID时直接返回空结果
		if (qo.getScenicSpotSelect() != null && qo.getScenicSpotSelect()
				&& StringUtils.isBlank(qo.getScenicSpotId())) {
			return checkPagination(null, qo);
		}
		
		StringBuilder pub = new StringBuilder();
		StringBuilder qsb = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		
		Pagination pagination = null;
		Integer totalCount = 0;
		List<Object> params = new ArrayList<Object>();
		
		csb.append("SELECT COUNT(*) ");
		
		//查询明细
		qsb.append("SELECT ");
		qsb.append("	o.ID AS ticketOrderId, ");
		qsb.append("	gt.ID AS groupTicketId,");
		qsb.append("	gt.TICKET_NO AS ticketNo, ");
		qsb.append("	tps. NAME AS policyName, ");
		qsb.append("	o.CREATE_DATE AS orderDate, ");
		qsb.append("	o.PAY_DATE AS payDate, ");
		qsb.append("	st.PRICE AS price, ");
		qsb.append("	st.FIRST_TIME_USE_DATE as firstTimeUseDate, ");
		qsb.append("	(CASE o.PAID WHEN 0 THEN 0 ELSE st.PRICE END) AS paidAmount, ");
		qsb.append("	st.SCENIC_SPOT_ID AS scenicSpotId, ");
		qsb.append("	ss.NAME AS scenicSpotName, ");
		qsb.append("	(CASE WHEN st.SETTLEMENT_AMOUNT IS NULL THEN 0 ELSE st.SETTLEMENT_AMOUNT END) AS settlementAmount ");
		pub.append("FROM ");
		pub.append("	dzpw_single_ticket st ");
		pub.append("	LEFT JOIN dzpw_group_ticket gt ON st.GROUP_TICKET_ID = gt.ID ");
		pub.append("	LEFT JOIN dzpw_ticket_order o ON gt.TICKET_ORDER_ID = o.ID ");
		pub.append("	LEFT JOIN dzpw_ticket_policy_snapshot tps ON o.TICKET_POLICY_SNAPSHOT_ID = tps.ID ");
		pub.append("	LEFT JOIN dzpw_scenic_spot ss ON st.SCENIC_SPOT_ID = ss.ID ");
		pub.append("WHERE gt.TYPE = ? ");
		params.add(GroupTicket.GROUP_TICKET_TYPE_SINGLE_SCENIC_SPOT);
		
		// 景区端查询范围：门票非未激活状态和已结算过的
		if (qo.getScenicSpotSelect() != null && qo.getScenicSpotSelect()) {
			pub.append("AND gt.CURRENT <> ? ");
			params.add(GroupTicketStatus.GROUP_TICKET_STATUS_UNACTIVE);
			pub.append("AND st.SETTLEMENT = 'Y' ");
		}

		if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
			pub.append("AND st.SCENIC_SPOT_ID = ?");
			params.add(qo.getScenicSpotId());
		}
		if (qo.getOrderDateBegin() != null) {
			pub.append("AND o.CREATE_DATE >= ? ");
			params.add(qo.getOrderDateBegin());
		}
		if (qo.getOrderDateEnd() != null) {
			pub.append("AND o.CREATE_DATE <= ? ");
			params.add(qo.getOrderDateEnd());
		}
		
		qsb.append(pub);
		if (qo.getOrderDateSort() != null)
			qsb.append("ORDER BY o.CREATE_DATE ").append(qo.getOrderDateSort() > 0 ? "ASC" : "DESC");
		
		csb.append(pub);
		
		if (!selectAll) {
			Query countQuery = getSession().createSQLQuery(csb.toString());
			for (int i = 0; i < params.size(); i++)
				countQuery.setParameter(i, params.get(i));
			totalCount = ((Number) countQuery.uniqueResult()).intValue();
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), totalCount.intValue());
		}
		
		if (selectAll || (!selectAll && totalCount > 0)) {

			Query query = getSession().createSQLQuery(qsb.toString());
			
			for (int i = 0; i < params.size(); i++)
				query.setParameter(i, params.get(i));

			query.setResultTransformer(new AliasToBeanResultTransformer(ReconciliationOrderVo.class));

			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			
			@SuppressWarnings("unchecked")
			List<ReconciliationOrderVo> list = query.list();

			if (selectAll)
				pagination = new Pagination(1, list.size(), list.size());

			pagination.setList(list);
		}
		
		return checkPagination(pagination, qo);
	}
	
}
