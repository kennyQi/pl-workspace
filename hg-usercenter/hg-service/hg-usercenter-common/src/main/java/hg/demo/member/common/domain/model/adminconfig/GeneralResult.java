package hg.demo.member.common.domain.model.adminconfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** 
 * 通用结果类
 * 
 * @author wangkq 2016年1月11日
 */
public class GeneralResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GeneralResult() {
		//设置成功
		super();
		setResultTrue();
		
	}

	private static final String RESULT_KEY = "result";
	private Boolean result = null;
	private String message = null;

	private Map<String, Object> dataMp = new HashMap<String, Object>();

	public void addDoubleData(String k, double v) {
		dataMp.put(k, v);
	}

	public void addIntegerData(String k, Integer v) {
		dataMp.put(k, v);
	}

	public void addLongData(String k, long v) {
		dataMp.put(k, v);
	}

	public Long getLongData(String k) {
		Object o = dataMp.get(k);
		if (o == null) {
			return null;
		}
		Long v;
		try {
			v = (long) o;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GeneralResult\n类型转换异常\n\n\n");
			return null;
		}
		return v;
	}

	public void addStringData(String k, String v) {
		dataMp.put(k, v);
	}

	public String getStringData(String k) {
		Object o = dataMp.get(k);
		if (o == null) {
			return null;
		}
		String v;
		try {
			v = (String) o;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GeneralResult\n类型转换异常\n\n\n");
			return null;
		}
		return v;

	}

	public Double getDoubleData(String k) {
		Object o = dataMp.get(k);
		if (o == null) {
			return null;
		}
		Double v;
		try {
			v = (double) o;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GeneralResult\n类型转换异常\n\n\n");
			return null;
		}
		return v;

	}

	public Integer getIntegerData(String k) {
		Object o = dataMp.get(k);
		if (o == null) {
			return null;
		}
		Integer v;
		try {
			v = (int) o;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GeneralResult\n类型转换异常\n\n\n");
			return null;
		}
		return v;

	}

	public Map<String, Object> getDataMp() {
		return dataMp;
	}

	public void addData(String k, Object v) {
		dataMp.put(k, v);
	}

	public boolean isResult() {
		return result;
	}

	public boolean getResult() {
		return result;
	}

	public void setResultTrue() {
		dataMp.put(RESULT_KEY, true);
		result = true;
	}

	public void setResultFalse() {
		dataMp.put(RESULT_KEY, false);
		result = false;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if (message != null) {
			dataMp.put("message", message);
			this.message = message;
		}
	}

	public static Map<String, Object> errorResultMp(String message) {
		GeneralResult gr = new GeneralResult();
		gr.setResultFalse();
		gr.setMessage(message);
		return gr.getDataMp();
	}

	public static Map<String, Object> successResultMp(String message) {
		GeneralResult gr = new GeneralResult();
		gr.setResultTrue();
		gr.setMessage(message);
		return gr.getDataMp();
	}
	public static GeneralResult errorResult(String message) {
		GeneralResult gr = new GeneralResult();
		gr.setResultFalse();
		gr.setMessage(message);
		return gr;
	}
	
	public static GeneralResult successResult(String message) {
		GeneralResult gr = new GeneralResult();
		gr.setResultTrue();
		gr.setMessage(message);
		return gr;
	}

	public static GeneralResult errWithData(String message, Object data) {
		GeneralResult gr = new GeneralResult();
		gr.setResultFalse();
		gr.setMessage(message);
		if (data != null) {
			gr.addData("data", data);
		}
		return gr;
	}

	public static GeneralResult succWithData(String message, Object data) {
		GeneralResult gr = new GeneralResult();
		gr.setResultTrue();
		gr.setMessage(message);
		if (data != null) {
			gr.addData("data", data);
		}
		return gr;
	}
	
	
	static public String dwzSuccessMsgClose(String message, String navTabRel) {
		if(message == null) {
			message = "";
		}
		String ExtraData = "";
		return "{\"callbackType\":\"closeCurrent\",\"message\":\""+message+"\",\"navTabId\":\""+navTabRel+"\",\"statusCode\":\"200\",\"extraData\":\""+ExtraData +"\"}";
		
	}
	static public String dwzSuccessMsg(String message, String navTabRel) {
		if(message == null) {
			message = "";
		}
		String ExtraData = "";
		return "{\"callbackType\":\"forward\",\"message\":\""+message+"\",\"navTabId\":\""+navTabRel+"\",\"statusCode\":\"200\",\"extraData\":\""+ExtraData+"\"}";
		
	}
	

	static public String dwzErrorMsg(String message) {
		return "{\"message\":\"" + message + "\",\"statusCode\":\"300\"}";
	}
	static public String dwzExceptionMsg(Exception e) {
		return dwzErrorMsg(e.getMessage());
	}
	
	


}
