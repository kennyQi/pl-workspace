package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.DistributorSQO;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "distributorDAO")
public class DistributorQO extends BaseHibernateQO<String> {

	/**
	 * 商户编号
	 */
	@QOAttr(name = "code", type = QOAttrType.LIKE_ANYWHERE)
	private String code;

	/**
	 * 商户启用状态
	 */
	@QOAttr(name = "status", type = QOAttrType.EQ_OR_NULL)
	private Integer status;
	
	/**
	 * 审核状态
	 */
	@QOAttr(name = "checkStatus", type = QOAttrType.EQ_OR_NULL)
	private Integer checkStatus;

	/**
	 * 是否查出余额信息
	 */
	private boolean queryReserveInfo;

	/**
	 * 商户手机
	 */
	@QOAttr(name = "phone", type = QOAttrType.EQ)
	private String phone;

	/**
	 * 排序字段(默认按姓名升序)
	 */
	@QOAttr(name = "name", type = QOAttrType.ORDER)
	private Integer orderByName;

	@QOAttr(name = "discountType", type = QOAttrType.EQ)
	private Integer discountType;
	
	public static DistributorQO build(DistributorSQO sqo) {
		DistributorQO qo = new DistributorQO();
		qo.setId(sqo.getId());
		qo.setCode(sqo.getCode());
		qo.setStatus(sqo.getStatus());
		qo.setLimit(sqo.getLimit());
		qo.setQueryReserveInfo(sqo.isQueryReserveInfo());
		qo.setDiscountType(sqo.getDiscountType());
		if(sqo.getPhone() != null)
			qo.setPhone(sqo.getPhone());
		// 默认按姓名升序
		qo.setOrderByName(1);
		return qo;
	}

	public Integer getOrderByName() {
		return orderByName;
	}

	public void setOrderByName(Integer orderByName) {
		this.orderByName = orderByName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}

	
}
