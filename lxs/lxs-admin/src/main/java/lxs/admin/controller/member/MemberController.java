package lxs.admin.controller.member;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.Md5Util;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lxs.admin.controller.BaseController;
import lxs.app.service.line.LineOrderLocalService;
import lxs.app.service.line.LineOrderTravelerService;
import lxs.app.service.user.LxsUserService;
import lxs.app.service.user.RegisterSagaService;
import lxs.domain.model.line.LineOrder;
import lxs.domain.model.user.LxsUser;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.command.user.ResetPasswordCommand;
import lxs.pojo.dto.line.LineOrderStatusDTO;
import lxs.pojo.dto.line.XLOrderStatusConstant;
import lxs.pojo.dto.user.user.UserBaseInfoDTO;
import lxs.pojo.dto.user.user.UserBusinessInfoDTO;
import lxs.pojo.dto.user.user.UserContactInfoDTO;
import lxs.pojo.dto.user.user.UserDTO;
import lxs.pojo.dto.user.user.UserStatusDTO;
import lxs.pojo.qo.line.LineOrderQO;
import lxs.pojo.qo.line.LineOrderTravelerQO;
import lxs.pojo.qo.user.user.LxsUserQO;
import lxs.pojo.qo.user.user.RegisterSagaQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/member")
public class MemberController extends BaseController {

	@Autowired
	private LxsUserService lxsUserService;
	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private RegisterSagaService registerSagaService;
	@Autowired
	private LineOrderTravelerService lineOrderTravelerService;
		
	/**
	 * 
	 * @方法功能说明：用户列表
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日上午10:04:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param userQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/personList")
	public String queryPersonList(HttpServletRequest request, Model model,
			@ModelAttribute LxsUserQO userQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		//获取查询条件
		if(userQO.getNickName()!=null){
			userQO.setNickNameLike(true);
		}
		if(pageNo==null&&pageSize==null){
			pageNo=1;
			pageSize=20;
		}
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(userQO);
		pagination = lxsUserService.queryPagination(pagination);
		List<LxsUser> lxsUsers= (List<LxsUser>) pagination.getList();
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for(LxsUser lxsUser:lxsUsers){
			//基本信息赋值
			UserDTO userDTO = new UserDTO();
			userDTO.setId(lxsUser.getId());
			UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
			if (lxsUser.getBaseInfo()!=null) {
				userBaseInfoDTO.setNickName(lxsUser.getBaseInfo().getNickName());
				userBaseInfoDTO.setCreateTime(lxsUser.getBaseInfo().getCreateTime());
			}
		
			userDTO.setBaseInfo(userBaseInfoDTO);
			UserContactInfoDTO userContactInfoDTO = new UserContactInfoDTO();
			
			if (lxsUser.getContactInfo()!=null) {
				userContactInfoDTO.setMobile(lxsUser.getContactInfo().getMobile());
			}
			
			userDTO.setContactInfo(userContactInfoDTO);
			UserStatusDTO userStatusDTO = new UserStatusDTO();
			if (lxsUser.getStatus()!=null) {
				userStatusDTO.setLastLoginTime(lxsUser.getStatus().getLastLoginTime());
			}
		
			userDTO.setStatus(userStatusDTO);
			//交易信息赋值
			UserBusinessInfoDTO userBusinessInfoDTO = new UserBusinessInfoDTO();
			LineOrderQO lineOrderQO = new LineOrderQO();
			lineOrderQO.setUserId(lxsUser.getId());
			userBusinessInfoDTO.setOrderTimes(lineOrderLocalService.queryCount(lineOrderQO).toString());
			List<LineOrder> lineOrders= lineOrderLocalService.queryList(lineOrderQO);
			double tradingVolume =0.0;
			java.math.BigDecimal d1=new java.math.BigDecimal(String.valueOf(tradingVolume));
			for(LineOrder lineOrder:lineOrders){
				if (lineOrder.getBaseInfo()!=null) {
					java.math.BigDecimal d2=new java.math.BigDecimal(String.valueOf(lineOrder.getBaseInfo().getSalePrice()));
					d1=d1.add(d2);
				}
				
			}
			userBusinessInfoDTO.setTradingVolume(d1.toString());
			userDTO.setBusinessInfo(userBusinessInfoDTO);
			//结束赋值
			userDTOs.add(userDTO);
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("userList", userDTOs);
		model.addAttribute("userQO", userQO);
		List<LineOrder> lineOrders = lineOrderLocalService.queryList(new LineOrderQO());
		
		/**
		 * 会员统计统能
		 */
		//1:计算当前有多少张有人出行的订单,根据订单数量计算总价
		LineOrderQO  lineOrderQO = new LineOrderQO();
		lineOrderQO.setOrderStatus(XLOrderStatusConstant.SLFX_RESERVE_SUCCESS);
		List<LineOrder> lineOrders2 = lineOrderLocalService.queryList(lineOrderQO);
		double totalPrice  = 0.0;
		for (LineOrder lineOrder : lineOrders2) {
			totalPrice+=lineOrder.getBaseInfo().getSalePrice();
		}
		//总交易金额
		model.addAttribute("totalPrice", totalPrice);
		//2:计算有多少个成功出游的人
		LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
		LineOrderStatusDTO lineOrderStatusDTO = new LineOrderStatusDTO();
		lineOrderStatusDTO.setOrderStatus(Integer.valueOf(XLOrderStatusConstant.SLFX_RESERVE_SUCCESS));
		lineOrderTravelerQO.setLineOrderStatusDTO(lineOrderStatusDTO);
		int sum_of_traveler = lineOrderTravelerService.queryCount(lineOrderTravelerQO);
		//客单价
		if(totalPrice==0||sum_of_traveler==0){
			model.addAttribute("orderAvgPrice", 0);
		}else{
			model.addAttribute("orderAvgPrice", Math.ceil((totalPrice/sum_of_traveler)*100)/100);
		}
		//注册总人数
		model.addAttribute("userNO", lxsUserService.queryCount(new LxsUserQO()));
		return "/member/personlist.html";
	}
	
	/**
	 * 
	 * @方法功能说明：重置密码
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日上午10:04:04
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/reset")
	@ResponseBody
	public String resetPassword(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id){
		try{
			ResetPasswordCommand command = new ResetPasswordCommand();
			command.setUserId(id);
			command.setNewPassword(Md5Util.MD5("123456"));
			lxsUserService.ResetPassword(command);
		}catch(Exception e){
			HgLogger.getInstance().info("lxs_dev", "【resetPassword】"+"异常，"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createSimpleJsonString("500", "密码重置失败");
		}
		return DwzJsonResultUtil.createSimpleJsonString("200", "密码重置成功");
	}
	
	
	/**
	 * 
	 * @方法功能说明：获取用户详情
	 * @修改者名字：cangs
	 * @修改时间：2015年5月20日上午11:38:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/detail")
	public String getPersonDetail(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id){
		LxsUser lxsUser = lxsUserService.get(id);
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setUserId(lxsUser.getId());
		List<LineOrder> lineOrders= lineOrderLocalService.queryList(lineOrderQO);
		double tradingVolume =0.0;
		java.math.BigDecimal d1=new java.math.BigDecimal(String.valueOf(tradingVolume));
		for(LineOrder lineOrder:lineOrders){
			java.math.BigDecimal d2=new java.math.BigDecimal(String.valueOf(lineOrder.getBaseInfo().getSalePrice()));
			d1=d1.add(d2);
		}
		if(lineOrders.size()!=0){
			model.addAttribute("firstOrderDate",lineOrders.get(lineOrders.size()-1).getBaseInfo().getCreateDate());
			model.addAttribute("salePrice",lineOrders.get(lineOrders.size()-1).getBaseInfo().getSalePrice());
		}else{
			model.addAttribute("firstOrderDate","-");
			model.addAttribute("salePrice",0.0);
		}
		model.addAttribute("tradingVolume",d1.toString());
		model.addAttribute("orderTimes", lineOrderLocalService.queryCount(lineOrderQO));
		model.addAttribute("lxsUser", lxsUser);
		return "/member/persondetail.html";
	}
	
	
	
	@RequestMapping(value="/getQueryValidPage")
	public String getQueryValidPage(HttpServletRequest request, Model model,@ModelAttribute RegisterSagaQO registerSagaQO){
		model.addAttribute("validCode", "");
		return "/member/queryValid.html";
	}
	
	@RequestMapping(value="/queryValid")
	public String queryValid(HttpServletRequest request, Model model,
			@ModelAttribute RegisterSagaQO registerSagaQO){
		List<RegisterSaga> registerSaga= registerSagaService.queryList(registerSagaQO);
		model.addAttribute("registerSagaQO", registerSagaQO);
		if(registerSaga!=null&&registerSaga.get(0)!=null){
			model.addAttribute("validCode", registerSaga.get(0).getValidCode());
		}else{
			model.addAttribute("validCode", "");
		}
		return "/member/queryValid.html";
	}
}
