package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * 商户查询qo
 * 
 * @author Caihuan
 * @date 2016年6月1日
 */
@SuppressWarnings("serial")
public class DistributorSQO extends BaseSPIQO {

	/** 折扣--定价模式 */
	public static final int DISCOUNT_TYPE_FIXED_PRICE = 1;

	/** 折扣--返利模式 */
	public static final int DISCOUNT_TYPE_REBATE = 2;

	private String id;

	/**
	 * 商户编号
	 */
	private String code;

	/**
	 * 商户帐号
	 */
	private String account;

	/**
	 * 商户姓名
	 */
	private String name;

	/**
	 * 商户启用状态 0--禁用 1--启用
	 */
	private Integer status;

	/**
	 * 是否查商户余额信息
	 */
	private boolean queryReserveInfo;

	/**
	 * 商户手机
	 */
	private String phone;

	private Integer discountType;

	public Integer getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}

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

	public boolean isQueryReserveInfo() {
		return queryReserveInfo;
	}

	public void setQueryReserveInfo(boolean queryReserveInfo) {
		this.queryReserveInfo = queryReserveInfo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
