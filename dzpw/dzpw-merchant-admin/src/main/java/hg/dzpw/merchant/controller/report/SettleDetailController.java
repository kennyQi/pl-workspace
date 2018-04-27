package hg.dzpw.merchant.controller.report;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.StringUtil;
import hg.dzpw.app.service.local.SettleDetailLocalService;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.pojo.qo.SettleDetailQo;
import hg.dzpw.pojo.vo.SettleDetailVo;

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
 * 此类描述的是： 结算明细控制器，景区端结算管理
 * @author: guotx 
 * @version: 2015-11-27 下午2:20:47
 */
@Controller
@RequestMapping(value="/report/settleDetail")
public class SettleDetailController {
	@Autowired
	private SettleDetailLocalService settleDetailLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response,Model model,
			@RequestParam(value="ticketType", required = false)Integer ticketType,
			@RequestParam(value="pageNum", required = false)Integer pageNum,
            @RequestParam(value="numPerPage", required = false)Integer pageSize,
            @RequestParam(value="BeginTimeStr", required = false)String BeginTimeStr,
            @RequestParam(value="EndTimeStr", required = false)String EndTimeStr,
            @RequestParam(value="scenicSpotStatus", required = false)Integer scenicSpotStatus){
		SettleDetailQo qo = new SettleDetailQo();
		
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
		if(ticketType!=null)
		{
			qo.setTicketType(ticketType);//票务类型
		}
		if(scenicSpotStatus!=null)
		{
			qo.setScenicSpotStatus(scenicSpotStatus);//景区状态
		}
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
		qo.setScenicSpotId(MerchantSessionUserManager.getSessionUserId(request));
		Pagination pagination = settleDetailLocalService.querySettleDetail(qo,false);
		model.addAttribute("pagination", pagination);
		model.addAttribute("BeginTimeStr", BeginTimeStr);
		model.addAttribute("EndTimeStr", EndTimeStr);
		model.addAttribute("ticketType", ticketType);
		model.addAttribute("scenicSpotStatus", scenicSpotStatus);
		return "report/settleDetail/settleDetail_list.html";
	}
	
	@RequestMapping("/export")
	public void exportExcel(HttpServletRequest request,
			                HttpServletResponse response,
			                @RequestParam(value="ticketType", required = false)Integer ticketType,
			                @RequestParam(value="pageNum", required = false)Integer pageNum,
                            @RequestParam(value="numPerPage", required = false)Integer pageSize,
                            @RequestParam(value="BeginTimeStr", required = false)String BeginTimeStr,
                            @RequestParam(value="EndTimeStr", required = false)String EndTimeStr,
                            @RequestParam(value="scenicSpotStatus", required = false)Integer scenicSpotStatus)
	{
        SettleDetailQo qo = new SettleDetailQo();
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
		if(ticketType!=null)
		{
			qo.setTicketType(ticketType);//门票类型
		}
		if(scenicSpotStatus!=null)
		{
			qo.setScenicSpotStatus(scenicSpotStatus);//景区状态
		}
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
		qo.setScenicSpotId(MerchantSessionUserManager.getSessionUserId(request));
		try {
			File file = settleDetailLocalService.exportSettleDetailToExcel(qo);
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
