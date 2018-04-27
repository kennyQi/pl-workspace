package hsl.domain.model.user.account;
import java.util.Date;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.account.BusinessPartnersCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @类功能说明：商业合作伙伴
 * @类修改者：
 * @修改日期：2015年8月29日上午9:54:06
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年8月29日上午9:54:06
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_ACCOUNT+"BUSINESS_PARTNERS")
public class BusinessPartners extends BaseModel{
	private static final long serialVersionUID = 1L;
	/**
	 * 公司姓名
	 */
	@Column(name="COMPANYNAME",length=64)
	private String companyName;
	/**
	 * 公司联系人姓名
	 */
	@Column(name="COMPANYLINKNAME",length=64)
	private String companyLinkName;
	/**
	 * 公司联系电话
	 */
	@Column(name="COMPANYLINKTEL",length=64)
	private String companyLinkTel;
	/**
	 * 创建时间
	 */
	@Column(name="CREATETIME",columnDefinition=M.DATE_COLUM)
	private Date createTime;
	/**
	 * @方法功能说明：创建公司
	 * @修改者名字：chenxy
	 * @修改时间：2015年8月29日上午10:07:00
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void createCompany(BusinessPartnersCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setCompanyName(command.getCompanyName());
		this.setCompanyLinkName(command.getCompanyLinkName());
		this.setCompanyLinkTel(command.getCompanyLinkTel());
		this.setCreateTime(new Date());
	}
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
