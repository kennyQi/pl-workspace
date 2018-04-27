/**
 * @文件名称：JfAccountType.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午3:09:26
 */
package hgtech.jfaccount;

import hgtech.jf.entity.Entity;
import hgtech.jf.tree.WithChildren;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 积分账户类型<br>
 * domain, consumeType组合为唯一性约束
 * @类修改者：
 * @修改日期：2014-9-5下午3:09:26
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午3:09:26
 *
 */
public class JfAccountType implements WithChildren<JfAccountType> ,Entity<JfAccountTypeUK>{
	/**
	 */
	private static final long serialVersionUID = 2969320116663732382L;


	String name;
	

	public JfAccountTypeUK uk = new JfAccountTypeUK();
	/**
	 * 与汇购积分比率.多少=100汇积分
	 */
	public int jfRate;
	
	/**
	 * 是否可换入、换出
	 */
	public boolean canIn,canOut;
	
	/**
	 * 是否是活动的
	 */
    public boolean forActive;
	/**
	 * 结算方式
	 */
	public int moneyMode=MONEYMODE_AFTER;
	public static int MONEYMODE_PRE=1, MONEYMODE_NOW=2, MONEYMODE_AFTER=3;
	/**
	 * 互转规则介绍
	 */
	public String desc;
	/**
	 * 上级
	 */
	public JfAccountType upper;
	  LinkedList<WithChildren<JfAccountType>> sublist=new LinkedList<WithChildren<JfAccountType>>();
	  
	  /**
	   * 转为汇积分时候的配置
	   */
	  public TransferToHjfData toHjf = new TransferToHjfData();
	  /**
	   * 从汇积分转时候的配置
	   */
	  public TransferFromHjfData fromHjf = new TransferFromHjfData();

	  /**
	   * 接入对方，时给分配的系统id
	   */
	  public String clientAppId;
	  /**
	   * 接入对方，时给分配的用以安全的key（请保密）
	   
	  public String clientAppKey;*/
	  
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ( uk.domain)	+"-"+(uk.consumeType);
	}

	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getSubList()
	 */
	@Override
	public LinkedList<WithChildren<JfAccountType>> getSubList() {
		return sublist;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getMe()
	 */
	@Override
	public JfAccountType getMe() {
		return this;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getCode(){
		return readUK().getkey();
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see hgtech.entity.Entity#getUK()
	 */
	@Override
	public JfAccountTypeUK readUK() {
		return this.uk;
	}
	
	public JfAccountTypeUK getUk(){
	    return uk;
	}
	
	/* (non-Javadoc)
	 * @see hgtech.entity.Entity#setUK(java.lang.Object)
	 */
	@Override
	public void putUK(JfAccountTypeUK uk) {
		this.uk=uk;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#syncTransentPersistence()
	 */
	@Override
	public void syncUK() {
		// 
		
	}
	
	public String getDomain(){
		return uk.domain.name;
	}
	public String getJfName(){
		return uk.consumeType.name;
	}

	/**
	 * @return the jfRate
	 */
	public int getJfRate() {
		return jfRate;
	}

	/**
	 * @param jfRate the jfRate to set
	 */
	public void setJfRate(int jfRate) {
		this.jfRate = jfRate;
	}

	/**
	 * @return the canIn
	 */
	public boolean isCanIn() {
		return canIn;
	}

	/**
	 * @param canIn the canIn to set
	 */
	public void setCanIn(boolean canIn) {
		this.canIn = canIn;
	}

	/**
	 * @return the canOut
	 */
	public boolean isCanOut() {
		return canOut;
	}

	/**
	 * @param canOut the canOut to set
	 */
	public void setCanOut(boolean canOut) {
		this.canOut = canOut;
	}

	/**
	 * @return the feeIn
	 */
	public int getFeeIn() {
		return toHjf.feeIn;
	}

	public int getFeeOut(){
	    return fromHjf.feeOut;
	}
	/**
	 * @param feeIn the feeIn to set
	 */
	public void setFeeIn(int feeIn) {
		this.toHjf.feeIn = feeIn;
	}

	 

	/**
	 * @return the noCheckWhenIn
	 */
	public boolean isNoCheckWhenIn() {
		return toHjf.noCheckWhenIn;
	}

	/**
	 * @param noCheckWhenIn the noCheckWhenIn to set
	 */
	public void setNoCheckWhenIn(boolean noCheckWhenIn) {
		this.toHjf.noCheckWhenIn = noCheckWhenIn;
	}
	

	public boolean isForActive() {
		return forActive;
	}

	public void setForActive(boolean forActive) {
		this.forActive = forActive;
	}

	/**
	 * @return the moneyMode
	 */
	public int getMoneyMode() {
		return moneyMode;
	}

	/**
	 * @param moneyMode the moneyMode to set
	 */
	public void setMoneyMode(int moneyMode) {
		this.moneyMode = moneyMode;
	}

	/**
	 * @return the validDay
	 */
	public int getValidDay() {
		return toHjf.validDay;
	}

	/**
	 * @param validDay the validDay to set
	 */
	public void setValidDay(int validDay) {
		this.toHjf.validDay = validDay;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean getCanIn(){
	    return canIn;
	}
	public boolean getCanOut(){
	    return canOut;
	}

	/**
	 * @return the toHjf
	 */
	public TransferToHjfData getToHjf() {
	    return toHjf;
	}

	/**
	 * @param toHjf the toHjf to set
	 */
	public void setToHjf(TransferToHjfData toHjf) {
	    this.toHjf = toHjf;
	}

	/**
	 * @return the fromHjf
	 */
	public TransferFromHjfData getFromHjf() {
	    return fromHjf;
	}

	/**
	 * @param fromHjf the fromHjf to set
	 */
	public void setFromHjf(TransferFromHjfData fromHjf) {
	    this.fromHjf = fromHjf;
	}

	/**
	 * @return the clientAppId
	 */
	public String getClientAppId() {
	    return clientAppId;
	}

	/**
	 * @param clientAppId the clientAppId to set
	 */
	public void setClientAppId(String clientAppId) {
	    this.clientAppId = clientAppId;
	}





	
	
}
