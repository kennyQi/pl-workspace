package hsl.api.v1.request.qo.user;

import hsl.api.base.ApiPayload;

/**
 * 
 * @类功能说明：查询用户信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月4日下午2:12:06
 * 
 */
public class UserQO extends ApiPayload {

	/**
	 * 按商城用户id查询
	 */
	private String userId;

	/**
	 * 按商城用户名查询
	 */
	private String loginName;

	/**
	 * 按绑定帐号id查询
	 */
	private String bindAccountId;

	/**
	 * 按绑定帐号名查询
	 */
	private String bindAccountName;

	/**
	 * 绑定帐号类型 1、微信
	 */
	private Integer type;
	
	/**
	 * 用户手机号
	 */
	private String mobile;
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getBindAccountId() {
		return bindAccountId;
	}

	public void setBindAccountId(String bindAccountId) {
		this.bindAccountId = bindAccountId;
	}

	public String getBindAccountName() {
		return bindAccountName;
	}

	public void setBindAccountName(String bindAccountName) {
		this.bindAccountName = bindAccountName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
