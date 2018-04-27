package hsl.domain.model.xl;
import hg.common.component.BaseModel;
import hsl.domain.model.M;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import plfx.xl.pojo.command.line.CreateLineCommand;
import plfx.xl.pojo.command.line.ModifyLineCommand;
import plfx.xl.pojo.command.route.ModifyLineRouteInfoCommand;
/**
 * @类功能说明：
 * @备注：
 * @类修改者：
 * @修改日期：2015-01-27 14:30:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-01-27 14:30:59
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_XL + "LINE")
public class Line extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 基本信息
	 */
	@Embedded
	private LineBaseInfo baseInfo;
	/**
	 * 支付信息
	 */
	@Embedded
	private LinePayInfo payInfo;
	/**
	 * 路线信息
	 */
	@Embedded
	private LineRouteInfo routeInfo;
	
	/**
	 * 图片文件夹，列表
	 * 使用LineImage
	 */
	//@OneToMany(mappedBy = "line",cascade={CascadeType.ALL})
	//private List<LinePictureFolder> folderList;
	
	/**
	 * 价格日历，每日价格列表
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "line",cascade={CascadeType.ALL})
	@OrderBy("saleDate")
	private List<DateSalePrice> dateSalePriceList;
	
	/**
	 * 最新快照id
	 */
	@Column(name="SLFX_LINE_SNAPSHOTID")
	private String  lineSnapshotId;
	
	/**
	 * 是否上架
	 */
	@Column(name="FORSALE",length=4)
	private int forSale;
	
	
	public static final int SALE = 1;//上架
	
	public static final int NOT_SALE = 2;//下架
	
	@Column(name="MIN_PRICE",columnDefinition=M.MONEY_COLUM)
	private Double minPrice;


	/**
	 * 首图图片列表
	 */
	@OneToMany(mappedBy = "line",cascade={CascadeType.ALL})
	private List<LineImage> lineImageList;
	
	/**
	 * 新增线路
	 * @param command
	 */
	public void createLine(CreateLineCommand command){
		
	}
	/**
	 * @方法功能说明：修改线路行程信息
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月30日上午8:56:21
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLineRouteInfo(ModifyLineRouteInfoCommand command){
	}
	/**
	 * @方法功能说明：线路基本信息修改
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月30日上午8:56:46
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLine(ModifyLineCommand command){
		
	}
	
	public LineRouteInfo getRouteInfo() {
		if (routeInfo == null)
			routeInfo = new LineRouteInfo();
		return routeInfo;
	}

	public void setRouteInfo(LineRouteInfo routeInfo) {
		this.routeInfo = routeInfo;
	}
	
	public LinePayInfo getPayInfo() {
		if (payInfo == null)
			payInfo = new LinePayInfo();
		return payInfo;
	}

	public void setPayInfo(LinePayInfo payInfo) {
		this.payInfo = payInfo;
	}

	public LineBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new LineBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(LineBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public List<DateSalePrice> getDateSalePriceList() {
		return dateSalePriceList;
	}
	public void setDateSalePriceList(List<DateSalePrice> dateSalePriceList) {
		this.dateSalePriceList = dateSalePriceList;
	}
	public String getLineSnapshotId() {
		return lineSnapshotId;
	}
	public void setLineSnapshotId(String lineSnapshotId) {
		this.lineSnapshotId = lineSnapshotId;
	}
	public int getForSale() {
		return forSale;
	}
	public void setForSale(int forSale) {
		this.forSale = forSale;
	}
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	public List<LineImage> getLineImageList() {
		return lineImageList;
	}
	public void setLineImageList(List<LineImage> lineImageList) {
		this.lineImageList = lineImageList;
	}
	
	
}
