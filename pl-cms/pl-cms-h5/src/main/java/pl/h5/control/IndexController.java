package pl.h5.control;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.app.service.IndexLunboServiceImpl;
import pl.cms.domain.entity.lunbo.IndexLunbo;
import pl.cms.pojo.qo.IndexLunboQO;
@Controller
public class IndexController extends BaseController{
	@Resource
	private IndexLunboServiceImpl indexLunboServiceImpl;
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		HgLogger.getInstance().info("chenxy", "进入首页");
		IndexLunboQO qo=new IndexLunboQO();
		qo.setIsShow(true);
		qo.setOrderByCreateDate(-1);
		List<IndexLunbo> indexLunbos=indexLunboServiceImpl.queryList(qo);
		model.addAttribute("indexLunbos", indexLunbos);
		String fileUploadPath = SysProperties.getInstance().get("fileUploadPath");
		model.addAttribute("fileUploadPath", fileUploadPath);
		return "/index.html";
	}
	@RequestMapping(value = "/about")
	public String  about(){
		return "/about.html";
	}
}
