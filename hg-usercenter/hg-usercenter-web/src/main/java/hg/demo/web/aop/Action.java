package hg.demo.web.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* <p>Title: Action</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月29日 上午11:32:22
 */
@Target(ElementType.METHOD)   
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited  
public @interface Action {
	/**
	 * 方法描述
	 * @return
	 */
	public String desc() default "no description"; 
	
	/**
	 * 1注册， 2登录，3修改基本信息，4重置密码，5修改密码
	 * @return
	 */
	public int type();
}