package hsl.pojo.qo.account;
import java.util.Date;
import hg.common.component.BaseQo;
public class BusinessPartnersQO extends BaseQo{
	private static final long serialVersionUID = 1L;
	/**
	 * 公司姓名
	 */
	private String companyName;
	/**
	 * 公司联系人姓名
	 */
	private String companyLinkName;
	/**
	 * 公司联系电话
	 */
	private String companyLinkTel;
	/**
	 * 创建时间
	 */
	private Date createTime;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
