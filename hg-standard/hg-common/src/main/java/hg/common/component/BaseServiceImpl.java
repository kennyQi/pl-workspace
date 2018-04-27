package hg.common.component;

import hg.common.page.Pagination;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础查询Service抽象实现
 * @author zhurz
 *
 * @param <T>
 * @param <QO>
 * @param <DAO>
 */
@Transactional
public abstract class BaseServiceImpl<T extends BaseModel, QO extends BaseQo, DAO extends BaseDao<T, QO>> implements BaseService<T, QO> {
	
	abstract protected DAO getDao();
	
	@Autowired
	protected SimpleDao simpleDao;

	@Override
	@Transactional(readOnly=true)
	public T queryUnique(QO qo) {
		return getDao().queryUnique(qo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> queryList(QO qo) {
		return getDao().queryList(qo);
	}

	@Override
	@Transactional(readOnly=true)
	public Pagination queryPagination(Pagination pagination) {
		return getDao().queryPagination(pagination);
	}
	@Override
	public void deleteById(Serializable id) {
		getDao().deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public T get(Serializable id) {
		return getDao().get(id);
	}

	@Override
	public T save(T entity) {
		getDao().save(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		getDao().update(entity);
		return entity;
	}

	@Override
	public void saveArray(T... entitys) {
		getDao().saveArray(entitys);
	}

	@Override
	public void updateArray(T... entitys) {
		getDao().updateArray(entitys);
	}

	@Override
	public void deleteByIds(Serializable... ids) {
		getDao().deleteByIds(ids);
	}

	@Override
	@Transactional(readOnly = true)
	public Double sumProperty(String propertyName, QO qo) {
		return getDao().sumProperty(propertyName, qo);
	}

	@Override
	@Transactional(readOnly = true)
	public Double avgProperty(String propertyName, QO qo) {
		return getDao().avgProperty(propertyName, qo);
	}

	@Override
	public Integer queryCount(QO qo) {
		return getDao().queryCount(qo);
	}
	
}
