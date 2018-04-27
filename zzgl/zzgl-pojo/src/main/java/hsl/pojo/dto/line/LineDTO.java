package hsl.pojo.dto.line;
import hsl.pojo.dto.BaseDTO;

import java.util.List;

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
	 * 图片文件夹，列表
	 */
	private List<LinePictureFolderDTO> folderList;
	
	/**
	 * 价格日历，每日价格列表
	 */
	private List<DateSalePriceDTO> dateSalePriceList;
	
	/**
	 * 
	 * 线路最低价格
	 */
	private Double minPrice;
	
	private int forSale;
	
	/**
	 * 线路首图图片列表
	 */
	private List<LineImageDTO> lineImageList;

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

	public List<LinePictureFolderDTO> getFolderList() {
		return folderList;
	}

	public void setFolderList(List<LinePictureFolderDTO> folderList) {
		this.folderList = folderList;
	}

	public List<DateSalePriceDTO> getDateSalePriceList() {
		return dateSalePriceList;
	}

	public void setDateSalePriceList(List<DateSalePriceDTO> dateSalePriceList) {
		this.dateSalePriceList = dateSalePriceList;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public int getForSale() {
		return forSale;
	}

	public void setForSale(int forSale) {
		this.forSale = forSale;
	}

	public List<LineImageDTO> getLineImageList() {
		return lineImageList;
	}

	public void setLineImageList(List<LineImageDTO> lineImageList) {
		this.lineImageList = lineImageList;
	}

	
	

}