package jxc.domain.model;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

public class M implements ColumnDefinitionMysql {

	/**
	 * 商品表前缀
	 */
	public static final String TABLE_PREFIX_JXC_PRODUCT = "PROD_";

	/**
	 * 供应商表前缀
	 */
	public static final String TABLE_PREFIX_JXC_SUPPLIER = "SUP_";

	/**
	 * 系统表前缀
	 */
	public static final String TABLE_PREFIX_JXC_SYETEM = "SYS_";

	/**
	 * 仓库表前缀
	 */
	public static final String TABLE_PREFIX_JXC_WAREHOUSE = "WH_";
	
	/**
	 * 采购单表前缀
	 */
	public static final String TABLE_PREFIX_PURCHASE_ORDER = "PO_";
	/**
	 * 采购单表前缀
	 */
	public static final String TABLE_PREFIX_WAREHOUSING = "WHI_";
}
