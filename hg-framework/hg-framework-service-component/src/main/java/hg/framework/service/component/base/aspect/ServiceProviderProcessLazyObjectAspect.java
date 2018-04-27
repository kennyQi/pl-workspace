package hg.framework.service.component.base.aspect;

import hg.framework.common.base.BaseModel;
import hg.framework.common.util.ObjectReplaceUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Hibernate;
import org.hibernate.collection.internal.AbstractPersistentCollection;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.io.Serializable;

/**
 * 处理服务提供者延迟加载对象切面处理
 */
public class ServiceProviderProcessLazyObjectAspect {

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		// 调用服务
		final Object proceed = pjp.proceed();

		// 处理可能存在的Hibernate未初始化的代理对象
		return ObjectReplaceUtils.replace(proceed, new ObjectReplaceUtils.Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public Object replace(String name, Object value) {
				if (Hibernate.isInitialized(value))
					return value;
				SessionImplementor session = null;
				if (value instanceof HibernateProxy) {
					session = ((HibernateProxy) value).getHibernateLazyInitializer().getSession();
				} else if (value instanceof AbstractPersistentCollection) {
					session = ((AbstractPersistentCollection) value).getSession();
				}
				if (session != null)
					return value;
				else if (value instanceof HibernateProxy && value instanceof BaseModel) {
					try {
						HibernateProxy proxy = (HibernateProxy) value;
						LazyInitializer initializer = proxy.getHibernateLazyInitializer();
						Serializable id = initializer.getIdentifier();
						Class persistentClass = initializer.getPersistentClass();
						BaseModel entity = (BaseModel) persistentClass.newInstance();
						entity.setId(id);
						return entity;
					} catch (Exception ignored) {
					}
				}
				return null;
			}
		});
	}

}
