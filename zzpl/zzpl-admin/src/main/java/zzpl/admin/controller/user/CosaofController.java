package zzpl.admin.controller.user;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.user.COSAOFService;
import zzpl.app.service.local.user.DepartmentService;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.Department;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.command.user.SettingCosaofCommand;
import zzpl.pojo.dto.user.CosaofDTO;
import zzpl.pojo.qo.user.COSAOFQO;
import zzpl.pojo.qo.user.CostCenterQO;
import zzpl.pojo.qo.user.DepartmentQO;
import zzpl.pojo.qo.user.UserRoleQO;

@Controller
@RequestMapping(value = "/cosaof")
public class CosaofController extends BaseController{
	
	@Autowired
	private COSAOFService cosaofService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 
	 * @方法功能说明：跳转到列表页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:10:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param companyQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute CostCenterQO costCenterQO) {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		DepartmentQO departmentQO = new DepartmentQO();
		departmentQO.setCompanyID(user.getCompanyID());
		List<Department> departments = new ArrayList<Department>();
		if(StringUtils.isNotBlank(user.getCompanyID())){
			departments = departmentService.queryList(departmentQO);
		}
		model.addAttribute("departments", departments);
		
		String travleID = SysProperties.getInstance().get("travleAdminID");
		UserRoleQO userRoleQO = new UserRoleQO();
		userRoleQO.setUserID(user.getId());
		List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
		String roleID = "";
		for (UserRole userRole : userRoles) {
			roleID += userRole.getRole().getId()+",";
		}
		if (roleID.contains(travleID)) {
			model.addAttribute("role", "success");
		}else {
			model.addAttribute("role", "fail");
		}
		
		return "/content/user/cosaof_list.html";
	}

	/**
	 * @throws ParseException 
	 * @方法功能说明：列表展示
	 * @throws ParseException 
	 * @修改者名字：guok
	 * @修改时间：2015年8月13日 10:01:54
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param cosaofQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param companyName
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/cosaoflist")
	public Pagination cosaofList(HttpServletRequest request, Model model,COSAOFQO cosaofQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "departmentID", required = false) String departmentID,
			@RequestParam(value = "startCreateTime", required = false) Date startCreateTime,
			@RequestParam(value = "endCreateTime", required = false) Date endCreateTime) throws ParseException {
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		cosaofQO.setDepartmentID(departmentID);
		cosaofQO.setStatus(status);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//下单时间
		if (StringUtils.isBlank(cosaofQO.getStartDate())) {
			cosaofQO.setStartCreateTime(null);
		}else {
			cosaofQO.setStartCreateTime(sdf.parse(cosaofQO.getStartDate()));
		}
		if(StringUtils.isBlank(cosaofQO.getEndDate())){
			cosaofQO.setEndCreateTime(null);
		}else {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse(cosaofQO.getEndDate()));
			cal1.add(Calendar.DATE, 1);
			cosaofQO.setEndCreateTime(cal1.getTime());
		}
		
		cosaofQO.setOrderByCreatTime("desc");
		//获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		UserRoleQO userRoleQO = new UserRoleQO();
		userRoleQO.setUserID(user.getId());
		List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
		String roleID = "";
		for (UserRole userRole : userRoles) {
			roleID += userRole.getRole().getId()+",";
		}
		cosaofQO.setCompanyID(user.getCompanyID());
		cosaofQO.setRoleID(roleID);
		
		HgLogger.getInstance().info("gk",	"【CosaofController】【cosaofList】,cosaofQO："+JSON.toJSONString(cosaofQO));
		
		//正常状态
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(cosaofQO);
		if(StringUtils.isNotBlank(user.getCompanyID())){
			pagination = cosaofService.getList(pagination);
		}
		
		return pagination;
	}
	
	/**
	 * @Title: settingCosaof 
	 * @author guok
	 * @Description: 结算
	 * @Time 2015年8月13日下午3:26:05
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/setting")
	public String settingCosaof(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute SettingCosaofCommand command) {
		try {
			HgLogger.getInstance().info("gk",	"【CosaofController】【settingCosaof】,command："+JSON.toJSONString(command));
			cosaofService.setting(command);
			return DwzJsonResultUtil.createJsonString("200", "已结算!", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("cosaofMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}
	
	/**
	 * @throws ParseException 
	 * @Title: getCosaofExcel 
	 * @author guok
	 * @Description: 导出结算中心列表
	 * @Time 2015年9月23日上午10:30:08
	 * @param request
	 * @param response
	 * @param cosaofQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/getCosaofExcel")
	public String getCosaofExcel(HttpServletRequest request,
			HttpServletResponse response,COSAOFQO cosaofQO) throws ParseException {
		HgLogger.getInstance().info("gk",	"【CosaofController】【getCosaofExcel】：导出结算中心。");
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		String travleID = SysProperties.getInstance().get("travleAdminID");
		UserRoleQO userRoleQO = new UserRoleQO();
		userRoleQO.setUserID(user.getId());
		List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
		String roleID = "";
		boolean flag = false;
		for (UserRole userRole : userRoles) {
			roleID += userRole.getRole().getId()+",";
		}
		if (roleID.contains(travleID)) {
			//model.addAttribute("role", "success");
			flag = true;
		}else {
			//model.addAttribute("role", "fail");
			flag = false;
		}
		cosaofQO.setCompanyID(user.getCompanyID());
		cosaofQO.setRoleID(roleID);
		cosaofQO.setOrderByCreatTime("desc");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//下单时间
		if (StringUtils.isBlank(cosaofQO.getStartDate())) {
			cosaofQO.setStartCreateTime(null);
		}else {
			cosaofQO.setStartCreateTime(sdf.parse(cosaofQO.getStartDate()));
		}
		if(StringUtils.isBlank(cosaofQO.getEndDate())){
			cosaofQO.setEndCreateTime(null);
		}else {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse(cosaofQO.getEndDate()));
			cal1.add(Calendar.DATE, 1);
			cosaofQO.setEndCreateTime(cal1.getTime());
		}
		
		OutputStream os;
		WritableWorkbook workbook;
		//设置内容格式
		response.setHeader("Content-Type","application/x-xls;charset=utf-8" );
		//这个用来提示下载的文件名
		response.setHeader("Content-Disposition", "attachment; filename="+"cosaof.xls");
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			List<CosaofDTO> cosaofDTOs = cosaofService.getCosaofDTOs(cosaofQO);
			HgLogger.getInstance().info("gk", "导出结算中心Excel，要导出的结算中心列表："+JSON.toJSONString(cosaofDTOs));
			
			if(cosaofDTOs.size()>0){
				WritableSheet ws = workbook.createSheet("结算中心表", 0);
				Label head1 = new Label(0,0,"订单号");
				ws.addCell(head1);
				Label head13 = new Label(1,0,"公司名称");
				ws.addCell(head13);
				Label head2 = new Label(2,0,"乘机人");
				ws.addCell(head2);
				Label head3 = new Label(3,0,"航程");
				ws.addCell(head3);
				Label head4 = new Label(4,0,"票号");
				ws.addCell(head4);
				Label head5 = new Label(5,0,"下单时间");
				ws.addCell(head5);
				Label head6 = new Label(6,0,"成本中心");
				ws.addCell(head6);
				Label head7 = new Label(7,0,"支付价");
				ws.addCell(head7);
				Label head8 = new Label(8,0,"退款金额");
				ws.addCell(head8);
				Label head9 = new Label(9,0,"企业结算价");
				ws.addCell(head9);
				if (flag) {
					Label head10 = new Label(10,0,"供应商价");
					ws.addCell(head10);
					Label head11 = new Label(11,0,"出票状态");
					ws.addCell(head11);
					Label head12 = new Label(12,0,"结算状态");
					ws.addCell(head12);
					Label head14 = new Label(13,0,"订票人");
					ws.addCell(head14);
				}else {
					Label head11 = new Label(10,0,"出票状态");
					ws.addCell(head11);
					Label head12 = new Label(11,0,"结算状态");
					ws.addCell(head12);
					Label head14 = new Label(12,0,"订票人");
					ws.addCell(head14);
				}
				
				
				int i = 1;
				//Double totalPirce = 0d;
				for(CosaofDTO cosaofDTO : cosaofDTOs){
					Label orderNO = new Label(0, i, cosaofDTO.getOrderNO());
					ws.addCell(orderNO); 
					Label compayName = new Label(1, i, cosaofDTO.getCompanyName());
					ws.addCell(compayName); 
					Label passengerName = new Label(2,i,cosaofDTO.getPassengerName());
					ws.addCell(passengerName);
					Label voyage = new Label(3,i,cosaofDTO.getVoyage());
					ws.addCell(voyage);
					Label airID = new Label(4,i,cosaofDTO.getAirID());
					ws.addCell(airID);
					Label createTime = new Label(5, i, cosaofDTO.getCreateTime());
					ws.addCell(createTime);
					Label costCenterName = new Label(6,i,cosaofDTO.getCostCenterName());
					ws.addCell(costCenterName);
					Label platTotalPrice = new Label(7,i,cosaofDTO.getPlatTotalPrice().toString());
					ws.addCell(platTotalPrice);
					Label refundPrice = new Label(8,i,cosaofDTO.getRefundPrice().toString());
					ws.addCell(refundPrice);
					Label casaofPrice = new Label(9,i,cosaofDTO.getCasaofPrice().toString());
					ws.addCell(casaofPrice);
					if (flag) {
						if (cosaofDTO.getTotalPrice() != null) {
							Label totalPrice = new Label(10,i,cosaofDTO.getTotalPrice().toString());
							ws.addCell(totalPrice);
						}else {
							Label totalPrice = new Label(10,i,cosaofDTO.getFailed());
							ws.addCell(totalPrice);
						}
						
						Label oStatus = new Label(11,i,cosaofDTO.getoStatus());
						ws.addCell(oStatus);
						Label status = new Label(12,i,cosaofDTO.getStatus());
						ws.addCell(status);
						Label userName = new Label(13,i,cosaofDTO.getUserName());
						ws.addCell(userName);
					}else {
						Label oStatus = new Label(10,i,cosaofDTO.getoStatus());
						ws.addCell(oStatus);
						Label status = new Label(11,i,cosaofDTO.getStatus());
						ws.addCell(status);
						Label userName = new Label(12,i,cosaofDTO.getUserName());
						ws.addCell(userName);
					}
					
					
//					totalPirce = totalPirce + cosaofDTO.getPlatTotalPrice();
					i++;
				}
				//添加合计金额
				/*Label orderNum = new Label(0,i,"合计");
				ws.addCell(orderNum);
				Label price = new Label(6,i,totalPirce.toString());
				ws.addCell(price);*/
				
				workbook.write();
				workbook.close();
				os.close();
			}
			
		} catch (Exception e) {
			HgLogger.getInstance().error("gk", "CompanyCenterController->getCosaofExcel->Exception:" + HgLogger.getStackTrace(e));
		}
		return null;
	}
	
}
