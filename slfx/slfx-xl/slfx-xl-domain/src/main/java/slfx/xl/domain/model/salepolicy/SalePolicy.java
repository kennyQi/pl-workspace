package slfx.xl.domain.model.salepolicy;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import slfx.xl.domain.model.M;
import slfx.xl.domain.model.crm.LineDealer;
import slfx.xl.domain.model.line.Line;
import slfx.xl.pojo.command.salepolicy.CancelSalePolicyCommand;
import slfx.xl.pojo.command.salepolicy.CreateSalePolicyCommand;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.system.SalePolicyConstants;

/**
 * @类功能说明：销售政策
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午2:26:32
 */
@Entity
@Table(name = M.TABLE_PREFIX+"SALE_POLICY")
public class SalePolicy extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 销售政策名称
	 */
	@Column(name = "NAME", length = 64)
	private String salePolicyLineName;
	
	/**
	 * 线路
	 */
	@ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE })
	@JoinTable(
		name = M.TABLE_PREFIX + "SALE_POLICY_LINE",
		joinColumns = {@JoinColumn(name = "SALE_POLICY_ID")},
		inverseJoinColumns = {@JoinColumn(name = "LINE_ID")}
	)
	private List<Line> lines = new ArrayList<Line>();
	
	/**
	 *经销商
	 */
	@OneToOne
	@JoinColumn(name="LINE_DEALER_ID")
    private LineDealer  lineDealer;
	
	/**
	 * 优先级
	 */
	@Column(name="PRIORITY",columnDefinition = M.TYPE_NUM_COLUM)
	private  Integer priority;
	
	/**
	 * 有效开始日期
	 */
	@Column(name = "START_DATE", columnDefinition = M.DATE_COLUM)
	private Date startDate;
	
	/**
	 * 有效结束日期
	 */
	@Column(name = "END_DATE", columnDefinition = M.DATE_COLUM)
	private Date endDate;
	
	/**
	 * 价格政策类型
	 */
	@Column(name = "PRICE_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer priceType;
	public final static Integer PRICE_TYPE_ADULT = 1; // 成人价格
	public final static Integer PRICE_TYPE_CHILD = 2; // 儿童价格

	/**
	 * 是否隐藏不销售
	 */
	@Type(type = "yes_no")
	@Column(name = "HIDE")
	private Boolean hide;
	
	/**
	 * 价格是否上涨
	 */
	@Type(type = "yes_no")
	@Column(name = "RISE")
	private Boolean rise;

	/**
	 * 调整单位
	 */
	@Column(name = "UNIT", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer unit;
	public final static Integer UNIT_PERCENT = 1; // 百分比
	public final static Integer UNIT_YUAN = 2; // 元

	/**
	 * 价格政策调整幅度：如增加10元或者是增加10%
	 */
	@Column(name = "IMPROVE_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double improvePrice;

	/**
	 * 备注
	 */
	@Column(name = "DESCRIPTION", length = 512)
	private String description;
	
	/**
	 * 政策状态
	 */
	private  SalePolicyLineStatusInfo salePolicyLineStatus;
	
	/**
	 * 创建人
	 */
	@Column(name = "CREATE_NAME", length = 64)
	private String  createName;
	
	/**
	 * 政策创建时间
	 */
	@Column(name="CREATE_DATE")
	private Date salePolicyLineCreateDate;
	
	/**
	 * 政策编号
	 */
	@Column(name="NUMBER", length=64)
	private String salePolicyLineNumber;
	
	/**
	 * 最新快照
	 */
//	@OneToMany
//	@JoinTable(
//			name = M.TABLE_PREFIX + "SALE_POLICY_SALE_SNAPSHOT",
//			joinColumns = {@JoinColumn(name = "SALE_POLICY_ID")},
//			inverseJoinColumns = {@JoinColumn(name = "SNAPSHOT_ID")}
//		)
	@OneToMany(mappedBy="salePolicy")
	private List<SalePolicySnapshot> snapshotList;
	
	/**
	 * 取消原因
	 */
	@Column(name = "CANCLE_REMARK", length = 512)
	private String cancleRemark;
	
	/**
	 * 
	 * @方法功能说明：计算执行该价格政策后的销售价格
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月29日下午3:41:16
	 * @修改内容：
	 * @参数：@param price
	 * @参数：@return
	 * @return:Double
	 * @throws
	 */
	public Double countSalePrice(Double price){
		
		if(rise){
			if(unit == UNIT_PERCENT){
				price = price*(1 + improvePrice*0.01);
			}else{
				price = price + improvePrice;
			}
		}else{
			if(unit == UNIT_PERCENT){
				price = price*(1 - improvePrice*0.01);
			}else{
				price = price - improvePrice;
			}
		}
		return price;
	}
	
	/**
	 * @方法功能说明：创建销售政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18上午10:20:41
	 * @param command
	 */
	public void create(CreateSalePolicyCommand command,List<Line> lines,LineDealer dealer){
		setId(UUIDGenerator.getUUID());
		setSalePolicyLineCreateDate(new Date());
		// 政策名称
		setSalePolicyLineName(command.getName());
		// 优先级
		setPriority(command.getPriority());
		//有效期
		setStartDate(command.getStartDate());
		setEndDate(command.getEndDate());
		//价格政策类型
		setPriceType(command.getPriceType());
		//是否隐藏不销售
		setHide(command.getHide());
		//价格是否上涨
		setRise(command.getRise());
		//调整单位
		setUnit(command.getUnit());
		//价格政策调整幅度
		setImprovePrice(command.getImprovePrice());
		//备注
		setDescription(command.getDescription());
		//创建人
		setCreateName(command.getCreateName());
		//政策编号
		setSalePolicyLineNumber(command.getNumber());
		
		//适用经销商
		setLineDealer(dealer);
		//适用线路
		setLines(lines);
		//状态
		setSalePolicyLineStatus(new SalePolicyLineStatusInfo());
		
	}
	
	/**
	 * @方法功能说明: 发布
	 * @throws SlfxXlException 
	 */
	public void issue() throws SlfxXlException{
		if(this.salePolicyLineStatus.down() || this.salePolicyLineStatus.cancel())
			throw new SlfxXlException(SlfxXlException.SALE_POLICY_STATUS_NOT_NOTVALID,"价格政策已结束或已取消");
		this.salePolicyLineStatus.setSalePolicyLineStatus(SalePolicyConstants.SALE_SUCCESS);
	}
	
	/**
	 * @方法功能说明: 取消
	 * @throws SlfxXlException 
	 */
	public void cancel(CancelSalePolicyCommand command) throws SlfxXlException{
		//价格政策在已开始和已发布是才允许取消
		if(salePolicyLineStatus.getSalePolicyLineStatus() == SalePolicyConstants.SALE_START
				|| salePolicyLineStatus.getSalePolicyLineStatus() == SalePolicyConstants.SALE_SUCCESS){
			
			this.salePolicyLineStatus.setSalePolicyLineStatus(SalePolicyConstants.SALE_CANCEL);
			cancleRemark = command.getCancleRemark();
			
		}else{
			throw new SlfxXlException(SlfxXlException.SALE_POLICY_STATUS_NOT_NOTVALID,"价格政策已开始和已发布才允许取消");
		}
		
	}
	
	/**
	 * @方法功能说明: 更新状态
	 */
	public void updateStatus() {
		if(this.salePolicyLineStatus.cancel() || this.salePolicyLineStatus.down() 
				|| this.salePolicyLineStatus.release())
			return;
		
		Date now = new Date();//当前时间
		if(now.after(this.startDate) && now.before(this.endDate))
			this.salePolicyLineStatus.setSalePolicyLineStatus(SalePolicyConstants.SALE_START);
		if(now.after(this.endDate))
			this.salePolicyLineStatus.setSalePolicyLineStatus(SalePolicyConstants.SALE_DOWN);
	}
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getPriceType() {
		return priceType;
	}
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public Boolean getRise() {
		return rise;
	}
	public void setRise(Boolean rise) {
		this.rise = rise;
	}
	public Boolean getHide() {
		return hide;
	}
	public void setHide(Boolean hide) {
		this.hide = hide;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public List<SalePolicySnapshot> getSnapshotList() {
		if(null == this.snapshotList)
			this.snapshotList = new ArrayList<SalePolicySnapshot>(0);
		return snapshotList;
	}
	public void setSnapshotList(List<SalePolicySnapshot> snapshotList) {
		this.snapshotList = snapshotList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LineDealer getLineDealer() {
		return lineDealer;
	}
	public void setLineDealer(LineDealer lineDealer) {
		this.lineDealer = lineDealer;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Double getImprovePrice() {
		return improvePrice;
	}
	public void setImprovePrice(Double improvePrice) {
		this.improvePrice = improvePrice;
	}
	public SalePolicyLineStatusInfo getSalePolicyLineStatus() {
		return salePolicyLineStatus;
	}
	public void setSalePolicyLineStatus(
			SalePolicyLineStatusInfo salePolicyLineStatus) {
		this.salePolicyLineStatus = salePolicyLineStatus;
	}
	public String getSalePolicyLineName() {
		return salePolicyLineName;
	}
	public void setSalePolicyLineName(String salePolicyLineName) {
		this.salePolicyLineName = salePolicyLineName;
	}
	public Date getSalePolicyLineCreateDate() {
		return salePolicyLineCreateDate;
	}
	public void setSalePolicyLineCreateDate(Date salePolicyLineCreateDate) {
		this.salePolicyLineCreateDate = salePolicyLineCreateDate;
	}
	public String getSalePolicyLineNumber() {
		return salePolicyLineNumber;
	}
	public void setSalePolicyLineNumber(String salePolicyLineNumber) {
		this.salePolicyLineNumber = salePolicyLineNumber;
	}
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public String getCancleRemark() {
		return cancleRemark;
	}

	public void setCancleRemark(String cancleRemark) {
		this.cancleRemark = cancleRemark;
	}
	

}