package hsl.pojo.qo.account;
import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class GrantCodeRecordQO extends BaseQo{
	
	/**
	 * 公司ID
	 */
	private String companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 公司联系人
	 */
	private String companyLinkName;
	/**
	 * 公司联系电话
	 */
	private String companyLinkTel;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	
	/**
	 * 发码时间，起始
	 */
	private String beginTime;
	
	/**
	 * 发码时间，截至
	 */
	private String endTime;

	/**
	 * 发码日期
	 */
	private String createTime;
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLinkName() {
		return companyLinkName;
	}

	public void setCompanyLinkName(String companyLinkName) {
		this.companyLinkName = companyLinkName;
	}

	public String getCompanyLinkTel() {
		return companyLinkTel;
	}

	public void setCompanyLinkTel(String companyLinkTel) {
		this.companyLinkTel = companyLinkTel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	

}
