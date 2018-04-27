package hg.framework.service.component.base.hibernate;

import hg.framework.common.base.BaseModel;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrGroup;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.BaseDomainQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

/**
 * 自动构建标准查询DAO
 *
 * @author zhurz
 */
@Repository
public class AutoBuildCriteriaDAO extends BaseHibernateDAO<BaseModel, BaseHibernateQO> {

	private ThreadLocal<Class<? extends BaseModel>> entityClass = new ThreadLocal<>();

	/**
	 * 设置查询实体
	 *
	 * @param clazz 查询实体类
	 * @return
	 */
	public AutoBuildCriteriaDAO forEntity(Class<? extends BaseModel> clazz) {
		entityClass.set(clazz);
		return this;
	}

	/**
	 * 得到QO有注解的属性MAP
	 *
	 * @param qo 查询对象
	 * @return 属性MAP
	 */
	private Map<String, List<Field>> getQOFieldMap(BaseHibernateQO qo) {
		
		Map<String, List<Field>> map = new HashMap<>();
		map.put(null, new ArrayList<Field>());
		List<Field> fieldList = new ArrayList<>();
		if (qo == null)
			return map;

		Class<?> currentClass = qo.getClass();
		while (currentClass.getSuperclass() != null
				&& !currentClass.equals(BaseDomainQO.class)) {
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
	 * 判断是否为true
	 *
	 * @param qo   查询对象
	 * @param name 属性名称
	 * @return
	 */
	private boolean isTrue(BaseHibernateQO qo, String name) {

		if (StringUtils.isBlank(name))
			return false;
		
		Field field = null;
		Class<?> currentClass = qo.getClass();
		while (field == null && currentClass.getSuperclass() != null
				&& !currentClass.equals(BaseDomainQO.class)) {
			try {
				field = currentClass.getDeclaredField(name);
			} catch (Exception ignored) {
			}
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
		} catch (Exception ignored) {
		}
		if (b == null || b == false)
			return false;

		return true;
	}
	
	/**
	 * 如果是String判断是否为空
	 *
	 * @param obj 传入对象
	 * @return
	 */
	private boolean checkIfStringBlank(Object obj) {
		return obj instanceof String && StringUtils.isBlank((String) obj);
	}

	@Override
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	protected Criteria buildCriteria(Criteria criteria, BaseHibernateQO qo) {

		Map<String, List<Field>> fieldMap = getQOFieldMap(qo);

		for (Entry<String, List<Field>> entry : fieldMap.entrySet()) {

			List<Criterion> criterions = new ArrayList<>();

			for (Field field : entry.getValue()) {
				QOAttr queryAttribute = field.getAnnotation(QOAttr.class);

				String propertyName = queryAttribute.name();
				Object fieldValue = null;
				try {
					fieldValue = field.get(qo);
				} catch (Exception ignored) {
				}
				
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
				if (!BaseHibernateQO.class.isAssignableFrom(field.getType()))
					continue;

				BaseHibernateQO joinQo = (BaseHibernateQO) fieldValue;
				QOConfig queryObject = field.getType().getAnnotation(QOConfig.class);
				
				if (queryObject == null)
					continue;
				
				BaseHibernateDAO<?, BaseHibernateQO> dao = (BaseHibernateDAO<?, BaseHibernateQO>) applicationContext.getBean(queryObject.daoBeanId());
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
				if (joinCriteria != null) {
					Class<? extends BaseModel> currentEntityClass = getEntityClass();
					dao.buildCriteriaOut(joinCriteria, joinQo);
					forEntity(currentEntityClass);
				}
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
	
	@Override
	@SuppressWarnings("unchecked")
	protected Class<BaseModel> getEntityClass() {
		if (entityClass.get() == null)
			throw new RuntimeException("entityClass未指定");
		return (Class<BaseModel>) entityClass.get();
	}

	@Override
	protected void queryEntityComplete(BaseHibernateQO qo, List<BaseModel> list) {

	}

}
