package pay.record.domain.base;

import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;

/**
 * 
 * @类功能说明：表前缀
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月20日上午10:36:16
 * @版本：V1.0
 *
 */
public class M implements ColumnDefinitionMysql {
	/**
	 * 对应表前缀
	 */
	public static final String TABLE_PREFIX_PAYRECORD = "PAY_RECORD";
	
	
	// ---------------------<Hibernate自动创建表用>---------------------
	public static final String MONEY_COLUM = "decimal(26,2)";
	
}