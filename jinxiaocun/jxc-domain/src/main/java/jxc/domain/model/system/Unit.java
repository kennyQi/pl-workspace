package jxc.domain.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;

import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateUnitCommand;
import hg.pojo.command.ModifyUnitCommand;
import hg.pojo.command.RemoveUnitCommand;

/**
 * 单位表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SYETEM + "UNIT")
public class Unit extends JxcBaseModel {
	
	/**
	*
	*/
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	/**
	 * 单位编码
	 */
	@Column(name = "UNIT_CODE", length = 2)
	private String unitCode;

	/**
	 * 单位名称
	 */
	@Column(name = "UNIT_NAME", length = 4)
	private String unitName;

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void createUnit(CreateUnitCommand command) {
		setId(UUIDGenerator.getUUID());
		setUnitName(command.getUnitName());
		setStatusRemoved(false);
		
		createDate = new Date();
	}

	public void modifyUnit(ModifyUnitCommand command) {
		setUnitName(command.getUnitName());

	}

	public void remove(RemoveUnitCommand command) {
		setId(command.getId());
		setStatusRemoved(true);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
