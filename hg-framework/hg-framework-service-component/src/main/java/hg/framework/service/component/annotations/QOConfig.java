package hg.framework.service.component.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 查询配置
 *
 * @author zhurz
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface QOConfig {
	
	/**
	 * dao在spring中的beanId
	 */
	String daoBeanId();
}
