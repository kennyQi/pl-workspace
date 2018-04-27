package hsl.pojo.qo.account;
import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class PayCodeQO extends BaseQo{
	/**
	 * 充值码
	 */
	private String code;
	/**
	 * 账户名
	 */
	private String  userName;
	/**
	 * 合作伙伴ID
	 */
	private String  userSnapshotId;

	/**
	 * 查询记录  充值记录 1 发放记录 2
	 */
	 public int type;
	/**
	 * 查询记录  充值记录 1
	 */
	public final static Integer PAYCODE_CZ=1;
	/**
	 * 查询记录 发放记录 2
	 */
	public final static Integer PAYCODE_FF=2;
	/**
	 * 充值日期
	 */
	private String rechargeDate;
	
	/**
	 * 所属记录Id
	 */
	private String grantCodeRecordID;
	
	/**
	 * 发码时间，起始
	 */
	private String beginTime;
	
	/**
	 * 发码时间，截至
	 */
	private String endTime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSnapshotId() {
		return userSnapshotId;
	}
	public void setUserSnapshotId(String userSnapshotId) {
		this.userSnapshotId = userSnapshotId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public String getGrantCodeRecordID() {
		return grantCodeRecordID;
	}
	public void setGrantCodeRecordID(String grantCodeRecordID) {
		this.grantCodeRecordID = grantCodeRecordID;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



}
