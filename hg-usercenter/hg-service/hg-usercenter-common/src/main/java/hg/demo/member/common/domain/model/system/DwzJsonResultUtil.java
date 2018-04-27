/**
 * @DwzJsonResultUtil.java Create on 2016-5-24上午10:31:45
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.domain.model.system;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-24上午10:31:45
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-24上午10:31:45
 * @version：
 */
public class DwzJsonResultUtil {

	public final static String STATUS_CODE_200 = "200";
	public final static String STATUS_CODE_300 = "300";
	public final static String STATUS_CODE_500 = "300";

	public final static String CALL_BACK_TYPE_CLOSE_CURRENT = "closeCurrent";
	public final static String FLUSH_FORWARD = "forward";

	public static String createSimpleJsonString(String statusCode, String message){
		return createJsonString(statusCode, message, null, null, null, null, null);
	}

	public static String createJsonString(String statusCode, String message, String callbackType, String navTabId){
		return createJsonString(statusCode, message, callbackType, navTabId, null, null, null);
	}

	public static String createJsonString(String statusCode, String message, String callbackType, String navTabId, 
			String confirmMsg, String forwardUrl, String rel){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("statusCode", statusCode);
		map.put("message", message);
		if(StringUtils.isNotBlank(callbackType)){
			map.put("callbackType", callbackType);
		}
		if(StringUtils.isNotBlank(navTabId)){
			map.put("navTabId", navTabId);
		}
		if(StringUtils.isNotBlank(confirmMsg)){
			map.put("confirmMsg", confirmMsg);
		}
		if(StringUtils.isNotBlank(forwardUrl)){
			map.put("forwardUrl", forwardUrl);
		}
		if(StringUtils.isNotBlank(rel)){
			map.put("rel", rel);
		}
		return JsonUtil.parseObject(map, false);
	}
}
