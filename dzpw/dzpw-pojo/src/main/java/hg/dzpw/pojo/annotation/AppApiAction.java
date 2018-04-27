package hg.dzpw.pojo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @类功能说明: API注解
 * @类修改者：
 * @修改日期：2014-11-18下午2:17:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午2:17:25
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppApiAction {
	
	/**
	 * @方法功能说明：APP API 动作
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-27下午2:47:45
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String value();
}