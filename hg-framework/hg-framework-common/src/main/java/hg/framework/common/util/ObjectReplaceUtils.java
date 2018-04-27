package hg.framework.common.util;


import hg.framework.common.model.KeyValue;
import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 对象替换工具
 *
 * @author zhurz
 */
@SuppressWarnings("unchecked")
public class ObjectReplaceUtils {

	/**
	 * 替换处理器
	 */
	public interface Handler {
		/**
		 * 替换对象
		 *
		 * @param name  属性名称
		 * @param value 传入的对象
		 * @return 替换后的对象
		 */
		Object replace(String name, Object value);
	}

	private static Map<Class, List<Field>> cacheFieldMap = new HashMap<>();

	private static List<KeyValue<Field, Object>> getFields(Object obj) {
		List<KeyValue<Field, Object>> fields = new ArrayList<>();
		Class<?> aClass = obj.getClass();
		if (cacheFieldMap.containsKey(aClass)) {
			List<Field> cacheFields = cacheFieldMap.get(aClass);
			for (Field cacheField : cacheFields) {
				fields.add(new KeyValue<>(cacheField, obj));
			}
			return fields;
		}
		List<Class<?>> allSuperclasses = ClassUtils.getAllSuperclasses(aClass);
		allSuperclasses.add(0, aClass);
		List<Field> cacheFields = new ArrayList<>();
		for (Class<?> superclass : allSuperclasses) {
			if (superclass.getName().startsWith("java.")) continue;
			Field[] declaredFields = superclass.getDeclaredFields();
			for (Field declaredField : declaredFields) {
				int modifiers = declaredField.getModifiers();
				if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers) && !Modifier.isFinal(modifiers)) {
					cacheFields.add(declaredField);
					fields.add(new KeyValue<>(declaredField, obj));
				}
			}
		}
		cacheFieldMap.put(aClass, cacheFields);
		return fields;
	}

	private static List<KeyValue<Field, Object>> getFields(Object obj, HashSet<Object> set) {
		List<KeyValue<Field, Object>> fields = new ArrayList<>();
		if (obj == null) return fields;
		if (set == null) set = new HashSet<>();
		if (obj instanceof Iterable) {
			Iterable iterable = (Iterable) obj;
			for (Object o : iterable) {
				if (set.contains(obj)) continue;
				set.add(o);
				fields.addAll(getFields(o, set));
			}
		} else if (obj instanceof Map) {
			Map<Object, Object> objMap = (Map<Object, Object>) obj;
			for (Map.Entry<Object, Object> entry : objMap.entrySet()) {
				Object o = entry.getValue();
				if (set.contains(o)) continue;
				set.add(o);
				fields.addAll(getFields(o, set));
			}
		}

		fields.addAll(getFields(obj));

		return fields;
	}

	/**
	 * 替换对象
	 *
	 * @param obj     需要替换的对象
	 * @param handler 替换对象的处理器
	 * @param <T>     返回类型
	 * @return 替换后的对象
	 * @throws IllegalAccessException
	 */
	public static <T> T replace(T obj, Handler handler) throws IllegalAccessException {
		obj = (T) handler.replace("this", obj);
		if (obj == null) return null;
		HashSet<Object> set = new HashSet<>();
		set.add(obj);
		List<KeyValue<Field, Object>> fields = getFields(obj, set);
		Queue<KeyValue<Field, Object>> queue = new ArrayDeque<>();
		queue.addAll(fields);
		while (!queue.isEmpty()) {
			KeyValue<Field, Object> fieldObjectEntry = queue.poll();
			Field field = fieldObjectEntry.getKey();
			Object target = fieldObjectEntry.getValue();
			field.setAccessible(true);
			Object oldValue = field.get(target);
			if (oldValue == null || set.contains(oldValue)) continue;
			if (!oldValue.getClass().getName().startsWith("java.")) {
				set.add(oldValue);
			}
			Object newValue = handler.replace(field.getName(), oldValue);
			if (!oldValue.equals(newValue))
				field.set(target, newValue);
			if (newValue instanceof List) {
				List valueList = (List) newValue;
				for (int j = 0; j < valueList.size(); j++) {
					Object oldListValue = valueList.get(j);
					if (oldListValue == null || set.contains(oldListValue)) continue;
					set.add(oldListValue);
					Object newListValue = handler.replace(String.valueOf(j), oldListValue);
					if (!oldListValue.equals(newListValue))
						valueList.set(j, newListValue);
					queue.addAll(getFields(newListValue, set));
				}
			} else if (newValue instanceof Set) {
				Set<Object> newValueSet = (Set<Object>) newValue;
				Object[] objects = newValueSet.toArray();
				for (int j = 0; j < objects.length; j++) {
					Object o = objects[j];
					if (o == null || set.contains(o)) continue;
					set.add(o);
					objects[j] = o = handler.replace(String.valueOf(j), o);
					queue.addAll(getFields(o, set));
				}
				newValueSet.clear();
				Collections.addAll(newValueSet, objects);
			} else if (newValue instanceof Map) {
				Map<Object, Object> map = (Map<Object, Object>) newValue;
				for (Map.Entry<Object, Object> entry : map.entrySet()) {
					Object oldMapValue = entry.getValue();
					if (oldMapValue == null || set.contains(oldMapValue)) continue;
					set.add(oldMapValue);
					String key = entry.getKey() == null ? null : entry.getKey().toString();
					Object newMapValue = handler.replace(key, oldMapValue);
					if (!oldMapValue.equals(newMapValue))
						entry.setValue(newMapValue);
					queue.addAll(getFields(newMapValue, set));
				}
			} else if (newValue != null && !newValue.getClass().getName().startsWith("java.")) {
				queue.addAll(getFields(newValue, set));
			}
		}
		return obj;
	}

}
