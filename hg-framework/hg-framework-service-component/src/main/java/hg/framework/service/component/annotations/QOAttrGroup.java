package hg.framework.service.component.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 查询属性组
 *
 * @author zhurz
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface QOAttrGroup {

	/**
	 * 组名：相同组名的条件使用OR查询
	 */
	String value();

}
