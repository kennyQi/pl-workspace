package pay.record.domain.model.authip;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import pay.record.domain.base.M;

/**
 * 
 * @类功能说明：授权可以访问的ip地址
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月27日下午2:49:54
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_PAYRECORD + "_AUTH")
public class AuthIP extends BaseModel{
	
	/**
	 * 来源项目IP
	 */
	@Column(name = "FROMPROJECTIP", length = 32)
	public String fromProjectIP;
	
	/**
	 * 状态,是否启用
	 * 0:未启用，1：启用
	 */
	@Column(name = "AUTHIP_STATUS", columnDefinition = M.CHAR_COLUM)
	private String status;
	
	/**
	 * 创建日期
	 */
	@Column(name = "AUTHIP_CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public String getFromProjectIP() {
		return fromProjectIP;
	}

	public void setFromProjectIP(String fromProjectIP) {
		this.fromProjectIP = fromProjectIP;
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
	
}
