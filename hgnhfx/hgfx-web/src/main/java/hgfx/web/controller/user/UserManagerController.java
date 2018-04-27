package hgfx.web.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.distributoruser.ModifyDistributorUserCommand;
import hg.fx.command.distributoruser.RemoveDistributorUserCommand;
import hg.fx.domain.DistributorUser;
import hg.fx.spi.DistributorRegisterSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.util.CharacterUtil;
import hgfx.web.controller.sys.BaseController;

@Controller
@RequestMapping("/userManager")
public class UserManagerController extends BaseController {
	/** pageSize*/
	private final static String PAGE_SIZE = "10";//默认分页数量
	@Autowired
	private DistributorUserSPI distributorUserService;
	@Autowired
	private DistributorRegisterSPI distributorRegisterService;
	/**
	 * 用户管理
	 * @author Caihuan
	 * @date   2016年6月6日
	 */
	@RequestMapping("/staffList")
	public String staffList(HttpSession httpSession,Model model,
			Integer pageNum){
		
		DistributorUser distributorUser = getSessionUserInfo(httpSession);
		//子帐号
		if(!distributorUser.getStatus().equals(DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN))
		{
			model.addAttribute("message", "子帐号没有该权限");
			return "/error/error.html";
		}
		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setDistributorId(distributorUser.getDistributor().getId());
		sqo.setType(DistributorUser.DSTRIBUTOR_USER_TYPE_SUB);
		sqo.setUserRemoved(false);
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(pageNum);
		sqo.getLimit().setPageSize(Integer.valueOf(PAGE_SIZE));
		Pagination<DistributorUser> pagination = distributorUserService.queryPagination(sqo);
		int totalcount = pagination.getTotalCount();
		double totalPageNum = 1;
		if(totalcount>Integer.valueOf(PAGE_SIZE)){
			totalPageNum=Math.ceil(Double.parseDouble(Integer.toString(totalcount))/Double.parseDouble(PAGE_SIZE));
		}
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("pageNum", pageNum==null?1:pageNum);
		model.addAttribute("pagination", pagination);
		return "/user/staffList.html";
	}
	
	/**
	 * 添加操作员页面
	 * @author Caihuan
	 * @date   2016年6月6日
	 */
	@RequestMapping("/toAddStaff")
	public String toAddStaff()
	{
		
		return "";
	}
	
	/**
	 * 保存操作员
	 * @author Caihuan
	 * @date   2016年6月6日
	 */
	@ResponseBody
	@RequestMapping("/saveStaff")
	public String saveStaff(String loginName,String name,HttpSession httpSession)
	{
		DistributorUser admin = getSessionUserInfo(httpSession);
		if(!admin.getStatus().equals(DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN))
		{
			return "子帐号没有权限！";
		}
		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setAccount(loginName);
		sqo.setEqAccount(true);
		sqo.setUserRemoved(false);
	//	DistributorUser user = distributorUserService.queryUnique(sqo);
		boolean isExist = distributorRegisterService.checkExistAccount(sqo.getAccount());
		if(isExist)
		{
			return "1"; //账户已存在或者存在待审核审核账户
		}
		if(!CharacterUtil.checkDistributorAccount(loginName))
		{
			return "2"; //用户名格式错误
		}
		if(!CharacterUtil.checkCharacterOrHan(name))
		{
			return "3"; //姓名格式错误
		}
		
		CreateDistributorUserCommand command = new CreateDistributorUserCommand();
		command.setAccount(loginName);
		command.setPassword(CharacterUtil.getDistributorPassword(admin.getDistributor().getName()));
		command.setDistributorId(admin.getDistributor().getId());
		command.setName(name);
		command.setType(DistributorUser.DSTRIBUTOR_USER_TYPE_SUB);
		distributorUserService.create(command);
		return "success";
	}
	
	/**
	 * 
	 * @author Caihuan
	 * @date   2016年6月6日
	 */
	@ResponseBody
	@RequestMapping("/deleteStaff")
	public String deleteStaff(String staffId,HttpSession httpSession)
	{
		DistributorUser admin = getSessionUserInfo(httpSession);
		if(!admin.getStatus().equals(DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN))
		{
			return "子帐号没有权限！";
		}
		String result = "删除失败";
		try
		{
		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setId(staffId);
		sqo.setDistributorId(admin.getDistributor().getId());
		sqo.setUserRemoved(false);
		DistributorUser user = distributorUserService.queryUnique(sqo);
		if(user==null)
		{
			return "账户不存在";
		}
		RemoveDistributorUserCommand command = new RemoveDistributorUserCommand();
		command.setId(staffId);
		distributorUserService.delete(command);
		result = "success";
		return result;
		}catch(Exception e)
		{
		}
		return result;
	}
	
	/**
	 * 重置密码
	 * @author Caihuan
	 * @date   2016年6月6日
	 */
	@ResponseBody
	@RequestMapping("/resetStaffPwd")
	public String resetStaffPwd(String staffId,HttpSession httpSession)
	{
		DistributorUser admin = getSessionUserInfo(httpSession);
		if(!admin.getStatus().equals(DistributorUser.DSTRIBUTOR_USER_TYPE_MAIN))
		{
			return "子帐号没有权限！";
		}
		String result = "error";
		try
		{
		ModifyDistributorUserCommand command = new ModifyDistributorUserCommand();
		command.setId(staffId);
		command.setPassword(CharacterUtil.getDistributorPassword(admin.getDistributor().getName()));
		distributorUserService.modify(command);
		result = "success";
		return result;
		}catch(Exception e)
		{
		}
		return result;
	}

}
