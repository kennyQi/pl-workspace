package hg.demo.web.controller;

import com.alibaba.fastjson.JSON;
import hg.demo.member.common.MD5HashUtil;
import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.Staff;
import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.demo.member.common.domain.viewmodel.StaffViewModel;
import hg.demo.member.common.spi.SecuritySPI;
import hg.demo.member.common.spi.StaffSPI;
import hg.demo.member.common.spi.command.authuser.*;
import hg.demo.member.common.spi.command.staff.CreateStaffCommand;
import hg.demo.member.common.spi.command.staff.DeleteStaffCommand;
import hg.demo.member.common.spi.command.staff.ModifyStaffCommand;
import hg.demo.member.common.spi.qo.AuthRoleSQO;
import hg.demo.member.common.spi.qo.AuthUserSQO;
import hg.demo.member.common.spi.qo.Security.CheckLoginNameSQO;
import hg.demo.member.common.spi.qo.Security.FindUserRolesSQO;
import hg.demo.member.common.spi.qo.Security.QueryAuthUserByIdSQO;
import hg.demo.member.common.spi.qo.Security.QueryAuthUserSQO;
import hg.demo.member.common.spi.qo.StaffSQO;
import hg.demo.web.common.UserInfo;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

import logUtil.LogConstants;
import logUtil.logUtil;

/**
 * Created by admin on 2016/5/23.
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {
    public final static String DEFAULT_PASSWORD = "123456";
    @Resource
    private StaffSPI staffService;
    @Resource
    private SecuritySPI securityService;

    /**
     * 操作员列表
     * @param request
     * @param response
     * @param model
     * @param staffSQO
     * @return
     */
    @RequestMapping(value = "/list")
    public String List(HttpSession httpSession,HttpServletRequest request, HttpServletResponse response,
                       Model model, @ModelAttribute StaffSQO staffSQO,@RequestParam(value = "pageNum",defaultValue = "1")
                           String currpage,@RequestParam(value = "numPerPage" ,defaultValue = "20") String pagesize ){

		staffSQO.setLimit(new LimitQuery());
		staffSQO.getLimit().setPageNo(Integer.parseInt(currpage));
		staffSQO.getLimit().setPageSize(Integer.parseInt(pagesize));

        Pagination<StaffViewModel> pagination = new Pagination<StaffViewModel>();
        staffSQO.setQueryAuthRole(true);
        pagination= staffService.queryStaffPagination(staffSQO);

        model.addAttribute("pagination", pagination);
        model.addAttribute("staffSqo", staffSQO);
        model.addAttribute("userEnableList", SecurityConstants.USER_ENABLE_LIST);
        model.addAttribute("userinfo",getUserInfo(httpSession));
        model.addAttribute("userRoleList", securityService.findAllRoles(new AuthRoleSQO()));

        return "/staff/list.ftl";
    }

    /**
     * 新增编辑页面
     * @param request
     * @param response
     * @param model
     * @param staffid
     * @return
     */
    @RequestMapping(value = "/addoredit")
    public String addoredit(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "id",defaultValue = "") String staffid){

        model.addAttribute("userEnableList", SecurityConstants.USER_ENABLE_LIST);
        List<AuthRole> rolelist=new ArrayList<AuthRole>();
        List<AuthRole> authRoles=securityService.findAllRoles(new AuthRoleSQO());
        for (AuthRole role:authRoles) {
            if (!role.getRoleName().equals("admin")){
                rolelist.add(role);
            }
        }
        model.addAttribute("authRoleList", rolelist);
        if (StringUtils.isEmpty(staffid)){
            return "/staff/add.ftl";
        }

        Staff staff=new Staff();
        StaffSQO staffSQO=new StaffSQO();
        staffSQO.setId(staffid);
        staff= staffService.queryStaff(staffSQO);
        FindUserRolesSQO findUserRolesSQO=new FindUserRolesSQO();
        findUserRolesSQO.setId(staffid);
        List<String> hasRoleList = securityService.findUserRoles(findUserRolesSQO);
        model.addAttribute("staff",staff);
        model.addAttribute("hasRoleName", hasRoleList);

        return "/staff/edit.ftl";
    }

    /**
     * 修改操作员密码
     * @param request
     * @param response
     * @param model
     * @param staffid
     * @return
     */
    @RequestMapping(value = "/modifypwd")
    public String modifypwd(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "id",defaultValue = "") String staffid){
        model.addAttribute("staffid",staffid);
        return "/staff/modifypwd.ftl";
    }

    @ResponseBody
    @RequestMapping(value = "/modifypwdpost")
    public Map<String,Object> modifypwdpost(@RequestParam(value = "id",required = false) String id, @RequestParam(value = "loginPwd", required = false) String loginPwd){
        Map<String, Object> map=new HashMap<String, Object>();
        if (StringUtils.isEmpty(id)){
            map.put("statusCode", 300);
            map.put("message", "参数有误");
            return map;
        }
        QueryAuthUserByIdSQO queryAuthUserByIdSQO=new QueryAuthUserByIdSQO();
        queryAuthUserByIdSQO.setId(id);
        AuthUser authUser=securityService.queryAuthUserById(queryAuthUserByIdSQO);
        if (authUser==null){
            map.put("statusCode", 300);
            map.put("message", "帐号有误");
            return map;
        }
        authUser.setPasswd(MD5HashUtil.toMD5(loginPwd));
        ChangePwdCommand changePwdCommand=new ChangePwdCommand();
        changePwdCommand.setId(authUser.getId());
        changePwdCommand.setPassword(authUser.getPasswd());
        securityService.changepwd(changePwdCommand);
        map.put("statusCode", 200);
        map.put("callbackType","closeCurrent");
        map.put("message", "修改密码成功");
        return map;
    }

    /**
     * 新增编辑提交
     * @param id
     * @param realName
     * @param roleIds
     * @param loginName
     * @param mobile
     * @param loginPwd
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save")
    public Map<String, Object> save(HttpSession httpSession,@RequestParam(value = "id",required = false) String id, @RequestParam(value = "realName", required = false) String realName, @RequestParam(value = "roleIds", required = false) String roleIds,
                                    @RequestParam(value = "loginName", required = false) String loginName, @RequestParam(value = "mobile", required = false) String mobile,
                                    @RequestParam(value = "loginPwd", required = false) String loginPwd, @RequestParam(value = "email", required = false) String email){
    	 UserInfo userInfo= getUserInfo(httpSession);
        Map<String, Object> map=new HashMap<String, Object>();

        if (StringUtils.isEmpty(id)){
            CheckLoginNameSQO checkLoginNameSQO=new CheckLoginNameSQO();
            checkLoginNameSQO.setLoginName(loginName);
            checkLoginNameSQO.setDisName(realName);
            if (!securityService.checkStaffName(checkLoginNameSQO)){
                map.put("statusCode", 300);
                map.put("message", "操作员名称已被占用");
                return map;
            }
            if (!securityService.checkloginname(checkLoginNameSQO)){
                map.put("statusCode", 300);
                map.put("message", "登录名已注册");
                return map;
            }
            CreateStaffCommand createStaffCommand=new CreateStaffCommand();
            createStaffCommand.setRealName(realName);
            createStaffCommand.setLoginName(loginName);
            createStaffCommand.setMobile(mobile);
            createStaffCommand.setLoginPwd(loginPwd);
            createStaffCommand.setRoleIds(roleIds);
            createStaffCommand.setEmail(email);
            createStaffCommand.setEnable((short)1);
            createStaffCommand.setCreateTime(new Date());
            Staff staff = staffService.create(createStaffCommand);
            logUtil.addLog(null, userInfo.getLoginName(), LogConstants.OPERATOR_ADD, staff.getAuthUser().getLoginName()+": "+staff.getId());
            map.put("statusCode", 200);
            map.put("callbackType","closeCurrent");
            map.put("message", "创建成功");
        }
        else {
            ModifyStaffCommand modifyStaffCommand=new ModifyStaffCommand();
            modifyStaffCommand.setUserId(id);
            modifyStaffCommand.setRealName(realName);
            modifyStaffCommand.setLoginName(loginName);
            modifyStaffCommand.setMobile(mobile);
            modifyStaffCommand.setRoleIds(roleIds);
            modifyStaffCommand.setEmail(email);
            Staff staff = staffService.modify(modifyStaffCommand);
            logUtil.addLog(null, userInfo.getLoginName(), LogConstants.OPERATOR_EDIT, staff.getAuthUser().getLoginName()+": "+staff.getId());
            map.put("statusCode", 200);
            map.put("callbackType","closeCurrent");
            map.put("message", "更新成功");
        }
        return map;
    }

    /**
     * 重置密码
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetpwd")
    public Map<String,Object> resetPwd(HttpSession httpSession ,@RequestParam(value = "ids",required = false) String ids){
    	 UserInfo userInfo= getUserInfo(httpSession);
    	Map<String, Object> map=new HashMap<String, Object>();
        String[] idarray=ids.split(",");
        for (String id :idarray){
            if (!StringUtils.isEmpty(id)){
                ResetPwdCommand resetPwdCommand=new ResetPwdCommand();
                resetPwdCommand.setId(id);
                resetPwdCommand.setPassword(MD5HashUtil.toMD5(DEFAULT_PASSWORD));
                securityService.resetpwd(resetPwdCommand);
                logUtil.addLog(null, userInfo.getLoginName(), LogConstants.RESET_PASS, "被重设id："+id);
            }
        }
        map.put("statusCode", 200);
        map.put("message", "重置密码成功，密码为【"+DEFAULT_PASSWORD+"】");
        return map;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    public Map<String,Object> delete(HttpSession httpSession, @RequestParam(value = "id",required = false) String id){
    	UserInfo userInfo= getUserInfo(httpSession);
    	Map<String, Object> map=new HashMap<String, Object>();
        if (id.equals(getUserInfo(httpSession).getUserId())){
            map.put("statusCode", 300);
            map.put("message", "参数有误");
        }
        try {
            DeleteStaffCommand deleteStaffCommand=new DeleteStaffCommand();
            DeleteAuthUserCommand deleteAuthUserCommand=new DeleteAuthUserCommand();
            deleteStaffCommand.setId(id);
            deleteAuthUserCommand.setId(id);
            staffService.delete(deleteStaffCommand);
            logUtil.addLog(null, userInfo.getLoginName(), LogConstants.OPERATOR_DEL, "被删除id："+id);
            map.put("statusCode", 200);
            map.put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("statusCode", 300);
            map.put("message", "删除失败");
        }
        return map;
    }

    /**
     * 禁用启用
     * @param id
     * @param enable
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyenable")
    public Map<String, Object> modifyEnable(HttpSession httpSession,@RequestParam(value = "id",defaultValue = "0") String id,@RequestParam(value = "enable",defaultValue = "0") Short enable){
    	UserInfo userInfo= getUserInfo(httpSession);
    	Map<String, Object> map=new HashMap<String, Object>();
        UpdateTypeCommand updateTypeCommand=new UpdateTypeCommand();
        updateTypeCommand.setId(id);
        if (enable.equals((short)0)|| enable.equals((short)1)){
            updateTypeCommand.setEnable(enable);
            try {
                securityService.updateenable(updateTypeCommand);
                if(enable==0){
                logUtil.addLog(null, userInfo.getLoginName(), LogConstants.STAFF_STOP, "id："+updateTypeCommand.getId());
                }else{
                logUtil.addLog(null, userInfo.getLoginName(), LogConstants.STAFF_START, "id："+updateTypeCommand.getId());
                }
                map.put("statusCode", 200);
                map.put("message", "操作成功");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("statusCode", 300);
                map.put("message", "操作失败");
            }
        }else {
            map.put("statusCode", 300);
            map.put("message", "参数有误");
        }
        return map;
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/changepwd")
    public String changepwd(){
        return "/staff/changepwd.ftl";
    }

    @ResponseBody
    @RequestMapping(value = "/changepwdpost")
    public Map<String,Object> changepwdpost(HttpSession httpSession, String pwd, String oldPwd){
        Map<String, Object> map=new HashMap<String, Object>();
        UserInfo userInfo= getUserInfo(httpSession);
        String opMd5 = MD5HashUtil.toMD5(oldPwd);
        QueryAuthUserSQO queryAuthUserSQO=new QueryAuthUserSQO();
        queryAuthUserSQO.setLoginName(userInfo.getLoginName());
        AuthUser authUser=securityService.queryAuthUser(queryAuthUserSQO);
        if (authUser==null){
            map.put("statusCode", 300);
            map.put("message", "帐号有误");
            return map;
        }
        if (!opMd5.equals(authUser.getPasswd())) {
            map.put("statusCode", 300);
            map.put("message", "密码错误");
            return map;
        }

        authUser.setPasswd(MD5HashUtil.toMD5(pwd));
        ChangePwdCommand changePwdCommand=new ChangePwdCommand();
        changePwdCommand.setId(authUser.getId());
        changePwdCommand.setPassword(authUser.getPasswd());
        securityService.changepwd(changePwdCommand);
        logUtil.addLog(null, userInfo.getLoginName(), LogConstants.MODIFY_PASS, authUser.getLoginName()+": "+authUser.getId());
        map.put("statusCode", 200);
        map.put("callbackType","closeCurrent");
        map.put("message", "修改成功");
        return map;
    }
}
