package hg.framework.common.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Locale;

public class MyBeanUtils {

	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 *
	 * @param object    对象
	 * @param fieldName 属性名称
	 * @return 属性值
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("never happend exception!", e);
		}

		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 *
	 * @param object    对象
	 * @param fieldName 属性名称
	 * @param value     属性值
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("never happend exception!", e);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 *
	 * @param object    对象
	 * @param fieldName 属性名称
	 * @return 类中的属性领域
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 *
	 * @param clazz     类
	 * @param fieldName 属性名
	 * @return 类中的属性领域
	 */
	@SuppressWarnings("rawtypes")
	protected static Field getDeclaredField(final Class clazz, final String fieldName) {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 强制转换fileld可访问.
	 *
	 * @param field 属性领域
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	public static Object getSimpleProperty(Object bean, String propName)
			throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object returnValue = bean;
		if (StringUtils.contains(propName, ".")) {
			String[] propNames = StringUtils.split(propName, ".");
			for (String propName1 : propNames) {
				returnValue = returnValue.getClass().getMethod(getReadMethod(propName1)).invoke(returnValue);
			}
			return returnValue;
		} else {
			return bean.getClass().getMethod(getReadMethod(propName)).invoke(bean);
		}

	}

	public static void setSimpleProperty(Object bean, String propName, Object value)
			throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (StringUtils.contains(propName, ".")) {
			String[] propNames = StringUtils.split(propName, ".");
			for (int i = 0; i < propNames.length; i++) {
				if (i < propNames.length - 1) {
					bean = bean.getClass().getMethod(getReadMethod(propNames[i])).invoke(bean);
				} else {
					bean.getClass().getMethod(getWriteMethod(propNames[i]), value.getClass()).invoke(bean, value);
				}
			}
		} else {
			bean.getClass().getMethod(getWriteMethod(propName), value.getClass()).invoke(bean, value);
		}

	}

	private static String getReadMethod(String name) {
		return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
	}

	private static String getWriteMethod(String name) {
		return "set" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
	}

}
