package hg.common.util;
/**
 * 加密传输协议
 * @author zqq
 *
 */
public class EncodedCommunicateData{

	/**
	 * 分配给汇积分应用的标识。
	 *	应以区分不同的应用
	 */
	public String appId;
	/**
	 * 业务数据
	 */
	public String data;
	/**
	 * 时间的毫秒
	 */
	public String time;
	/**
	 * 加密后的aesKey
	 */
	public String encryptKey;
	/**
	 * 签名
	 */
	public String sign;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "EncodedCommunicateData [appId=" + appId + ", data=" + data
				+ ", time=" + time + ", k=" + encryptKey + ", sign=" + sign + "]\n";
	}
	
	
}
