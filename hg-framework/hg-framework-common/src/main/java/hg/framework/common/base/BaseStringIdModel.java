package hg.framework.common.base;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 基础领域模型（String作为主键）
 *
 * @author zhurz
 */
@MappedSuperclass
@SuppressWarnings("serial")
@AttributeOverride(name = "id", column = @Column(name = "ID", length = 64))
public abstract class BaseStringIdModel extends BaseModel<String> {

}
