package hg.common.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

/**
 * 领域模型基础类，需要id的聚合根和实体继承此类
 * 
 * @author yuxx
 */
@MappedSuperclass
public class BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID", unique = true, length = 64)
	private String id;
	
	/**
	 * 获取有@Id注解的Entity属性
	 * 
	 * @param clazz
	 * @return
	 */
	private static Field getEntityIdField(Class<?> clazz) {
		Class<?> currentClazz = clazz;
		while (currentClazz != Object.class) {
			try {
				Field[] fields = currentClazz.getDeclaredFields();
				for (Field field : fields) {
					if (field.getAnnotation(Id.class) != null) {
						return field;
					}
				}
			} catch (Exception e) { }
			currentClazz = currentClazz.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 创建延迟加载失败的对象
	 * 
	 * @param obj
	 * @return
	 */
	private static Object createInitializeFailedObject(Object obj) {
		if (obj instanceof HibernateProxy) {
			HibernateProxy proxy = (HibernateProxy) obj;
			Serializable id = proxy.getHibernateLazyInitializer().getIdentifier();
			if (id == null) {
				return null;
			}
			try {
				Class<?> entityClass = proxy.getHibernateLazyInitializer().getPersistentClass();
				Field idField = getEntityIdField(entityClass);
				if (idField != null) {
					Object entity = entityClass.newInstance();
					idField.setAccessible(true);
					idField.set(entity, id);
					return entity;
				}
			} catch (Exception e1) { }
			return null;
		} else if (obj instanceof PersistentCollection) {
			if (obj instanceof List) {
				return new ArrayList<Object>();
			} else if (obj instanceof Map) {
				return new HashMap<Object, Object>();
			} else if (obj instanceof Set) {
				return new HashSet<Object>();
			}
			return null;
		}
		return obj;
	}
	
	/**
	 * 获取属性，遇到延迟加载异常返回NULL
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static <T> T getProperty(Object obj, Class<T> clazz) {
		if (obj == null || clazz == null) {
			return (T) obj;
		}
		if (!Hibernate.isInitialized(obj)) {
			try {
				Hibernate.initialize(obj);
			} catch (LazyInitializationException e) {
				return (T) createInitializeFailedObject(obj);
			} catch (ObjectNotFoundException e) {
				return (T) createInitializeFailedObject(obj);
			} catch (Exception e){
				return null;
			}
		}
		return (T) obj;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
