package hg.dzpw.app.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import hg.common.component.hibernate.HibernateSimpleDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.dzpw.pojo.qo.FinanceDetailQo;
import hg.dzpw.pojo.qo.SettleDetailQo;
import hg.dzpw.pojo.vo.FinanceVo;
import hg.dzpw.pojo.vo.GroupTicketSaleStatisticsVo;
import hg.dzpw.pojo.vo.SettleDetailVo;
/**
 * 经销商端财务明细数据库操作
 * @author CaiHuan
 *
 * 日期:2015-12-24
 */
@Repository
public class FinanceStatisticsDao extends HibernateSimpleDao {

	/**
	 * 财务明细统计
	 * @author CaiHuan
	 * @param qo
	 * @return
	 */
	public Pagination queryFinanceDetail(FinanceDetailQo qo, boolean selectAll)
	{
		 //不查询非闭合时间区间
		if (qo.getDateEnd() == null ^ qo.getDateBegin() == null) {
			Pagination pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
			pagination.setList(new ArrayList<Object>());
			return pagination;
		}
		 //默认搜索昨天
		if (qo.getDateBegin() == null && qo.getDateEnd() == null) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			Date yd = c.getTime();
			Date begin = DateUtil.dateStr2BeginDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			Date end = DateUtil.dateStr2EndDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			qo.setDateBegin(begin);
			qo.setDateEnd(end);
		}
		List<Object> params = new ArrayList<Object>();
		StringBuilder pub = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		StringBuilder qsb = new StringBuilder();
		StringBuilder sumsb = new StringBuilder(); //总计票价,总计手续费
		StringBuilder sumrefund = new StringBuilder(); //总退款金额
		StringBuilder spub= new StringBuilder();
		StringBuilder wsb = new StringBuilder();//条件
		
		qsb.append("select ");
	    qsb.append("tsp.baseInfo.name as policyName,");
	    qsb.append("tsp.baseInfo.key as policyNo,");
	    qsb.append("gt.ticketNo as ticketNo,");
	    qsb.append("gt.id as groupTicketId,");
	    qsb.append("to.id as orderId,");
	    qsb.append("tsp.baseInfo.scenicSpotNameStr as scenicSpotNameStr,");
	    qsb.append("to.baseInfo.linkMan as linkMan,");
	    qsb.append("to.baseInfo.createDate as createDate,");
	    qsb.append("to.payInfo.payDate as ticketDate,");
	    qsb.append("gt.price as dealerAmount,");
	    qsb.append("gt.status.current as status,");
	    qsb.append("gt.status.refundDate as refundTime,");
	    qsb.append("gt.dealerSettlementFee as dealerSettlementFee,");
	    qsb.append("gt.payTradeNo as payTradeNo,");
	    qsb.append("gt.refundBatchNo as refundBatchNo,");
	    qsb.append("d.accountInfo.accountType as dealerAccountType,");
	    qsb.append("d.accountInfo.accountNumber as dealerAccountNumber");
	    
	    csb.append("select ");
		csb.append("count(distinct gt.id) ");
		
		sumsb.append("select ");
		sumsb.append(" sum(gt.price) as totalAmount,sum(gt.dealerSettlementFee)"); //总结算金额，总手续费
		
		sumrefund.append("select  sum(st.settlementInfo.dealerPrice) as refundPrice ");
		
		pub.append(" from ");
		pub.append("GroupTicket gt ");
		pub.append("join gt.ticketPolicySnapshot tsp ");
//		pub.append("join gt.singleTickets st ");
		pub.append("join gt.ticketOrder to ");
		pub.append("join to.baseInfo.fromDealer d ");
		
		sumsb.append(pub);

		sumrefund.append(pub);
		sumrefund.append("join gt.singleTickets st ");
		wsb.append("where 1=1 ");
		
		if(StringUtils.isNotBlank(qo.getDealerId()))
		{
			wsb.append(" and d.id=? ");  //经销商id
			params.add(qo.getDealerId());
		}
		
		if(StringUtils.isNotBlank(qo.getPolicyNo()))
		{
			wsb.append(" and tsp.baseInfo.key like ? ");  //产品编号
			params.add("%"+qo.getPolicyNo()+"%");
		}
		if(StringUtils.isNotBlank(qo.getPolicyName()))
		{
			wsb.append(" and tsp.baseInfo.name like ? ");  //产品编号
			params.add("%"+qo.getPolicyName()+"%");
		}
		if(qo.getStatus()!=null)
		{
			wsb.append(" and gt.status.current=? ");  //订单状态
			params.add(qo.getStatus());
		}
		if(StringUtils.isNotBlank(qo.getOrderId()))
		{
			wsb.append(" and to.id like ? ");  //订单编号
			params.add("%"+qo.getOrderId()+"%");
		}
		if(qo.getDateBegin()!=null )
		{
			wsb.append(" and  to.payInfo.payDate >= ? ");//结算开始时间
			params.add(qo.getDateBegin());
		}
		if( qo.getDateEnd()!=null)
		{
			wsb.append("and to.payInfo.payDate <= ? "); //结算结束时间
			params.add(qo.getDateEnd());
		}
		
		pub.append(wsb);
		qsb.append(pub);
		sumsb.append(wsb);
		sumrefund.append(wsb).append(" and st.status.current=7 ");
		qsb.append("order by to.baseInfo.createDate desc");

		csb.append(pub);
		Pagination pagination = null;
		Long totalCount = 0L;
//		SettleDetailVo vo = new SettleDetailVo();
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
			Query sumQuery = getSession().createQuery(sumsb.toString());
			Query refundQuery = getSession().createQuery(sumrefund.toString());
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
				sumQuery.setParameter(i, params.get(i));
				refundQuery.setParameter(i, params.get(i));
			}
			query.setResultTransformer(new AliasToBeanResultTransformer(FinanceVo.class));
			Object[]  totoPrice=   (Object[]) sumQuery.uniqueResult();
			Double totoalAmount = (Double) refundQuery.uniqueResult();
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<FinanceVo> list = query.list();
			
			if(list.size()>0)
			{
				list.get(0).setTotalPrice((Double)totoPrice[0]);
				list.get(0).setTotalDealerFee((Double)totoPrice[1]);
				if(totoalAmount==null)
					totoalAmount = 0d;
				list.get(0).setTotalAmount(totoalAmount);
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
	 * 运营端财务明细
	 * @author CaiHuan
	 * @param qo
	 * @param selectAll
	 * @return
	 */
	public Pagination queryAdminFinanceDetail(FinanceDetailQo qo,
			boolean selectAll) {
		
		 //不查询非闭合时间区间
		if (qo.getDateEnd() == null ^ qo.getDateBegin() == null) {
			Pagination pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), 0);
			pagination.setList(new ArrayList<Object>());
			return pagination;
		}
		 //默认搜索昨天
		if (qo.getDateBegin() == null && qo.getDateEnd() == null) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			Date yd = c.getTime();
			Date begin = DateUtil.dateStr2BeginDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			Date end = DateUtil.dateStr2EndDate(DateUtil.formatDateTime(yd, "yyyy-MM-dd"));
			qo.setDateBegin(begin);
			qo.setDateEnd(end);
		}
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder pub = new StringBuilder();
		StringBuilder csb = new StringBuilder();
		StringBuilder qsb = new StringBuilder();
//		StringBuilder sumsb = new StringBuilder(); //总计票价,总计手续费

		StringBuilder dealersb = new StringBuilder(); //经销商总手续费
		StringBuilder dealersb2 = new StringBuilder(); //，经销商总结算价
		StringBuilder scenicsb = new StringBuilder(); //景区总结算价，手续费
		StringBuilder sumrefund = new StringBuilder(); //总退款金额
		
		StringBuilder wsb = new StringBuilder();//条件
		
		qsb.append("select ");
	    qsb.append("tsp.baseInfo.name as policyName,");
	    qsb.append("tsp.baseInfo.key as policyNo,");
	    qsb.append("gt.ticketNo as ticketNo,");
	    qsb.append("gt.id as groupTicketId,");
	    qsb.append("st.id as singleTicketId,");
	    qsb.append("to.id as orderId,");
	    qsb.append("ss.baseInfo.name as scenicSpotNameStr,");
	    qsb.append("to.baseInfo.linkMan as linkMan,");
	    qsb.append("(select t.name from Tourist t where t.id = st.tourist.id )as touristName,");
	    qsb.append("to.baseInfo.createDate as createDate,");
	    qsb.append("to.payInfo.payDate as ticketDate,");
	    qsb.append("d.baseInfo.name as dealerName,");
	    qsb.append("d.accountInfo.accountType as dealerAccountType,");
	    qsb.append("d.accountInfo.accountNumber as dealerAccountNumber,");
//	    qsb.append("d.accountInfo.settlementFee as totalFee,");
	    qsb.append("st.settlementInfo.dealerPrice as dealerAmount,");
	    qsb.append("st.price as settlementPrice,");
	    qsb.append("st.settlementInfo.settlementFee as settlementFee,");
	    qsb.append("st.status.current as scenicStatus,");
	    qsb.append("gt.status.current as status,");
	    qsb.append("gt.status.refundDate as refundTime,");
	    qsb.append("gt.payTradeNo as payTradeNo,");
	    qsb.append("gt.refundBatchNo as refundBatchNo,");
	    qsb.append("st.settlementInfo.settlementDate as settleDate,");
	    qsb.append("st.useDateEnd as useDateEnd");
	    
	    csb.append("select ");
		csb.append("count(distinct st.id) ");
		
		
		dealersb.append("select sum(gt1.dealerSettlementFee)"); //总经销商手续费,经销商总结算价
		dealersb.append(" from GroupTicket gt1 where gt1.id in ");
		dealersb.append(" (select distinct gt.id ");
		
		dealersb2.append("select sum(st.settlementInfo.dealerPrice) ");
		
		scenicsb.append(" select");
		scenicsb.append(" sum(st.price),sum(st.settlementInfo.settlementFee) ");//景区总结算价，手续费

		sumrefund.append("select  sum(st.settlementInfo.dealerPrice) as refundPrice "); //总退款
		
		pub.append(" from ");
		pub.append("GroupTicket gt ");
		pub.append("join gt.ticketPolicySnapshot tsp ");
		pub.append("join gt.ticketOrder to ");
		pub.append("join to.baseInfo.fromDealer d ");
		pub.append("join gt.singleTickets st ");
//		pub.append("join st.ticketPolicySnapshot stsp ");
		pub.append("join st.scenicSpot ss ");
//		pub.append("join st.tourist t ");
		
		
		
		wsb.append("where 1=1 ");
		
		if(StringUtils.isNotBlank(qo.getDealerId()))
		{
			wsb.append(" and d.id=? ");  //经销商id
			params.add(qo.getDealerId());
		}
		
		if(StringUtils.isNotBlank(qo.getPolicyNo()))
		{
			wsb.append(" and tsp.baseInfo.key like ? ");  //产品编号
			params.add("%"+qo.getPolicyNo()+"%");
		}
		if(StringUtils.isNotBlank(qo.getPolicyName()))
		{
			wsb.append(" and tsp.baseInfo.name like ? ");  //产品名称
			params.add("%"+qo.getPolicyName()+"%");
		}
		if(StringUtils.isNotBlank(qo.getScenicSpotName()))
		{
			wsb.append(" and ss.baseInfo.name like ? ");  //景区名称
			params.add("%"+qo.getScenicSpotName()+"%");
		}
		if(StringUtils.isNotBlank(qo.getDealerName()))
		{
			wsb.append(" and d.baseInfo.name like ? ");  //经销商名称
			params.add("%"+qo.getDealerName()+"%");
		}
		if(qo.getStatus()!=null)
		{
			wsb.append(" and gt.status.current=? ");  //订单状态
			params.add(qo.getStatus());
		}
		if(qo.getScenicSpotStatus()!=null)
		{
			wsb.append(" and  st.status.current = ?"); //景区状态
			params.add(qo.getScenicSpotStatus());
		}
		if(StringUtils.isNotBlank(qo.getOrderId()))
		{
			wsb.append(" and to.id like ? ");  //订单编号
			params.add("%"+qo.getOrderId()+"%");
		}
		if(qo.getDateBegin()!=null )
		{
			wsb.append(" and  to.payInfo.payDate >= ? ");//出票开始时间
			params.add(qo.getDateBegin());
		}
		if( qo.getDateEnd()!=null)
		{
			wsb.append("and to.payInfo.payDate <= ? "); //出票结束时间
			params.add(qo.getDateEnd());
		}
		
		pub.append(wsb);
		qsb.append(pub);
		dealersb.append(pub).append(" and gt.status.current != 0 ) ");
		dealersb2.append(pub).append(" and gt.status.current != 0  ");
		scenicsb.append(pub).append(" and st.status.current in(2,8)  ");
		sumrefund.append(pub).append(" and st.status.current = 7");
		qsb.append("order by to.baseInfo.createDate desc");

		csb.append(pub);
		Pagination pagination = null;
		Long totalCount = 0L;
//		SettleDetailVo vo = new SettleDetailVo();
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
			Query scenicQuery = getSession().createQuery(scenicsb.toString());
			Query refundQuery = getSession().createQuery(sumrefund.toString());
			Query dealerQuery = getSession().createQuery(dealersb.toString());
			Query dealerQeury2  =getSession().createQuery(dealersb2.toString());
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
				dealerQuery.setParameter(i, params.get(i));
				scenicQuery.setParameter(i, params.get(i));
				refundQuery.setParameter(i, params.get(i));
				dealerQeury2.setParameter(i, params.get(i));
			}
			query.setResultTransformer(new AliasToBeanResultTransformer(FinanceVo.class));
			Double dealerFee =  (Double)  dealerQuery.uniqueResult();
			Object scenicFee[] = (Object[]) scenicQuery.uniqueResult();
			Double refundAmmount = (Double) refundQuery.uniqueResult();
			Double dealerPrice = (Double) dealerQeury2.uniqueResult();
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<FinanceVo> list = query.list();
			
			if(list.size()>0)
			{
				list.get(0).setTotalDealerAmount(dealerPrice==null?0:dealerPrice);
				list.get(0).setTotalDealerFee(dealerFee==null?0:dealerFee);
				list.get(0).setTotalScenicAmount((Double)scenicFee[0]==null?0:(Double)scenicFee[0]);
				list.get(0).setTotalScenicFee((Double)scenicFee[1]==null?0:(Double)scenicFee[1]);
				list.get(0).setRefundAmmount(refundAmmount);
				if(refundAmmount==null)
				{
					refundAmmount = 0d;
				}
				Double platformIncome = list.get(0).getTotalDealerAmount()-list.get(0).getTotalScenicAmount()
						+list.get(0).getTotalDealerFee()+list.get(0).getTotalScenicFee()-refundAmmount;
				list.get(0).setPlatformIncome(platformIncome);
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
}
