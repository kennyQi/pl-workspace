package hg.dzpw.domain.model;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

/**
 * @类功能说明：表前缀
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 */
public class M implements ColumnDefinitionMysql {
	/**
	 * 电子票务表前缀
	 */
	public static final String TABLE_PREFIX = "DZPW_";
}
