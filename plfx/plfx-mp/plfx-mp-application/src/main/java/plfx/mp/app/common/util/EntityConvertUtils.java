package plfx.mp.app.common.util;

import hg.common.util.BeanMapperUtils;

import java.util.List;

import org.hibernate.Hibernate;

import plfx.mp.domain.model.scenicspot.ScenicSpot;

import com.alibaba.fastjson.serializer.ValueFilter;

public class EntityConvertUtils {
	
	protected final static ValueFilter valueFilter = new ValueFilter() {
		@Override
		public Object process(Object source, String name, Object value) {
			if (!Hibernate.isInitialized(value)) {
				return null;
			}
			return value;
		}
	};

	protected static Object replaceProxy(Object entity) {
		if (entity == null)
			return null;
		if (entity instanceof ScenicSpot) {
			ScenicSpot scenicSpot = (ScenicSpot) entity;
			scenicSpot.setImages(scenicSpot.getImages());
		}
		return entity;
	}

	protected static List<Object> replaceProxyList(List<Object> list) {
		if (list == null)
			return null;
		for (Object obj : list) {
			replaceProxy(obj);
		}
		return list;
	}

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
		return BeanMapperUtils.getMapper().map(replaceProxy(entity), clazz);
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
		return BeanMapperUtils.map(dto, clazz);
	}

	/**
	 * 将实体转换为DTO集合
	 * 
	 * @param entity
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> convertEntityToDtoList(List<?> entityList, Class<T> clazz) {
		return BeanMapperUtils.getMapper().mapAsList(replaceProxyList((List<Object>) entityList), clazz);
	}

	/**
	 * 将DTO转换为实体集合
	 * 
	 * @param dtoList
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> convertDtoToEntityList(List<?> dtoList, Class<T> clazz) {
		return BeanMapperUtils.getMapper().mapAsList(dtoList, clazz);
	}

}
