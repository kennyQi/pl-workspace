package hg.common.component;

import hg.common.component.hibernate.HibernateBaseDao;
import hg.common.page.Pagination;
import hg.common.util.MyBeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;

/**
 * Hibernate基础查询DAO
 * @author zhurz
 *
 * @param <T>
 * @param <QO>
 */
public abstract class SimpleBaseDao<T extends BaseModel, QO extends BaseQo> extends HibernateBaseDao<T, Serializable> {

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
		if (pagination.getCondition() == null || !(pagination.getCondition() instanceof BaseQo)) {
			pagination.setCondition(new BaseQo());
		}
		Criteria criteria = createCriteria((QO) pagination.getCondition());
		return super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
	}
	
	private Criteria buildBaseCriteria(Criteria criteria, QO qo) {
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if (qo.getIdNotIn() != null && qo.getIdNotIn().length > 0) {
				Criterion[] idNotInCriterions = new Criterion[qo.getIdNotIn().length];
				for (int i = 0; i < qo.getIdNotIn().length; i++) {
					idNotInCriterions[i] = Restrictions.ne("id", qo.getIdNotIn()[i]);
				}
				criteria.add(Restrictions.and(idNotInCriterions));
			}
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
		buildBaseCriteria(criteria, qo);
		return buildCriteria(criteria, qo);
	}
	
	public Criteria buildCriteriaOut(Criteria criteria, QO qo) {
		buildBaseCriteria(criteria, qo);
		return buildCriteria(criteria, qo);
	}

	/**
	 * 为Hibernate标准查询添加条件
	 * 
	 * @param criteria
	 * @return
	 */
	abstract protected Criteria buildCriteria(Criteria criteria, QO qo);
	
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
		return super.get(id);
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
		for(T entity:entitys){
			save(entity);
		}
	}

	public void updateArray(T... entitys) {
		for(T entity:entitys){
			update(entity);
		}
	}

	public void deleteByIds(Serializable... ids) {
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

}
