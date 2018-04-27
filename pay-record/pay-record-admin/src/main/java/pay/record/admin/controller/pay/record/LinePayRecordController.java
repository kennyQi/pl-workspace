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
import pay.record.pojo.command.ModifyLinePayReocrdSpiCommand;
import pay.record.pojo.command.line.CreateLineUTJPayReocrdSpiCommand;
import pay.record.pojo.dto.LinePayRecordDTO;
import pay.record.pojo.qo.pay.LinePayRecordQO;
import pay.record.pojo.system.AirPayRecordConstants;
import pay.record.spi.inter.pay.LinePayRecordService;

import com.alibaba.fastjson.JSON;


/**
 * 
 * @类功能说明：线路支付记录Controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月9日上午11:02:39
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value = "payRecord/line")
public class LinePayRecordController extends BaseController{
	@Autowired
	private LinePayRecordService linePayRecordService;
	
	private static String LINE_LIST = "linepayrecord/list.html";
	private static String LINE_ADD = "linepayrecord/add.html";
	private static String LINE_AUDIT = "linepayrecord/audit.html";
	private static String LINE_EXPORT = "linepayrecord/export.html";
	
	/*****
	 * 
	 * @方法功能说明：
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月14日上午8:53:22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param linePayRecordQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryList(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute LinePayRecordQO linePayRecordQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr){
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			linePayRecordQO.setPayDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			linePayRecordQO.setPayDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		//按创建时间倒序排序
		linePayRecordQO.setCreateDateAsc(false);
		
		pagination.setCondition(linePayRecordQO);
		pagination = linePayRecordService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("linePayRecordQO", linePayRecordQO);
		
		//订单状态
		//model.addAttribute("statusList", JPOrderStatusConstant.PLFX_JPORDER_STATUS_LIST);
		//支付状态
		//model.addAttribute("orderPayStatusList", JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_LIST);
		//记录类型
		model.addAttribute("PAY_RECORD_TYPE_MAP", AirPayRecordConstants.RECORD_TYEP_MAP);
		//项目来源
		model.addAttribute("PAY_RECORD_PROJECT_TYPE_MAP", AirPayRecordConstants.FROM_PROJECT_CODE_MAP);
		//支付方式
		model.addAttribute("PAYMENT_TYPE", AirPayRecordConstants.PAY_PLATFORM_MAP);
		//订单状态
		model.addAttribute("statusMap", AirPayRecordConstants.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", AirPayRecordConstants.SLFX_XLORDER_PAY_STATUS_MAP); 
		//线路类型
		//model.addAttribute("typeMap", LineConstants.typeMap); 
		return LINE_LIST;
	}
	

	/****
	 * 
	 * @方法功能说明：：跳转到新增线路支付记录页面
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月10日下午5:20:05
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAdd")
	public String toAddLinePayRecord(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//订单状态
		model.addAttribute("statusMap", AirPayRecordConstants.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", AirPayRecordConstants.SLFX_XLORDER_PAY_STATUS_MAP); 
		//记录类型
		model.addAttribute("PAY_RECORD_TYPE_MAP", AirPayRecordConstants.RECORD_TYEP_MAP);
		//项目来源
		model.addAttribute("PAY_RECORD_PROJECT_TYPE_MAP", AirPayRecordConstants.FROM_PROJECT_CODE_MAP);
		//支付方式
		model.addAttribute("PAYMENT_TYPE", AirPayRecordConstants.PAY_PLATFORM_MAP);
		//来源客户端类型
		model.addAttribute("FROM_CLIENT_TYPE", AirPayRecordConstants.FROM_CLIENT_TYPE);
		return LINE_ADD;
	}
	
	/**
	 * 
	 * @方法功能说明：新增线路支付记录
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月11日上午9:32:55
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
//	@RequestMapping(value = "/add")
//	@ResponseBody
//	public String addLinePayRecord(HttpServletRequest request,
//			HttpServletResponse response, Model model,
//			@ModelAttribute CreateLineUTJPayReocrdSpiCommand command) {
//
//		try {
//
//			String message = "";
//			String statusCode = "";
//			Boolean result = linePayRecordService.createLinePayRecord(command);
//
//			if (result) {
//				message = "新增成功";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
//			} else {
//				message = "新增失败";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//				HgLogger.getInstance().error("yaosanfeng","LinePayRecordController->addLineRecord" + JSON.toJSONString(command));
//			}
//			return DwzJsonResultUtil.createJsonString(statusCode, message,
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
//
//		} catch (Exception e) {
//			 HgLogger.getInstance().error("yaosanfeng", "LinePayRecordController->addLineRecord->Exception:" + HgLogger.getStackTrace(e));
//			  return DwzJsonResultUtil.createJsonString(
//					DwzJsonResultUtil.STATUS_CODE_300, "新增失败",
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
//		}
//
//	}
//	
//	@RequestMapping("/toChange")
//	public String toChangeLineRecord(HttpServletRequest request,
//			HttpServletResponse response, Model model,
//			String id) {
//		LinePayRecordQO qo = new LinePayRecordQO();
//		//主键查询
//		qo.setId(id);
//		LinePayRecordDTO linePayRecordDTO = linePayRecordService.queryUnique(qo);
//		//记录详情
//		model.addAttribute("linePayRecordDTO", linePayRecordDTO);
//		//订单状态
//		model.addAttribute("statusMap", AirPayRecordConstants.SLFX_XLORDER_STATUS_MAP); 
//		//订单支付状态
//		model.addAttribute("payStatusMap", AirPayRecordConstants.SLFX_XLORDER_PAY_STATUS_MAP); 
//		//记录类型
//		model.addAttribute("PAY_RECORD_TYPE_MAP", AirPayRecordConstants.RECORD_TYEP_MAP);
//		//项目来源
//		model.addAttribute("PAY_RECORD_PROJECT_TYPE_MAP", AirPayRecordConstants.FROM_PROJECT_CODE_MAP);
//		//支付方式
//		model.addAttribute("PAYMENT_TYPE", AirPayRecordConstants.PAY_PLATFORM_MAP);
//		//来源客户端类型
//		model.addAttribute("FROM_CLIENT_TYPE", AirPayRecordConstants.FROM_CLIENT_TYPE);
//		return LINE_AUDIT;
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
//	public String changeLineRecord(HttpServletRequest request,
//			HttpServletResponse response, Model model,
//			ModifyLinePayReocrdSpiCommand command) {
//		
//		try {
//			String message = "";
//			String statusCode = "";
//			Boolean result = linePayRecordService.modifyLinePayRecord(command);
//
//			if (result) {
//				message = "修改成功";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
//			} else {
//				message = "修改失败";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//				HgLogger.getInstance().error("yaosanfeng","LinePayRecordController->addLineRecord" + JSON.toJSONString(command));
//			}
//			return DwzJsonResultUtil.createJsonString(statusCode, message,
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
//
//		} catch (Exception e) {
//			 HgLogger.getInstance().error("yaosanfeng", "LinePayRecordController->addLineRecord->Exception:" + HgLogger.getStackTrace(e));
//			  return DwzJsonResultUtil.createJsonString(
//					DwzJsonResultUtil.STATUS_CODE_300, "修改失败",
//					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "line");
//		}
//
//	}
//	
//	/****
//	 * 
//	 * @方法功能说明：导出线路支付记录信息Excel
//	 * @修改者名字：yaosanfeng
//	 * @修改时间：2015年12月4日下午3:02:33
//	 * @修改内容：
//	 * @参数：@param request
//	 * @参数：@param response
//	 * @参数：@param payRecordQO
//	 * @参数：@return
//	 * @return:String
//	 * @throws
//	 */
//	@RequestMapping("/export")
//	@ResponseBody
//	public String exportLineRecordExcel(HttpServletRequest request,
//			HttpServletResponse response,LinePayRecordQO linePayRecordQO,
//			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
//			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
//			) {
//		
//		HgLogger.getInstance().info("yaosanfeng","AirPayRecordController->getLinePaymentRecordExcel->linePayRecordQO:"+JSON.toJSONString(linePayRecordQO));
//		
//		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
//			linePayRecordQO.setPayDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
//		}
//		if (null != endTimeStr && !"".equals(endTimeStr)) {
//			linePayRecordQO.setPayDateTo(DateUtil.dateStr2EndDate(endTimeStr));
//		}
//		//按创建时间倒序排序
//		linePayRecordQO.setCreateDateAsc(false);
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
//			List<LinePayRecordDTO> linePaymentRecordList = linePayRecordService.queryLinePaymentRecordList(linePayRecordQO);
//			HgLogger.getInstance().info("yaosanfeng", "AirPayRecordController->getJPPaymentRecordExcel->jPPaymentRecordList"+JSON.toJSONString(linePaymentRecordList));
//			if(linePaymentRecordList.size() > 0){
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
//				for(LinePayRecordDTO linePayRecordDTO : linePaymentRecordList){
//					//订单来源
//					if(null != linePayRecordDTO.getFromProjectCode()){
//						Label fromProjectCode = new Label(0, i, AirPayRecordConstants.FROM_PROJECT_CODE_MAP.get(linePayRecordDTO.getFromProjectCode().toString()));
//						ws.addCell(fromProjectCode); 
//					}else{
//						Label fromProjectCode = new Label(0, i, null);
//						ws.addCell(fromProjectCode); 
//					}
//					//订购人
//					Label booker = new Label(1,i,linePayRecordDTO.getBooker());
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
//					if(null != linePayRecordDTO.getCreateTime()){
//						Label createTime= new Label(4,i,DateUtil.formatDateTime(linePayRecordDTO.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
//						ws.addCell(createTime);
//					}else{
//						Label createTime= new Label(4,i,null);
//						ws.addCell(createTime);
//					}
//					
//					//易行订单号
//					Label supplierNo = new Label(5, i, linePayRecordDTO.getSupplierNo());
//					ws.addCell(supplierNo);
//					
//					//平台订单号
//					Label platOrderNo = new Label(6,i,linePayRecordDTO.getPlatOrderNo());
//					ws.addCell(platOrderNo);
//					
//					//乘机人
//					Label passengers = new Label(7,i,linePayRecordDTO.getPassengers());
//					ws.addCell(passengers);
//					
//					//始发地
//					Label startCity = new Label(8,i,linePayRecordDTO.getStartCity());
//					ws.addCell(startCity);
//					
//					//目的地
//					Label destCity = new Label(9,i,linePayRecordDTO.getDestCity());
//					ws.addCell(destCity);
//					
//					//订单状态(也包括支付状态,格式:已出票/已支付)
//					KeyValue obj1 = (KeyValue)CollectionUtils.find(AirPayRecordConstants.PLFX_JPORDER_STATUS_LIST, new BeanPropertyValueEqualsPredicate("key",linePayRecordDTO.getOrderStatus()));
//					KeyValue obj2 = (KeyValue)CollectionUtils.find(AirPayRecordConstants.PLFX_JPORDER_PAY_STATUS_LIST, new BeanPropertyValueEqualsPredicate("key",linePayRecordDTO.getPayStatus()));
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
//					if(linePayRecordDTO.getOrderPrice() != null){
//						Label orderPrice = new Label(11,i,linePayRecordDTO.getOrderPrice().toString());
//						ws.addCell(orderPrice);
//					}else{//如果为空默认设置0.0
//						Label orderPrice = new Label(11,i,"0.0");
//						ws.addCell(orderPrice);
//					}
//					
//					if(null == linePayRecordDTO.getRebate()){
//						linePayRecordDTO.setRebate(0.0);
//					}
//					//折扣/返点
//					Label rebate = new Label(12,i,linePayRecordDTO.getRebate().toString());
//					ws.addCell(rebate);
//					
//					//佣金
//					if(null == linePayRecordDTO.getBrokerage()){
//						linePayRecordDTO.setBrokerage(0.0);
//					}
//					Label brokerage = new Label(13,i,linePayRecordDTO.getBrokerage().toString());
//					ws.addCell(brokerage);
//					
//					//支付方式 (付款方式)
//					if(null != linePayRecordDTO.getPayPlatform()){
//						Label payPlatform = new Label(14,i,AirPayRecordConstants.PAY_PLATFORM_MAP.get(linePayRecordDTO.getPayPlatform().toString()));
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
//					if(linePayRecordDTO.getRecordType().toString().equals(AirPayRecordConstants.RECORD_TYEP_UTJ) ){
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
//						if(null == linePayRecordDTO.getIncomeMoney()){
//							linePayRecordDTO.setIncomeMoney(0.0);
//						}
//						Label incomeMoney = new Label(15,i,linePayRecordDTO.getIncomeMoney().toString());
//						ws.addCell(incomeMoney);
//						
//						//支出   不传 默认0.0
//						if(null == linePayRecordDTO.getPayMoney()){
//							linePayRecordDTO.setPayMoney(0.0);
//						}
//						Label payMoney = new Label(16,i,linePayRecordDTO.getPayMoney().toString());
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
//					
//					//直销->用户
//					if(linePayRecordDTO.getRecordType().toString().equals(AirPayRecordConstants.RECORD_TYEP_JTU)){
//						//收入(不传默认0.0)
//						if(null == linePayRecordDTO.getIncomeMoney()){
//							linePayRecordDTO.setIncomeMoney(0.0);
//						}
//						Label incomeMoney = new Label(15,i,linePayRecordDTO.getIncomeMoney().toString());
//						ws.addCell(incomeMoney);
//					
//						//支出   不传 默认0.0
//						if(null == linePayRecordDTO.getPayMoney()){
//							linePayRecordDTO.setPayMoney(0.0);
//						}
//						
//						Label payMoney = new Label(16,i,linePayRecordDTO.getPayMoney().toString());
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
//						if(null == linePayRecordDTO.getToUserRefundMoney()){
//							linePayRecordDTO.setToUserRefundMoney(0.0);
//						}
//						Label toUserRefundPrice = new Label(21,i,linePayRecordDTO.getToUserRefundMoney().toString());
//						ws.addCell(toUserRefundPrice);
//						//实退金额(没传就设置0.0)
//						if(null == linePayRecordDTO.getRealRefundMoney()){
//							linePayRecordDTO.setRealRefundMoney(0.0);
//						}
//						Label reallyRefundPrice = new Label(22,i,linePayRecordDTO.getRealRefundMoney().toString());
//						ws.addCell(reallyRefundPrice);
//						//差价
//						Double valuePrice = linePayRecordDTO.getToUserRefundMoney() - linePayRecordDTO.getRealRefundMoney();
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
//					if(null != linePayRecordDTO.getPayTime()){
//						Label payTime = new Label(25,i,DateUtil.formatDateTime(linePayRecordDTO.getPayTime(), "yyyy-MM-dd HH:mm:ss"));
//						ws.addCell(payTime);
//					}else{
//						Label payTime = new Label(25,i,null);
//						ws.addCell(payTime);
//					}
//					
//					//对方账号  (付款帐号)
//					Label payAccountNo = new Label(26,i,linePayRecordDTO.getPayAccountNo());
//					ws.addCell(payAccountNo);
//					
//					//账务流水号(支付流水号)
//					Label paySerialNumber = new Label(28,i,linePayRecordDTO.getPaySerialNumber());
//					ws.addCell(paySerialNumber);
//					
//					//商户订单号
//					Label supplierNos = new Label(29, i, linePayRecordDTO.getSupplierNo());
//					ws.addCell(supplierNos);
//				
//					//统计支出
//					expenses = expenses + linePayRecordDTO.getIncomeMoney();
//					//统计收入
//					income = income + linePayRecordDTO.getIncomeMoney();
//					//统计订票平台退款总金额
//					refundPrice = refundPrice  + linePayRecordDTO.getIncomeMoney();
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
//		//return DwzJsonResultUtil.createJsonString("200", "导出线路支付记录成功", "closeCurrent", "air");
//		return null;
//	}
//	
//	@RequestMapping(value="/toExport")
//	public String toExportLineRecord(HttpServletRequest request, Model model){
//		
//		return LINE_EXPORT;
//	}
//	
	
	

}
