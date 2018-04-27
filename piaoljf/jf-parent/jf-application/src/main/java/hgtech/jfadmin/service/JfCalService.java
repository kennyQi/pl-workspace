/**
 * @文件名称：JfService.java
 * @类路径：hgtech.jfadmin.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月20日下午4:49:51
 */
package hgtech.jfadmin.service;

import hgtech.jf.JfChangeApply;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfaccount.AdjustBean;
import hgtech.jfaccount.JfFlow;
import hgtech.jfadmin.dto.*;import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月20日下午4:49:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月20日下午4:49:51
 *
 */
public interface JfCalService {
	/**
	 * 
	 * @方法功能说明：根据规则的变化同步
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月29日上午8:43:37
	 * @修改内容：
	 * @参数：@throws IllegalAccessException
	 * @参数：@throws InvocationTargetException
	 * @return:void
	 * @throws
	 */
	public abstract void refreshCalModel() throws Exception
			;
	/**
	 * 账户调整
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月3日上午11:31:19
	 * @修改内容：
	 * @参数：@param json
	 * @return:void
	 * @throws
	 */
	public abstract void adjust(String json);
	/**
	 * 
	 * @方法功能说明：中间数据查询
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月3日上午11:31:30
	 * @修改内容：
	 * @参数：@param user
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:List<SessionController.SessionDto>
	 * @throws
	 */
	public abstract List<SessionDto> querySession(String user) throws Exception;
	
	/**
	 * 账户积分调整
	 * @方法功能说明：
	 * @修改者名字：xy
	 * @修改时间：2014年11月4日 
	 * @修改内容：
	 * @参数：@param json
	 * @return:void
	 * @throws
	 */
	public abstract void adjustAcountJf(JfChangeApply cal, int validDay);
	
	/**
	 * 批量账户积分调整
	 * @方法功能说明：
	 * @修改者名字：xy
	 * @修改时间：2014年12月17日 
	 * @修改内容：
	 * @参数：@param json
	 * @return:void
	 * @throws
	 */
	void batchadjustJf(jfAdjustDto dto, List<AdjustBean> jfList ) throws Exception;
	
	/**
	 * @方法功能说明：积分消耗
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月20日下午3:27:49
	 * @修改内容：
	 * @参数：@param json
	 * @return:void
	 * @throws
	 */
	JfFlow[] exchange(String json);
	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月19日上午9:58:15
	 * @version：
	 * @修改内容：
	 * @参数：@param json
	 * @return:void
	 * @throws
	 */
	void transferout(String json);
	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月19日上午9:58:26
	 * @version：
	 * @修改内容：
	 * @参数：@param json
	 * @return:void
	 * @throws
	 */
	void transferin(String json);
	/**
	 * @return 
	 * @方法功能说明：积分撤销
	 * @修改者名字：zhaoqifeng
	 * @修改时间：2015年8月5日上午9:58:26
	 * @version：
	 * @修改内容：
	 * @参数：@param json
	 * @return:void
	 * @throws
	 */
	public JfFlow[] cancelJf(String json);
	/**
	 * 按批次号撤销
	 */
	public abstract   JfFlow[] cancelBatchNo(String json);	
	public void charge(String json);
	public abstract void charge(String json, String appId);

	/**
	 * 不记cal_flow。
	 * @param trade
	 * @return
	 */
	public abstract Collection<CalResult> cal(PiaolTrade trade);
	/**
	 * 
	 *  计算并返回计算结果(CalResult).记入表cal_flow
	 */
	public String cal(String json);
	public String calNoSave(String json);
	/**
	 * @方法功能说明：计算、入账
	 */
	public Collection<CalResult> calAndCommit(String json);
	public Collection<CalResult> calAndCommit(String json, String appId);
	public abstract Collection<CalResult> calCommit(PiaolTrade trade);
	public abstract void removeRule(String code);
	public abstract void addRule(Rule r);

	
}
