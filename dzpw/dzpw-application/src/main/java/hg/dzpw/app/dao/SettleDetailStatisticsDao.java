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
import hg.dzpw.pojo.qo.SettleDetailQo;
import hg.dzpw.pojo.vo.GroupTicketSaleStatisticsVo;
import hg.dzpw.pojo.vo.SettleDetailVo;
/**
 * 财务明细数据库操作
 * @author CaiHuan
 *
 * 日期:2015-12-24
 */
@Repository
public class SettleDetailStatisticsDao extends HibernateSimpleDao {

	/**
	 * 财务明细统计
	 * @author CaiHuan
	 * @param qo
	 * @return
	 */
	public Pagination querySettleDetail(SettleDetailQo qo, boolean selectAll)
	{
		// 不查询非闭合时间区间
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
		
		qsb.append("select ");
	    qsb.append("tsp.baseInfo.name as policyName,");
	    qsb.append("tsp.type as ticketPolicyType,");
	    qsb.append("gt.ticketNo as ticketNo,");
	    qsb.append("t.name as touristName,");
	    qsb.append("t.idNo as tourisIdNo,");
	    qsb.append("to.id as orderId,");
//	    qsb.append("stp.baseInfo.settlementPrice as scenicSpotAmount,");
	    qsb.append("stk.price as scenicSpotAmount,");
	    qsb.append("stk.status.current as scenicSpotStatus,");
	    qsb.append("to.payInfo.payDate as payDate,");
	    qsb.append("stk.settlementInfo.settlementFee as amountFee,");
	    qsb.append("stk.settlementInfo.settlementDate as settlementDate ");
	    
	    csb.append("select ");
		csb.append("count(distinct stk.id) ");
		
		sumsb.append("select ");
		sumsb.append(" sum(stk.price) as totalAmount,sum(stk.settlementInfo.settlementFee) as totalFee ");
		
		pub.append(" from ");
		pub.append("SingleTicket stk ");
		pub.append("join stk.tourist t ");
		pub.append("join stk.groupTicket gt ");
		pub.append("join gt.ticketPolicySnapshot tsp ");
//		pub.append("join stk.ticketPolicySnapshot stp ");
		pub.append("join stk.scenicSpot ss ");
		pub.append("join gt.ticketOrder to ");
		pub.append("where 1=1 and stk.status.current in (2,8) ");
		
		//出票时间设置
		if(qo.getDateBegin()!=null )
		{
			pub.append(" and  to.payInfo.payDate >= ? ");
			params.add(qo.getDateBegin());
		}
		if( qo.getDateEnd()!=null)
		{
			pub.append("and to.payInfo.payDate <= ? ");
			params.add(qo.getDateEnd());
		}
		
		
		
		
		if(StringUtils.isNotBlank(qo.getScenicSpotId()))
		{
			pub.append(" and ss.id=? ");  //景区id
			params.add(qo.getScenicSpotId());
		}
		if (qo.getTicketType()!=null){
			pub.append(" and tsp.type=? ");  //查询单票、联票
			params.add(qo.getTicketType());
		}
		if(qo.getScenicSpotStatus()!=null)
		{
			pub.append(" and stk.status.current=? ");  //景区状态
			params.add(qo.getScenicSpotStatus());
		}
		
		qsb.append(pub);
		qsb.append("order by to.baseInfo.createDate desc");

		csb.append(pub);
		sumsb.append(pub);
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
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
				sumQuery.setParameter(i, params.get(i));
			}
			query.setResultTransformer(new AliasToBeanResultTransformer(SettleDetailVo.class));
			Object[] obj = (Object[]) sumQuery.uniqueResult();
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<SettleDetailVo> list = query.list();
			
			if(list.size()>0)
			{
				list.get(0).setTotalPrice((Double)obj[0]);
				list.get(0).setTotalFee((Double)obj[1]);
				if(list.get(0).getTotalPrice()!=null && list.get(0).getTotalFee()!=null)
				{
					list.get(0).setTotalAmount(list.get(0).getTotalPrice()-list.get(0).getTotalFee());
				}
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
