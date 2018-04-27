package plfx.yxgjclient.pojo.param;
/**
 * 查询政策信息
 * @author guotx
 * 2015-07-10
 */
public class QueryPolicyStateParams extends BaseParam{
	/**
	 * 政策ID
	 */
	private String policyId;
	/**
	 * 政策类型
	 * 1奖励 2文件
	 */
	private String policyType;
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	
}
