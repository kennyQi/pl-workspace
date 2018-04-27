/**
 * @文件名称：UserDaoImp.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：流水dao
 * @作者：xinglj
 * @时间：2014年10月31日上午10:14:08
 */
package hgtech.jfadmin.dao.imp;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hg.common.component.hibernate.Finder;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.StringUtil;
import hg.common.util.UUIDGenerator;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jfaccount.BStatAccount;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfaccount.TradeType;
import hgtech.jfaccount.dao.FlowDao;
import hgtech.jfaccount.listener.FlowListener;
import hgtech.jfadmin.dao.RuleDao;
import hgtech.jfadmin.dto.*;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.util.GroupUtil;
import hgtech.util.GroupUtil.FlowDefine;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * @类功能说明：积分流水的dao
 * @类修改者：
 * @修改日期：2014年10月31日上午10:14:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月31日上午10:14:08
 * 
 */
@SuppressWarnings("rawtypes")
@Repository("flowDao")
public class FlowDaoImp extends BaseEntityHiberDao<StringUK, JfFlow> implements
		FlowDao {
	private static final String ORDER_BY = " order by ";

	private static final int ONE_DAY_TIME = 24 * 60 * 60 * 1000;

	public FlowDaoImp() {
		super(JfFlow.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hgtech.jfadmin.dao.imp.BaseEntityHiberDao#beforeSaveEntity(hgtech.jf.
	 * entity.Entity, java.lang.Object)
	 */
	@Override
	protected void beforeSaveEntity(JfFlow pojo) {
		JfFlow flow = (JfFlow) pojo;
		if (flow.flowId == null)
			flow.flowId = new StringUK(UUIDGenerator.getUUID());
		flow.user = pojo.account.getUser();

	}

	/**
	 * 积分流水分页查询实现类
	 */
	@Override
	public Pagination findPagination(Pagination pagination) {

		try {
			Criteria criteria = super.getSession().createCriteria(JfFlow.class);
			if (pagination.getCondition() != null
					&& pagination.getCondition() instanceof showDto) {
				showDto condition = (showDto) pagination.getCondition();
				if (null != condition.getCode()
						&& !"".equals(condition.getCode())) {
					criteria.add(Restrictions.eq("user", condition.getCode()));
				}
				if (null != condition.getBatchNo()
						&& !"".equals(condition.getBatchNo())) {
					criteria.add(Restrictions.eq("batchNo", condition.getBatchNo()));
				}				
				if (null != condition.getStatus()
						&& !"".equals(condition.getStatus())) {
					criteria.add(Restrictions.eq("status",
							condition.getStatus()));
				}
				if ((null != condition.getStartDate())||(null != condition.getEndDate())) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String strBeginTime;
					String strEndTime;
					if(StringUtils.isBlank(condition.getStartDate())){
						strBeginTime = "2000-01-01";
					}else{
						strBeginTime = condition.getStartDate();
					}
					if(StringUtils.isBlank(condition.getEndDate())){
						strEndTime = "3000-01-01";
					}else{
						strEndTime = condition.getEndDate();
					}
					try {
						Date beginTime = sdf.parse(strBeginTime);
						Date endTime = sdf.parse(strEndTime);
						Calendar ca = Calendar.getInstance();
						ca.setTime(endTime);
						ca.add(ca.DATE, 1);
						endTime = ca.getTime();
						criteria.add(Restrictions.between("insertDate",
								beginTime, endTime));
						// criteria.add(Restrictions.ge("calTime", beginTime));
						// criteria.add(Restrictions.le("calTime", endTime));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getType())){
					criteria.add(Restrictions.eq("tradeType",JfTradeType.findType(condition.getType())));
				}
				if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getTradeRemark())){
					criteria.add(Restrictions.like("detail","%"+condition.getTradeRemark()+"%"));
				}
				criteria.addOrder(Order.asc("insertTime"));
			}
			return super.findByCriteria(criteria, pagination.getPageNo(),
					pagination.getPageSize());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 转入统计查询
	 */
	@Override
	public List<BStatAccount> transferInStat(Object condition) {
		String tradeType = JfTradeType.in.code;
		TransferStatQo dto = (TransferStatQo) condition;
		return transferStat(tradeType, dto);

	}

	/**
	 * 转出统计查询
	 */
	@Override
	public List<BStatAccount> transferOutStat(Object condition) {
		String tradeType = JfTradeType.out.code;
		TransferStatQo dto = (TransferStatQo) condition;
		return transferStat(tradeType, dto);

	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日上午9:45:10
	 * @version：
	 * @修改内容：
	 * @参数：@param tradeType
	 * @参数：@param dto
	 * @参数：@return
	 * @return:List<BStatAccount>
	 * @throws
	 */
	List<BStatAccount> transferStat(String tradeType, TransferStatQo dto) {
		try {
			JfFlow f;
			Map<String, BStatAccount> dataMap = new HashMap<String, BStatAccount>();

			transferStat4Status(dto, dataMap, tradeType, JfFlow.NOR);
			transferStat4Status(dto, dataMap, tradeType, JfFlow.ARRVING);
			List ret = new ArrayList<>();
			ret.addAll(dataMap.values());
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/**
	 * 转入查询
	 */
	@Override
	public List<JfFlow> transferInMemo(Object condition) {
		TransferStatQo dto = (TransferStatQo) condition;
		try {
			JfFlow f;
			// String tradeType = JfTradeType.out.code;
			JfTradeType.init();
			String status = JfFlow.NOR;

			List ret = new ArrayList<>();
			ret = transferQuery(dto, JfTradeType.in);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();

	}

	/**
	 * 转出查询
	 */
	@Override
	public List<JfFlow> transferOutMemo(Object condition) {
		TransferStatQo dto = (TransferStatQo) condition;
		try {
			JfFlow f;

			JfTradeType.init();

			List ret = new ArrayList<>();
			ret = transferQuery(dto, JfTradeType.out);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();

	}

	@Autowired
	RuleDao ruleDao;
	/**
	 * @return
	 * @throws ParseException
	 * @方法功能说明：明细查询
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月4日上午9:59:54
	 * @version：
	 * @修改内容：
	 * @参数：@param dto
	 * @参数：@param dataMap
	 * @参数：@param tradeType null，表示所有明细
	 * @参数：@param status
	 * @return:void
	 * @throws
	 */
	List<JfFlow> transferQuery(TransferStatQo dto, TradeType tradeType)
			throws ParseException {
		QueryData data = genQuery(dto, tradeType);

		List<JfFlow> list = createQuery(data.hql, data.params).list();

		for (JfFlow flow : list) {
			flow.account.syncUK();
			if (flow.getMerchandise() != null) {
				JfAccountType obj = SetupAccountContext.findType(flow
						.getMerchandise());
				if (obj == null)
					obj = SetupAccountContext.accTypeHjf;
				flow.setMerchandiseObj(obj);
			}
			
		}
		super.getSession().clear();
		return list;
	}

	public Pagination transferQuery(TransferStatQo dto, TradeType tradeType,
			int pageSize, int pageNo) throws ParseException {
		QueryData data = genQuery(dto, tradeType);

		MyFinder finder = (MyFinder) MyFinder.create(data.hql);
		finder.setParams(data.params);
		Pagination find = find(finder, pageNo, pageSize);

		List<JfFlow> list = (List<JfFlow>) find.getList();
		// createQuery(data.hql, data.params).list();

		for (JfFlow flow : list) {
			flow.account.syncUK();
			if (flow.getMerchandise() != null) {
				JfAccountType obj = SetupAccountContext.findType(flow
						.getMerchandise());
				if (obj == null)
					obj = SetupAccountContext.accTypeHjf;
				flow.setMerchandiseObj(obj);
			}
		}
		super.getSession().clear();
		return find;
	}

	private QueryData genQuery(TransferStatQo dto, TradeType tradeType)
			throws ParseException {
		boolean isTransfer = JfTradeType.in.equals(tradeType)
				|| JfTradeType.out.equals(tradeType);

		JfFlow f;
		LinkedList para = new LinkedList<>();
		String hql = " from " + JfFlow.class.getName() + " o where 1=1 ";
		if (tradeType != null) {
			if (isTransfer) {
				// 因为商城和互转公用交易码，所以另加条件将商城排除
				hql += " and o.merchandise is not null";
			}
			if (tradeType.equals(JfTradeType.in)) {
				// 转入查询，其实应该是 交易码：转入、转入生效
				hql += " and (o.tradeType = ? or  (o.tradeType=? and o.oldTradeType =?) )";
				para.add(JfTradeType.in);
				para.add(JfTradeType.ARRIVED);
				para.add(JfTradeType.in);
			} else {
				hql += " and o.tradeType= ?";
				para.add(tradeType);
			}
		}

		boolean notEmptyName = StringUtils.isNotEmpty(dto.getCzName());
		boolean notEmptyCard = StringUtils.isNotEmpty(dto.getCzCard());
		if (notEmptyCard && notEmptyName) {
			hql += " and o.merchant like ?";
			para.add("%" + dto.getCzCard() + "%" + dto.getCzName() + "%:%");

		} else if (notEmptyName) {
			hql += " and o.merchant like ?";
			para.add("%:%" + dto.getCzName() + "%:%");

		} else if (notEmptyCard) {
			hql += " and o.merchant like ?";
			para.add("%" + dto.getCzCard() + "%:%:%");

		}

		if (dto.getStatusCheck() != null) {
			hql += " and o.statusCheck = ?";
			para.add(dto.getStatusCheck());
		}

		if (StringUtils.isNotEmpty(dto.getDomain())) {
			hql += " and o.merchandise like ?";
			para.add(dto.getDomain() + "__%");
		}

		if (StringUtils.isNotEmpty(dto.getStatus())) {
			hql += " and o.status = ?";
			para.add(dto.getStatus());
		} else {
			/*
			 * hql += " and o.status in (?,?,?,?)"; para.add(JfFlow.NOR);
			 * para.add(JfFlow.ARRVING); para.add(JfFlow.STATUS_NOREPLY);
			 * para.add(JfFlow.STATUS_NOREPLY_END);
			 */
			if (JfTradeType.in.equals(tradeType)) {
				hql += " and o.status in (?,?)";
				para.add(JfFlow.NOR);
				para.add(JfFlow.ARRVING);
				// para.add(JfFlow.ARRIVED);
			} else if (JfTradeType.out.equals(tradeType)) {
				hql += " and o.status in (?,?)";
				para.add(JfFlow.NOR);
				para.add(JfFlow.UNDO);// 用户的转出要看到撤销的
			} else {

			}
		}
		if (StringUtils.isNotEmpty(dto.getMerchandiseStatus())) {
			hql += " and o.merchandiseStatus = ?";
			para.add(dto.getMerchandiseStatus());
		}

		if (StringUtils.isNotEmpty(dto.getSendStatus())) {
			hql += " and o.sendStatus = ?";
			para.add(dto.getSendStatus());
		}

		if (StringUtils.isNotEmpty(dto.getAcctType())) {
			if (isTransfer) {
				hql += " and o.merchandise = ?";
				para.add(dto.getAcctType());
			} else {
				hql += " and o.account.acct_type='" + dto.getAcctType() + "'";
			}
		}

		if (StringUtils.isNotEmpty(dto.getDomainLogin())) {
			hql += " and o.merchant like ?";
			para.add("%" + dto.getDomainLogin() + "%");
		}

		if (StringUtils.isNotEmpty(dto.getFromDate())) {
			hql += " and o.insertDate >= ?";
			para.add(df.parse(dto.getFromDate()));
		}
		if (StringUtils.isNotEmpty(dto.getToDate())) {
			hql += " and o.insertDate <= ?";
			para.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dto
					.getToDate() + " 23:59:59"));
		}
		if (StringUtils.isNotEmpty(dto.getUserCode())) {
			// hql += " and o.user = ?";
			hql += " and o.account.user= ?";// 使用账户中的手机号关联，不再使用流水中手机号查询。有更换手机的情况
			para.add(dto.getUserCode());
		}
		String orderString="";
		if(StringUtils.isNotEmpty(dto.getOrderFiled())&&StringUtils.isNotEmpty(dto.getOrderDirection())){
			if(dto.getOrderFiled().equals("userName")){
				orderString = "convert(substring(o.merchant,LOCATE(':',o.merchant)+1,LENGTH(o.merchant)))"+dto.getOrderDirection();
			}else if(dto.getOrderFiled().equals("cardId")){
				orderString = "substring(o.merchant,1,LOCATE(':',o.merchant)-1) "+dto.getOrderDirection();
			}else{
				orderString=" o.insertTime desc ";
			}
		}else{
			orderString=" o.insertTime desc ";
		}
		hql += ORDER_BY + orderString;
		System.out.println(hql);
		Object arr[] = new Object[para.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = para.get(i);

		QueryData data = new QueryData();
		data.hql = hql;
		data.params = arr;
		return data;
	}

	/**
	 * @throws ParseException
	 * @方法功能说明：一个状态的积分求和
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月4日上午9:59:54
	 * @version：
	 * @修改内容：
	 * @参数：@param dto
	 * @参数：@param dataMap
	 * @参数：@param tradeType
	 * @参数：@param status
	 * @return:void
	 * @throws
	 */
	void transferStat4Status(TransferStatQo dto,
			Map<String, BStatAccount> dataMap, String tradeType, String status)
			throws ParseException {
		JfFlow f;
		LinkedList para = new LinkedList<>();
		String hql = "select o.merchandise,sum(o.jf), sum(o.fee), sum(o.merchandiseAmount) from "
				+ JfFlow.class.getName()
				+ " o where o.merchandise is not null  and o.tradeType='"
				+ tradeType + "' and o.status = '" + status + "'";
		if (StringUtils.isNotEmpty(dto.getStatus())) {
			hql += " and o.status = ?";
			para.add(dto.getStatus());
		}
		if (StringUtils.isNotEmpty(dto.getDomain())) {
			hql += " and o.merchandise like ?";
			para.add(dto.getDomain() + "__%");
		}
		if (StringUtils.isNotEmpty(dto.getAcctType())) {
			hql += " and o.merchandise = ?";
			para.add(dto.getAcctType());
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(dto.getFromDate())) {
			hql += " and o.insertDate >= ?";
			para.add(df.parse(dto.getFromDate()));
		}
		if (StringUtils.isNotEmpty(dto.getToDate())) {
			hql += " and o.insertDate <= ?";
			para.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dto
					.getToDate() + " 23:59:59"));
		}
		hql += " group by o.merchandise ";
		Object arr[] = new Object[para.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = para.get(i);

		List<Object[]> list = createQuery(hql, arr).list();
		for (Object[] row : list) {
			String acctType = row[0].toString();
			BStatAccount flow = dataMap.containsKey(acctType) ? dataMap
					.get(acctType) : new BStatAccount();
			flow.setAccountType(SetupAccountContext.findType(acctType));
			if (status == JfFlow.NOR)
				// 可用积分
				flow.jf = ((Number) row[1]).intValue();
			else
				// 在途积分
				flow.jfTODO += ((Number) row[1]).intValue();

			flow.fee += ((Number) row[2]).intValue();
			flow.objJf += ((Number) row[3]).intValue();
			dataMap.put(acctType, flow);
		}
	}

	@Override
	public List<JfFlow> getjfFlowList(Pagination pagination) {

		try {
			Criteria criteria = super.getSession().createCriteria(JfFlow.class);
			showDto dto = (showDto) pagination.getCondition();
			if (dto != null) {
				criteria.add(Restrictions.eq("user", dto.getCode()));
				if ((null != dto.getStartDate() && !"".equals(dto
						.getStartDate()))) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					String strBeginTime = dto.getStartDate();
					String strEndTime = dto.getEndDate();
					try {
						Date beginTime = sdf.parse(strBeginTime);
						Date endTime = sdf.parse(strEndTime);
						Calendar ca = Calendar.getInstance();
						ca.setTime(endTime);
						ca.add(ca.DATE, 1);
						endTime = ca.getTime();
						criteria.add(Restrictions.between("insertDate",
								beginTime, endTime));
						// criteria.add(Restrictions.ge("calTime", beginTime));
						// criteria.add(Restrictions.le("calTime", endTime));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			return (List<JfFlow>) criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hgtech.jfaccount.dao.FlowDao#getGotList(hgtech.jfaccount.JfAccount)
	 */
	@Override
	public ScrollableResults getGotList(String acctId) {
		// Criteria criteria = super.getSession().createCriteria(JfFlow.class);
		// criteria.add(Restrictions.eq("user", user));
		// criteria.add(Restrictions.gt("jf-usejf", 0));
		// criteria.add(Restrictions.or(Restrictions.eq("status", JfFlow.NOR),
		// Restrictions.isNull("status")));
		// criteria.addOrder(Order.asc("invalidDate"));
		ScrollableResults list = getSession()
				.createSQLQuery(
				// "select id, trade_amount,use_amount from jf_flow where user='"
				// +
				// user+"' and (`status`='NOR' or `status` is null) and trade_amount>0 and trade_amount-use_amount>0   order by jf_flow.invalid_date ")
						"select id, trade_amount,use_amount from jf_flow where acct_id='"
								+ acctId
								+ "' and can_consume=1   order by jf_flow.invalid_date ")
				.scroll();
		return list;
	}

	/**
	 * 查询对南航的某卡号互换
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年8月31日下午1:33:51
	 * @version：
	 * @修改内容：
	 * @参数：@param batchno
	 * @参数：@param user
	 * @参数：@return
	 * @return:JfFlow
	 * @throws
	 */
	@Override
	public List<JfFlow> queryCzFlow(String batchno, String czCardNo) {
		return getSession()
				.createQuery("from JfFlow where batchNo=? and merchant like ?")
				.setParameter(0, batchno).setParameter(1, czCardNo + ":%")
				.list();
	}

	@Override
	public JfFlow getFlowByRefFlowId(String refFlowId) {
		String hql = "from JfFlow where refFlowId=?";

		Query query = super.getSession().createQuery(hql);
		query.setString(0, refFlowId);
		List<JfFlow> list = query.list();
		JfFlow flow = list.get(0);

		return flow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hgtech.jfaccount.dao.FlowDao#queryArrivingBefore(int)
	 */
	@Override
	public List<JfFlow> queryArrivingBefore(JfAccountType accountType) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String theday = new SimpleDateFormat("yyyy-MM-dd")
				.format(System.currentTimeMillis() /*- accountType.toHjf.validDay * ONE_DAY_TIME*/);
		Date d1;
		Date d2;
		try {
			String sd1 = theday + " 00:00:00";
			d1 = df.parse(sd1);
			String sd2 = theday + " 23:59:59";
			d2 = df.parse(sd2);
			String hql = "from JfFlow where status='"
					+ JfFlow.ARRVING
					+ "' and validDate between '%s' and '%s' and merchandise= '%s'";
			hql = String.format(hql, sd1, sd2, accountType.getCode());
			return getSession().createQuery(hql)
			// .setDate(0, d1)
			// .setDate(1, d2)
			// .setString(2, accountType.getCode())
					.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();

	}

	@Override
	public List<JfFlow> queryArriving4Other() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String theday = new SimpleDateFormat("yyyy-MM-dd")
				.format(System.currentTimeMillis() /*- accountType.toHjf.validDay * ONE_DAY_TIME*/);
		Date d1;
		Date d2;
		try {
			String sd1 = theday + " 00:00:00";
			d1 = df.parse(sd1);
			String sd2 = theday + " 23:59:59";
			d2 = df.parse(sd2);
			String hql = "from JfFlow where status='" + JfFlow.ARRVING
					+ "' and validDate between '%s' and '%s' ";
			hql = String.format(hql, sd1, sd2);
			return getSession().createQuery(hql)
			// .setDate(0, d1)
			// .setDate(1, d2)
			// .setString(2, accountType.getCode())
					.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/**
	 * 所有明细
	 */
	@Override
	public List transferMemo(Object condition) {
		TransferStatQo dto = (TransferStatQo) condition;
		try {
			JfFlow f;
			List ret = new ArrayList<>();
			ret = transferQuery(dto, null);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public JfAccount memoTotal(Object condition) {
		TransferStatQo dto = (TransferStatQo) condition;

		try {
			QueryData data = genQuery(dto, null);
			String hql = data.hql;
			if (hql.contains(ORDER_BY))
				hql = hql.substring(0, hql.indexOf(ORDER_BY));

			// 分别汇总:收入，支出，在途。可能更慢
			throw new RuntimeException("not yet");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 所有明细,with page
	 * 
	 * @param pageNo
	 * @param pageSize
	 */
	@Override
	public Pagination allMemoPage(Object condition, int pageNo, int pageSize) {
		TransferStatQo dto = (TransferStatQo) condition;
		try {
			JfFlow f;
			return transferQuery(dto, null, pageSize, pageNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Pagination(pageNo, pageSize, 0);
	}

	@Override
	public JfFlow get(String id) {
		return super.get(new StringUK(id));
	}

	@Override
	public List<String> findMerchantByUser(String user) {
		String hql = "select distinct o.merchant from JfFlow as o where user=? and merchandiseStatus='NOR'";

		Query query = super.getSession().createQuery(hql);
		query.setString(0, user);
		return query.list();
	}

 

	@Override
	public List<JfFlow> queryEx() {
		String hql = "from JfFlow where (tradeType = 'EX' or tradeType = 'UNDO') and resultStatus is null and TO_DAYS(NOW()) - TO_DAYS(insertDate) = 1 and status not in "
				+ notInString();
		List<JfFlow> list = getSession().createQuery(hql).list();
		return list;
	}

	@Override
	public List<JfFlow> queryIn() {
		String hql = "from JfFlow where status = 'NOR' and tradeType = 'IN' and resultStatus is null and TO_DAYS(NOW()) - TO_DAYS(insertDate) = 1";
		List list = getSession().createQuery(hql).list();
		return list;
	}

	@Override
	public List<JfFlow> queryOut() {
		String hql = "from JfFlow where status = 'NOR' and tradeType = 'OUT' and resultStatus is null and TO_DAYS(NOW()) - TO_DAYS(insertDate) = 1";
		List list = getSession().createQuery(hql).list();
		return list;
	}

	public static void main(String[] args) {
		FlowDefine define = new FlowDefine<JfFlow>() {
			@Override
			public String getGroupKey(JfFlow flow) {
				return flow.merchant;
			}

			@Override
			public void setGropuKey(JfFlow flow, String groupkey) {
				flow.merchant = groupkey;
			}

			@Override
			public JfFlow newSumFlow(JfFlow flow) {
				return new JfFlow();
			}

			@Override
			public void sum(JfFlow flow, JfFlow sumFlow) {
				sumFlow.jf += flow.jf;
			}
		};

		List<JfFlow> list = new ArrayList<JfFlow>();
		JfFlow f = new JfFlow();
		f.merchant = "shop1";
		f.jf = 100;
		list.add(f);

		f = new JfFlow();
		f.merchant = "shop2";
		f.jf = 200;
		list.add(f);

		f = new JfFlow();
		f.merchant = "shop1";
		f.jf = 105;
		list.add(f);

		f = new JfFlow();
		f.jf = 105;
		list.add(f);
		f = new JfFlow();
		f.jf = 105;
		list.add(f);

		// test group
		for (JfFlow ff : list)
			System.out.println("old " + ff.getMerchant() + " " + ff.jf);
		Map<JfFlow, List<JfFlow>> group = GroupUtil.group(list, define);
		for (JfFlow k : group.keySet()) {
			System.out.println(k.getMerchant() + " " + k.jf);
			for (JfFlow ff : group.get(k)) {
				System.out.println("\t" + k.getMerchant() + " " + k.jf);
			}
		}
	}

	/**
	 * 积分奖励查询
	 */
	@Override
	public List<JfFlow> calMemo(Object condition) {
		TransferStatQo dto = (TransferStatQo) condition;
		try {
			JfFlow f;
			// String tradeType = JfTradeType.out.code;
			JfTradeType.init();
			String status = JfFlow.NOR;

			List ret = new ArrayList<>();
			ret = transferQuery(dto, JfTradeType.commit);
			for(Object o:ret){
				f =(JfFlow) o;
				f.setRuleObj(ruleDao.queryByCode(f.getRule()));
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();

	}

	@Override
	public List<JfFlow> queryFlow(String loginName) {
		String hql = "from JfFlow where user = ? and (tradeType = 'EX' or tradeType = 'OUT' or tradeType = 'IN') order by insertTime desc";
		List list = getSession().createQuery(hql).setString(0, loginName)
				.setMaxResults(3).list();
		return list;
	}

	@Override
	public List<JfFlow> queryCancel() {
		String hql = "from JfFlow where status = 'TODO' and tradeType = 'UNDO' and resultStatus is null and TO_DAYS(NOW()) - TO_DAYS(insertDate) = 1";
		List list = getSession().createQuery(hql).list();
		return list;
	}

	@Override
	public JfFlow queryOldFlow(String oldFlow) {
		String hql = "from JfFlow where refFlowId = ? and tradeType = 'CAL' and jf > 0";
		JfFlow flow = (JfFlow) getSession().createQuery(hql)
				.setString(0, oldFlow).uniqueResult();
		return flow;
	}

	@Autowired
	FlowListener listener;

	public static ThreadLocal<List<JfFlow>> tlFlows = new ThreadLocal<List<JfFlow>>() {
		protected java.util.List<JfFlow> initialValue() {
			return new ArrayList<JfFlow>();
		};
	};

	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void insertEntity(JfFlow flow) {
		if (flow.canConsumeMode)
			flow.resetCanConsume();

		this.listener.beforeSaveFlow(flow);

		super.insertEntity(flow);
		flush();

		// 暂存起来，以便后面活动奖励.因为此处还没有提交，还不知道是否会最终成功，所有此处不能奖励积分。
		List<JfFlow> list = tlFlows.get();
		list.add(flow);
		tlFlows.set(list);
		/*
		 * String status = JfProperty.getJfRuleStatus(); try { if
		 * (status.equals("1")) { listener.afterSaveFlow(flow); } } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
	}

	@Override
	public void save(Object entity) throws DataAccessException {
		JfFlow flow = (JfFlow) entity;
		if (flow.canConsumeMode)
			flow.resetCanConsume();

		this.listener.beforeSaveFlow(flow);
		super.insertEntity(flow);
	}

	@Override
	public void updateEntity(JfFlow en) {
		if (JfFlow.canConsumeMode) {
			// (`status`='NOR' or `status` is null) and trade_amount>0 and
			// trade_amount-use_amount>0
			en.resetCanConsume();
		}

		super.updateEntity(en);
	};

	@Override
	public void update(Object entity) throws DataAccessException {
		super.updateEntity((JfFlow) entity);
	}

	@Override
	public int update(String sql) {
		return super.getSession().createSQLQuery(sql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hgtech.jfaccount.dao.FlowDao#getCzCountMonthByUser(java.lang.String)
	 */
	@Override
	public int getCzCountMonth(String czUser) {
		String sql = "SELECT * FROM jf_flow WHERE left(buy_merchant,12)=? and buy_merchandise='cz__licheng' and DATE_FORMAT(flow_time,'%y-%m')=DATE_FORMAT(NOW(),'%y-%m')";

		Query query = super.getSession().createSQLQuery(sql);
		query.setString(0, czUser);
		return query.list().size();
	}

	@Override
	public long getBaseJfByFlow(JfAccount account) {
		long sum=0;
		//奖励积分余额
		String hql="select sum(o.jf-o.usejf) from JfFlow o where o.account.id='"+account.id+"' and (o.invalidDate !='3000-01-01 00:00:00') and o.status not in "+notInString();
		log.info(hql);
		//全部积分-奖励积分 就是基本积分
		final Object r = getSession().createQuery(hql).uniqueResult();
		long award = r == null ? 0L : ((Number) r).longValue();
		sum =account.jf - award;
		return sum;
	}

	@Override
	public long getUsedAwardJfByFlow(JfAccount account) {
		//奖励积分使用
		String hql="select sum(o.usejf) from JfFlow o where o.account.id='"+account.id+"' and (o.invalidDate !='3000-01-01 00:00:00') and o.status not in "+notInString();
		log.info(hql);
		final Object r = getSession().createQuery(hql).uniqueResult();
		long award = r == null ? 0L : ((Number) r).longValue();
		return award;
	}
	
	/**
	 * 不参与账户变动的jfflow.status状态
	 * @return
	 */
	public static String notInString(){
		String noin = "('"+JfFlow.ARRVING+"','"+JfFlow.ARRIVED+"','"+JfFlow.STATUS_NOREPLY +"','"+ JfFlow.STATUS_NOREPLY_END +"','EXP')";
		//2016.3,加 EXP（过期）
		return noin;
	}

	/**
	 * 
	 * @param status
	 * @return 是否变动账务的状态
	 */
	public static boolean isStatusFluenceAccount(String status){
		return ! (JfFlow.ARRVING.equals(status) || JfFlow.ARRIVED.equals(status) || JfFlow.STATUS_NOREPLY.equals(status) ||  JfFlow.STATUS_NOREPLY_END.equals(status));
	}
}
