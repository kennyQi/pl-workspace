package slfx.mp.domain.model.platformpolicy;

import static slfx.mp.spi.common.MpEnumConstants.SalePolicySnapshotStatusEnum.STATUS_CANCEL;
import static slfx.mp.spi.common.MpEnumConstants.SalePolicySnapshotStatusEnum.STATUS_DEPLOY;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import slfx.mp.command.admin.CreatePlatformPolicyCommand;
import slfx.mp.command.admin.ModifyPlatformPolicyCommand;
import slfx.mp.command.admin.StartPlatformPolicyCommand;
import slfx.mp.command.admin.StopPlatformPolicyCommand;
import slfx.mp.domain.model.M;

/**
 * 经销商价格政策快照
 * 
 * @author Administrator
 */
@Entity(name="salePolicySnapshot_mp")
@Table(name = M.TABLE_PREFIX + "SALE_POLICY_SNAPSHOT")
public class SalePolicySnapshot extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 经销商政策id
	 */
	@Column(name = "POLICY_ID", length = 64)
	private String policyId;
	/**
	 * 经销商政策名称
	 */
	@Column(name = "POLICY_NAME", length = 64)
	private String policyName;
	/**
	 * 供应商id
	 */
	@Column(name = "SUPPLIER_ID", length = 64)
	private String supplierId;
	/**
	 * 商品筛选方式 1按省市区 2按价格 3按景点名称
	 */
	@Column(name = "FILTER_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer filterType;
	// --------------- 按省市区 ---------------
	@Column(name = "FILTER_PROV_CODE", length = 64)
	private String filterProvCode;
	@Column(name = "FILTER_CITY_CODE", length = 64)
	private String filterCityCode;
	@Column(name = "FILTER_AREA_CODE", length = 64)
	private String filterAreaCode;
	// --------------- 按价格 ---------------
	@Column(name = "FILTER_PRICE_MIN", columnDefinition = M.MONEY_COLUM)
	private Double filterPriceMin;
	@Column(name = "FILTER_PRICE_MAX", columnDefinition = M.MONEY_COLUM)
	private Double filterPriceMax;
	// --------------- 按景点名称 ---------------
	/**
	 * 景点id集
	 */
	@Column(name = "SCENIC_SPOT_IDS", length = 4000)
	private String scenicSpotIds;
	/**
	 * 景点名称集(冗余字段，格式：,某某景点,某某景点,)
	 */
	@Column(name = "SCENIC_SPOT_NAMES", length = 4000)
	private String scenicSpotNames;
	/**
	 * 经销商id
	 */
	@Column(name = "DEALER_ID", length = 64)
	private String dealerId;
	/**
	 * 政策生效时间
	 */
	@Column(name = "BEGIN_DATE", columnDefinition = M.DATE_COLUM)
	private Date beginDate;
	/**
	 * 政策结束时间
	 */
	@Column(name = "END_DATE", columnDefinition = M.DATE_COLUM)
	private Date endDate;
	/**
	 * 调价类型
	 */
	@Column(name = "MODIFY_PRICE_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer modifyPriceType;
	/**
	 * 调价值
	 */
	@Column(name = "MODIFY_PROCE_VALUE", columnDefinition = M.DOUBLE_COLUM)
	private Double modifyProceValue;
	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 1024)
	private String remark;
	/**
	 * 取消原因
	 */
	@Column(name = "CANCEL_REMARK", length = 1024)
	private String cancelRemark;
	/**
	 * 优先级
	 */
	@Column(name = "LEVEL_", columnDefinition = M.NUM_COLUM)
	private Integer level;
	/**
	 * 快照日期
	 */
	@Column(name = "SNAPSHOT_DATE", columnDefinition = M.DATE_COLUM)
	private Date snapshotDate;
	/**
	 * 创建人姓名
	 */
	@Column(name = "OPERATOR_NAME", length = 64)
	private String operatorName;
	/**
	 * 状态
	 */
	@Embedded
	private SalePolicySnapshotStatus status;
	
	public void createPlatformPolicy(CreatePlatformPolicyCommand command) {
		setId(UUIDGenerator.getUUID());
		if (StringUtils.isBlank(command.getPolicyId()))
			setPolicyId(getId());
		else
			setPolicyId(command.getPolicyId());
		setSupplierId(command.getCommandId());
		setFilterType(command.getFilterType());
		setDealerId(command.getDealerId());
		setBeginDate(command.getBeginDate());
		setEndDate(command.getEndDate());
		setModifyPriceType(command.getModifyPriceType());
		setModifyProceValue(command.getModifyProceValue());
		setRemark(command.getRemark());
		if (command.getSnapshotDate() == null)
			setSnapshotDate(new Date());
		else
			setSnapshotDate(command.getSnapshotDate());
		setFilterProvCode(command.getScenicSpotProviceCode());
		setFilterCityCode(command.getScenicSpotCityCode());
		setFilterAreaCode(command.getScenicSpotAreaCode());
		setScenicSpotIds(command.getScenicSpotIds());
		setFilterPriceMax(command.getHighPrice());
		setFilterPriceMin(command.getLowPrice());
		setOperatorName(command.getOperatorName());
		setPolicyName(command.getPolicyName());
		setLevel(command.getLevel());
		getStatus().setLastSnapshot(true);
	}

	public SalePolicySnapshot modifyPlatformPolicy(ModifyPlatformPolicyCommand command) {
		SalePolicySnapshot policySnapshot = null;
		try {
			policySnapshot = (SalePolicySnapshot) BeanUtils.cloneBean(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (policySnapshot == null) return null;
		
		policySnapshot.setId(UUIDGenerator.getUUID());
		getStatus().setLastSnapshot(false);
		policySnapshot.getStatus().setLastSnapshot(true);
		
		Date now = new Date();
		policySnapshot.setId(UUIDGenerator.getUUID());
		policySnapshot.setSnapshotDate(now);
		policySnapshot.getStatus().setStatus(command.getState());
		if (command.getState().intValue() == STATUS_CANCEL.intValue()) {
			policySnapshot.getStatus().setCancel(true);
		} else if (command.getState().intValue() == STATUS_DEPLOY.intValue()) {
			policySnapshot.getStatus().setDeploy(true);
			if (policySnapshot.getBeginDate() != null && policySnapshot.getEndDate() != null) {
				if (now.getTime() >= policySnapshot.getBeginDate().getTime() && now.getTime() < policySnapshot.getEndDate().getTime()) {
					policySnapshot.getStatus().setStart(true);
				} else if (now.getTime() >= policySnapshot.getEndDate().getTime()) {
					policySnapshot.getStatus().setPast(true);
				}
			}
		}
		
		return policySnapshot;
	}

	public void stopPlatformPolicy(StopPlatformPolicyCommand command) {
		getStatus().setLastSnapshot(false);
		getStatus().setCancel(true);
		getStatus().setStatus(STATUS_CANCEL);
		setCancelRemark(command.getCancelInfo());
	}
	
	public void startPlatformPolicy(StartPlatformPolicyCommand command) {
		getStatus().setDeploy(true);
		getStatus().setStatus(STATUS_DEPLOY);
		// 已开始
		if (new Date().getTime() >= getBeginDate().getTime()) {
			getStatus().setStart(true);
		}
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getFilterType() {
		return filterType;
	}

	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}

	public String getFilterProvCode() {
		return filterProvCode;
	}

	public void setFilterProvCode(String filterProvCode) {
		this.filterProvCode = filterProvCode;
	}

	public String getFilterCityCode() {
		return filterCityCode;
	}

	public void setFilterCityCode(String filterCityCode) {
		this.filterCityCode = filterCityCode;
	}

	public String getFilterAreaCode() {
		return filterAreaCode;
	}

	public void setFilterAreaCode(String filterAreaCode) {
		this.filterAreaCode = filterAreaCode;
	}

	public Double getFilterPriceMin() {
		return filterPriceMin;
	}

	public void setFilterPriceMin(Double filterPriceMin) {
		this.filterPriceMin = filterPriceMin;
	}

	public Double getFilterPriceMax() {
		return filterPriceMax;
	}

	public void setFilterPriceMax(Double filterPriceMax) {
		this.filterPriceMax = filterPriceMax;
	}

	public String getScenicSpotIds() {
		return scenicSpotIds;
	}

	public void setScenicSpotIds(String scenicSpotIds) {
		this.scenicSpotIds = scenicSpotIds;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getModifyPriceType() {
		return modifyPriceType;
	}

	public void setModifyPriceType(Integer modifyPriceType) {
		this.modifyPriceType = modifyPriceType;
	}

	public Double getModifyProceValue() {
		return modifyProceValue;
	}

	public void setModifyProceValue(Double modifyProceValue) {
		this.modifyProceValue = modifyProceValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public SalePolicySnapshotStatus getStatus() {
		if (status == null) {
			status = new SalePolicySnapshotStatus();
		}
		return status;
	}

	public void setStatus(SalePolicySnapshotStatus status) {
		this.status = status;
	}

	public String getScenicSpotNames() {
		return scenicSpotNames;
	}

	public void setScenicSpotNames(String scenicSpotNames) {
		this.scenicSpotNames = scenicSpotNames;
	}

}