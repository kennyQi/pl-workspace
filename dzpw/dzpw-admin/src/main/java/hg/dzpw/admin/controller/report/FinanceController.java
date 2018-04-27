package hg.dzpw.admin.controller.report;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.StringUtil;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.vo.DealerSessionUserVo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.FinanceLocalService;
import hg.dzpw.app.service.local.FinanceManagementLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.pojo.qo.FinanceDetailQo;
import hg.dzpw.pojo.qo.SettleDetailQo;
import hg.dzpw.pojo.vo.FinanceVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 运营端财务明细Controller
 * @author CaiHuan
 *
 * 日期:2016-1-5
 */
@Controller
@RequestMapping("/report/finance")
public class FinanceController {
	
	@Autowired
	private FinanceLocalService financeLocalService;
	
	@Autowired
	private DealerLocalService dealerLocalService;
	
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	 * @描述： 财务明细主页面
	 * @author: guotx 
	 * @version: 2015-12-7 上午9:39:39
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list")
	public String detail(HttpServletRequest request,
			HttpServletResponse response,Model model,
			@RequestParam(value="pageNum", required = false)Integer pageNum,
            @RequestParam(value="numPerPage", required = false)Integer pageSize,
            @RequestParam(value="scenicSpotName", required = false)String scenicSpotName,
            @RequestParam(value="dealerName", required = false)String dealerName,
            @RequestParam(value="policyNo", required = false)String policyNo,
            @RequestParam(value="policyName", required = false)String policyName,
            @RequestParam(value="orderId", required = false)String orderId,
            @RequestParam(value="scenicSpotStatus", required = false)Integer scenicSpotStatus,//景区状态
            @RequestParam(value="status", required = false)Integer status,
            @RequestParam(value="BeginTimeStr", required = false)String BeginTimeStr,
            @RequestParam(value="EndTimeStr", required = false)String EndTimeStr
			){
		FinanceDetailQo qo = new FinanceDetailQo();
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
		if(StringUtils.isNotBlank(scenicSpotName))
		{
			qo.setScenicSpotName(scenicSpotName);
		}
		if(StringUtils.isNotBlank(dealerName))
		{
			qo.setDealerName(dealerName);
		}
		if(StringUtils.isNotBlank(policyNo))
		{
			qo.setPolicyNo(policyNo);
		}
		if(StringUtils.isNotBlank(policyName))
		{
			qo.setPolicyName(policyName);
		}
		if(status!=null)
		{
			qo.setStatus(status);
		}
		if(StringUtils.isNotBlank(orderId))
		{
			qo.setOrderId(orderId);
		}
		if(scenicSpotStatus!=null)
		{
			qo.setScenicSpotStatus(scenicSpotStatus);
		}
		
		//结算的起止时间
		if(StringUtils.isNotBlank(BeginTimeStr)){
			try {
				qo.setDateBegin(sdf.parse(BeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setDateBegin(null);
			}
		}
		
		if(StringUtils.isNotBlank(EndTimeStr)){
			try {
				qo.setDateEnd(sdf.parse(EndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setDateEnd(null);
			}
		}
		
		Pagination pagination = financeLocalService.queryAdminFinanceDetail(qo, false);
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("BeginTimeStr",BeginTimeStr);
		model.addAttribute("EndTimeStr",EndTimeStr);
		model.addAttribute("nowDate", new Date());
		return "report/finance/financeDetail_list.html";
	}
	
	/**
	 * 导出
	 * @author CaiHuan
	 * @param request
	 * @param response
	 * @param ticketType
	 * @param pageNum
	 * @param pageSize
	 * @param BeginTimeStr
	 * @param EndTimeStr
	 */
	@RequestMapping("/export")
	public void exportExcel(HttpServletRequest request,
			                HttpServletResponse response,
			                @RequestParam(value="scenicSpotName", required = false)String scenicSpotName,
			                @RequestParam(value="dealerName", required = false)String dealerName,
			                @RequestParam(value="policyNo", required = false)String policyNo,
			                @RequestParam(value="policyName", required = false)String policyName,
			                @RequestParam(value="orderId", required = false)String orderId,
			                @RequestParam(value="scenicSpotStatus", required = false)Integer scenicSpotStatus,//景区状态
			                @RequestParam(value="status", required = false)Integer status,
			                @RequestParam(value="BeginTimeStr", required = false)String BeginTimeStr,
			                @RequestParam(value="EndTimeStr", required = false)String EndTimeStr)
	{
		FinanceDetailQo qo = new FinanceDetailQo();
//		qo.setDealerId(DealerSessionUserManager.getSessionUserId(request));
		if(StringUtils.isNotBlank(scenicSpotName))
		{
			qo.setScenicSpotName(scenicSpotName);
		}
		if(StringUtils.isNotBlank(dealerName))
		{
			qo.setDealerName(dealerName);
		}
		if(StringUtils.isNotBlank(policyNo))
		{
			qo.setPolicyNo(policyNo);
		}
		if(StringUtils.isNotBlank(policyName))
		{
			qo.setPolicyName(policyName);
		}
		if(status!=null)
		{
			qo.setStatus(status);
		}
		if(StringUtils.isNotBlank(orderId))
		{
			qo.setOrderId(orderId);
		}
		if(scenicSpotStatus!=null)
		{
			qo.setScenicSpotStatus(scenicSpotStatus);
		}
		//结算的起止时间
		if(StringUtils.isNotBlank(BeginTimeStr)){
			try {
				qo.setDateBegin(sdf.parse(BeginTimeStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setDateBegin(null);
			}
		}
		
		if(StringUtils.isNotBlank(EndTimeStr)){
			try {
				qo.setDateEnd(sdf.parse(EndTimeStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setDateEnd(null);
			}
		}
		
		try {
			File file = financeLocalService.exportAdminFinanceToExcel(qo);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
