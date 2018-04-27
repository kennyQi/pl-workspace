/**
 * 
 */
package hgtech.jfadmin.dao;

import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfFlow;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public interface JFExpireDao {
	
	/**
	 * 
	 * @方法功能说明：查询当日的积分流水
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	public List<JfFlow> queryByNowDate();

	/**
	 * 
	 * @方法功能说明：更新积分流水为过期
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	public void updateFlowExpire(JfFlow jfFlow);

	/**
	 * 
	 * @方法功能说明：更新用户
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	public void updateJFExpire(JfAccount jfAcccount);

	/**
	 * 
	 * @方法功能说明： 处理过期积分 
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	public void updateJf();
	
	/*
	 * 查询近期过期流水
	 */
	List<JfFlow> queryExpireFlow(String usercode, int days);

	public int queryExpireFlowSize(String userCode, int expireWithinDays);
}
