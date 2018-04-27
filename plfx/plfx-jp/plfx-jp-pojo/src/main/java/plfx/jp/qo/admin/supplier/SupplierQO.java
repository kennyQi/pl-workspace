
package plfx.jp.qo.admin.supplier;

import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 
 * @类功能说明：供应商QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:02:42
 * @版本：V1.0
 *
 */
public class SupplierQO extends BaseQo {
	
	
	private static final long serialVersionUID = -4607393398249743926L;
	

	private String name;
	private String code;
	private String number;
	private String status;
	/** 开始生效时间 */
	private Date beginTime;
	/** 结束生效时间 */
	private Date endTime;
	/**
	 * 排序条件
	 */
	private Boolean createDateAsc;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
