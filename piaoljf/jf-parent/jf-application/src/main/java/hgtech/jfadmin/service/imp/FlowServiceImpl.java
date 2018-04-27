/**
 * @文件名称：FlowServiceImpl.java
 * @类路径：hgtech.jfadmin.service.imp
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年11月3日下午1:58:59
 */
package hgtech.jfadmin.service.imp;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 





















import hg.common.page.Pagination;
import hgtech.jf.entity.StringUK;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.dao.FlowDao;
import hgtech.jfadmin.dto.*;
import hgtech.jfadmin.dao.CalFlowDao;
import hgtech.jfadmin.dao.JFExpireDao;
import hgtech.jfadmin.dao.imp.FlowDaoImp;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfadmin.hibernate.RuleHiberEntity;
import hgtech.jfadmin.service.FlowService;
import hgtech.jfadmin.service.RuleService;
import hgtech.jfcal.model.Rule;

/**
 * @类功能说明：积分查询Service实现类
 * @类修改者：
 * @修改日期：2014年11月3日下午1:58:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年11月3日下午1:58:59
 *
 */
@Transactional
@Service("flowService")
public class FlowServiceImpl implements FlowService {
	@Resource
	FlowDao flowDao;
	@Resource
	JFExpireDao jfExpireDao;
	@Resource
	CalFlowDao calFlowDao;

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.FlowService#findPagination(hg.common.page.Pagination)
	 */
	@Override
	public Pagination findPagination(Pagination pagination) {
		
		return flowDao.findPagination(pagination);
	}

	@Override
	public List<JfFlow> getjfFlowList(Pagination pagination) {
		
		return flowDao.getjfFlowList(pagination);
	}

	@Override
	public void flowAdjust(jfAdjustDto dto) {
		
		StringUK uk=new StringUK(dto.getAccount());
		JfFlow flow=flowDao.get(uk);
		//System.out.println(flow.getUsejf());
		JfFlow flow2=new JfFlow();
		System.out.println(flow2.getFlowId());
		flow2.setAccount(flow.getAccount());
		flow2.setBatchNo(flow.getBatchNo());
		flow2.setInsertTime(flow.getInsertTime());
		flow2.setStatus(flow.getStatus()); 
		flow2.setUsejf(flow.getUsejf());//要现将元数据中的使用积分清零，修改jf_use数据表将所有用到该数据的数据状态修改为撤销，总分加上清零之前的分值，然后再进行消耗操作
		flow2.setUser(flow.getUser());
		flow2.setInvalidDate(flow.getInvalidDate());
		flow2.setJf(Integer.parseInt(dto.getScore()));
		flow2.setDetail(dto.getDescription());
		flow2.setRefFlowId(flow.getFlowId().getS());
		flow2.setUpdateTime(new Date());
		JfTradeType tradeType=JfTradeType.adjed;
		flow2.setTradeType(tradeType);
		System.out.println(flow2.getFlowId());
		flowDao.saveEntity(flow2);
		
		flow2=flowDao.getFlowByRefFlowId(flow.getFlowId().getS());
		System.out.println(flow2.getFlowId());
		
		/*flow.setUpdateTime(new Date());
		flow.setDetail(flow2.getFlowId().getS());
		flow.setStatus(JfFlow.ADJED);
		flowDao.saveEntity(flow);
		System.out.println(flow.getFlowId());
		
		JfFlow flow3=flowDao.get(uk);
		System.out.println("");*/
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.FlowService#transferInStat(java.lang.Object)
	 */
	@Override
	public List  transferInStat(Object condition) {
	    return flowDao.transferInStat(condition);
	}

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.FlowService#transferInMemo(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List transferInMemo(Object condition) {
	    List in = flowDao.transferInMemo(condition);
	    
	    /*List cal = flowDao.calMemo(condition);
	    //将奖励明细和转入明细何在一起都算转入
	    in.addAll(cal);
	    Collections.sort(in, new Comparator<JfFlow>() {
		 (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 
		@Override
		public int compare(JfFlow o1, JfFlow o2) {
		    //倒叙
		    return o2.getInsertTime().compareTo(o1.getInsertTime());
		}
	    });*/
	    
	    return in;
	}
	
	@Override
	public List calMemo(CalLogDto dto){
	     
		TransferStatQo condition = new TransferStatQo();
		condition.setUserCode(dto.getIn_userCode());
		condition.setFromDate(dto.getStartTime());
		condition.setToDate(dto.getEndTime());
		List list = calMemoPage(condition);
		refreshRuleObj(list);
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List calMemoPage(TransferStatQo condition) {
	    List<JfFlow> calMemo = flowDao.calMemo(condition);
	    this.refreshRuleObj(calMemo);
		return calMemo;
	}	

	@Autowired
	RuleService ruleService;
	
	void refreshRuleObj(List<JfFlow> list){
		for(JfFlow f:list){
				RuleHiberEntity rule = ruleService.queryByCode(f.getRule());
				f.setRuleObj(rule);
		}
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.FlowService#transferInStat(java.lang.Object)
	 */
	@Override
	public List  transferOutStat(Object condition) {
	    return flowDao.transferOutStat(condition);
	}
	

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.FlowService#transferInMemo(java.lang.Object)
	 */
	@Override
	public List transferOutMemo(Object condition) {
	    return flowDao.transferOutMemo(condition);
	}	
	/**
	 * 所有明细
	 */
	@Override
	public List transferMemo(Object condition) {
		List list = flowDao.transferMemo(condition);
		refreshRuleObj(list);
	    return list;
	}
	
	@Override
	public Pagination allMemoPage(Object condition, int pageSize, int pageNo){
		return flowDao.allMemoPage(condition, pageNo, pageSize);
	}
	
	
	@Override
	public JfAccount MemoTotal(Object condition) {
	    return flowDao.memoTotal(condition);
	}

	
	@Override
	public JfFlow get(String id){
	    return flowDao.get(id);
	}
	@Override
	public void update(JfFlow flow){
		flowDao.updateEntity(flow);  
		flowDao.flush();
    }

	
	@Override
	public List<String> findMerchantByUser(String user) {
		return flowDao.findMerchantByUser(user);
	}


	@Override
	public int getCzCountMonth(String czUser) {
		
		return flowDao.getCzCountMonth(czUser);
	}
	@Override
	public void check(String[] idList) {
		for (String id : idList) {
			JfFlow flow = flowDao.get(id);
			flow.setStatusCheck(JfFlow.STATUS_CHECK_PASS);
			update(flow);
		}

	}

	/**
	 * 明细的汇总
	 * @param lst
	 * @return
	 */
	public static JfAccount getMemoTotal(List<JfFlow> lst) {
		JfAccount sum = new JfAccount();
		for (JfFlow f : lst) {
//			//只统计消费积分的
//			if(!f.account.uk.type.uk.consumeType.code.equals("consume"))
//				continue;
			//2015.11.26, 用户要在转出明细可查看失败交易
			if(JfFlow.isStatusFluenceAccount(f.status ) && !JfFlow.UNDO.equals(f.status)){
				if (f.jf>0)
					sum.jfIn += f.jf;
				else
					sum.jfOut +=f.jf;
			}else if(f.status.equals(f.ARRVING))
				sum.jfTODO += f.jf;
			
		}
		return sum;
	}
	@Override
	public List expireMemo(String userCode, int expireWithinDays) {
		return jfExpireDao.queryExpireFlow(userCode, expireWithinDays);
	}
	/**
	 * 1:有要过期的，0：没有
	 */
	@Override
	public int expireSize(String userCode, int expireWithinDays) {
		return jfExpireDao.queryExpireFlowSize(userCode, expireWithinDays);
	}

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.FlowService#getBaseJfByFlow(hgtech.jfaccount.JfAccount)
	 */
	@Override
	public long getBaseJfByFlow(JfAccount account) {
		return flowDao.getBaseJfByFlow(account);
	} 	
}
