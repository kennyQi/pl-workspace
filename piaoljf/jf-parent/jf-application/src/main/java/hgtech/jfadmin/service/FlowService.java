package hgtech.jfadmin.service;

import java.util.List;

import hg.common.page.Pagination;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfFlow;
import hgtech.jfadmin.dto.*;
/**
 * 
 * @类功能说明：流水查询Service
 * @类修改者：
 * @修改日期：2014年11月3日下午1:56:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年11月3日下午1:56:53
 *
 */
public interface FlowService {
	/**
	 * @方法功能说明：积分流水分页查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:38:38
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	Pagination findPagination(Pagination pagination);
	
	/**
	 * @方法功能说明：积分流水对账单查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:38:38
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	List<JfFlow> getjfFlowList(Pagination pagination);
	
	/**
	 * @方法功能说明：积分流水调整
	 * @修改者名字：pengel
	 * @修改时间：2014年12月4日上午10:23:27
	 * @修改内容：
	 * @参数：@param dto
	 * @return:void
	 * @throws
	 */
	void flowAdjust(jfAdjustDto dto);

	/**
	 * @方法功能说明：为企业端的查询
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月3日下午3:37:09
	 * @version：
	 * @修改内容：
	 * @参数：@param paging
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	List  transferInStat(Object condition);
	/**
	 * @方法功能说明：查询明细
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月3日下午3:37:09
	 * @version：
	 * @修改内容：
	 * @参数：@param paging
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	List  transferInMemo(Object condition);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日上午9:53:07
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List
	 * @throws
	 */
	List transferOutMemo(Object condition);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日上午9:53:13
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List
	 * @throws
	 */
	List transferOutStat(Object condition);

	/**
	 * 所有明细
	 * @param condition
	 * @return
	 */
	List transferMemo(Object condition);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年6月8日下午4:18:50
	 * @version：
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:JfFlow
	 * @throws
	 */
	JfFlow get(String id);
	/**
	 * 
	 * @方法功能说明：更新流水
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-23上午9:47:50
	 * @version：
	 * @修改内容：
	 * @参数：@param flow
	 * @return:void
	 * @throws
	 */
	public void update(JfFlow flow);
	
	/**
	 * 
	 * @方法功能说明：更新流水
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-23上午9:47:50
	 * @version：
	 * @修改内容：
	 * @参数：@param flow
	 * @return:void
	 * @throws
	 */
	public List<String> findMerchantByUser(String user);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年9月8日上午10:24:50
	 * @version：
	 * @修改内容：
	 * @参数：@param condition
	 * @参数：@return
	 * @return:List
	 * @throws
	 */
	List calMemo(CalLogDto dto);

	/**
	 * 将要失效的流水
	 * @param userCode
	 * @param expireWithinDays
	 * @return
	 */
	List expireMemo(String userCode, int expireWithinDays);

	Pagination allMemoPage(Object condition, int pageSize, int pageNo);

	JfAccount MemoTotal(Object condition);

	/**
	 * 1:有要过期的，0：没有
	 */
	int expireSize(String userCode, int expireWithinDays);

	 
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
	 * 标记互转流水为已审核
	 * @param idList2
	 */
	void check(String[] idList2);

	List calMemoPage(TransferStatQo condition);
	
	/**
	 * 
	 * @param account
	 * @return 基本积分（永久积分），相对于奖励积分而言
	 */
	long getBaseJfByFlow(JfAccount account);
}
