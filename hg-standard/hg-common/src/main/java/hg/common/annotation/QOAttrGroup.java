package hg.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @类功能说明：查询属性组
 * @类修改者：
 * @修改日期：2015-3-27上午9:30:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-27上午9:30:31
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface QOAttrGroup {

	/**
	 * 组名：相同组名的条件使用OR查询
	 */
	public String value();

}
