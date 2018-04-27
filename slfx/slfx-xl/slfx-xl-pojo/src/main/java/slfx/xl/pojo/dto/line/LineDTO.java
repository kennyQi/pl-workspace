package slfx.xl.pojo.dto.line;

import java.util.List;

import slfx.xl.pojo.dto.BaseXlDTO;
import slfx.xl.pojo.dto.LineSupplierDTO;
import slfx.xl.pojo.dto.price.DateSalePriceDTO;

/**
 * 
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
public class LineDTO extends BaseXlDTO {
	
	/**
	 * 线路基本信息
	 */
	private LineBaseInfoDTO baseInfo;

	/**
	 * 线路支付信息
	 */
	private LinePayInfoDTO payInfo;
	
	/**
	 * 线路状态
	 */
	private LineStatusInfoDTO statusInfo;
	
	/**
	 * 线路行程信息
	 */
	private LineRouteInfoDTO routeInfo;

    /**
	 * 线路供应商
	 */
	private LineSupplierDTO lindSupplier;
	/**
	 * 价格日历
	 */
	private List<DateSalePriceDTO> dateSalePriceList;
	/**
	 * 最新快照id
	 */
	private String  lineSnapshotId;
	
	/**
	 * 线路最低价格
	 */
	private Double minPrice;
	
	/**
	 * 线路首图图片列表
	 */
	private List<LineImageDTO> lineImageList;
	
	public List<DateSalePriceDTO> getDateSalePriceList() {
		return dateSalePriceList;
	}

	public void setDateSalePriceList(List<DateSalePriceDTO> dateSalePriceList) {
		this.dateSalePriceList = dateSalePriceList;
	}

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

	public LineStatusInfoDTO getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(LineStatusInfoDTO statusInfo) {
		this.statusInfo = statusInfo;
	}

	public LineRouteInfoDTO getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(LineRouteInfoDTO routeInfo) {
		this.routeInfo = routeInfo;
	}

	public LineSupplierDTO getLindSupplier() {
		return lindSupplier;
	}

	public void setLindSupplier(LineSupplierDTO lindSupplier) {
		this.lindSupplier = lindSupplier;
	}

	public String getLineSnapshotId() {
		return lineSnapshotId;
	}

	public void setLineSnapshotId(String lineSnapshotId) {
		this.lineSnapshotId = lineSnapshotId;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public List<LineImageDTO> getLineImageList() {
		return lineImageList;
	}

	public void setLineImageList(List<LineImageDTO> lineImageList) {
		this.lineImageList = lineImageList;
	}
}