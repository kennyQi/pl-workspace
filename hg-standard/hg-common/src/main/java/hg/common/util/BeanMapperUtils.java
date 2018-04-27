package hg.common.util;

import hg.common.testmodel.User;
import hg.common.testmodel.UserBaseInfo;

import java.lang.reflect.InvocationTargetException;

import ma.glasnost.orika.CustomFilter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import com.alibaba.fastjson.JSON;

/**
 * BEAN映射拷贝工具
 * 
 * @author zhurz
 */
public class BeanMapperUtils {

	private static MapperFacade mapper;// 映射
	static {
		CustomFilter<Object, Object> filter = new CustomFilter<Object, Object>() {
			@Override
			public <S, D> boolean shouldMap(Type<S> sourceType,
					String sourceName, S source, Type<D> destType,
					String destName, D dest, MappingContext mappingContext) {
				if (Hibernate.isInitialized(source))
					return true;
				return false;
			}

			@Override
			public boolean filtersSource() {
				return false;
			}

			@Override
			public boolean filtersDestination() {
				return true;
			}

			@Override
			public <S> S filterSource(S sourceValue, Type<S> sourceType,
					String sourceName, Type<?> destType, String destName,
					MappingContext mappingContext) {
				return sourceValue;
			}

			@Override
			public <D> D filterDestination(D destinationValue,
					Type<?> sourceType, String sourceName, Type<D> destType,
					String destName, MappingContext mappingContext) {
				return destinationValue;
			}
		};

		DefaultMapperFactory factory = new DefaultMapperFactory.Builder()
				.build();
		factory.registerFilter(filter);
		mapper = factory.getMapperFacade();
	}

	/**
	 * 映射BEAN，并拷贝
	 * 
	 * @param sourceObject
	 * @param destinationClass
	 * @return
	 */
	public static <T> T map(Object sourceObject, Class<T> destinationClass) {
		if (sourceObject == null || destinationClass == null)
			return null;
		return mapper.map(sourceObject, destinationClass);

	}

	public static <T> T map(Object sourceObject, Class<T> destinationClass,
			String... customProperties) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if (sourceObject == null || destinationClass == null)
			return null;
		T t = mapper.map(sourceObject, destinationClass);
		if (customProperties != null) {
			for (String prop : customProperties) {
				String[] propArr = StringUtils.split(prop, ":");
				MyBeanUtils
						.setSimpleProperty((Object) t, propArr[1], MyBeanUtils
								.getSimpleProperty(sourceObject, propArr[0]));
			}
		}

		return t;
	}

	public static MapperFacade getMapper() {
		return mapper;
	}

	public static void main(String[] args) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		User user = new User();
		UserBaseInfo baseInfo = new UserBaseInfo();
		baseInfo.setName("aa");
		baseInfo.setAge(1);
		user.setBaseInfo(baseInfo);

		// UserDTO dto = BeanMapperUtils.map(user, UserDTO.class,
		// "baseInfo.name:baseName", "baseInfo.age:baseInfo.nianling");
		// System.out.println(JSON.toJSONString(dto));

		User dto2 = BeanMapperUtils.map(user, User.class);
		System.out.println(JSON.toJSONString(dto2));
	}
}