package hsl.pojo.command.yxjp.order;

import hg.common.component.BaseCommand;
import hsl.pojo.dto.yxjp.YXFlightDTO;
import hsl.pojo.dto.yxjp.YXLinkmanDTO;
import hsl.pojo.dto.yxjp.YXPassengerDTO;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.util.HSLConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 创建易行机票订单
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
public class CreateYXJPOrderCommand extends BaseCommand implements HSLConstants.FromType {

	/**
	 * 下单用户
	 */
	private String fromUserId;

	/**
	 * 来源标识：0 mobile , 1  pc
	 * 默认为PC
	 *
	 * @see HSLConstants.FromType
	 */
	private Integer fromType = FROM_TYPE_PC;

	/**
	 * 航班信息（页面上为加密字符串，传递到后台解密，防止篡改）
	 */
	private YXFlightDTO flightInfo;

	/**
	 * 乘客
	 */
	private List<YXPassengerDTO> passengers;

	/**
	 * 联系人
	 */
	private YXLinkmanDTO linkman;

	public void check() {

		// 检查航班信息
		if (flightInfo == null || flightInfo.getCabinInfo() == null || flightInfo.getBaseInfo() == null || flightInfo.getPolicyInfo() == null)
			throw new ShowMessageException("缺少航班信息");

		// 检查乘客
		if (passengers == null || passengers.size() == 0)
			throw new ShowMessageException("缺少乘客信息");

		Set<String> idNoSet = new HashSet<String>();
		Set<String> nameSet = new HashSet<String>();

		for (YXPassengerDTO passenger : passengers) {
			if (!HSLConstants.Traveler.ID_TYPE_SFZ.equals(passenger.getIdType()))
				throw new ShowMessageException("乘客仅支持身份证");
			if (!HSLConstants.Traveler.TYPE_ADULT.equals(passenger.getType()))
				throw new ShowMessageException("乘客仅支持成人");
			if (idNoSet.contains(passenger.getIdNo()))
				throw new ShowMessageException("乘客证件号重复");
			if (nameSet.contains(passenger.getName()))
				throw new ShowMessageException("不支持有相同姓名的乘客在一个订单");

			idNoSet.add(passenger.getIdNo());
			nameSet.add(passenger.getName());
		}

	}

	public Integer getFromType() {
		if (fromType == null)
			fromType = FROM_TYPE_PC;
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public YXFlightDTO getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(YXFlightDTO flightInfo) {
		this.flightInfo = flightInfo;
	}

	public List<YXPassengerDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<YXPassengerDTO> passengers) {
		this.passengers = passengers;
	}

	public YXLinkmanDTO getLinkman() {
		return linkman;
	}

	public void setLinkman(YXLinkmanDTO linkman) {
		this.linkman = linkman;
	}
}
