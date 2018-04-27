
package slfx.jp.domain.model.dealer;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.jp.command.admin.dealer.DealerCommand;
import slfx.jp.domain.model.J;
import slfx.jp.pojo.system.DealerConstants;

/**
 * 
 * @类功能说明： 经销商的model类，暂时只有一个字段，后期在进行修改
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月5日下午3:37:15
 * @版本：V1.0
 *
 */
@Entity
@Table(name = J.TABLE_PREFIX + "DEALER")
public class Dealer extends BaseModel {

	private static final long serialVersionUID = -8947365429318665816L;
	
	/**  经销商名称*/
	@Column(name="DEALER_NAME", length=64)
	private String name;

	/**  经销商代码*/
	@Column(name="DEALER_CODE", length=64)
	private String code;
	
	/**启用状态*/
	@Column(name="SUPPLIER_STATUS", columnDefinition = J.CHAR_COLUM)
	private String status;
	
	/** 创建日期*/
	@Column(name="SUPPLIER_CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;
	
	public Dealer() {
		super();
	}
	
	public Dealer(DealerCommand command) {
		setId(UUIDGenerator.getUUID());
		setName(command.getName());
		setCode(command.getCode());
		setCreateDate(new Date());
		setStatus(DealerConstants.PRE_USE);
	}
	
	public void update(DealerCommand command) {
		setName(command.getName());
		setCode(command.getCode());
	}
	
	public void updateStatus(DealerCommand command){
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
	
}
