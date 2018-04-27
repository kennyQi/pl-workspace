package hg.demo.web.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import hg.demo.member.common.spi.OpLogSPI;
import hg.demo.member.common.spi.command.userinfo.CreateOpLogCommand;
import hg.framework.common.util.UUIDGenerator;

public class LogAspect {
	
	@Resource
	private OpLogSPI opLogSPIService;
	
	private Log logger = LogFactory.getLog(LogAspect.class);

	public Object doSystemLog(ProceedingJoinPoint point) throws Throwable {

		String methodName = point.getSignature().getName();

		// 目标方法不为空
		if (StringUtils.isNotEmpty(methodName)) {
			// set与get方法除外
			if (!(methodName.startsWith("set") || methodName.startsWith("get"))) {
				Method method = null;
				Class targetClass = point.getTarget().getClass();
				 String targetName = targetClass.getName();    
				 Object[] arguments = point.getArgs();    
				 Class targetClass2 = Class.forName(targetName);    
			        Method[] methods = targetClass2.getMethods();    
			        for (Method method2 : methods) {    
			             if (method2.getName().equals(methodName)) {    
			                Class[] clazzs = method2.getParameterTypes();    
			                if (clazzs.length == arguments.length) {    
			                    method = method2;
			                    break;    
			                }  
			            }    
			        }
				if (method != null) {

					boolean hasAnnotation = method.isAnnotationPresent(Action.class);

					if (hasAnnotation) {
						Action annotation = method.getAnnotation(Action.class);
						
						String methodDescp = annotation.desc();
						int type = annotation.type();
						if (logger.isDebugEnabled()) {
							logger.debug("Action method:" + method.getName() + " Description:" + methodDescp);
						}
						//取到当前的操作用户
						 Object[] args = point.getArgs();  
						 
						 CreateOpLogCommand command = new CreateOpLogCommand();
							command.setId(UUIDGenerator.getUUID());
							command.setType(type);
							command.setContent(methodDescp+"["+args+"]");
							command.setStatus(1);
							
				            HttpServletRequest request = null;  
				                        //通过分析aop监听参数分析出request等信息  
				            for (int i = 0; i < args.length; i++) {  
				                if (args[i] instanceof HttpServletRequest) {  
				                    request = (HttpServletRequest) args[i];  
				                    command.setOptAppId(request.getParameter("appId"));
				                    break;
				                }  
				            }
				            opLogSPIService.createOpLog(command);
				            
					}
				}

			}
		}
		return point.proceed();
	}

}
