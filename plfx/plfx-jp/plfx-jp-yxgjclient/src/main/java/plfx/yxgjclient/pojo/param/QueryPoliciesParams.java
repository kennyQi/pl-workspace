package plfx.yxgjclient.pojo.param;
/**
 * 获取政策参数列表
 * @author guotx
 * 2015-07-10
 */
public class QueryPoliciesParams extends BaseParam{
	
	/**
	 * 乘客类型
	 * 1：一般成人 2：学生 3：青年 4：移民
	 * 5：劳务 6：海员 7：特殊身份 8：一般儿童
	 * 9：移民儿童. 多种乘客类型用^分隔，例如： 1^2^3^4
	 */
	private String passengerType;
	/**
	 * 航程类型
	 * 航程类型（1-不限， 2-单程， 3-往返）
	 */
	private String segType;
	/**
	 * 使用人数1-9
	 */
	private String peopleNum;
	/**
	 * 是否OPEN
	 * （0-否） ,目前只支持否
	 */
	private String isPermitOpen;
	/**
	 * 政策类型
	 * 0 有奖励政策和文件政策 1 有奖励， 2 文件
	 */
	private String policyType;
	/**
	 * 排序类型
	 * 影响政策的最终排序， ADT-成人， CNN-儿童， INF-婴儿
	 */
	private String sortType;
	/**
	 * 是否最优政策
	 * 1 最优政策， 0 全部政策
	 */
	private String isBestPolicy;
	/**
	 * 加密字符串
	 */
	private String encryptString;
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getSegType() {
		return segType;
	}
	public void setSegType(String segType) {
		this.segType = segType;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getIsPermitOpen() {
		return isPermitOpen;
	}
	public void setIsPermitOpen(String isPermitOpen) {
		this.isPermitOpen = isPermitOpen;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getIsBestPolicy() {
		return isBestPolicy;
	}
	public void setIsBestPolicy(String isBestPolicy) {
		this.isBestPolicy = isBestPolicy;
	}
	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
}
