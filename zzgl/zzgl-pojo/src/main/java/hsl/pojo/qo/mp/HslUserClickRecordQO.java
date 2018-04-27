package hsl.pojo.qo.mp;
/**
 * 用户浏览景点历史QO
 * @author zhuxy
 *
 */

public class HslUserClickRecordQO {
	/**
	 * 用户id
	 */
	private String userId;
	
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
