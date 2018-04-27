package hsl.admin.common;

import java.util.HashMap;

public class CouponActivityParam {
	/**
	 * 卡券类型
	 */
	private static HashMap<String,String> couponTypeMap; 
	static{
		couponTypeMap = new HashMap<String, String>();
		couponTypeMap.put("0","无" );
		couponTypeMap.put("1","代金券" );
		couponTypeMap.put("2","现金券" );
	}
	
	/**
	 * 发放渠道
	 */
	private static HashMap<String,String> issueWayMap;
	static{
		issueWayMap = new HashMap<String, String>();
		issueWayMap.put("1","注册");
		issueWayMap.put("2","订单满");
		issueWayMap.put("3","指定人员发放");
		issueWayMap.put("4","转赠");
		
	}
	
	/**
	 * 当前活动状态 
	 */
	private static HashMap<String,String> currentValueMap;
	static{
		currentValueMap = new HashMap<String, String>();
		currentValueMap.put("1","未审核 ");
		currentValueMap.put("2","审核未通过");
		currentValueMap.put("3","审核成功");
		currentValueMap.put("4","发放中");
		currentValueMap.put("5","名额已满");
		currentValueMap.put("6","活动结束");
		currentValueMap.put("7","活动结束");
	}
	
	/**
	 * 使用条件
	 */
	private static HashMap<String,String> conditionMap;
	static{
		conditionMap = new HashMap<String, String>();
		conditionMap.put("0","不限");
		conditionMap.put("1","订单满");
	}
	public static HashMap<String, String> getCouponTypeMap() {
		return couponTypeMap;
	}
	public static HashMap<String, String> getIssueWayMap() {
		return issueWayMap;
	}
	public static HashMap<String, String> getCurrentValueMap() {
		return currentValueMap;
	}
	public static HashMap<String, String> getConditionMap() {
		return conditionMap;
	}
	
	
}
