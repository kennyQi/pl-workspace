package lxs.domain.model;
import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

public class M implements ColumnDefinitionMysql {
	
	/**
	 * APP表功能表前缀
	 */
	 public static final String TABLE_PREFIX_MP="MP_";
	
	 /**
	 * 平台用户表前缀
	 */
	 public static final String TABLE_PREFIX_XL="XL_";
	 /**
	 * 用户模块表前缀
	 */
	public static final String TABLE_PREFIX_USER = "USER_";
	
	/**
	 * 流程表前缀
	 */
	public static final String TABLE_PREFIX_SAGA = "LXS_SAGA_";
}
