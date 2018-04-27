/**
 * 
 */
package hgtech.jfadmin.service;

import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfFlow;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface JFTaskService {
	
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

	/**
	 * @方法功能说明：@方法功能说明：将几天后的在途积分变为正常，按账户类型
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日下午5:01:43
	 * @version：
	 * @修改内容：
	 * @参数：@param day
	 * @return:void
	 * @throws
	 */
	void changeArrving2Nor(JfAccountType accountType);

	/**
	 * 将撤销操作等其他原因导致的在途积分变为正常
	 */
	public void changeArrving2Nor4Other();
}
