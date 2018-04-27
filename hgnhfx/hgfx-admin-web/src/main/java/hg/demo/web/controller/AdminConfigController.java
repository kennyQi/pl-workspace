package hg.demo.web.controller;

import hg.demo.member.common.domain.model.adminconfig.GeneralResult;
import hg.demo.member.common.spi.AdminConfigSPI;
import hg.demo.member.common.spi.command.adminconfig.ClearTestCommand;
import hg.demo.member.common.spi.command.adminconfig.CreateAdminAccountCommand;
import hg.demo.member.common.spi.command.adminconfig.CreateAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.DeleteAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.ModifyAdminConfigCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 * 后台系统配置管理
 * 
 * @author Administrator
 * @date 2016-5-27
 * @since
 */
@Controller
public class AdminConfigController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdminConfigSPI adminConfigService;

	/**
	 * 添加后台系统配置
	 * 
	 * @author Administrator
	 * @since hg-admin-web
	 * @date 2016-5-27
	 * @param request
	 * @param model
	 * @param command
	 *            添加后台系统配置命令
	 * @return
	 */
	// path:/adminConfig/create
	@RequestMapping("/adminConfig/create")
	public String create(HttpServletRequest request, Model model, CreateAdminConfigCommand command) {
		command.setValue("v");
		command.setDataKey("k");
		try {
			adminConfigService.create(command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/adminConfig/adminconfig_create.ftl";
	}

	/**
	 * 删除后台系统配置
	 * 
	 * @author Administrator
	 * @since hg-admin-web
	 * @date 2016-5-27
	 * @param request
	 * @param model
	 * @param command
	 *            删除后台系统配置命令
	 * @return
	 */
	// path:/adminConfig/delete
	@RequestMapping("/adminConfig/delete")
	public String delete(HttpServletRequest request, Model model, DeleteAdminConfigCommand command) {

		return "/adminConfig/adminconfig_delete.ftl";
	}

	/**
	 * 修改后台系统配置
	 * 
	 * @author Administrator
	 * @since hg-admin-web
	 * @date 2016-5-27
	 * @param request
	 * @param model
	 * @param command
	 *            修改后台系统配置命令
	 * @return
	 */
	// path:/adminConfig/modify
	@RequestMapping("/adminConfig/modify")
	public String modify(HttpServletRequest request, Model model, ModifyAdminConfigCommand command) {
		command.setId("cb2dbcdb514349659dafa08a43b64f06");
		command.setValue("v3");
		command.setDataKey("k3");
		adminConfigService.modify(command);
		return "/adminConfig/adminconfig_modify.ftl";
	}
	
	
	// path:/adminConfig/createAdminAccount
	@RequestMapping("/adminConfig/createAdminAccount")
	@ResponseBody
	public Map<String, Object> createAdminAccount( HttpServletRequest request,CreateAdminAccountCommand command) {
		try {
			GeneralResult r = adminConfigService.createAdminAccount(command);
			return r.getDataMp();
		} catch (Exception e) {
			return GeneralResult.errorResultMp(e.getMessage());
		}
	}
	
	// path:/adminConfig/toCreateAdminAccount
	@RequestMapping("/adminConfig/toCreateAdminAccount")
	public String toCreateAdminAccount( HttpServletRequest request, Model model ) {

	return "/adminConfig/adminconfig_tocreateadminaccount.ftl";
	}
	
	// path:/adminConfig/adminIsCreated
	@RequestMapping("/adminConfig/adminIsCreated")
	@ResponseBody
	public Map<String, Object> adminIsCreated( HttpServletRequest request) {
		

	return adminConfigService.adminIsCreated().getDataMp();
	}
	
	
	// path:/adminConfig/genDbConfig
	@RequestMapping("/adminConfig/genDbConfig")
	@ResponseBody
	public Map<String, Object> genDbConfig( HttpServletRequest request,String jdbcUrl,String userName,String pwd) {
jdbcUrl="jdbcUrl";
userName="userName";
pwd="pwd";
	return adminConfigService.genDbConfig( jdbcUrl, userName, pwd).getDataMp();
	}
	
	// path:/adminConfig/toGenDbConfig
	@RequestMapping("/adminConfig/toGenDbConfig")
	public String toGenDbConfig( HttpServletRequest request, Model model ) {

	return "/adminConfig/adminconfig_togendbconfig.ftl";
	}
	
	// path:/clear/toClearTest
	@RequestMapping("/clear/toClearTest")
	public String toClearTest( HttpServletRequest request, Model model ) {

	return "/adminConfig/clear_tocleartest.html";
	}
	
	/**
	 * 清楚测试数据
	 * @author Administrator
	 * @since hg-admin-web
	 * @date 2016-5-30
	 * @param request
	 * @return
	 */
	// path:/clear/clearTest
	@RequestMapping("/clear/clearTest")
	@ResponseBody
	public String clearTest( HttpServletRequest request,ClearTestCommand clearTestCommand) {
		try {
			
			GeneralResult r = adminConfigService.clearTest(clearTestCommand );
			return GeneralResult.dwzSuccessMsg(r.getMessage(), "");
		} catch (Exception e) {
			return GeneralResult.dwzExceptionMsg(e);
		}

	}
	
	
	// path:/adminConfig/genConfigJs
	@RequestMapping("/adminConfig/genConfigJs")
	@ResponseBody
	public Map<String, Object> genConfigJs(HttpServletRequest request) {
		String cp = request.getContextPath();
		genConfigJs("var jsConfigLoadOk = true\nvar jsConfigCp=\"" + cp + "\"");

		return GeneralResult.succWithData("", null).getDataMp();
	}

	private static void genConfigJs(String v) {
		FileWriter fw;
		try {
			String u = AdminConfigController.class.getResource("/").toString().replace("file:", "").split("WEB-INF")[0] + "\\resources\\commonFile";
			fw = new FileWriter(u + "/jsConfig.js");
			fw.write(v);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static {
		genConfigJs("");
		
	}
	


}