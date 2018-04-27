package hg.fx.command.distributor;

import hg.framework.common.base.BaseSPICommand;

/**
 * 更新商户信息命令
 * @author zqq
 * @date 2016-6-16上午10:39:52
 * @since
 */
@SuppressWarnings("serial")
public class ModifyDistributorCommand extends BaseSPICommand{
	/**
	 * 商户id
	 */
	private String id;
	/**
	 * 公司名称
	 */
	private String name;
	/**
	 * 联系人
	 */
	private String linkMan;
	/**
	 * 联系人手机号
	 */
	private String phone;
	/**
	 * 审核状态
	 * -1--已拒绝  
	 *  0--待审核  
	 *  1--已通过
	 */
	private Integer checkStatus;
	/**
	 * 使用状态 
	 * 0--禁用  
	 * 1--启用
	 */
	private Integer status;
	
	//使用商品数量
	private Integer prodNum;
	
	public String getName() {
		return name;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getProdNum() {
		return prodNum;
	}

	public void setProdNum(Integer prodNum) {
		this.prodNum = prodNum;
	}
	
	
}
