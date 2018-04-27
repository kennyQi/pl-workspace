/**
 * @TransferFromHjfData.java Create on 2015年1月29日下午5:48:11
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfaccount;

import java.io.Serializable;

/**
 * @类功能说明：从汇积分转出时候的配置
 * @类修改者：
 * @修改日期：2015年1月29日下午5:48:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月29日下午5:48:11
 * @version：
 */
public class TransferFromHjfData implements Serializable{
    	/**
     * @FieldsserialVersionUID:TODO
     */
    private static final long serialVersionUID = 2698003494806682943L;


	public int feeOut;

    	
	/**
	 * 转入积分调用的url
	 */
	public String transferInUrl;
	
	/**
	 * 操作页面的url
	 */
	public String pageUrl;
	
	/**
	 * 转入积分调用的url后data=?中的？部分
	 */
//	public String transferInData;
	/**
	 * @return the feeOut
	 */
	public int getFeeOut() {
	    return feeOut;
	}
	/**
	 * @param feeOut the feeOut to set
	 */
	public void setFeeOut(Integer feeOut) {
	    if(feeOut==null)
		this.feeOut=0;
	    else
		this.feeOut = feeOut;
	}
	/**
	 * 
	 * @方法功能说明：允许null
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年4月3日下午3:14:51
	 * @version：
	 * @修改内容：
	 * @参数：@param feeOut
	 * @return:void
	 * @throws
	 */
	public void setFeeOutNull(Integer feeOut) {
	    if(feeOut==null)
		this.feeOut=0;
	    else
		this.feeOut = feeOut;
	} 
	public Integer getFeeOutNull(){
	    return this.feeOut;
	}
	
	/**
	 * @return the transferInUrl
	 */
	public String getTransferInUrl() {
	    return transferInUrl;
	}
	/**
	 * @param transferInUrl the transferInUrl to set
	 */
	public void setTransferInUrl(String transferInUrl) {
	    this.transferInUrl = transferInUrl;
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
