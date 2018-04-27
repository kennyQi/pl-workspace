package hsl.pojo.command;

/**
 * 用户浏览记录创建Comd
 * @author zhuxy
 *
 */





public class UserClickRecordCommand {


	/**
	 * 景点id
	 */
	private String scenicSpotId;

	
	/**
	 * 点击用户id（匿名点击无id）
	 */
	private String userId;
	
	/**
	 * 点击链接
	 */
	private String url;
	
	/**
	 * 点击来源
	 */
	private String fromIP;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFromIP() {
		return fromIP;
	}

	public void setFromIP(String fromIP) {
		this.fromIP = fromIP;
	}

}
