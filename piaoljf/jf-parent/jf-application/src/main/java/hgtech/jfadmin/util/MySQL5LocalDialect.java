package hgtech.jfadmin.util;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;
/**
 * hibernate 方言转换
 * @author admin
 *
 */
public class MySQL5LocalDialect extends MySQL5Dialect {
	public MySQL5LocalDialect(){
		super();
		registerFunction("convert", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using gb2312)"));
	}
}
