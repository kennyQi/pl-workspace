package hsl.pojo.command;

/**
 * 删除一个用户的COMD
 * @author zhuxy
 *
 */
public class DeleteMemberCommand {
	/**
	 * 用户Id
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
