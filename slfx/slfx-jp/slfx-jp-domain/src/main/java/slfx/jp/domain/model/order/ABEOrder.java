package slfx.jp.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import slfx.jp.command.client.ABEOrderFlightCommand;
import slfx.jp.domain.model.J;
import slfx.jp.pojo.dto.supplier.abe.ABEOrderFlightDTO;

/**
 * 
 * @类功能说明：ABE订单信息 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月31日下午4:18:42
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "ABE_ORDER")
public class ABEOrder extends BaseModel{
	
	/**
	 * 订座记录编码(PNR)
	 */
	@Column(name = "PNR", length = 6)
	private String pnr;
	
	/**
	 * 旅客信息集合
	 * 注意：
	 * 		1.婴儿的旅客序号为0,可以重复， 其他旅客序号不能重复
	 *		2.婴儿人数必须成人一对一，并且在list中的顺序为一个成人后面跟一个婴儿
	 *		例如：3个成人，2个婴儿  旅客序号的顺序为：1,0,2,0,3 
	 * 表中保存JSON串
	 */
	@Transient
	private List<ABEPassangerInfo> abePsgList;
	
	/**
	 *乘客信息JSON
	 */
	@JSONField(serialize = false)
	@Column(name = "PASSENGER_INFO_JSON",  columnDefinition = J.TEXT_COLUM)
	private String passengerInfoJson;
	
	/**
	 * 乘客类型报价集合 
	 * 仅传旅客信息集合里有的乘客类型  
	 * 表中保存JSON串
	 */
	@Transient
	private List<PriceItem> abePriceDetailList;
	
	/**
	 * 报价JSON
	 */
	@JSONField(serialize = false)
	@Column(name = "PRICE_ITEM_JSON",  columnDefinition = J.TEXT_COLUM)
	private String priceDetailListJson;

	/** 创建时间 */
	@Column(name = "CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public List<ABEPassangerInfo> getAbePsgList() {
		return abePsgList;
	}

	public void setAbePsgList(List<ABEPassangerInfo> abePsgList) {
		this.abePsgList = abePsgList;
	}

	public String getPassengerInfoJson() {
		return passengerInfoJson;
	}

	public void setPassengerInfoJson(String passengerInfoJson) {
		this.passengerInfoJson = passengerInfoJson;
	}

	public List<PriceItem> getAbePriceDetailList() {
		return abePriceDetailList;
	}



	public void setAbePriceDetailList(List<PriceItem> abePriceDetailList) {
		this.abePriceDetailList = abePriceDetailList;
	}

	public String getPriceDetailListJson() {
		return priceDetailListJson;
	}

	public void setPriceDetailListJson(String priceDetailListJson) {
		this.priceDetailListJson = priceDetailListJson;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * ABE订单联系信息
	@Embedded
	private Linker abeLinkerInfo;
	 */
	
	/**
	 * ABE订单信息明细
	@Embedded 
	private ABEOrderInfoDetail orderInfo;
	 */
	
	/**
	 * 航空公司代码
	@Column(name = "CARRIER", length = 8)
	private String carrier;
	 */
	
	/**
	 * 航班号 如：MU1234
	@Column(name = "FLIGHT_NO", length = 16)
	private String flightNo;
	 */
	
	/**
	 * 出发城市代码
	@Column(name = "START_CITY_CODE", length = 8)
	private String startCityCode;
	 */
	
	/**
	 * 目的城市代码
	@Column(name = "END_CITY_CODE", length = 8)
	private String endCityCode;
	 */
	
	/**
	 * 出发日期
	@Column(name = "START_DATE", length = 16)
	private String startDate;
	 */
	
	/**
	 * 出发时间  格式：07:30 代表7点30
	@Column(name = "START_TIME", length = 8)
	private String startTime;
	 */
	
	/**
	 * 到达日期
	@Column(name = "END_DATE", length = 16)
	private String endDate;
	 */
	
	/**
	 * 到达时间
	@Column(name = "END_TIME", length = 8)
	private String endTime;
	 */
	
	/**
	 * 机型代码
	@Column(name = "AIRCRAFT_CODE", length = 8)
	private String aircraftCode;
	 */
	
	/**
	 *  舱位码
	@Column(name = "CLASS_CODE", length = 8)
	private String classCode;
	 */
	
	/**
	 *  舱位价格
	@Column(name = "CLASS_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double classPrice;
	 */
	
	/**
	 *  舱位折扣
	@Column(name = "CLASS_REBATE", length = 8)
	private String classRebate;
	 */
	
	/**
	 * Y舱价格   必填
	@Column(name = "YPRICE", columnDefinition = J.MONEY_COLUM)
	private Double yprice;
	 */
	
	/**
	 * 燃油费
	@Column(name = "FUEL_SUR_TAX", columnDefinition = J.MONEY_COLUM)
	private Double fuelSurTax;
	 */
	
	/**
	 * 机场建设费
	@Column(name = "AIR_PORT_TAX", columnDefinition = J.MONEY_COLUM)
	private Double airportTax;
	 */
	
	/**
	 * 里程
	@Column(name = "MILEAGE", columnDefinition = J.NUM_COLUM)
	private Integer mileage;
	 */
	public  ABEOrder(ABEOrderFlightCommand abeCommand,
			ABEOrderFlightDTO abeOrderDto){
		String tempId = UUIDGenerator.getUUID();
		setId(tempId);
		setCreateDate(new Date());
		setPriceDetailListJson(JSON.toJSONString(abeCommand.getAbePriceDetailList()));
		setPassengerInfoJson(JSON.toJSONString(abeCommand.getAbePsgList()));
		setPnr(abeOrderDto.getPnr());

		
		/*//拷贝基本属性
		String[] ignoreProperties =new String[]{"abePsgList","abePriceDetailList","abeLinkerInfoCommand"};
		BeanUtils.copyProperties(abeCommand, abeOrder,ignoreProperties);
		
		//拷贝旅客信息
		List<ABEPassangerInfo> abePsgInfoList =new ArrayList<ABEPassangerInfo>();
		List<ABEPassengerCommand> abePsgCommandList =abeCommand.getAbePsgList();
		if(abePsgCommandList!=null&&abePsgCommandList.size()>0){
			for(ABEPassengerCommand abePsgCommand:abePsgCommandList){
				ABEPassangerInfo abePassangerInfo =new ABEPassangerInfo();
				BeanUtils.copyProperties(abePsgCommand, abePassangerInfo);
				abePsgInfoList.add(abePassangerInfo);
			}
		}
		abeOrder.setAbePsgList(abePsgInfoList);
		
		
		//拷贝航班信息
		List<ABEPriceDetailCommand> abePriceDetailList=abeCommand.getAbePriceDetailList();
		List<PriceItem> abePriceItemList=new ArrayList<PriceItem>();
		
		if(abePriceDetailList!=null){
			for(ABEPriceDetailCommand abePriceDetail:abePriceDetailList){
				PriceItem priceItem =new PriceItem();
				BeanUtils.copyProperties(abePriceDetail, priceItem);
				abePriceItemList.add(priceItem);
			}
		}
		abeOrder.setAbePriceDetailList(abePriceItemList);
		
		//拷贝联系人
		Linker abeLinkerInfo =new Linker();
		ABELinkerInfoCommand abeLinker=abeCommand.getAbeLinkerInfoCommand();
		BeanUtils.copyProperties(abeLinker, abeLinkerInfo);
		abeOrder.setAbeLinkerInfo(abeLinkerInfo);*/

	}
	public void  updateAbe(ABEOrderFlightDTO abeOrderDto){
		if(abeOrderDto != null && abeOrderDto.getPnr() != null){
			setPnr(abeOrderDto.getPnr());
		}
		String tempId = UUIDGenerator.getUUID();
		setId(tempId);
		setCreateDate(new Date());
	}

	public ABEOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
}