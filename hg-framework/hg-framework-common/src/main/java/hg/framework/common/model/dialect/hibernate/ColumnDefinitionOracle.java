package hg.framework.common.model.dialect.hibernate;

/**
 * 字段声明 - ORACLE
 *
 * @author zhurz
 */
public interface ColumnDefinitionOracle {
	// ---------------------<Hibernate自动创建表用>---------------------
	String MONEY_COLUM = "NUMBER(26,4)";
	String DATE_COLUM = "DATE";
	String TYPE_NUM_COLUM = "NUMBER(7)";
	String LONG_NUM_COLUM = "NUMBER(20)";
	String NUM_COLUM = "NUMBER(16)";
	String DOUBLE_COLUM = "NUMBER";
	String CHAR_COLUM = "CHAR(1)";
	String TEXT_COLUM = "CLOB";
}
