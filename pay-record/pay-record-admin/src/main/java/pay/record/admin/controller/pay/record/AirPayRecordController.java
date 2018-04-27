package pay.record.admin.controller.pay.record;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pay.record.admin.controller.BaseController;
import pay.record.pojo.command.CreateAirPayReocrdSpiCommand;
import pay.record.pojo.command.ModifyAirPayReocrdSpiCommand;
import pay.record.pojo.dto.AirPayRecordDTO;
import pay.record.pojo.qo.pay.AirPayRecordQO;
import pay.record.pojo.system.AirPayRecordConstants;
import pay.record.spi.inter.pay.AirPayRecordService;

import com.alibaba.fastjson.JSON;

/****
 * 
 * @类功能说明：机票记录Controller
 * @类修改者：yaosanfeng
 * @修改日期：2015年12月10  10:00
 * @修改说明：完成添加,修改记录功能
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月10日上午9:56:41
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value = "/payRecord/air")
public class AirPayRecordController extends BaseController{
	@Autowired
	private AirPayRecordService airPayRecordService;
	
	private static String AIR_LIST = "airpayrecord/list.html";
	private static String AIR_ADD = "airpayrecord/add.html";
	private static String AIR_AUDIT = "airpayrecord/audit.html";
	private static String AIR_EXPORT = "airpayrecord/export.html";
	/**
	 * 
	 * @方法功能说明：查询机票支付记录
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月04日下午2:02:26
	 * @修改内容：添加map常量
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryPayRecordList(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute AirPayRecordQO airPayRecordQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr1", required = false) String beginTimeStr1,
			@RequestParam(value = "endTimeStr1", required = false) String endTimeStr1) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);
		
		if (null != beginTimeStr1 && !"".equals(beginTimeStr1)) {
			airPayRecordQO.setPayDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr1));
		}
		if (null != endTimeStr1 && !"".equals(endTimeStr1)) {
			airPayRecordQO.setPayDateTo(DateUtil.dateStr2EndDate(endTimeStr1));
		}
		//按创建时间倒序排序
		airPayRecordQO.setCreateDateAsc(false);
		
		pagination.setCondition(airPayRecordQO);
		pagination = airPayRecordService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("airPayRecordQO", airPayRecordQO);
		
		//订单状态
		model.addAttribute("statusList", AirPayRecordConstants.PLFX_JPORDER_STATUS_LIST);
		//支付状态
		model.addAttribute("orderPayStatusList", AirPayRecordConstants.PLFX_JPORDER_PAY_STATUS_LIST);
		//记录类型
		model.addAttribute("PAY_RECORD_TYPE_MAP", AirPayRecordConstants.RECORD_TYEP_MAP);
		//项目来源
		model.addAttribute("PAY_RECORD_PROJECT_TYPE_MAP", AirPayRecordConstants.FROM_PROJECT_CODE_MAP);
		//支付方式
		model.addAttribute("PAYMENT_TYPE", AirPayRecordConstants.PAY_PLATFORM_MAP);
		return AIR_LIST;
	}
	
	/****
	 * 
	 * @方法功能说明：导出机票支付记录信息Excel
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月4日下午3:02:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param payRecordQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
//	@RequestMapping("/export")
//	@ResponseBody
//	public String exportAirRecordExcel(HttpServletRequest request,
//			HttpServletResponse response,AirPayRecordQO airPayRecordQO,
//			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
//			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
//			) {
//		
//		HgLogger.getInstance().info("yaosanfeng","AirPayRecordController->getJPPaymentRecordExcel->airPayRecordQO:"+JSON.toJSONString(airPayRecordQO));
//		
//		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
//			airPayRecordQO.setPayDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
//		}
//		if (null != endTimeStr && !"".equals(endTimeStr)) {
//			airPayRecordQO.setPayDateTo(DateUtil.dateStr2EndDate(endTimeStr));
//		}
//		//按创建时间倒序排序
//		airPayRecordQO.setCreateDateAsc(false);
//		
//		OutputStream os;
//		WritableWorkbook workbook;
//		//设置内容格式
//		response.setHeader("Content-Type","application/x-xls;charset=utf-8" );
//		//这个用来提示下载的文件名
//		response.setHeader("Content-Disposition", "attachment; filename="+"机票支付记录表"+DateUtil.formatDateTime(new Date(), "yyyy-MM-dd")+".xls");
//		try {
//			os = response.getOutputStream();
//			workbook = Workbook.createWorkbook(os);
//			//查询列表List
//			
//			List<AirPayRecordDTO> airPaymentRecordList = airPayRecordService.queryAirPaymentRecordList(airPayRecordQO);
//			HgLogger.getInstance().info("yaosanfeng", "AirPayRecordController->getJPPaymentRecordExcel->jPPaymentRecordList"+JSON.toJSONString(airPaymentRecordList));
//			if(airPaymentRecordList.size() > 0){
//				WritableSheet ws = workbook.createSheet("机票佣金收入表", 0);
//				Label head1 = new Label(0,0,"订单来源");//来源项目标识
//				ws.addCell(head1);
//				Label head2 = new Label(1,0,"订购人");
//				ws.addCell(head2);
//				Label head3 = new Label(2,0,"是否为内部员工差旅");
//				ws.addCell(head3);
//				Label head4 = new Label(3,0,"机票订购");
//				ws.addCell(head4);
//				Label head5 = new Label(4,0,"订单时间");
//				ws.addCell(head5);
//				Label head6 = new Label(5,0,"易行订单号");
//				ws.addCell(head6);
//				Label head7 = new Label(6,0,"平台订单号");
//				ws.addCell(head7);
//				Label head8 = new Label(7,0,"乘机人");
//				ws.addCell(head8);
//				Label head9 = new Label(8,0,"始发地");
//				ws.addCell(head9);
//				Label head10 = new Label(9,0,"目的地");
//				ws.addCell(head10);
//				Label head11 = new Label(10,0,"订单状态");
//				ws.addCell(head11);
//				Label head12 = new Label(11,0,"订单金额");
//				ws.addCell(head12);
//				Label head13 = new Label(12,0,"返点");
//				ws.addCell(head13);
//				Label head14 = new Label(13,0,"佣金");
//				ws.addCell(head14);
//				Label head15 = new Label(14,0,"付款方式");
//				ws.addCell(head15);
//				Label head16 = new Label(15,0,"收入");
//				ws.addCell(head16);
//				Label head17 = new Label(16,0,"支出");
//				ws.addCell(head17);
//				Label head18 = new Label(17,0,"优惠卷");
//				ws.addCell(head18);
//				Label head19 = new Label(18,0,"是否收到订票平台退款");
//				ws.addCell(head19);
//				Label head20 = new Label(19,0,"订票平台退款金额");
//				ws.addCell(head20);
//				Label head21 = new Label(20,0,"是否退款给客户");
//				ws.addCell(head21);
//				Label head22 = new Label(21,0,"退给客户金额");
//				ws.addCell(head22);
//				Label head23 = new Label(22,0,"实退金额");
//				ws.addCell(head23);
//				Label head24 = new Label(23,0,"差价");
//				ws.addCell(head24);
//				Label head25 = new Label(24,0,"商品名称");
//				ws.addCell(head25);
//				Label head26 = new Label(25,0,"发生时间");
//				ws.addCell(head26);
//				Label head27 = new Label(26,0,"对方账号");
//				ws.addCell(head27);
//				Label head28 = new Label(27,0,"业务类型");
//				ws.addCell(head28);
//				Label head29 = new Label(28,0,"账务流水号");
//				ws.addCell(head29);
//				Label head30 = new Label(29,0,"商户订单号");
//				ws.addCell(head30);
//				
//				int i = 1;
//				//总支出
//				Double expenses = 0d;
//				//总收入
//				Double income = 0d;
//				//订票平台退款总金额
//				Double refundPrice =0d;
//				for(AirPayRecordDTO airPayRecordDTO : airPaymentRecordList){
//					//订单来源
//					if(null != airPayRecordDTO.getFromProjectCode()){
//						Label fromProjectCode = new Label(0, i, AirPayRecordConstants.FROM_PROJECT_CODE_MAP.get(airPayRecordDTO.getFromProjectCode().toString()));
//						ws.addCell(fromProjectCode); 
//					}else{
//						Label fromProjectCode = new Label(0, i, null);
//						ws.addCell(fromProjectCode); 
//					}
//					//订购人
//					Label booker = new Label(1,i,airPayRecordDTO.getBooker());
//					ws.addCell(booker);
//					
//					//是否为内部员工差旅(默认先不填)
//					Label isEmployee = new Label(2,i,null);
//					ws.addCell(isEmployee);
//					
//					//机票订购(默认易行机票)
//					Label ticket = new Label(3,i,"易行机票");
//					ws.addCell(ticket);
//					
//					//订单时间(下单时间)
//					if(null != airPayRecordDTO.getCreateTime()){
//						Label createTime= new Label(4,i,DateUtil.formatDateTime(airPayRecordDTO.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
//						ws.addCell(createTime);
//					}else{
//						Label createTime= new Label(4,i,null);
//						ws.addCell(createTime);
//					}
//					
//					//易行订单号
//					Label supplierNo = new Label(5, i, airPayRecordDTO.getSupplierNo());
//					ws.addCell(supplierNo);
//					
//					//平台订单号
//					Label platOrderNo = new Label(6,i,airPayRecordDTO.getPlatOrderNo());
//					ws.addCell(platOrderNo);
//					
//					//乘机人
//					Label passengers = new Label(7,i,airPayRecordDTO.getPassengers());
//					ws.addCell(passengers);
//					
//					//始发地
//					Label startCity = new Label(8,i,airPayRecordDTO.getStartCity());
//					ws.addCell(startCity);
//					
//					//目的地
//					Label destCity = new Label(9,i,airPayRecordDTO.getDestCity());
//					ws.addCell(destCity);
//					
//					//订单状态(也包括支付状态,格式:已出票/已支付)
//					KeyValue obj1 = (KeyValue)CollectionUtils.find(AirPayRecordConstants.PLFX_JPORDER_STATUS_LIST, new BeanPropertyValueEqualsPredicate("key",airPayRecordDTO.getOrderStatus()));
//					KeyValue obj2 = (KeyValue)CollectionUtils.find(AirPayRecordConstants.PLFX_JPORDER_PAY_STATUS_LIST, new BeanPropertyValueEqualsPredicate("key",airPayRecordDTO.getPayStatus()));
//					if(null != obj1 && null != obj2){
//						
//						Label orderStatus = new Label(10,i,obj1.getValue().toString() + "/" +obj2.getValue().toString());
//						ws.addCell(orderStatus);
//                    }else{
//                    	Label orderStatus = new Label(10,i,null);
//    					ws.addCell(orderStatus);	
//                    }
//					
//					//订单金额  
//					if(airPayRecordDTO.getOrderPrice() != null){
//						Label orderPrice = new Label(11,i,airPayRecordDTO.getOrderPrice().toString());
//						ws.addCell(orderPrice);
//					}else{//如果为空默认设置0.0
//						Label orderPrice = new Label(11,i,"0.0");
//						ws.addCell(orderPrice);
//					}
//					
//					if(null == airPayRecordDTO.getRebate()){
//						airPayRecordDTO.setRebate(0.0);
//					}
//					//折扣/返点
//					Label rebate = new Label(12,i,airPayRecordDTO.getRebate().toString());
//					ws.addCell(rebate);
//					
//					//佣金
//					if(null == airPayRecordDTO.getBrokerage()){
//						airPayRecordDTO.setBrokerage(0.0);
//					}
//					Label brokerage = new Label(13,i,airPayRecordDTO.getBrokerage().toString());
//					ws.addCell(brokerage);
//					
//					//支付方式 (付款方式)
//					if(null != airPayRecordDTO.getPayPlatform()){
//						Label payPlatform = new Label(14,i,AirPayRecordConstants.PAY_PLATFORM_MAP.get(airPayRecordDTO.getPayPlatform().toString()));
//						ws.addCell(payPlatform);
//					}else{
//						Label payPlatform = new Label(14,i,null);
//						ws.addCell(payPlatform);
//					}
//					//--------------------------------------------------------------------------
//					//收入    支出   是否收到平台退款    订票平台退款    退给客户金额   实退金额      差价针对四个过程做处理
//					//用户->直销               在线支付           //供应商->分销       交易退款
//					//分销->供应商          在线支付              //直销->用户         交易退款
//					
//					//用户->直销         分销->供应商           在线支付   
//					if(airPayRecordDTO.getRecordType().toString().equals(AirPayRecordConstants.RECORD_TYEP_UTJ) || airPayRecordDTO.getRecordType().toString().equals(AirPayRecordConstants.RECORD_TYEP_FTG)){
//						//收入    支出   是否收到   订票平台退款   退给客户金额  实退金额  差价
//						//是否收到订票平台退款
//						Label isRefund = new Label(18,i,"否");
//						ws.addCell(isRefund);
//						
//						//订票平台退款金额
//						Label platRefunceMoney = new Label(19,i,"0.0");
//						ws.addCell(platRefunceMoney);
//						
//						//收入(不传默认0.0)
//						if(null == airPayRecordDTO.getIncomeMoney()){
//							airPayRecordDTO.setIncomeMoney(0.0);
//						}
//						Label incomeMoney = new Label(15,i,airPayRecordDTO.getIncomeMoney().toString());
//						ws.addCell(incomeMoney);
//						
//						//支出   不传 默认0.0
//						if(null == airPayRecordDTO.getPayMoney()){
//							airPayRecordDTO.setPayMoney(0.0);
//						}
//						Label payMoney = new Label(16,i,airPayRecordDTO.getPayMoney().toString());
//						ws.addCell(payMoney);
//						
//						//业务类型(在线支付)
//						Label bussinesType = new Label(27,i,AirPayRecordConstants.ON_LINE_PAY_STRING);
//						ws.addCell(bussinesType);
//						
//						//是否退给客户
//						Label isRefundToUser = new Label(20,i,"否");
//						ws.addCell(isRefundToUser);
//						
//						//退给客户金额(支出)
//						Label toUserRefundPrice = new Label(21,i,"0.0");
//						ws.addCell(toUserRefundPrice);
//						
//						//实退金额
//						Label reallyRefundPrice = new Label(22,i,"0.0");
//						ws.addCell(reallyRefundPrice);
//						
//						//差价
//						Label priceDifferent = new Label(23,i,"0.0");
//						ws.addCell(priceDifferent);
//							
//					}
//					//供应商->分销 
//					if(airPayRecordDTO.getRecordType().toString().equals(AirPayRecordConstants.RECORD_TYEP_GTF)){
//						//供应商->分销满足这种条件算收到订票平台退款金额
//						airPayRecordDTO.setPlatRefunceMoney(airPayRecordDTO.getIncomeMoney());
//						
//						//是否收到订票平台退款
//						Label isRefund = new Label(18,i,"是");
//						ws.addCell(isRefund);
//						
//						//订票平台退款金额   订票平台退款金额根据记录类型来判断收入是不是订票平台退款金额,是就显示订票平台退款金额,不是就显示为0
//						Label platRefunceMoney = new Label(19,i,airPayRecordDTO.getPlatRefunceMoney().toString());
//						ws.addCell(platRefunceMoney);
//						
//						//收入
//						Label incomeMoney = new Label(15,i,"0.0");
//						ws.addCell(incomeMoney);
//						
//						//业务类型(交易退款)
//						Label bussinesType = new Label(27,i,AirPayRecordConstants.TRADE_REFUND_STRING);
//						ws.addCell(bussinesType);
//						
//						//是否退款客户
//						Label isRefundToUser = new Label(20,i,"否");
//						ws.addCell(isRefundToUser);
//						
//						//退给客户金额(支出)
//						Label toUserRefundPrice = new Label(21,i,"0.0");
//						ws.addCell(toUserRefundPrice);
//						
//						//实退金额
//						Label reallyRefundPrice = new Label(22,i,"0.0");
//						ws.addCell(reallyRefundPrice);
//						
//						//差价
//						Label priceDifferent = new Label(23,i,"0.0");
//						ws.addCell(priceDifferent);
//					}
//					//直销->用户
//					if(airPayRecordDTO.getRecordType().toString().equals(AirPayRecordConstants.RECORD_TYEP_JTU)){
//						//收入(不传默认0.0)
//						if(null == airPayRecordDTO.getIncomeMoney()){
//							airPayRecordDTO.setIncomeMoney(0.0);
//						}
//						Label incomeMoney = new Label(15,i,airPayRecordDTO.getIncomeMoney().toString());
//						ws.addCell(incomeMoney);
//					
//						//支出   不传 默认0.0
//						if(null == airPayRecordDTO.getPayMoney()){
//							airPayRecordDTO.setPayMoney(0.0);
//						}
//						
//						Label payMoney = new Label(16,i,airPayRecordDTO.getPayMoney().toString());
//						ws.addCell(payMoney);
//						
//						//是否收到订票平台退款
//						Label isRefund = new Label(18,i,"否");
//						ws.addCell(isRefund);
//						
//						//订票平台退款金额
//						Label platRefunceMoney = new Label(19,i,"0.0");
//						ws.addCell(platRefunceMoney);
//						
//						//订票平台退款金额
//						Label isRefundToUser = new Label(20,i,"是");
//						ws.addCell(isRefundToUser);
//						//退给客户金额(没传就设置0.0)
//						if(null == airPayRecordDTO.getToUserRefundMoney()){
//							airPayRecordDTO.setToUserRefundMoney(0.0);
//						}
//						Label toUserRefundPrice = new Label(21,i,airPayRecordDTO.getToUserRefundMoney().toString());
//						ws.addCell(toUserRefundPrice);
//						//实退金额(没传就设置0.0)
//						if(null == airPayRecordDTO.getRealRefundMoney()){
//							airPayRecordDTO.setRealRefundMoney(0.0);
//						}
//						Label reallyRefundPrice = new Label(22,i,airPayRecordDTO.getRealRefundMoney().toString());
//						ws.addCell(reallyRefundPrice);
//						//差价
//						Double valuePrice = airPayRecordDTO.getToUserRefundMoney() - airPayRecordDTO.getRealRefundMoney();
//						Label priceDifferent = new Label(23,i,valuePrice.toString());
//						ws.addCell(priceDifferent);
//						
//						//业务类型(交易退款)
//						Label bussinesType = new Label(27,i,AirPayRecordConstants.TRADE_REFUND_STRING);
//						ws.addCell(bussinesType);
//					}
//								
//					//商品名称(默认 易行天下电子客票)
//					Label goodsName = new Label(24,i,"易行天下电子客票");
//					ws.addCell(goodsName);
//					
//					//支付时间(发生时间)
//					if(null != airPayRecordDTO.getPayTime()){
//						Label payTime = new Label(25,i,DateUtil.formatDateTime(airPayRecordDTO.getPayTime(), "yyyy-MM-dd HH:mm:ss"));
//						ws.addCell(payTime);
//					}else{
//						Label payTime = new Label(25,i,null);
//						ws.addCell(payTime);
//					}
//					
//					//对方账号  (付款帐号)
//					Label payAccountNo = new Label(26,i,airPayRecordDTO.getPayAccountNo());
//					ws.addCell(payAccountNo);
//					
//					//账务流水号(支付流水号)
//					Label paySerialNumber = new Label(28,i,airPayRecordDTO.getPaySerialNumber());
//					ws.addCell(paySerialNumber);
//					
//					//商户订单号
//					Label supplierNos = new Label(29, i, airPayRecordDTO.getSupplierNo());
//					ws.addCell(supplierNos);
//				
//					//统计支出
//					expenses = expenses + airPayRecordDTO.getIncomeMoney();
//					//统计收入
//					income = income + airPayRecordDTO.getIncomeMoney();
//					//统计订票平台退款总金额
//					refundPrice = refundPrice  + airPayRecordDTO.getIncomeMoney();
//					
//					i++;
//				}
//				//添加合计金额
//				/*Label orderNum = new Label(0,i,"合计");
//				ws.addCell(orderNum);
//				Label price = new Label(6,i,totalPirce.toString());
//				ws.addCell(price);*/
//				
//				workbook.write();
//				workbook.close();
//				//response.getWriter().print(DwzJsonResultUtil.createJsonString("200", "导出机票支付记录成功", "closeCurrent", "air"));
//				os.close();
//			}
//			
//		} catch (Exception e) {
//			HgLogger.getInstance().error("yaosanfeng", "AirPayRecordController->getJPPaymentRecordExcel->Exception:" + HgLogger.getStackTrace(e));
//		}
//		
//		return DwzJsonResultUtil.createJsonString("200", "导出机票支付记录成功", "closeCurrent", "air");
//		//return null;
//	}
//	
//	@RequestMapping(value="/toExport")
//	public String toExportAirRecord(HttpServletRequest request, Model model){
//		
//		return AIR_EXPORT;
//	}
//	
//
//	/****
//	 * 
//	 * @方法功能说明：跳转到新增补单页面(添加机票支付记录信息)
//	 * @修改者名字：yaosanfeng
//	 * @修改时间：2015年12月10日下午5:20:05
//	 * @修改内容：
//	 * @参数：@param request
//	 * @参数：@param response
//	 * @参数：@param model
//	 * @参数：@return
//	 * @return:String
//	 * @throws
//	 */
//	@RequestMapping("/toAdd")
//	public String toAddAirRecord(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		//订单状态
//		model.addAttribute("statusList", AirPayRecordConstants.PLFX_JPORDER_STATUS_LIST);
//		//支付状态
//		model.addAttribute("orderPayStatusList", AirPayRecordConstants.PLFX_JPORDER_PAY_STATUS_LIST);
//		//记录类型
//		model.addAttribute("PAY_RECORD_TYPE_MAP", AirPayRecordConstants.RECORD_TYEP_MAP);
//		//项目来源
//		model.addAttribute("PAY_RECORD_PROJECT_TYPE_MAP", AirPayRecordConstants.FROM_PROJECT_CODE_MAP);
//		//支付方式
//		model.addAttribute("PAYMENT_TYPE", AirPayRecordConstants.PAY_PLATFORM_MAP);
//		//来源客户端类型
//		model.addAttribute("FROM_CLIENT_TYPE", AirPayRecordConstants.FROM_CLIENT_TYPE);
//		return AIR_ADD;
//	}
//	
//	/****
//	 * 
//	 * @方法功能说明：添加机票支付记录
//	 * @修改者名字：yaosanfeng
//	 * @修改时间：2015年12月10日下午5:29:05
//	 * @修改内容：
//	 * @参数：@param request
//	 * @参数：@param response
//	 * @参数：@param model
//	 * @参数：@param command
//	 * @参数：@return
//	 * @return:String
//	 * @throws
//	 */
//	@RequestMapping(value = "/add")
//	@ResponseBody
//	public String addAirRecord(HttpServletRequest request,
//			HttpServletResponse response, Model model,
//			CreateAirPayReocrdSpiCommand command) {
//		
//		try {
//			String message = "";
//			String statusCode = "";
//			Boolean result = airPayRecordService.createAirRecord(command);
//
//			if (result) {
//				message = "新增成功";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
//			} else {
//				message = "新增失败";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//				HgLogger.getInstance().error("yaosanfeng","AirPayRecordController->addAirRecord" + JSON.toJSONString(command));
//			}
//			return DwzJsonResultUtil.createJsonString(statusCode, message,
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "air");
//
//		} catch (Exception e) {
//			 HgLogger.getInstance().error("yaosanfeng", "AirPayRecordController->addAirRecord->Exception:" + HgLogger.getStackTrace(e));
//			  return DwzJsonResultUtil.createJsonString(
//					DwzJsonResultUtil.STATUS_CODE_300, "新增失败",
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "air");
//		}
//
//	}
//	
//	@RequestMapping("/toChange")
//	public String toChangeAirRecord(HttpServletRequest request,
//			HttpServletResponse response, Model model,
//			String id) {
//		AirPayRecordQO qo = new AirPayRecordQO();
//		//主键查询
//		qo.setId(id);
//		AirPayRecordDTO airPayRecordDTO = airPayRecordService.queryUnique(qo);
//		//记录详情
//		model.addAttribute("airPayRecordDTO", airPayRecordDTO);
//		//订单状态
//		model.addAttribute("statusList", AirPayRecordConstants.PLFX_JPORDER_STATUS_LIST);
//		//支付状态
//		model.addAttribute("orderPayStatusList", AirPayRecordConstants.PLFX_JPORDER_PAY_STATUS_LIST);
//		//记录类型
//		model.addAttribute("PAY_RECORD_TYPE_MAP", AirPayRecordConstants.RECORD_TYEP_MAP);
//		//项目来源
//		model.addAttribute("PAY_RECORD_PROJECT_TYPE_MAP", AirPayRecordConstants.FROM_PROJECT_CODE_MAP);
//		//支付方式
//		model.addAttribute("PAYMENT_TYPE", AirPayRecordConstants.PAY_PLATFORM_MAP);
//		//来源客户端类型
//		model.addAttribute("FROM_CLIENT_TYPE", AirPayRecordConstants.FROM_CLIENT_TYPE);
//		return AIR_AUDIT;
//	}
//	
//	/*****
//	 * 
//	 * @方法功能说明：修改机票支付记录
//	 * @修改者名字：yaosanfeng
//	 * @修改时间：2015年12月11日上午10:31:27
//	 * @修改内容：
//	 * @参数：@param request
//	 * @参数：@param response
//	 * @参数：@param model
//	 * @参数：@param command
//	 * @参数：@return
//	 * @return:String
//	 * @throws
//	 */
//	@RequestMapping(value = "/change")
//	@ResponseBody
//	public String changeAirRecord(HttpServletRequest request,
//			HttpServletResponse response, Model model,
//			ModifyAirPayReocrdSpiCommand command) {
//		
//		try {
//			String message = "";
//			String statusCode = "";
//			Boolean result = airPayRecordService.modifyAirRecord(command);
//
//			if (result) {
//				message = "修改成功";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
//			} else {
//				message = "修改失败";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//				HgLogger.getInstance().error("yaosanfeng","AirPayRecordController->addAirRecord" + JSON.toJSONString(command));
//			}
//			return DwzJsonResultUtil.createJsonString(statusCode, message,
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "air");
//
//		} catch (Exception e) {
//			 HgLogger.getInstance().error("yaosanfeng", "AirPayRecordController->addAirRecord->Exception:" + HgLogger.getStackTrace(e));
//			  return DwzJsonResultUtil.createJsonString(
//					DwzJsonResultUtil.STATUS_CODE_300, "新增失败",
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "air");
//		}
//
//	}
//	
}
