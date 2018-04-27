package hg.hjf.app.dao.operationlog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.hjf.domain.model.log.OperationLog;
import hg.hjf.domain.model.log.OperationLogQo;
import hg.logservice.OperationLogListener;
import hg.system.model.config.KVConfig;
import hgtech.jfadmin.dto.CalLogDto;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OperationLogDao  extends BaseDao<OperationLog, OperationLogQo>{

	private static final String KV_VAL_DAY_END = "dayEnd";
	private static final String KV_DATA_KEY = "dataKey";
	private static final String REGISTER = "UserService.userRegister";
	private static final String ACTIVE = "UserService.userActive";
	private static final String LOGIN = "login";
	private static final String JFCARDBINDING = "BindUserService.save";
	private static final String PERFECTINFO = "UserService.addUserBaseInfo";

	@Override
	protected Criteria buildCriteria(Criteria criteria, OperationLogQo qo) {
		if(qo!=null){
			if (StringUtils.isNotBlank(qo.getLonginName())) {
				criteria.add(Restrictions.eq("loginName", qo.getLonginName()));
			}
			if (StringUtils.isNotBlank(qo.getOperType())) {
				criteria.add(Restrictions.eq("operType", qo.getOperType()));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if (StringUtils.isNotBlank(qo.getStartDate())) {
				try {
					String sDate=qo.getStartDate()+" 00:00:00";
					criteria.add(Restrictions.ge("operTime", df.parse(sDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		
			if (StringUtils.isNotBlank(qo.getEndDate())) {
				try {
					String eDate=qo.getEndDate()+" 23:59:59";
					criteria.add(Restrictions.le("operTime", df.parse(eDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			

			criteria.addOrder(Order.desc("operTime"));
		}
		return criteria;
	}

	@Override
	protected Class<OperationLog> getEntityClass() {
		return OperationLog.class;
	}

	/**
	 * 是否日终批处理时间
	 */
	public boolean isDayEnd(){
		KVConfig kv = (KVConfig) getSession().createCriteria(KVConfig.class).add(Restrictions.eq(KV_DATA_KEY, KV_VAL_DAY_END)).uniqueResult();
		if(kv==null)
			return false;
		else
			return kv.getDataValue().equalsIgnoreCase("Y");
	}

	public void checkDayEnd(){
		if( isDayEnd())
			throw new RuntimeException("系统日终操作，请稍后（约半小时）再来！");
	}
	
	/**
	 * 设置日终开关
	 * @param dayEnd
	 */
	public void  setDayEnd(boolean dayEnd){
		KVConfig kv = (KVConfig) getSession().createCriteria(KVConfig.class).add(Restrictions.eq(KV_DATA_KEY, KV_VAL_DAY_END)).uniqueResult();
		if(kv==null){
			kv = new KVConfig();
			kv.setDataKey(KV_VAL_DAY_END);
		}
		kv.setDataValue(dayEnd?"Y":"N");
		getSession().saveOrUpdate(kv);
		 
	}

	/**
	 * 获取注册记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OperationLog> queryRegisterList() {
		String hql = "from OperationLog where operOk = '1' and TO_DAYS(NOW()) - TO_DAYS(operTime) = 1 and operType = ? and loginName is not null";
		List<OperationLog> list = getSession().createQuery(hql).setString(0, REGISTER).list();
		return list;
	}

	/**
	 * 获取激活记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OperationLog> queryActiveList() {
		String hql = "from OperationLog where operOk = '1' and TO_DAYS(NOW()) - TO_DAYS(operTime) = 1 and operType = ? and loginName is not null";
		List<OperationLog> list = getSession().createQuery(hql).setString(0, ACTIVE).list();
		return list;
	}

	/**
	 * 获取签到记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OperationLog> queryLoginList() {
		String hql = "from OperationLog where operOk = '1' and (TO_DAYS(NOW()) - TO_DAYS(operTime) = 1) and operType = ? and loginName is not null";
		List<OperationLog> list = getSession().createQuery(hql).setString(0, LOGIN).list();
		return list;
	}

	/**
	 * 获取积分卡绑定记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OperationLog> queryJfCardBinding() {
		String hql = "from OperationLog where operOk = '1' and (TO_DAYS(NOW()) - TO_DAYS(operTime) = 1) and operType = ? and loginName is not null";
		List<OperationLog> list = getSession().createQuery(hql).setString(0, JFCARDBINDING).list();
		return list;
	}

	/**
	 * 完善信息记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OperationLog> queryPerfectInfo() {
		String hql = "from OperationLog where operOk = '1' and (TO_DAYS(NOW()) - TO_DAYS(operTime) = 1) and operType = ? and loginName is not null";
		List<OperationLog> list = getSession().createQuery(hql).setString(0, PERFECTINFO).list();
		return list;
	}

	@Autowired
	OperationLogListener listener;

	public static ThreadLocal<List<OperationLog>> tlLogs=new ThreadLocal<List<OperationLog>>(){
		protected java.util.List<OperationLog> initialValue() {
			return new ArrayList<OperationLog>();
		};
	};

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-1-8上午10:20:06
	 * @version：
	 * @修改内容：
	 * @参数：@param paging
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination findPagination(Pagination paging) {
		Criteria criteria=super.getSession().createCriteria(OperationLog.class);
		if(paging.getCondition()!=null&&paging.getCondition() instanceof OperationLogQo){
			OperationLogQo qo = (OperationLogQo) paging.getCondition();
			if (StringUtils.isNotBlank(qo.getLonginName())) {
				criteria.add(Restrictions.like("loginName", "%"+qo.getLonginName()+"%"));
			}
			if (StringUtils.isNotBlank(qo.getOperType())) {
				criteria.add(Restrictions.like("operType", "%"+qo.getOperType()+"%"));
			}
			if (StringUtils.isNotBlank(qo.getOperData())) {
				criteria.add(Restrictions.like("operData", "%"+qo.getOperData()+"%"));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if (StringUtils.isNotBlank(qo.getStartDate())) {
				try {
					String sDate=qo.getStartDate()+" 00:00:00";
					criteria.add(Restrictions.ge("operTime", df.parse(sDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		
			if (StringUtils.isNotBlank(qo.getEndDate())) {
				try {
					String eDate=qo.getEndDate()+" 23:59:59";
					criteria.add(Restrictions.le("operTime", df.parse(eDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			criteria.addOrder(Order.desc("operTime"));
		}
		return super.findByCriteria(criteria, paging.getPageNo(), paging.getPageSize());

	}
}
