package plfx.yeexing.pojo.dto.order;

import java.util.Date;
import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;


/**
 * 
 * @类功能说明：机票平台订单DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月1日上午10:27:28
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderDTO extends BaseJpDTO {

	//经销商相关：
		/**
		 * 经销商订单号 
		 */
		
		private String dealerOrderCode;
		
		/** 
		 * 经销商名字
		 */
	
		private String dealerName;
		/**
		 * 经销商代码
		 */
		
		private String dealerCode;
		/**
		 * 支付平台
		 * 1—支付宝 2—汇付 6—IPS 7—德付通
		 */
	
		private Integer payPlatform;
		/**
		 * 支付方式
		 * 1：自动扣款，2：收银台支付
		 */
		
		private Integer payType;
		/**
		 * 第三方支付单号
		 */
	
		private String payTradeNo;
		
		/** 
		 * 付款账号 
		 */
		
		private String payAccountNo;
		
		/** 
		 * 退款批次号
		 */
		
		private String batchNo;

		/**
		 * 流水号相关：  1.支付宝接口用户付钱，得到用户支付流水号
		 * 		    2. 用户退废票，支付宝接口平台退钱，得到平台退款流水号
		 * 
		 * 			3. 平台想供应商付钱，得到平台支付流水号
		 * 			4. 用户退废票，供应商向平台退钱，得到供应商退款流水号
		 */	
	//易行相关：	
		/**
		 * 易行订单ID :数据库ID
		 */

		private String yeeXingOrderId;
		
	//订单金额
		/**
		 * 支付供应商金额
		 */
		
		private Double totalPrice;
		
		/**
		 * 票面价
		 */
		
		private Double ibePrice;
		
		/**
		 * 返点
		 */
		private Double disc;
		
		/**
		 * 红包  
		 * 返点之外再减
		 */
		
		private Double extReward;
		
		/**
		 * 单张利润
		 */
		
		private Double profits;
		
		/**
		 * 票号类型
		 */
		
		private String tickType;
		
		/**
		 * 婴儿价格
		 */
		
		private Double infPrice;
		
		/**
		 * 单张票价
		 */
		
		private Double tickPrice;
		
		/**
		 * 易行的政策id
		 */
		
		private int plcid;
		
		/**
		 * 出票速度
		 */
	
		private String outTime;
		
		/**
		 * 订单创建时间
		 */
	
		private String createTime;
		
		/**
		 * 备注
		 */
		
		private String memo;
		
		/**
		 * 表示该次操作是否成功 T:成功F：失败
		 */
		
		private String is_success;
		
		/**
		 * 机场建设费
		 */
		
		private Double buildFee;
		
		/**
		 * 是否更改pnr
		 */
		
		private String changePnr;

		private String pnr;
		
		/**
		 * 特殊高政策
		 */
	
		private String isSphigh;
		
		/**
		 *燃油税
		 */
		
		private Double oilFee;
		
		/**
		 *供应商周一至周五工作时间
		 */
	
		private String workTime;
		
		/**
		 *供应商周六、周日工作时间
		 */
	
		private String restWorkTime;
		
		/** 
		 * 平台所得佣金 (单张利润profits * 数量passangerCount + 总的加价政策platPolicyPirce)
		 */
		
		private Double commAmount;
		
		/** 平台政策价格 （自己平台加价部分） */
		/** 平台政策ID （自己平台加价部分） */
		private Double platPolicyPirce;//总的加价政策
	
		private String paltPolicyId;
		
		
		/** 
		 * 用户支付总价 (总票面价totalPrice + 平台所得佣金commAmount) 
		 */
		
		private Double userPayAmount;

	//订单状态： orderStatus 和 type 为必填项

		private  JPOrderStatus orderStatus;
		
		/** 异常订单为1，普通订单为0，默认为0； */
	
		private String type;  

		/** 平台订单号 */
	
		private String orderNo;
		
		/** 乘机人 */

		private List<JPPassengerDTO> passengerList ;
		/***
		 * 乘机人 id,多个用,隔开
		 */
		private  String passengerIds;
		
		/**
		 * 乘机人数量
		 */
	
		private Integer passangerCount;
		/**
		 * 航班信息快照
		 * 对应SlfxFlightDTO
		 */
		
		private String flightSnapshotJSON;
		
		/** 舱位代码 */
		private String classCode;
		
        /***舱位名称     ***/
		private String cabinName;
		/** 下单人  */

		private String userId;
		
		/** 下单登录名 */

		private String loginName;
		
	
		private Double adjustAmount;
		
		/**
		 * 异常订单调整凭证 admin端用
		 */
	
		private String voucherPicture;
		
		/**
		 * 异常订单调整原因 admin端用
		 */
	
		private String adjustReason;
		
		
		/** 航空公司   */
	
		private String airCompName;
		

		/**
		 * 退票时间
		 */
	
		private String refundWorkTime;
		/**
		 * 航班废票截至时间
		 */
	
		private Date wastWorkTime;
		
		
	//退废票相关信息：
		/**  退票订单（如果不为空，则被退废票） */
		private String orderBackId;
		/** 易购退票订单号  */
		
		/** 下单时间 */

		private Date createDate;

		/**
		 * 航班号 string ZH9814
		 */

		private String flightNo;
		/**
		 * 起飞时间
		 */

		private String departureTime;
		/***
		 * 到达时间
		 */
		private String endTime;
		/**
		 * 航空公司二字码
		 */
	
		private String carrier;
		/**
		 * 出发城市代码:admin端用
		 */
	
		private String startCityCode;
		/**
		 * 出发城市名称
		 */
	
		private String startCityName;
		
		/**
		 * 目的城市代码：admin端用
		 */
	
		private String endCityCode;
		
		/**
		 * 到达城市名称
		 */
		private String endCityName;
		
		/**
		 * 手续费
		 */
	
		private Double serviceCharge;
		
		/**
		 * 差价(佣金)
		 */
	
		private Double disparity;
		
		/**
		 * 退废种类
		 * (T退废c取消0代表除去前两种)
		 */
		private String refundType;
		
		/***
		 * 申请种类
		 * 1.	当日作废
           2.	自愿退票
           3.	非自愿退票
           4.	差错退款
           5.	其他
		 */
		private String applyType;
		
		/****
		 * 退废理由
		 */
		private String refundMemo;
		
		/**
		 * 实退金额
		 */
	
		private Double refundPrice;
		
		/**
		 * 退款手续费
		 */
	
		private Double procedures;
		
		/**
		 * 供应商订单号
		 */
		private String supplierOrderNo;
		
		private Double suppPrice;
		/**
		 * 平台支付流水号
		 */
		private String platPaySerialNumber;
		
		public String getPlatPaySerialNumber() {
			return platPaySerialNumber;
		}

		public void setPlatPaySerialNumber(String platPaySerialNumber) {
			this.platPaySerialNumber = platPaySerialNumber;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public Double getSuppPrice() {
			return suppPrice;
		}

		public void setSuppPrice(Double suppPrice) {
			this.suppPrice = suppPrice;
		}

		private Double adjust;
		
		public String getApplyType() {
			return applyType;
		}

		public void setApplyType(String applyType) {
			this.applyType = applyType;
		}

		public String getRefundMemo() {
			return refundMemo;
		}

		public void setRefundMemo(String refundMemo) {
			this.refundMemo = refundMemo;
		}

		public String getDealerOrderCode() {
			return dealerOrderCode;
		}

		public void setDealerOrderCode(String dealerOrderCode) {
			this.dealerOrderCode = dealerOrderCode;
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

		public Integer getPayType() {
			return payType;
		}

		public void setPayType(Integer payType) {
			this.payType = payType;
		}

		public String getPayTradeNo() {
			return payTradeNo;
		}

		public void setPayTradeNo(String payTradeNo) {
			this.payTradeNo = payTradeNo;
		}

		public String getSupplierOrderNo() {
			return supplierOrderNo;
		}

		public void setSupplierOrderNo(String supplierOrderNo) {
			this.supplierOrderNo = supplierOrderNo;
		}

		public Double getAdjust() {
			return adjust;
		}

		public void setAdjust(Double adjust) {
			this.adjust = adjust;
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

		public String getTickType() {
			return tickType;
		}

		public void setTickType(String tickType) {
			this.tickType = tickType;
		}

		public Double getInfPrice() {
			return infPrice;
		}

		public void setInfPrice(Double infPrice) {
			this.infPrice = infPrice;
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

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public String getIs_success() {
			return is_success;
		}

		public void setIs_success(String is_success) {
			this.is_success = is_success;
		}

		public Double getBuildFee() {
			return buildFee;
		}

		public void setBuildFee(Double buildFee) {
			this.buildFee = buildFee;
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

		public Double getOilFee() {
			return oilFee;
		}

		public void setOilFee(Double oilFee) {
			this.oilFee = oilFee;
		}

		public String getWorkTime() {
			return workTime;
		}

		public void setWorkTime(String workTime) {
			this.workTime = workTime;
		}

		public String getRestWorkTime() {
			return restWorkTime;
		}

		public void setRestWorkTime(String restWorkTime) {
			this.restWorkTime = restWorkTime;
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
		
		public List<JPPassengerDTO> getPassengerList() {
			return passengerList;
		}

		public void setPassengerList(List<JPPassengerDTO> passengerList) {
			this.passengerList = passengerList;
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

		public String getClassCode() {
			return classCode;
		}

		public void setClassCode(String classCode) {
			this.classCode = classCode;
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

		public Double getProcedures() {
			return procedures;
		}

		public void setProcedures(Double procedures) {
			this.procedures = procedures;
		}

		public JPOrderStatus getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(JPOrderStatus orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getPassengerIds() {
			return passengerIds;
		}

		public void setPassengerIds(String passengerIds) {
			this.passengerIds = passengerIds;
		}
		
		public String getPnr() {
			return pnr;
		}

		public void setPnr(String pnr) {
			this.pnr = pnr;
		}

		public String getCabinName() {
			return cabinName;
		}

		public void setCabinName(String cabinName) {
			this.cabinName = cabinName;
		}
		
	
}