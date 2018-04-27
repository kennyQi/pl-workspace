package hg.dzpw.admin.controller.report;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hg.common.page.Pagination;
import hg.common.util.StringUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.TicketSaleStatisticsLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.qo.DealerSaleStatisticsQo;
import hg.dzpw.pojo.qo.TicketOrderTouristDetailQo;

/**
 * @类功能说明：经销商销售统计
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-14上午10:15:24
 * @版本：V1.0
 *
 */
@Controller
public class DealerSaleCountController extends BaseController{
	
	@Autowired
	private DealerLocalService dealerLocalServiceImpl;
	
	@Autowired
	private TicketSaleStatisticsLocalService ticketSaleStatisticsLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	/**
	 * @方法功能说明：经销商销售统计
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28上午11:03:35
	 * @修改内容：
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param saleBeginTimeStr
	 * @参数：@param saleEndTimeStr
	 * @参数：@param sortMethod
	 * @参数：@param dealerId
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/report/dealer_sale_count")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
                             @RequestParam(value="numPerPage", required = false)Integer pageSize,
                             @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
                             @RequestParam(value="sortMethod", required = false)Integer sortMethod,
                             @RequestParam(value="dealerId", required = false)String dealerId){

		//获取经销商列表
		List<Dealer> dealerList = this.dealerLocalServiceImpl.queryList(new DealerQo());
		
		DealerSaleStatisticsQo qo = new DealerSaleStatisticsQo();
		
		if(sortMethod!=null && sortMethod>0){
			//统计方式
			qo.setQueryType(sortMethod);
		}
		
		//经销商ID
		if(StringUtils.isNotBlank(dealerId)){
			qo.setDealerId(dealerId);
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
		
		Pagination pagination2 = this.ticketSaleStatisticsLocalService.queryDealerSaleStatistics(qo);
		if(pagination2!=null){
			pagination.setList(pagination2.getList());
			pagination.setTotalCount(pagination2.getTotalCount());
		}
		
		
		ModelAndView mav = new ModelAndView("/report/dealersale/dealer_sale_count_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
		mav.addObject("saleEndTimeStr", saleEndTimeStr);
		mav.addObject("sortMethod", sortMethod);
		mav.addObject("dealerId", dealerId);//经销商名称
		mav.addObject("dealerList", dealerList);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：经销商销售统计--用户明细
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28上午11:04:00
	 * @修改内容：
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param dealerId
	 * @参数：@param orderNo
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/report/dealer_sale_count/detail_list")
	public ModelAndView detailList(@RequestParam(value="pageNum", required = false)Integer pageNum,
            					   @RequestParam(value="numPerPage", required = false)Integer pageSize,
            					   @RequestParam(value="dealerId", required = false)String dealerId,
            					   @RequestParam(value="orderNo", required = false)String orderNo,
            					   @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                                   @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr){
		
		TicketOrderTouristDetailQo qo = new TicketOrderTouristDetailQo();
		if(StringUtils.isNotBlank(dealerId)){
			qo.setDealerId(dealerId);
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
		
		ModelAndView mav = new ModelAndView("/report/dealersale/dealer_sale_count_detail_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("dealerId", dealerId);
		mav.addObject("orderNo", orderNo);
		mav.addObject("cerMap", DZPWConstants.CER_TYPE_NAME);
		mav.addObject("saleBeginTimeStr", saleBeginTimeStr);
		mav.addObject("saleEndTimeStr", saleEndTimeStr);
//		mav.addObject("statusMap", DZPWConstants.GROUP_TICKET_STATUS_NAME);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：经销商销售统计导出
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28上午11:09:32
	 * @修改内容：
	 * @参数：@param saleBeginTimeStr
	 * @参数：@param saleEndTimeStr
	 * @参数：@param sortMethod
	 * @参数：@param dealerId
	 * @return:void
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/report/dealer_sale_count/export")
	public void export(  HttpServletResponse response,
						 @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
			             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
			             @RequestParam(value="sortMethod", required = false)Integer sortMethod,
			             @RequestParam(value="dealerId", required = false)String dealerId){
		
		DealerSaleStatisticsQo qo = new DealerSaleStatisticsQo();
		//统计方式
		qo.setQueryType(sortMethod);
		//经销商ID
		if(StringUtils.isNotBlank(dealerId)){
			qo.setDealerId(dealerId);
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
			File file = this.ticketSaleStatisticsLocalService.exportDealerSaleStatisticsToExcel(qo);
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
	 * @方法功能说明：用户明细导出
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-28上午11:29:36
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param dealerId
	 * @参数：@param orderNo
	 * @return:void
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/report/dealer_sale_count/export/detail")
	public void exportDetail( HttpServletResponse response,
							  @RequestParam(value="dealerId", required = false)String dealerId,
							  @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                              @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
							  @RequestParam(value="orderNo", required = false)String orderNo){
		
		TicketOrderTouristDetailQo qo = new TicketOrderTouristDetailQo();
		if(StringUtils.isNotBlank(dealerId)){
			qo.setDealerId(dealerId);
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
