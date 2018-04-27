package hg.dzpw.admin.controller.report;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hg.common.page.Pagination;
import hg.common.util.StringUtil;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.TicketUseStatisticsLocalService;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.qo.ScenicSpotUseStatisticsQo;
import hg.dzpw.pojo.qo.TicketUsedTouristDetailQo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：景区入园统计
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-19下午4:32:21
 * @版本：V1.0
 *
 */
@Controller
public class EnterScenicSpotController {
	
	@Autowired
	private TicketUseStatisticsLocalService ticketUseStatisticsLocalService;
	
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * @方法功能说明：景区入园统计列表
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-26下午2:37:12
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param saleBeginTimeStr
	 * @参数：@param saleEndTimeStr
	 * @参数：@param name
	 * @参数：@return
	 * @return:ModelAndView
	 */
	@RequestMapping("/report/enter_scenicspot_count")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
                             @RequestParam(value="numPerPage", required = false)Integer pageSize,
                             @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                             @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
                             @RequestParam(value="scenicSpotId", required = false)String scenicSpotId,
                             @RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType){
		
		ScenicSpotUseStatisticsQo qo = new ScenicSpotUseStatisticsQo();
		
		List<ScenicSpot> scenicSpotList = scenicSpotLocalService.queryList(new ScenicSpotQo());
		//景区id
		if(StringUtils.isNotBlank(scenicSpotId)){
			qo.setScenicSpotId(scenicSpotId);
		}
		
		//产品类型 单票、联票
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
		mav.addObject("scenicSpotId", scenicSpotId);//景区id
		mav.addObject("ticketPolicyType", ticketPolicyType);
		mav.addObject("scenicSpotList", scenicSpotList);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：景区入园统计用户明细列表
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-26下午2:37:30
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param scenicSpotId
	 * @参数：@param userName
	 * @参数：@param cerNo
	 * @参数：@return
	 * @return:ModelAndView
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
	 * @方法功能说明：景区入园统计列表导出
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-26下午2:38:01
	 * @参数：@param saleBeginTimeStr
	 * @参数：@param saleEndTimeStr
	 * @参数：@param name
	 * @参数：@return
	 * @return:String
	 */
	@ResponseBody
	@RequestMapping("/report/enter_scenicspot_count/export")
	public void exportExcel( HttpServletResponse response,
			                @RequestParam(value="pageNum", required = false)Integer pageNum,
                            @RequestParam(value="numPerPage", required = false)Integer pageSize,
                            @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
                            @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
                            @RequestParam(value="scenicSpotId", required = false)String scenicSpotId,
                            @RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType){
		
		ScenicSpotUseStatisticsQo qo = new ScenicSpotUseStatisticsQo();
		
		//景区id
				if(StringUtils.isNotBlank(scenicSpotId)){
					qo.setScenicSpotId(scenicSpotId);
				}
				
				//产品类型 单票、联票
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
			File file = this.ticketUseStatisticsLocalService.exportScenicSpotUseStatisticsToExcel(qo);
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
	 * @方法功能说明：景区入园统计用户明细列表导出
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-26下午2:38:21
	 * @参数：@param scenicSpotId
	 * @参数：@param userName
	 * @参数：@param cerNo
	 * @参数：@return
	 * @return:String
	 */
	@ResponseBody
	@RequestMapping("/report/enter_scenicspot_count/export/detail")
	public void exportDetailExcel( HttpServletResponse response,
									HttpServletRequest request,
									 @RequestParam(value="scenicSpotId", required = false)String scenicSpotId,
									 @RequestParam(value="userName", required = false)String userName,
									 @RequestParam(value="saleBeginTimeStr", required = false)String saleBeginTimeStr,
							         @RequestParam(value="saleEndTimeStr", required = false)String saleEndTimeStr,
							         @RequestParam(value="cerNo", required = false)String  cerNo,
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
		
		if(ticketPolicyType!=null && ticketPolicyType==1)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		
		if(ticketPolicyType!=null && ticketPolicyType==2)
			qo.setTicketPolicyType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		
		try {
			File file = this.ticketUseStatisticsLocalService.exportTicketUsedTouristDetailToExcel(qo);
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
