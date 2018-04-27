package hg.framework.service.component.base.hibernate;

import hg.framework.common.base.BaseModel;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.framework.common.util.MyBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 基础查询接口
 *
 * @param <T>  实体类型
 * @param <QO> 查询对象
 * @author zhurz
 */
public abstract class BaseHibernateDAO<T extends BaseModel, QO extends BaseHibernateQO> {

	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	protected ApplicationContext applicationContext;

	private AutoBuildCriteriaDAO autoBuildCriteriaDAO;

	private AutoBuildCriteriaDAO getAutoBuildCriteriaDAO() {
		if (autoBuildCriteriaDAO == null && applicationContext != null)
			autoBuildCriteriaDAO = applicationContext.getBean(AutoBuildCriteriaDAO.class);
		if (autoBuildCriteriaDAO == null)
			throw new RuntimeException("AutoBuildCriteriaDAO尚未初始化");
		return autoBuildCriteriaDAO;
	}

	/**
	 * 得到当前会话
	 *
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 获得DAO对于的实体类
	 *
	 * @return
	 */
	abstract protected Class<T> getEntityClass();

	/**
	 * 查询实体完成
	 *
	 * @param qo   查询对象
	 * @param list 查询结果
	 */
	abstract protected void queryEntityComplete(QO qo, List<T> list);

	/**
	 * 将实体踢出hibernate会话缓存
	 *
	 * @param entity
	 */
	public void evictHibernateSession(Object entity) {
		getSession().evict(entity);
	}

	/**
	 * 清空hibernate会话缓存
	 */
	public void clearHibernateSession() {
		getSession().clear();
	}

	/**
	 * 将hibernate会话缓存里的实体更新到数据库
	 */
	public void flushHibernateSession() {
		getSession().flush();
	}


	/**
	 * 加载实体
	 *
	 * @param id   对象ID
	 * @param lock 是否锁定，使用LockMode.UPGRADE
	 * @return 持久化对象
	 */
	@SuppressWarnings("unchecked")
	protected T load(Serializable id, boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getSession().load(getEntityClass(), id, LockOptions.UPGRADE);
		} else {
			entity = (T) getSession().load(getEntityClass(), id);
		}
		return entity;
	}

	/**
	 * 获取实体
	 *
	 * @param id   对象ID
	 * @param lock 是否锁定，使用LockMode.UPGRADE
	 * @return 持久化对象
	 */
	@SuppressWarnings("unchecked")
	protected T get(Serializable id, boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getSession().get(getEntityClass(), id, LockOptions.UPGRADE);
		} else {
			entity = (T) getSession().get(getEntityClass(), id);
		}
		return entity;
	}

	/**
	 * 执行HQL语句
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public int executeHql(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();
	}

	/**
	 * HQL查询列表
	 *
	 * @param alias
	 * @param hql
	 * @param params
	 * @param <ASTYPE> 实体类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <ASTYPE> List<ASTYPE> hqlQueryList(Class<ASTYPE> alias, String hql, List<Object> params) {

		Query query = getSession().createQuery(hql);

		if (params != null)
			for (int i = 0; i < params.size(); i++)
				query.setParameter(i, params.get(i));

		query.setResultTransformer(new AliasToBeanResultTransformer(alias));

		return query.list();
	}

	/**
	 * HQL查询列表
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> hqlQueryList(String hql, List<Object> params) {
		return hqlQueryList(getEntityClass(), hql, params);
	}

	/**
	 * 查询第一个实体
	 *
	 * @param qo 查询对象
	 * @return
	 */
	public T queryFirst(QO qo) {
		List<T> list = queryList(qo);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/**
	 * 查询唯一实体
	 *
	 * @param qo 查询对象
	 * @return
	 */
	public T queryUnique(QO qo) {
		List<T> list = queryList(qo);
		if (list != null) {
			if (list.size() == 1)
				return list.get(0);
			else
				throw new RuntimeException("查询结果不唯一");
		}
		return null;
	}

	/**
	 * 查询实体列表
	 *
	 * @param qo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryList(QO qo) {
		Criteria criteria = createCriteria(qo);
		if (qo.getLimit().getStartIndex() != null && qo.getLimit().getStartIndex() >= 0)
			criteria.setFirstResult(qo.getLimit().getStartIndex());
		if (qo.getLimit().getPageSize() != null && qo.getLimit().getPageSize() >= 1) {
			criteria.setMaxResults(qo.getLimit().getPageSize());
		}
		List<T> list = criteria.list();
		queryEntityComplete(qo, list);
		return list;
	}

	/**
	 * HIBERNATE 的 order 属性
	 */
	protected static final String ORDER_ENTRIES = "orderEntries";

	/**
	 * 分页查询实体
	 *
	 * @param qo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination<T> queryPagination(QO qo) {

		LimitQuery limitQuery = qo.getLimit();

		Criteria criteria = createCriteria(qo);
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = criteriaImpl.getProjection();
		ResultTransformer transformer = criteriaImpl.getResultTransformer();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) MyBeanUtils.getFieldValue(criteriaImpl, ORDER_ENTRIES);
			MyBeanUtils.setFieldValue(criteriaImpl, ORDER_ENTRIES, new ArrayList());
		} catch (Exception e) {
			throw new RuntimeException("cannot read/write 'orderEntries' from CriteriaImpl", e);
		}

		int totalCount = 0;
		try {
			totalCount = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		} catch (Exception e) {
			throw new RuntimeException("查询总数异常", e);
		}

		Pagination<T> pagination = new Pagination(limitQuery.getPageNo(), limitQuery.getPageSize(), totalCount);
		if (totalCount < 1) {
			pagination.setList(new ArrayList());
			return pagination;
		}

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			criteria.setResultTransformer(transformer);
		}
		try {
			MyBeanUtils.setFieldValue(criteriaImpl, ORDER_ENTRIES, orderEntries);
		} catch (Exception e) {
			throw new RuntimeException("set 'orderEntries' to CriteriaImpl faild", e);
		}
		criteria.setFirstResult(pagination.getStartIndex());
		criteria.setMaxResults(pagination.getPageSize());
		List<T> list = criteria.list();
		queryEntityComplete(qo, list);
		pagination.setList(list);
		return pagination;
	}

	/**
	 * 保存实体
	 *
	 * @param entity
	 * @return
	 */
	public T save(T entity) {
		getSession().save(entity);
		return entity;
	}

	/**
	 * 批量保存实体
	 *
	 * @param entities
	 * @return
	 */
	public Collection<T> save(Collection<T> entities) {
		for (T entity : entities) {
			save(entity);
		}
		return entities;
	}

	/**
	 * 更新实体
	 *
	 * @param entity
	 * @return
	 */
	public T update(T entity) {
		getSession().update(entity);
		return entity;
	}

	/**
	 * 批量更新实体
	 *
	 * @param entities
	 * @return
	 */
	public Collection<T> update(Collection<T> entities) {
		for (T entity : entities) {
			update(entity);
		}
		return entities;
	}

	/**
	 * 保存或更新实体
	 *
	 * @param entity
	 * @return
	 */
	public T saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	/**
	 * 保存或更新实体
	 *
	 * @param entities
	 * @return
	 */
	public Collection<T> saveOrUpdate(Collection<T> entities) {
		for (T entity : entities) {
			saveOrUpdate(entity);
		}
		return entities;
	}

	/**
	 * 根据ID删除实体
	 *
	 * @param id
	 */
	void deleteById(Serializable id) {
		T t = get(id);
		if (t != null)
			getSession().delete(t);
	}

	/**
	 * 根据ID数组批量删除实体
	 *
	 * @param ids
	 */
	void deleteByIds(Serializable... ids) {
		for (Serializable id : ids) {
			deleteById(id);
		}
	}

	/**
	 * 根据ID获取实体
	 *
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return get(id, false);
	}

	/**
	 * 根据ID获取实体
	 *
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return load(id, false);
	}

	/**
	 * 总和查询
	 *
	 * @param propertyName
	 * @param qo
	 * @return
	 */
	public Double sumProperty(String propertyName, QO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<>());
		Number number = ((Number) criteria.setProjection(Projections.sum(propertyName)).uniqueResult());
		return number == null ? 0d : number.doubleValue();
	}

	/**
	 * 平均值查询
	 *
	 * @param propertyName
	 * @param qo
	 * @return
	 */
	public Double avgProperty(String propertyName, QO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<>());
		Number number = ((Number) criteria.setProjection(Projections.avg(propertyName)).uniqueResult());
		return number == null ? 0d : number.doubleValue();
	}

	/**
	 * 查询数量
	 *
	 * @param qo
	 * @return
	 */
	public Integer queryCount(QO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<>());
		return ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	/**
	 * 为Hibernate标准查询添加条件
	 *
	 * @param criteria
	 * @return
	 */
	abstract protected Criteria buildCriteria(Criteria criteria, QO qo);

	/**
	 * 创建Hibernate标准查询
	 *
	 * @param qo
	 * @return
	 */
	protected Criteria createCriteria(QO qo) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		return buildCriteriaOut(criteria, qo);
	}

	/**
	 * 构建标准查询对象（主要用于外部联表）
	 *
	 * @param criteria
	 * @param qo
	 * @return
	 */
	public Criteria buildCriteriaOut(Criteria criteria, QO qo) {
		buildBaseCriteria(criteria, qo);
		buildCriteria(criteria, qo);
		return criteria;
	}

	private Criteria buildBaseCriteria(Criteria criteria, QO qo) {
		if (qo != null) {
			if (this instanceof AutoBuildCriteriaDAO) {
				if (qo.getId() instanceof String) {
					if (StringUtils.isNotBlank((String) qo.getId()))
						criteria.add(Restrictions.eq("id", qo.getId()));
				} else if (qo.getId() != null) {
					criteria.add(Restrictions.eq("id", qo.getId()));
				}
			}
			// 自动处理QO注解
			if (!(this instanceof AutoBuildCriteriaDAO))
				getAutoBuildCriteriaDAO().forEntity(getEntityClass()).buildCriteriaOut(criteria, qo);
		}
		return criteria;
	}


}
