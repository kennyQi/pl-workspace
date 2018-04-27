package hg.pojo.qo;

import hg.common.component.BaseQo;
import java.util.Date;

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
public class WarehousingNoticeQo extends BaseQo {
	private static final long serialVersionUID = 1L;
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
	 * 项目名称
	 */
	private String projectId;
	/**
	 * 供应商
	 */
	private String supplierId;
	/**
	 * 通知时间
	 */
	private Date noticeDateBegin;
	private Date noticeDateEnd;
	/**
	 * 采购单编号
	 */
	private String purchaseOrderCode;

	public Boolean getGenerateWarehousingOrder() {
		return generateWarehousingOrder;
	}

	public void setGenerateWarehousingOrder(Boolean generateWarehousingOrder) {
		this.generateWarehousingOrder = generateWarehousingOrder;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehousingNoticeCode() {
		return warehousingNoticeCode;
	}

	public void setWarehousingNoticeCode(String warehousingNoticeCode) {
		this.warehousingNoticeCode = warehousingNoticeCode;
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

	public Date getNoticeDateBegin() {
		return noticeDateBegin;
	}

	public void setNoticeDateBegin(Date noticeDateBegin) {
		this.noticeDateBegin = noticeDateBegin;
	}

	public Date getNoticeDateEnd() {
		return noticeDateEnd;
	}

	public void setNoticeDateEnd(Date noticeDateEnd) {
		this.noticeDateEnd = noticeDateEnd;
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}
}
