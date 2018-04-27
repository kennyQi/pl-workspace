package hg.common.component;

import hg.common.page.Pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 基础查询接口
 * @author zhurz
 *
 * @param <T>
 * @param <QO>
 */
public interface BaseService<T extends BaseModel, QO extends BaseQo> {
	
	/**
	 * 查询唯一实体
	 * @param qo
	 * @return
	 */
	public T queryUnique(QO qo);
	
	/**
	 * 查询实体列表
	 * @param qo
	 * @return
	 */
	public List<T> queryList(QO qo);
	
	/**
	 * 分页查询实体
	 * @param pagination
	 * @return
	 */
	public Pagination queryPagination(Pagination pagination);
	
	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public T save(T entity);
	
	/**
	 * 批量保存实体
	 * 
	 * @param entitys
	 */
	public void saveArray(T... entitys);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public T update(T entity);
	
	/**
	 * 批量更新实体
	 * 
	 * @param entity
	 */
	public void updateArray(T... entitys);

	/**
	 * 根据ID删除实体
	 * 
	 * @param id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 根据ID数组批量删除实体
	 * @param id
	 */
	public void deleteByIds(Serializable... ids);
	
	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	public T get(Serializable id);

	/**
	 * 总和查询
	 * 
	 * @param propertyName
	 * @param qo
	 * @return
	 */
	public Double sumProperty(String propertyName, QO qo);

	/**
	 * 平均值查询
	 * 
	 * @param propertyName
	 * @param qo
	 * @return
	 */
	public Double avgProperty(String propertyName, QO qo);

	/**
	 * 查询数量
	 * 
	 * @param qo
	 * @return
	 */
	public Integer queryCount(QO qo);
}
