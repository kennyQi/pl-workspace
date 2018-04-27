/**
 * @BJfQueryDto.java Create on 2015年2月3日下午3:13:50
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

import hg.common.model.qo.DwzPaginQo;

/**
 * @类功能说明：运营端（B)积分统计查询 dto
 * @类修改者：
 * @修改日期：2015年2月3日下午3:13:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年2月3日下午3:13:50
 * @version：
 */
public class TransferStatQo extends DwzPaginQo{
	/**
	*南航卡号
	*/
	private String czCard;

	/**
	*南航姓名
	*/
	private String czName;

	
	private int numPerPage, pageNum;
    //渠道
    String domain;
//    积分名称
    String acctType;
//    其中日期
    String fromDate, toDate;
    //用户账户
    String userCode;
    String domainLogin;
    String status;
    String merchandiseStatus; 
    String sendStatus; 
    //排序字段
    String orderFiled;
    //排序方式
    String orderDirection;
    boolean transferIn=true;
    //用户单位
    String userOrgan;
    public String getUserOrgan() {
		return userOrgan;
	}
	public void setUserOrgan(String userOrgan) {
		this.userOrgan = userOrgan;
	}
	/**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }
    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    /**
     * @return the jfName
     */
    public String getAcctType() {
        return acctType;
    }
    /**
     * @param jfName the jfName to set
     */
    public void setAcctType(String jfName) {
        this.acctType = jfName;
    }
    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }
    /**
     * @param fromDate the fromDate to set yyyy-MM-dd
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }
    /**
     * @param toDate the toDate to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    /**
     * @return the userCode
     */
    public String getUserCode() {
        return userCode;
    }
    /**
     * @param userCode the userCode to set
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the transferIn
     */
    public boolean isTransferIn() {
        return transferIn;
    }
    /**
     * @param transferIn the transferIn to set
     */
    public void setTransferIn(boolean transferIn) {
        this.transferIn = transferIn;
    }
    /**
     * @return the domainLogin
     */
    public String getDomainLogin() {
        return domainLogin;
    }
    /**
     * @param domainLogin the domainLogin to set
     */
    public void setDomainLogin(String domainLogin) {
        this.domainLogin = domainLogin;
    }
	public Integer getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getMerchandiseStatus() {
		return merchandiseStatus;
	}
	public void setMerchandiseStatus(String merchandiseStatus) {
		this.merchandiseStatus = merchandiseStatus;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	
	public Integer getStatusCheck() {
		return statusCheck;
	}
	public void setStatusCheck(Integer statusCheck) {
		this.statusCheck = statusCheck;
	}


	public String getCzCard() {
		return czCard;
	}
	public void setCzCard(String czCard) {
		this.czCard = czCard;
	}


	public String getCzName() {
		return czName;
	}
	public void setCzName(String czName) {
		this.czName = czName;
	}

	
	public String getOrderFiled() {
		return orderFiled;
	}
	public void setOrderFiled(String orderFiled) {
		this.orderFiled = orderFiled;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}


	private Integer statusCheck;
}
