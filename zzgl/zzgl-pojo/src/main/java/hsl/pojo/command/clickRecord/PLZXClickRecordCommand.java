package hsl.pojo.command.clickRecord;
/**
 * @类功能说明：浏览记录创建Comd
 * @类修改者：
 * @修改日期：2015年7月7日上午9:57:00
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月7日上午9:57:00
 */
public class PLZXClickRecordCommand {
	/**
	 * 实体id
	 */
	private String objectId;
	/**
	 * 实体类型
	 */
	private String objectType;
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
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}
