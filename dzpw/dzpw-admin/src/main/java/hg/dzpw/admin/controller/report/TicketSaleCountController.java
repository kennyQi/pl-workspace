package hg.dzpw.admin.controller.report;

import hg.common.page.Pagination;
import hg.common.util.StringUtil;
import hg.dzpw.app.service.local.TicketSaleStatisticsLocalService;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.qo.GroupTicketSaleStatisticsQo;
import hg.dzpw.pojo.qo.TicketOrderTouristDetailQo;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @类功能说明：门票销售统计
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-14上午10:20:39
 * @版本：V1.0
 *
 */
@Controller
public class TicketSaleCountController {
	
	@Autowired
	private TicketSaleStatisticsLocalService ticketSaleStatisticsLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * @方法功能说明：联票销售统计
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28下午2:10:53
	 * @修改内容：
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param saleBeginTimeStr
	 * @参数：@param saleEndTimeStr
	 * @参数：@param sortMethod
	 * @参数：@param name
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/report/ticket_sale_count")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
                             @RequestParam(value="numPerPage", required = false)Integer pageSize,
                             @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
                             @RequestParam(value="name", required = false)String name,
                             @RequestParam(value="scenicSpotName", required = false)String scenicSpotName,
                             @RequestParam(value="dealerName", required = false)String dealerName,
                             @RequestParam(value="status", required = false)Integer status){

		GroupTicketSaleStatisticsQo qo = new GroupTicketSaleStatisticsQo();
		
		//排序方式
//		if(sortMethod!=null && sortMethod>0){
//			qo.setQueryType(sortMethod);
//		}
		
		//产品名称 门票政策名
		if(StringUtils.isNotBlank(name)){
			qo.setTicketPolicyName(name);
		}
		//所属景区名称
		if(StringUtils.isNotBlank(scenicSpotName))
		{
			qo.setScenicSpotName(scenicSpotName);
		}
		//经销商名称
		if(StringUtils.isNotBlank(dealerName))
		{
			qo.setDealerName(dealerName);
		}
		
		
		//销售开始时间
		if(StringUtils.isNotBlank(saleBeginTimeStr)){
			try {
				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateBegin(null);
			}
		}
		//销售结束时间
		if(StringUtils.isNotBlank(saleEndTimeStr)){
			try {
				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateEnd(null);
			}
		}
		
		//订单状态
		if(status!=null)
		{
			qo.setStatus(status);
		}
		
		//分页设置
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
			
		
		Pagination pagination = this.ticketSaleStatisticsLocalService.queryGroupTicketSaleStatistics(qo);
		if (pagination==null) {
			pagination=new Pagination();
		}
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		
		ModelAndView mav = new ModelAndView("/report/sale/ticket_sale_count_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
		mav.addObject("saleEndTimeStr", saleEndTimeStr);
		mav.addObject("scenicSpotName", scenicSpotName);
		mav.addObject("dealerName", dealerName);
		mav.addObject("name", name);//联票名称
		return mav;
	}
	
	
	/**
	 * @方法功能说明：联票销售统计用户明细
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28下午2:10:39
	 * @修改内容：
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param orderNo
	 * @参数：@param ticketPolicyId
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/report/ticket_sale_count/detail_list")
	public ModelAndView detailList(@RequestParam(value="pageNum", required = false)Integer pageNum,
            					   @RequestParam(value="numPerPage", required = false)Integer pageSize,
            					   @RequestParam(value="orderNo", required = false)String orderNo,
            					   @RequestParam(value="ticketPolicyId", required = false)String ticketPolicyId,
            					   @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                                   @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr){
		
		
		TicketOrderTouristDetailQo qo = new TicketOrderTouristDetailQo();
		
		if(StringUtils.isNotBlank(ticketPolicyId)){
			qo.setTicketPolicyId(ticketPolicyId);
		}
		
		if(StringUtils.isNotBlank(orderNo)){
			qo.setOrderNo(orderNo);
		}
		
		//销售开始时间
		if(StringUtils.isNotBlank(saleBeginTimeStr)){
			try {
				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateBegin(null);
			}
		}
		//销售结束时间
		if(StringUtils.isNotBlank(saleEndTimeStr)){
			try {
				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateEnd(null);
			}
		}
		
		//分页设置
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
		
		Pagination pagination2 = this.ticketSaleStatisticsLocalService.queryTicketOrderTouristDetail(qo);
		if(pagination2!=null){
			pagination.setList(pagination2.getList());
			pagination.setTotalCount(pagination2.getTotalCount());
		}
		
		ModelAndView mav = new ModelAndView("/report/sale/ticket_sale_count_detail_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("ticketPolicyId", ticketPolicyId);
		mav.addObject("orderNo", orderNo);
		mav.addObject("cerMap", DZPWConstants.CER_TYPE_NAME);
		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
		mav.addObject("saleEndTimeStr", saleEndTimeStr);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：联票销售统计导出
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28下午2:06:48
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param saleBeginTimeStr
	 * @参数：@param saleEndTimeStr
	 * @参数：@param sortMethod
	 * @参数：@param name
	 * @return:void
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/report/ticket_sale_count/export/")
	public void export(HttpServletResponse response,
			 @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
             @RequestParam(value="name", required = false)String name,
             @RequestParam(value="scenicSpotName", required = false)String scenicSpotName,
             @RequestParam(value="dealerName", required = false)String dealerName,
             @RequestParam(value="status", required = false)Integer status){
		
		GroupTicketSaleStatisticsQo qo = new GroupTicketSaleStatisticsQo();
		
		
		if(StringUtils.isNotBlank(name)){
			qo.setTicketPolicyName(name);
		}
		//所属景区名称
		if(StringUtils.isNotBlank(scenicSpotName))
		   {
			   qo.setScenicSpotName(scenicSpotName);
		   }
				//经销商名称
		if(StringUtils.isNotBlank(dealerName))
		   {
			   qo.setDealerName(dealerName);
		   }		
		
		//销售开始时间
		if(StringUtils.isNotBlank(saleBeginTimeStr)){
			try {
				qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateBegin(null);
			}
		}
		//销售结束时间
		if(StringUtils.isNotBlank(saleEndTimeStr)){
			try {
				qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setOrderDateEnd(null);
			}
		}
		
		//订单状态
		if(status!=null)
		{
			   qo.setStatus(status);
		}
		
		try {
			File file = this.ticketSaleStatisticsLocalService.exportGroupTicketSaleStatisticsToExcel(qo);
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
	
	
	/**
	 * @方法功能说明：联票销售统计用户明细导出
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28下午2:10:16
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param orderNo
	 * @参数：@param ticketPolicyId
	 * @return:void
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/report/ticket_sale_count/export/detail")
	public void exportDetail(HttpServletResponse response,
			 				 @RequestParam(value="orderNo", required = false)String orderNo,
			 				 @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
				             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
			 				 @RequestParam(value="ticketPolicyId", required = false)String ticketPolicyId){
	
		TicketOrderTouristDetailQo qo = new TicketOrderTouristDetailQo();
		
		if(StringUtils.isNotBlank(ticketPolicyId)){
			qo.setTicketPolicyId(ticketPolicyId);
		}
		
		if(StringUtils.isNotBlank(orderNo)){
			qo.setOrderNo(orderNo);
		}
		
		//销售开始时间
				if(StringUtils.isNotBlank(saleBeginTimeStr)){
					try {
						qo.setOrderDateBegin(sdf.parse(saleBeginTimeStr+" 00:00:00"));
					} catch (ParseException e) {
						e.printStackTrace();
						qo.setOrderDateBegin(null);
					}
				}
				//销售结束时间
				if(StringUtils.isNotBlank(saleEndTimeStr)){
					try {
						qo.setOrderDateEnd(sdf.parse(saleEndTimeStr+" 23:59:59"));
					} catch (ParseException e) {
						e.printStackTrace();
						qo.setOrderDateEnd(null);
					}
				}
		
		try {
			File file = this.ticketSaleStatisticsLocalService.exportTicketOrderTouristDetailToExcel(qo);
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
