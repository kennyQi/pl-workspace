package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.dto.yxjp.YXFlightDTO;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 易行机票订单航班信息（快照）
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "ORDER_FLIGHT")
public class YXJPOrderFlight extends BaseModel {

	/**
	 * 航班基本信息
	 */
	@Embedded
	private YXJPOrderFlightBaseInfo baseInfo;

	/**
	 * 航班政策信息
	 */
	@Embedded
	private YXJPOrderFlightPolicyInfo policyInfo;

	public YXJPOrderFlightBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new YXJPOrderFlightBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(YXJPOrderFlightBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public YXJPOrderFlightPolicyInfo getPolicyInfo() {
		if (policyInfo == null)
			policyInfo = new YXJPOrderFlightPolicyInfo();
		return policyInfo;
	}

	public void setPolicyInfo(YXJPOrderFlightPolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {
		/**
		 * 创建航班
		 *
		 * @param dto          下单command中的航班DTO
		 * @param orgCityName  出发城市名称
		 * @param destCityName 到达城市名称
		 * @return
		 */
		public YXJPOrderFlight create(YXFlightDTO dto, String orgCityName, String destCityName) {
			setId(UUIDGenerator.getUUID());

			// 基础信息
			getBaseInfo().setDepartAirport(dto.getBaseInfo().getOrgCityName());
			getBaseInfo().setArrivalAirport(dto.getBaseInfo().getDstCityName());
			getBaseInfo().setOrgCity(dto.getBaseInfo().getOrgCity());
			getBaseInfo().setOrgCityName(StringUtils.isNotBlank(orgCityName) ? orgCityName : dto.getBaseInfo().getOrgCityName());
			getBaseInfo().setDstCity(dto.getBaseInfo().getDstCity());
			getBaseInfo().setDstCityName(StringUtils.isNotBlank(destCityName) ? destCityName : dto.getBaseInfo().getDstCityName());
			getBaseInfo().setDepartTerm(dto.getBaseInfo().getDepartTerm());
			getBaseInfo().setArrivalTerm(dto.getBaseInfo().getArrivalTerm());
			getBaseInfo().setAirCompanyName(dto.getBaseInfo().getAirCompanyName());
			getBaseInfo().setAirCompanyShortName(dto.getBaseInfo().getAirCompanyShotName());
			getBaseInfo().setAirComp(dto.getBaseInfo().getAirComp());
			getBaseInfo().setFlightNo(dto.getBaseInfo().getFlightno());
			getBaseInfo().setPlaneType(dto.getBaseInfo().getPlaneType());
			getBaseInfo().setStartTime(dto.getBaseInfo().getStartTime());
			getBaseInfo().setEndTime(dto.getBaseInfo().getEndTime());
			getBaseInfo().setCabinDiscount(Integer.valueOf(dto.getCabinInfo().getCabinDiscount()));
			getBaseInfo().setCabinCode(dto.getCabinInfo().getCabinCode());
			getBaseInfo().setCabinName(dto.getCabinInfo().getCabinName());
			getBaseInfo().setCabinType(Integer.valueOf(dto.getCabinInfo().getCabinType()));

			// 政策信息
			getPolicyInfo().setIbePrice(dto.getPolicyInfo().getIbePrice());
			getPolicyInfo().setBuildFee(dto.getPolicyInfo().getBuildFee());
			getPolicyInfo().setOilFee(dto.getPolicyInfo().getOilFee());
			getPolicyInfo().setEncryptString(dto.getPolicyInfo().getEncryptString());
			getPolicyInfo().setWorkTime(dto.getPolicyInfo().getWorkTime());
			getPolicyInfo().setRestWorkTime(dto.getPolicyInfo().getRestWorkTime());
			getPolicyInfo().setWorkReturnTime(dto.getPolicyInfo().getWorkReturnTime());
			getPolicyInfo().setRestReturnTime(dto.getPolicyInfo().getRestReturnTime());
			getPolicyInfo().setTickType(dto.getPolicyInfo().getTickType());
			getPolicyInfo().setRefund(dto.getPolicyInfo().getRefund());
			getPolicyInfo().setInvalid(dto.getPolicyInfo().getInvalid());
			getPolicyInfo().setIndemnity(dto.getPolicyInfo().getIndemnity());
			getPolicyInfo().setPlcid(String.valueOf(dto.getPolicyInfo().getPlcid()));
			getPolicyInfo().setMemo(dto.getPolicyInfo().getMemo());
			getPolicyInfo().setSupplierPrice(dto.getPolicyInfo().getSingleTotalPrice());
			getPolicyInfo().setPayPrice(dto.getPolicyInfo().getSinglePlatTotalPrice());

			return YXJPOrderFlight.this;
		}
	}
}
