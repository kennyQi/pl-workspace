package hsl.pojo.command;


/**
 * 创建公司（组织）
 * @author zhuxy
 *
 */
public class CreateCompanyCommand {
	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 关联用户的ID
	 */
	private String userId;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
