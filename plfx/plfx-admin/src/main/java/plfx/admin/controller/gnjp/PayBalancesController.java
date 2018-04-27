package plfx.admin.controller.gnjp;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.admin.controller.BaseController;
import plfx.jp.command.pay.balances.CreatePayBalancesCommand;
import plfx.jp.command.pay.balances.DeletePayBalancesCommand;
import plfx.jp.command.pay.balances.UpdatePayBalancesCommand;
import plfx.jp.domain.model.pay.balances.PayBalances;
import plfx.jp.pojo.dto.pay.balances.PayBalancesDTO;
import plfx.jp.qo.pay.balances.PayBalancesQO;
import plfx.jp.spi.inter.pay.balances.PayBalancesService;
import plfx.jp.system.PayBalancesConstants;

@Controller
@RequestMapping(value = "/pay/balances")
public class PayBalancesController extends BaseController{
	
	@Autowired
	private PayBalancesService payBalancesService;
	
	public static String PAGE_LIST = "/pay/balances/balances_list.html";
	public static String PAGE_TOADD = "/pay/balances/balances_add.html";
	public static String PAGE_TOUPDATE = "/pay/balances/balances_update.html";
	
	/**
	 * 
	 * @方法功能说明：支付宝余额列表
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午9:59:15
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param payBalancesQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryPayBalancesList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute PayBalancesQO payBalancesQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize
		) {
		
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination.setCondition(payBalancesQO);// 添加查询条件

		pagination = payBalancesService.queryPagination(pagination);

		model.addAttribute("pagination", pagination);
		model.addAttribute("payBalancesQO", payBalancesQO);
		//预警类型map
		model.addAttribute("PAY_BALANCES_TYPE_MAP", PayBalancesConstants.PAY_BALANCES_TYPE_MAP);
		return PAGE_LIST;
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到支付宝余额新增界面
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午9:59:30
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAdd")
	public String toAddPayBalancesList(HttpServletRequest request, Model model){
		//预警类型map
		model.addAttribute("PAY_BALANCES_TYPE_MAP", PayBalancesConstants.PAY_BALANCES_TYPE_MAP);
		return PAGE_TOADD;
	}
	
	/**
	 * 
	 * @方法功能说明：新增支付宝余额
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午10:00:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public String addPayBalances(HttpServletRequest request, Model model,
			@ModelAttribute CreatePayBalancesCommand command){
		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "保存失败";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try{
			payBalancesService.createPayBalances(command);
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "保存成功";
		}catch(Exception e){
			HgLogger.getInstance().info("yuqz", "新增支付宝余额记录异常:" + HgLogger.getStackTrace(e));
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "balances");
	}
	
	
	@RequestMapping(value = "/toUpdate")
	public String toUpdatePayBalancesList(HttpServletRequest request, Model model, @RequestParam(value = "id", required = true) String id){
		PayBalancesQO payBalancesQO = new PayBalancesQO();
		payBalancesQO.setId(id);
		PayBalancesDTO payBalancesDTO = payBalancesService.queryUnique(payBalancesQO);
		model.addAttribute("payBalancesDTO", payBalancesDTO);
		//预警类型map
		model.addAttribute("PAY_BALANCES_TYPE_MAP", PayBalancesConstants.PAY_BALANCES_TYPE_MAP);
		return PAGE_TOUPDATE;
	}
	
	/**
	 * 
	 * @方法功能说明：修改支付宝余额
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午10:00:35
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest request, Model model,
			@ModelAttribute UpdatePayBalancesCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "修改失败";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		boolean result = payBalancesService.adminUpdatePayBalances(command);
		if (result) {
			HgLogger.getInstance().info("yuqz", "票量分销平台-分销管理-支付宝余额管理管理-修改成功"+command.getId());
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "修改成功";
		} else {
			HgLogger.getInstance().info("yuqz", "票量分销平台-分销管理-支付宝余额管理管理-修改失败"+command.getId());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "balances");
	}
	
	/**
	 * 
	 * @方法功能说明：删除支付宝余额
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午10:00:45
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest request, Model model,
			@ModelAttribute DeletePayBalancesCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "删除失败";
		String callbackType = null;
		boolean result = payBalancesService.deletePayBalances(command);
		HgLogger.getInstance().info("yuqz", "票量分销平台-分销管理-支付宝余额管理管理-删除结果="+result + ",id=" + command.getId());
		if(result){
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "删除成功";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "balances");
		  
	}
}
