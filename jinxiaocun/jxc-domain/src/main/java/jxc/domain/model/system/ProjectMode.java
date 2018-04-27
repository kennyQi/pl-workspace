package jxc.domain.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jxc.domain.model.M;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

/**
 * 项目模式
 * @author liujz
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_SYETEM+"PROJECT_MODE")
public class ProjectMode extends BaseModel {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 项目模式名称
	 */
	@Column(name="PROJECT_MODE_NAME",length=20)
	private String name;

	public void createMode(String name) {
		setId(UUIDGenerator.getUUID());
		setName(name);
	}
}
