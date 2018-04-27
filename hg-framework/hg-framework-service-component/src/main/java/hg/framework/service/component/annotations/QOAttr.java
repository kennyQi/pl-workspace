package hg.framework.service.component.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 查询属性
 *
 * @author zhurz
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface QOAttr {
	
	/**
	 * ENTITY属性名称
	 */
	String name();

	/**
	 * 查询类型
	 */
	QOAttrType type() default QOAttrType.EQ;
	
	/**
	 * 如果用LIKE查询的判断属性名称
	 */
	String ifTrueUseLike() default "";
	
}
