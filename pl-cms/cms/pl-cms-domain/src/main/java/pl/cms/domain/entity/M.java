package pl.cms.domain.entity;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

public class M implements ColumnDefinitionMysql {

	/**
	 * 平台用户表前缀
	 */
	public static final String TABLE_PREFIX_HSL_USER = "U_";
	
	/**
	 * 平台门票表前缀
	 */
	public static final String TABLE_PREFIX_HSL_MP = "MP_";
	
	/**
	 * CMS表前缀
	 */
	public static final String TABLE_PREFIX = "CMS_";
}
