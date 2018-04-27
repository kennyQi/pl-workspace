package hg.framework.common.base;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 模型基类
 *
 * @param <T> 主键类型
 * @author zhurz
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseModel<T extends Serializable> extends BaseObject implements Serializable {

	@Id
	private T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
}
