package hg.common.component;

import hg.common.component.hibernate.HibernateBaseDao;
import hg.common.component.hibernate.MyResultTransformer;
import hg.common.page.Pagination;
import hg.common.util.MyBeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Hibernate基础查询DAO
 * @author zhurz
 *
 * @param <T>
 * @param <QO>
 */
public abstract class BaseDao<T extends BaseModel, QO extends BaseQo> extends HibernateBaseDao<T, Serializable> {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private CommonDao commonDao;
	private CommonDao getCommonDao() {
		if (commonDao == null && applicationContext != null)
			commonDao = applicationContext.getBean(CommonDao.class);
		if (commonDao == null)
			throw new RuntimeException("CommonDao尚未初始化");
		return commonDao;
	}

	/**
	 * 查询唯一对象
	 * 
	 * @param qo
	 * @return
	 */
	public T queryUnique(QO qo) {
		List<T> list = queryList(qo, 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询对象列表
	 * 
	 * @param qo
	 * @return
	 */
	public List<T> queryList(QO qo) {
		return queryList(qo, 0, null);
	}

	/**
	 * 查询实体列表
	 * 
	 * @param qo
	 * @param maxSize		最大获取数量
	 * @return
	 */
	public List<T> queryList(QO qo, Integer maxSize){
		return queryList(qo, 0, maxSize);
	}
	
	/**
	 * 查询实体列表
	 * 
	 * @param qo
	 * @param offset		偏移量
	 * @param maxSize		最大获取数量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryList(QO qo, Integer offset, Integer maxSize) {
		Criteria criteria = createCriteria(qo);
		if (offset != null && offset >= 0) {
			criteria.setFirstResult(offset);
		}
		if (maxSize != null && maxSize >= 1) {
			criteria.setMaxResults(maxSize);
		}
		return criteria.list();
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
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		int totalCount = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return totalCount;
	}

	/**
	 * 分页查询对象
	 * 
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {
		if (!(pagination.getCondition() instanceof BaseQo)) {
			pagination.setCondition(null);
		}
		Criteria criteria = createCriteria((QO) pagination.getCondition());
		Pagination resultPagination = super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize(), pagination.isCheckPassLastPageNo());
		resultPagination.setCheckPassLastPageNo(pagination.isCheckPassLastPageNo());
		resultPagination.setCondition(pagination.getCondition());
		return resultPagination;
	}

	@SuppressWarnings("unchecked")
	public <ASTYPE> List<ASTYPE> hqlQueryList(Class<ASTYPE> alias, String hql,
			List<Object> params) {

		Query query = getSession().createQuery(hql);

		if (params != null)
			for (int i = 0; i < params.size(); i++)
				query.setParameter(i, params.get(i));

		query.setResultTransformer(new AliasToBeanResultTransformer(alias));

		return query.list();
	}

	public Pagination hqlQueryPagination(Class<?> alias,
			String hql, String countHql, Integer pageNo, Integer pageSize,
			Object condition, List<Object> params) {

		Long totalCount = 0L;

		Query countQuery = getSession().createQuery(countHql);

		for (int i = 0; i < params.size(); i++) {
			countQuery.setParameter(i, params.get(i));
		}
		totalCount = (Long) countQuery.uniqueResult();

		Pagination pagination = new Pagination(pageNo, pageSize,
				totalCount.intValue());

		if (totalCount > 0) {
			
			Query query = getSession().createQuery(hql);
			
			for (int i = 0; i < params.size(); i++)
				query.setParameter(i, params.get(i));

			query.setResultTransformer(new AliasToBeanResultTransformer(alias));
			query.setFirstResult(pagination.getFirstResult());
			query.setMaxResults(pageSize);
			
			List<?> list = query.list();
			pagination.setList(list);
		}

		if (pagination.getList() == null)
			pagination.setList(new ArrayList<Object>());
		if (pagination.getCondition() == null)
			pagination.setCondition(condition);

		return pagination;
	}

	private Criteria buildBaseCriteria(Criteria criteria, QO qo) {
		if (qo != null) {
			if (this instanceof CommonDao) {
				if (StringUtils.isNotBlank(qo.getId())) {
					criteria.add(Restrictions.eq("id", qo.getId()));
				}
				if (qo.getIdNotIn() != null && qo.getIdNotIn().length > 0) {
					criteria.add(Restrictions.and(eachNeProperty(qo.getIdNotIn(), "id")));
				}
				if (qo.getIds() != null && qo.getIds().size() > 0) {
					criteria.add(Restrictions.in("id", qo.getIds()));
				}
			}
			// 自动处理QO注解
			if (!(this instanceof CommonDao))
				getCommonDao().buildCriteriaOut(criteria, qo);
		}
		return criteria;
	}

	/**
	 * 创建Hibernate标准查询
	 * 
	 * @param qo
	 * @return
	 */
	protected Criteria createCriteria(QO qo){
		Criteria criteria = super.getSession().createCriteria(getEntityClass());
		buildIfProjectionResultCriteria(criteria, qo);
		buildBaseCriteria(criteria, qo);
		buildCriteria(criteria, qo);
		return criteria;
	}
	
	public Criteria buildCriteriaOut(Criteria criteria, QO qo) {
		buildCriteria(criteria, qo);
		buildBaseCriteria(criteria, qo);
		buildIfProjectionResultCriteria(criteria, qo);
		return criteria;
	}

	/**
	 * 为Hibernate标准查询添加条件
	 * 
	 * @param criteria
	 * @return
	 */
	abstract protected Criteria buildCriteria(Criteria criteria, QO qo);
	
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
	 * 根据HQL查询列表
	 * 
	 * @param hql
	 * @param offset
	 * @param size
	 * @param params
	 * @return
	 */
	public List<?> find(String hql, int offset, int size, Object... params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.setFirstResult(offset);
		query.setMaxResults(size);
		return query.list();		
	}

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param cntHql
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	protected Pagination findPagination(String hql, String cntHql, int pageNo, int pageSize, Object... values) {
		Long totalCount = (Long) findUnique(cntHql, values);
		List<?> list = createQuery(hql, values).setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
		Pagination pagination = new Pagination(pageNo, pageSize, totalCount.intValue(), list);
		return pagination;
	}
	
	public void save(T entity) {
		super.save(entity);
	}

	public void saveOrUpdate(T entity) {
		super.saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public T merge(T entity) {
		return (T) super.getSession().merge(entity);
	}

	public void update(T entity) {
		super.update(entity);
	}

	public void delete(T entity) {
		super.delete(entity);
	}

	public void deleteById(Serializable id) {
		T entity = get(id);
		if(entity != null){
			super.delete(get(id));
		}
	}

	public T get(Serializable id) {
		T entity = null;
		try {
			entity = super.get(id);
		} catch (Exception e) { }
		return entity;
	}
	
	public T load(Serializable id) {
		return super.load(id);
	}

	public void lockUpgrade(T entity) {
		super.getSession().buildLockRequest(LockOptions.UPGRADE).lock(entity);
	}

	public void evict(T entity) {
		super.evict(entity);
	}

	public void saveArray(T... entitys) {
		for (T entity : entitys) {
			save(entity);
		}
	}

	public void saveList(List<T> list) {
		for (T entity : list) {
			save(entity);
		}
	}

	public void updateArray(T... entitys) {
		for (T entity : entitys) {
			update(entity);
		}
	}

	public void updateList(List<T> list) {
		for (T entity : list) {
			update(entity);
		}
	}

	public void deleteByIds(Serializable... ids) {
		for(Serializable id:ids){
			deleteById(id);
		}
	}
	
	public void deleteByIds(List<Serializable> ids) {
		for(Serializable id:ids){
			deleteById(id);
		}
	}

	public void flush() {
		super.getSession().flush();
	}

	public void clear() {
		super.getSession().clear();
	}

	public Double sumProperty(String propertyName, QO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.sum(propertyName)).uniqueResult());
		return number == null ? 0d : number.doubleValue();
	}

	public Double avgProperty(String propertyName, QO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.avg(propertyName)).uniqueResult());
		return number == null ? 0d : number.doubleValue();
	}

	@SuppressWarnings("unchecked")
	public T maxProperty(String propertyName, QO qo, Class<T> clazz) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		return ((T) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
	}
	
	/**
	 * 遍历数组条件（代替IN查询）
	 * 
	 * @param objs
	 * @param propertyName
	 * @return
	 */
	protected Criterion[] eachEqProperty(Object[] objs, String propertyName) {
		Criterion[] criterions = new Criterion[objs.length];
		for (int i = 0; i < objs.length; i++) {
			criterions[i] = Restrictions.eq(propertyName, objs[i]);
		}
		return criterions;
	}
	
	/**
	 * 遍历数组条件（代替NOT IN查询）
	 * 
	 * @param objs
	 * @param propertyName
	 * @return
	 */
	protected Criterion[] eachNeProperty(Object[] objs, String propertyName) {
		Criterion[] criterions = new Criterion[objs.length];
		for (int i = 0; i < objs.length; i++) {
			criterions[i] = Restrictions.ne(propertyName, objs[i]);
		}
		return criterions;
	}
	
	/**
	 * 构建如果是指定字段的查询且必须指定别名
	 * 
	 * @param criteria
	 * @param qo
	 */
	protected void buildIfProjectionResultCriteria(Criteria criteria, QO qo) {
		if (qo.getProjectionPropertys() != null && qo.getProjectionPropertys().length > 0) {
			if (criteria instanceof CriteriaImpl) {
				ProjectionList projectionList = Projections.projectionList();
				for (String property : qo.getProjectionPropertys()) {
					projectionList.add(Projections.property(property), property);
				}
				criteria.setProjection(projectionList);
				criteria.setResultTransformer(new MyResultTransformer(getEntityClass()));
			} else if (criteria instanceof Subcriteria && qo.getAlias() != null) {
				StringBuilder aliasSb = new StringBuilder("");
				StringBuilder propertySb = new StringBuilder("");
				Criteria preCriteria = criteria;
				while (preCriteria instanceof Subcriteria) {
					Subcriteria currentSubcriteria = (Subcriteria) preCriteria;
					aliasSb.insert(0, currentSubcriteria.getAlias());
					propertySb.insert(0, currentSubcriteria.getPath());
					preCriteria = currentSubcriteria.getParent();
					if (preCriteria instanceof Subcriteria) {
						aliasSb.insert(0, ".");
						propertySb.insert(0, ".");
					}
				}
				String alias = aliasSb.toString();
				String propertyPath = propertySb.toString();
				ProjectionList projectionList = Projections.projectionList();
				if (preCriteria instanceof CriteriaImpl) {
					CriteriaImpl criteriaImpl = (CriteriaImpl) preCriteria;
					if (criteriaImpl.getProjection() != null) {
						projectionList = (ProjectionList) criteriaImpl.getProjection();
					}
					for (String property : qo.getProjectionPropertys()) {
						projectionList.add(Projections.property(alias + "." + property), propertyPath + "." + property);
					}
					ResultTransformer oldRtf = criteriaImpl.getResultTransformer();
					criteriaImpl.setProjection(projectionList);
					if (oldRtf != null) {
						criteriaImpl.setResultTransformer(oldRtf);
					}
				}
			}
		}
	}
}
