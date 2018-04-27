package zzpl.h5.controller;

import hg.common.util.UUIDGenerator;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.CostCenterDTO;
import zzpl.api.client.dto.user.FrequentFlyerDTO;
import zzpl.api.client.dto.user.UserDTO;
import zzpl.api.client.request.user.AddFrequentFlyerCommand;
import zzpl.api.client.request.user.ChangePasswordCommand;
import zzpl.api.client.request.user.CostCenterQO;
import zzpl.api.client.request.user.DeleteFrequentFlyerCommand;
import zzpl.api.client.request.user.ModifyFrequentFlyerCommand;
import zzpl.api.client.request.user.ModifyUserCommand;
import zzpl.api.client.request.user.QueryFrequentFlyerQO;
import zzpl.api.client.response.user.AddFrequentFlyerResponse;
import zzpl.api.client.response.user.DeleteFrequentFlyerResponse;
import zzpl.api.client.response.user.LoginResponse;
import zzpl.api.client.response.user.ModifyFrequentFlyerResponse;
import zzpl.api.client.response.user.ModifyUserResponse;
import zzpl.api.client.response.user.QueryFrequentFlyerResponse;
import zzpl.api.client.response.user.QueryUserResponse;
import zzpl.h5.service.FlightService;
import zzpl.h5.service.UserService;
import zzpl.h5.util.base.BaseController;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FlightService flightService;

	@Autowired
	private LoginController loginController;
	@RequestMapping("/view")
	public String userView(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		AuthUser user = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if (user != null) {
			QueryUserResponse queryUserResponse = userService.getUserInfo(user
					.getId());
			UserDTO userDTO = queryUserResponse.getUserDTO();
			model.addAttribute("user", userDTO);
		}
		return "fly_person.html";
	}

	/**
	 * jinyy 跳转到个人资料页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/person_editInfo")
	public String person_editInfoView(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		AuthUser user = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if (user != null) {

			QueryUserResponse queryUserResponse = userService.getUserInfo(user
					.getId());
			UserDTO userDTO = queryUserResponse.getUserDTO();
			model.addAttribute("user", userDTO);
		}

		return "person_editInfo.html";
	}

	/**
	 * 修改个人资料
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param modifyUserCommand
	 * @param sex
	 * @return
	 */
	@RequestMapping("/editPerson")
	@ResponseBody
	public Map<Object, Object> editPerson(HttpServletRequest request,
			HttpServletResponse response, Model model,
			ModifyUserCommand modifyUserCommand,
			@RequestParam(value = "sex", required = false) String sex) {

		AuthUser user = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if ("男".equals(sex)) {
			modifyUserCommand.setGender(1);
		} else {
			modifyUserCommand.setGender(0);
		}
		modifyUserCommand.setUserID(user.getId());
		model.addAttribute("user", modifyUserCommand);
		ModifyUserResponse modifyUserResponse = userService
				.editPerson(modifyUserCommand);
		Map<Object, Object> maplist = new HashMap<Object, Object>();
		maplist.put("success", true);
		maplist.put("message", modifyUserResponse.getMessage());

		return maplist;

	}

	/**
	 * 查询所有联系人
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/fly_connect")
	public String fly_connect(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		AuthUser user = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		QueryFrequentFlyerQO queryFrequentFlyerQO = new QueryFrequentFlyerQO();
		queryFrequentFlyerQO.setUserID(user.getId());
		QueryFrequentFlyerResponse response2 = userService
				.getFrequentFlyers(queryFrequentFlyerQO);
		List<FrequentFlyerDTO> dtos = response2.getFrequentFlyerDTOs();
		
		
		QueryUserResponse userResponse = userService.getUserInfo(user.getId());
		UserDTO userDTO = userResponse.getUserDTO();
		
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		//查询成本中心
		CostCenterQO costCenterQO = new CostCenterQO();
		costCenterQO.setCompanyID(userDTO.getCompanyID());
		List<CostCenterDTO> costCenterDTOs = flightService.queryCostCenter(costCenterQO,sessionID).getCostCenterDTOs();
		model.addAttribute("costCenterDTOs", costCenterDTOs);
		model.addAttribute("costCenterDTO_0",costCenterDTOs.get(0));
	
		
		
		
		model.addAttribute("users", dtos);
		//当前登陆人
		model.addAttribute("userDTO", userDTO);
		return "fly_connect.html";

	}

	/**
	 * 跳转到修改联系人页面
	 */
	@RequestMapping("/editConnectUserView")
	public String editConnectUserView(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String myid) {
		return "fly_connect.html";

	}

	/**
	 * 添加联系人
	 * 
	 * @param frequentFlyerDTO
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addConnectUser")
	@ResponseBody
	public Map<Object, Object> addConnectUser(FrequentFlyerDTO frequentFlyerDTO,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		AuthUser user = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);

		AddFrequentFlyerCommand addFrequentFlyerCommand = new AddFrequentFlyerCommand();
		
		frequentFlyerDTO.setUserID(user.getId());
		frequentFlyerDTO.setId(UUIDGenerator.getUUID());
		/*String id_namestr=	frequentFlyerDTO.getCostCenterName();
		if (id_namestr!=null&&!"".equals(id_namestr)) {
			String [] id_name= id_namestr.split("_");
			frequentFlyerDTO.setCostCenterID(id_name[0]);
			frequentFlyerDTO.setCostCenterName(id_name[1]);
		}else {
			frequentFlyerDTO.setCostCenterID("");
			frequentFlyerDTO.setCostCenterName("");
		}*/
		
		addFrequentFlyerCommand.setFrequentFlyer(frequentFlyerDTO);
		Map<Object, Object> maplist = new HashMap<Object, Object>();
		AddFrequentFlyerResponse addresponse = userService.addConnectUser(addFrequentFlyerCommand);
		
		
		if (addresponse!=null&&addresponse.getResult()==ApiResponse.RESULT_CODE_OK) {
			maplist.put("passenger", addresponse.getFrequentFlyerDTO());
			maplist.put("success", true);
			maplist.put("message", "操作成功！");
		}else {
			maplist.put("success", false);
			maplist.put("message", addresponse.getMessage());
		}
		
		return maplist;

	}

	/**
	 * 修改联系人
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/editConnectUser")
	public Map<Object, Object>  editConnectUser(FrequentFlyerDTO frequentFlyerDTO,
			HttpServletRequest request, HttpServletResponse response,
			Model model, @RequestParam(value = "id") String id)
			throws IOException {
		AuthUser user = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		ModifyFrequentFlyerCommand modifyFrequentFlyerCommand = new ModifyFrequentFlyerCommand();
		

		frequentFlyerDTO.setUserID(user.getId());
		frequentFlyerDTO.setId(id);
		
		/*String id_namestr=	frequentFlyerDTO.getCostCenterName();
		if (id_namestr!=null&&!"".equals(id_namestr)) {
			String [] id_name= id_namestr.split("_");
			frequentFlyerDTO.setCostCenterID(id_name[0]);
			frequentFlyerDTO.setCostCenterName(id_name[1]);
		}else {
			frequentFlyerDTO.setCostCenterID("");
			frequentFlyerDTO.setCostCenterName("");
		}*/
		
		
		
		modifyFrequentFlyerCommand.setFrequentFlyer(frequentFlyerDTO);
		ModifyFrequentFlyerResponse modifyResponse =userService.editConnectUser(modifyFrequentFlyerCommand);
		
		Map<Object, Object> maplist = new HashMap<Object, Object>();
		if (modifyResponse!=null&&modifyResponse.getResult()==ApiResponse.RESULT_CODE_OK) {
			maplist.put("success", true);
			maplist.put("message", "操作成功！");
		}else {
			maplist.put("success", false);
			maplist.put("message", modifyResponse.getMessage());
		}
		return maplist;
	}

	/**
	 * 删除联系人
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/deleteConnectUser")
	@ResponseBody
	public Map<Object, Object> deleteConnectUser(
			@RequestParam(value = "id") String id, HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {
		DeleteFrequentFlyerCommand deleteFrequentFlyerCommand = new DeleteFrequentFlyerCommand();
		deleteFrequentFlyerCommand.setId(id);
		DeleteFrequentFlyerResponse delResponse = userService.delete(deleteFrequentFlyerCommand);
	
		Map<Object, Object> maplist = new HashMap<Object, Object>();
		if (delResponse!=null&&delResponse.getResult()==ApiResponse.RESULT_CODE_OK) {
			maplist.put("success", true);
			maplist.put("message", "操作成功！");
		}else {
			maplist.put("success", false);
			maplist.put("message", delResponse.getMessage());
		}
		return maplist;

	}

	/**
	 * 跳转到修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/person_changePwd")
	public String person_changePwd() {

		return "person_changePwd.html";

	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changePwd")
	public Map<Object, Object> changePwd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			ChangePasswordCommand changePasswordCommand) {
		AuthUser user = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		changePasswordCommand.setUserID(user.getId());
		LoginResponse loginResponse = userService.changePwd(changePasswordCommand);
		Map<Object, Object> maplist = new HashMap<Object, Object>();
		if(loginResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			maplist.put("changepwd", true);
		}else{
			maplist.put("changepwd", false);
		}
		// return "person_changePwd.html";
		maplist.put("success", true);
		maplist.put("message", loginResponse.getMessage());

		return maplist;
	}

	/**
	 * 跳转到意见反馈页面
	 */
	@RequestMapping("/person_setting")
	public String person_setting() {

		return "person_setting.html";
	}
}
