package hg.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @类功能说明：查询配置
 * @类修改者：
 * @修改日期：2015-3-27上午9:30:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-27上午9:30:31
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface QOConfig {
	
	/**
	 * @方法功能说明：dao在spring中的beanId
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午6:04:48
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String daoBeanId();
}
