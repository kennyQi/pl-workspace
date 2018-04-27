package plfx.gnjp.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import plfx.jp.domain.model.J;
import plfx.yeexing.pojo.command.order.JPAutoOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPBookTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPOrderCommand;
import plfx.yeexing.pojo.command.order.JPRefundQueryOrderStatusSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundTicketSpiCommand;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.YeeXingJPAutoOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPOrderDTO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：机票平台订单 model
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午1:55:27
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX_GN + "ORDER")
public class GNJPOrder extends BaseModel {
	public GNJPOrder() {
		super();
	}
	//经销商相关：
	/**
	 * 经销商订单号 
	 */
	@Column(name = "DEALER_ORDER_CODE", length = 64 )
	private String dealerOrderCode;
	
	/** 
	 * 经销商名字
	 */
	@Column(name = "DEALER_NAME", length = 64 )
	private String dealerName;
	/**
	 * 经销商代码
	 */
	@Column(name="DEALER_CODE", length=64)
	private String dealerCode;
	/**
	 * 支付平台
	 * 1—支付宝 2—汇付 6—IPS 7—德付通
	 */
	@Column(name="PAY_PLATFORM", length=2)
	private Integer payPlatform;
	/**
	 * 支付方式
	 * 1：自动扣款，2：收银台支付
	 */
	@Column(name="PAY_TYPE", length=2)
	private Integer payType;
	/**
	 * 第三方支付单号
	 */
	@Column(name = "PAY_TRADE_NO")
	private String payTradeNo;
	
	/** 
	 * 付款账号 
	 */
	@Column(name = "PAY_ACCOUNTNO") 
	private String payAccountNo;
	
	/** 
	 * 退款批次号
	 */
	@Column(name = "BATCH_NO")
	private String batchNo;

	/**
	 * 流水号相关：  1.支付宝接口用户付钱，得到用户支付流水号
	 * 		    2. 用户退废票，支付宝接口平台退钱，得到平台退款流水号
	 * 
	 * 			3. 平台想供应商付钱，得到平台支付流水号
	 * 			4. 用户退废票，供应商向平台退钱，得到供应商退款流水号
	 */	
	/** 用户支付流水号    admin端用 */
	@Column(name = "CUSTOMER_PAY_SERIAL_NUMBER", length = 32)
	private String customerPaySerialNumber;
	/** 平台退款流水号    admin端用 */
	@Column(name = "PLAT_BACK_SERIAL_NUMBER", length = 32)
	private String platBackSerialNumber;
	/** 平台支付流水号    admin端用 */
	@Column(name = "PLAT_PAY_SERIAL_NUMBER", length = 32)
	private String platPaySerialNumber;
	/** 供应商退款流水号    admin端用 */
	@Column(name = "SUPP_BACK_SERIAL_NUMBER", length = 32)
	private String suppBackSerialNumber;

//易行相关：	
	/**
	 * 易行订单ID :数据库ID
	 */
	@Column(name = "YEEXING_ORDER_ID", length = 50)
	private String yeeXingOrderId;
	
	

//订单金额
	/**
	 * 支付供应商金额
	 */
	@Column(name = "YEEXING_TOTALPRICE", columnDefinition = J.DOUBLE_COLUM)
	private Double totalPrice;
	
	/**
	 * 票面价
	 */
	@Column(name = "YEEXING_IBEPRICE", columnDefinition = J.DOUBLE_COLUM)
	private Double ibePrice;
	
	/**
	 * 返点
	 */
	@Column(name = "YEEXING_DISC", columnDefinition = J.DOUBLE_COLUM)
	private Double disc;
	
	/**
	 * 红包  
	 * 返点之外再减
	 */
	@Column(name = "YEEXING_EXTREWARD", columnDefinition = J.DOUBLE_COLUM)
	private Double extReward;
	
	/**
	 * 单张利润
	 */
	@Column(name = "YEEXING_PROFITS", columnDefinition = J.DOUBLE_COLUM)
	private Double profits;
	
	/**
	 * 票号类型
	 * 1--B2B，2—BSP，3—所有
	 */
	@Column(name = "YEEXING_TICKTYPE", columnDefinition=J.NUM_COLUM, length = 1)
	private int tickType;
	
	/**
	 * 婴儿价格
	 */
	@Column(name = "YEEXING_INFPRICE", columnDefinition = J.DOUBLE_COLUM)
	private Double infPrice;
	
	/**
	 * 单张票价
	 */
	@Column(name = "YEEXING_TICKPRICE", columnDefinition = J.DOUBLE_COLUM)
	private Double tickPrice;
	
	/**
	 * 易行的政策id
	 */
	@Column(name = "YEEXING_PLCID", length = 30)
	private int plcid;
	
	/**
	 * 出票速度
	 */
	@Column(name = "YEEXING_OUTTIME", length = 15)
	private String outTime;
	
	/**
	 * 订单创建时间
	 */
	@Column(name = "YEEXING_CREATETIME", length = 30)
	private String createTime;
	
	/**
	 * 备注
	 */
	@Column(name = "YEEXING_MEMO", columnDefinition=J.TEXT_COLUM)
	private String memo;
	
	/**
	 * 表示该次操作是否成功 T:成功F：失败
	 */
	@Column(name = "YEEXING_IS_SUCCESS", length = 2)
	private String is_success;
	
	/**
	 * 机场建设费
	 */
	@Column(name = "YEEXING_BUILDFEE", columnDefinition = J.DOUBLE_COLUM)
	private Double buildFee;
	
	/**
	 * 是否更改pnr
	 */
	@Column(name = "YEEXING_CHANGEPNR", length = 15)
	private String  changePnr;
	
	/**
	 * 特殊高政策
	 */
	@Column(name = "YEEXING_ISSPHIGH", length = 2)
	private String  isSphigh;
	
	/**
	 *燃油税
	 */
	@Column(name = "YEEXING_OILFEE", columnDefinition = J.DOUBLE_COLUM)
	private Double oilFee;
	
	/**
	 *供应商周一至周五工作时间
	 */
	@Column(name = "YEEXING_WORKTIME", length = 30)
	private String workTime;
	
	/**
	 *供应商周六、周日工作时间
	 */
	@Column(name = "YEEXING_RESTWORKTIME", length = 30)
	private String restWorkTime;
	
	/** 
	 * 平台所得佣金 (单张利润profits * 数量passangerCount + 总的加价政策platPolicyPirce)
	 */
	@Column(name = "COMM_AMOUNT", columnDefinition = J.DOUBLE_COLUM)
	private Double commAmount;
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/** 平台政策价格 （自己平台加价部分） */
	@Column(name = "PLAT_POLICY_PRICE", columnDefinition = J.DOUBLE_COLUM)
	private Double platPolicyPirce;//总的加价政策
	/**
	 *  平台计算政策价格后需要支付的总价，totalPrice基础上算的价格 
	 */
	@Column(name = "PLAT_POLICY_TOTALPRICE", columnDefinition = J.DOUBLE_COLUM)
	private Double platTotalPrice;//总的加价政策
	/** 平台政策ID （自己平台加价部分） */
	@Column(name = "PLAT_POLICY_ID", length = 64)
	private String paltPolicyId;
	
	
	/** 
	 * 用户支付总价  
	 */
	@Column(name = "USER_PAY_AMOUNT", columnDefinition = J.DOUBLE_COLUM)
	private Double userPayAmount;

//订单状态： orderStatus 和 type 为必填项
	/** 订单状态 */
	@Embedded
	private GNJPOrderStatus orderStatus;
	
	/** 异常订单为1，普通订单为0，默认为0； */
	@Column(name = "TYPE", length = 2)
	private String type;  

	/** 平台订单号 */
	@Column(name = "ORDER_NO", length = 64)
	private String orderNo;
	
	/** 乘机人 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="jpOrder",cascade={CascadeType.ALL})
	private List<GNJPPassenger> passengerList ;
	
	/**
	 * 乘机人数量
	 */
	@Column(name = "PASSANGER_COUNT", length = 15)
	private Integer passangerCount;
	/**
	 * 航班信息快照
	 * 对应SlfxFlightDTO
	 */
	@Column(name = "FLIGHT_SNAP_SHOT_JSON", columnDefinition = J.TEXT_COLUM)
	private String flightSnapshotJSON;
	
	/**
	 * 该订单生成之前的比价记录
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPARE_PRICE_ID")
	private ComparePrice comparePrice;
	 */

	
	/** 订单PNR */
	@Column(name = "PNR", length = 8)
	private String pnr;
	/** 舱位代码 */
	@Column(name = "CLASS_CODE", length = 6)
	private String classCode;
	/***舱位名称  *****/
	@Column(name = "CABIN_NAME", length = 10)
	private String cabinName;
	/** 下单人 */
	@Embedded
	private GNJPLinker linker;
	/** 下单人  */
	@Column(name = "USER_ID",length = 64)
	private String userId;
	
	/** 下单登录名 */
	@Column(name = "LOGIN_NAME",length = 64)
	private String loginName;
	
	/** 异常订单调整 金额 admin端用*/
	@Column(name = "ADJUST_AMOUNT", columnDefinition = J.DOUBLE_COLUM)
	private Double adjustAmount;
	
	/**
	 * 异常订单调整凭证 admin端用
	 */
	@Column(name = "VOUCHER_PICTURE",length=255)
	private String voucherPicture;
	
	/**
	 * 异常订单调整原因 admin端用
	 */
	@Column(name = "ADJUST_REASON",length = 255)
	private String adjustReason;
	
	
	/** 航空公司   */
	@Column(name = "AIR_COMP_NAME",length = 64)
	private String airCompName;
	
//	/**
//	 * 出票工作时间
//	 */
//	@Column(name = "WORK_TIME", length = 64)
//	private String workTime;
	/**
	 * 退票时间
	 */
	@Column(name = "REFUND_WORK_TIME", length = 64)
	private String refundWorkTime;
	/**
	 * 航班废票截至时间
	 */
	@Column(name = "WAST_WORK_TIME", columnDefinition = J.DATE_COLUM)
	private Date wastWorkTime;
	
	
//退废票相关信息：
	/**  退票订单（如果不为空，则被退废票） */
	@Column(name="ORDER_BACK_ID", length=64)
	private String orderBackId;
	/** 易购退票订单号  */
	@Column(name = "YG_REFUND_ORDER_NO", length = 64 )
	private String ygRefundOrderNo;
	/** 退废票平台订单号  */
	@Column(name = "REFUND_PLATFORM_ORDER_NO", length = 64 )
	private String refundPlatformOrderNo;
	/**   退废类型：详情参考 GNJPOrderStatus.CATEGORY_REFUND   */
	@Column(name = "BACK_CATEGORY", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer backCategory;

	/** 下单时间 */
	@Column(name = "CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;

	/**
	 * 航班号 string ZH9814
	 */
	@Column(name = "FLIGHT_NO", length = 8)
	private String flightNo;
	/**
	 * 起飞时间
	 */
	@Column(name = "DEPARTURE_TIME", length = 19)
	private String departureTime;
	
	/**
	 * 到达时间
	 */
	@Column(name = "END_TIME", length = 19)
	private String endTime;
	
	/**
	 * 航空公司二字码
	 */
	@Column(name = "CARRIER", length = 8)
	private String carrier;
	/**
	 * 出发城市代码:admin端用
	 */
	@Column(name = "START_CITY_CODE", length = 8)
	private String startCityCode;
	/**
	 * 出发城市名称
	 */
	@Column(name = "START_CITY_NAME", length = 15)
	private String startCityName;
	
	/**
	 * 目的城市代码：admin端用
	 */
	@Column(name = "END_CITY_CODE", length = 8)
	private String endCityCode;
	
	/**
	 * 到达城市名称
	 */
	@Column(name = "END_CITY_NAME", length = 15)
	private String endCityName;
	
	/**
	 * 手续费
	 */
	@Column(name = "SERVICE_CHARGE", columnDefinition = J.DOUBLE_COLUM)
	private Double serviceCharge;
	
	/**
	 * 差价(佣金)
	 */
	@Column(name = "DISPARITY", columnDefinition = J.DOUBLE_COLUM)
	private Double disparity;
	
	/**
	 * 申请种类
	 */
	@Column(name = "REFUND_TYPE", columnDefinition = J.CHAR_COLUM)
	private String refundType;
	
	/**
	 * 退废种类
	 * 1.当日作废
       2.自愿退票
       3.非自愿退票
       4.差错退款
       5.其他

	 */
	@Column(name = "APPLY_TYPE", columnDefinition = J.CHAR_COLUM)
	private String applyType;
	
	/****
	 * 退废理由
	 */
	@Column(name = "REFUND_MEMO", columnDefinition=J.TEXT_COLUM)
	private String refundMemo;
	
	/**
	 * 实退金额
	 */
	@Column(name = "REFUND_PRICE", columnDefinition = J.DOUBLE_COLUM)
	private Double refundPrice;
	
	/**
	 * 退款手续费
	 */
	@Column(name = "REFUND_PROCEDURES", columnDefinition = J.DOUBLE_COLUM)
	private Double procedures;


	/****
	 * 新增订单
	 * @类名：GNJPOrder.java
	 * @描述：TODO
	 * @param jPBookTicketSpiCommand
	 * @param yxFlightOrderDTO
	 * @param status
	 * @param yeeXingFlightDTO
	 */
	public GNJPOrder(JPBookTicketSpiCommand command,YeeXingJPOrderDTO yeeXingJPOrderDTO,GNJPOrderStatus status,YeeXingFlightDTO yeeXingFlightDTO) {
		HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->YeeXingJPOrderDTO:"+ JSON.toJSONString(yeeXingJPOrderDTO));
		//易行天下订单号
		this.setYeeXingOrderId(yeeXingJPOrderDTO.getOrderid());
		if(command != null){
			//经销商订单号
			this.setDealerOrderCode(command.getDealerOrderId());
			//舱位代码
			this.setClassCode(command.getCabinCode());
			//舱位名称
			this.setCabinName(command.getCabinName());
		}
		//订单类型(普通0和异常1)
		this.setType(JPOrderStatusConstant.COMMON_TYPE);//创建订单默认设置为0普通订单
		//订单总支付价格
		this.setTotalPrice(yeeXingJPOrderDTO.getPriceDTO().getTotalPrice());
	    //政策返点
		this.setDisc(yeeXingJPOrderDTO.getPriceDTO().getDisc());
		//红包
		this.setExtReward(yeeXingJPOrderDTO.getPriceDTO().getExtReward());
		//票面价
		this.setIbePrice(yeeXingJPOrderDTO.getPriceDTO().getIbePrice());
		//单张利润
		this.setProfits(yeeXingJPOrderDTO.getPriceDTO().getProfits());
		//票号类型(1--B2B，2--BSP)
		this.setTickType(yeeXingJPOrderDTO.getPriceDTO().getTickType());
		//单张票价
		this.setTickPrice(yeeXingJPOrderDTO.getPriceDTO().getTickPrice());
		//设置平台总的加价政策金额
		this.setPlatPolicyPirce(yeeXingJPOrderDTO.getPriceDTO().getPlatPolicyPirce());
		//平台所得佣金
		Double plfxCommAmount = null;
		//用户支付总价
		Double userPayA = null;
		if(null != command.getUserPayAmount()){
			userPayA = command.getUserPayAmount();
			//佣金=用户支付的总价-手续费-支付供应商的钱
			plfxCommAmount = userPayA - yeeXingJPOrderDTO.getPriceDTO().getServiceCharge()
					- yeeXingJPOrderDTO.getPriceDTO().getTotalPrice();
			HgLogger.getInstance().info("yuqz", "用户支付总价为经销商传过来userPayA=" + userPayA + ",plfxCommAmount="+plfxCommAmount);
		}
		//智行
		else if(command.getFromDealerId().equals("F1001")){
			//用户支付的钱   （票面价+机建+燃油）* 个数
			userPayA = (yeeXingJPOrderDTO.getPriceDTO().getIbePrice() + yeeXingJPOrderDTO.getPriceDTO().getBuildFee()
					+ yeeXingJPOrderDTO.getPriceDTO().getOilFee())
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			//平台所得佣金=(单张票利润-支付宝手续费+价格政策）*数量
			plfxCommAmount = (yeeXingJPOrderDTO.getPriceDTO().getProfits() 
					- yeeXingJPOrderDTO.getPriceDTO().getServiceCharge()//手续费
					+ yeeXingJPOrderDTO.getPriceDTO().getPlatPolicyPirce())
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			HgLogger.getInstance().info("yuqz", "用户支付总价为智行未传userPayA=" + userPayA + ",plfxCommAmount="+plfxCommAmount);
		}
		//中智票量
		else if(command.getFromDealerId().equals("F1002")){
			//平台所得佣金=(用户支付的单张票价 - 支付供应商的单张票价 - 支付宝手续费) * 数量
			plfxCommAmount = (yeeXingJPOrderDTO.getPriceDTO().getPlatTotalPrice()
					- yeeXingJPOrderDTO.getPriceDTO().getTotalPrice())
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			//用户支付的钱   用户支付的单张票价 *人数
			userPayA = yeeXingJPOrderDTO.getPriceDTO().getPlatTotalPrice()
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			HgLogger.getInstance().info("yuqz", "用户支付总价为中智票量未传userPayA=" + userPayA + ",plfxCommAmount="+plfxCommAmount);
		}
		//有时候平台佣金小数位太长,需要统一四舍五入保留两位小数
		this.setCommAmount(this.saveTwoNumberOfDouble(plfxCommAmount));
		this.setUserPayAmount(userPayA);
		//设置手续费
		this.setServiceCharge(yeeXingJPOrderDTO.getPriceDTO().getServiceCharge());
		//设置平台价格(单张票)
		this.setPlatTotalPrice(yeeXingJPOrderDTO.getPriceDTO().getTotalPrice());
		//政策id
		this.setPlcid(yeeXingJPOrderDTO.getPriceDTO().getPlcid());
		//机建费用
		this.setBuildFee(yeeXingJPOrderDTO.getPriceDTO().getBuildFee());
		//燃油费
		this.setOilFee(yeeXingJPOrderDTO.getPriceDTO().getOilFee());
		//出票速度
		this.setOutTime(yeeXingJPOrderDTO.getPriceDTO().getOutTime());
		//备注
		this.setMemo(yeeXingJPOrderDTO.getPriceDTO().getMemo());
		//供应商周一至周五工作时间
		this.setWorkTime(yeeXingJPOrderDTO.getPriceDTO().getWorkTime());
		//供应商周六、周日工作时间
		this.setRestWorkTime(yeeXingJPOrderDTO.getPriceDTO().getRestWorkTime());	
		//是否更改pnr
		this.setChangePnr(yeeXingJPOrderDTO.getPriceDTO().getChangePnr());
		//特殊高政策
		this.setIsSphigh(yeeXingJPOrderDTO.getPriceDTO().getIsSphigh());
		//婴儿价
		this.setInfPrice(yeeXingJPOrderDTO.getPriceDTO().getInfPrice());
		//订单创建时间
		this.setCreateTime(yeeXingJPOrderDTO.getCreateTime());
		//订单支付支持方式
		this.setPayPlatform(yeeXingJPOrderDTO.getPriceDTO().getPayType());
		//是否操作成功(T成功F失败)
		this.setIs_success(yeeXingJPOrderDTO.getIs_success());
		//用户订单状态：待支付
		this.setOrderStatus(status);
		//乘机人
		this.setPassangerCount(yeeXingJPOrderDTO.getPassengerList().size());
		List<GNJPPassenger> passengerList = new ArrayList<GNJPPassenger>();
		List<YeeXingPassengerDTO> passangers = command.getYeeXingpassengerInfoDTO().getPassengerList();
		if (passangers != null && passangers.size() > 0) {
			for (YeeXingPassengerDTO psgDto : passangers) {
				GNJPPassenger psg = new GNJPPassenger();
				psg.setId(UUIDGenerator.getUUID());
				//姓名
				psg.setName(psgDto.getPassengerName());
				//旅客类型
				psg.setPsgType(psgDto.getPassengerType());
			    //证件类型
			    psg.setIdType(psgDto.getIdType());
			    //证件号
			    psg.setIdNo(psgDto.getIdNo());
				// 单人票面价
				psg.setFare(yeeXingJPOrderDTO.getPriceDTO().getIbePrice());
				passengerList.add(psg);
			}
			this.setPassengerList(passengerList);
		}
		//保存航班信息
		HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->YeeXingFlightDTO:"+ JSON.toJSONString(yeeXingFlightDTO));
		//出发机场三字码
		if(yeeXingFlightDTO != null){
			this.setStartCityCode(yeeXingFlightDTO.getOrgCity());
			//目的机场三字码
			this.setEndCityCode(yeeXingFlightDTO.getDstCity());
			//航空公司二字码
			this.setCarrier(yeeXingFlightDTO.getAirComp());
			//航空公司名称
			this.setAirCompName(yeeXingFlightDTO.getAirCompanyName());
			//起飞时间
			this.setDepartureTime(yeeXingFlightDTO.getStartTime());
			//结束时间
			this.setEndTime(yeeXingFlightDTO.getEndTime());
			//出发地机场
			this.setStartCityName(yeeXingFlightDTO.getOrgCityName());
			//目的地机场
			this.setEndCityName(yeeXingFlightDTO.getDstCityName());
			//航班号
			this.setFlightNo(yeeXingFlightDTO.getFlightno());
			//航班快照
			this.setFlightSnapshotJSON(JSON.toJSONString(yeeXingFlightDTO));	
		}
		
		GNJPLinker linker = new GNJPLinker();
		
		//联系人姓名
		linker.setName(command.getYeeXingpassengerInfoDTO().getName());
		//联系人手机
		linker.setMobilePhone(command.getYeeXingpassengerInfoDTO().getTelephone());
	
		//下单人
		this.setUserId(command.getYeeXingpassengerInfoDTO().getId());
		
		//下单登录名
	    this.setLoginName(command.getYeeXingpassengerInfoDTO().getName());
	    
		// 分销商id＋订单编号＋流水号，编号长度不固定，流水号从1开始往上做加法；
		this.setOrderNo(command.getOutOrderId());
		
	}
	
	/**
	 * @方法功能说明：出票成功更新机票订单信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日上午9:07:36
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	public void updateJPOrder(JPOrderCommand command) {
		this.pnr = command.getNewPnr();
		String airId = command.getAirId();
		String[] passengerNames = command.getPassengerName().split("\\^");
		String[] airIds = airId.split("\\^");
		for(GNJPPassenger passenger : passengerList){
			for(int i = 0; i < passengerNames.length; i++){
				if(passenger.getName().equals(passengerNames[i])){
					GNFlightTicket ticket = new GNFlightTicket(airIds[i],passenger.getFare());
					passenger.setTicket(ticket);
				}
			}
		}
	}
	
	/**
	 * 新增取消订单记录
	 * @param jpOrder
	 * @param passenger
	 */
	public GNJPOrder(GNJPOrder jpOrder, GNJPPassenger passenger){
		try {
			BeanUtils.copyProperties(jpOrder, this);
			//对取消生成的订单做处理(单张票的总价,平台所得佣金,用户支付总价,乘机人数)这些数据有变化
			//平台所得佣金(单张利润profits + 平台总的加价政策platPolicyPirce)
			Double plfxCommAmount = jpOrder.getProfits() + jpOrder.getPlatPolicyPirce();
			//单张票的总价=单张票价+基建+燃油
			Double totalPrice = jpOrder.getTickPrice() + jpOrder.getBuildFee() + jpOrder.getOilFee();
			//重新设置平台所得佣金
			this.setCommAmount(plfxCommAmount);
			//重新设置单张票总价
			this.setTotalPrice(totalPrice);
			//重新设置用户支付总价            用户支付总价=单张票总价+平台所得佣金
			this.setUserPayAmount(totalPrice + plfxCommAmount);
			//重新设置乘机人数
			this.setPassangerCount(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String uuid = UUIDGenerator.getUUID();
		this.setId(uuid);
		this.setPassengerList(null);
		List<GNJPPassenger> passengerList = new ArrayList<GNJPPassenger>();
		passenger.setJpOrder(this);
		passenger.setJpCancelOrderId(uuid);
		passengerList.add(passenger);
		this.setPassengerList(passengerList);
		this.setRefundType("C");//类型为取消订单
		GNJPOrderStatus orderStatus = this.getOrderStatus();
		//订单状态改为已取消
		orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_CANCEL));
		//支付状态  已支付->待退款 
		if(orderStatus.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC)){
			orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_WAIT));
		}
	}

	/**
	 * 新增退废票记录(只能保存机票和乘客信息,退废信息个别字段等通知后在修改)
	 * @param jpOrder
	 * @param newPassengers
	 * @param command
	 */
	public GNJPOrder(GNJPOrder jpOrder, GNJPPassenger passenger,JPRefundTicketSpiCommand command) {
		try {
			BeanUtils.copyProperties(jpOrder, this);
			//更新有变动的字段数据
			//对申请退废生成的订单做处理(单张票的总价,平台所得佣金,用户支付总价,乘机人数)这些数据有变化
			//平台所得佣金(单张利润profits + 平台总的加价政策platPolicyPirce)
			Double plfxCommAmount = jpOrder.getProfits() + jpOrder.getPlatPolicyPirce();
			//单张票的总价=单张票价+基建+燃油
			Double totalPrice = jpOrder.getTickPrice() + jpOrder.getBuildFee() + jpOrder.getOilFee();
			//重新设置平台所得佣金
			this.setCommAmount(plfxCommAmount);
			//重新设置单张票总价
			this.setTotalPrice(totalPrice);
			//重新设置用户支付总价            用户支付总价=单张票总价+平台所得佣金
			this.setUserPayAmount(totalPrice + plfxCommAmount);
			//重新设置乘机人数
			this.setPassangerCount(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String uuid = UUIDGenerator.getUUID();
		this.setId(uuid);
		this.setPassengerList(null);
		List<GNJPPassenger> passengerList = new ArrayList<GNJPPassenger>();
		passenger.setJpOrder(this);
		passenger.setJpCancelOrderId(uuid);
		passengerList.add(passenger);
		this.setPassengerList(passengerList);
		//类型为退废票
		this.setRefundType("T");
		//申请种类
		this.setApplyType(command.getRefundType());
		//申请理由	
		this.setRefundMemo(command.getRefundMemo());
		GNJPOrderStatus orderStatus = this.getOrderStatus();
		orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REFUND_DEALING));// 退废处理中
		orderStatus.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_WAIT));// 等待退款
	}

	public String getCabinName() {
		return cabinName;
	}

	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Double getProcedures() {
		return procedures;
	}

	public void setProcedures(Double procedures) {
		this.procedures = procedures;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getPayAccountNo() {
		return payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCustomerPaySerialNumber() {
		return customerPaySerialNumber;
	}

	public void setCustomerPaySerialNumber(String customerPaySerialNumber) {
		this.customerPaySerialNumber = customerPaySerialNumber;
	}

	public String getPlatBackSerialNumber() {
		return platBackSerialNumber;
	}

	public void setPlatBackSerialNumber(String platBackSerialNumber) {
		this.platBackSerialNumber = platBackSerialNumber;
	}

	public String getPlatPaySerialNumber() {
		return platPaySerialNumber;
	}

	public void setPlatPaySerialNumber(String platPaySerialNumber) {
		this.platPaySerialNumber = platPaySerialNumber;
	}

	public String getSuppBackSerialNumber() {
		return suppBackSerialNumber;
	}

	public void setSuppBackSerialNumber(String suppBackSerialNumber) {
		this.suppBackSerialNumber = suppBackSerialNumber;
	}

	public String getYeeXingOrderId() {
		return yeeXingOrderId;
	}

	public void setYeeXingOrderId(String yeeXingOrderId) {
		this.yeeXingOrderId = yeeXingOrderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(Double ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Double getDisc() {
		return disc;
	}

	public void setDisc(Double disc) {
		this.disc = disc;
	}

	public Double getExtReward() {
		return extReward;
	}

	public void setExtReward(Double extReward) {
		this.extReward = extReward;
	}

	public Double getProfits() {
		return profits;
	}

	public void setProfits(Double profits) {
		this.profits = profits;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	public Double getPlatPolicyPirce() {
		return platPolicyPirce;
	}

	public void setPlatPolicyPirce(Double platPolicyPirce) {
		this.platPolicyPirce = platPolicyPirce;
	}

	public String getPaltPolicyId() {
		return paltPolicyId;
	}

	public void setPaltPolicyId(String paltPolicyId) {
		this.paltPolicyId = paltPolicyId;
	}

	public Double getUserPayAmount() {
		return userPayAmount;
	}

	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}

	public GNJPOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(GNJPOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPassangerCount() {
		return passangerCount;
	}

	public void setPassangerCount(Integer passangerCount) {
		this.passangerCount = passangerCount;
	}

	public String getFlightSnapshotJSON() {
		return flightSnapshotJSON;
	}

	public void setFlightSnapshotJSON(String flightSnapshotJSON) {
		this.flightSnapshotJSON = flightSnapshotJSON;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public GNJPLinker getLinker() {
		return linker;
	}

	public void setLinker(GNJPLinker linker) {
		this.linker = linker;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getVoucherPicture() {
		return voucherPicture;
	}

	public void setVoucherPicture(String voucherPicture) {
		this.voucherPicture = voucherPicture;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getAirCompName() {
		return airCompName;
	}

	public void setAirCompName(String airCompName) {
		this.airCompName = airCompName;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRefundWorkTime() {
		return refundWorkTime;
	}

	public void setRefundWorkTime(String refundWorkTime) {
		this.refundWorkTime = refundWorkTime;
	}

	public Date getWastWorkTime() {
		return wastWorkTime;
	}

	public void setWastWorkTime(Date wastWorkTime) {
		this.wastWorkTime = wastWorkTime;
	}

	public String getOrderBackId() {
		return orderBackId;
	}

	public void setOrderBackId(String orderBackId) {
		this.orderBackId = orderBackId;
	}

	public String getYgRefundOrderNo() {
		return ygRefundOrderNo;
	}

	public void setYgRefundOrderNo(String ygRefundOrderNo) {
		this.ygRefundOrderNo = ygRefundOrderNo;
	}

	public String getRefundPlatformOrderNo() {
		return refundPlatformOrderNo;
	}

	public void setRefundPlatformOrderNo(String refundPlatformOrderNo) {
		this.refundPlatformOrderNo = refundPlatformOrderNo;
	}

	public Integer getBackCategory() {
		return backCategory;
	}

	public void setBackCategory(Integer backCategory) {
		this.backCategory = backCategory;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getStartCityCode() {
		return startCityCode;
	}

	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}

	public String getStartCityName() {
		return startCityName;
	}

	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}

	public String getEndCityCode() {
		return endCityCode;
	}

	public void setEndCityCode(String endCityCode) {
		this.endCityCode = endCityCode;
	}

	public String getEndCityName() {
		return endCityName;
	}

	public void setEndCityName(String endCityName) {
		this.endCityName = endCityName;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Double getDisparity() {
		return disparity;
	}

	public void setDisparity(Double disparity) {
		this.disparity = disparity;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public int getTickType() {
		return tickType;
	}

	public void setTickType(int tickType) {
		this.tickType = tickType;
	}

	

	public Double getTickPrice() {
		return tickPrice;
	}

	public void setTickPrice(Double tickPrice) {
		this.tickPrice = tickPrice;
	}

	
	public int getPlcid() {
		return plcid;
	}

	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	
	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Double getBuildFee() {
		return buildFee;
	}

	public void setBuildFee(Double buildFee) {
		this.buildFee = buildFee;
	}

	public Double getOilFee() {
		return oilFee;
	}

	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}

	public String getRestWorkTime() {
		return restWorkTime;
	}

	public void setRestWorkTime(String restWorkTime) {
		this.restWorkTime = restWorkTime;
	}

	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	
	public String getChangePnr() {
		return changePnr;
	}

	public void setChangePnr(String changePnr) {
		this.changePnr = changePnr;
	}

	
	public String getIsSphigh() {
		return isSphigh;
	}

	public void setIsSphigh(String isSphigh) {
		this.isSphigh = isSphigh;
	}

	
	public Double getInfPrice() {
		return infPrice;
	}

	public void setInfPrice(Double infPrice) {
		this.infPrice = infPrice;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}
	
	

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	public List<GNJPPassenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<GNJPPassenger> passengerList) {
		this.passengerList = passengerList;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}
	
	/****
	 * 新增订单(生成订单并自动扣款)
	 * @类名：GNJPOrder.java
	 * @描述：TODO
	 * @@param jPBookTicketSpiCommand
	 * @@param yxFlightOrderDTO
	 * @@param status
	 * @@param yeeXingFlightDTO
	 */
	public GNJPOrder(JPAutoOrderSpiCommand command,YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO,GNJPOrderStatus status,YeeXingFlightDTO yeeXingFlightDTO) {
		HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->YeeXingJPOrderDTO:"+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
		//易行天下订单号
		this.setYeeXingOrderId(yeeXingJPAutoOrderDTO.getOrderid());
		if(command != null){
			//经销商订单号
			this.setDealerOrderCode(command.getDealerOrderId());
			//舱位代码
			this.setClassCode(command.getCabinCode());
			//舱位名称
			this.setCabinName(command.getCabinName());
		}
		//订单类型(普通0和异常1)
		this.setType(JPOrderStatusConstant.COMMON_TYPE);//创建订单默认设置为0普通订单
		//订单总支付价格
		this.setTotalPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getTotalPrice());
	    //政策返点
		this.setDisc(yeeXingJPAutoOrderDTO.getPriceDTO().getDisc());
		//红包
		this.setExtReward(yeeXingJPAutoOrderDTO.getPriceDTO().getExtReward());
		//票面价
		this.setIbePrice(yeeXingJPAutoOrderDTO.getPriceDTO().getIbePrice());
		//单张利润
		this.setProfits(yeeXingJPAutoOrderDTO.getPriceDTO().getProfits());
		//票号类型(1--B2B，2--BSP)
		this.setTickType(yeeXingJPAutoOrderDTO.getPriceDTO().getTickType());
		//单张票价
		this.setTickPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getTickPrice());
		//设置平台总的加价政策金额
		this.setPlatPolicyPirce(yeeXingJPAutoOrderDTO.getPriceDTO().getPlatPolicyPirce());
		//平台所得佣金
		Double plfxCommAmount = null;
		//用户支付总价
		Double userPayA = null;
		if(null != command.getUserPayAmount()){
			userPayA = command.getUserPayAmount();
			//佣金=用户支付的总价-手续费-支付供应商的钱
			plfxCommAmount = userPayA - yeeXingJPAutoOrderDTO.getPriceDTO().getServiceCharge()
					- yeeXingJPAutoOrderDTO.getPriceDTO().getTotalPrice();
			HgLogger.getInstance().info("yuqz", "用户支付总价为经销商传过来userPayA=" + userPayA + ",plfxCommAmount="+plfxCommAmount);
		}
		//智行
		else if(command.getFromDealerId().equals("F1001")){
			//用户支付的钱   （票面价+机建+燃油）* 个数
			userPayA = (yeeXingJPAutoOrderDTO.getPriceDTO().getIbePrice() + yeeXingJPAutoOrderDTO.getPriceDTO().getBuildFee()
					+ yeeXingJPAutoOrderDTO.getPriceDTO().getOilFee())
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			//平台所得佣金=(单张票利润-支付宝手续费+价格政策）*数量
			plfxCommAmount = (yeeXingJPAutoOrderDTO.getPriceDTO().getProfits() 
					- yeeXingJPAutoOrderDTO.getPriceDTO().getServiceCharge()//手续费
					+ yeeXingJPAutoOrderDTO.getPriceDTO().getPlatPolicyPirce())
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			HgLogger.getInstance().info("yuqz", "用户支付总价为智行未传userPayA=" + userPayA + ",plfxCommAmount="+plfxCommAmount);
		}
		//中智票量
		else if(command.getFromDealerId().equals("F1002")){
			//平台所得佣金=(用户支付的单张票价 - 支付供应商的单张票价 - 支付宝手续费) * 数量
			plfxCommAmount = (yeeXingJPAutoOrderDTO.getPriceDTO().getPlatTotalPrice()
					- yeeXingJPAutoOrderDTO.getPriceDTO().getTotalPrice())
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			//用户支付的钱   （票面价+机建+燃油）* 个数
			userPayA = yeeXingJPAutoOrderDTO.getPriceDTO().getPlatTotalPrice()
					* command.getYeeXingpassengerInfoDTO().getPassengerList().size();
			HgLogger.getInstance().info("yuqz", "用户支付总价为中智票量未传userPayA=" + userPayA + ",plfxCommAmount="+plfxCommAmount);
		}
		//有时候平台佣金小数位太长,需要统一四舍五入保留两位小数
		this.setCommAmount(this.saveTwoNumberOfDouble(plfxCommAmount));
		this.setUserPayAmount(userPayA);
		//平台支付总价+加价政策
		//设置手续费
		this.setServiceCharge(yeeXingJPAutoOrderDTO.getPriceDTO().getServiceCharge());
		//设置平台价格(单张票)
		this.setPlatTotalPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getTotalPrice());
		//政策id
		this.setPlcid(yeeXingJPAutoOrderDTO.getPriceDTO().getPlcid());
		//机建费用
		this.setBuildFee(yeeXingJPAutoOrderDTO.getPriceDTO().getBuildFee());
		//燃油费
		this.setOilFee(yeeXingJPAutoOrderDTO.getPriceDTO().getOilFee());
		//出票速度
		this.setOutTime(yeeXingJPAutoOrderDTO.getPriceDTO().getOutTime());
		//备注
		this.setMemo(yeeXingJPAutoOrderDTO.getPriceDTO().getMemo());
		//供应商周一至周五工作时间
		this.setWorkTime(yeeXingJPAutoOrderDTO.getPriceDTO().getWorkTime());
		//供应商周六、周日工作时间
		this.setRestWorkTime(yeeXingJPAutoOrderDTO.getPriceDTO().getRestWorkTime());	
		//是否更改pnr
		this.setChangePnr(yeeXingJPAutoOrderDTO.getPriceDTO().getChangePnr());
		//特殊高政策
		this.setIsSphigh(yeeXingJPAutoOrderDTO.getPriceDTO().getIsSphigh());
		//婴儿价
		this.setInfPrice(yeeXingJPAutoOrderDTO.getPriceDTO().getInfPrice());
		//订单创建时间
		this.setCreateTime(yeeXingJPAutoOrderDTO.getCreateTime());
		//订单支付支持方式
		this.setPayPlatform(yeeXingJPAutoOrderDTO.getPriceDTO().getPayType());
		//是否操作成功(T成功F失败)
		this.setIs_success(yeeXingJPAutoOrderDTO.getIs_success());
		//用户订单状态：待支付
		this.setOrderStatus(status);
		//乘机人
		this.setPassangerCount(yeeXingJPAutoOrderDTO.getPassengerList().size());
		List<GNJPPassenger> passengerList = new ArrayList<GNJPPassenger>();
		List<YeeXingPassengerDTO> passangers = command.getYeeXingpassengerInfoDTO().getPassengerList();
		if (passangers != null && passangers.size() > 0) {
			for (YeeXingPassengerDTO psgDto : passangers) {
				GNJPPassenger psg = new GNJPPassenger();
				psg.setId(UUIDGenerator.getUUID());
				//姓名
				psg.setName(psgDto.getPassengerName());
				//旅客类型
				psg.setPsgType(psgDto.getPassengerType());
			    //证件类型
			    psg.setIdType(psgDto.getIdType());
			    //证件号
			    psg.setIdNo(psgDto.getIdNo());
				// 单人票面价
				psg.setFare(yeeXingJPAutoOrderDTO.getPriceDTO().getIbePrice());
				passengerList.add(psg);
			}
			this.setPassengerList(passengerList);
		}
		//保存航班信息
		HgLogger.getInstance().info("yaosanfeng","GNJPOrderLocalService->shopCreateOrder->YeeXingFlightDTO:"+ JSON.toJSONString(yeeXingFlightDTO));
		//出发机场三字码
		if(yeeXingFlightDTO != null){
			this.setStartCityCode(yeeXingFlightDTO.getOrgCity());
			//目的机场三字码
			this.setEndCityCode(yeeXingFlightDTO.getDstCity());
			//航空公司二字码
			this.setCarrier(yeeXingFlightDTO.getAirComp());
			//航空公司名称
			this.setAirCompName(yeeXingFlightDTO.getAirCompanyName());
			//起飞时间
			this.setDepartureTime(yeeXingFlightDTO.getStartTime());
			//结束时间
			this.setEndTime(yeeXingFlightDTO.getEndTime());
			//出发地机场
			this.setStartCityName(yeeXingFlightDTO.getOrgCityName());
			//目的地机场
			this.setEndCityName(yeeXingFlightDTO.getDstCityName());
			//航班号
			this.setFlightNo(yeeXingFlightDTO.getFlightno());
			//航班快照
			this.setFlightSnapshotJSON(JSON.toJSONString(yeeXingFlightDTO));	
		}
		
		GNJPLinker linker = new GNJPLinker();
		
		//联系人姓名
		linker.setName(command.getYeeXingpassengerInfoDTO().getName());
		//联系人手机
		linker.setMobilePhone(command.getYeeXingpassengerInfoDTO().getTelephone());
	
		//下单人
		this.setUserId(command.getYeeXingpassengerInfoDTO().getId());
		
		//下单登录名
	    this.setLoginName(command.getYeeXingpassengerInfoDTO().getName());
	    
		// 分销商id＋订单编号＋流水号，编号长度不固定，流水号从1开始往上做加法；
		this.setOrderNo(command.getOutOrderId());
		
	}

	/****
	 * 同步退票状态 更新
	 * @类名：GNJPOrder.java
	 * @描述：TODO
	 * @@param jpOrder
	 * @@param passenger
	 * @@param command
	 */
	public GNJPOrder(GNJPOrder jpOrder, GNJPPassenger passenger,
			JPRefundQueryOrderStatusSpiCommand command, GNJPOrderStatus status) {
		try {
			BeanUtils.copyProperties(jpOrder, this);
			//更新有变动的字段数据
			//对申请退废生成的订单做处理(单张票的总价,平台所得佣金,用户支付总价,乘机人数)这些数据有变化
			//平台所得佣金(单张利润profits + 平台总的加价政策platPolicyPirce)
			Double plfxCommAmount = jpOrder.getProfits() + jpOrder.getPlatPolicyPirce();
			//单张票的总价=单张票价+基建+燃油
			Double totalPrice = jpOrder.getTickPrice() + jpOrder.getBuildFee() + jpOrder.getOilFee();
			//重新设置平台所得佣金
			this.setCommAmount(plfxCommAmount);
			//重新设置单张票总价
			this.setTotalPrice(totalPrice);
			//重新设置用户支付总价            用户支付总价=单张票总价+平台所得佣金
			this.setUserPayAmount(totalPrice + plfxCommAmount);
			//重新设置乘机人数
			this.setPassangerCount(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String uuid = UUIDGenerator.getUUID();
		this.setId(uuid);
		this.setPassengerList(null);
		List<GNJPPassenger> passengerList = new ArrayList<GNJPPassenger>();
		passenger.setJpOrder(this);
		passenger.setJpCancelOrderId(uuid);
		passengerList.add(passenger);
		this.setPassengerList(passengerList);
		//类型为退废票
		this.setRefundType("T");
		GNJPOrderStatus orderStatus = this.getOrderStatus();
		orderStatus.setStatus(status.getStatus());
		orderStatus.setPayStatus(status.getPayStatus());
	}
	/***
	 * 
	 * @方法功能说明：double四舍五入保留两位小数返回double类型
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年10月19日下午1:35:11
	 * @修改内容：
	 * @参数：@param number
	 * @参数：@return
	 * @return:double
	 * @throws
	 */
	public double saveTwoNumberOfDouble(double number){
		DecimalFormat  formate = new DecimalFormat("#.00");
		double f = Double.parseDouble(formate.format(number)); 
	    return f;
	}
}