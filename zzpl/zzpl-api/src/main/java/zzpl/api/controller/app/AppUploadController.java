package zzpl.api.controller.app;

import hg.common.util.UUIDGenerator;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zzpl.api.controller.BaseController;
import zzpl.app.service.local.app.APPService;
import zzpl.domain.model.app.APP;
import zzpl.pojo.qo.app.APPQO;

@Controller
@RequestMapping(value = "/app")
public class AppUploadController extends BaseController {

	@Autowired
	private APPService appService;
	
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		APP app = appService.queryUnique(new APPQO());
		if(app!=null){
			model.addAttribute("APPName", app.getAPPName());
		}
		return "/page/app/download.html";
	}

	@RequestMapping(value = "/upload")
	public String upload(HttpServletRequest request, Model model) {
		return "/page/app/upload.html";
	}

	@ResponseBody
	@RequestMapping(value = "/uploading")
	public String companyList(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		String path = request.getSession().getServletContext()
				.getRealPath("upload/");
		String fileName = file.getOriginalFilename();
		System.out.println(path);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		APP app =appService.queryUnique(new APPQO());
		if(app==null){
			app = new APP();
			app.setId(UUIDGenerator.getUUID());
			app.setAPPName(fileName);
			appService.save(app);
		}else{
			app.setAPPName(fileName);
			appService.update(app);
		}
		return "ok";
	}
}
