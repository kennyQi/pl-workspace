/**
 * 
 */
package hgtech.jfadmin.service.imp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.dao.FlowDao;
import hgtech.jfaccount.listener.FlowListener;
import hgtech.jfaccount.service.AccountService;
import hgtech.jfadmin.dao.JFExpireDao;
import hgtech.jfadmin.service.JFTaskService;

/**
 * @author Administrator
 * 
 */
@Transactional
@Service("jfTaskService")
public class JFTaskServiceImpl  implements JFTaskService {

	@Resource
	JFExpireDao  jfExpireDao;
	@Autowired
	AccountService accountService;
	@Resource
	FlowDao flowDao;
	/**
	 * 
	 * @方法功能说明：查询当日的积分流水
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	@Override
	public List<JfFlow> queryByNowDate() {
		return jfExpireDao.queryByNowDate();
	}


	/**
	 * 
	 * @方法功能说明：更新积分流水为过期
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	@Override
	public void updateFlowExpire(JfFlow jfFlow) {
		jfExpireDao.updateFlowExpire(jfFlow);
	}

	/**
	 * 
	 * @方法功能说明：更新用户
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	@Override
	public void updateJFExpire(JfAccount jfAcccount) {
		jfExpireDao.updateJFExpire(jfAcccount);
	}
	
	/**
	 * 
	 * @方法功能说明：过期积分处理
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	@Override
	public void updateJf()
	{
		jfExpireDao.updateJf();
	}
	
	@Autowired
	FlowListener flowListener;
	
	/**
	 * 
	 * @方法功能说明：将几天后的在途积分变为正常
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日下午4:58:38
	 * @version：
	 * @修改内容：
	 * @参数：@param day
	 * @return:void
	 * @throws
	 */
	@Override
	public void changeArrving2Nor(JfAccountType accountType){
	    List<JfFlow> flows = flowDao.queryArrivingBefore(accountType);
	    System.out.println("在途要变为正常状态的流水 "+flows.size());
	    for(JfFlow f:flows){
			accountService.changeArriveState(f, true);
			flowListener.afterSaveFlow(f);
			System.out.println("生效的流水已送给流水监听器");
	    }
	}
	@Override
	public void changeArrving2Nor4Other() {
	    List<JfFlow> flows = flowDao.queryArriving4Other();
	    System.out.println("撤销操作中在途要变为正常状态的流水 "+flows.size());
	    for(JfFlow f:flows){
		accountService.changeArriveState(f, true);
		flowListener.afterSaveFlow(f);
	    }
	}
}
