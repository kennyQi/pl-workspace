package lxs.pojo.dto.line;
import java.util.List;

import lxs.pojo.BaseDTO;
/**
 * @类功能说明：线路DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月02日下午4:55:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineDTO extends BaseDTO{
	
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
	private List<DateSalePriceDTO> dateSalePriceList;
	
	/**
	 * 
	 * @return
	 */
	private Integer minprice;

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

	public List<DateSalePriceDTO> getDateSalePriceList() {
		return dateSalePriceList;
	}

	public void setDateSalePriceList(List<DateSalePriceDTO> dateSalePriceList) {
		this.dateSalePriceList = dateSalePriceList;
	}

	public Integer getMinprice() {
		return minprice;
	}

	public void setMinprice(Integer minprice) {
		this.minprice = minprice;
	}

}