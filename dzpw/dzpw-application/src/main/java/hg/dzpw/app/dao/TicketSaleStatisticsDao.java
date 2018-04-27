package hg.dzpw.app.dao;

import hg.common.component.hibernate.HibernateSimpleDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.dzpw.app.common.util.DateUtils;
import hg.dzpw.pojo.qo.DealerSaleStatisticsQo;
import hg.dzpw.pojo.qo.GroupTicketSaleStatisticsQo;
import hg.dzpw.pojo.qo.TicketOrderTouristDetailQo;
import hg.dzpw.pojo.vo.DealerSaleStatisticsVo;
import hg.dzpw.pojo.vo.GroupTicketSaleStatisticsVo;
import hg.dzpw.pojo.vo.TicketOrderTouristDetailVo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：销售统计数据库操作
 * @类修改者：
 * @修改日期：2014-11-21下午2:55:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21下午2:55:17
 */
@Repository
public class TicketSaleStatisticsDao extends HibernateSimpleDao {

	/**
	 * @方法功能说明：经销商销售统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:45:04
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see DealerSaleStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryDealerSaleStatistics(DealerSaleStatisticsQo qo, boolean selectAll) {
		// 不查询非闭合时间区间
		if (qo.getOrderDateEnd() == null ^ qo.getOrderDateBegin() == null) {
			Pagination pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
			pagination.setList(new ArrayList<Object>());
			return pagination;
		}
		
		// 默认搜索昨天
		if (qo.getOrderDateBegin() == null && qo.getOrderDateEnd() == null) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			Date yd = c.getTime();
			Date begin = DateUtil.dateStr2BeginDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			Date end = DateUtil.dateStr2EndDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			qo.setOrderDateBegin(begin);
			qo.setOrderDateBegin(end);
		}
		
		// 间隔天数
		int days = DateUtils.days(qo.getOrderDateBegin(), qo.getOrderDateEnd());
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder pub = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		StringBuilder qsb = new StringBuilder();

		qsb.append("select ");
		qsb.append("fd.id as dealerId, ");
		qsb.append("fd.baseInfo.name as dealerName, ");
		qsb.append("sum(to.baseInfo.ticketNo) as saleTicketTotalCount, ");
		qsb.append("sum(to.payInfo.price) as saleTotalAmount ");

		csb.append("select ");
		csb.append("count(distinct fd.id) ");
		
		pub.append("from ");
		pub.append("TicketOrder to ");
		pub.append("join to.baseInfo.fromDealer fd ");
		pub.append("where ");
		pub.append("to.baseInfo.createDate >= ? and to.baseInfo.createDate <= ? ");
		pub.append("and (to.payInfo.paid=1 or to.payInfo.paid=2) ");
		
		params.add(qo.getOrderDateBegin());
		params.add(qo.getOrderDateEnd());
		
		if (StringUtils.isNotBlank(qo.getDealerId())) {
			pub.append("and fd.id=? ");
			params.add(qo.getDealerId());
		}
		
		qsb.append(pub);
		qsb.append("group by fd.id ");
		
		// 按销量
		if (qo.getQueryType() == GroupTicketSaleStatisticsQo.QUERY_TYPE_ORDER_TIKECT_COUNT)
			qsb.append("order by saleTicketTotalCount desc");
		
		// 按销售额
		else if (qo.getQueryType() == GroupTicketSaleStatisticsQo.QUERY_TYPE_SALE_TIKECT_AMOUNT)
			qsb.append("order by saleTotalAmount desc");

		csb.append(pub);
		
		Pagination pagination = null;
		Long totalCount = 0L;
		
		if (!selectAll) {

			Query countQuery = getSession().createQuery(csb.toString());
			
			for (int i = 0; i < params.size(); i++) {
				countQuery.setParameter(i, params.get(i));
			}
			totalCount = (Long) countQuery.uniqueResult();
			
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), totalCount.intValue());
		}

		if (selectAll || (!selectAll && totalCount > 0)) {

			Query query = getSession().createQuery(qsb.toString());
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
			query.setResultTransformer(new AliasToBeanResultTransformer(DealerSaleStatisticsVo.class));
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<DealerSaleStatisticsVo> list = query.list();
			
			for (int i = 0; i < list.size(); i++) {
				DealerSaleStatisticsVo vo = list.get(i);
				vo.setSaleTicketDayCount(vo.getSaleTicketTotalCount() * 1f / days);
				if (selectAll)
					vo.setRank(i + 1);
				else
					vo.setRank(pagination.getFirstResult() + i + 1);
			}
			
			if (selectAll)
				pagination = new Pagination(1, list.size(), list.size());
			
			pagination.setList(list);
		}

		if (pagination == null)
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
		if (pagination.getList() == null)
			pagination.setList(new ArrayList<Object>());
		if (pagination.getCondition() == null)
			pagination.setCondition(qo);

		return pagination;
	}

	/**
	 * @方法功能说明：联票销售统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:45:33
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see GroupTicketSaleStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryGroupTicketSaleStatistics(GroupTicketSaleStatisticsQo qo, boolean selectAll) {

		// 不查询非闭合时间区间
		if (qo.getOrderDateEnd() == null ^ qo.getOrderDateBegin() == null) {
			Pagination pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
			pagination.setList(new ArrayList<Object>());
			return pagination;
		}
		
		// 默认搜索昨天
		if (qo.getOrderDateBegin() == null && qo.getOrderDateEnd() == null) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			Date yd = c.getTime();
			Date begin = DateUtil.dateStr2BeginDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			Date end = DateUtil.dateStr2EndDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			qo.setOrderDateBegin(begin);
			qo.setOrderDateEnd(end);
		}
		
		// 间隔天数
		int days = DateUtils.days(qo.getOrderDateBegin(), qo.getOrderDateEnd());
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder pub = new StringBuilder(); 
		StringBuilder csb = new StringBuilder();
		StringBuilder qsb = new StringBuilder(); //查询字段
		StringBuilder sum = new StringBuilder(); //sql 的sum()方法

		qsb.append("select ");
//		qsb.append("tp.id as ticketPolicyId, ");
//		qsb.append("tp.type as ticketPolicyType,");
//		qsb.append("tp.baseInfo.name as ticketPolicyName, ");
		qsb.append("to.id as orderId, ");
		qsb.append("gt.status.current as status, ");
		qsb.append("gt.ticketNo as ticketNo,");
		qsb.append("tsp.baseInfo.name as ticketPolicyName, ");
//		qsb.append("sum(to.baseInfo.ticketNo) as saleTicketTotalCount, ");
//		qsb.append("sum(to.payInfo.price) as saleTotalAmount, ");
		qsb.append("gt.price as settlementAmount, ");
		qsb.append("tsp.baseInfo.scenicSpotNameStr as scenicSpotName, ");
		qsb.append("deal.baseInfo.name as dealerName, ");
		qsb.append("to.baseInfo.createDate as createOrderDate ");

		//门票总计,销售总额
		sum.append("select ");
		sum.append("count(distinct gt.id) as saleTicketTotalCount, ");
		sum.append("sum(gt.price) as saleTotalAmount ");
		
		
		csb.append("select ");
		csb.append("count(distinct gt.id) ");
		
		pub.append("from ");
		pub.append("TicketOrder to ");
//		pub.append("join to.ticketPolicy tp ");
		pub.append("join to.baseInfo.fromDealer deal ");
		pub.append("join to.groupTickets gt ");
		pub.append("join gt.ticketPolicySnapshot tsp ");
		pub.append("where ");
		pub.append("to.baseInfo.createDate >= ? and to.baseInfo.createDate <= ? ");
		pub.append(" and gt.status.current !=0 ");
//		pub.append("and to.payInfo.paid=2 ");
		
		params.add(qo.getOrderDateBegin());
		params.add(qo.getOrderDateEnd());
		
//		if (StringUtils.isNotBlank(qo.getTicketPolicyId())) {
//			pub.append("and tp.id=? ");
//			params.add(qo.getTicketPolicyId());
//		}
		
		if (StringUtils.isNotBlank(qo.getScenicSpotName())) {
			pub.append("and (tsp.baseInfo.scenicSpotNameStr like ? ) ");
			params.add("%" + qo.getScenicSpotName() + "%");
		}
		if (StringUtils.isNotBlank(qo.getDealerName())) {
			pub.append("and (deal.baseInfo.name like ? ) ");
			params.add("%" + qo.getDealerName() + "%");
		}
		if (StringUtils.isNotBlank(qo.getTicketPolicyName())) {
			pub.append("and (tsp.baseInfo.name like ? ) ");
			params.add("%" + qo.getTicketPolicyName() + "%");
		}
		if(qo.getStatus()!=null)
		{
			pub.append("and gt.status.current = ? ");
			params.add(qo.getStatus());
		}
		/*if (StringUtils.isNotBlank(qo.getTicketPolicyName())) {
			pub.append("and (tp.baseInfo.name like ? or tp.baseInfo.shortName like ?) ");
			params.add("%" + qo.getTicketPolicyName() + "%");
			params.add("%" + qo.getTicketPolicyName() + "%");
			
		}
		if (qo.getTicketPolicyType()!=null){
			pub.append("and tp.type=?");  //查询单票、联票
			params.add(qo.getTicketPolicyType());
		}*/
		
		
		qsb.append(pub);
//		qsb.append("group by tp.id ");
		
//		// 按销量
//		if (qo.getQueryType() == GroupTicketSaleStatisticsQo.QUERY_TYPE_ORDER_TIKECT_COUNT)
//			qsb.append("order by saleTicketTotalCount desc");
//		// 按销售额
//		else if (qo.getQueryType() == GroupTicketSaleStatisticsQo.QUERY_TYPE_SALE_TIKECT_AMOUNT)
//			qsb.append("order by saleTotalAmount desc");
		
		qsb.append("order by createOrderDate desc");

		csb.append(pub);
		sum.append(pub);
		
		Pagination pagination = null;
		Long totalCount = 0L;
		
		if (!selectAll) {

			Query countQuery = getSession().createQuery(csb.toString());
			
			for (int i = 0; i < params.size(); i++) {
				countQuery.setParameter(i, params.get(i));
			}
			totalCount = (Long) countQuery.uniqueResult();
			
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), totalCount.intValue());
		}

		if (selectAll || (!selectAll && totalCount > 0)) {

			Query query = getSession().createQuery(qsb.toString());
			Query sumQuery = getSession().createQuery(sum.toString());
			
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
				sumQuery.setParameter(i, params.get(i));
			}
			query.setResultTransformer(new AliasToBeanResultTransformer(GroupTicketSaleStatisticsVo.class));
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<GroupTicketSaleStatisticsVo> list = query.list();
			Object[] sumObj = (Object[]) /*(GroupTicketSaleStatisticsVo)*/ sumQuery.uniqueResult();
			for (int i = 0; i < list.size(); i++) {
				GroupTicketSaleStatisticsVo vo = list.get(i);
				if ((Long)sumObj[0] > 0)
				//平均票价
					vo.setSettlementAvgAmount((Double)sumObj[1] / (Long)sumObj[0]);
				//日销售票数
				vo.setSaleTicketDayCount((Long)sumObj[0] * 1f / days);
				if (selectAll)
					vo.setRank(i + 1);
				else
					vo.setRank(pagination.getFirstResult() + i + 1);
			}
			if(list.size()>0)
			{
			list.get(0).setSaleTicketTotalCount((Long)sumObj[0]);
			list.get(0).setSaleTotalAmount((Double)sumObj[1]);
			}
			
			if (selectAll)
				pagination = new Pagination(1, list.size(), list.size());
			
			pagination.setList(list);
		}

		if (pagination == null)
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
		if (pagination.getList() == null)
			pagination.setList(new ArrayList<Object>());
		if (pagination.getCondition() == null)
			pagination.setCondition(qo);

		return pagination;
	}

	/**
	 * @方法功能说明：门票订单里的用户查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:45:43
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see TicketOrderTouristDetailVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryTicketOrderTouristDetail(TicketOrderTouristDetailQo qo, boolean selectAll) {

		// 不查询非闭合时间区间
		if (qo.getOrderDateEnd() == null ^ qo.getOrderDateBegin() == null) {
			Pagination pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
			pagination.setList(new ArrayList<Object>());
			return pagination;
		}
		
		// 默认搜索昨天
		if (qo.getOrderDateBegin() == null && qo.getOrderDateEnd() == null) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			Date yd = c.getTime();
			Date begin = DateUtil.dateStr2BeginDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			Date end = DateUtil.dateStr2EndDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			qo.setOrderDateBegin(begin);
			qo.setOrderDateBegin(end);
		}
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder pub = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		StringBuilder qsb = new StringBuilder();
		
		qsb.append("select ");
		qsb.append("t.id as touristId, ");
		qsb.append("gtk.id as ticketId, ");
		qsb.append("to.id as ticketOrderId, ");
		qsb.append("gtk.ticketNo as ticketNo, ");
		qsb.append("t.name as name, ");
		qsb.append("t.idType as cerType, ");
		qsb.append("t.idNo as cerNo, ");
		qsb.append("gtk.status.current as status, ");
		qsb.append("to.baseInfo.createDate as orderDate ");

		csb.append("select ");
		csb.append("count(t.id) ");
		
		pub.append("from ");
		pub.append("TicketOrder to ");
		pub.append("join to.groupTickets gtk ");
		pub.append("join gtk.tourist t ");
		pub.append("where ");
		pub.append("to.baseInfo.createDate >= ? and to.baseInfo.createDate <= ? ");
		pub.append("and (to.payInfo.paid=1 or to.payInfo.paid=2) ");
		
		params.add(qo.getOrderDateBegin());
		params.add(qo.getOrderDateEnd());
		if (StringUtils.isNotBlank(qo.getDealerId())) {
			pub.append("and to.baseInfo.fromDealer.id=? ");
			params.add(qo.getDealerId());
		}
		if (StringUtils.isNotBlank(qo.getTicketPolicyId())) {
			pub.append("and to.ticketPolicy.id=? ");
			params.add(qo.getTicketPolicyId());
		}
		if (StringUtils.isNotBlank(qo.getOrderNo())) {
			pub.append("and to.id like ? ");
			params.add("%" + qo.getOrderNo() + "%");
		}
		
		qsb.append(pub);
		qsb.append("order by to.baseInfo.createDate desc");

		csb.append(pub);
		
		Pagination pagination = null;
		Long totalCount = 0L;
		
		if (!selectAll) {

			Query countQuery = getSession().createQuery(csb.toString());
			
			for (int i = 0; i < params.size(); i++) {
				countQuery.setParameter(i, params.get(i));
			}
			totalCount = (Long) countQuery.uniqueResult();
			
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), totalCount.intValue());
		}

		if (selectAll || (!selectAll && totalCount > 0)) {

			Query query = getSession().createQuery(qsb.toString());
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
			query.setResultTransformer(new AliasToBeanResultTransformer(TicketOrderTouristDetailVo.class));
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<TicketOrderTouristDetailVo> list = query.list();
			
			if (selectAll)
				pagination = new Pagination(1, list.size(), list.size());
			
			pagination.setList(list);
		}

		if (pagination == null)
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
		if (pagination.getList() == null)
			pagination.setList(new ArrayList<Object>());
		if (pagination.getCondition() == null)
			pagination.setCondition(qo);

		return pagination;
	}
}
