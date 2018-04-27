/**
 * @文件名称：PiaolTrade.java
 * @类路径：hgtech.jfcal.rulelogic.piaol
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-22上午9:55:12
 */
package hgtech.jf.piaol.trade;

import java.io.Serializable;

/**
 * @类功能说明：会员签到信息
 * @类修改者：
 * @修改日期：2014-9-22上午9:52:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22上午9:52:20
 *
 */
public class PiaolTrade implements Serializable{
	/**
	 * 操作名称
	 */
	public String tradeName;
	/**
	 * 用户名
	 */
	public String user;
	/**
	 * 慧眼生日
	 */
	public String birthday;
	/**
	 * 操作日期
	 */
	public String date;
	/**
	 * 会员等级
	 */
	public String level;
    /**
     * 交易金额
     */
	public Double money;
	/**
	 * 交易方式（手机下单，电脑下单）
	 */
	public String tradeWay;
	/**
	 * 普通会员
	 */
	public final static String LEVEL_NORMAL="普通会员"; 
	/**
	 * 白银会员
	 */
	public final static String LEVEL_SILVER="白银会员"; 
	/**
	 * 金牌会员
	 */
	public final static String LEVEL_GOLD="金牌会员"; 
	/**
	 * 白金会员
	 */
	public final static String LEVLE_PT="白金会员"; 
	/**
	 * 钻石会员
	 */
	public final static String LEVEL_DIAMOND="钻石会员"; 
	/**
	 * 签到操作
	 */
	public final static String TRADE_SIGN="sign";
	/**
	 * 购物天数
	 */
	public final static String TRADE_DEAL="deal";
	/**
	 * 完善资料
	 */
	public final static String TRADE_PERFECTINFO="perfectInfo";
	/**
	 * 手机绑定
	 */
	public final static String TRADE_PHONEBINDING="phoneBinding";
	/**
	 * 邮箱绑定
	 */
	public final static String TRADE_MAILBINDING="mailBinding";
	/**
	 * 无线下单
	 */
	public final static String TRADE_WAY_WIRELESS="wireless";
	/**
	 * 电脑下单
	 */
	public final static String TRADE_WAY_COMPUTER="computer";
	
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	
	
}