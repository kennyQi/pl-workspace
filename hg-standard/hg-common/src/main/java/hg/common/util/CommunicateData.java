package hg.common.util;
/**
 * 传输对象
 * @author zqq
 *
 */
public class CommunicateData {

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
	 * aes 加密用到的key
	 */
	public String aesKey;
	
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

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	@Override
	public String toString() {
		return "CommunicateData [appId=" + appId + ", data=" + data + ", time="
				+ time + ", aesKey=" + aesKey + "]\n";
	}
	
}
