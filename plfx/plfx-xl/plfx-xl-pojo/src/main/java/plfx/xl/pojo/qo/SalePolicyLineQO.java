package plfx.xl.pojo.qo;

import java.util.Date;
import hg.common.component.BaseQo;

/**
 * @类功能说明：价格政策QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月18日上午10:08:19
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class SalePolicyLineQO extends BaseQo {
	
	/**
	 * 政策名称
	 */
	private String salePolicyLineName;
	
	/**
	 * 有效开始日期
	 */
	private Date startDate;
	
	/**
	 * 有效结束日期
	 */
	private Date endDate;
	
	
//	/**
//	 *适用线路类别城市编号
//	 */
//	private String endCityID;
//	
//	/**
//	 * 适用线路类别目的地所在省
//	 * 只供页面展现查询条件使用
//	 */
//	private String endProvinceID;
	
	/**
	 * 适用线路类别城市编号
	 */
	private String cityOfType;
	
	/**
	 * 适用线路类别省份编号
	 * 只供页面交互使用
	 */
	private String provinceOfType;
	
	/**
	 * 适用线路类别
	 */
	public Integer type;
	
	/**
	 *适用经销商
	 */
    private String  lineDealerID;
	
	/**
	 * 政策状态
	 */
	private Integer salePolicyLineStatus;
	
	/**
	 * 创建人
	 */
	private String  createName;
	
	/**
	 * 政策编号
	 */
	private String salePolicyLineNumber;
	
	/**
	 * 线路类别：1、国内线；2、国外线
	 * 只供页面交互使用
	 */
	private String domesticLine;
	

	/**
	 * 线路ID:该字段用于在创建价格政策的时候判断该价格政策是否
	 * 有使用的线路
	 */
	private String lineID;
	
	/**
	 * 是否查询最高优先级政策
	 */
	private Boolean maxPriority;
	
	/**
	 * 价格政策类型
	 */
	private Integer priceType;
	
	/**
	 * 是否隐藏
	 */
	private Boolean isHide;
	
	/**
	 * 当前日期价格政策是否在有效期时间内
	 */
	private Boolean isVaild;
	
	/**
	 * 按创建时间排序
	 */
	private Boolean createDateAsc = false;
	
	
	public SalePolicyLineQO(){
		super();
	}
	
	public SalePolicyLineQO(String id){
		super();
		setId(id);
	}

	public String getSalePolicyLineName() {
		return salePolicyLineName;
	}

	public void setSalePolicyLineName(String salePolicyLineName) {
		this.salePolicyLineName = salePolicyLineName;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLineDealerID() {
		return lineDealerID;
	}

	public void setLineDealerID(String lineDealerID) {
		this.lineDealerID = lineDealerID;
	}

	public Integer getSalePolicyLineStatus() {
		return salePolicyLineStatus;
	}

	public void setSalePolicyLineStatus(Integer salePolicyLineStatus) {
		this.salePolicyLineStatus = salePolicyLineStatus;
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

	public String getDomesticLine() {
		return domesticLine;
	}

	public void setDomesticLine(String domesticLine) {
		this.domesticLine = domesticLine;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public Boolean getMaxPriority() {
		return maxPriority;
	}

	public void setMaxPriority(Boolean maxPriority) {
		this.maxPriority = maxPriority;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Boolean getIsHide() {
		return isHide;
	}

	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

	public Boolean getIsVaild() {
		return isVaild;
	}

	public void setIsVaild(Boolean isVaild) {
		this.isVaild = isVaild;
	}

	public String getCityOfType() {
		return cityOfType;
	}

	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
	}

	public String getProvinceOfType() {
		return provinceOfType;
	}

	public void setProvinceOfType(String provinceOfType) {
		this.provinceOfType = provinceOfType;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	
	
	
}