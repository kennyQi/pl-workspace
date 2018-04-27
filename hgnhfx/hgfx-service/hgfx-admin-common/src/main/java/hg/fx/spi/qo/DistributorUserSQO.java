package hg.fx.spi.qo;

import java.util.Date;

import hg.framework.common.base.BaseSPIQO;

/**
 * 商户查询qo
 * @author Caihuan
 * @date   2016年6月1日
 */
@SuppressWarnings("serial")
public class DistributorUserSQO extends BaseSPIQO{

	/**
	 * 商户userid
	 */
	private String id;
	
	/**
	 * 商户id
	 */
	private String distributorId;
	/**
	 * 商户编号
	 */
	private String code;
	
	/**
	 * 商户帐号
	 */
	private String account;
	
	/**
	 * 是否account 精确匹配，默认false
	 */
	private boolean eqAccount;
	
	/**
	 * 商户姓名
	 */
	private String name;
	
	/**
	 * 类型 1主账号 2子帐号
	 */
	private Integer type;
	
	/**
	 * 商户启用状态
	 * 0--禁用  
	 * 1--启用
	 */
	private Integer status;
	
	/**
	 * 审核状态
	 * -1--已拒绝  
	 *  0--待审核  
	 *  1--已通过
	 */
	private Integer checkStatus;
	
	/**
	 * 查询开始时间
	 */
	private Date beginDate;
	
	/**
	 * 查询结束时间
	 */
	private Date endDate;
	
	/**
	 * 是否查账户余额等信息
	 */
	private boolean queryReserveInfo;
	
	/**
	 * 是否逻辑删除
	 */
	private Boolean userRemoved;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public boolean isQueryReserveInfo() {
		return queryReserveInfo;
	}

	public void setQueryReserveInfo(boolean queryReserveInfo) {
		this.queryReserveInfo = queryReserveInfo;
	}

	public boolean isEqAccount() {
		return eqAccount;
	}

	public void setEqAccount(boolean eqAccount) {
		this.eqAccount = eqAccount;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public Boolean getUserRemoved() {
		return userRemoved;
	}

	public void setUserRemoved(Boolean userRemoved) {
		this.userRemoved = userRemoved;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	
	
}
