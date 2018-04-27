package hg.pojo.command.warehousingnotice;

import hg.common.component.BaseCommand;
import hg.pojo.dto.warehousing.WarehousingNoticeDetailDTO;

import java.util.Date;
import java.util.List;

/**
 * @类功能说明：
 * @备注：
 * @类修改者：
 * @修改日期：2015-03-17 16:59:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-03-17 16:59:24
 */
public class ModifyWarehousingNoticeCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 是否生成入库单
	 */
	private Boolean generateWarehousingOrder;
	/**
	 * 入库仓库
	 */
	private String warehouseId;
	/**
	 * 通知单编号
	 */
	private String warehousingNoticeCode;
	/**
	 * 不含税总价格
	 */
	private Double totalPriceExclTax;
	/**
	 * 项目名称
	 */
	private String projectId;
	/**
	 * 物流费
	 */
	private Double totalLogisticCost;
	/**
	 * 入库单类型
	 */
	private Integer noticeType;
	/**
	 * 通知人
	 */
	private String noticeUser;
	/**
	 * 供应商
	 */
	private String supplierId;
	/**
	 * 通知时间
	 */
	private Date noticeDate;
	/**
	 * 采购单编号
	 */
	private String purchaseOrderCode;
	/**
	 * 总数量
	 */
	private Integer totalQuantity;

	
	private List<WarehousingNoticeDetailDTO> detailList;

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public String getNoticeUser() {
		return noticeUser;
	}

	public void setNoticeUser(String noticeUser) {
		this.noticeUser = noticeUser;
	}

	public Double getTotalLogisticCost() {
		return totalLogisticCost;
	}

	public void setTotalLogisticCost(Double totalLogisticCost) {
		this.totalLogisticCost = totalLogisticCost;
	}

	public Double getTotalPriceExclTax() {
		return totalPriceExclTax;
	}

	public void setTotalPriceExclTax(Double totalPriceExclTax) {
		this.totalPriceExclTax = totalPriceExclTax;
	}

	public String getWarehousingNoticeCode() {
		return warehousingNoticeCode;
	}

	public void setWarehousingNoticeCode(String warehousingNoticeCode) {
		this.warehousingNoticeCode = warehousingNoticeCode;
	}


	public Boolean getGenerateWarehousingOrder() {
		return generateWarehousingOrder;
	}

	public void setGenerateWarehousingOrder(Boolean generateWarehousingOrder) {
		this.generateWarehousingOrder = generateWarehousingOrder;
	}


	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<WarehousingNoticeDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<WarehousingNoticeDetailDTO> detailList) {
		this.detailList = detailList;
	}
}
