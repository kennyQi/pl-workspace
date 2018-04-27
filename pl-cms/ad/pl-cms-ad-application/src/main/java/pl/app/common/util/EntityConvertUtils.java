package pl.app.common.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

public class EntityConvertUtils {

	private final static ValueFilter valueFilter = new ValueFilter() {
		@Override
		public Object process(Object source, String name, Object value) {
			if (!Hibernate.isInitialized(value)) {
				return null;
			}
			return value;
		}
	};

	/**
	 * 将实体转换为DTO
	 * 
	 * @param entity
	 * @param clazz
	 * @return
	 */
	public static <T> T convertEntityToDto(Object entity, Class<T> clazz) {
		if (entity == null || clazz == null)
			return null;
		String json = JSON.toJSONString(entity, valueFilter);
		return JSON.parseObject(json, clazz);
	}

	/**
	 * 将DTO转换为实体
	 * 
	 * @param dto
	 * @param clazz
	 * @return
	 */
	public static <T> T convertDtoToEntity(Object dto, Class<T> clazz) {
		if (dto == null || clazz == null)
			return null;
		String json = JSON.toJSONString(dto, valueFilter);
		return JSON.parseObject(json, clazz);
	}

	/**
	 * 将实体转换为DTO集合
	 * 
	 * @param entity
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> convertEntityToDtoList(List<?> entityList, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (Object entity : entityList) {
			list.add(convertEntityToDto(entity, clazz));
		}
		return list;
	}

	/**
	 * 将DTO转换为实体集合
	 * 
	 * @param dtoList
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> convertDtoToEntityList(List<?> dtoList, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (Object dto : dtoList) {
			list.add(convertDtoToEntity(dto, clazz));
		}
		return list;
	}

}
