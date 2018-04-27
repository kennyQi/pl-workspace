package plfx.xl.domain.model.crm;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.xl.domain.model.M;
import plfx.xl.pojo.command.dealer.AuditLineDealerCommand;
import plfx.xl.pojo.command.dealer.CreateLineDealerCommand;
import plfx.xl.pojo.command.dealer.ModifyLineDealerCommand;
import plfx.xl.pojo.system.DealerConstants;

/**
 * 
 * @类功能说明：线路经销商
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日上午10:37:20
 * 
 */
@Entity
@Table(name = M.TABLE_PREFIX + "DEALER")
public class LineDealer extends BaseModel {

private static final long serialVersionUID = -8947365429318665816L;
	
	/**  经销商名称*/
	@Column(name="DEALER_NAME", length=64)
	private String name;

	/**  经销商代码*/
	@Column(name="DEALER_CODE", length=64)
	private String code;
	
	/** 经销商商城访问地址：http://www.aa.com */
	@Column(name="DEALER_URL", length=255)
	private String dealerUrl;
	
	/**启用状态*/
	@Column(name="SUPPLIER_STATUS", columnDefinition = M.CHAR_COLUM)
	private String status;
	
	/** 创建日期*/
	@Column(name="SUPPLIER_CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	public LineDealer() {
		super();
	}
	
	public LineDealer(CreateLineDealerCommand command) {
		setId(UUIDGenerator.getUUID());
		setName(command.getName());
		setCode(command.getCode());
		setDealerUrl(command.getDealerUrl());
		setCreateDate(new Date());
		setStatus(DealerConstants.PRE_USE);
	}
	
	public void update(ModifyLineDealerCommand command) {
		setName(command.getName());
		setCode(command.getCode());
		setDealerUrl(command.getDealerUrl());
	}
	
	public void updateStatus(AuditLineDealerCommand command){
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

	public String getDealerUrl() {
		return dealerUrl;
	}

	public void setDealerUrl(String dealerUrl) {
		this.dealerUrl = dealerUrl;
	}
	
}