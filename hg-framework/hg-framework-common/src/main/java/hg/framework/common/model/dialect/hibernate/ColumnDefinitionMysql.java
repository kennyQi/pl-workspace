package hg.framework.common.model.dialect.hibernate;

/**
 * 字段声明 - MYSQL
 *
 * @author zhurz
 */
public interface ColumnDefinitionMysql {
	// ---------------------<Hibernate自动创建表用>---------------------
	String MONEY_COLUM = "decimal(26,4)";
	String DATE_COLUM = "datetime";
	String TYPE_NUM_COLUM = "int(11)";
	String LONG_NUM_COLUM = "bigint(20)";
	String NUM_COLUM = "int(11)";
	String DOUBLE_COLUM = "double";
	String CHAR_COLUM = "char(1)";
	String TEXT_COLUM = "text";
}