package hsl.admin.controller.member;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.Md5Util;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.CreateMemberCommand;
import hsl.pojo.command.UserUpdatePasswordCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.user.BatchRegisterUserCommand;
import hsl.pojo.dto.coupon.CouponActivityDTO;
import hsl.pojo.dto.user.UserAuthInfoDTO;
import hsl.pojo.dto.user.UserBaseInfoDTO;
import hsl.pojo.dto.user.UserContactInfoDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.CouponException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.pojo.util.LogFormat;
import hsl.spi.inter.Coupon.CouponActivityService;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.user.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping(value="/member")
public class MemberController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private CouponActivityService couponActivityService;
	@Autowired
	private CouponService couponService;
	/**
	 * @方法功能说明：查询企业用户
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月21日上午10:21:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param hslUserQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/list")
	public String queryUserList(HttpServletRequest request, Model model,
			@ModelAttribute HslUserQO hslUserQO,
			@RequestParam(value="pageNo",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){

		//添加分页查询条件
		Pagination pagination=null;
		hslUserQO.setType(Integer.parseInt(UserDTO.USER_TYPE_OF_COMPANY));
		//查询用户列表
	    pagination = queryUserDTOList(request,hslUserQO,pageNo,pageSize);
	    List<UserDTO> userCompanyList=BeanMapperUtils.getMapper().mapAsList(pagination.getList(), UserDTO.class);
	    
		pagination.setList(userCompanyList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("userQO", hslUserQO);
		
		return "/member/member_list.html";
	}
	/**
	 * @方法功能说明：重置密码
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月21日上午10:21:16
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
		UserUpdatePasswordCommand command=new UserUpdatePasswordCommand();
		command.setUserId(id);
		command.setNewPwd("123456");
		try {
			userService.resetPassword(command);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "MemberController->resetPassword->exception:" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createSimpleJsonString("500", "密码重置失败");
		}
		
		
		return DwzJsonResultUtil.createSimpleJsonString("200", "密码重置成功");
	}
	
	
	/**
	 * 个人用户列表查询
	 * @param request
	 * @param model
	 * @param hslUserQO
	 * @param pageNo
	 * @param pageSize
	 * */
	@RequestMapping(value="/personList")
	public String queryPersonList(HttpServletRequest request, Model model,
			@ModelAttribute HslUserQO hslUserQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		
		//添加分页查询条件
		Pagination pagination=null;
		//查询用户列表
		hslUserQO.setType(Integer.parseInt(UserDTO.USER_TYPE_OF_PERSON));
		pagination = queryUserDTOList(request,hslUserQO,pageNo,pageSize);
		List<UserDTO> userPersonList=BeanMapperUtils.getMapper().mapAsList(pagination.getList(), UserDTO.class);

		pagination.setList(userPersonList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("userQO", hslUserQO);
		
		return "/member/person_list.html";
	}
	
	/**
	 * 查询用户列表信息
	 * @param request
	 * @param hslUserQO
	 * @param pageNo
	 * @param pageSize
	 * @return List<UserDTO>
	 * */
	public  Pagination   queryUserDTOList(HttpServletRequest request,
			@ModelAttribute HslUserQO hslUserQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		
		//添加分页查询条件
				Pagination pagination=new Pagination();
				//判断页面是否传查询参数
				if(hslUserQO!=null){
					hslUserQO.setLoginNameLike(true);
					//判断注册时间查询条件是否被选择
					if(StringUtils.isNotBlank(hslUserQO.getBeginTime()) && StringUtils.isBlank(hslUserQO.getEndTime())){
						hslUserQO.setEndTime(hslUserQO.getBeginTime());
						hslUserQO.setBeginTime("2000-01-01");
					}
				}
				
				pagination.setCondition(hslUserQO);
				pageNo=pageNo==null?new Integer(1):pageNo;
				pageSize=pageSize==null?new Integer(20):pageSize;
				pagination.setPageNo(pageNo);
				pagination.setPageSize(pageSize);
				
				pagination=userService.queryPagination(pagination);
			
		return  pagination;
	}
	/**
	 * @方法功能说明：跳转导入用户
	 * @修改者名字：chenxy
	 * @修改时间：2015年2月12日上午10:43:03
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/user/batchAddUser")
	public String toUploadUsers(){
		return "/member/upload_member.html";
	}
	
	/**
	 * @方法功能说明：跳转发送卡劵
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月13日上午9:13:54
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/user/sendCouponView")
	public String sendCouponView(){
		return "/member/sendCoupon.html";
	}
	
	/**
	 * 导入成员列表
	 * @throws IOException 
	 * 
	 */
	@ResponseBody
	@RequestMapping("/user/upload")
	public String uploadUsers(@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request) throws Exception{
		Map<String,Object> model = new HashMap<String, Object>(3);
		HgLogger.getInstance().debug("zhuxy", newHeader("批量导入个人用户开始"));
		//添加校验的正则表达式
		Pattern namep = Pattern.compile("^(\\w){4,20}$");
		Pattern emailp = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
		Pattern telp = Pattern.compile("^0?(13|15|17|18)[0-9]{9}$");
		int i = 1;
		int j = 0;
		InputStream  is=null;
		//获取文件输入流
		try {
		  is = file.getInputStream();
			Workbook wb = Workbook.getWorkbook(is);
			//开始校验excel
			Sheet se = wb.getSheet(0);
			List<CreateMemberCommand> cmds = new ArrayList<CreateMemberCommand>();
			BatchRegisterUserCommand command = new BatchRegisterUserCommand();
			List<UserDTO> users = new ArrayList<UserDTO>();
			command.setUsers(users);
			UserDTO user ;
			String name;
			String email;
			String tel;
			List<String> names = new ArrayList<String>();
			List<String> emails = new ArrayList<String>();
			List<String> tels = new ArrayList<String>();
			while(i<se.getRows()){
				if(StringUtils.isBlank(se.getCell(0,i).getContents())){
					model.put("detial","用户名为空");
					throw new Exception();
				}
				user = new UserDTO();
				//设置主键
				user.setId(UUIDGenerator.getUUID());
				//设置类型为个人用户
				UserBaseInfoDTO baseInfoDTO = new UserBaseInfoDTO();
				baseInfoDTO.setType(1);
				user.setBaseInfo(baseInfoDTO);
				
				
				//设置用户名和密码
				name = se.getCell(0,i).getContents();
				Matcher nm =  namep.matcher(name);
				if(nm.matches()){
					HslUserQO qo = new HslUserQO();
					qo.setLoginName(name);
					qo.setLoginNameLike(false);
					int num = userService.queryCount(qo);
					if(num>0){
						model.put("detial","用户名重复");
						throw new Exception();
					}
					if(names.contains(name)){
						model.put("detial","用户名前面的重复");
						throw new Exception();
					}else{
						names.add(name);
					}
					UserAuthInfoDTO authInfoDTO = new UserAuthInfoDTO();
					authInfoDTO.setLoginName(name);
					authInfoDTO.setPassword(Md5Util.MD5("123456"));
					user.setAuthInfo(authInfoDTO);
				}else{
					j=0;
					model.put("detial","用户名格式错误");
					throw new Exception();
				}

				//设置邮箱和手机
				UserContactInfoDTO contactInfoDTO = new UserContactInfoDTO();
				email = se.getCell(1,i).getContents();
				Matcher jm =  emailp.matcher(email);
				if(jm.matches()){
					HslUserQO qo = new HslUserQO();
					qo.setEmail(email);
					int num = userService.queryCount(qo);
					if(num>0){
						model.put("detial","邮箱重复");
						throw new Exception();
					}
					if(emails.contains(email)){
						model.put("detial","邮箱与前面的重复");
						throw new Exception();
					}else{
						emails.add(email);
					}
					contactInfoDTO.setEmail(email);
				}else{
					j=1;
					model.put("detial","邮箱格式错误");
					throw new Exception();
				}
				tel = se.getCell(2,i).getContents();
				Matcher tm =  telp.matcher(tel);
				if(tm.matches()){
					HslUserQO qo = new HslUserQO();
					qo.setMobile(tel);
					int num = userService.queryCount(qo);
					if(num>0){
						model.put("detial","电话重复");
						throw new Exception();
					}
					if(tels.contains(tel)){
						model.put("detial","电话与前面的重复");
						throw new Exception();
					}else{
						tels.add(tel);
					}
					contactInfoDTO.setMobile(tel);
				}else{
					j=2;
					model.put("detial","手机号格式错误");
					throw new Exception();
				}
				user.setContactInfo(contactInfoDTO);
				
				users.add(user);
				i++;
			}
			
			is.close();
			HgLogger.getInstance().info("zhuxy",LogFormat.log("成员列表的数据信息", JSON.toJSONString(cmds)));
			//保存数据
			userService.batchRegister(command);
			model.put("msg", "success");
		} catch (Exception e1) {
			e1.printStackTrace();
			model.put("msg", "dddd");
			model.put("row", i+1);
			model.put("col", j+1);
			HgLogger.getInstance().error("zhuxy", LogFormat.log("错误信息", HgLogger.getStackTrace(e1)));
			String json=JSON.toJSONString(model);
			System.out.println(json);
			return json;
		}finally{
			if(is!=null){
				is.close();
			}
			
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("批量导入个人用户结束"));
		String json=JSON.toJSONString(model);
		System.out.println(json);
		return json;
	}
	/**
	 * @方法功能说明：批量给用户发送卡券
	 * @修改者名字：chenxy
	 * @修改时间：2015年2月13日上午9:06:06
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/sendCoupon")
	public String sendCoupon(HttpServletRequest request) {
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "发送成功";
		String callbackType = null;
		
		String names = request.getParameter("names");
		String couponId = request.getParameter("couponId");
		String[] nameStrs = names.split(",");
		List<String> noSend=new ArrayList<String>();
		for (String name : nameStrs) {
			// 根据用户登录名查询用户
			HslUserQO hslUserQO = new HslUserQO();
			hslUserQO.setLoginName(name.trim());
			hslUserQO.setLoginNameLike(false);
			UserDTO userDTO = userService.queryUnique(hslUserQO);
			if(null==userDTO){
				noSend.add(name);
				continue;
			}
			//创建卡券command
			CouponMessage baseAmqpMessage = new CouponMessage();
			baseAmqpMessage.paddingUserRegisterContent(userDTO);
			CreateCouponCommand cmd = baseAmqpMessage.getContent();
			// 根据卡券活动ID查询
			HslCouponActivityQO qo = new HslCouponActivityQO();
			qo.setId(couponId);
			List<CouponActivityDTO> activityDTOs = couponActivityService.queryList(qo);
			if (activityDTOs != null && activityDTOs.size() > 0) {
				CouponActivityDTO activityDTO = activityDTOs.get(0);
				// 判断卡券活动的优先级
				cmd.setCouponActivityId(activityDTO.getId());
				try {
					HgLogger.getInstance().info("chenxy","批量发送卡券>>发放卡券：" + JSON.toJSONString(cmd, true));
					couponService.createCoupon(cmd);
				} catch (CouponException e) {
					e.printStackTrace();
					noSend.add(name);
					HgLogger.getInstance().error("chenxy","批量发送卡券>>活动id为：" + activityDTO.getId()+ "的卡券,用户为："+name+"发放失败");
				}
			}else{
				//卡劵不存在
				message = "卡劵活动id不存在";
				break;
			}
		}
		if(noSend.size() > 0){
			message = "用户";
			for(String s : noSend){
				message = message + s + "，";
			}
			message = message + "发送失败";
		}
		//如果noSend有值，返回没发送成功的登录名
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "");
		// CouponMessage baseAmqpMessage=new CouponMessage();
		// baseAmqpMessage.paddingUserRegisterContent(userDTO);
		// String issue=SysProperties.getInstance().get("issue_on_register");
		// int type=0;
		// if(StringUtils.isBlank(issue))
		// type=1;
		// else{
		// type=Integer.parseInt(issue);
		// }
		// baseAmqpMessage.setType(type);//注册发放
		// baseAmqpMessage.setSendDate(new Date());
		// baseAmqpMessage.setArgs(null);
		// template.convertAndSend("hsl.regist",baseAmqpMessage);
	}
	
}
