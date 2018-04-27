package hg.jxc.admin.common;

import hg.common.util.DwzJsonResultUtil;
import hg.common.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;



/**
 * 
 * @author wangkq
 * updateDate 2015年3月3日
 */
public class JsonResultUtil {
	private Map<String, String> retMap;
	private StringBuffer sb;

	public JsonResultUtil() {
		this.retMap = new HashMap<String, String>();
	}

	public void setResultSuccess() {
		this.addAttr("result", "success");
	}

	public void setResultError() {
		this.addAttr("result", "error");
	}

	public void addAttr(String n, String v) {
		this.retMap.put(n, v);
	}

	public String outputJsonString() {
		return JsonUtil.parseObject(retMap, false);
	}
	
	public void addTip(String tipFor,String msg){
		this.addAttr("tip>>"+tipFor, msg);
	}
	/**
	 * 拼接
	 * 
	 * @param n
	 * @param v
	 */
	public void linkAttrString(String n, String v) {
		String val = this.retMap.get(n);
		val = val + ";" + v;
	}

	public static String successResult(String message, String data) {
		JsonResultUtil jru = new JsonResultUtil();
		jru.setResultSuccess();
		jru.addDataAndMessage(message, data);
		return jru.outputJsonString();
	}

	public static String errorResult(String message, String data) {
		JsonResultUtil jru = new JsonResultUtil();
		jru.setResultError();
		jru.addDataAndMessage(message, data);
		return jru.outputJsonString();
	}

	private void addDataAndMessage(String m, String d) {
		if (m != null) {
			retMap.put("message", m);
		}
		if (d != null) {
			retMap.put("data", d);
		}
	}

	public void linkString(String s) {
		if (sb == null) {
			sb = new StringBuffer(s);
		} else {
			sb.append(s);
		}
		sb.append(";");
	}

	public String getLinkedString() {
		if (sb == null) {
			return "";
		} else {
			return sb.toString();
		}

	}

	static public String dwzSuccessMsg(String message, String navTabRel) {
		return DwzJsonResultUtil.createJsonString("200", message, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, navTabRel);

	}
	static public String dwzSuccessMsg(String message, String navTabRel,String ExtraData) {
		return "{\"callbackType\":\"closeCurrent\",\"message\":\""+message+"\",\"navTabId\":\""+navTabRel+"\",\"statusCode\":\"200\",\"extraData\":\""+ExtraData+"\"}";
		
	}
	
	static public String dwzSuccessMsgNoClose(String message, String navTabRel) {
		return DwzJsonResultUtil.createJsonString("200", message, DwzJsonResultUtil.FLUSH_FORWARD, navTabRel);
		
	}

	static public String dwzErrorMsg(String message) {
		return "{\"message\":\"" + message + "\",\"statusCode\":\"300\"}";
	}
	static public String dwzExceptionMsg(Exception e) {
		return dwzErrorMsg(e.getMessage());
	}
	static private void addJsonDataWithName(Model model,String attrName,Object obj) {
		model.addAttribute(attrName, JSON.toJSONString(obj));
	}
	static public void AddFormData(Model model,Object obj) {
		addJsonDataWithName(model, "_jsonData", obj);
	}
	

}
