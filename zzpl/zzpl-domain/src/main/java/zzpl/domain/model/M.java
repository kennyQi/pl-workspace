package zzpl.domain.model;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

public class M implements ColumnDefinitionMysql {

	/**
	 * 平台用户表前缀
	 */
	public static final String TABLE_PREFIX_ZZPL_USER = "U_";
	/**
	 * 平台门票表前缀
	 */
	public static final String TABLE_PREFIX_ZZPL_MP = "MP_";
	/**
	 * 平台线路前缀
	 */
	public static final String TABLE_PREFIX_ZZPL_XL = "XL_";
	/**
	 * 机票前缀
	 */
	public static final String TABLE_PREFIX_ZZPL_JP = "JP_";
	/**
	 * 平台广告前缀
	 */
	public static final String TABLE_PREFIX_ZZPL_AD = "AD_";
	/**
	 * 平台流程表
	 */
	public static final String TABLE_PREFIX_ZZPL_WORKFLOW = "W_";
	/**
	 * 平台订单表
	 */
	public static final String TABLE_PREFIX_ZZPL_ORDER = "O_";
	
	/**
	 * 平台订单表
	 */
	public static final String TABLE_PREFIX_ZZPL_SAGA= "SAGA_";
	
	/**
	 * APP表
	 */
	public static final String TABLE_PREFIX_ZZPL_APP= "APP_";
	
}
