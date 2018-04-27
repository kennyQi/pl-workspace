/**
 * @SessionDto.java Create on 2015年1月29日下午2:06:34
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

import hg.common.model.qo.DwzPaginQo;

public class SessionDto extends DwzPaginQo {
	public SessionDto(){
		
	}
	public   String  user,rule,propName,templateCode;
	public Object propValue;
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
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}
	/**
	 * @param rule the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}
	/**
	 * @return the propName
	 */
	public String getPropName() {
		return propName;
	}
	/**
	 * @param propName the propName to set
	 */
	public void setPropName(String propName) {
		this.propName = propName;
	}
	/**
	 * @return the propValue
	 */
	public Object getPropValue() {
		return propValue;
	}
	/**
	 * @param propValue the propValue to set
	 */
	public void setPropValue(Object propValue) {
		this.propValue = propValue;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
}