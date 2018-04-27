/**
 * @IAccountService.java Create on 2015年1月28日下午1:46:16
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfaccount.service;

import hgtech.jf.JfChangeApply;
import hgtech.jfaccount.JfFlow;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年1月28日下午1:46:16
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月28日下午1:46:16
 * @version：
 */
public interface IAccountService  {

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
    public void commit(JfChangeApply cal, int jfValidYear, String calID);

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
    public JfFlow transferIn(JfChangeApply order/*,String orderid*/);

    /**
     * @return 
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
    public JfFlow transferOut(JfChangeApply order/*, String orderid*/);

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
    public void undoGotjf(String flowid);

    /**
     * 改变在途积分状态
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月10日下午5:40:51
     * @version：
     * @修改内容：
     * @参数：@param flowid
     * @return:void
     * @throws
     */
    public void changeArriveState(String flowid, boolean ok);

    /**
     * @方法功能说明：改变在途积分状态
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月10日下午5:54:28
     * @version：
     * @修改内容：
     * @参数：@param flow
     * @参数：@param ok
     * @return:void
     * @throws
     */
    public void changeArriveState(JfFlow flow, boolean ok);

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
    public JfFlow exchange(JfChangeApply cal, int jfValidYear);

    /**
     * @param jfValidYear
     * 
     * @方法功能说明：消耗明细的撤销
     * @修改者名字：xinglj
     * @修改时间：2014年11月26日下午3:34:25
     * @修改内容：
     * @参数：@param flowid
     * @return:void
     * @throws
     */
    public void undoCostjf(String flowid, int jfValidYear);

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
    public void adjust(JfChangeApply cal, int jfValidYear);

    public void transferin(JfChangeApply cal, int jfValidYear);

    public void transferout(JfChangeApply cal, int jfValidYear);

	/**
	 * @方法功能说明：加入失败信息
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-29下午4:09:29
	 * @version：
	 * @修改内容：
	 * @参数：@param flowid
	 * @参数：@param jfValidYear
	 * @参数：@param message
	 * @return:void
	 * @throws
	 */
	void undoCostjf(String flowid, int jfValidYear, String message);

	/**
	 * @return 
	 * @方法功能说明：积分撤销(1.5版）
	 * @修改者名字：zhaoqifeng
	 * @修改时间：2014年11月26日下午3:34:25
	 * @修改内容：
	 * @参数：@param flowid
	 * @param jfValidYear
	 * @return:void
	 * @throws
	 */
	JfFlow cancelJf(JfChangeApply cal, int jfValidYear);

	/*void redoGotjf(String flowid);
*/
	/**
	 * 积分撤销(1.5版）
	 * @param flowId
	 * @param remark
	 * @param jfValidYear
	 * @return old jfflow
	 */
	JfFlow cancelJf(String flowId, String remark, int jfValidYear);

	/**
	 * 重做 未响应
	 * @param flowid
	 */
	JfFlow redoNoReplyjf(String flowid);

	/**
	 * 确认 未响应
	 * @param flowid
	 */
	void confirmNoReplyjf(String flowid);

	void charge(JfChangeApply cal);

	 
}