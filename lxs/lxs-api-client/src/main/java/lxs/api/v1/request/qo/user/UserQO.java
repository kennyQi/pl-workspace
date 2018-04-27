package lxs.api.v1.request.qo.user;

import lxs.api.base.ApiPayload;

import com.alibaba.fastjson.JSON;

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
@SuppressWarnings("serial")
public class UserQO extends ApiPayload {

	/**
	 * 按用户id查询
	 */
	private String userId;

	/**
	 * 按登录名查询
	 */
	private String loginName;
	
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static void main(String[] arg){
		UserQO u = new UserQO();
		u.setLoginName("admin");
		u.setMobile("18888888888");
		u.setUserId("123654");
		System.out.println(JSON.toJSON(u));
	}
}
