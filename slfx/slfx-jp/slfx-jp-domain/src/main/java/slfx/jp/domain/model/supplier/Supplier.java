
package slfx.jp.domain.model.supplier;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.jp.command.admin.supplier.SupplierCommand;
import slfx.jp.domain.model.J;
import slfx.jp.pojo.system.SupplierConstants;

/**
 * 
 * @类功能说明：  供应商的model类，暂时只有一个字段，后期在进行修改
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月5日下午3:37:44
 * @版本：V1.0
 *
 */
@Entity
@Table(name = J.TABLE_PREFIX + "SUPPLIER")
public class Supplier extends BaseModel {

	private static final long serialVersionUID = 7036394414245823326L;
	
	/**  供应商名称*/
	@Column(name="SUPPLIER_NAME", length=64)
	private String name;
	
	/**  供应商代码*/
	@Column(name="SUPPLIER_CODE", length=64)
	private String code;
	
	/**  供应商编号*/
	@Column(name="SUPPLIER_NUMBER", length=64)
	private String number;

	/**启用状态*/
	@Column(name="SUPPLIER_STATUS", columnDefinition = J.CHAR_COLUM)
	private String status;
	
	/** 创建日期*/
	@Column(name="SUPPLIER_CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;
	
	public Supplier() {
		super();
	}
	
	public Supplier(SupplierCommand command) {
		setId(UUIDGenerator.getUUID());
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
	
	public void updateStatus(SupplierCommand command){
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
