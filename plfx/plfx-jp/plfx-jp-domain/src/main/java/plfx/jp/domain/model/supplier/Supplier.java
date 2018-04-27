package plfx.jp.domain.model.supplier;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.jp.command.admin.supplier.SupplierCommand;
import plfx.jp.domain.model.J;
import plfx.jp.pojo.system.SupplierConstants;

/**
 * @类功能说明：供应商的model类，暂时只有一个字段，后期在进行修改
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午5:49:18
 * @版本：V1.0
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX + "SUPPLIER")
public class Supplier extends BaseModel {

	/** 供应商名称 */
	@Column(name = "SUPPLIER_NAME", length = 64)
	private String name;

	/** 供应商代码 */
	@Column(name = "SUPPLIER_CODE", length = 64)
	private String code;
	
	/** 供应商编号 */
	@Column(name = "SUPPLIER_NUMBER", length = 64)
	private String number;

	/** 启用状态 */
	@Column(name = "SUPPLIER_STATUS", columnDefinition = J.CHAR_COLUM)
	private String status;

	/** 创建日期 */
	@Column(name = "SUPPLIER_CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;

	public Supplier() {
		super();
	}

	public Supplier(SupplierCommand command) {
//		setId(UUIDGenerator.getUUID());
		setId(command.getCode());
		setName(command.getName());
		setCode(command.getCode());
		setNumber(command.getNumber());
		setCreateDate(new Date());
		setStatus(SupplierConstants.PRE_USE);
	}

	public void update(SupplierCommand command) {
		setName(command.getName());
		setCode(command.getCode());
		setNumber(command.getNumber());
	}

	public void updateStatus(SupplierCommand command) {
		setStatus(command.getStatus());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
