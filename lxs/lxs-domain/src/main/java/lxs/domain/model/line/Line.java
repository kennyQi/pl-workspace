package lxs.domain.model.line;
import hg.common.component.BaseModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lxs.domain.model.M;
import slfx.xl.pojo.command.line.CreateLineCommand;
import slfx.xl.pojo.command.line.ModifyLineCommand;
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
@Table(name = M.TABLE_PREFIX_XL + "LINE")
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
	
	@OneToOne(mappedBy = "line",cascade={CascadeType.ALL})
	private LineSelective selectiveLine;
	
	@OneToOne(mappedBy = "line",cascade={CascadeType.ALL})
	private LineActivity lineActivity;
	
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
	
	/**
	 * 排序
	 */
	private int sort;
	
	/**
	 * 本地状态
	 * 0：正常显示
	 * 1：隐藏
	 * 2：删除
	 */
	private int localStatus;
	
	public static final int SALE = 1;//上架
	
	public static final int NOT_SALE = 2;//下架
	
	
	/**
	 * 所包含出行日期
	 * 格式如下
	 *
	 * 201501:201502:201611
	 */
	@Column(name="SALE_DATES")
	private String  saleDates;
	
	
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
	public LineSelective getSelectiveLine() {
		return selectiveLine;
	}
	public void setSelectiveLine(LineSelective selectiveLine) {
		this.selectiveLine = selectiveLine;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getLocalStatus() {
		return localStatus;
	}
	public void setLocalStatus(int localStatus) {
		this.localStatus = localStatus;
	}
	public String getSaleDates() {
		return saleDates;
	}
	public void setSaleDates(String saleDates) {
		this.saleDates = saleDates;
	}
	
	
	
}
