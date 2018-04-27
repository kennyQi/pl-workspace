package slfx.xl.domain.model;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

/**
 * 
 * @类功能说明：建表常量
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月3日下午4:03:19
 * @版本：V1.0
 *
 */
public class M implements ColumnDefinitionMysql {

	/**
	 * 对应表前缀
	 */
	public static final String TABLE_PREFIX = "SLFX_XL_";
	
	/**
	 * 流程表前缀
	 */
	public static final String TABLE_PREFIX_SLFX_XL_SAGA = "SLFX_XL_SAGA_";
	
}
