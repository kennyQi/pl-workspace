package hsl.pojo.qo.log;
import hg.log.base.BaseLogQo;

public class PLZXClickRecordQo extends BaseLogQo{
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 实体ID
	 */
	private String objectId;
	/**
	 * 实体类型
	 */
	private Integer objectType;
	/**
	 * 返回条目
	 */
	private Integer pageSize=1;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
