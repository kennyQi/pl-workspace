package slfx.jp.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import slfx.jp.command.admin.AdminCancelOrderCommand;
import slfx.jp.command.admin.ApplyRefundCommand;
import slfx.jp.command.admin.JPOrderCommand;
import slfx.jp.command.admin.jp.JPAskOrderTicketSpiCommand;
import slfx.jp.command.admin.jp.JPOrderCreateSpiCommand;
import slfx.jp.command.client.YGAskOrderTicketCommand;
import slfx.jp.domain.model.J;
import slfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import slfx.jp.pojo.dto.dealer.DealerDTO;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.dto.policy.PolicyDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightOrderDTO;
import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderDTO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：机票平台订单 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:27:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "ORDER")
public class JPOrder extends BaseModel {

	public JPOrder() {
		super();
	}
	
	/**
	 * 新增废单记录
	 * @类名：JPOrder.java
	 * @描述：TODO
	 * @@param jpOrder
	 * @@param command
	 * @@param ygQueryOrderDTO
	 * @@param orderStatus
	 */
	public JPOrder(JPOrder jpOrder,ApplyRefundCommand command,YGQueryOrderDTO ygQueryOrderDTO,JPOrderStatus orderStatus) {
		BeanUtils.copyProperties(jpOrder, this);
		Set<Passenger> newPassangerList = new HashSet<Passenger>(); 
		Set<Passenger> passangerList = jpOrder.getPassangerList();
		Iterator<Passenger> iter = passangerList.iterator();
		Passenger tempPassenger = null;
		
		String ticketNos = command.getTicketNos();
		while (iter.hasNext()) {
			tempPassenger = iter.next();
			if (null == tempPassenger || null == tempPassenger.getTicket())
				continue;
			
			FlightTicket flightTicket = tempPassenger.getTicket();
			if (StringUtils.isNotBlank(flightTicket.getTicketNo()) && ticketNos.contains(flightTicket.getTicketNo())) {
				String refund = "";
				refund = JPOrderStatusConstant.CATEGORY_BACK_VAL;
				flightTicket.setRefund(refund);
				newPassangerList.add(tempPassenger);
			}
		}
		this.setId(UUIDGenerator.getUUID());
		this.setYgOrderNo(ygQueryOrderDTO.getOrderNo());
		this.setSupplierOrderNo(ygQueryOrderDTO.getPlatOrderNo());
		
		
		this.setOrderStatus(orderStatus);
		this.setPassangerList(newPassangerList);
		Integer backCateGory = JPOrderStatusConstant.CATEGORY_BACK;
		
		
		this.setBackCategory(backCateGory);
		this.setTktPrice(ygQueryOrderDTO.getFare());
		this.setTktTax(this.getTktTax() == 0.0d ? 0.0d:(-this.getTktTax()));
		this.setDisparity(this.getDisparity() == 0.0d?0.0d:(-this.getDisparity()));
		this.setServiceCharge(this.getServiceCharge() == 0.0d ? 0.0d:(-this.getServiceCharge()));
		//退废票后的新单，查询结果没有这些数据
		//this.setTktTax(ygQueryOrderDTO.getTaxAmount());
		//this.setUserPayAmount(ygQueryOrderDTO.getPayMoney());
		//jpOrder.setPlatPolicyPirce(jpOrder.getPlatPolicyPirce() * ratio);
		//jpOrder.setSuppPrice(jpOrder.getSuppPrice() * ratio);
		//jpOrder.setCommRate(jpOrder.getCommRate() * ratio);
		//this.setCommAmount(ygQueryOrderDTO.getCommAmount());
		
		this.setCreateDate(new Date());
		if(StringUtils.isNotBlank(command.getRefundType())){
			this.setRefundType(command.getRefundType());			
		}
	}
	
	/**
	 * 新增订单
	 * @类名：JPOrder.java
	 * @描述：TODO
	 * @@param jPOrderCreateCommand
	 * @@param yGFlightOrderDTO
	 * @@param policyDTO
	 * @@param flightDto
	 * @@param jpPlatformPolicySnapshot
	 * @@param dealerDTO
	 */
	public JPOrder(JPOrderCreateSpiCommand jPOrderCreateCommand,
			YGFlightOrderDTO yGFlightOrderDTO,
			PolicyDTO  policyDTO,
			SlfxFlightDTO flightDto,
			JPPlatformPolicySnapshot jpPlatformPolicySnapshot,
			DealerDTO dealerDTO,
			JPOrderStatus status) {
		
		this.setId(UUIDGenerator.getUUID());
		this.setClassCode(jPOrderCreateCommand.getClassCode());
		this.setUserId(jPOrderCreateCommand.getLinker().getId());
		this.setLoginName(jPOrderCreateCommand.getLinker()
				.getLoginName());
		this.setDealerOrderCode(jPOrderCreateCommand.getDealerOrderId());
		this.setDealerCode(jPOrderCreateCommand.getFromClientKey());
		if (null != dealerDTO) {
			this.setDealerName(dealerDTO.getName());
		}
		this.setYgOrderId(yGFlightOrderDTO.getId());
		this.setYgOrderNo(yGFlightOrderDTO.getYgOrderNo());
		this.setAgencyName(yGFlightOrderDTO.getSupplierName());
		this.setPlatCode(yGFlightOrderDTO.getPlatCode());
		this.setSuppShortName(jPOrderCreateCommand.getPlatCode());
		this.setSupplierOrderNo(yGFlightOrderDTO.getSupplierOrderNo());
		this.setAbeOrderId(jPOrderCreateCommand.getAbeOrderId());
		Double totalTktPrice = yGFlightOrderDTO.getPsgCount()
				* yGFlightOrderDTO.getFare();
		this.setTktPrice(totalTktPrice);
		this.setTktTax(yGFlightOrderDTO.getTaxAmount());
		this.setSuppPrice(yGFlightOrderDTO.getBalanceMoney());
		this.setCommRate(yGFlightOrderDTO.getCommRate());
		this.setCommAmount(yGFlightOrderDTO.getCommAmount());
		
		//配合使用红包
		// 手续费 无论是原始数据小数点是1位还是2位或者2位以上都要转为2位
		this.setServiceCharge(0.00d);		
		// 用户订单价格
		Double userPayAmount = (yGFlightOrderDTO.getTaxAmount() + yGFlightOrderDTO.getFare());
		this.setUserPayAmount(userPayAmount);		
		// 差价
		this.setDisparity(yGFlightOrderDTO.getCommAmount());
		
		/*裸价促销暂停-201412-01
		// 根据最新的产品来更改价格算法
		if(yGFlightOrderDTO.getCommRate() != null && yGFlightOrderDTO.getCommRate() >= 2 ){
			// 手续费 无论是原始数据小数点是1位还是2位或者2位以上都要转为2位
			this.setServiceCharge((yGFlightOrderDTO.getTaxAmount() + yGFlightOrderDTO.getFare()) * 0.008);
			
			// 用户订单价格
			Double userPayAmount = ((yGFlightOrderDTO.getTaxAmount() + yGFlightOrderDTO.getFare()) - yGFlightOrderDTO.getCommAmount() + this.getServiceCharge());
			this.setUserPayAmount(Math.ceil(userPayAmount / 10) * 10);
			
			// 差价
			this.setDisparity(this.getUserPayAmount() - userPayAmount);
		}else{
			this.setServiceCharge(0d);
			//设置佣金
			this.setDisparity(yGFlightOrderDTO.getCommAmount());
			this.setUserPayAmount(yGFlightOrderDTO.getFare() * yGFlightOrderDTO.getPsgCount() + yGFlightOrderDTO.getTaxAmount());
		}*/

		/* 分销平台价格政策屏蔽-20141201-tandeng
		Double adjust = 0.0;
		if (null != policyDTO) {
			Double pricePolicy = policyDTO.getPricePolicy();
			Double percentPolicy = policyDTO.getPercentPolicy();
			if (pricePolicy != null) {// 价格政策为 增加或则减少多少元计算
				adjust = (int) pricePolicy.doubleValue() + 0.0; // 价格有小数，只入不舍
				if (pricePolicy.doubleValue() > (int) pricePolicy
						.doubleValue()) {
					adjust += 1.0;
				}

			} else if (percentPolicy != null) {// 价格政策 以百分比计算 = 票面价 * 百分比
				double d = totalTktPrice * percentPolicy;
				adjust = (int) (d) + 0.0; // 价格有小数，只入不舍
				if (d > (int) d) {
					adjust += 1.0;
				}

			}

			userPayAmount += adjust;
			this.setPaltPolicyId(policyDTO.getId());
		}

		this.setPlatPolicyPirce(adjust * yGFlightOrderDTO.getPsgCount());
		this.setUserPayAmount(userPayAmount);
		 */
		
		//???用户订单状态：待支付
		this.setOrderStatus(status);
		// 乘机人
		this.setPassangerCount(yGFlightOrderDTO.getPsgCount());
		Set<Passenger> passangerList = new HashSet<Passenger>();
		List<FlightPassengerDTO> passangers = jPOrderCreateCommand
				.getPassangers();
		if (passangers != null && passangers.size() > 0) {
			for (FlightPassengerDTO psgDto : passangers) {

				Passenger psg = new Passenger();

				psg.setId(UUIDGenerator.getUUID());
				psg.setName(psgDto.getName());
				psg.setPsgType(psgDto.getPassangerType());
				psg.setIdentityType(psgDto.getIdentityType());
				psg.setCardType(psgDto.getCardType());

				String idCard = psgDto.getCardNo();
				psg.setCardNo(idCard);
				if (idCard != null && idCard.length() == 18) {
					// 18位
					psg.setBirthDay(idCard.substring(6, 10) + "-"
							+ idCard.substring(10, 12) + "-"
							+ idCard.substring(12, 14));
				} else if (idCard != null && idCard.length() == 15) {
					// 15位
					psg.setBirthDay("19" + idCard.substring(6, 8) + "-"
							+ idCard.substring(8, 10) + "-"
							+ idCard.substring(10, 12));
				}
				// psg.setCountry(psgDto.getCountry());
				psg.setMobilePhone(psgDto.getMobile());
				psg.setInsueFee(psgDto.getInsueFee());
				psg.setInsueSum(psgDto.getInsueSum());
				// 单人票面价
				psg.setFare(yGFlightOrderDTO.getFare());
				// 单人平台供给商城销售价
				// psg.setSalePrice(yGFlightOrderDTO.getFare() + adjust);
				// 单人（基建+燃油）
				psg.setTaxAmount(yGFlightOrderDTO.getTaxAmount()
						/ yGFlightOrderDTO.getPsgCount());

				psg.setPsgId(psgDto.getPsgNo());

				FlightTicket ticket = new FlightTicket();
				if (yGFlightOrderDTO != null) {
					double fare = yGFlightOrderDTO.getFare();
					psg.setFare(fare);
					// 税款合计 = 机场建设费+燃油费
					double taxAmount = jpPlatformPolicySnapshot
							.getAirportTax()
							+ jpPlatformPolicySnapshot.getFuelSurTax();
					psg.setTaxAmount(taxAmount);

					// 销售价 = 票面价+税款合计
					double amount = 0.0;
					if (null != yGFlightOrderDTO
							&& yGFlightOrderDTO.getFare() != null) {
						amount = yGFlightOrderDTO.getFare() + taxAmount;
					}
					psg.setSalePrice(amount);
					ticket.setId(UUIDGenerator.getUUID());// id
					ticket.setPrice(amount);// 单人支付价格
					ticket.setAirportTax(jpPlatformPolicySnapshot
							.getAirportTax());// 机场建设费
					ticket.setFuelSurTax(jpPlatformPolicySnapshot
							.getFuelSurTax());// 燃油
					// 票号需要支付后获取
				}

				psg.setTicket(ticket);

				passangerList.add(psg);

			}
			this.setPassangerList(passangerList);
		}

		// 比价政策id
		this.setComparePocilyId(jPOrderCreateCommand.getComparePocilyId());
		// 航班信息
		flightDto.setPolicyRemark(jpPlatformPolicySnapshot.getRemark());
		this.setFlightSnapshotJSON(JSON.toJSONString(flightDto));
		this.setAirCompName(flightDto.getAirCompName());
		this.setCarrier(flightDto.getCarrier());
		this.setDepartureTime(flightDto.getStartDate() + " "
				+ flightDto.getStartTime());
		this.setStartCityCode(flightDto.getStartCityCode());
		this.setStartCityName(flightDto.getStartCity());
		this.setEndCityCode(flightDto.getEndCityCode());
		this.setEndCityName(flightDto.getEndCity());
		this.setFlightNo(flightDto.getFlightNo());
		Linker linker = new Linker();
		linker.setPayType("ZH");
		if (jPOrderCreateCommand.getLinker() != null) {
			linker.setMobilePhone(jPOrderCreateCommand.getLinker()
					.getMobile());
		}
		this.setLinker(linker);
		this.setPnr(yGFlightOrderDTO.getPnr());
		this.setWorkTime(jpPlatformPolicySnapshot.getTktWorktime());
		this.setRefundWorkTime(jpPlatformPolicySnapshot.getRfdWorktime());
		
		String wastWorkTime = jpPlatformPolicySnapshot
				.getWastWorkTime();// 23:59止
		if (StringUtils.isNotBlank(wastWorkTime)) {
			if (wastWorkTime.contains("止")) {
				wastWorkTime = wastWorkTime.replaceAll("止", "");
			}
		} else {
			wastWorkTime = "23:59";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		wastWorkTime = date + " " + wastWorkTime;

		this.setWastWorkTime(DateUtil.parseDate5(wastWorkTime));
		
		this.setSuppShortName(yGFlightOrderDTO.getPlatCode());
		this.setType(JPOrderStatusConstant.COMMON_TYPE);
		// 分销商id＋订单编号＋流水号，编号长度不固定，流水号从1开始往上做加法；
		this.setOrderNo(jPOrderCreateCommand.getFromClientKey()
				+ jPOrderCreateCommand.getDealerOrderId());
		this.setCreateDate(new Date());
	}
	
	public void  updateOrderStatus(JPOrderCommand command){
		JPOrderStatus orderStatus = new JPOrderStatus();
		orderStatus.setStatus(command.getStatus());
		setOrderStatus(orderStatus);
	}
	
	public  void cancelOrderNotify(JPOrderCommand command){
		setOrderBackId(command.getId());
		setYgRefundOrderNo(command.getYgOrderNo());
		setRefundPlatformOrderNo(command.getSupplierOrderNo());
		if (command.getStatus() != null) {
			JPOrderStatus status = new JPOrderStatus();
			status.setStatus(command.getStatus());
			setOrderStatus(status);
		}

	}
	
	/**
	 * 请求出票成功
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年11月24日下午4:09:52
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @参数：@param orderStatus
	 * @参数：@return
	 * @return:JPOrder
	 * @throws
	 */
	public JPOrder shopAskOrderTicketSucc(JPOrder jpOrder,
			JPAskOrderTicketSpiCommand command,
			JPOrderStatus orderStatus){
		jpOrder.setBuyerEmail(command.getBuyerEmail());
		jpOrder.setPayTradeNo(command.getPayOrderId());
		jpOrder.setCustomerPaySerialNumber(command.getPayOrderId());
		jpOrder.setOrderStatus(orderStatus);
		return jpOrder;
	}
	
	/**
	 * 请求出票失败
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年11月24日下午4:09:52
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @参数：@param orderStatus
	 * @参数：@return
	 * @return:JPOrder
	 * @throws
	 */
	public JPOrder shopAskOrderTicketFail(JPOrder jpOrder,
			JPAskOrderTicketSpiCommand command,
			JPOrderStatus orderStatus){
		jpOrder.setBuyerEmail(command.getBuyerEmail());
		jpOrder.setPayTradeNo(command.getPayOrderId());
		jpOrder.setCustomerPaySerialNumber(command.getPayOrderId());
		jpOrder.setOrderStatus(orderStatus);
		return jpOrder;
	}
	
	/**
	 * 取消机票
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年11月24日下午4:09:52
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @参数：@param orderStatus
	 * @参数：@return
	 * @return:JPOrder
	 * @throws
	 */
	public JPOrder shopCancelOrder(JPOrder jpOrder,
			AdminCancelOrderCommand command,
			JPOrderStatus orderStatus){
		jpOrder.setOrderStatus(orderStatus);
		return jpOrder;
	}

	/**
	 * 退/废机票
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年11月24日下午4:09:52
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @参数：@param orderStatus
	 * @参数：@return
	 * @return:JPOrder
	 * @throws
	 */
	public JPOrder adminRefundOrder(JPOrder jpOrder,
			ApplyRefundCommand command,
			JPOrderStatus orderStatus){
		jpOrder.setOrderStatus(orderStatus);
		if(StringUtils.isNotBlank(command.getRefundType())){
			jpOrder.setRefundType(command.getRefundType());			
		}
		return jpOrder;
	}
	
	/**
	 * 更改机票状态
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年11月24日下午4:09:52
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @参数：@param orderStatus
	 * @参数：@return
	 * @return:JPOrder
	 * @throws
	 */
	public JPOrder updateOrderStatus(JPOrder jpOrder,
			JPOrderCommand command,
			JPOrderStatus orderStatus){
		jpOrder.setOrderStatus(orderStatus);
		return jpOrder;
	}
	
	/**
	 * 更改机票状态
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年11月24日下午4:09:52
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @参数：@param orderStatus
	 * @参数：@return
	 * @return:JPOrder
	 * @throws
	 */
	public JPOrder adminCancelOrder(JPOrder jpOrder,
			AdminCancelOrderCommand command,
			JPOrderStatus orderStatus){
		jpOrder.setOrderStatus(orderStatus);
		return jpOrder;
	}
	/**
	 *请求出票LocalPlatformAskOrderTicketService
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年11月24日下午4:09:52
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@param command
	 * @参数：@param orderStatus
	 * @参数：@return
	 * @return:JPOrder
	 * @throws
	 */
	public JPOrder askOrderTicket(JPOrder jpOrder,
			YGAskOrderTicketCommand command,
			JPOrderStatus orderStatus){
		jpOrder.setOrderStatus(orderStatus);
		jpOrder.setPayTradeNo(command.getPayOrderId());
		return jpOrder;
	}
	

	
	
	//经销商相关：
	/** 经销商订单号 */
	@Column(name = "DEALER_ORDER_CODE", length = 64 )
	private String dealerOrderCode;
	/** 经销商名字 */
	@Column(name = "DEALER_NAME", length = 64 )
	private String dealerName;
	/** 经销商代码  */
	@Column(name="DEALER_CODE", length=64)
	private String dealerCode;
	
	/**第三方支付单号 */
	@Column(name = "PAY_TRADE_NO")
	private String payTradeNo;
	
	/** 付款支付宝账号 */
	@Column(name = "BUYER_EMAIL") 
	private String buyerEmail;
	
	/** 退款批次号 */
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

//易购相关：	
	/** 易购订单ID :数据库ID*/
	@Column(name = "YG_ORDER_ID", length = 64)
	private String ygOrderId;
	/** 易购订单号 */
	@Column(name = "YG_ORDER_NO", length = 64)
	private String ygOrderNo;

//供应商-商家相关：
	/** 比价政策id */
	@Column(name = "COMPARE_POCILY_ID", length = 64)
	private String comparePocilyId;
	
	/** 商家信息  */
	@Column(name = "AGENCY_NAME",length = 200)
	private String agencyName;
	
//	/** 商家联系方式(初始数据的时候，多个号码请用空格分割)  */
//	@Column(name = "AGENCY_TEL",length = 200)
//	private String agencyTel;	
	/**  出票平台code  供应商的代号如：（001或BT）、（002或B5） 对应agencyName */
	@Column(name = "PLAT_CODE", length = 64)
	private String platCode;
	/** 供应商代码  */
	@Column(name = "SUPP_SHORT_NAME",length = 16)
	private String suppShortName;
	/** 供应商商订单号 */
	@Column(name = "SUPPLIER_ORDE_NO", length = 64)
	private String supplierOrderNo;
	
//ABE相关：
	/** abe订单ID */
	@Column(name = "ABE_ORDER_ID", length = 64)
	private String abeOrderId;

//订单金额
	/** 机票总票面价  易购(fare * psgCount)*/
	@Column(name = "TKT_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double tktPrice;
	
	/**所有乘客的  易购（TaxAmount）*/
	@Column(name = "TKT_TAX", columnDefinition = J.MONEY_COLUM)
	private Double tktTax;
	
	/**  供应商价格 易购（BalanceMoney） */
	@Column(name = "SUPP_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double suppPrice;
	
	/** 返点 */
	@Column(name = "COMM_RATE", columnDefinition = J.MONEY_COLUM)
	private Double commRate;
	
	/** 平台所得佣金 易购（CommAmount） 或者 （Fare * PsgCount - BalanceMoney - TaxAmount） */
	@Column(name = "COMM_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double commAmount;
	
	/** 平台政策价格 （自己平台加价部分） */
	/** 平台政策ID （自己平台加价部分） */
	@Column(name = "PLAT_POLICY_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double platPolicyPirce;//总的加价政策
	@Column(name = "PLAT_POLICY_ID", length = 64)
	private String paltPolicyId;
	
	
	/** 用户支付总价 [易购（Fare * PsgCount + TaxAmount） + 加价政策金额]  */
	@Column(name = "USER_PAY_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double userPayAmount;

//订单状态： orderStatus 和 type 为必填项
	/** 订单状态 */
	@Embedded
	private JPOrderStatus orderStatus;
	
	
	/** 异常订单为1，普通订单为0，默认为0； */
	@Column(name = "TYPE", length = 2)
	private String type;  

	/** 平台订单号 */
	@Column(name = "ORDER_NO", length = 64)
	private String orderNo;
	
	/** 乘机人 */
	@OneToMany(fetch=FetchType.EAGER, mappedBy="jpOrder")
//	@JoinColumn(name="JP_ORDER_ID")
	private Set<Passenger> passangerList ;
	
	@Column(name = "PASSANGER_COUNT", columnDefinition = J.DOUBLE_COLUM)
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
	/** 下单人 */
	@Embedded
	private Linker linker;
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
	
	/**
	 * 出票工作时间
	 */
	@Column(name = "WORK_TIME", length = 64)
	private String workTime;
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
	/**   退废类型：详情参考 JPOrderStatus.CATEGORY_REFUND   */
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
	@Column(name = "SERVICE_CHARGE", columnDefinition = J.MONEY_COLUM)
	private Double serviceCharge;
	
	/**
	 * 差价(佣金)
	 */
	@Column(name = "DISPARITY", columnDefinition = J.MONEY_COLUM)
	private Double disparity;
	
	/**
	 * T:退票单
	 * F:废票单
	 */
	@Column(name = "REFUND_TYPE", columnDefinition = J.CHAR_COLUM)
	private String refundType;
	
	/**
	 * 退废总金额
	 */
	@Column(name = "REFUND_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double refundPrice;
	
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
	public String getRefundWorkTime() {
		return refundWorkTime;
	}
	public void setRefundWorkTime(String refundWorkTime) {
		this.refundWorkTime = refundWorkTime;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getPlatCode() {
		return platCode;
	}


	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}


	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}


	public String getAbeOrderId() {
		return abeOrderId;
	}


	public void setAbeOrderId(String abeOrderId) {
		this.abeOrderId = abeOrderId;
	}

	public String getYgOrderNo() {
		return ygOrderNo;
	}

	public void setYgOrderNo(String ygOrderNo) {
		this.ygOrderNo = ygOrderNo;
	}


	public String getUserId() {
		return userId;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}


	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/*public String getAgencyTel() {
		return agencyTel;
	}
	public void setAgencyTel(String agencyTel) {
		this.agencyTel = agencyTel;
	}*/

	public Double getTktPrice() {
		return tktPrice;
	}

	public void setTktPrice(Double tktPrice) {
		this.tktPrice = tktPrice;
	}

	public Double getTktTax() {
		return tktTax;
	}

	public void setTktTax(Double tktTax) {
		this.tktTax = tktTax;
	}

	public Double getUserPayAmount() {
		return userPayAmount;
	}
	
	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}
	
	public Double getCommRate() {
		return commRate;
	}


	public void setCommRate(Double commRate) {
		this.commRate = commRate;
	}


	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}
	
	public JPOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(JPOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFlightSnapshotJSON() {
		return flightSnapshotJSON;
	}

	public void setFlightSnapshotJSON(String flightSnapshotJSON) {
		this.flightSnapshotJSON = flightSnapshotJSON;
	}

	public Set<Passenger> getPassangerList() {
		return passangerList;
	}

	public void setPassangerList(Set<Passenger> passangerList) {
		this.passangerList = passangerList;
	}

	public Integer getPassangerCount() {
		if (null == passangerCount) {
			if (null != passangerList) {
				passangerCount = passangerList.size(); 
			}
		}
		return passangerCount;
	}
	public void setPassangerCount(Integer passangerCount) {
		this.passangerCount = passangerCount;
	}
	public String getYgOrderId() {
		return ygOrderId;
	}

	public void setYgOrderId(String ygOrderId) {
		this.ygOrderId = ygOrderId;
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
	public Double getSuppPrice() {
		return suppPrice;
	}

	public void setSuppPrice(Double suppPrice) {
		this.suppPrice = suppPrice;
	}


	public String getSuppShortName() {
		return suppShortName;
	}


	public void setSuppShortName(String suppShortName) {
		this.suppShortName = suppShortName;
	}


	public Linker getLinker() {
		return linker;
	}


	public void setLinker(Linker linker) {
		this.linker = linker;
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


	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getComparePocilyId() {
		return comparePocilyId;
	}
	public void setComparePocilyId(String comparePocilyId) {
		this.comparePocilyId = comparePocilyId;
	}
	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
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
	

	
	public String getFlightNo() {
		return flightNo;
	}


	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}


	public String getCarrier() {
		return carrier;
	}


	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}


	public String getStartCityName() {
		return startCityName;
	}


	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}


	public String getEndCityName() {
		return endCityName;
	}


	public void setEndCityName(String endCityName) {
		this.endCityName = endCityName;
	}


	public String getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getStartCityCode() {
		return startCityCode;
	}
	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}


	public String getEndCityCode() {
		return endCityCode;
	}
	public void setEndCityCode(String endCityCode) {
		this.endCityCode = endCityCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPayTradeNo() {
		return payTradeNo;
	}
	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}
	
	

	public void saveJPOrderInfo(){
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}
	
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	
	public String getBatchNo() {
		return batchNo;
	}
	
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	
	//	/**
//	 * 航程	admin端用
//	 */
//	@Column(name = "FLIGHT_WAY", length = 10)
//	private String flightay;	
//	public void setFlightay(String flightay) {
//		this.flightay = flightay;
//	}
//	public String getFlightay() {
//		return flightay;
//	}

}