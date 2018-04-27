package jxc.domain.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Constants implements Serializable {

	/**
	 * 供应商类型 经销
	 */
	public static final Integer SUPPLIER_TYPE_COMMISSION_SALE = 0;

	/**
	 * 供应商类型 代销
	 */
	public static final Integer SUPPLIER_TYPE_CONSIGNMENT_SALE = 1;

	/**
	 * 商品属性 普通商品
	 */
	public static final Integer PRODUCT_ATTRIBUTE_NORMAL = 0;

	/**
	 * 商品属性 虚拟商品
	 */
	public static final Integer PRODUCT_ATTRIBUTE_VIRTUAL = 1;

	/**
	 * 供应商状态 未审核
	 */
	public static final Integer SUPPLIER_STATUS_UNCHECK = 0;

	/**
	 * 供应商状态 审核通过
	 */
	public static final Integer SUPPLIER_STATUS_PASS = 1;

	/**
	 * 供应商状态 审核未通过
	 */
	public static final Integer SUPPLIER_STATUS_NO_PASS = 2;

	/**
	 * 图片类型 商品正面图
	 */
	public static final Integer PRODUCT_IMAGE_FRONT = 1;

	/**
	 * 图片类型 商品背面图
	 */
	public static final Integer PRODUCT_IMAGE_BACK = 2;

	/**
	 * 图片类型 商品细节图
	 */
	public static final Integer PRODUCT_IMAGE_DETAIL = 3;

	/**
	 * 图片类型 商品配件图
	 */
	public static final Integer PRODUCT_IMAGE_FITTINGS = 4;

	/**
	 * 图片类型 商品其他图
	 */
	public static final Integer PRODUCT_IMAGE_OTHER_ONE = 5;

	/**
	 * 图片类型 商品其他图
	 */
	public static final Integer PRODUCT_IMAGE_OTHER_TWO = 6;

	/**
	 * 图片类型 供应商营业执照图
	 */
	public static final Integer SUPPLIER_IMAGE_LICENSE = 7;

	/**
	 * 图片类型 供应商税务登记证图
	 */
	public static final Integer SUPPLIER_IMAGE_TAX = 8;

	/**
	 * 图片类型 供应商法人身份证图
	 */
	public static final Integer SUPPLIER_IMAGE_LEGAL_PERSON_IDCARD = 9;

	/**
	 * 图片类型 供应商授权书图
	 */
	public static final Integer SUPPLIER_IMAGE_AUTHORIZATION = 10;

	/**
	 * 图片类型 供应商资质图
	 */
	public static final Integer SUPPLIER_IMAGE_QUALIFICATION = 11;

	/**
	 * 图片类型 供应商授权书图
	 */
	public static final Integer SUPPLIER_IMAGE_OTHER = 12;

	public static final Integer PURCHASE_ORDER_NO_CHECK = 0;
	public static final Integer PURCHASE_ORDER_CHECK_PASS = 1;
	public static final Integer PURCHASE_ORDER_WAREHOUSING_WAIT = 2;
	public static final Integer PURCHASE_ORDER_WAREHOUSING_PARTIAL = 3;
	public static final Integer PURCHASE_ORDER_WAREHOUSING_ALL = 4;
	// 取消审核
	public static final Integer PURCHASE_ORDER_WAREHOUSING_CANCEL = 5;
}
