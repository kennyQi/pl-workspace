package hg.framework.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基础对象
 *
 * @author zhurz
 */
public abstract class BaseObject {

	/**
	 * JSON过滤器，未初始化的Hibernate代理对象不参与序列化。
	 */
	private static class JSONFilter implements ValueFilter {

		private ThreadLocal<SerializerFeature[]> serializerFeature = new ThreadLocal<SerializerFeature[]>();

		public JSONFilter serializerFeature(SerializerFeature... serializerFeatures) {
			serializerFeature.set(serializerFeatures);
			return this;
		}

		public Object process(Object object, String name, Object value) {
			SerializerFeature[] serializerFeatures = serializerFeature.get();
			if (serializerFeatures != null && value instanceof Date) {
				if (ArrayUtils.contains(serializerFeatures, SerializerFeature.UseISO8601DateFormat)) {
					return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(value);
				} else if (ArrayUtils.contains(serializerFeatures, SerializerFeature.WriteDateUseDateFormat)) {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
				} else {
					return ((Date) value).getTime();
				}
			} else if (Hibernate.isInitialized(value))
				return value;
			return null;
		}
	}

	private final static transient JSONFilter jsonFilter = new JSONFilter();

	/**
	 * 转为格式化后的JSON串
	 *
	 * @return
	 */
	public String toFormatJSONString() {
		return toJSONString(SerializerFeature.PrettyFormat);
	}

	/**
	 * 转为JSON字符串
	 *
	 * @param features
	 * @return
	 */
	public String toJSONString(SerializerFeature... features) {
		return JSON.toJSONString(this, jsonFilter.serializerFeature(features), features);
	}

}
