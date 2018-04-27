package hg.framework.common.base;

/**
 * 领域经理基类（所有对model的set操作都在此完成，service不碰model的任何set方法）
 *
 * @param <T> 领域对象
 * @author zhurz
 */
public abstract class BaseDomainManager<T extends BaseModel> {

	protected final T entity;

	public BaseDomainManager(T entity) {
		this.entity = entity;
	}

	public T get() {
		return entity;
	}

}
