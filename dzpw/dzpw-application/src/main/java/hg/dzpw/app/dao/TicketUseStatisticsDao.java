package hg.dzpw.app.dao;

import hg.common.component.hibernate.HibernateSimpleDao;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.dzpw.app.common.util.DateUtils;
import hg.dzpw.pojo.qo.GroupTicketUseStatisticsQo;
import hg.dzpw.pojo.qo.ScenicSpotUseStatisticsQo;
import hg.dzpw.pojo.qo.TicketUsedTouristDetailQo;
import hg.dzpw.pojo.vo.GroupTicketUseStatisticsVo;
import hg.dzpw.pojo.vo.ScenicSpotUseStatisticsVo;
import hg.dzpw.pojo.vo.TicketUsedTouristDetailVo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：入园统计数据库操作
 * @类修改者：
 * @修改日期：2014-11-20下午5:10:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午5:10:03
 */
@Repository
public class TicketUseStatisticsDao extends HibernateSimpleDao {
	
	/**
	 * @方法功能说明：联票入园统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午4:59:31
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see GroupTicketUseStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryGroupTicketUseStatistics(GroupTicketUseStatisticsQo qo, boolean selectAll) {

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
		qsb.append("gtkPolicy.id as ticketPolicyId, ");
		qsb.append("gtkPolicy.baseInfo.name as ticketPolicyName, ");
		qsb.append("count(gtkPolicy.id) as useCount ");

		csb.append("select ");
		csb.append("count(distinct gtkPolicy.id) ");
		
		pub.append("from ");
		pub.append("UseRecord ur, ");
		pub.append("TicketOrder to ");
		pub.append("join to.groupTickets gtk ");
		pub.append("join to.ticketPolicy gtkPolicy ");
		pub.append("where ");
		pub.append("ur.groupTicketId=gtk.id ");
		pub.append("and to.baseInfo.createDate >= ? and to.baseInfo.createDate <= ? ");
		
		params.add(qo.getOrderDateBegin());
		params.add(qo.getOrderDateEnd());
		
		if (StringUtils.isNotBlank(qo.getTicketPolicyId())) {
			pub.append("and gtkPolicy.id=? ");
			params.add(qo.getTicketPolicyId());
		}
		if (StringUtils.isNotBlank(qo.getTicketPolicyName())) {
			pub.append("and (gtkPolicy.baseInfo.name like ? or gtkPolicy.baseInfo.shortName like ?)");
			params.add("%" + qo.getTicketPolicyName() + "%");
			params.add("%" + qo.getTicketPolicyName() + "%");
		}
		
		qsb.append(pub);
		qsb.append("group by gtkPolicy.id ");
		qsb.append("order by useCount desc");
		
		csb.append(pub);
		

		Long totalCount = 0L;
		Pagination pagination = null;

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
			query.setResultTransformer(new AliasToBeanResultTransformer(GroupTicketUseStatisticsVo.class));
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<GroupTicketUseStatisticsVo> list = query.list();
			for (int i = 0; i < list.size(); i++) {
				GroupTicketUseStatisticsVo vo = list.get(i);
				vo.setDayCount(vo.getUseCount() * 1d / days);
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
	 * @方法功能说明：景区入园统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午4:59:54
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see ScenicSpotUseStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryScenicSpotUseStatistics(ScenicSpotUseStatisticsQo qo, boolean selectAll) {

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
		StringBuilder csb = new StringBuilder(); //入园总次数
		StringBuilder qsb = new StringBuilder();
		StringBuilder countSingle  = new StringBuilder(); //单票张数
		StringBuilder countGroup = new StringBuilder(); //联票张数
		
		qsb.append("select ");
		qsb.append("ss.id as scenicSpotId, ");
		qsb.append("gtk.id as groupTicketId, ");
		qsb.append("tsp.baseInfo.name as policyName, ");
		qsb.append("t.name as touristName, ");
		qsb.append("t.idNo as tourisIdNo, ");
		qsb.append("ss.baseInfo.name as scenicSpotName, ");
		qsb.append("to.id as orderId, ");
//		qsb.append("count(ss.id) as useCount, ");
		qsb.append("tsp.type as ticketPolicyType ,");
		qsb.append("ur.useDate as enterDate ,");
		qsb.append("gtk.ticketNo as ticketNo ");

		
		csb.append("select ");
		csb.append("count(distinct ur.id) ");
		
		countSingle.append("select ");
		countSingle.append("count(distinct gtk.ticketNo)");
		
		countGroup.append("select ");
		countGroup.append("count(distinct gtk.ticketNo)");
		
		pub.append("from ");
		pub.append("UseRecord ur, ScenicSpot ss, ");
		pub.append("GroupTicket gtk , SingleTicket stks ");
		pub.append("join gtk.ticketOrder to ");
		pub.append("join gtk.ticketPolicySnapshot tsp ");
		pub.append("join stks.tourist t ");
		pub.append("where ");
		pub.append("ur.scenicSpotId=ss.id and ur.groupTicketId = gtk.id and ur.singleTicketId = stks.id ");
//		pub.append("and to.baseInfo.createDate >= ? and to.baseInfo.createDate <= ? ");
		if (qo.getOrderDateBegin()!=null) {
			pub.append("and ur.useDate >= ? ");
			params.add(qo.getOrderDateBegin());
		}
		if (qo.getOrderDateEnd()!=null) {
			pub.append("and ur.useDate <= ? ");
			params.add(qo.getOrderDateEnd());
		}
		
		if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
			pub.append("and ss.id=? ");
			params.add(qo.getScenicSpotId());
		} 
		if (StringUtils.isNotBlank(qo.getScenicSpotName())) {
			pub.append("and (ss.baseInfo.name like ? or ss.baseInfo.shortName like ?) ");
			params.add("%" + qo.getScenicSpotName() + "%");
			params.add("%" + qo.getScenicSpotName() + "%");
		}
		if (qo.getTicketPolicyType()!=null){
			pub.append("and tsp.type=?");
			params.add(qo.getTicketPolicyType());
		}
		if(StringUtils.isNotBlank(qo.getOrderId()))
		{
			pub.append("and to.id like ?  ");
			params.add("%" + qo.getOrderId() + "%");
		}
		if(StringUtils.isNotBlank(qo.getTicketNo()))
		{
			pub.append("and gtk.ticketNo like ?  ");
			params.add("%" + qo.getTicketNo() + "%");
		}
		
		qsb.append(pub);
//		qsb.append("group by ur.scenicSpotId ");
		qsb.append("order by ur.useDate desc");
		
		
		csb.append(pub);
		
		countSingle.append(pub).append(" and tsp.type=1");
		countGroup.append(pub).append(" and tsp.type=2");
		
		Long totalCount = 0L;
		Pagination pagination = null;
		Integer singleCount = 0;
		Integer groupCount = 0;
	
			Query countQuery = getSession().createQuery(csb.toString());
			
			
			for (int i = 0; i < params.size(); i++) {
				countQuery.setParameter(i, params.get(i));
			}
			totalCount = (Long) countQuery.uniqueResult();
			
			pagination = new Pagination(qo.getPageNo(), qo.getPageSize(), totalCount.intValue());

		if (selectAll || (!selectAll && totalCount > 0)) {

			Query csquery = getSession().createQuery(countSingle.toString());
			Query cgquery = getSession().createQuery(countGroup.toString());
			Query query = getSession().createQuery(qsb.toString());
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
				csquery.setParameter(i, params.get(i));
				cgquery.setParameter(i, params.get(i));
			}
			singleCount = ((Long) csquery.uniqueResult()).intValue();
			groupCount = ((Long) cgquery.uniqueResult()).intValue();
			query.setResultTransformer(new AliasToBeanResultTransformer(ScenicSpotUseStatisticsVo.class));
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			@SuppressWarnings("unchecked")
			List<ScenicSpotUseStatisticsVo> list = query.list();
			/*for (int i = 0; i < list.size(); i++) {
				ScenicSpotUseStatisticsVo vo = list.get(i);
				vo.setDayCount(vo.getUseCount() * 1d / days);
				if (selectAll)
					vo.setRank(i + 1);
				else
					vo.setRank(pagination.getFirstResult() + i + 1);
			}*/
			if(list.size()>0)
			{
			list.get(0).setSigleTicketNum(singleCount);
			list.get(0).setGroupTicketNum(groupCount);
			list.get(0).setUseCount(totalCount);
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
	 * @方法功能说明：入园用户明细查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午5:04:30
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param selectAll
	 * @参数：@return @see TicketUsedTouristDetailVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryTicketUsedTouristDetail(TicketUsedTouristDetailQo qo, boolean selectAll) {
		
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
		qsb.append("gtkPolicy.id as ticketPolicyId, ");
		qsb.append("ss.id as scenicSpotId, ");
		qsb.append("t.idType as cerType, ");
		qsb.append("t.idNo as cerNo, ");
		qsb.append("t.name as name, ");
		qsb.append("gtkPolicy.baseInfo.name as ticketPolicyName, ");
		qsb.append("ss.baseInfo.name as scenicSpotName ");
		
		csb.append("select ");
		csb.append("count(distinct ur.id) ");
		
		pub.append("from ");
		pub.append("UseRecord ur, ScenicSpot ss, ");
		pub.append("TicketOrder to ");
		pub.append("join to.groupTickets gtks ");
		pub.append("join gtks.tourist t ");
		pub.append("join to.ticketPolicy gtkPolicy ");
		pub.append("where ");
		pub.append("ur.scenicSpotId=ss.id and ur.groupTicketId=gtks.id ");
//		pub.append("and to.baseInfo.createDate >= ? and to.baseInfo.createDate <= ? ");
		pub.append("and ur.useDate >= ? and ur.useDate <= ? ");
		
		params.add(qo.getOrderDateBegin());
		params.add(qo.getOrderDateEnd());
		
		if (StringUtils.isNotBlank(qo.getName())) {
			pub.append("and t.name like ? ");
			params.add("%" + qo.getName() + "%");
		}
		if (StringUtils.isNotBlank(qo.getCerNo())) {
			pub.append("and t.idNo like ? ");
			params.add("%" + qo.getCerNo() + "%");
		}
		if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
			pub.append("and ur.scenicSpotId=? ");
			params.add(qo.getScenicSpotId());
		}
		if (StringUtils.isNotBlank(qo.getTicketPolicyId())) {
			pub.append("and gtkPolicy.id=? ");
			params.add(qo.getTicketPolicyId());
		}
		if (qo.getTicketPolicyType()!=null){
			pub.append("and gtks.type=?");
			params.add(qo.getTicketPolicyType());
		}
		
		qsb.append(pub);
		qsb.append("group by ur.scenicSpotId, gtkPolicy.id, t.id ");
		qsb.append("order by ur.useDate desc");
		
		csb.append(pub);
		
		Long totalCount = 0L;
		Pagination pagination = null;
		
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
			query.setResultTransformer(new AliasToBeanResultTransformer(TicketUsedTouristDetailVo.class));
			if (!selectAll) {
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(qo.getPageSize());
			}
			
			@SuppressWarnings("unchecked")
			List<TicketUsedTouristDetailVo> list = query.list();
			
			// 当前列表中的游客
			Set<String> touristIds = new HashSet<String>();
			for (TicketUsedTouristDetailVo vo : list) {
				touristIds.add(vo.getTouristId());
			}

			// 查询入园时间
			if (touristIds.size() > 0) {
				String hql = "select " +
							"gtks.tourist.id, " +
							"ur.scenicSpotId, " +
							"to.ticketPolicy.id, " +
							"ur.useDate " +
						"from " +
							"UseRecord ur, TicketOrder to " +
							"join to.groupTickets gtks " +
						"where " +
							"ur.groupTicketId=gtks.id " +
//							"and to.baseInfo.createDate >= ? and to.baseInfo.createDate <= ? " +
							"and ur.useDate >= ? and ur.useDate <= ? " +
							"and gtks.tourist.id in (:idlist) " +
						"order by ur.useDate desc";
				Query useDateQuery = getSession().createQuery(hql);
				useDateQuery.setParameter(0, qo.getOrderDateBegin());
				useDateQuery.setParameter(1, qo.getOrderDateEnd());
				useDateQuery.setParameterList("idlist", touristIds);

				Map<String, List<Date>> map = new HashMap<String, List<Date>>();
				@SuppressWarnings("unchecked")
				List<Object[]> useList = useDateQuery.list();
				for (Object[] objs : useList) {
					String touristId = (String) objs[0];
					String scenicSpotId = (String) objs[1];
					String ticketPolicyId = (String) objs[2];
					Date useDate = (Date) objs[3];
					String key = getkey(touristId, scenicSpotId, ticketPolicyId);
					if (!map.containsKey(key))
						map.put(key, new ArrayList<Date>());
					map.get(key).add(useDate);
				}
				
				for (TicketUsedTouristDetailVo vo : list) {
					vo.setUseDates(map.get(getkey(vo.getTouristId(), vo.getScenicSpotId(), vo.getTicketPolicyId())));
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
	
	private String getkey(String touristId, String scenicSpotId, String ticketPolicyId) {
		StringBuilder key = new StringBuilder();
		key.append(touristId).append("_").append(scenicSpotId).append("_")
				.append(ticketPolicyId);
		return key.toString();
	}
}
