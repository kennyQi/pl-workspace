package hg.dzpw.merchant.controller.check;

import hg.common.page.Pagination;
import hg.common.util.StringUtil;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;
import hg.dzpw.app.common.util.DateUtils;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.service.local.FinanceManagementLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.pojo.qo.ReconciliationCollectOrderQo;
import hg.log.util.HgLogger;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @类功能说明：财务对账汇总
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午4:47:48
 * @版本：V1.0
 */
@Controller
@RequestMapping(value = "/checkAccountAll")
public class CheckAccountAllController extends BaseController {

	public final static String PAGE_PATH_ALL_LIST = "/check/check_account_all_list.html";

	@Autowired
	private FinanceManagementLocalService financeManagementLocalService;
	
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	
	@RequestMapping(value = "/list2")
	public String list(Model model){
		
//		//查询景区
//		List<ScenicSpot> scenicSpotList = scenicSpotLocalService.queryList(new ScenicSpotQo());
//		Map<String,String> scenicSpotMap=new HashMap<String,String>();
//		if(scenicSpotList!=null){
//			for(ScenicSpot s :scenicSpotList){
//				scenicSpotMap.put(s.getId(), s.getBaseInfo().getName());
//			}
//		}
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(20);
				
//		model.addAttribute("scenicSpotMap", scenicSpotMap);
		model.addAttribute("pagination", pagination);
//		model.addAttribute("reconciliationCollectOrderQo", new ReconciliationCollectOrderQo());
		return PAGE_PATH_ALL_LIST;
	}
	
	
	/**
	 * 财务汇总对账单列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param articleQo
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String allList(HttpServletRequest request, Model model,
						  @ModelAttribute ReconciliationCollectOrderQo reconciliationCollectOrderQo,
						  @RequestParam(value="pageNum", required = false)Integer pageNum,
			              @RequestParam(value="numPerPage", required = false)Integer pageSize) {
		
		HgLogger.getInstance().info("zzx",
				"进入财务汇总对账单列表" +
				"查询方法:reconciliationCollectOrderQo【" + reconciliationCollectOrderQo + "】");
		
		String endDateStr = DateUtils.formatDate(reconciliationCollectOrderQo.getOrderDateEnd())+" 23:59:59";
		reconciliationCollectOrderQo.setOrderDateEnd(DateUtils.parseDateTime(endDateStr));
		
		Pagination pagination = new Pagination();
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
			reconciliationCollectOrderQo.setPageNo(pageNum);
		}else{
			pagination.setPageNo(1);
			reconciliationCollectOrderQo.setPageNo(1);
		}
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
			reconciliationCollectOrderQo.setPageSize(pageSize);
		}else{
			pagination.setPageSize(20);
			reconciliationCollectOrderQo.setPageSize(20);
		}
		
		
		//获取登录用户id，即景区id
		String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);
		reconciliationCollectOrderQo.setScenicSpotId(scenicSpotId);
		reconciliationCollectOrderQo.setScenicSpotSelect(true);
		
		pagination =  financeManagementLocalService.queryReconciliationCollectOrder(reconciliationCollectOrderQo);
		
		//查询景区
		List<ScenicSpot> scenicSpotList = scenicSpotLocalService.queryList(new ScenicSpotQo());
		Map<String,String> scenicSpotMap=new HashMap<String,String>();
		if(scenicSpotList!=null){
			for(ScenicSpot s :scenicSpotList){
				scenicSpotMap.put(s.getId(), s.getBaseInfo().getName());
			}
		}
		
		model.addAttribute("scenicSpotMap", scenicSpotMap);
		model.addAttribute("pagination", pagination);
		model.addAttribute("reconciliationCollectOrderQo", reconciliationCollectOrderQo);
		
		return PAGE_PATH_ALL_LIST;
	}

	
	@RequestMapping("/excel_download_all")
	public void toExcelAll(ModelMap model,HttpServletResponse response,
			@ModelAttribute ReconciliationCollectOrderQo reconciliationCollectOrderQo,
			HttpServletRequest request) {
		
		HgLogger.getInstance().info("zzx",
				"进入财务汇总对账单excel下载方法:reconciliationCollectOrderQo【" + reconciliationCollectOrderQo + "】");
		
		//修正结束时间为一天最后一秒
		String endDateStr = DateUtils.formatDate(reconciliationCollectOrderQo.getOrderDateEnd())+" 23:59:59";
		reconciliationCollectOrderQo.setOrderDateEnd(DateUtils.parseDateTime(endDateStr));
		//获取登录用户id，即景区id
		String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);
		reconciliationCollectOrderQo.setScenicSpotId(scenicSpotId);
		reconciliationCollectOrderQo.setScenicSpotSelect(true);
		
		try {
			File file = this.financeManagementLocalService.exportReconciliationCollectOrderToExcel(reconciliationCollectOrderQo);
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
