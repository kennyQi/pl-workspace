package hg.dzpw.domain.aop;

import hg.common.util.SpringContextUtil;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;

import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 用于记录MODEL事件的切点处理
 * @author zhurz
 */
public aspect ModelAspect {

	/**
	 * 切入点
	 */
	public pointcut modelMethods(): execution(public * hg.dzpw.domain.model..*(..));

	Object around(): modelMethods(){
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		String methodName = methodSignature.getName();
		if (methodName.startsWith("get") || methodName.startsWith("set") || methodName.startsWith("is"))
			return proceed();
		ApplicationContext ctx = SpringContextUtil.getApplicationContext();
		if (ctx == null)
			return proceed();
		DomainEventRepository repository = ctx.getBean(DomainEventRepository.class);
		if (repository == null)
			return proceed();
		try {
			return proceed();
		} finally {
			try {
				String className = "unknowClass";
				if (thisJoinPoint.getTarget() != null) {
					className = thisJoinPoint.getTarget().getClass().getName();
				} else if (thisJoinPoint.getThis() != null) {
					className = thisJoinPoint.getThis().getClass().getName();
				}
				Object[] objs = thisJoinPoint.getArgs();
				String[] params = new String[objs.length];
				ValueFilter valueFilter = new ValueFilter() {
					@Override
					public Object process(Object source, String name, Object value) {
						if (!Hibernate.isInitialized(value))
							return null;
						return value;
					}
				};
				for (int i = 0; i < objs.length; i++) {
					params[i] = JSON.toJSONString(objs[i], valueFilter);
				}
				DomainEvent event = new DomainEvent(className, methodName, params);
				repository.save(event);
			} catch (Exception e) { }
		}
	}

}
