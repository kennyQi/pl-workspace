/**
 * @CalLogDto.java Create on 2015年1月29日下午2:01:11
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

import javax.persistence.Column;

import hg.common.model.qo.DwzPaginQo;

public class CalLogDto extends DwzPaginQo {
	private  String  in_userCode;
	private String calTime;
	private String nowTime;
	private String out_jf;
	private String out_resultCode;
	
	public String getOut_jf() {
		return out_jf;
	}

	public void setOut_jf(String out_jf) {
		this.out_jf = out_jf;
	}

	public String getOut_resultCode() {
		return out_resultCode;
	}

	public void setOut_resultCode(String out_resultCode) {
		this.out_resultCode = out_resultCode;
	}
	/**
	 * 规则
	 */
	private String in_rulecode;
	
	//线上0积分的
	private boolean show0;
	
	public boolean isShow0() {
		return show0;
	}
	//是否线上out_resultCode='N'的行
	public boolean showFail;

	public boolean isShowFail() {
		return showFail;
	}

	public void setShowFail(boolean showFail) {
		this.showFail = showFail;
	}

	public void setShow0(boolean show0) {
		this.show0 = show0;
	}

	public String getIn_rulecode() {
		return in_rulecode;
	}

	public void setIn_rulecode(String in_rulecode) {
		this.in_rulecode = in_rulecode;
	}

	public String getCalTime() {
		return calTime;
	}

	public void setCalTime(String calTime) {
		this.calTime = calTime;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	public String getIn_userCode() {
		return in_userCode;
	}

	public void setIn_userCode(String in_userCode) {
		this.in_userCode = in_userCode;
	}
	
	
	
	private String activityId;
	private String userId;
	private String startTime;
	private String endTime;
	private String ruleTemplate;
	private int maxResults = Integer.MAX_VALUE;
	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public String getRuleTemplate() {
		return ruleTemplate;
	}

	public void setRuleTemplate(String ruleTemplate) {
		this.ruleTemplate = ruleTemplate;
	}



	private boolean queryAvtivityStat = false;


	public String getActivityId() {
		return activityId;
	}

	public String getUserId() {
		return userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isQueryAvtivityStat() {
		return queryAvtivityStat;
	}

	public void setQueryAvtivityStat(boolean queryAvtivityStat) {
		this.queryAvtivityStat = queryAvtivityStat;
	}

}