package hg.dzpw.dealer.admin.controller.report;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import hg.dzpw.dealer.admin.component.manager.DealerSessionUserManager;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.ticket.SingleTicket;
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
 * 
 * @类功能说明：经销商端财务明细控制器
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-7上午9:29:00
 * @版本：
 */
@Controller
@RequestMapping("/finance")
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
	@RequestMapping(value="/detail")
	public String detail(HttpServletRequest request,
			HttpServletResponse response,Model model,
			@RequestParam(value="pageNum", required = false)Integer pageNum,
            @RequestParam(value="numPerPage", required = false)Integer pageSize,
            @RequestParam(value="settleNo", required = false)String settleNo,
            @RequestParam(value="policyNo", required = false)String policyNo,
            @RequestParam(value="policyName", required = false)String policyName,
            @RequestParam(value="orderId", required = false)String orderId,
            @RequestParam(value="status", required = false)Integer status,
            @RequestParam(value="BeginTimeStr", required = false)String BeginTimeStr,
            @RequestParam(value="EndTimeStr", required = false)String EndTimeStr){
		FinanceDetailQo qo = new FinanceDetailQo();
		qo.setDealerId(DealerSessionUserManager.getSessionUserId(request));
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
		if(StringUtils.isNotBlank(settleNo))
		{
			qo.setSettleNo(settleNo);
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
		//出票的起止时间
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
		
		Pagination pagination = financeLocalService.queryFinanceDetail(qo, false);
		
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		GroupTicketQo gqo = new GroupTicketQo();
		singleTicketQo.setGroupTicketQo(gqo);
		singleTicketQo.setStatus(7);
		for(FinanceVo v:(List<FinanceVo>)pagination.getList())
		{
			Double total = 0d;
			//设置退款金额
			gqo.setId(v.getGroupTicketId());
			List<SingleTicket> singleTicketList = singleTicketLocalService.queryList(singleTicketQo);
			for(SingleTicket s:singleTicketList)
			{
				total = total+(s.getSettlementInfo().getDealerPrice()==null?0:s.getSettlementInfo().getDealerPrice());
			}
//			if(total>0)
//			total =total - dealer.getAccountInfo().getSettlementFee();
			v.setRefundAmmount(total);
		}
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("BeginTimeStr",BeginTimeStr);
		model.addAttribute("EndTimeStr",EndTimeStr);
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
			                @RequestParam(value="settleNo", required = false)String settleNo,
			                @RequestParam(value="policyNo", required = false)String policyNo,
			                @RequestParam(value="policyName", required = false)String policyName,
			                @RequestParam(value="orderId", required = false)String orderId,
			                @RequestParam(value="status", required = false)Integer status,
			                @RequestParam(value="BeginTimeStr", required = false)String BeginTimeStr,
			                @RequestParam(value="EndTimeStr", required = false)String EndTimeStr)
	{
		FinanceDetailQo qo = new FinanceDetailQo();
		qo.setDealerId(DealerSessionUserManager.getSessionUserId(request));
		if(StringUtils.isNotBlank(settleNo))
		{
			qo.setSettleNo(settleNo);
		}
		if(StringUtils.isNotBlank(policyNo))
		{
			qo.setPolicyNo(policyNo);
		}
		if(StringUtils.isNotBlank(policyName))
		{
			qo.setPolicyName(policyName);
		}
		if(StringUtils.isNotBlank(orderId))
		{
			qo.setOrderId(orderId);
		}
		if(status!=null)
		{
			qo.setStatus(status);
		}
		//出票的起止时间
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
			File file = financeLocalService.exportFinanceToExcel(qo);
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
