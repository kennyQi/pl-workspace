package jxc.domain.model.distributor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.util.CodeUtil;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreatePaymentMethodCommand;
import hg.pojo.command.CreateUnitCommand;
import hg.pojo.command.ModifyPaymentMethodCommand;
import hg.pojo.command.ModifyUnitCommand;
import hg.pojo.command.RemovePaymentMethodCommand;
import hg.pojo.command.RemoveUnitCommand;
import hg.pojo.command.mileOrder.CreateMileOrderCommand;
import hg.pojo.command.mileOrder.ImportMileOrderCommand;
import hg.pojo.command.mileOrder.ModifyMileOrderCommand;
import hg.system.model.auth.AuthUser;

/**
 * 里程订单
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_LCFX + "MILE_ORDER")
public class MileOrder extends JxcBaseModel {

	// 待审核
	public static final int STATUS_NO_CHECK = 200;

	// 审核通过
	public static final int STATUS_CHECK_PASS = 201;

	// 审核不通过
	public static final int STATUS_CHECK_NO_PASS = 202;

	// 提交南航
	public static final int STATUS_TO_CSAIR = 203;

	// 南航成功
	public static final int STATUS_CSAIR_SUCCEED = 204;

	// 南航失败
	public static final int STATUS_CSAIR_ERROR = 205;

	/**
	 * 分销商
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;

	/**
	 * 订单号
	 */
	@Column(name = "ORDER_CODE", length = 32)
	private String orderCode;

	/**
	 * 流水号
	 */
	@Column(name = "FLOW_CODE", length = 32)
	private String flowCode;

	/**
	 * 南航卡号
	 */
	@Column(name = "CSAIR_CARD", length = 32)
	private String csairCard;

	/**
	 * 南航姓名
	 */
	@Column(name = "CSAIR_NAME", length = 16)
	private String csairName;

	/**
	 * 份数
	 */
	@Column(name = "NUM", columnDefinition = M.NUM_COLUM)
	private Integer num;
	/**
	 * 数量
	 */
	@Column(name = "count", columnDefinition = M.NUM_COLUM)
	private Integer  count;

	/**
	 * 支付时间
	 */
	@Column(name = "PAY_DATE", columnDefinition = M.DATE_COLUM)
	private Date payDate;

	/**
	 * 导入时间
	 */
	@Column(name = "IMPORT_DATE", columnDefinition = M.DATE_COLUM)
	private Date importDate;

	/**
	 * 状态
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;

	/**
	 * 审核人
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHECK_PERSON_ID")
	private AuthUser checkPerson;

	/**
	*审核时间
	*/
	@Column(name = "CHECK_DATE", columnDefinition = M.DATE_COLUM)
	private Date checkDate;
	
	/**
	*南航处理时间
	*/
	@Column(name = "TO_CSAIR_DATE", columnDefinition = M.DATE_COLUM)
	private Date toCsairDate;
	
	/**
	*南航处理结果信息
	*/
	@Column(name = "CSAIR_INFO", length = 64)
	private String csairInfo;
	
	/**
	*南航返回处理时间
	*/
	@Column(name = "CSAIR_RETURN_DATE", columnDefinition = M.DATE_COLUM)
	private Date csairReturnDate;






	public void create(CreateMileOrderCommand command, ImportMileOrderCommand importMileOrderCommand, String distributorCode) {

		setStatusRemoved(false);
		setId(UUIDGenerator.getUUID());

		status = STATUS_NO_CHECK;

		Distributor d = new Distributor();
		d.setId(importMileOrderCommand.getDistributorId());
		this.setDistributor(d);

		this.setOrderCode(command.getOrderCode());
		this.setCsairCard(command.getCsairCard());
		this.setCsairName(command.getCsairName());
		this.setNum(command.getNum());
		this.setPayDate(command.getPayDate());
		this.setImportDate(new Date());

		flowCode = CodeUtil.getMileOrderFlowCode(distributorCode);

	}

	public void modify(ModifyMileOrderCommand command) {
	}

	// public void remove(RemoveMileOrderCommand command) {
	//
	// }

	public Distributor getDistributor() {
		return distributor;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getCsairCard() {
		return csairCard;
	}

	public String getCsairName() {
		return csairName;
	}

	public Integer getNum() {
		return num;
	}

	public Date getPayDate() {
		return payDate;
	}

	public Date getImportDate() {
		return importDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setCsairCard(String csairCard) {
		this.csairCard = csairCard;
	}

	public void setCsairName(String csairName) {
		this.csairName = csairName;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public AuthUser getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(AuthUser checkPerson) {
		this.checkPerson = checkPerson;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getToCsairDate() {
		return toCsairDate;
	}

	public void setToCsairDate(Date toCsairDate) {
		this.toCsairDate = toCsairDate;
	}

	public String getCsairInfo() {
		return csairInfo;
	}

	public void setCsairInfo(String csairInfo) {
		this.csairInfo = csairInfo;
	}

	public Date getCsairReturnDate() {
		return csairReturnDate;
	}

	public void setCsairReturnDate(Date csairReturnDate) {
		this.csairReturnDate = csairReturnDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
