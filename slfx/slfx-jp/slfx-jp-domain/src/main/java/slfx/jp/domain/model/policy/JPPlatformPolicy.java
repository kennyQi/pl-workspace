package slfx.jp.domain.model.policy;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import slfx.jp.command.admin.policy.PolicyCommand;
import slfx.jp.domain.model.J;
import slfx.jp.domain.model.dealer.Dealer;
import slfx.jp.domain.model.supplier.Supplier;
import slfx.jp.pojo.system.PolicyConstants;

/**
 * 
 * @类功能说明：平台价格政策
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月5日上午9:36:36
 * @版本：V1.0
 *
*/

@Entity
@Table(name = J.TABLE_PREFIX + "POLICY")
public class JPPlatformPolicy extends BaseModel{
	
	private static final long serialVersionUID = 2115168437070L;
	
	/** 政策编号*/
	@Column(name="POLICY_NO", length=64)
	private String no;
	
	/** 政策名称*/
	@Column(name="POLICY_NAME", length=64)
	private String name;
	
	/** 供应商ID*/
	@Column(name="SUPP_ID", length=64)
	private String suppId;
	
	/** 经销商ID*/
	@Column(name="DEALER_ID", length=64)
	private String dealerId;
	
	/** 开始生效时间*/
	@Column(name="BEGIN_TIME", columnDefinition = J.DATE_COLUM)
	private Date beginTime;
	
	/** 结束生效时间*/
	@Column(name="END_TIME", columnDefinition = J.DATE_COLUM)
	private Date endTime;
	
	/** 价格政策*/
	@Column(name="PRICE_POLICY", columnDefinition = J.MONEY_COLUM)
	private Double pricePolicy;
	
	/** 价格百分比政策:以0.23的形式存在,即23%          */
	@Column(name="PERCENT_POLICY", columnDefinition = J.MONEY_COLUM)
	private Double percentPolicy;
	
	/** 状态
	 * 
    	未发布、           已发布 、                      已开始、           已下架、                 已取消
		0		   1			2		 3		    4	
	 * */
	@Column(name="STATUS", columnDefinition = J.CHAR_COLUM)
	private String status;
	
	/** 创建人 */
	@Column(name="CREATE_PERSION", length=64)
	private String createPersion;
	
	/** 创建时间*/
	@Column(name="CREATE_TIME", columnDefinition = J.DATE_COLUM)
	private Date createTime;
	
	/** 备注*/
	@Column(name="REMARK", length=4000)
	private String remark;
	
	/** 取消原因*/
	@Column(name="CANCLE_REASON", length=4000)
	private String cancleReason;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUPP_ID_FOR")
	private Supplier supplier;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEALER_ID_FOR")
	private Dealer dealer;
	
	

	public JPPlatformPolicy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JPPlatformPolicy(PolicyCommand command) {
		setId(UUIDGenerator.getUUID());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//使用时间作为政策编号
		setNo(sdf.format(date));
		setName(command.getName());
		setBeginTime(command.getBeginTime());
		setEndTime(command.getEndTime());
		setPricePolicy(command.getPricePolicy());
		setPercentPolicy(command.getPercentPolicy());
		setStatus(PolicyConstants.PRE_PUBLISH);
		setCreatePersion(command.getCreatePersion());
		setCreateTime(new Date());
		setRemark(command.getRemark());
	}
	
	public JPPlatformPolicy(PolicyCommand command,Supplier supplier,Dealer dealer) {
		setId(UUIDGenerator.getUUID());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//使用时间作为政策编号
		setNo(sdf.format(date));
		setName(command.getName());
		setBeginTime(command.getBeginTime());
		setEndTime(command.getEndTime());
		setPricePolicy(command.getPricePolicy());
		setPercentPolicy(command.getPercentPolicy());
		setStatus(PolicyConstants.PRE_PUBLISH);
		setCreatePersion(command.getCreatePersion());
		setCreateTime(new Date());
		setRemark(command.getRemark());
		setSupplier(supplier);
		setSuppId(supplier.getCode());
		setDealer(dealer);
		setDealerId(dealer.getCode());
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuppId() {
		return suppId;
	}

	public void setSuppId(String suppId) {
		this.suppId = suppId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
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

	public Double getPricePolicy() {
		return pricePolicy;
	}

	public void setPricePolicy(Double pricePolicy) {
		this.pricePolicy = pricePolicy;
	}

	public Double getPercentPolicy() {
		return percentPolicy;
	}

	public void setPercentPolicy(Double percentPolicy) {
		this.percentPolicy = percentPolicy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatePersion() {
		return createPersion;
	}

	public void setCreatePersion(String createPersion) {
		this.createPersion = createPersion;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCancleReason() {
		return cancleReason;
	}

	public void setCancleReason(String cancleReason) {
		this.cancleReason = cancleReason;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
}
