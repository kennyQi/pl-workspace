package hg.service.image.domain.model.base;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

/**
 * @类功能说明：表前缀
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 */
public class M implements ColumnDefinitionMysql {
	/**
	 * 对应表前缀
	 */
	public static final String TABLE_PREFIX = "IMG_";
}