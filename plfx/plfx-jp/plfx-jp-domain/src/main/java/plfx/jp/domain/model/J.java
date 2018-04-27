package plfx.jp.domain.model;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

/**
 * @类功能说明：建表常量
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午1:40:04
 * @版本：V1.0
 */
public class J implements ColumnDefinitionMysql {

	/**
	 * 对应表前缀
	 */
	public static final String TABLE_PREFIX = "PLFX_JP_";

	/**
	 * 国内机票前缀
	 */
	public static final String GN = "GN_";

	/**
	 * 国际机票前缀
	 */
	public static final String GJ = "GJ_";
	
	/**
	 * 国内机票对应表前缀
	 */
	public static final String TABLE_PREFIX_GN = TABLE_PREFIX + GN;

	/**
	 * 国际机票对应表前缀
	 */
	public static final String TABLE_PREFIX_GJ = TABLE_PREFIX + GJ;

}