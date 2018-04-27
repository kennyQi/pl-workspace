package jxc.domain.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jxc.domain.model.M;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

/**
 * 运营方式表
 * @author liujz
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_SYETEM+"OPERATION_FORM")
public class OperationForm extends BaseModel {

	/**
	 * 运营方式名称
	 */
	@Column(name="OPERATION_FORM_NAME",length=20)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void createOperationForm(String name) {
		setId(UUIDGenerator.getUUID());
		setName(name);
	}
}
