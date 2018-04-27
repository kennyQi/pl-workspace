package hg.demo.member.common.domain.model.def;

import hg.framework.common.model.dialect.hibernate.ColumnDefinitionMysql;

/**
 * @author zhurz
 */
public class M implements ColumnDefinitionMysql {

	/**
	 * 表前缀
	 */
	public static final String TABLE_PREFIX = "OPT_";
	
	/**
	 * 表前缀 里程分销
	 */
	public static final String FX_TABLE_PREFIX = "HGFX_";
}