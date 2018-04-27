package slfx.jp.app.service.spi;

import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.service.local.policy.PolicyLocalService;
import slfx.jp.domain.model.policy.JPPlatformPolicy;
import slfx.jp.pojo.system.PolicyConstants;
import slfx.jp.qo.admin.policy.PolicyQO;
import slfx.jp.spi.inter.AutoCheckPolicyStatusService;


/**
 * 自动检查价格政策状态
 * @author tandeng
 *
 */
@Service("autoCheckPolicyStatusService")
public class AutoCheckPolicyStatusServiceImpl implements AutoCheckPolicyStatusService{
	
	@Autowired
	PolicyLocalService policyLocalService;
	
	public void run() {
		try{
			check();			
		}catch(Exception e){
			HgLogger.getInstance().error("qiuxianxiang", "AutoCheckPolicyStatus->run->exception[自动检查价格政策状态]:" + HgLogger.getStackTrace(e));
		}
	}
	
	public void check() {
		HgLogger.getInstance().debug("qiuxianxiang", "AutoCheckPolicyStatus[检查价格政策定时任务开始执行]->时间："+new java.util.Date());
		PolicyQO qo = new PolicyQO();
		//条件：状态为：发布     
		qo.setStatus(PolicyConstants.PUBLIC);
		List<JPPlatformPolicy> publicList = policyLocalService.queryList(qo);
		for (JPPlatformPolicy policy : publicList) {
			if (checkToEffect(policy)) {
				policy.setStatus(PolicyConstants.EFFECT);
				policyLocalService.update(policy);
			} 
		}
		
		//条件：状态为：已生效  
		qo.setStatus(PolicyConstants.EFFECT);
		List<JPPlatformPolicy> effectList = policyLocalService.queryList(qo);
		for (JPPlatformPolicy policy : effectList) {
			if (checkToLostEffect(policy)) {
				policy.setStatus(PolicyConstants.LOSE_EFFECT);
				policyLocalService.update(policy);
			}
		}
		
		HgLogger.getInstance().debug("qiuxianxiang", "AutoCheckPolicyStatus[检查价格政策定时任务结束执行]->时间："+new java.util.Date());
	}
	
	//检查是否需要变成生效
	private boolean checkToEffect(JPPlatformPolicy policy) {
		Date beginTime = policy.getBeginTime();
		Date endTime = policy.getEndTime(); 
		Date currentTime = new Date();
		if (beginTime.before(currentTime) && endTime.after(currentTime) ) {
			if (policy.getStatus().equals(PolicyConstants.PUBLIC)) {
				return true;
			}
		}
		return false;
	}
	//检查是否需要变成失效
	private boolean checkToLostEffect(JPPlatformPolicy policy) {
		Date beginTime = policy.getBeginTime();
		Date endTime = policy.getEndTime(); 
		Date currentTime = new Date();
		if (beginTime.before(currentTime) && endTime.before(currentTime) ) {
			if (policy.getStatus().equals(PolicyConstants.EFFECT)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void autoCheckPolicyStatus() {
		run();
	}

}
