package lxs.api.controller.download;

import lxs.api.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DownloadController extends BaseController{

	@RequestMapping("AppDownload")
	public ModelAndView appDownload(){
		ModelAndView mav = new ModelAndView("download/download");
		return mav;
		
	}

}
