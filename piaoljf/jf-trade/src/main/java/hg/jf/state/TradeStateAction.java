/**
 * @OrderStateAction.java Create on 2015年1月18日上午11:44:36
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.state;

/**
 * @类功能说明：在积分互转上的action
 * @类修改者：
 * @修改日期：2015年1月18日上午11:44:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月18日上午11:44:36
 * @version：
 */
public interface TradeStateAction {

	public void tranfserOut();

	public void transferIn();

	public boolean isOutOk();

	public boolean isInOk();

}