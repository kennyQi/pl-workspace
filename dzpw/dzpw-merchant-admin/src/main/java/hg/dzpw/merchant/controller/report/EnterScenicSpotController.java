package hg.dzpw.merchant.controller.report;

import hg.common.page.Pagination;
import hg.common.util.StringUtil;
import hg.dzpw.app.service.local.TicketUseStatisticsLocalService;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.qo.ScenicSpotUseStatisticsQo;
import hg.dzpw.pojo.qo.TicketUsedTouristDetailQo;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 景区端景区入园统计
 * @author CaiHuan
 *
 * 日期:2015-12-25
 */
@Controller
public class EnterScenicSpotController {
	
	@Autowired
	private TicketUseStatisticsLocalService ticketUseStatisticsLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @方法功能说明：入园统计列表
	 * @修改者名字：yangkang
	 * @修改时间：2015-5-4下午3:15:08
	 */
	@RequestMapping("/report/enter_scenicspot_count")
	public ModelAndView list(HttpServletRequest request,
							 @RequestParam(value="pageNum", required = false)Integer pageNum,
                             @RequestParam(value="numPerPage", required = false)Integer pageSize,
                             @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
                             @RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType,
                             @RequestParam(value="orderId", required = false)String orderId,
                             @RequestParam(value="ticketNo", required = false)String ticketNo){
		
		ScenicSpotUseStatisticsQo qo = new ScenicSpotUseStatisticsQo();
		
		//获取当前景区ID
		qo.setScenicSpotId(MerchantSessionUserManager.getSessionUserId(request));
		
		//产品类型 单票、联票
		if(ticketPolicyType!=null && ticketPolicyType==1)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		
		if(ticketPolicyType!=null && ticketPolicyType==2)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		
		//订单编号
		if(StringUtils.isNotBlank(orderId))
		{
			qo.setOrderId(orderId);
		}
		
		//票务编号
		if(StringUtils.isNotBlank(ticketNo))
		{
			qo.setTicketNo(ticketNo);
		}
		
		//入园开始、截止日期
		if(StringUtils.isNotBlank(saleBeginTimeStr)){
			try {
				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateBegin(null);
			}
		}
		
		if(StringUtils.isNotBlank(saleEndTimeStr)){
			try {
				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateEnd(null);
			}
		}
		
		if(pageNum!=null){
			qo.setPageNo(pageNum);
		}else{
			qo.setPageNo(1);
		}
		if(pageSize!=null){
			qo.setPageSize(pageSize);
		}else{
			qo.setPageSize(20);
		}
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		
		Pagination pagination2 = this.ticketUseStatisticsLocalService.queryScenicSpotUseStatistics(qo);
		if(pagination2!=null){
			pagination.setList(pagination2.getList());
			pagination.setTotalCount(pagination2.getTotalCount());
			pagination.setCondition(pagination2.getCondition());
		}
		
		ModelAndView mav = new ModelAndView("/report/enter/enter_scenicsport_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
		mav.addObject("saleEndTimeStr", saleEndTimeStr);
		mav.addObject("ticketPolicyType", ticketPolicyType);
		mav.addObject("orderId",orderId);
		mav.addObject("ticketNo",ticketNo);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：入园统明细计列表
	 * @修改者名字：yangkang
	 * @修改时间：2015-5-4下午3:15:08
	 */
	@RequestMapping("/report/enter_scenicspot_count/detail_list")
	public ModelAndView detailList(@RequestParam(value="pageNum", required = false)Integer pageNum,
	           					   @RequestParam(value="numPerPage", required = false)Integer pageSize,
	           					   @RequestParam(value="scenicSpotId", required = false)String scenicSpotId,
	           					   @RequestParam(value="userName", required = false)String userName,
						           @RequestParam(value="cerNo", required = false)String  cerNo,
						           @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
		                           @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
		                           @RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType){
		
		TicketUsedTouristDetailQo qo = new TicketUsedTouristDetailQo();
		
		if(StringUtils.isNotBlank(scenicSpotId)){
			qo.setScenicSpotId(scenicSpotId);
		}
		
		if(StringUtils.isNotBlank(userName)){
			qo.setName(userName);
		}
		
		if(StringUtils.isNotBlank(cerNo)){
			qo.setCerNo(cerNo);
		}
		
		if(ticketPolicyType!=null && ticketPolicyType==1)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		
		if(ticketPolicyType!=null && ticketPolicyType==2)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		
		
		//入园开始、截止日期
		if(StringUtils.isNotBlank(saleBeginTimeStr)){
			try {
				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateBegin(null);
			}
		}
		
		if(StringUtils.isNotBlank(saleEndTimeStr)){
			try {
				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateEnd(null);
			}
		}
		
		if(pageNum!=null){
			qo.setPageNo(pageNum);
		}else{
			qo.setPageNo(1);
		}
		if(pageSize!=null){
			qo.setPageSize(pageSize);
		}else{
			qo.setPageSize(20);
		}
		
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		
		Pagination pagination2 = this.ticketUseStatisticsLocalService.queryTicketUsedTouristDetail(qo);
		
		if(pagination2!=null){
			pagination.setList(pagination2.getList());
			pagination.setTotalCount(pagination2.getTotalCount());
		}
		
		ModelAndView mav = new ModelAndView("/report/enter/enter_scenicsport_detail_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("cerNo", cerNo);
		mav.addObject("userName", userName);
		mav.addObject("scenicSpotId", scenicSpotId);
		mav.addObject("cerMap", DZPWConstants.CER_TYPE_NAME);
		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
		mav.addObject("saleEndTimeStr", saleEndTimeStr);
		mav.addObject("ticketPolicyType", ticketPolicyType);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：入园统计列表导出
	 * @修改者名字：yangkang
	 * @修改时间：2015-5-18上午10:59:58
	 */
	@ResponseBody
	@RequestMapping("/report/enter_scenicspot_count/export")
	public void exportExcel(@RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
            				@RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
            				@RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType,
            				HttpServletRequest request,
            				HttpServletResponse response,
            				@RequestParam(value="orderId", required = false)String orderId,
                            @RequestParam(value="ticketNo", required = false)String ticketNo){
	
		ScenicSpotUseStatisticsQo qo = new ScenicSpotUseStatisticsQo();
		
		//获取当前景区ID
		qo.setScenicSpotId(MerchantSessionUserManager.getSessionUserId(request));
		
		//产品类型 单票、联票
		if(ticketPolicyType!=null && ticketPolicyType==1)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
				
		if(ticketPolicyType!=null && ticketPolicyType==2)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		
		//订单编号
		if(StringUtils.isNotBlank(orderId))
		{
			qo.setOrderId(orderId);
		}
				
		//票务编号
		if(StringUtils.isNotBlank(ticketNo))
		{
			qo.setTicketNo(ticketNo);
		}
	
		//入园开始、截止日期
		if(StringUtils.isNotBlank(saleBeginTimeStr)){
			try {
				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateBegin(null);
			}
		}
				
		if(StringUtils.isNotBlank(saleEndTimeStr)){
			try {
				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateEnd(null);
			}
		}
		
		
		try {
			File file = ticketUseStatisticsLocalService.exportScenicSpotUseStatisticsToExcel(qo);
			FileInputStream fis = new FileInputStream(file);
			
			try {
				// 设置输出的格式
				response.reset();
				response.setContentType("application/vnd.ms-excel MSEXCEL");
				response.setHeader("Content-Disposition", "attachment;filename=\""+ StringUtil.getRandomName() + ".xls" + "\"");
				// 循环取出流中的数据
				byte[] b = new byte[100];
				int len;
				while ((len = fis.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping("/report/enter_scenicspot_count/export/detail")
	public void exportDeatailExcel(@RequestParam(value="scenicSpotId", required = false)String scenicSpotId,
			   @RequestParam(value="userName", required = false)String userName,
	           @RequestParam(value="cerNo", required = false)String  cerNo,
	           @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
	           @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
	           @RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType,
	           HttpServletResponse response){
		
		TicketUsedTouristDetailQo qo = new TicketUsedTouristDetailQo();
		
		if(StringUtils.isNotBlank(scenicSpotId)){
			qo.setScenicSpotId(scenicSpotId);
		}
		
		if(StringUtils.isNotBlank(userName)){
			qo.setName(userName);
		}
		
		if(StringUtils.isNotBlank(cerNo)){
			qo.setCerNo(cerNo);
		}
		
		if(ticketPolicyType!=null && ticketPolicyType==1)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		
		if(ticketPolicyType!=null && ticketPolicyType==2)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		
		//入园开始、截止日期
		if(StringUtils.isNotBlank(saleBeginTimeStr)){
			try {
				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateBegin(null);
			}
		}
				
		if(StringUtils.isNotBlank(saleEndTimeStr)){
			try {
				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateEnd(null);
			}
		}
				
		
		try {
			File file = ticketUseStatisticsLocalService.exportTicketUsedTouristDetailToExcel(qo);
			FileInputStream fis = new FileInputStream(file);
			
			try {
				// 设置输出的格式
				response.reset();
				response.setContentType("application/vnd.ms-excel MSEXCEL");
				response.setHeader("Content-Disposition", "attachment;filename=\""+ StringUtil.getRandomName() + ".xls" + "\"");
				// 循环取出流中的数据
				byte[] b = new byte[100];
				int len;
				while ((len = fis.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
