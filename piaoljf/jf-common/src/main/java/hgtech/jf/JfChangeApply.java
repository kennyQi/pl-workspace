/**
 * @文件名称：JfChangeResult.java
 * @类路径：hgtech.jfcal.model
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月2日下午2:38:55
 */
package hgtech.jf;

import java.io.Serializable;
import java.util.Date;

/**
 * @类功能说明：积分变动单
 * @类修改者：
 * @修改日期：2014年11月2日下午2:38:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月2日下午2:38:55
 *
 */
public interface JfChangeApply {

	/**
	 * @return the out_jf 本系统变化的积分（含手续费）
	 */
	public abstract int getjf();
	
	/**
	 * 汇积分是否在途,默认否.
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月10日下午5:07:36
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isArriving();
	
	/**
	 * 变为可用的日期(对在途来说）
	 */
	public Date getValidDay();
	/**
	 * 失效的日期.may null
	 */
	public Date getInValidDate();
	
	/**
	 * @return the in_userCode
	 */
	public abstract String getuserCode();
	/**
	 * 
	 * @return 应用id，可以为null
	 */
	public String getAppId();

	/**
	 * @return the in_tradeFlow
	 */
	public abstract Serializable gettradeFlow();

	/**
	 * 变动单本身id
	 * @return
	 */
	public abstract String getId();
	
	/**
	 * @return the 参考流水号
	 */
	public abstract String gettradeFlowId();

	/**
	 * @return the in_remark
	 */
	public abstract String getremark();

	/**
	 * @return the in_batchNo
	 */
	public abstract String getbatchNo();

	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月2日下午1:54:50
	 * @修改内容：
	 * @参数：@return
	 * @return:JfAccountType 账户变动的账户类型。源账户类型
	 * @throws
	 */
	public abstract Object getaccountType();
	/**
	 * 
	 * @方法功能说明：获取待rate的积分类型
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-7-13上午10:36:07
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	public abstract Object getAccountTypeForJfRate();
	/**
	 * 
	 * @方法功能说明：涉及商品
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月11日上午10:29:04
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getMerchandise();
	/**
	 * 
	 * @方法功能说明：花掉的对方数量（含手续费）
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日下午1:46:18
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public int getMerchandiseAmount();
	
	/**
	 * 购买商品是否在途中,默认应该为否
	 */
	public boolean isMerchandiseArriving();
	
	/**
	 * 操作的手续费。单位是本系统还是对方单位由应用决定
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日下午1:46:31
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public int getFee();
	/**
	 * 
	 * @方法功能说明：涉及商户
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月11日上午10:28:43
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getMerchant();
	public String getNoticeMobile();
	public String getMerchandiseStatus();
	/**
	 * jf status 
	 * @see JfFlow
	 * @return
	 */
	public String getFlowStatus();
	public void setFlowStatus(String flowStatus);
	public String getSendStatus();
	public String getRule();

	boolean isNewUser();

	/**
	 * 积分为0 的是否也要保存
	 * @return
	 */
	public abstract boolean isSavejf0();
}