/**
 * @文件名称：AccountManage.java
 * @类路径：hgtech.jfaccount.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-22上午10:32:41
 */
package hgtech.jfaccount.service;

import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hgtech.jf.JfChangeApply;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.JfAccountUK;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.JfUseDetail;
import hgtech.jfaccount.JfUser;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfaccount.TradeType;
import hgtech.jfaccount.dao.AccountDao;
import hgtech.jfaccount.dao.FlowDao;
import hgtech.jfaccount.dao.UseDao;
import hgtech.jfaccount.listener.IDomainAccountListener;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ScrollableResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @类功能说明：积分账户操作的service
 * @类修改者：
 * @修改日期：2014-9-22上午10:32:41
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22上午10:32:41
 * 
 */
@Transactional
@Service("accountServiceImp")
public class AccountService implements IAccountService {
    	public static final String JFNAME_PREPAY = "prepay";
		static {
    	    System.out.println("AccountService version 2015/5/26");
    	}
    	public static Log log = LogFactory.getLog(AccountService.class);
	/**
	 * @FieldsINT_NOFLOWVALID:不限制积分流水的有效期
	 */
	public static final int INT_NOFLOWVALID = -1;
	public static   Date DT_YEAR3000 =null;
	static{
		try {
			DT_YEAR3000= new SimpleDateFormat("yyyyMMdd").parse("30000101");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
//	public static final int INT_TRANSFERIN_FLOWVALIDYEAR = 50;
	@Resource
	public EntityDao<StringUK, JfUser> jfuserDao/*
											 * =new BaseEntityHome_Mem<StringUK,
											 * JfUser>(JfUser.class)
											 */;
	@Resource
	public FlowDao flowDao/*
						 * =new BaseEntityHome_Mem<StringUK,
						 * JfFlow>(JfFlow.class){ int flow=0; public void
						 * saveEntity(JfFlow en) { en.flowId=new StringUK(
						 * ++flow+""); super.saveEntity(en); }; }
						 */;

	@Resource
	public UseDao useDao;
	@Resource
	public AccountDao accountDao /* =new JfAccountHome_Mem() */;

	@Autowired
	IDomainAccountListener accountListener;
	
	/**
	 * @param jfValidYear
	 *            积分有效期（年） 累积计算出的积分/积分获得
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-24上午10:01:11
	 * @修改内容：
	 * @参数：@param cal
	 * @return:void
	 * @throws
	 */
	@Override
	public void commit(JfChangeApply cal, int jfValidYear, String calID) {
		JfTradeType trade = JfTradeType.commit;
		if (cal.getjf() > 0 || (cal.getjf()==0 && cal.isSavejf0()))
			gotJf(cal, trade, cal.getFlowStatus(), jfValidYear);
		else if(cal.getjf()<0)
			costJf(cal, trade, jfValidYear);
	}
	/**
	 * 
	 * @方法功能说明：转入
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月10日下午5:33:57
	 * @version：
	 * @修改内容：
	 * @参数：@param order
	 * @参数：@param orderid
	 * @return:void
	 * @throws
	 */
	@Override
	public JfFlow transferIn(JfChangeApply order/*,String orderid*/){
		return gotJf(order,JfTradeType.in, order.getFlowStatus(), INT_NOFLOWVALID/*,orderid*/);
	}

	
	/**
	 * 
	 * @方法功能说明：转出
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月10日下午5:34:01
	 * @version：
	 * @修改内容：
	 * @参数：@param order
	 * @参数：@param orderid
	 * @return:void
	 * @throws
	 */
	@Override
	public JfFlow transferOut(JfChangeApply order ){
		//return costJf(order, JfTradeType.out, JfProperty.getJfValidYear());
		return costJf(order, JfTradeType.out, INT_NOFLOWVALID);
	}
	
	/**
	 * @return 
	 * @param jfStatus  
	 * @方法功能说明：积分获得
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午2:07:54
	 * @修改内容：
	 * @参数：@param cal
	 * @参数：@param trade
	 * @参数：@param jfValidYear
	 * @return:void
	 * @throws
	 */
	protected JfFlow gotJf(JfChangeApply cal, JfTradeType trade, String jfStatus, int jfValidDay/*,
			String calID*/) {
		StringUK userid = new StringUK(cal.getuserCode());
		JfUser user = cal.isNewUser()?null: jfuserDao.get(userid);
		if (true && user == null) {
			user = jfuserDao.newEntity(userid);
			jfuserDao.insertEntity(user);
			//由于 StringUK是每次new出来的，hibernate认为是不同的（即使同一usercode），导致hibernate.get出来为空对象。使用flush会对数据库做insert
			jfuserDao.flush();
		}
		JfAccountType acctype = (JfAccountType) cal.getaccountType();
		if(acctype==null)
		    throw new RuntimeException("积分类型不被识别");
		JfAccountUK uk = new JfAccountUK(user,	acctype);
		JfAccount acc = cal.isNewUser()?null: accountDao.get(uk);
		if (acc == null) {
			acc = accountDao.newEntity(uk);
		}
		
		JfFlow flow = new JfFlow();

		//记录发放积分的商户,
		//并将商户的积分账户减少 TODO 大量并发扣减商户预付积分的情况
		if(cal.getAppId()!=null){
			checkAppPrePay(cal, acctype, flow);
		}
		
		if(JfFlow.STATUS_NOREPLY.equals(jfStatus)){
			//对方未返回。只记流水不更新账户
			flow.status=jfStatus;
			flow.merchandiseStatus=JfFlow.STATUS_NOREPLY;
			if(cal.isArriving())
				flow.setValidDate(cal.getValidDay());//可用日期
		}else{
			//正常流水
			acc.lastUpdate = new Date();
			if(cal.isArriving()){
				//区分是否在途
				acc.jfTODO += cal.getjf();
				flow.status = flow.ARRVING;
				flow.setValidDate(cal.getValidDay());//可用日期
			}else{
				acc.jf += cal.getjf();
				flow.status = flow.NOR;
			}
			
		}
		
		flow.account = acc;
		flow.user = cal.getuserCode();
		flow.jf = cal.getjf();
		flow.tradeType = trade;
		flow.setInsertTime(new Date());
		flow.setFee(cal.getFee());
		flow.setMerchandiseAmount(cal.getMerchandiseAmount());
		
		//失效日期设置
		if(cal.getInValidDate()==null){
			//如果失效日期没有设置的话，
			if (jfValidDay != INT_NOFLOWVALID) {
				Calendar validC = Calendar.getInstance();
				validC.setTime(flow.insertTime);
				validC.add(Calendar.DAY_OF_MONTH, jfValidDay);
				flow.setInvalidDate( validC.getTime());
			}else
				flow.setInvalidDate(DT_YEAR3000);
		}else
			flow.setInvalidDate(cal.getInValidDate());

		if(cal instanceof JfFlow){
			//重做
			JfFlow old = (JfFlow) cal;
			flow.refFlowId=old.getRefFlowId();//参考号/订单号 取原先的
			flow.detail="重做流水号" + old.getId()+"的流水。"+old.getDetail();
		}else{
			flow.refFlowId = cal.getId();
			flow.detail = cal.getremark();
		}
		flow.batchNo = cal.getbatchNo();
		flow.merchant = cal.getMerchant();
		flow.merchandise = cal.getMerchandise();
		flow.acctJf = ( acc.jf);
		JfAccountType jt=(JfAccountType)cal.getAccountTypeForJfRate();
		if(jt!=null)
			flow.rate=jt.jfRate+"";
		
		flow.setMerchandiseStatus(cal.getMerchandiseStatus());
		flow.setSendStatus(cal.getSendStatus());
		flow.setRule(cal.getRule());
		//flow.refFlowId = calID;

		if(cal.isNewUser())
			accountDao.insertEntity(acc);
		else
			accountDao.saveEntity(acc);
		flowDao.insertEntity(flow);
		
		accountDao.flush();//没有flush之前，spring不能回滚
		flowDao.flush();
		return flow;
	}
	
	/**
	 * 		//记录发放积分的商户,
		//并将商户的积分账户减少 TODO 大量并发扣减商户预付积分的情况
	 * @param cal
	 * @param acctype
	 * @param flow
	 */
	public void checkAppPrePay(JfChangeApply cal, JfAccountType acctype,
			JfFlow flow) {
		JfAccount debit;
		try {
			JfAccountType debitAcctType= new JfAccountType();
			debitAcctType.uk = new JfAccountTypeUK(acctype.uk.getDomain(), SetupAccountContext.jfNameFileDao.get(new StringUK(JFNAME_PREPAY)));
			debit = accountDao.get(new JfAccountUK(jfuserDao.get(new StringUK(cal.getAppId())), acctype));
			Domain sh = SetupAccountContext.findDomain(cal.getAppId());
			long oldjf = debit.jf;
			debit.jf -= cal.getjf();
			if (sh == null)
				throw new RuntimeException("没有此代码的商户！" + cal.getAppId());
			if (debit.jf <= sh.getSuspendedLine()){
				//停发
				if(! sh.isStatusStop())
					accountListener.suspend(sh, oldjf);
				throw new RuntimeException("该商户积分预付费余额不足，停止积分发放！商户代码："+sh.getCode());
			}
			else if (debit.jf <= sh.getPrepaidCordon()	&& !sh.isStatusAlarm())
				//预警
				accountListener.alarm(sh, oldjf);
			flow.accountDebit = debit;
			accountDao.saveEntity(debit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("扣减商户预付积分时候有错，请联系技术人员");
		}
	}

	/**
	 * 
	 * @方法功能说明：获得明细的撤销
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午3:34:25
	 * @修改内容：
	 * @参数：@param flowid
	 * @return:void
	 * @throws
	 */
	@Override
	public void undoGotjf(String flowid) {
		throw new RuntimeException("已废弃！。请使用cancelJf（）");
		
		/*JfFlow flow = flowDao.get(new StringUK(flowid));
		if (!flow.status.equals(JfFlow.NOR) || flow.jf < 0)
			throw new RuntimeException("状态正常的获得明细才能撤销");
		undojfFlow(flow);*/
	}

	

	@Override
	public JfFlow redoNoReplyjf(String flowid) {

		JfFlow flow = flowDao.get(new StringUK(flowid));
		if (!flow.status.equals(JfFlow.STATUS_NOREPLY) )
			throw new RuntimeException("状态为未响应的才能重做");
		return redoUnReplyFlow(flow);
	}
	
	@Override
	public void confirmNoReplyjf(String flowid) {

		JfFlow flow = flowDao.get(new StringUK(flowid));
		if (!flow.status.equals(JfFlow.STATUS_NOREPLY) )
			throw new RuntimeException("状态为未响应的才能确定");
		
		flow.status=(flow.STATUS_NOREPLY_END);
		flowDao.updateEntity(flow);
		flowDao.flush();
	}

	
	/**
	 * 改变在途积分状态 为正常
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月10日下午5:40:51
	 * @version：
	 * @修改内容：
	 * @参数：@param flowid
	 * @return:void
	 * @throws
	 */
	@Override
	public void changeArriveState(String flowid, boolean ok){
		
		JfFlow flow = flowDao.get(new StringUK(flowid));
		changeArriveState(flow, ok);
		
	}
	/**
	 * @方法功能说明：改变在途积分状态 为正常
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月10日下午5:54:28
	 * @version：
	 * @修改内容：
	 * @参数：@param flow
	 * @参数：@param ok
	 * @return:void
	 * @throws
	 */
	public void _old_changeArriveState(JfFlow flow, boolean ok) {
		if (!flow.status.equals(JfFlow.ARRVING)  )
			throw new RuntimeException("不是在途状态");
		JfAccount acc = flow.account;// accountDao.get(flow.getAccountId());

		acc.jfTODO -= flow.getJf();
		if(ok){
			acc.jf += flow.getJf();
			flow.status = JfFlow.NOR;
		}else
		{	
			flow.status = JfFlow.EXP;
			flow.detail +="在途积分失效";
		}
		acc.lastUpdate = new Date();
		flow.updateTime = new Date();

		accountDao.updateEntity(acc);
		flowDao.updateEntity(flow);
		
	}

	/**
	 * @方法功能说明：改变在途积分状态 为正常
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月10日下午5:54:28
	 * @version：
	 * @修改内容：
	 * @参数：@param flow
	 * @参数：@param ok
	 * @return:void
	 * @throws
	 */
	@Override
	public void changeArriveState(JfFlow flow,boolean ok) {
		if (!flow.status.equals(JfFlow.ARRVING)  )
			throw new RuntimeException("不是在途状态");
		JfAccount acc = flow.account;// accountDao.get(flow.getAccountId());

	
		acc.jfTODO -= flow.getJf();
		if(ok){
			acc.jf += flow.getJf();
			flow.status = JfFlow.ARRIVED;
		}else
		{	
			flow.status = JfFlow.EXP;
			flow.detail +="在途积分失效";
		}
		acc.lastUpdate = new Date();
		flow.updateTime = new Date();

		accountDao.updateEntity(acc);
		flowDao.updateEntity(flow);
		
		//2015.7.29,同时插入流水
		if(ok){
			JfFlow newFlow = new JfFlow();
//				BeanUtils.copyProperties(newFlow, flow);
				org.springframework.beans.BeanUtils.copyProperties(flow, newFlow);
			newFlow.setFlowId(new StringUK( UUIDGenerator.getUUID()));
			newFlow.setInsertTime(new Date());
			newFlow.setUpdateTime(new Date());
			newFlow.setRefFlowId(( flow.getFlowId().getS()));
			newFlow.setDetail( new SimpleDateFormat("yyyy-MM-dd").format(flow.getInsertDate() )+"的在途积分生效");
			newFlow.setTradeType(JfTradeType.ARRIVED);
			newFlow.setOldTradeType(flow.getTradeType());//set 原来交易码
			newFlow.setAcctJf(acc.getJf());
			newFlow.setStatus(JfFlow.NOR);
			flowDao.insertEntity(newFlow);
		}
		//end 2015.7.29
		
	}	
	
	/**
	 * @方法功能说明：对jf_flow撤销
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日下午4:29:25
	 * @修改内容：
	 * @参数：@param flow
	 * @return:void
	 * @throws
	 */
	private void undojfFlow(JfFlow flow) {
		
		JfAccount acc = flow.account;// accountDao.get(flow.getAccountId());

		acc.jf -= flow.getJf();
		acc.lastUpdate = new Date();
		flow.status = JfFlow.UNDO;
		flow.updateTime = new Date();
		flow.acctJf = ( acc.jf);

		accountDao.updateEntity(acc);
		flowDao.updateEntity(flow);
		accountDao.flush();
		flowDao.flush();
	}
	
	private JfFlow redoUnReplyFlow(JfFlow oldflow) {
		//update old
		oldflow.status=JfFlow.STATUS_NOREPLY_END;
		flowDao.updateEntity(oldflow);
		JfFlow flow;
		if(oldflow.jf>=0)
		{	
			  flow =transferIn(oldflow);
			
			//今天或昨天生效的话，需要立即处理。因为自动生效程序可能已经跑过了
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if(flow.validDate!=null){
				if(  flow.validDate.getTime()<=new Date().getTime()){
					System.out.println("已经生效的话，需要立即处理。因为自动生效程序可能已经跑过了");
					changeArriveState(flow, true);
				}
			}
		}else
		{
			  flow = transferOut(oldflow);
		}
		return flow;
	}
	/**
	 * 用流水来更新账户
	 * @param flow
	 * @return
	 */
	protected JfAccount updateAccWithFlow(JfFlow flow) {
		JfAccount acc = flow.account;
		if (flow.validDate != null) {
			// 在途
			acc.jfTODO += flow.jf;
		} else {
			acc.jf += flow.jf;
		}
		return acc;
	}

	/**
	 * @return 
	 * @param jfValidYear
	 * 
	 * @方法功能说明：积分消耗，积分负值情况下不运行。
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日上午10:53:43
	 * @修改内容：
	 * @参数：@param cal
	 * @return:void
	 * @throws
	 */
	@Override
	public JfFlow exchange(JfChangeApply cal, int jfValidYear) {
		JfTradeType trade = JfTradeType.exchange;
		return costJf(cal, trade, jfValidYear);
	}

	/**
	 * @return 
	 * @param jfValidYear
	 * @方法功能说明：积分消耗
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午2:08:18
	 * @修改内容：
	 * @参数：@param cal
	 * @参数：@param trade
	 * @return:void
	 * @throws
	 */
	protected JfFlow costJf(JfChangeApply cal, JfTradeType trade, int jfValidYear) {
		//账户查询以及检查
		StringUK userid = new StringUK(cal.getuserCode());
		JfUser user = jfuserDao.get(userid);
		JfAccountUK uk = null;
		JfAccount acc = null;
		if (user == null) {
			throw new RuntimeException("积分用户不存在！");
		} else {
			JfAccountType getaccountType = (JfAccountType) cal.getaccountType();
			if(getaccountType ==null)
				log.error("getaccountType null!"+cal.toString());
			uk = new JfAccountUK(user, getaccountType);
			if(accountDao==null)
				log.error("accountDao isnull");
			acc = accountDao.get(uk);
			if (acc == null) {
				// acc =accountDao.newEntity(uk);
				// acc.lastUpdate=new Date();
				throw new RuntimeException("积分账户不存在！用户："+user +"，积分类型代码： "+cal.getaccountType());
			}
		}
		
		//根据单据扣减积分，并保存流水
		return costJf(cal, acc, trade, jfValidYear);
		
	}
	
	 
	
	/**
	 * 
	 * @param cal 扣减单据
	 * @param acc 账户
	 * @param trade 交易吗
	 * @param jfValidYear 是否使用有效期
	 * @return 
	 */
	public JfFlow costJf(JfChangeApply cal, JfAccount acc, JfTradeType trade, int jfValidYear) {
		int jf = cal.getjf();
		if(jf>=0)
		    throw new RuntimeException("扣减数额须为负值！");
		
		JfUser jfUser = jfuserDao.get(new StringUK(acc.getUser()));
		boolean isReal = (jfUser.isReal!=null && jfUser.isReal);
		
		//检查未认证会员基本积分余额是否够
		boolean whenOnValid = (jfValidYear != INT_NOFLOWVALID && JfProperty.checkReal() && !isReal && JfProperty.unRealBonusAmount()==0);
		boolean whenOffValid= (jfValidYear == INT_NOFLOWVALID);
		boolean onlyUseBase = (whenOffValid || whenOnValid)
				&& !trade.getCode().equals(JfTradeType.expire.getCode());//失效的只能是奖励积分
		
		if(onlyUseBase) {
					//只能使用基本积分
					long baseJf = flowDao.getBaseJfByFlow(acc);
					if (baseJf<jf*(-1))
						throw new RuntimeException("未认证会员不能使用奖励积分。您的基本积分为" +baseJf +",余额不足!");
		}else if (JfProperty.checkReal() && !isReal){
			//未认证下，奖励积分有使用限制
			int bonus=JfProperty.unRealBonusAmount();//未认证下可使用的奖励限制
			long baseJf = flowDao.getBaseJfByFlow(acc);
			{
				long awardJf=acc.getJf()-baseJf;
				long usedawardJf= flowDao.getUsedAwardJfByFlow(acc);
				long maxAwardAvailable = bonus-usedawardJf; 	
				long awardAvailable = awardJf > maxAwardAvailable ? maxAwardAvailable	: awardJf;
				if (baseJf + awardAvailable<jf*(-1))
					throw new RuntimeException("未认证会员只能使用奖励积分" + bonus+"，您已使用奖励积分" + usedawardJf+"。您的基本积分为" +baseJf +"。余额不足!");
				
			}
			
		}

		JfFlow flow = new JfFlow();
		if(JfFlow.STATUS_NOREPLY.equals(cal.getFlowStatus())){
			// 对方未响应下只记录流水
			flow.status= JfFlow.STATUS_NOREPLY;
			flow.merchandiseStatus=JfFlow.STATUS_NOREPLY;
		}else{                   
			acc.jf += jf;
			if (trade != JfTradeType.adjust && acc.jf < 0)// 调整情况下，允许余额为负。其他情况不允许！
				throw new RuntimeException("积分余额不足！");
			acc.lastUpdate = new Date();
			flow.status = JfFlow.NOR;			
		}
		
		
		flow.account = acc;
		flow.user = acc.getUser();
		flow.jf = jf;
		flow.tradeType = trade;
		flow.setInsertTime(new Date());
		if(cal instanceof JfFlow){
			//重做
			JfFlow old = (JfFlow) cal;
			flow.refFlowId=old.getRefFlowId();//参考号/订单号 取原先的
			flow.detail="重做流水号" + old.getId()+"的流水。"+old.getDetail();
		}else{		
			flow.refFlowId = cal.getId();
			flow.detail = cal.getremark();
		}
		flow.batchNo = cal.getbatchNo();
		flow.setFee(cal.getFee());
		flow.setMerchandiseAmount(cal.getMerchandiseAmount());
		flow.setMerchandise(cal.getMerchandise());
		flow.setMerchant(cal.getMerchant());
		flow.acctJf = ( acc.jf);
		flow.setNoticeMobile(cal.getNoticeMobile());
		JfAccountType jt=(JfAccountType)cal.getAccountTypeForJfRate();
		if(jt!=null)
			flow.setRate(jt.jfRate+"");
		flow.setMerchandiseStatus(cal.getMerchandiseStatus());
		flow.setSendStatus(cal.getSendStatus());
		accountDao.updateEntity(acc);
		flowDao.insertEntity(flow);
		
		boolean hasFlowValid = (jfValidYear != INT_NOFLOWVALID); // 每笔积分获取都有有效期，则需要记住扣的哪些获得明细
		if ( hasFlowValid && !onlyUseBase  && !JfFlow.STATUS_NOREPLY.equals(cal.getFlowStatus())) {
			int totalUse = costDetail(flow);
			//flow.detail += " 使用汇豆"+totalUse;
		}
		accountDao.flush();//没有flush之前，spring不能回滚
		flowDao.flush();
		//log.warn(String.format("订单号%s,用户%sflush完毕。", cal.getId(),cal.getuserCode()));
		return flow;
		
	}

	private void checkBaseJf(boolean isRealUser, JfAccount account, int to_use) {
		if(JfProperty.checkReal() && !isRealUser) {
			//只能使用基本积分
			long baseJf = flowDao.getBaseJfByFlow(account);
			if (baseJf<to_use)
				throw new RuntimeException("未认证会员不能使用奖励积分。您的基本积分为" +baseJf +",余额不足!");
//			else
//				return;
		}
	}	
	/**
	 * @param jfValidYear
	 * 
	 * @方法功能说明：消耗明细的撤销
	 * 积分1.5旧版。对原来流水标记，积分有效期重新开始
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午3:34:25
	 * @修改内容：
	 * @参数：@param flowid
	 * @return:void
	 * @throws
	 */
	@Override
	public void undoCostjf(String flowid, int jfValidYear) {
		
		throw new RuntimeException("已废弃！。请使用cancelJf（）");
		
		/*JfFlow flow = flowDao.get(new StringUK(flowid));
		if (!flow.status.equals(JfFlow.NOR) || flow.jf > 0)
			throw new RuntimeException("状态正常的消耗明细才能撤销");
		undojfFlow(flow);

		boolean hasFlowValid = (jfValidYear != INT_NOFLOWVALID); // 每笔积分获取都有有效期，则需要记住扣的哪些获得明细
		if (hasFlowValid) {
			undocostDetail(flow);
		}*/
	}

	/**
	 * @方法功能说明：积分扣减明细（从哪些获得明细上扣减）
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午3:50:13
	 * @修改内容：
	 * @参数：@param flow
	 * @return:总共扣减的奖励积分
	 * @throws
	 */
	private int costDetail(JfFlow flow) {
		 ScrollableResults gotlist = flowDao.getGotList( flow.account.getId());// 
		int to_use = flow.jf * (-1);// 还要扣的积分
		int use;// 此次使用
		int totalUse = 0;//总共使用
		while(gotlist.next()) {
			JfFlow f=new JfFlow();
			Object[] r=gotlist.get();
			f.flowId=new StringUK(r[0].toString());
			f.jf=Integer.parseInt(r[1].toString());
			f.usejf=Integer.parseInt(r[2].toString());
			
			if (to_use == 0)// 扣够了，退出
				break;

			int canUsejf = f.jf - f.usejf;
			if (canUsejf <= 0)
				continue;

			use = canUsejf < to_use ? canUsejf : to_use;
			to_use -= use;
			totalUse += use;
			f.usejf += use;
			f.updateTime = new Date();
			String sql = "update jf_flow set use_amount="+f.usejf +",update_time=now()"
					+"," + f.getUpdateCanConsumeSql()+" where id='"
					+f.flowId.getS()+"'";
			int up=flowDao.update(sql);
			if(up!=1)
				throw new RuntimeException("failed sql:"+sql);
			
			// 扣减明细
			JfUseDetail useDetail = new JfUseDetail(flow.flowId.getS(),
					f.flowId.getS(), use);
			useDetail.status = JfFlow.NOR;
			useDao.save(useDetail);
		}
		gotlist.close();
		if (to_use != 0) {
			/*if (flow.tradeType == JfTradeType.adjust)
				HgLogger.getInstance().warn("",
						"该调整无法获取扣减明细！导致余额为负。" + flow.flowId);
			else
				throw new RuntimeException("无法获取扣减明细！");*/
			log.warn(String.format("流水%s共需要%s积分，其中%s积分无法从带有效期的流水中扣减，将从无有效期的流水中扣减(不记jf_use)！",flow.getId(),flow.jf+"",to_use+""));
		}
		return totalUse;
	}

	/**
	 * @方法功能说明：撤销积分扣减明细（从哪些获得明细上扣减）
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午3:50:13
	 * @修改内容：
	 * @参数：@param flow
	 * @return:最大的有效期， null表示永久期限
	 * @throws
	 */
	private String undocostDetail(JfFlow flow) {
		int total = flow.jf;//交易积分
		int useTotal =0;//交易积分中的有有效期积分
		List<JfUseDetail> uselist = useDao.getUseList(flow.flowId.toString());
		String max = "20000101";
		for (JfUseDetail f : uselist) {
			f.status = JfFlow.UNDO;
			JfFlow getFlow = flowDao.get(new StringUK(f.getFlow));// 获得明细
			getFlow.usejf -= f.jf;// 已用积分还原
			useTotal -=f.jf;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date invalidDate = getFlow.getInvalidDate();
			if (invalidDate != null) {
				String invalid = sdf.format(invalidDate);
				try {
					if (Integer.parseInt(invalid) > Integer.parseInt(sdf.format(sdf.parse(max)))) {
						max = invalid;
					}
					max = sdf.format(sdf.parse(max));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			flowDao.saveEntity(getFlow);
		}
		if (max.equals("20000101") || total!=useTotal) {
			return null;
		}
		return max;
	}

	/**
	 * 
	 * @方法功能说明：积分调整,允许调为负数
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月3日下午2:34:02
	 * @修改内容：
	 * @参数：@param cal
	 * @return:void
	 * @throws
	 */
	@Override
	public void adjust(JfChangeApply cal, int jfValidYear) {
		JfTradeType trade = JfTradeType.adjust;
		if (cal.getjf() > 0)
			gotJf(cal, trade, cal.getFlowStatus(), jfValidYear/*, null*/);
		else
			costJf(cal, trade,JfProperty.getJfValidYear() );//2015.12.24,积分调减的有效期按系统配置，非界面传递
		// updateAcc (cal, trade, INT_NOFLOWVALID,true,false);
	}
	
	@Override
	public void transferin(JfChangeApply cal, int jfValidYear) {
		JfTradeType trade = JfTradeType.in;
		if (cal.getjf() > 0)
			gotJf(cal, trade, cal.getFlowStatus(), INT_NOFLOWVALID/*, null*/);
		else
			throw new RuntimeException("转入积分时候交易的积分应该是正值！");
		// updateAcc (cal, trade, INT_NOFLOWVALID,true,false);
	}	
	@Override
	public void charge(JfChangeApply cal) {
		JfTradeType trade = JfTradeType.charge;
		if (cal.getjf() > 0)
			gotJf(cal, trade, cal.getFlowStatus(), INT_NOFLOWVALID/*, null*/);
		else
			throw new RuntimeException("充值积分时候交易的积分应该是正值！");
		// updateAcc (cal, trade, INT_NOFLOWVALID,true,false);
	}	
	
	@Override
	public void transferout(JfChangeApply cal, int jfValidYear) {
		JfTradeType trade = JfTradeType.out;
		if (cal.getjf() > 0)
			throw new RuntimeException("转出积分时候交易的积分应该是负值！");
		else
			costJf(cal, trade, jfValidYear);
		// updateAcc (cal, trade, INT_NOFLOWVALID,true,false);
	}
	
	public static void main(String[] args) {
		Calendar validC = Calendar.getInstance();
		validC.setTime(new Date() );
		validC.add(Calendar.DAY_OF_MONTH, 30);
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(d.format(validC.getTime()));
		validC.add(Calendar.DAY_OF_MONTH, 30);
		System.out.println(d.format(validC.getTime()));
		validC.add(Calendar.DAY_OF_MONTH, 30);
		System.out.println(d.format(validC.getTime()));
		validC.add(Calendar.DAY_OF_MONTH, 30);
		System.out.println(d.format(validC.getTime()));
		
	}
	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月17日下午4:08:27
	 * @version：
	 * @修改内容：
	 * @参数：@param acct
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean hasAny(JfAccountType acct) {
	    return accountDao.hasAny(acct);
	}
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.service.IAccountService#undoCostjf(java.lang.String, int, java.lang.String)
	 */
	@Override
	public void undoCostjf(String flowid, int jfValidYear, String message) {
		undoCostjf(flowid, jfValidYear);
		JfFlow flow = flowDao.get(new StringUK(flowid));
		flow.setDetail(message);
		flowDao.updateEntity(flow);
	}

	 

	/**
	 * @return 
	 * @方法功能说明：积分撤销.实际上仅仅积分扣减（包括消费和转出）的撤销
	 * 新版本的积分撤销。考虑了 1）积分有效期处理（撤销有效期顺延）
	 * 2）原来作废，新插入一条流水
	 * @修改者名字：zhaoqifeng
	 * @修改时间：2014年11月26日下午2:07:54
	 * @修改内容：
	 * @参数：@param cal
	 * @参数：@param trade
	 * @参数：@param jfValidYear
	 * @return:void
	 * @throws
	 */
	@Override
	public JfFlow cancelJf(JfChangeApply cal, int jfValidYear) {
//		StringUK userid = new StringUK(cal.getuserCode());
//		JfUser user = jfuserDao.get(userid);
//		if (true && user == null) {
//			user = jfuserDao.newEntity(userid);
//			jfuserDao.insertEntity(user);
//			// 由于StringUK是每次new出来的，hibernate认为是不同的（即使同一usercode），导致hibernate.get出来为空对象。使用flush会对数据库做insert
//			jfuserDao.flush();
//		}
//		JfAccountType acctype = (JfAccountType) cal.getaccountType();
//		if (acctype == null)
//			throw new RuntimeException("积分类型不被识别");
//		JfAccountUK uk = new JfAccountUK(user, acctype);
//		JfAccount acc = accountDao.get(uk);
//		if (acc == null) {
//			acc = accountDao.newEntity(uk);
//		}

		String flowId = cal.gettradeFlowId();
		String getremark = cal.getremark();
		
		return cancelJf(flowId, getremark, jfValidYear);
	}
	/**
	 * 2015.9，jf1.5.
	 * 新版本的积分撤销。考虑了 1）积分有效期处理（撤销有效期顺延）
	 * 2）原来作废，新插入一条流水
	 * @param flowId
	 * @param remark
	 * @param jfValidYear
	 * @return old jf_flow
	 */
	@Override
	public JfFlow cancelJf(String flowId, String remark, int jfValidYear) {
		JfTradeType trade=JfTradeType.undo;
		JfFlow flow = flowDao.get(new StringUK(flowId));
		JfAccount acc =flow.account;
		
		if (!flow.status.equals(JfFlow.NOR))
			throw new RuntimeException("状态正常的交易才能撤销");
		// 日期格式化
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		flow.setStatus(flow.UNDO);
		flow.setDetail(remark);
		flow.updateTime = date;
		JfFlow flow2 = new JfFlow();

//		撤销的限制
//		if (!sdf.format(flow.insertTime).equals(sdf.format(date)) && flow.tradeType.code.equals(trade.out.code)) {
//			throw new RuntimeException("转出撤销只能当天执行");
//		}

		// 区分是否在途
		int mallSettlementCycle = JfProperty.getMallSettlementCycle();
		if (sdf.format(flow.insertTime).equals(sdf.format(date)) || mallSettlementCycle == 0) {
		//一天内，资金未清算，积分立即返回
			acc.jf -= flow.jf;
			flow2.status = JfFlow.NOR;
		} else {
		//超过一天，积分消费的资金可能已清算，积分不能立即返回，使其在途	
			acc.jfTODO -= flow.jf;
			ca.setTime(date);
			ca.add(Calendar.DAY_OF_MONTH, mallSettlementCycle);
			flow2.setValidDate(ca.getTime());
			flow2.setInvalidDate(flow.getInvalidDate());
			flow2.status = JfFlow.ARRVING;
		}
		acc.lastUpdate = new Date();

		flow2.account = flow.account;
		flow2.user = flow.user;
		flow2.jf = flow.jf * -1;
		flow2.tradeType = trade;
		flow2.setInsertTime(new Date());
		flow2.fee = flow.fee * -1;
		flow2.setMerchandiseAmount(flow.getMerchandiseAmount());

//		if (jfValidYear != INT_NOFLOWVALID) {
//			Calendar validC = Calendar.getInstance();
//			validC.setTime(flow2.insertTime);
//			validC.add(Calendar.YEAR, jfValidYear);
//			flow2.setInvalidDate(validC.getTime());
//		}
		//flow2.setInvalidDate(flow.getInvalidDate());

		flow2.refFlowId = flowId;
		
		flow2.detail = remark;
		flow2.batchNo = flow.getBatchNo();
		flow2.merchant = flow.getMerchant();
		flow2.merchandise = flow.getMerchandise();
		flow2.merchandiseAmount = flow.merchandiseAmount;
		flow2.acctJf = (acc.jf);
		flow2.rate = flow.rate;
//		JfAccountType jt = (JfAccountType) cal.getAccountTypeForJfRate();
//		if (jt != null)
//			flow2.rate = jt.jfRate + "";

		flow2.setMerchandiseStatus(flow.getMerchandiseStatus());
		flow2.setSendStatus(flow.getSendStatus());
		flow2.setIndustryCode(flow.getIndustryCode());
		flow2.setIndustryName(flow.getIndustryName());
		flow2.setParentOrgCode(flow.getParentOrgCode());
		flow2.setOrgCode(flow.getOrgCode());
		//带有效期的撤销
		boolean hasFlowValid = (jfValidYear != INT_NOFLOWVALID); // 每笔积分获取都有有效期，则需要记住扣的哪些获得明细
		if (hasFlowValid) {
			String max = undocostDetail(flow);
			if (max != null) {
				Date invalid = null;
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					invalid = format.parse(max);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				int days = (int) ((date.getTime() - flow.getInsertTime().getTime()) / 85400000);
				ca.setTime(invalid);
				ca.add(Calendar.DAY_OF_MONTH, days);
				flow2.setInvalidDate(ca.getTime());
			}
		}

		flowDao.updateEntity(flow);
		accountDao.saveEntity(acc);
		flowDao.insertEntity(flow2);
		return flow;
	}
	 

}
