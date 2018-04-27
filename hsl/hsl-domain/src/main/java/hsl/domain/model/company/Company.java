package hsl.domain.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import hsl.domain.model.user.User;

/**
 * 
 * @类功能说明：企业帐号资料
 * @类修改者：chenxy
 * @修改日期：2014年9月16日下午3:45:34
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年9月16日下午3:45:34
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_COMPANY+"COMPANY")
public class Company extends BaseModel {
	/*
	 * 企业名称
	 */
	@Column(name="COMPANYNAME" ,length=20)
	private String companyName;
	/**
	 * 关联用户的ID
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
