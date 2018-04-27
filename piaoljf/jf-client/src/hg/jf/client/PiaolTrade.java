/**
 * @文件名称：PiaolTrade.java
 * @类路径：hgtech.jfcal.rulelogic.piaol
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-22上午9:55:12
 */
package hg.jf.client;

import java.io.Serializable;

import javax.persistence.Column;

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
	 * 操作名称 默认为消费
	 */
	public String tradeName=TRADE_CONSUMPTION;
	/**
	 * 用户名
	 */
	public String user;
	/**
	 * 会员生日
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
	 * 交易方式（手机下单，电脑下单）默认电脑下单
	 */
	public String tradeWay=TRADE_WAY_COMPUTER;
	
	/**
	 * 交易所在商户
	 */
	public String merchant;
	/**
	 * 交易的产品
	 */
	public String merchandise;
	/**
	 * 优惠码
	 */
	public String discountCode;
	/**
	 * 交易的产品数量
	 */
	public int merchandiseAmount=0;
	/**
	 * 交易的流水
	 */
	public String flowId;
	
	/**
	 * 普通会员
	 */
	public final static String LEVEL_NORMAL="normal"; 
	/**
	 * 白银会员
	 */
	public final static String LEVEL_SILVER="silver"; 
	/**
	 * 金牌会员
	 */
	public final static String LEVEL_GOLD="gold"; 
	/**
	 * 白金会员
	 */
	public final static String LEVLE_PT="pt"; 
	/**
	 * 钻石会员
	 */
	public final static String LEVEL_DIAMOND="diamond"; 
	/**
	 * 签到操作
	 */
	public final static String TRADE_SIGN="sign";
	
	/**
	 * 抽奖
	 */
	public final static String TRADE_CHOUJIAGN="choujiang";
	/**
	 * 查询优惠券是否可用
	 */
	public final static String TRADE_QUERYDISCOUNTCODEVALID="querydiscountcodevalid";
	/**
	 * 核销优惠券
	 */
	public final static String TRADE_DODISCOUNTCODE="dodiscountcode";
	/**
	 * 购物天数
	 */
	public final static String TRADE_DEAL="consumption";
	/**
	 * 完善资料
	 */
	public final static String TRADE_PERFECTINFO="perfectInfo";
	/**
	 * 邀请好友
	 */
	public final static String TRADE_INVITEFRIENDS="inviteFriends";
	/**
	 * 被邀请好友
	 */
	public final static String TRADE_BEINVITEFRIENDS="beInviteFriends";
	/**
	 * 下单消费
	 */
	public final static String TRADE_CONSUMPTION="consumption";
	/**
	 * 手机绑定
	 */
	public final static String TRADE_PHONEBINDING="phoneBinding";
	/**
	 * 用户注册
	 */
	public final static String TRADE_REGISTER="register";
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
	/**
	 * 用户激活
	 */
	public final static String TRADE_ACTIVATE = "active";
	/**
	 * 积分卡绑定
	 */
	public final static String TRADE_JFCARDBINDING = "jfCardBinding";
	/**
	 * 转入
	 */
	public final static String TRADE_TRANSFERIN = "transferIn";
	/**
	 * 转出
	 */
	public final static String TRADE_TRANSFEROUT = "transferOut";
	/**
	 * 撤销
	 */
	public final static String TRADE_CANCEL = "cancel";
	
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
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	@Override
	public String toString() {
		
		String s="";
		if(this.TRADE_DEAL.equals(this.tradeName))
			s+="本月消费天数超过三天";
		if(this.TRADE_MAILBINDING.equals(this.tradeName))
			s+="邮箱绑定";
		if(this.TRADE_PERFECTINFO.equals(this.tradeName))
			s+="完善资料";
		if(this.TRADE_PHONEBINDING.equals(this.tradeName))
			s+="手机绑定";
		if(this.TRADE_SIGN.equals(this.tradeName))
			s+="签到一次";
		if(this.TRADE_CONSUMPTION.equals(this.tradeName))
			s+="消费 "+this.money;
		if(this.TRADE_INVITEFRIENDS.equals(this.tradeName))
			s+="邀请了一位好友";
		if(this.TRADE_REGISTER.equals(this.tradeName))
			s+="用户完成了注册";
		if (this.TRADE_ACTIVATE.equals(this.tradeName))
			s+="用户完成了激活";
		if (this.TRADE_JFCARDBINDING.equals(this.tradeName))
			s+="积分卡绑定";
		if (this.TRADE_TRANSFERIN.equals(this.tradeName))
			s+="转入";
		if (this.TRADE_TRANSFEROUT.equals(this.tradeName))
			s+="转出";
		if (this.TRADE_CANCEL.equals(this.tradeName))
			s+="撤销";
		
		return s;
	}
	
	/**
	 * 
	 * @方法功能说明：具体显示积分流水信息
	 * @修改者名字：pengel
	 * @修改时间：2014年11月5日下午1:55:22
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String showSelf(){
		
		String s="";
		if(!"".equals(this.date) && null!=this.date)
		    s+="时间："+this.date+";";
		if(this.TRADE_DEAL.equals(this.tradeName))
			s+="本月消费天数超过三天  ;";
		if(this.TRADE_MAILBINDING.equals(this.tradeName))
			s+="邮箱绑定;";
		if(this.TRADE_PERFECTINFO.equals(this.tradeName))
			s+="完善资料;";
		if(this.TRADE_PHONEBINDING.equals(this.tradeName))
			s+="手机绑定;";
		if(this.TRADE_SIGN.equals(this.tradeName))
			s+="签到一次;";
		if(this.TRADE_REGISTER.equals(this.tradeName))
			s+="用户完成注册;";
		if(this.TRADE_ACTIVATE.equals(this.tradeName))
			s+="用户完成了激活;";
		if(this.TRADE_JFCARDBINDING.equals(this.tradeName))
			s+="积分卡绑定;";
		if (this.TRADE_TRANSFERIN.equals(this.tradeName))
			s+="转入;";
		if (this.TRADE_TRANSFEROUT.equals(this.tradeName))
			s+="转出;";
		if (this.TRADE_CANCEL.equals(this.tradeName))
			s+="撤销;";
		if(this.TRADE_CONSUMPTION.equals(this.tradeName))
			s+="消费  "+this.money+";";
		if(!"".equals(this.level) && null!=this.level)
			s+="会员等级："+this.level+";";
		if(!"".equals(this.birthday) && null!=this.birthday)
			s+="会员生日："+this.birthday+";";
		return s;
	}
	
	public boolean isSavejf0() {
		return savejf0;
	}
	public boolean savejf0=false;

	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	
	
}