package slfx.xl.pojo.dto.salepolicy;

import java.util.Date;
import java.util.List;

import slfx.xl.pojo.dto.BaseXlDTO;
import slfx.xl.pojo.dto.LineDealerDTO;
import slfx.xl.pojo.dto.line.LineDTO;

/**
 * 
 * @类功能说明：销售政策DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月17日下午2:26:32
 * 
 */
@SuppressWarnings("serial")
public class SalePolicyLineDTO extends BaseXlDTO{
	/**
	 * 政策编号
	 */
	private String salePolicyLineNumber;
	
	/**
	 * 优先级
	 */
	private   Integer priority;
	
	/**
	 * 销售政策名称
	 */
	private String salePolicyLineName;
	
	/**
	 * 生效开始日期
	 */
	private Date startDate;
	
	/**
	 * 生效结束日期
	 */
	private Date endDate;
	
	/**
	 * 价格政策类型
	 */
	private Integer priceType;

	/**
	 * 是否价格上涨
	 */
	private Boolean rise;

	/**
	 * 是否隐藏不销售
	 */
	private Boolean hide;

	/**
	 * 调整单位
	 */
	private Integer unit;

	/**
	 * 价格政策调整幅度：如增加10元或者是增加10%
	 */
	private Double improvePrice;

	/**
	 * 备注
	 */
	private String description;
	
	/**
	 *线路基本信息
	 */
	 private List<LineDTO> lineList;
	 
	 /**
	 *经销商
	 */
	 private LineDealerDTO  lineDealer;
	 
	 /**
	 * 政策状态
	 */
	private SalePolicyLineStatusInfoDTO   salePolicyLineStatus;
	
	/**
	 * 创建人
	 */
	private String  createName;
	
	/**
	 * 政策创建时间
	 */
	private Date salePolicyLineCreateDate;
	
	/**
	 * 取消原因
	 */
	private String cancleRemark;
	

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LineDealerDTO getLineDealer() {
		return lineDealer;
	}

	public void setLineDealer(LineDealerDTO lineDealer) {
		this.lineDealer = lineDealer;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getSalePolicyLineNumber() {
		return salePolicyLineNumber;
	}

	public void setSalePolicyLineNumber(String salePolicyLineNumber) {
		this.salePolicyLineNumber = salePolicyLineNumber;
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

	public Double getImprovePrice() {
		return improvePrice;
	}

	public void setImprovePrice(Double improvePrice) {
		this.improvePrice = improvePrice;
	}

	public SalePolicyLineStatusInfoDTO getSalePolicyLineStatus() {
		return salePolicyLineStatus;
	}

	public void setSalePolicyLineStatus(
			SalePolicyLineStatusInfoDTO salePolicyLineStatus) {
		this.salePolicyLineStatus = salePolicyLineStatus;
	}

	public List<LineDTO> getLineList() {
		return lineList;
	}

	public void setLineList(List<LineDTO> lineList) {
		this.lineList = lineList;
	}

	public String getCancleRemark() {
		return cancleRemark;
	}

	public void setCancleRemark(String cancleRemark) {
		this.cancleRemark = cancleRemark;
	}


	
	
	

}