//package hg.dzpw.admin.controller.report;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import javax.servlet.http.HttpServletResponse;
//import hg.common.page.Pagination;
//import hg.common.util.StringUtil;
//import hg.dzpw.admin.controller.BaseController;
//import hg.dzpw.app.service.local.TicketUseStatisticsLocalService;
//import hg.dzpw.pojo.common.DZPWConstants;
//import hg.dzpw.pojo.qo.GroupTicketUseStatisticsQo;
//import hg.dzpw.pojo.qo.TicketUsedTouristDetailQo;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//
///**
// * @类功能说明：联票入园统计
// * @类修改者：
// * @修改日期：
// * @修改说明：
// * @公司名称：浙江汇购科技有限公司
// * @部门：技术部
// * @作者：yangkang
// * @创建时间：2014-11-14上午10:29:41
// * @版本：V1.0
// *
// */
//@Controller
//public class TicketUseCountController extends BaseController{
//	
//	@Autowired
//	private TicketUseStatisticsLocalService ticketUseStatisticsLocalService;
//	
//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	
//	
//	/**
//	 * @方法功能说明：联票入园统计列表
//	 * @修改者名字：yangkang
//	 * @修改时间：2014-11-21下午4:09:13
//	 * @参数：@param pageNum
//	 * @参数：@param pageSize
//	 * @参数：@param saleBeginTimeStr
//	 * @参数：@param saleEndTimeStr
//	 * @参数：@param name
//	 * @参数：@return
//	 * @return:ModelAndView
//	 */
//	@RequestMapping("/report/ticket_use_count")
//	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
//                             @RequestParam(value="numPerPage", required = false)Integer pageSize,
//                             @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
//                             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
//                             @RequestParam(value="name", required = false)String name){
//		
//		GroupTicketUseStatisticsQo qo = new GroupTicketUseStatisticsQo();
//		
//		if(StringUtils.isNotBlank(name)){
//			qo.setTicketPolicyName(name);
//		}
//		
//		if(StringUtils.isNotBlank(saleBeginTimeStr)){
//			try {
//				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateBegin(null);
//			}
//		}
//		
//		if(StringUtils.isNotBlank(saleEndTimeStr)){
//			try {
//				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateEnd(null);
//			}
//		}
//		
//		if(pageNum!=null){
//			qo.setPageNo(pageNum);
//		}else{
//			qo.setPageNo(1);
//		}
//		if(pageSize!=null){
//			qo.setPageSize(pageSize);
//		}else{
//			qo.setPageSize(20);
//		}
//		
//		Pagination pagination = new Pagination();
//		pagination.setPageNo(qo.getPageNo());
//		pagination.setPageSize(qo.getPageSize());
//		
//		Pagination pagination2 = this.ticketUseStatisticsLocalService.queryGroupTicketUseStatistics(qo);
//		if(pagination2!=null){
//			pagination.setList(pagination2.getList());
//		}
//		
//		ModelAndView mav = new ModelAndView("/report/ticket_use_list.html");
//		mav.addObject("pagination", pagination);
//		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
//		mav.addObject("saleEndTimeStr", saleEndTimeStr);
//		mav.addObject("name", name);//联票名称
//		return mav;
//	}
//	
//	
//	/**
//	 * @方法功能说明：用户明细列表
//	 * @修改者名字：yangkang
//	 * @修改时间：2014-11-25下午5:29:57
//	 * @参数：@param pageNum
//	 * @参数：@param pageSize
//	 * @参数：@param ticketPolicyId
//	 * @参数：@param userName
//	 * @参数：@param cerNo
//	 * @参数：@return
//	 * @return:ModelAndView
//	 */
//	@RequestMapping("/report/ticket_use_count/detail_list")
//	public ModelAndView detailList(@RequestParam(value="pageNum", required = false)Integer pageNum,
//						           @RequestParam(value="numPerPage", required = false)Integer pageSize,
//						           @RequestParam(value="ticketPolicyId", required = false)String ticketPolicyId,
//						           @RequestParam(value="userName", required = false)String userName,
//						           @RequestParam(value="cerNo", required = false)String  cerNo,
//						           @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
//		                           @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr){
//		
//		TicketUsedTouristDetailQo qo = new TicketUsedTouristDetailQo();
//		
//		if(StringUtils.isNotBlank(ticketPolicyId)){
//			qo.setTicketPolicyId(ticketPolicyId);
//		}
//		
//		if(StringUtils.isNotBlank(userName)){
//			qo.setName(userName);
//		}
//		
//		if(StringUtils.isNotBlank(cerNo)){
//			qo.setCerNo(cerNo);
//		}
//		
//		if(StringUtils.isNotBlank(saleBeginTimeStr)){
//			try {
//				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateBegin(null);
//			}
//		}
//		
//		if(StringUtils.isNotBlank(saleEndTimeStr)){
//			try {
//				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateEnd(null);
//			}
//		}
//		
//		if(pageNum!=null){
//			qo.setPageNo(pageNum);
//		}else{
//			qo.setPageNo(1);
//		}
//		if(pageSize!=null){
//			qo.setPageSize(pageSize);
//		}else{
//			qo.setPageSize(20);
//		}
//		
//		Pagination pagination = new Pagination();
//		pagination.setPageNo(qo.getPageNo());
//		pagination.setPageSize(qo.getPageSize());
//		
//		Pagination pagination2 = this.ticketUseStatisticsLocalService.queryTicketUsedTouristDetail(qo);
//		if(pagination2!=null){
//			pagination.setList(pagination2.getList());
//		}
//		
//		ModelAndView mav = new ModelAndView("/report/ticket_use_detail_list.html");
//		mav.addObject("pagination", pagination);
//		mav.addObject("userName", userName);
//		mav.addObject("cerNo", cerNo);
//		mav.addObject("ticketPolicyId", ticketPolicyId);
//		mav.addObject("cerMap", DZPWConstants.CER_TYPE_NAME);
//		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
//		mav.addObject("saleEndTimeStr", saleEndTimeStr);
//		return mav;
//	}
//	
//	
//	/**
//	 * @方法功能说明：导出列表
//	 * @修改者名字：yangkang
//	 * @修改时间：2014-11-25下午5:24:27
//	 * @参数：@param saleBeginTimeStr
//	 * @参数：@param saleEndTimeStr
//	 * @参数：@param name
//	 * @参数：@return
//	 * @return:String
//	 */
//	@ResponseBody
//	@RequestMapping("/report/ticket_use_count/export")
//	public void exportExcel( HttpServletResponse response,
//							 @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
//					         @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
//					         @RequestParam(value="name", required = false)String name){
//		
//		GroupTicketUseStatisticsQo qo = new GroupTicketUseStatisticsQo();
//		
//		if(StringUtils.isNotBlank(name)){
//			qo.setTicketPolicyName(name);
//		}
//		if(StringUtils.isNotBlank(saleBeginTimeStr)){
//			try {
//				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateBegin(null);
//			}
//		}
//		if(StringUtils.isNotBlank(saleEndTimeStr)){
//			try {
//				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateEnd(null);
//			}
//		}
//		
//		try {
//			File file = this.ticketUseStatisticsLocalService.exportGroupTicketUseStatisticsToExcel(qo);
//			FileInputStream fis = new  FileInputStream(file);
//			try {
//				// 设置输出的格式
//				response.reset();
//				response.setContentType("application/vnd.ms-excel MSEXCEL");
//				response.setHeader("Content-Disposition", "attachment;filename=\""+ StringUtil.getRandomName() + ".xls" + "\"");
//				// 循环取出流中的数据
//				byte[] b = new byte[100];
//				int len;
//				while ((len = fis.read(b)) > 0) {
//					response.getOutputStream().write(b, 0, len);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}finally{
//				fis.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	/**
//	 * @方法功能说明：用户明细导出
//	 * @修改者名字：yangkang
//	 * @修改时间：2014-11-25下午5:34:15
//	 * @修改内容：
//	 * @参数：@param ticketPolicyId
//	 * @参数：@param userName
//	 * @参数：@param cerNo
//	 * @参数：@return
//	 * @return:String
//	 * @throws
//	 */
//	@ResponseBody
//	@RequestMapping("/report/ticket_use_count/export/detail")
//	public void exportDetailExcel( HttpServletResponse response,
//									 @RequestParam(value="ticketPolicyId", required = false)String ticketPolicyId,
//							         @RequestParam(value="userName", required = false)String userName,
//							         @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
//							         @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
//							         @RequestParam(value="cerNo", required = false)String  cerNo){
//		
//		TicketUsedTouristDetailQo qo = new TicketUsedTouristDetailQo();
//		
//		if(StringUtils.isNotBlank(ticketPolicyId)){
//			qo.setTicketPolicyId(ticketPolicyId);
//		}
//		
//		if(StringUtils.isNotBlank(userName)){
//			qo.setName(userName);
//		}
//		
//		if(StringUtils.isNotBlank(cerNo)){
//			qo.setCerNo(cerNo);
//		}
//		
//		//销售开始时间
//		if(StringUtils.isNotBlank(saleBeginTimeStr)){
//			try {
//				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateBegin(null);
//			}
//		}
//		//销售结束时间
//		if(StringUtils.isNotBlank(saleEndTimeStr)){
//			try {
//				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				qo.setOrderDateEnd(null);
//			}
//		}
//		
//		try {
//			File file = this.ticketUseStatisticsLocalService.exportTicketUsedTouristDetailToExcel(qo);
//			FileInputStream fis = new FileInputStream(file);
//			try {
//				// 设置输出的格式
//				response.reset();
//				response.setContentType("application/vnd.ms-excel MSEXCEL");
//				response.setHeader("Content-Disposition", "attachment;filename=\""+ StringUtil.getRandomName() + ".xls" + "\"");
//				// 循环取出流中的数据
//				byte[] b = new byte[100];
//				int len;
//				while ((len = fis.read(b)) > 0) {
//					response.getOutputStream().write(b, 0, len);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}finally{
//				fis.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//}
