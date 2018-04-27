package jxc.domain.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jxc.domain.model.M;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

/**
 * 项目类型表
 * @author liujz
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_SYETEM+"PROJECT_TYPE")
public class ProjectType extends BaseModel{
	
	/**
	 * 项目类型名称
	 */
	@Column(name="PROJECT_TYPE_NAME",length=30)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void createProjectType(String name) {
		setId(UUIDGenerator.getUUID());
		setName(name);
	}

}
