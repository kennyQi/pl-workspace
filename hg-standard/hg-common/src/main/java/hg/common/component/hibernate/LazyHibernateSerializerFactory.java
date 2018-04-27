package hg.common.component.hibernate;

import hg.common.component.BaseModel;
import hg.common.util.MyBeanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.CollectionSerializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.JavaSerializer;
import com.caucho.hessian.io.MapSerializer;
import com.caucho.hessian.io.Serializer;
import com.caucho.hessian.io.SerializerFactory;

/**
 * 处理：Hessian远程调用时，
 * 对象序列化hibernate实体bean中集合对象延迟加载问题 ， 
 * 采取将集合对象替换为空集合。
 */
@SuppressWarnings("rawtypes")
public class LazyHibernateSerializerFactory extends SerializerFactory {
	
	public static String versionString = org.hibernate.Version.getVersionString();

	@Override
	public Serializer getSerializer(Class cls) throws HessianProtocolException {

		if (PersistentCollection.class.isAssignableFrom(cls)) {
			return new LazySerializerForHibernate4(cls);
		}else if(HibernateProxy.class.isAssignableFrom(cls)){
			return new LazySerializerForHibernate4Proxy(cls, super.getSerializer(cls));
		}
		return super.getSerializer(cls);
	}
}

/**
 * 针对代理集合
 * @author zhurz
 */
@SuppressWarnings("rawtypes")
class LazySerializerForHibernate4 extends JavaSerializer {

	public LazySerializerForHibernate4(Class cls) {
		super(cls);
	}

	CollectionSerializer collectionSeiralizer = new CollectionSerializer();

	MapSerializer mapSerializer = new MapSerializer();
	
	/**
	 * @Title: writeObject
	 * @param @param object
	 * @param @param out
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	@Override
	public void writeObject(Object object, AbstractHessianOutput out)
			throws IOException {

		boolean ifHaveInit = Hibernate.isInitialized(object);
		PersistentCollection pc=(PersistentCollection) object;
		if (ifHaveInit && !pc.empty()) {
			if (PersistentMap.class.isAssignableFrom(object.getClass())) {
				mapSerializer.writeObject(MyBeanUtils.getFieldValue(pc, "map"), out);
			} else if(PersistentSet.class.isAssignableFrom(object.getClass())) {
				collectionSeiralizer.writeObject(MyBeanUtils.getFieldValue(pc, "set"), out);
			} else if(PersistentList.class.isAssignableFrom(object.getClass())) {
				collectionSeiralizer.writeObject(MyBeanUtils.getFieldValue(pc, "list"), out);
			} else if(PersistentBag.class.isAssignableFrom(object.getClass())) {
				collectionSeiralizer.writeObject(MyBeanUtils.getFieldValue(pc, "bag"), out);
			} else{
				super.writeObject(object, out);
			}
			out.flush();
			return;
		}

		if (PersistentMap.class.isAssignableFrom(object.getClass())) {
			mapSerializer.writeObject(new HashMap(), out);
		} else if(PersistentSet.class.isAssignableFrom(object.getClass())) {
			collectionSeiralizer.writeObject(new LinkedHashSet(), out);
		} else {
			collectionSeiralizer.writeObject(new ArrayList(), out);
		}
	}
}

/**
 * 针对代理类
 * @author zhurz
 */
class LazySerializerForHibernate4Proxy extends JavaSerializer {
	
	private Serializer serializer;

	public LazySerializerForHibernate4Proxy(Class<?> cl) {
		super(cl);
	}

	public LazySerializerForHibernate4Proxy(Class<?> cl, Serializer serializer) {
		super(cl);
		this.serializer=serializer;
	}

	/**
	 * @Title: writeObject
	 * @param @param object
	 * @param @param out
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void writeObject(Object object, AbstractHessianOutput out) throws IOException {
		boolean ifHaveInit = Hibernate.isInitialized(object);
		if (ifHaveInit) {
			serializer.writeObject(object, out);
		} else {
			Object entity = null;
			if (object != null && object instanceof HibernateProxy) {
				HibernateProxy hp = (HibernateProxy) object;
				LazyInitializer li = hp.getHibernateLazyInitializer();
				if (li != null && li.getIdentifier() != null) {
					try {
						entity = li.getPersistentClass().getDeclaredConstructor().newInstance();
						if (entity instanceof BaseModel) {
							String id = li.getIdentifier().toString();
							BaseModel bm = (BaseModel) entity;
							bm.setId(id);
						} else {
							entity = null;
						}
					} catch (Exception e) { }
				}
			}
			out.writeObject(entity);
			out.flush();
		}
	}
}