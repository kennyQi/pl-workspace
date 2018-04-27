package hg.common.component;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrGroup;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.page.Pagination;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：公共DAO，auto*()查询
 * @类修改者：
 * @修改日期：2015-3-27下午5:59:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-27下午5:59:33
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class CommonDao extends BaseDao<BaseModel, BaseQo> {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private ThreadLocal<Class<? extends BaseModel>> entityClass = new ThreadLocal<Class<? extends BaseModel>>();

	/**
	 * @方法功能说明：设置查询实体
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午5:58:06
	 * @修改内容：
	 * @参数：@param clazz
	 * @参数：@return
	 * @return:CommonDao
	 * @throws
	 */
	public CommonDao forEntity(Class<? extends BaseModel> clazz) {
		entityClass.set(clazz);
		return this;
	}
	
	/**
	 * @方法功能说明：根据QO注解自动查询唯一
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午5:58:26
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:T
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseModel> T autoQueryUnique(BaseQo qo) {
		return (T) super.queryUnique(qo);
	}

	/**
	 * @方法功能说明：根据QO注解自动查询列表
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午5:58:35
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<T>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseModel> List<T> autoQueryList(BaseQo qo) {
		return (List<T>) super.queryList(qo);
	}

	/**
	 * @方法功能说明：根据QO注解自动查询列表
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午5:58:56
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param offset
	 * @参数：@param maxSize
	 * @参数：@return
	 * @return:List<T>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseModel> List<T> autoQueryList(BaseQo qo, Integer offset, Integer maxSize) {
		return (List<T>) super.queryList(qo, offset, maxSize);
	}

	/**
	 * @方法功能说明：根据QO注解自动分页查询
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午5:59:05
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination autoQueryPagination(Pagination pagination) {
		return super.queryPagination(pagination);
	}

	/**
	 * @方法功能说明：得到QO有注解的属性MAP
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午4:54:10
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:Map<String,List<Field>>
	 * @throws
	 */
	private Map<String, List<Field>> getQoFieldMap(BaseQo qo) {
		
		Map<String, List<Field>> map = new HashMap<String, List<Field>>();
		map.put(null, new ArrayList<Field>());
		List<Field> fieldList = new ArrayList<Field>();
		if (qo == null)
			return map;

		Class<?> currentClass = qo.getClass();
		while (currentClass.getSuperclass() != null
				&& !currentClass.equals(BaseQo.class)) {
			Field[] fields = currentClass.getDeclaredFields();
			fieldList.addAll(Arrays.asList(fields));
			currentClass = currentClass.getSuperclass();
		}

		// 检查QO属性
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			if (field.getAnnotation(QOAttr.class) == null) {
				fieldList.remove(i--);
				continue;
			}
			field.setAccessible(true);
			QOAttrGroup queryAttributeGroup = field.getAnnotation(QOAttrGroup.class);
			if (queryAttributeGroup != null) {
				String groupName = queryAttributeGroup.value();
				if (!map.containsKey(groupName))
					map.put(groupName, new ArrayList<Field>());
				map.get(groupName).add(field);
			} else
				map.get(null).add(field);
		}

		return map;
	}
	
	
	
	/**
	 * @方法功能说明：判断是否为true
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午4:54:37
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param name
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private boolean isTrue(BaseQo qo, String name) {

		if (StringUtils.isBlank(name))
			return false;
		
		Field field = null;
		Class<?> currentClass = qo.getClass();
		while (field == null && currentClass.getSuperclass() != null
				&& !currentClass.equals(BaseQo.class)) {
			try {
				field = currentClass.getDeclaredField(name);
			} catch (Exception e) { }
			currentClass = currentClass.getSuperclass();
		}
		if (field == null)
			return false;
		
		if (!(field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)))
			return false;

		Boolean b = null;
		try {
			field.setAccessible(true);
			b = (Boolean) field.get(qo);
		} catch (Exception e) { }
		if (b == null || b == false)
			return false;

		return true;
	}
	
	/**
	 * @方法功能说明：如果是String判断是否为空
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-27下午4:54:54
	 * @修改内容：
	 * @参数：@param obj
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private boolean checkIfStringBlank(Object obj) {
		if (!(obj instanceof String))
			return false;
		if (StringUtils.isBlank((String) obj))
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {

		Map<String, List<Field>> fieldMap = getQoFieldMap(qo);

		for (Entry<String, List<Field>> entry : fieldMap.entrySet()) {

			List<Criterion> criterions = new ArrayList<Criterion>();

			for (Field field : entry.getValue()) {
				QOAttr queryAttribute = field.getAnnotation(QOAttr.class);

				String propertyName = queryAttribute.name();
				Object fieldValue = null;
				try {
					fieldValue = field.get(qo);
				} catch (Exception e) { }
				
				if (fieldValue == null)
					continue;
				if (checkIfStringBlank(fieldValue))
					continue;

				// like查询
				if (isTrue(qo, queryAttribute.ifTrueUseLike())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.ANYWHERE));
					continue;
				}

				// 完全匹配
				if (QOAttrType.EQ.equals(queryAttribute.type())) {
					criterions.add(Restrictions.eq(propertyName, fieldValue));
				} 
				// 完全匹配或为NULL
				else if (QOAttrType.EQ_OR_NULL.equals(queryAttribute.type())) {
					criterions.add(Restrictions.eqOrIsNull(propertyName, fieldValue));
				} 
				// 不匹配
				else if (QOAttrType.NE.equals(queryAttribute.type())) {
					criterions.add(Restrictions.ne(propertyName, fieldValue));
				} 
				// 不匹配或为NULL
				else if (QOAttrType.NE_OR_NULL.equals(queryAttribute.type())) {
					criterions.add(Restrictions.neOrIsNotNull(propertyName, fieldValue));
				} 
				// 大于等于
				else if (QOAttrType.GE.equals(queryAttribute.type())) {
					criterions.add(Restrictions.ge(propertyName, fieldValue));
				} 
				// 大于
				else if (QOAttrType.GT.equals(queryAttribute.type())) {
					criterions.add(Restrictions.gt(propertyName, fieldValue));
				} 
				// 小于等于
				else if (QOAttrType.LE.equals(queryAttribute.type())) {
					criterions.add(Restrictions.le(propertyName, fieldValue));
				} 
				// 小于
				else if (QOAttrType.LT.equals(queryAttribute.type())) {
					criterions.add(Restrictions.lt(propertyName, fieldValue));
				} 
				// 模糊匹配任何位置
				else if (QOAttrType.LIKE_ANYWHERE.equals(queryAttribute.type())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.ANYWHERE));
				} 
				// 模糊匹配开始位置
				else if (QOAttrType.LIKE_START.equals(queryAttribute.type())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.START));
				} 
				// 模糊匹配结尾
				else if (QOAttrType.LIKE_END.equals(queryAttribute.type())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.END));
				} 
				// 包含
				else if (QOAttrType.IN.equals(queryAttribute.type())) {
					if (field.getType().isArray()) {
						Object[] objects = (Object[]) fieldValue;
						if (objects.length == 0)
							continue;
						criterions.add(Restrictions.in(propertyName, objects));
					} else {
						Collection<Object> objects = (Collection<Object>) fieldValue;
						if (objects.size() == 0)
							continue;
						criterions.add(Restrictions.in(propertyName, objects));
					}
				} 
				// 排序
				else if (QOAttrType.ORDER.equals(queryAttribute.type())) {
					Number num = (Number) fieldValue;
					if (num.intValue() == 0)
						continue;
					criteria.addOrder(num.intValue() > 0 ? Order.asc(propertyName) : Order.desc(propertyName));
				} 
				// 立即加载
				else if (QOAttrType.FATCH_EAGER.equals(queryAttribute.type())) {
					if (fieldValue instanceof Boolean && ((Boolean) fieldValue))
						criteria.setFetchMode(propertyName, FetchMode.JOIN);
					else
						continue;
				}
				
				// -------------- QO联表查询 --------------
				if (!BaseQo.class.isAssignableFrom(field.getType()))
					continue;

				BaseQo joinQo = (BaseQo) fieldValue;
				QOConfig queryObject = field.getType().getAnnotation(QOConfig.class);
				
				if (queryObject == null)
					continue;
				
				BaseDao<?, BaseQo> dao = (BaseDao<?, BaseQo>) applicationContext.getBean(queryObject.daoBeanId());
				if (dao == null)
					continue;
				Criteria joinCriteria = null;
				
				// 内联表
				if (QOAttrType.JOIN.equals(queryAttribute.type())) {
					joinCriteria = criteria.createCriteria(propertyName, joinQo.getAlias(), JoinType.INNER_JOIN);
				} 
				// 左外联表
				else if (QOAttrType.LEFT_JOIN.equals(queryAttribute.type())) {
					joinCriteria = criteria.createCriteria(propertyName, joinQo.getAlias(), JoinType.LEFT_OUTER_JOIN);
				} 
				// 右外联表
				else if (QOAttrType.RIGHT_JOIN.equals(queryAttribute.type())) {
					joinCriteria = criteria.createCriteria(propertyName, joinQo.getAlias(), JoinType.RIGHT_OUTER_JOIN);
				}
				
				// 联表条件判断
				if (joinCriteria != null)
					dao.buildCriteriaOut(joinCriteria, joinQo);
			}

			// 组装条件
			if (criterions.size() > 0) {
				if (entry.getKey() == null)
					for (Criterion criterion : criterions)
						criteria.add(criterion);
				else {
					Criterion[] criterionsArray = new Criterion[criterions.size()];
					criterions.toArray(criterionsArray);
					criteria.add(Restrictions.or(criterionsArray));
				}
			}
		}

		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<BaseModel> getEntityClass() {
		if (entityClass.get() == null)
			throw new RuntimeException("entityClass未指定");
		return (Class<BaseModel>) entityClass.get();
	}

}
