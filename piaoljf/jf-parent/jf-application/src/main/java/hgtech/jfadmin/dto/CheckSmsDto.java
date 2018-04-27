/**
 * @CheckSmsDto.java Create on 2015年1月29日下午2:02:52
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

import java.io.Serializable;

/**
 * 
 * @类功能说明：短信校验dto
 * @类修改者：
 * @修改日期：2015年2月7日下午1:00:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年2月7日下午1:00:00
 * @version：
 */
public class CheckSmsDto implements Serializable {
    public static String CMD_BIND="bind", CMD_PAY="pay";
    public String user,orderId, phone, code, command=CMD_PAY ;
    public long createTime;
    public String hjfUser;
    /**
     * 渠道代码
     */
    public String domainCode;
    
    public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	/**
     * @类名：CheckSmsDto.java Created on 2015年3月16日下午5:16:18
     * 
     * @Copyright (c) 2012 by www.hg365.com。
     */
    public CheckSmsDto(String orderId, String user, String phone  ) {
	super();
	this.orderId = orderId;
	this.user = user;
	this.phone = phone;
    }
    public CheckSmsDto(String orderId, String user, String phone, String code  ) {
	super();
	this.orderId = orderId;
	this.user = user;
	this.phone = phone;
	this.code = code;
    }
    public CheckSmsDto(){};

  
    /**
     * @return the user
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setOrderId(String user) {
        this.orderId = user;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }
    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }
    /**
     * @return the hjfUser
     */
    public String getHjfUser() {
        return hjfUser;
    }
    /**
     * @param hjfUser the hjfUser to set
     */
    public void setHjfUser(String hjfUser) {
        this.hjfUser = hjfUser;
    }
}