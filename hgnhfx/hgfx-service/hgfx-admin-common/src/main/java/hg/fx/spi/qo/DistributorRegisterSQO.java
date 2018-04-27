package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * 经销商注册记录QO
 * @author yangkang
 * @date 2016-07-14
 * */
@SuppressWarnings("serial")
public class DistributorRegisterSQO extends BaseSPIQO{

	private String id;
	/**
	 * 登录名
	 */
	private String loginName;
	
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	
	/**
	 * 联系电话(手机号)
	 */
	private String phone;
	
	
	/**
	 * 注册申请日期(开始)
	 */
	private String strCreateDate;
	
	/**
	 * 注册申请日期(结束)
	 */
	private String endCreateDate;
	
	/**
	 * 状态    0--待审核   1--已通过   2--已拒绝
	 */
	private Integer status;
	/**
	 * 排序方式
	 * 1:时间降序
	 * 2:先状态升序,后时间倒序
	 */
	private int orderWay=1;
	

	public int getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(int orderWay) {
		this.orderWay = orderWay;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
