/**
 * @TransferToHjfData.java Create on 2015年1月25日上午10:57:03
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfaccount;

import java.io.Serializable;

/**
 * @修改日期：2015年1月25日上午10 :57:03
 * @公司名称：浙江汇购科技有限公司 <a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月25日上午10 :57:03
 */
public class TransferToHjfData implements Serializable{
    /**
     * @FieldsserialVersionUID:TODO
     */
    private static final long serialVersionUID = -4998280266430002986L;
    /**
     * 换入、换出手续费（百分数)
     * */
    public int feeIn;
     
    /**
     * 转入时免手机验证
     */
    public boolean noCheckWhenIn;
    /**
     * 变为可用状态的天数
     */
    public int validDay;
    /**
       * 转出时候发送验证码url
       */
   // public String sendValidCodeUrl;
    /**
     * 转出时候，验证验证码url
     */
//    public String checkValidCodeUrl;
    /**
     * 支付的url
     */
    public String payUrl;
    
	/**
	 * 操作页面的url
	 */
	public String pageUrl;
	/**查询积分的url**/
	public String queryJFUrl;

	
    /**
     * data=? 中的？部分
     */
//    public String payUrlData;

    public String getQueryJFUrl() {
		return queryJFUrl;
	}

	public void setQueryJFUrl(String queryJFUrl) {
		this.queryJFUrl = queryJFUrl;
	}

	/**
     * @类名：TransferToHjfData.java Created on 2015年1月25日上午10:57:03
     * 
     * @Copyright (c) 2012 by www.hg365.com。
     */
    public TransferToHjfData() {
    }

    /**
     * @return the noCheckWhenIn
     */
    public boolean isNoCheckWhenIn() {
        return noCheckWhenIn;
    }

    /**
     * @param noCheckWhenIn the noCheckWhenIn to set
     */
    public void setNoCheckWhenIn(boolean noCheckWhenIn) {
        this.noCheckWhenIn = noCheckWhenIn;
    }

    /**
     * @return the validDay
     */
    public int getValidDay() {
        return validDay;
    }

    /**
     * @param validDay the validDay to set
     */
    public void setValidDay(int validDay) {
        this.validDay = validDay;
    }

    /**
     * @return the feeIn
     */
    public int getFeeIn() {
        return feeIn;
    }

    /**
     * @param feeIn the feeIn to set
     */
    public void setFeeIn(Integer feeIn) {
	if(feeIn==null)
	    this.feeIn=0;
	else
	    this.feeIn = feeIn;
    }
    public void setFeeInNull(Integer feeIn) {
 	if(feeIn==null)
 	    this.feeIn=0;
 	else
 	    this.feeIn = feeIn;
     }

    public Integer getFeeInNull(){
	return this.feeIn;
    }
    /**
     * @return the payUrl
     */
    public String getPayUrl() {
        return payUrl;
    }

    /**
     * @param payUrl the payUrl to set
     */
    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    /**
     * @return the pageUrl
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * @param pageUrl the pageUrl to set
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

 
 

 
  
}