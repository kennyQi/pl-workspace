package hsl.pojo.command.account;
import hg.common.component.BaseCommand;

import java.util.Date;
/**
 * 
 * @类功能说明：保存公司信息
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-8-31上午10:59:18
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@SuppressWarnings("serial")
public class BusinessPartnersCommand extends BaseCommand{
	
	/**
	 * ID
	 */
	private String id;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
