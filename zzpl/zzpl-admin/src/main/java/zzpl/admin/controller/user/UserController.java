package zzpl.admin.controller.user;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.user.CompanyService;
import zzpl.app.service.local.user.DepartmentService;
import zzpl.app.service.local.user.RoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.Department;
import zzpl.domain.model.user.Role;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.user.AddTravleUserCommand;
import zzpl.pojo.command.user.AddUserCommand;
import zzpl.pojo.command.user.ChangePasswordCommand;
import zzpl.pojo.command.user.DeleteUserCommand;
import zzpl.pojo.command.user.ModifyTravleUserCommand;
import zzpl.pojo.command.user.ModifyUserCommand;
import zzpl.pojo.dto.user.UserDTO;
import zzpl.pojo.qo.user.CompanyQO;
import zzpl.pojo.qo.user.DepartmentQO;
import zzpl.pojo.qo.user.RoleQO;
import zzpl.pojo.qo.user.UserQO;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	/**
	 * 角色service
	 */
	@Autowired
	private RoleService roleService;
	/**
	 * 公司service
	 */
	@Autowired
	private CompanyService companyService;
	/**
	 * 人员service
	 */
	@Autowired
	private UserService userService;
	/**
	 * 部门service
	 */
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private JedisPool jedisPool; 
	
	/**
	 * 
	 * @方法功能说明：跳转到差旅管理员管理界面
	 * @修改者名字：cangs
	 * @修改时间：2015年8月4日下午1:46:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param userQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/travleAdmin/view")
	public String travleAdminview(HttpServletRequest request, Model model,
			@ModelAttribute UserQO userQO) {
		return "/content/user/travle_admin_list.html";
	}
	
	/**
	 * 
	 * @方法功能说明：差旅管理员列表
	 * @修改者名字：cangs
	 * @修改时间：2015年8月4日下午1:46:57
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param userQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param userName
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/travleAdminList")
	public Pagination travleAdminList(HttpServletRequest request, Model model,
			UserQO userQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "userName", required = false) String userName) {
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		userQO.setName(userName);
		userQO.setRoleID(SysProperties.getInstance().get("travleAdminID"));
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(userQO);
		pagination = userService.queryPagination(pagination);
		pagination.setList(EntityConvertUtils.convertEntityToDtoList(
				(List<UserDTO>) pagination.getList(), UserDTO.class));
		return pagination;
	}
	
	/**
	 * @Title: view 
	 * @author guok
	 * @时间  2015年6月26日 08:51:49
	 * @Description: 跳转人员列表页
	 * @param request
	 * @param model
	 * @param userQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute UserQO userQO) {
		return "/content/user/user_list.html";
	}
	
	/**
	 * @Title: companyList 
	 * @author guok
	 * @Description: 人员列表展示
	 * @time 2015年6月26日 08:51:44
	 * @param request
	 * @param model
	 * @param userQO
	 * @param pageNo
	 * @param pageSize
	 * @param userName
	 * @return Pagination 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/userList")
	public Pagination userList(HttpServletRequest request, Model model,
			UserQO userQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "userName", required = false) String userName) {
		
		HgLogger.getInstance().error("cs",	"【userController】【userList】");
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		userQO.setName(userName);
		//获取当前登陆用户，以用于查找用户所属公司
		//当用户有部门ID时查询只查询部门人员2015年7月6日 14:54:07
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		userQO.setCompanyID(user.getCompanyID());
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(userQO);
		if(StringUtils.isNotBlank(user.getCompanyID())){
			pagination = userService.queryPagination(pagination);
			pagination.setList(EntityConvertUtils.convertEntityToDtoList(
					(List<UserDTO>) pagination.getList(), UserDTO.class));
		}
		
		model.addAttribute("userID", authUser.getId());
		return pagination;
	}
	
	/**
	 * 
	 * @Title: deleteuser 
	 * @author guok
	 * @Description: 删除人员
	 * @time 2015年6月26日 08:51:38
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String deleteuser(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DeleteUserCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【userController】【deleteuser】,command："+JSON.toJSONString(command));
			userService.deleteUser(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("userMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}
	
	/**
	 * @throws Exception 
	 * @Title: add 
	 * @author guok
	 * @Description: 跳转添加页
	 * @time 2015年6月26日 08:52:03
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception {
		//获取当前登陆用户，以用于查找用户所属公司，并且查到公司部门列表
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		if(StringUtils.isBlank(user.getCompanyID())){
			throw new Exception("当前用户无权限");
		}
		DepartmentQO departmentQo=new DepartmentQO();
		departmentQo.setCompanyID(user.getCompanyID());
		List<Department> departmentList=departmentService.queryList(departmentQo);
		//查询角色列表
		RoleQO roleQo=new RoleQO();
		roleQo.setCompanyID(user.getCompanyID());
		List<Role> roleList=roleService.queryList(roleQo);
		
		model.addAttribute("roleList", roleList);
		model.addAttribute("departmentList", departmentList);
		return "/content/user/user_add.html";
	}
	
	/**
	 * @Title: useradd 
	 * @author guok
	 * @Description: 添加人员
	 * @time 2015年6月26日 08:51:58
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 * @throws userException String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/useradd")
	public String userAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AddUserCommand command){
		try {
			Jedis jedis = jedisPool.getResource();
			String creatstatus = jedis.get("ZHIXING_USER"+command.getIdCardNO()+"CREAT");
			HgLogger.getInstance().info("cs",	"【userController】【userAdd】,command："+creatstatus);
			if(StringUtils.endsWith(creatstatus, "creating")){
				HgLogger.getInstance().info("cs",	"【userController】【userAdd】正在创建");
				throw new Exception("正在创建，请耐心等候");
			}
			//获取当前登陆用户，以用于查找用户所属公司
			AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			User user2 = userService.get(user.getId());
			command.setCompanyID(user2.getCompanyID());
			HgLogger.getInstance().error("cs",	"【userController】【userAdd】,command："+JSON.toJSONString(command));
			jedis.setex("ZHIXING_USER"+command.getIdCardNO()+"CREAT",2592000 , "creating");
			userService.addUser(command);
			jedis.setex("ZHIXING_USER"+command.getIdCardNO()+"CREAT",2592000 , "created");
			jedis.del("ZHIXING_USER"+command.getIdCardNO()+"CREAT");
			jedisPool.returnResource(jedis);
			return DwzJsonResultUtil.createJsonString("200", "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("userMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
	}
	
	/**
	 * @Title: edit 
	 * @author guok
	 * @Description: 跳转到人员编辑页
	 * @time 2015年6月26日 08:52:10
	 * @param request
	 * @param response
	 * @param model
	 * @param userID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "userID") String userID) {
		User user=userService.getById(userID);
		model.addAttribute("user", user);
		
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user1=userService.get(authUser.getId());
		DepartmentQO departmentQo=new DepartmentQO();
		departmentQo.setCompanyID(user1.getCompanyID());
		List<Department> departmentList=departmentService.queryList(departmentQo);
		//查询角色列表
		RoleQO roleQo=new RoleQO();
		roleQo.setCompanyID(user.getCompanyID());
		List<Role> roleList=roleService.queryList(roleQo);
		
		model.addAttribute("roleList", roleList);
		model.addAttribute("departmentList", departmentList);
		
		return "/content/user/user_edit.html";
	}
	
	/**
	 * @Title: userEdit 
	 * @author guok
	 * @Description: 编辑人员
	 * @Time 2015年6月26日 08:52:15
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/useredit")
	public String userEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyUserCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【userController】【userEdit】,command："+JSON.toJSONString(command));
			if(command.getBuyOthers()==null){
				String[] str = {"0"};
				command.setBuyOthers(str);
			}
			userService.modfiyUser(command);
			return DwzJsonResultUtil.createJsonString("200", "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,SysProperties.getInstance().get("userMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
		
	}
	
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 人员详情页
	 * @time 2015年6月29日 15:05:04
	 * @param request
	 * @param response
	 * @param model
	 * @param userID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/detail")
	public String detail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "userID") String userID) {
		User user=userService.getById(userID);
		model.addAttribute("user", user);
		return "/content/user/user_detail.html";
	}
	
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 人员详情页
	 * @time 2015年6月29日 15:05:04
	 * @param request
	 * @param response
	 * @param model
	 * @param userID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/travleDetail")
	public String travleDetail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "userID") String userID) {
		User user=userService.getById(userID);
		model.addAttribute("user", user);
		return "/content/user/travle_admin_detail.html";
	}
	
	/**
	 * @throws Exception 
	 * @Title: add 
	 * @author guok
	 * @Description: 跳转差旅管理员添加页
	 * @time 2015年8月4日 16:11:09
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/creat")
	public String creat(HttpServletRequest request, Model model) throws Exception {
		List<Company> companies = companyService.queryList(new CompanyQO());
		model.addAttribute("companies", companies);
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		if(StringUtils.isNotBlank(user.getCompanyID())){
			throw new Exception("当前用户无权限");
		}
		return "/content/user/travle_admin_add.html";
	}
	
	/**
	 * @Title: travleUserAdd 
	 * @author guok
	 * @Description: 添加差旅管理员
	 * @Time 2015年8月4日下午4:11:58
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/travleUserAdd")
	public String travleUserAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AddTravleUserCommand command){
		try {
			HgLogger.getInstance().error("gk",	"【userController】【travleUserAdd】,command："+JSON.toJSONString(command));
			userService.travleAdd(command);
			return DwzJsonResultUtil.createJsonString("200", "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("travleAdminMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
	}
	
	/**
	 * @Title: edit 
	 * @author guok
	 * @Description: 跳转到人员编辑页
	 * @time 2015年6月26日 08:52:10
	 * @param request
	 * @param response
	 * @param model
	 * @param userID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/travleEdit")
	public String travleEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "userID") String userID) {
		User user=userService.getById(userID);
		model.addAttribute("user", user);
		//切割公司ID
		String[] companyIDs = null;
		if (StringUtils.isNotBlank(user.getCompanyID())) {
			companyIDs = user.getCompanyID().split(",");
			model.addAttribute("companyIDs", companyIDs);
		}
		
		model.addAttribute("companyIDs", companyIDs);
		
		List<Company> companies = companyService.queryList(new CompanyQO());
		model.addAttribute("companies", companies);
		
		return "/content/user/travle_admin_edit.html";
	}
	
	/**
	 * @Title: userEdit 
	 * @author guok
	 * @Description: 编辑人员
	 * @Time 2015年6月26日 08:52:15
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/travleModify")
	public String travleModify(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyTravleUserCommand command) {
		try {
			HgLogger.getInstance().error("gk",	"【userController】【travleModify】,command："+JSON.toJSONString(command));
			userService.modfiyTravle(command);
			return DwzJsonResultUtil.createJsonString("200", "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,SysProperties.getInstance().get("travleAdminMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
	}
	
	/**
	 * @Title: deleteTravle 
	 * @author guok
	 * @Description: 删除差旅员
	 * @Time 2015年8月18日下午2:14:06
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delTravle")
	public String deleteTravle(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DeleteUserCommand command) {
		try {
			HgLogger.getInstance().error("cs",	"【userController】【deleteTravle】,command："+JSON.toJSONString(command));
			userService.deleteUser(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("travleAdminMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(), null, "");
		}
	}
	
	
	/**
	 * @Title: uploadMembers 
	 * @author guok
	 * @Description:导入人员
	 * @Time 2015年8月18日下午1:30:32
	 * @param request
	 * @param response
	 * @param model
	 * @param file
	 * @return
	 * @throws Exception String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/asdaddList")
	public String uploadMembers(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		
		HgLogger.getInstance().info("cs", "【userController】【uploadMembers】"+"导入开始");
		
		Pattern namep = Pattern.compile("[\u4E00-\u9FA5]{1,10}");
		Pattern telp = Pattern.compile("(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
		Pattern idp = Pattern.compile("[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])(\\d{4}$|\\d{3}[Xx]{1})");
		Pattern sexp = Pattern.compile("[01]");
		Pattern emailp = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Pattern datep = Pattern.compile("(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)");
		
		int i = 1;
		int j = 0;
		//获取文件输入流
		try {
			InputStream  is = file.getInputStream();
			Workbook wb = Workbook.getWorkbook(is);
			//开始校验excel
			Sheet se = wb.getSheet(0);
			List<AddUserCommand> commands = new ArrayList<AddUserCommand>();
			AddUserCommand command ;
			AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			User user2 = userService.get(user.getId());
			String name;
			String tel;
			String userNO;
			String loginName;
			String gender;
			String birthday;
			String linkEmail;
			String idCardNO;
			String provinceID;
			String cityID;
			
			while(i<(se.getRows()-1)&&!StringUtils.isBlank(se.getCell(0,i).getContents())){
				//取值
				command = new AddUserCommand();
				command.setCompanyID(user2.getCompanyID());
				//1.姓名
				name = se.getCell(0,i).getContents().trim();
				Matcher nm =  namep.matcher(name);
				if(nm.matches()){
					command.setName(name);
				}else {
					j=0;
					throw new Exception();
				}
				
				//2.人员编号
				userNO = se.getCell(1,i).getContents().trim();
				command.setUserNO(userNO);
				//3.登录名
				loginName = se.getCell(2,i).getContents().trim();
				command.setLoginName(loginName);
				//4.手机号
				tel = se.getCell(3,i).getContents().trim();
				Matcher mobile =  telp.matcher(tel);
				if(mobile.matches()){
				command.setLinkMobile(tel);
				}else {
					j=3;
					throw new Exception();
				}
				//5.性别（1.男，0.女）
				gender = se.getCell(4,i).getContents().trim();
				Matcher sex =  sexp.matcher(gender);
				if(sex.matches()){
				command.setGender(new Integer(gender));
				}else {
					j=4;
					throw new Exception();
				}
				//6.身份证号
				idCardNO = se.getCell(5,i).getContents().trim();
				if (StringUtils.isNotBlank(idCardNO)) {
					Matcher id =  idp.matcher(idCardNO);
					if(id.matches()){
					command.setIdCardNO(idCardNO);
					}else {
						j=5;
						throw new Exception();
					}
				}
				
				//7.邮箱
				linkEmail = se.getCell(6,i).getContents().trim();
				if (StringUtils.isNotBlank(linkEmail)) {
					Matcher email =  emailp.matcher(linkEmail);
					if(email.matches()){
						command.setLinkEmail(linkEmail);
					}else {
						j=6;
						throw new Exception();
					}
				}
				birthday = se.getCell(7,i).getContents().trim();
				//8.生日
				if (StringUtils.isNotBlank(se.getCell(7,i).getContents())) {
					Matcher date =  datep.matcher(birthday);
					if(date.matches()){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					command.setBirthday(sdf.parse(birthday));
					}else {
						j=7;
						throw new Exception();
					}
				}
				
				//9.省份
				provinceID = se.getCell(8,i).getContents().trim();
				command.setProvinceID(provinceID);
				//10.城市
				cityID = se.getCell(9,i).getContents().trim();
				command.setCityID(cityID);
				commands.add(command);
				i++;
			}
			userService.addUsers(commands);
		} catch (Exception e) {
			HgLogger.getInstance().error("cs",	"【userController】【uploadMembers】："+(i+1)+"行"+(j+1)+"列导入错误");
			HgLogger.getInstance().error("cs",	"【userController】【uploadMembers】："+e.getMessage());
			return DwzJsonResultUtil.createJsonString("300", (i+1)+"行"+(j+1)+"列文本格式错误", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "importusers");
		}
		return DwzJsonResultUtil.createJsonString("200", "添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "importusers");
	}
	
	/**
	 * @throws Exception 
	 * @Title: execle 
	 * @author guok
	 * @Description: 跳转到导入人员页面
	 * @Time 2015年8月18日下午1:29:55
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/execel")
	public String execle(HttpServletRequest request, Model model) throws Exception {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		if(StringUtils.isBlank(user.getCompanyID())){
			throw new Exception("当前用户无权限");
		}
		return "/content/user/import_user_add.html";
	}
	
	
	/**
	 * @Title: editPasswd 
	 * @author guok
	 * @Description: 跳转修改密码页
	 * @Time 2015年8月18日下午2:48:52
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/toEditPasswd")
	public String toEditPasswd(HttpServletRequest request, Model model) {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		return "/content/user/user_editPass.html";
	}
	
	/**
	 * @Title: editPasswd 
	 * @author guok
	 * @Description: 修改密码
	 * @Time 2015年8月4日下午4:11:58
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/editPasswd")
	public String editPasswd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ChangePasswordCommand command){
		try {
			HgLogger.getInstance().info("gk",	"【userController】【editPasswd】,command："+JSON.toJSONString(command));
			userService.editPassword(command);
			return DwzJsonResultUtil.createJsonString("200", "密码修改成功，请用新密码重新登录", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "home");
		} catch (Exception e) {
			HgLogger.getInstance().error("gk",	"【userController】【editPasswd】,Exception："+e.getMessage());
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
	}
	
}