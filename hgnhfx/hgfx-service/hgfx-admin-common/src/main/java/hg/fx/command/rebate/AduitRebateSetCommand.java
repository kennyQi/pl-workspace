package hg.fx.command.rebate;

import hg.demo.member.common.domain.model.AuthUser;
import hg.framework.common.base.BaseSPICommand;

import java.util.Date;
/**
 * 审核返利配置
 * @author admin
 * @date 2016-7-25上午10:39:50
 * @since
 */
public class AduitRebateSetCommand extends BaseSPICommand{

	/**  */
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	
	private String productId;
	
	private String distributorId;
	
	/**
	 * 审核状态
	 * 0-待审核     1-已通过 2-已拒绝
	 * */
	private Integer checkStatus;
	
	
	/**
	 *  审核时间
	 * */
	private Date checkDate;
	
	/**
	 *  生效时间
	 * */
	private Date implementDate;
	
	/**
	 *  失效时间
	 * */
	private Date invalidDate;
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	private Boolean isImplement;

	
	/**
	 * 审核人
	 * */
	private AuthUser checkUser;

	private String checkUserName;

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getImplementDate() {
		return implementDate;
	}

	public void setImplementDate(Date implementDate) {
		this.implementDate = implementDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public Boolean getIsImplement() {
		return isImplement;
	}

	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}

	public AuthUser getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(AuthUser checkUser) {
		this.checkUser = checkUser;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
