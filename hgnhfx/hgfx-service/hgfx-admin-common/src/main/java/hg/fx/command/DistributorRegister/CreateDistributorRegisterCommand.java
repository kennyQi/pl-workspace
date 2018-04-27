package hg.fx.command.DistributorRegister;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseSPICommand;

import java.util.Date;

import javax.persistence.Column;

/**
 * 经销商注册command
 * @author yangkang
 * @date 2016-07-14
 * */
@SuppressWarnings("serial")
public class CreateDistributorRegisterCommand extends BaseSPICommand {

	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 密码
	 */
	private String passwd;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 联系人
	 */
	private String linkMan;
	
	/**
	 * 联系电话(手机号)
	 */
	private String phone;
	
	/**
	 * 公司网址
	 */
	private String webSite;
	
	/**
	 * 注册申请日期
	 */
	private Date createDate;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
