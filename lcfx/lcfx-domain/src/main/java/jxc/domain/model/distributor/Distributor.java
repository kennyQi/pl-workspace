package jxc.domain.model.distributor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import jxc.domain.model.M;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.distributor.CreateDistributorCommand;
import hg.pojo.command.distributor.ModifyDistributorCommand;

/**
 * 分销商
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_LCFX + "DISTRIBUTOR")
public class Distributor extends BaseModel {
	// 启用
	public static final int STATUS_ENABLE = 100;

	// 禁止
	public static final int STATUS_DISABLE = 101;

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 32)
	private String name;

	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 分销商编号
	 */
	@Column(name = "CODE", length = 4)
	private String code;

	/**
	 * 联系人
	 */
	@Column(name = "LINK_MAN", length = 16)
	private String linkMan;

	/**
	 * 联系电话
	 */
	@Column(name = "PHONE", length = 16)
	private String phone;

	/**
	 * 状态
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;

	public void create(CreateDistributorCommand command) {
		createDate = new Date();
		status = STATUS_ENABLE;
		setName(command.getName());
		phone = command.getPhone();
		linkMan = command.getLinkMan();

		
		code = command.getCode();
		setId(UUIDGenerator.getUUID());
	}

	public void modify(ModifyDistributorCommand command) {
		setName( command.getName() );
		setLinkMan( command.getLinkMan() );
		setPhone( command.getPhone() );
		
	}


	public Date getCreateDate() {
		return createDate;
	}

	public String getCode() {
		return code;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
