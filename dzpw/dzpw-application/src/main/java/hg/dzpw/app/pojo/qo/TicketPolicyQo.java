package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;
import hg.dzpw.pojo.common.util.StringFilterUtil;

import java.util.Date;

/**
 * @类功能说明: 联票政策QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 下午5:10:11
 * @版本：V1.0
 */
public class TicketPolicyQo extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 联票名称
	 */
	private String ticketPolicyName;
	private Boolean ticketPolicyNameLike = false;
	
	/**
	 * 票务类型
	 */
	private Integer ticketPolicyType;

	/**
	 * 代码
	 */
	private String key;

	/**
	 * 联票政策状态
	 */
	private Integer[] status;

	/**
	 * 政策类型
	 */
	private Integer[] type;

	private ScenicSpotQo scenicSpotQo;
	/**
	 * 包含景点
	 */
	private String scenicSpotStr;
	private boolean scenicFetchAble;

	/**
	 * 是否查询套票下的单票
	 */
	private boolean singleTicketPoliciesFetchAble = false;
	/**
	 * (查询套票下的单票时)是否查询parent
	 */
	private boolean parentTicketPolicyAble = false;
	/**
	 * 是否查询的是套票
	 */
	private boolean groupTicketPolicyAble = true;
	/**
	 * 创建时间(排序：>0 asc <0 desc)
	 */
	private int createDateSort;

	/**
	 * 修改时间(排序：>0 asc <0 desc)
	 */
	private int modifyDateSort;

	/**
	 * 创建开始时间
	 */
	private Date createDateBegin;

	/**
	 * 创建结束时间
	 */
	private Date createDateEnd;

	/**
	 * 销量 最小
	 */
	private Integer soldQtyMin;

	/**
	 * 销量 最大
	 */
	private Integer soldQtyMax;

	/**
	 * 基准价查询最小值
	 */
	private Double priceMin;

	/**
	 * 基准价查询最大值
	 */
	private Double priceMax;

	/**
	 * 是否被逻辑删除
	 */
	private Boolean removed = false;

	/**
	 * 修改时间开始
	 */
	private Date modifyDateBegin;

	/**
	 * 修改时间截止
	 */
	private Date modifyDateEnd;

	private Integer pageSize;
	private Integer pageNum;

	private TicketPolicyQo parentQo;
	
	/**
	 * 销售时间
	 */
	private Date saleDate;

	public TicketPolicyQo() {
	}

	public TicketPolicyQo(String id) {
		super.setId(id);
	}

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName == null ? null
				: StringFilterUtil.reverseString(ticketPolicyName.trim());
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key == null ? null : StringFilterUtil.reverseString(key.trim());
	}

	public Integer[] getStatus() {
		return status;
	}

	public void setStatus(Integer... status) {
		this.status = status;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Boolean getTicketPolicyNameLike() {
		return ticketPolicyNameLike;
	}

	public void setTicketPolicyNameLike(Boolean ticketPolicyNameLike) {
		this.ticketPolicyNameLike = ticketPolicyNameLike;
	}

	public ScenicSpotQo getScenicSpotQo() {
		return scenicSpotQo;
	}

	public void setScenicSpotQo(ScenicSpotQo scenicSpotQo) {
		this.scenicSpotQo = scenicSpotQo;
	}

	public Integer[] getType() {
		return type;
	}

	public void setType(Integer... type) {
		this.type = type;
	}

	public boolean isSingleTicketPoliciesFetchAble() {
		return singleTicketPoliciesFetchAble;
	}

	public void setSingleTicketPoliciesFetchAble(
			boolean singleTicketPoliciesFetchAble) {
		this.singleTicketPoliciesFetchAble = singleTicketPoliciesFetchAble;
	}

	public boolean isGroupTicketPolicyAble() {
		return groupTicketPolicyAble;
	}

	public void setGroupTicketPolicyAble(boolean groupTicketPolicyAble) {
		this.groupTicketPolicyAble = groupTicketPolicyAble;
	}

	public boolean isScenicFetchAble() {
		return scenicFetchAble;
	}

	public void setScenicFetchAble(boolean scenicFetchAble) {
		this.scenicFetchAble = scenicFetchAble;
	}

	public String getScenicSpotStr() {
		return scenicSpotStr;
	}

	public void setScenicSpotStr(String scenicSpotStr) {
		this.scenicSpotStr = scenicSpotStr;
	}

	public int getCreateDateSort() {
		return createDateSort;
	}

	public void setCreateDateSort(int createDateSort) {
		this.createDateSort = createDateSort;
	}

	public TicketPolicyQo getParentQo() {
		return parentQo;
	}

	public void setParentQo(TicketPolicyQo parentQo) {
		this.parentQo = parentQo;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Integer getSoldQtyMin() {
		return soldQtyMin;
	}

	public void setSoldQtyMin(Integer soldQtyMin) {
		this.soldQtyMin = soldQtyMin;
	}

	public Integer getSoldQtyMax() {
		return soldQtyMax;
	}

	public void setSoldQtyMax(Integer soldQtyMax) {
		this.soldQtyMax = soldQtyMax;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public Double getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Double priceMin) {
		this.priceMin = priceMin;
	}

	public Double getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(Double priceMax) {
		this.priceMax = priceMax;
	}

	public Date getModifyDateBegin() {
		return modifyDateBegin;
	}

	public void setModifyDateBegin(Date modifyDateBegin) {
		this.modifyDateBegin = modifyDateBegin;
	}

	public Date getModifyDateEnd() {
		return modifyDateEnd;
	}

	public void setModifyDateEnd(Date modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public int getModifyDateSort() {
		return modifyDateSort;
	}

	public void setModifyDateSort(int modifyDateSort) {
		this.modifyDateSort = modifyDateSort;
	}

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public boolean isParentTicketPolicyAble() {
		return parentTicketPolicyAble;
	}

	public void setParentTicketPolicyAble(boolean parentTicketPolicyAble) {
		this.parentTicketPolicyAble = parentTicketPolicyAble;
	}
	
}