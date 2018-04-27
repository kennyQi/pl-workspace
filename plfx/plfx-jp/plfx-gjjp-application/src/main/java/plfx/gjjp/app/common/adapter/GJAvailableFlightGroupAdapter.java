package plfx.gjjp.app.common.adapter;

import static plfx.jp.app.common.util.TimeUtils.convertMinutes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import plfx.api.client.api.v1.gj.dto.GJAvailableFlightGroupDTO;
import plfx.api.client.api.v1.gj.dto.GJAvailableFlightGroupDTO.PriceInfo;
import plfx.api.client.api.v1.gj.dto.GJFlightCabinDTO;
import plfx.yxgjclient.pojo.param.AvailJourney;
import plfx.yxgjclient.pojo.param.AvailableJourney;
import plfx.yxgjclient.pojo.param.Flight;

/**
 * @类功能说明：可用航班组合适配器
 * @类修改者：
 * @修改日期：2015-7-24下午6:03:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-24下午6:03:00
 */
public class GJAvailableFlightGroupAdapter {

	/**
	 * @方法功能说明：转换为API用的DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-24下午6:03:12
	 * @修改内容：
	 * @参数：@param availableJourney
	 * @参数：@return
	 * @return:GJAvailableFlightGroupDTO
	 * @throws
	 */
	public GJAvailableFlightGroupDTO convertDTO(AvailableJourney availableJourney) {

		AvailJourney tackoff = availableJourney.getTakeoffAvailJourney();
		AvailJourney back = availableJourney.getBackAvailJourney();

		GJAvailableFlightGroupDTO dto = new GJAvailableFlightGroupDTO();
		dto.setFlightCabinGroupToken(DigestUtils.md5Hex(availableJourney.getEncryptString()));
		dto.setTakeoffTotalDuration(convertMinutes(tackoff.getTotalDuration()) + convertMinutes(tackoff.getTransferTime()));
		if (back != null)
			dto.setBackTotalDuration(convertMinutes(back.getTotalDuration()) + convertMinutes(back.getTransferTime()));

		// 航班转换
		List<GJFlightCabinDTO> takeoffFlightCabins = new ArrayList<GJFlightCabinDTO>();
		List<GJFlightCabinDTO> backFlightCabins = null;
		for (Flight flight : tackoff.getFlights())
			takeoffFlightCabins.add(GJJPApiAdapter.flight.convertDTO(flight));
		if (back != null) {
			backFlightCabins = new ArrayList<GJFlightCabinDTO>();
			for (Flight flight : back.getFlights())
				backFlightCabins.add(GJJPApiAdapter.flight.convertDTO(flight));
		}
		dto.setTakeoffFlights(takeoffFlightCabins);
		dto.setBackFlights(backFlightCabins);

		// 价格信息
		Map<String, PriceInfo> priceInfoMap = new HashMap<String, PriceInfo>();
		dto.setPriceInfoMap(priceInfoMap);
		List<plfx.yxgjclient.pojo.param.PriceInfo> supplierPriceInfos = availableJourney.getPriceInfos();
		for (plfx.yxgjclient.pojo.param.PriceInfo supplierPriceInfo : supplierPriceInfos) {
			PriceInfo priceInfo = new PriceInfo();
			priceInfo.setTotalPrice(Double.valueOf(supplierPriceInfo.getTotalPrice()));
			priceInfo.setTotalTax(Double.valueOf(supplierPriceInfo.getTotalTax()));
			priceInfo.setPassType(supplierPriceInfo.getPassType());
			priceInfoMap.put(priceInfo.getPassType(), priceInfo);
		}
		return dto;
	}

}
