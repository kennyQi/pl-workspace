/**
 * @文件名称：RuleDaoImp.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:36:07
 */
package hgtech.jfadmin.dao.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import hg.common.component.hibernate.HibernateSimpleDao;
import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hgtech.jfadmin.dto.*;
import hgtech.jfadmin.dao.CalFlowDao;
import hgtech.jfadmin.dao.RuleDao;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfadmin.hibernate.RuleHiberEntity;
import hgtech.jfadmin.util.RuleUtil;
import hgtech.jfcal.model.Rule;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：计算流水的dao
 * @类修改者：
 * @修改日期：2014年10月22日上午9:36:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月22日上午9:36:07
 *
 */
@Repository("calDao")
public class CalFlowDaoImp  extends HibernateSimpleDao implements CalFlowDao{

	private static int ONEDAY=24*60*60*1000;
	@Override
	public Pagination findPagination(Pagination pagination) {
		
		try {
		Criteria criteria=super.getSession().createCriteria(CalFlowHiberEntity.class);
		criteria.add(Restrictions.eq("isCal",CalFlowHiberEntity.CONS_IS_CAL));
		//criteria.add(Restrictions.ne("out_jf", 0));
		if(pagination.getCondition() != null && pagination.getCondition() instanceof CalLogDto){
			CalLogDto condition = (CalLogDto) pagination.getCondition();
			if(null != condition.getIn_userCode()   && !"".equals(condition.getIn_userCode())){
				criteria.add(Restrictions.eq("in_userCode", condition.getIn_userCode()));
			}
			if(null != condition.getIn_rulecode()   && !"".equals(condition.getIn_rulecode())){
				criteria.add(Restrictions.like("in_rulecode", "%"+condition.getIn_rulecode()+"%"));
			}
			if((null != condition.getCalTime() && !"".equals(condition.getCalTime())) ){
				SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
				Calendar ca=Calendar.getInstance();

				String strBeginTime = StringUtils.isNotBlank(condition.getCalTime())? condition.getCalTime():"2000-01-01";
				if(StringUtils.isNotBlank(condition.getNowTime()))
					ca.setTime(sdf.parse(condition.getNowTime()));
				else
					ca.setTime(new Date());
				ca.add(ca.DATE, 1);
				String strEndTime = sdf.format(ca.getTime());
				try {
					Date beginTime =  sdf.parse(strBeginTime);
					Date endTime = sdf.parse(strEndTime);
					criteria.add(Restrictions.between("calTime", beginTime,endTime));
//					criteria.add(Restrictions.ge("calTime", beginTime));
//					criteria.add(Restrictions.le("calTime", endTime));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				}
			}
		return super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public Pagination findPaginationActivityStat(Pagination pagination) {
		
		try {
			Criteria criteria=super.getSession().createCriteria(CalFlowHiberEntity.class);
			criteria.add(Restrictions.eq("isCal",CalFlowHiberEntity.CONS_IS_CAL));
			criteria.add(Restrictions.eq("out_resultCode", "Y"));
			if(pagination.getCondition() != null && pagination.getCondition() instanceof CalLogDto){
				CalLogDto dto = (CalLogDto) pagination.getCondition();
				if(null != dto.getIn_userCode()   && !"".equals(dto.getIn_userCode())){
					criteria.add(Restrictions.eq("in_userCode", dto.getIn_userCode()));
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat shorts = new SimpleDateFormat("yyyy-MM-dd");
				if (StringUtils.isNotBlank(dto.getStartTime())) {
					Date startDate = dto.getStartTime().length()>10? sdf.parse(dto.getStartTime()):shorts.parse(dto.getStartTime());
					criteria.add(Restrictions.ge("calTime", startDate));
				}
				if (StringUtils.isNotBlank(dto.getEndTime())) {
					Date endDate = dto.getEndTime().length()>10? sdf.parse(dto.getEndTime()):shorts.parse(dto.getEndTime());
					endDate = new Date(endDate.getTime() + ONEDAY);
					criteria.add(Restrictions.le("calTime", endDate));
				}
				
				//会员名称
				if (StringUtils.isNotBlank(dto.getUserId())) {
					criteria.add(Restrictions.like("in_userCode", dto.getUserId()));
				}
				//活动类型
				if (StringUtils.isNotBlank(dto.getActivityId())) {
					criteria.add(Restrictions.eq("in_rulecode", dto.getActivityId()));
				}else {
					criteria.add(Restrictions.isNotNull("in_rulecode"));
				}
				
				if(!dto.isShow0())	
					criteria.add(Restrictions.ne("out_jf", 0));
			}
			
			criteria.addOrder(Order.desc("calTime"));
			final Pagination findByCriteria = super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
			for(Object o:findByCriteria.getList()){
				CalFlowHiberEntity flow = (CalFlowHiberEntity) o;
				flow.parseTradeJson();
			}
			return findByCriteria;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public Pagination findTradeFlowPagination(Pagination pagination) {
		
		try {
		Criteria criteria=super.getSession().createCriteria(CalFlowHiberEntity.class);
		criteria.add(Restrictions.eq("isCal",CalFlowHiberEntity.CONS_IS_CAL));
		criteria.add(Restrictions.ne("out_jf", 0));
		if(pagination.getCondition() != null && pagination.getCondition() instanceof CalLogDto){
			CalLogDto condition = (CalLogDto) pagination.getCondition();
			if(null != condition.getIn_userCode()   && !"".equals(condition.getIn_userCode())){
				criteria.add(Restrictions.eq("in_userCode", condition.getIn_userCode()));
			}
			if((null != condition.getCalTime() && !"".equals(condition.getCalTime())) ){
				SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
				String strBeginTime = condition.getCalTime();

				Calendar ca=Calendar.getInstance();
				if(null !=condition.getNowTime() && !"".equalsIgnoreCase(condition.getNowTime()))
					ca.setTime(sdf.parse(condition.getNowTime()));
				else
					ca.setTime(new Date());
				ca.add(ca.DATE, 1);
				String strEndTime = sdf.format(ca.getTime());
				try {
					Date beginTime = sdf.parse(strBeginTime);
					Date endTime = sdf.parse(strEndTime);
					criteria.add(Restrictions.between("calTime", beginTime,endTime));
//					criteria.add(Restrictions.ge("calTime", beginTime));
//					criteria.add(Restrictions.le("calTime", endTime));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				}
			}
		return super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void delete(CalFlowHiberEntity ruleEntity)
	{
		super.delete(ruleEntity);
	}
	
	@Override
	public void update(CalFlowHiberEntity ruleEntity)
	{
		super.update(ruleEntity);
	}
	

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.dao.CalFlowDao#get(java.lang.String)
	 */
	@Override
	public CalFlowHiberEntity get(String cal_id) {
		String hql="from CalFlowHiberEntity where calId=?";
		return (CalFlowHiberEntity) super.findUnique(hql, cal_id);
	}


	/* (non-Javadoc)
	 * @see hgtech.jfadmin.dao.CalFlowDao#save(java.util.Collection)
	 */
	@Override
	public void save(Collection<CalFlowHiberEntity> l) {
		String entityID=null;
		// 
		for(CalFlowHiberEntity en:l)
		{	en.calId=UUIDGenerator.getUUID();
			save(en);
		}
		getSession().flush();
	}
	
	/**
	 * 
	 * @方法功能说明：新开数据库连接保存
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月28日下午3:27:14
	 * @version：
	 * @修改内容：
	 * @参数：@param l
	 * @return:void
	 * @throws
	 */
	@Override
	public void save2(Collection<CalFlowHiberEntity> l) {
		String entityID=null;
		// 
		for(CalFlowHiberEntity en:l)
		{	en.calId=UUIDGenerator.getUUID();
			Session newSess = super.sessionFactory.openSession();
			try {
			    newSess. save(en);
			    newSess.flush();
			} finally{
			    if(newSess!=null)
				newSess.close();
			}
		}
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-mm-dd");
 
		try {
			Date beginTime = sdf.parse("2014-");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public List<CalFlowHiberEntity> findList(CalLogDto dto) {
		try {
			Criteria criteria = super.getSession().createCriteria(CalFlowHiberEntity.class);
			criteria.add(Restrictions.eq("isCal", CalFlowHiberEntity.CONS_IS_CAL));
			if(!dto.isShow0())	
				criteria.add(Restrictions.ne("out_jf", 0));

			if(!dto.isShowFail())
				criteria.add(Restrictions.eq("out_resultCode", "Y"));
			
			if (null != dto.getIn_userCode() && !"".equals(dto.getIn_userCode())) {
				criteria.add(Restrictions.eq("in_userCode", dto.getIn_userCode()));
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat shorts = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isNotBlank(dto.getStartTime())) {
				Date startDate = dto.getStartTime().length()>10? sdf.parse(dto.getStartTime()):shorts.parse(dto.getStartTime());
				criteria.add(Restrictions.ge("calTime", startDate));
			}
			if (StringUtils.isNotBlank(dto.getEndTime())) {
				Date endDate = dto.getEndTime().length()>10? sdf.parse(dto.getEndTime()):shorts.parse(dto.getEndTime());
				endDate = new Date(endDate.getTime() + ONEDAY);
				criteria.add(Restrictions.le("calTime", endDate));
			}

			// 会员名称
			if (StringUtils.isNotBlank(dto.getUserId())) {
				criteria.add(Restrictions.like("in_userCode", dto.getUserId()));
			}
			// 活动类型
			if (StringUtils.isNotBlank(dto.getActivityId())) {
				criteria.add(Restrictions.eq("in_rulecode", dto.getActivityId()));
			}else {
				criteria.add(Restrictions.isNotNull("in_rulecode"));

			}
			
			criteria.addOrder(Order.desc("calTime"));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Autowired
	RuleDao ruleDao;
	
	//ruleTemplate=hgtech.jf.hg.rulelogic.CZPrizeLogic
	@Override
	public List<CalFlowHiberEntity> findChoujiangList(CalLogDto dto) {
		try {
			Criteria criteria = super.getSession().createCriteria(CalFlowHiberEntity.class);
			criteria.add(Restrictions.eq("isCal", CalFlowHiberEntity.CONS_IS_CAL));
			//criteria.add(Restrictions.ne("out_jf", 0));
			
			if (null != dto.getIn_userCode() && !"".equals(dto.getIn_userCode())) {
				criteria.add(Restrictions.eq("in_userCode", dto.getIn_userCode()));
			}

			//根据模版，找到正在运行的规则作为条件
//			if (null != dto.getRuleTemplate() && !"".equals(dto.getRuleTemplate())) {
//				 List<RuleHiberEntity> rlst = ruleDao.queryByTemplate(dto.getRuleTemplate());
//				 for(RuleHiberEntity r :rlst){
//					 if(RuleUtil.isValid(r.getStatus(), r.getStartDate(), r.getEndDate()))
//						 criteria.add(Restrictions.eq("in_rulecode", r.getCode()));
//				 }
//			}else{
//				throw new RuntimeException("非法输入！没有抽奖模版参数");
//			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (StringUtils.isNotBlank(dto.getStartTime())) {
				Date startDate = sdf.parse(dto.getStartTime());
				criteria.add(Restrictions.ge("calTime", startDate));
			}
			if (StringUtils.isNotBlank(dto.getEndTime())) {
				Date endDate = sdf.parse(dto.getEndTime());
				endDate = new Date(endDate.getTime() + ONEDAY);
				criteria.add(Restrictions.le("calTime", endDate));
			}
			if (StringUtils.isNotBlank(dto.getOut_jf())) {
				
				criteria.add(Restrictions.gt("out_jf", Integer.valueOf(dto.getOut_jf())));
			}
			if (StringUtils.isNotBlank(dto.getOut_resultCode())) {
				
				criteria.add(Restrictions.eq("out_resultCode", dto.getOut_resultCode()));
			}

			 
			// 活动类型
			if (StringUtils.isNotBlank(dto.getActivityId())) {
				criteria.add(Restrictions.eq("in_rulecode", dto.getActivityId()));
			}else {
				//criteria.add(Restrictions.isNotNull("in_rulecode"));
				throw new RuntimeException("非法输入！没有规则代码");
			}
			
			criteria.addOrder(Order.desc("calTime"));
			
			// 查询多少个
			criteria.setMaxResults(dto.getMaxResults());
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
