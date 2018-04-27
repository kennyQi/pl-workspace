/**
 * @文件名称：FlowDao.java
 * @类路径：hgtech.jfadmin.dao
 * @描述：积分流水dao 
 * @作者：xinglj
 * @时间：2014年10月31日下午3:33:11
 */
package hgtech.jfaccount.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.ScrollableResults;

import hg.common.page.Pagination;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfaccount.BStatAccount;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfFlow;

/**
 * @类功能说明：积分流水dao
 * @类修改者：
 * @修改日期：2014年10月31日下午3:33:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月31日下午3:33:11
 *
 */
public interface FlowDao extends EntityDao<StringUK, JfFlow>{
	/**
	 * @方法功能说明：分页查询积分流水
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:33:50
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	Pagination findPagination(Pagination pagination);
	
	/**
	 * 
	 * @方法功能说明：对账单查询
	 * @修改者名字：penel
	 * @修改时间：2014年11月27日上午11:24:51
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	List<JfFlow> getjfFlowList(Pagination pagination);
	/**
	 * 
	 * @方法功能说明：获取一个账户的可用来扣减积分的获得明细
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日上午11:26:29
	 * @修改内容：
	 * @参数：@param account
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	public ScrollableResults getGotList(String acctId);
	/**
	 * @方法功能说明：根据流水源数据的ID查找积分流水
	 * @修改者名字：pengel
	 * @修改时间：2014年12月4日上午11:10:20
	 * @修改内容：
	 * @参数：@param refFlowId
	 * @参数：@return
	 * @return:JfFlow
	 * @throws
	 */
	public JfFlow getFlowByRefFlowId(String refFlowId);

	/**
	 * @方法功能说明：转入统计
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月3日下午5:34:16
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	List<BStatAccount> transferInStat(Object condition);

	/**
	 * @方法功能说明：转入查询明细
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月4日上午11:20:58
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List
	 * @throws
	 */
	List transferInMemo(Object condition);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日上午9:47:49
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	List<JfFlow> transferOutMemo(Object condition);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日上午9:47:58
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List<BStatAccount>
	 * @throws
	 */
	List<BStatAccount> transferOutStat(Object condition);

	/**
	 * @方法功能说明：查询n天前那一天的在途流水
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日下午5:02:06
	 * @version：
	 * @修改内容：
	 * @参数：@param day
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	List<JfFlow> queryArrivingBefore(JfAccountType accountType);
	/**
	 *查询因积分支付撤销而要要生效的积分流水 
	 * @return
	 */
	List<JfFlow> queryArriving4Other();
	
	List transferMemo(Object condition);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年6月8日下午4:16:15
	 * @version：
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:JfFlow
	 * @throws
	 */
	JfFlow get(String id);

	/**
	 * @方法功能说明：获取对方用户
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-30上午10:11:31
	 * @version：
	 * @修改内容：
	 * @参数：@param user
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	List<String> findMerchantByUser(String user);

	/**
	 * 获取消费流水
	 * @return
	 */
	List<JfFlow> queryEx();

	/**
	 * 获取转入流水
	 * @return
	 */
	List<JfFlow> queryIn();

	/**
	 * 获取转出流水
	 * @return
	 */
	List<JfFlow> queryOut();


	/**
	 * @方法功能说明：查询对南航的某卡号互换
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年8月31日下午1:44:01
	 * @version：
	 * @修改内容：
	 * @参数：@param batchno
	 * @参数：@param czCardNo
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	List<JfFlow> queryCzFlow(String batchno, String czCardNo);

	/**
	 * @方法功能说明：奖励明细
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年9月3日下午5:20:05
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List<JfFlow>
	 * @throws
	 */
	List<JfFlow> calMemo(Object condition);

	/**
	 * 根据用户名查询流水
	 * @param loginName
	 * @return
	 */
	List<JfFlow> queryFlow(String loginName);

	/**
	 * 查询撤销流水
	 * @return
	 */
	List<JfFlow> queryCancel();

	/**
	 * 查询撤销的原交易流水
	 * @return
	 */
	JfFlow queryOldFlow(String oldFlow);

	Pagination allMemoPage(Object condition, int pageNo, int pageSize);

	JfAccount memoTotal(Object condition);

	int update(String sql);
	
	/**
	 * 
	 * @方法功能说明：获取南航当月转出次数
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-10-20上午9:37:04
	 * @version：
	 * @修改内容：
	 * @参数：@param user
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public int  getCzCountMonth(String czUser);

	/**
	 * 
	 * @param account
	 * @return 基本积分（永久积分），相对于奖励积分而言
	 */
	long getBaseJfByFlow(JfAccount account);

	long getUsedAwardJfByFlow(JfAccount account);
	
}
