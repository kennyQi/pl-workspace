package lxs.pojo.command.line;
import hg.common.component.BaseCommand;

import java.util.List;

import lxs.pojo.dto.line.DateSalePriceDTO;
import lxs.pojo.dto.line.LineBaseInfoDTO;
import lxs.pojo.dto.line.LinePayInfoDTO;
import lxs.pojo.dto.line.LineRouteInfoDTO;

/**
 * @类功能说明：新增线路command
 * @类修改者：
 * @修改日期：2015年1月30日上午9:26:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2015年1月30日上午9:26:10
 *
 */
@SuppressWarnings("serial")
public class CreateLineCommand extends BaseCommand{
	/**
	 * 基本信息
	 */
	private LineBaseInfoDTO baseInfo;
	/**
	 * 支付信息
	 */
	private LinePayInfoDTO payInfo;
	/**
	 * 路线信息
	 */
	private LineRouteInfoDTO routeInfo;
	/**
	 * 价格日历，每日价格列表
	 */
	private List<DateSalePriceDTO> dateSalePrice;

	public LineBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(LineBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public LinePayInfoDTO getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(LinePayInfoDTO payInfo) {
		this.payInfo = payInfo;
	}

	public LineRouteInfoDTO getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(LineRouteInfoDTO routeInfo) {
		this.routeInfo = routeInfo;
	}

		public List<DateSalePriceDTO> getDateSalePrice() {
		return dateSalePrice;
	}

	public void setDateSalePrice(List<DateSalePriceDTO> dateSalePrice) {
		this.dateSalePrice = dateSalePrice;
	}
	
	
}
