package hg.common.component.hibernate.dialect;

/**
 * 字段声明 - MYSQL
 * @author zhurz
 */
public interface ColumnDefinitionMysql {
	// ---------------------<Hibernate自动创建表用>---------------------
	public static final String MONEY_COLUM = "decimal(26,4)";
	public static final String DATE_COLUM = "datetime";
	public static final String TYPE_NUM_COLUM = "int(11)";
	public static final String LONG_NUM_COLUM = "bigint(20)";
	public static final String NUM_COLUM = "int(11)";
	public static final String DOUBLE_COLUM = "double";
	public static final String CHAR_COLUM = "char(1)";
	public static final String TEXT_COLUM = "text";
}