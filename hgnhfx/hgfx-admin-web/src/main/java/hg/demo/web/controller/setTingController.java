package hg.demo.web.controller;

import hg.demo.member.common.domain.model.mall.Parameter;
import hg.demo.member.common.domain.model.system.DwzJsonResultUtil;
import hg.demo.member.common.domain.model.system.JsonUtil;
import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.demo.member.common.spi.ParameterSPI;
import hg.demo.member.common.spi.command.parameter.CreateParameterCommand;
import hg.demo.member.common.spi.command.parameter.DeleteParameterCommand;
import hg.demo.member.common.spi.command.parameter.ModifyParameterCommand;
import hg.demo.member.common.spi.qo.ParameterSQO;
import hg.demo.web.common.UserInfo;
import hg.framework.common.util.file.FdfsFileInfo;
import hg.framework.common.util.file.FdfsFileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * @author guok
 *	参数设置Controller
 */
@Controller
@RequestMapping("/set")
public class setTingController {

	@Autowired
	private ParameterSPI parameterService;
	
	/**
	 * @Title: toSetting 
	 * @author guok
	 * @Description: 跳转设置logo页
	 * @Time 2016年5月24日上午9:52:42
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/toSetLogo")
	public String toSetLogo(Model model) {
		ParameterSQO qo = new ParameterSQO();
		qo.setName("logoUrl");
		Parameter parameter = parameterService.queryParameter(qo);
		if (parameter != null) {
			model.addAttribute("logoUrl", parameter.getValue());
		}
		return "/set/logoUpload.ftl";
	}
	
	/**
	 * @Title: upLoad 
	 * @author guok
	 * @Description: 上传图片
	 * @Time 2016年5月23日下午2:53:42
	 * @param file
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/img")
	public String upLoad(@RequestParam(value = "file", required = false) MultipartFile file) {
		
        CommonsMultipartFile cf= (CommonsMultipartFile)file; 
        Map<String, String> map = new HashMap<String, String>();
        // 1 验证图片上传格式
		String fileName = cf.getFileItem().getName();
		if(!fileName.endsWith(".jpg") && !fileName.endsWith(".JPG") 
				&& !fileName.endsWith(".gif") && !fileName.endsWith(".GIF") 
				&& !fileName.endsWith(".png") && !fileName.endsWith(".PNG")
				&& !fileName.endsWith(".bmp") && !fileName.endsWith(".BMP")
				) {
			map.put("status", "false");
			map.put("msg", "请上传jpg、gif、png、bmp或JPG、GIF、PNG、BMP格式的文件！");
			return JsonUtil.parseObject(map, false);
		}
		// 2 验证文件大小
		Long size = cf.getFileItem().getSize();
		String maxSize="2048";
		if(size > Long.parseLong(maxSize) * 1024){
			map.put("status", "false");
			map.put("msg", "请上传【大小】小于等于" + maxSize + "KB的图片！");
			return JsonUtil.parseObject(map, false);
		}
        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
        File f = fi.getStoreLocation();
        
        
		
		FdfsFileUtil.init();
		FdfsFileInfo fdfsFileInfo = FdfsFileUtil.upload(f, map);
		//System.out.println(JSON.toJSONString(fdfsFileInfo));
		fdfsFileInfo.setSourceIpAddr("flyimg.hg365.com");
		return JSON.toJSONString(fdfsFileInfo);
	}
	
	/**
	 * @Title: saveLogo 
	 * @author guok
	 * @Description: 保存或修改logo
	 * @Time 2016年5月24日下午3:32:28
	 * @param request
	 * @param model
	 * @param logoUrl
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/saveLogo")
	public String saveLogo(HttpServletRequest request,HttpServletResponse response, Model model, @RequestParam(value = "logoUrl", required = false) String logoUrl) {
		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
		String message="设置成功";
		try {
			ParameterSQO qo = new ParameterSQO();
			qo.setName("logoUrl");
			Parameter parameter = parameterService.queryParameter(qo);
			//判断是否已经保存过
			if (parameter != null) {
				ModifyParameterCommand command = new ModifyParameterCommand();
				command.setName("logoUrl");
				command.setValue(logoUrl);
				command.setId(parameter.getId());
				parameter = parameterService.modify(command);
				
			}else {
				CreateParameterCommand command = new CreateParameterCommand();
				command.setName("logoUrl");
				command.setValue(logoUrl);
				parameter = parameterService.create(command);
			}
			model.addAttribute("logoUrl", parameter.getValue());
		} catch (Exception e) {
			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
			message="设置失败";
			e.printStackTrace();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "toSetLogo");
	}
	
	/**
	 * @Title: toSetSkin 
	 * @author guok
	 * @Description: 跳转设置皮肤
	 * @Time 2016年5月25日下午4:50:25
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/toSetSkin")
	public String toSetSkin(Model model,HttpSession httpSession) {
		UserInfo user = (UserInfo)httpSession.getAttribute(SecurityConstants.SESSION_USER_KEY);
		String skin = user.getLoginName()+"skinUrl";
		ParameterSQO qo = new ParameterSQO();
		qo.setName(skin);
		Parameter parameter = parameterService.queryParameter(qo);
		if (parameter != null) {
			model.addAttribute("skinUrl", parameter.getValue());
		}
		return "/set/setSkin.ftl";
	}
	
	/**
	 * @Title: saveSkin 
	 * @author guok
	 * @Description: 保存或修改皮肤图片
	 * @Time 2016年5月25日下午4:44:05
	 * @param request
	 * @param model
	 * @param skinUrl
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/saveSkin")
	public String saveSkin(HttpServletRequest request,HttpSession httpSession, Model model, @RequestParam(value = "skinUrl", required = false) String skinUrl) {
		String statusCode=DwzJsonResultUtil.STATUS_CODE_200;
		String message="设置成功";
		try {
			UserInfo user = (UserInfo)httpSession.getAttribute(SecurityConstants.SESSION_USER_KEY);
			String skin = user.getLoginName()+"skinUrl";
			ParameterSQO qo = new ParameterSQO();
			qo.setName(skin);
			Parameter parameter = parameterService.queryParameter(qo);
			//判断是否已经保存过
			if (parameter != null) {
				ModifyParameterCommand command = new ModifyParameterCommand();
				command.setName(skin);
				command.setValue(skinUrl);
				command.setId(parameter.getId());
				parameter = parameterService.modify(command);
				
			}else {
				CreateParameterCommand command = new CreateParameterCommand();
				command.setName(skin);
				command.setValue(skinUrl);
				parameter = parameterService.create(command);
			}
			model.addAttribute("skin", parameter.getName());
			model.addAttribute("skinUrl", parameter.getValue());
		} catch (Exception e) {
			statusCode=DwzJsonResultUtil.STATUS_CODE_500;
			message="设置失败";
			e.printStackTrace();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "toSetSkin");
	}
	
	/**
	 * @Title: delParameter 
	 * @author guok
	 * @Description: 刪除參數
	 * @Time 2016年5月30日下午5:49:24
	 * @param name
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/delParameter")
	public String delParameter(@RequestParam(value = "name", required = false) String name) {
		ParameterSQO sqo = new ParameterSQO();
		sqo.setName(name);
		Parameter parameter = parameterService.queryParameter(sqo);
		if (parameter != null) {
			DeleteParameterCommand command = new DeleteParameterCommand();
			command.setId(parameter.getId());
			parameterService.delete(command);
		}
		return "{data:'success'}";
	}
	
}
