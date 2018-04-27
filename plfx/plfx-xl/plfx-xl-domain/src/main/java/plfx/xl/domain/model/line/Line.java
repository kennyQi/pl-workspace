package plfx.xl.domain.model.line;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import plfx.xl.domain.model.M;
import plfx.xl.domain.model.crm.LineSupplier;
import plfx.xl.pojo.command.line.AuditLineCommand;
import plfx.xl.pojo.command.line.CopyLineCommand;
import plfx.xl.pojo.command.line.CreateLineCommand;
import plfx.xl.pojo.command.line.GroundingLineCommand;
import plfx.xl.pojo.command.line.ModifyLineCommand;
import plfx.xl.pojo.command.line.UnderCarriageLineCommand;
import plfx.xl.pojo.command.route.ModifyLineRouteInfoCommand;
import plfx.xl.pojo.system.LineConstants;

/**
 * @类功能说明：线路
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日上午10:39:00
 */
@Entity
@Table(name = M.TABLE_PREFIX + "LINE")
@SuppressWarnings("serial")
public class Line extends BaseModel {
	
	/**
	 * 基本信息
	 */
	private LineBaseInfo baseInfo;
	
	/**
	 * 支付信息
	 */
	private LinePayInfo payInfo;
	
	/**
	 * 行程信息
	 */
	private LineRouteInfo routeInfo;
	
	/**
	 * 目地的列表
	 */
//	@OneToMany(fetch = FetchType.EAGER,mappedBy = "line")
//	private List<LineDestinationCity> destinationList;
	
	/**
	 * 价格日历，每日价格列表
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "line")
	private List<DateSalePrice> dateSalePriceList;
	
	/**
	 * 首图图片列表
	 */
	@OneToMany(mappedBy = "line")
	private List<LineImage> lineImageList;
	
	/**
	 * 供应商
	 */
	@ManyToOne
	@JoinColumn(name="LINE_SUPPLIER_ID")
	private LineSupplier lindSupplier;
	
	/**
	 * 団期最低价格
	 */
	@Column(name="MIN_PRICE",columnDefinition=M.MONEY_COLUM)
	private Double minPrice;
	
	/**
	 * 线路状态
	 */
	private LineStatusInfo statusInfo;
//	/**
//	 * 图片列表
//	 */
//	@Transient
//	private List<LinePicture> pictureList;

	/**
	 * 新增线路
	 * @param command
	 */
	public void createLine(CreateLineCommand command){
		
		//线路基本信息
		setId(UUIDGenerator.getUUID());
		LineBaseInfo lineBaseInfo = new LineBaseInfo();
		lineBaseInfo.setName(command.getBaseInfoDTO().getName());
		lineBaseInfo.setNumber(command.getBaseInfoDTO().getNumber());
		lineBaseInfo.setRecommendationLevel(command.getBaseInfoDTO().getRecommendationLevel());
		lineBaseInfo.setType(command.getBaseInfoDTO().getType());
		lineBaseInfo.setStarting(command.getBaseInfoDTO().getStarting());
		lineBaseInfo.setDestinationCity(command.getBaseInfoDTO().getDestinationCity());
		lineBaseInfo.setFeatureDescription(command.getBaseInfoDTO().getFeatureDescription());
		lineBaseInfo.setFavorableDescription(command.getBaseInfoDTO().getFavorableDescription());
		lineBaseInfo.setNoticeDescription(command.getBaseInfoDTO().getNoticeDescription());
		lineBaseInfo.setTrafficDescription(command.getBaseInfoDTO().getTrafficDescription());
		lineBaseInfo.setFeeDescription(command.getBaseInfoDTO().getFeeDescription());
		lineBaseInfo.setBookDescription(command.getBaseInfoDTO().getBookDescription());
		lineBaseInfo.setCreateDate(new Date());
		lineBaseInfo.setCityOfType(command.getBaseInfoDTO().getCityOfType());
		lineBaseInfo.setSales(0);
		//1国内线.2国外线
		lineBaseInfo.setTypeGrade(command.getBaseInfoDTO().getTypeGrade());
		lineBaseInfo.setStartGrade(command.getBaseInfoDTO().getStartGrade());
		
		this.setBaseInfo(lineBaseInfo);
		
		//线路支付信息
		LinePayInfo linePayInfo = new LinePayInfo();
		linePayInfo.setDownPayment(command.getPayInfoDTO().getDownPayment());
		linePayInfo.setLastPayTotalDaysBeforeStart(command.getPayInfoDTO().getLastPayTotalDaysBeforeStart());
		linePayInfo.setPayTotalDaysBeforeStart(command.getPayInfoDTO().getPayTotalDaysBeforeStart());
		this.setPayInfo(linePayInfo);
		
		//供应商
		if(StringUtils.isNotBlank(command.getLineSupplierID())){
			LineSupplier lineSupplier = new LineSupplier();
			lineSupplier.setId(command.getLineSupplierID());
			setLindSupplier(lineSupplier);
		}
		
		
		//状态
		LineStatusInfo lineStatus = new LineStatusInfo();
		lineStatus.setStatus(LineStatusInfo.DO_NOT_AUDIT);//未审核
		setStatusInfo(lineStatus);
		
	}

	

	/**
	 * 
	 * @方法功能说明：修改线路的行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月15日下午3:00:56
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void modifyLineRouteInfo(ModifyLineRouteInfoCommand command){
		LineRouteInfo lineRouteInfo = new LineRouteInfo();
		lineRouteInfo.setRouteDays(command.getRouteDays());
		lineRouteInfo.setRouteName(command.getRouteName());
		lineRouteInfo.setShoppingTimeHour(command.getShoppingTimeHour());
		setRouteInfo(lineRouteInfo);
		//修改行程信息后线路状态为未审核
		LineStatusInfo lineStatus = new LineStatusInfo();
		lineStatus.setStatus(LineStatusInfo.DO_NOT_AUDIT);//未审核
		setStatusInfo(lineStatus);
	}
	
	
	/**
	 * @方法功能说明：线路基本信息修改
	 * @修改者名字：liusong
	 * @修改时间：2014年12月16日下午4:55:48
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLine(ModifyLineCommand command){
		
		//线路基本信息
		LineBaseInfo lineBaseInfo = getBaseInfo();
		lineBaseInfo.setName(command.getBaseInfoDTO().getName());
		lineBaseInfo.setNumber(command.getBaseInfoDTO().getNumber());
		lineBaseInfo.setRecommendationLevel(command.getBaseInfoDTO().getRecommendationLevel());
		lineBaseInfo.setType(command.getBaseInfoDTO().getType());
		lineBaseInfo.setStarting(command.getBaseInfoDTO().getStarting());
		lineBaseInfo.setDestinationCity(command.getBaseInfoDTO().getDestinationCity());
		lineBaseInfo.setFeatureDescription(command.getBaseInfoDTO().getFeatureDescription());
		lineBaseInfo.setFavorableDescription(command.getBaseInfoDTO().getFavorableDescription());
		lineBaseInfo.setNoticeDescription(command.getBaseInfoDTO().getNoticeDescription());
		lineBaseInfo.setTrafficDescription(command.getBaseInfoDTO().getTrafficDescription());
		lineBaseInfo.setFeeDescription(command.getBaseInfoDTO().getFeeDescription());
		lineBaseInfo.setBookDescription(command.getBaseInfoDTO().getBookDescription());
		lineBaseInfo.setCityOfType(command.getBaseInfoDTO().getCityOfType());
//		lineBaseInfo.setCreateDate(getBaseInfo().getCreateDate());
		//国内线,国外线
		lineBaseInfo.setStartGrade(command.getBaseInfoDTO().getStartGrade());
		lineBaseInfo.setTypeGrade(command.getBaseInfoDTO().getTypeGrade());
		this.setBaseInfo(lineBaseInfo);
		
		//线路支付信息
		LinePayInfo linePayInfo = getPayInfo();
		linePayInfo.setDownPayment(command.getPayInfoDTO().getDownPayment());
		linePayInfo.setLastPayTotalDaysBeforeStart(command.getPayInfoDTO().getLastPayTotalDaysBeforeStart());
		linePayInfo.setPayTotalDaysBeforeStart(command.getPayInfoDTO().getPayTotalDaysBeforeStart());
		this.setPayInfo(linePayInfo);
		
		//供应商
		if(StringUtils.isNotBlank(command.getLineSupplierID())){
			LineSupplier lineSupplier = new LineSupplier();
			lineSupplier.setId(command.getLineSupplierID());
			setLindSupplier(lineSupplier);
		}
		
		
		//状态
		LineStatusInfo lineStatus = getStatusInfo();
		lineStatus.setStatus(LineStatusInfo.DO_NOT_AUDIT);//未审核
		setStatusInfo(lineStatus);
	}

	/**
	 * 
	 * @方法功能说明：修改线路行程信息后，线路状态为初始状态未审核
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月6日下午3:18:38
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void initLineStatus(){
		LineStatusInfo lineStatus = new LineStatusInfo();
		lineStatus.setStatus(LineStatusInfo.DO_NOT_AUDIT);//未审核
		setStatusInfo(lineStatus);
	}
	
	/**
	 * 审核线路基本信息资源
	 */
	public void auditLine(AuditLineCommand command){
		//状态
		LineStatusInfo lineStatus = new LineStatusInfo();
		lineStatus.setStatus(command.getStatus());
		setStatusInfo(lineStatus);
	}
	
	/**
	 * 上架线路基本信息资源
	 */
	public void groundingLine(GroundingLineCommand command){
		//状态
		LineStatusInfo lineStatus = new LineStatusInfo();
		lineStatus.setStatus(LineConstants.AUDIT_UP);
		setStatusInfo(lineStatus);
	}
	
	/**
	 * 下架线路基本信息资源
	 */
	public void underCarriageLine(UnderCarriageLineCommand command){
		//状态
		LineStatusInfo lineStatus = new LineStatusInfo();
		lineStatus.setStatus(LineConstants.AUDIT_DOWN);
		setStatusInfo(lineStatus);
	}
	
	/**
	 * 复制线路基本信息资源
	 */
	public  void  copyLine(CopyLineCommand  command,Line line){
		//线路基本信息
		setId(UUIDGenerator.getUUID());
		LineBaseInfo lineBaseInfo = new LineBaseInfo();
		lineBaseInfo.setName(command.getName());
		lineBaseInfo.setNumber(command.getNumber());
		lineBaseInfo.setRecommendationLevel(command.getRecommendationLevel());
		lineBaseInfo.setType(command.getType());
		lineBaseInfo.setStarting(line.getBaseInfo().getStarting());
		lineBaseInfo.setDestinationCity(line.getBaseInfo().getDestinationCity());
		lineBaseInfo.setFeatureDescription(line.getBaseInfo().getFeatureDescription());
		lineBaseInfo.setFavorableDescription(line.getBaseInfo().getFavorableDescription());
		lineBaseInfo.setNoticeDescription(line.getBaseInfo().getNoticeDescription());
		lineBaseInfo.setTrafficDescription(line.getBaseInfo().getTrafficDescription());
		lineBaseInfo.setFeeDescription(line.getBaseInfo().getFeeDescription());
		lineBaseInfo.setBookDescription(line.getBaseInfo().getBookDescription());
		lineBaseInfo.setCreateDate(new Date());
		this.setBaseInfo(lineBaseInfo);
		
		//线路支付信息
		LinePayInfo linePayInfo = new LinePayInfo();
		linePayInfo.setDownPayment(line.getPayInfo().getDownPayment());
		linePayInfo.setLastPayTotalDaysBeforeStart(line.getPayInfo().getLastPayTotalDaysBeforeStart());
		linePayInfo.setPayTotalDaysBeforeStart(line.getPayInfo().getPayTotalDaysBeforeStart());
		this.setPayInfo(linePayInfo);
		
		//供应商
		if(StringUtils.isNotBlank(command.getLineSupplierID())){
			LineSupplier lineSupplier = new LineSupplier();
			lineSupplier.setId(command.getLineSupplierID());
			setLindSupplier(lineSupplier);
		}
		
		//状态
		LineStatusInfo lineStatus = new LineStatusInfo();
		lineStatus.setStatus(LineStatusInfo.DO_NOT_AUDIT);//未审核
		setStatusInfo(lineStatus);
		
		//线路行程信息
		LineRouteInfo lineRouteInfo = new LineRouteInfo();
		lineRouteInfo.setRouteName(line.getRouteInfo().getRouteName());
		lineRouteInfo.setRouteDays(line.getRouteInfo().getRouteDays());
		lineRouteInfo.setShoppingTimeHour(line.getRouteInfo().getShoppingTimeHour());
		this.setRouteInfo(lineRouteInfo);
	}
	
	/**
	 * 
	 * @方法功能说明：计算订单定金价格
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月29日下午4:12:26
	 * @修改内容：
	 * @参数：@param salePrice
	 * @参数：@return
	 * @return:Double
	 * @throws
	 */
	public Double countBargainMoney(Double salePrice){
		Double bargainMoney = salePrice*(this.getPayInfo().getDownPayment()*0.01);
		return bargainMoney;
	}
	
	public LinePayInfo getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(LinePayInfo payInfo) {
		this.payInfo = payInfo;
	}

	public LineBaseInfo getBaseInfo() {
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

	public LineSupplier getLindSupplier() {
		return lindSupplier;
	}

	public void setLindSupplier(LineSupplier lindSupplier) {
		this.lindSupplier = lindSupplier;
	}

	public LineStatusInfo getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(LineStatusInfo statusInfo) {
		this.statusInfo = statusInfo;
	}

	public LineRouteInfo getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(LineRouteInfo routeInfo) {
		this.routeInfo = routeInfo;
	}

	public List<LineImage> getLineImageList() {
		return lineImageList;
	}

	public void setLineImageList(List<LineImage> lineImageList) {
		this.lineImageList = lineImageList;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	
}