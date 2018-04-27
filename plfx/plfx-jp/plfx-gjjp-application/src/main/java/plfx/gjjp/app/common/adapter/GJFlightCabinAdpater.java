package plfx.gjjp.app.common.adapter;

import static plfx.jp.app.common.util.TimeUtils.convertMinutes;
import hg.common.util.DateUtil;
import plfx.api.client.api.v1.gj.dto.GJFlightCabinDTO;
import plfx.yxgjclient.pojo.param.Flight;

/**
 * @类功能说明：航班舱位组合适配器
 * @类修改者：
 * @修改日期：2015-7-29下午4:04:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-29下午4:04:02
 */
public class GJFlightCabinAdpater {

	/**
	 * @方法功能说明：转为API用的DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-29下午4:04:13
	 * @修改内容：
	 * @参数：@param flight
	 * @参数：@return
	 * @return:GJFlightCabinDTO
	 * @throws
	 */
	public GJFlightCabinDTO convertDTO(Flight flight) {
		GJFlightCabinDTO dto = new GJFlightCabinDTO();
		dto.setOrgCity(flight.getOrgCity());
		dto.setOrgTerm(flight.getOrgTerm());
		dto.setDstCity(flight.getDstCity());
		dto.setDstTerm(flight.getDstTerm());
		dto.setMarketingAirline(flight.getMarketingAirline());
		dto.setTicketingCarrier(flight.getTicketingCarrier());
		dto.setFlightNo(flight.getFlightNo());
		dto.setCarriageAirline(flight.getCarriageAirline());
		dto.setCarriageFlightno(flight.getCarriageFlightno());
		dto.setMealCode(flight.getMealCode());
		dto.setCabinCode(flight.getCabinCode());
		dto.setCabinSales(Integer.valueOf(flight.getCabinSales()));
		dto.setPlaneType(flight.getPlaneType());
		dto.setStartTime(DateUtil.parseDateTime(flight.getStartTime(), "yyyy-MM-dd HH:mm"));
		dto.setEndTime(DateUtil.parseDateTime(flight.getEndTime(), "yyyy-MM-dd HH:mm"));
		dto.setIsShare("1".equals(flight.getIsShare()) ? true : false);
		dto.setDuration(convertMinutes(flight.getDuration()));
		return dto;
	}

}
