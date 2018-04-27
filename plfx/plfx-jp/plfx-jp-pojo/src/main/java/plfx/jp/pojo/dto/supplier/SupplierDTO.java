package plfx.jp.pojo.dto.supplier;

import java.util.Date;

import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：供应商DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午2:52:09
 * @版本：V1.0
 *
 */
public class SupplierDTO extends BaseJpDTO {
	
	private static final long serialVersionUID = -7152346889038413691L;
	/**
	 * 供应商代码
	 */
	private String code;
	/**
	 * 供应商名称
	 */
	private String name;
	/**
	 * 供应商编号
	 */
	private String number;
	/**
	 * 供应商状态
	 */
	private String status;
	/**
	 * 供应商创建时间
	 */
	private Date createDate;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
		
	
}
