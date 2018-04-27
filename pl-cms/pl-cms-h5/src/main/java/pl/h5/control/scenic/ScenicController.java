package pl.h5.control.scenic;
import java.io.PrintWriter;

import hg.common.page.Pagination;
import hg.common.util.SysProperties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import pl.app.service.MemberApplyServiceImpl;
import pl.app.service.ScenicServiceImpl;
import pl.cms.domain.entity.scenic.Scenic;
import pl.cms.pojo.command.apply.CreateMemberApplyCommand;
import pl.cms.pojo.qo.ScenicQO;
import pl.h5.control.BaseController;
import pl.h5.control.constant.PullUpListResult;
@RequestMapping("/scenic")
@Controller
public class ScenicController extends BaseController{
	@Resource
	private ScenicServiceImpl scenicServiceImpl;
	@Resource
	private MemberApplyServiceImpl memberApplyServiceImpl;
	
	@RequestMapping(value = "/index")
	public String queryArticleList(HttpServletRequest request, Model model,
			@ModelAttribute ScenicQO qo){
		qo.setOrderByCreateDate(-1);
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(10);
		pagination.setCondition(qo);
		pagination.setCheckPassLastPageNo(false);
		pagination = scenicServiceImpl.queryPagination(pagination);
		if(pagination.getList().size() < 5&&pagination.getList().size()!=0){
			model.addAttribute("haveMore", "false");
		}else{
			model.addAttribute("haveMore", "true");
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("qo", qo);
		
		String fileUploadPath = SysProperties.getInstance().get("fileUploadPath");
		model.addAttribute("fileUploadPath", fileUploadPath);
		return "/scenic/scenic_index.html";
	}
	@ResponseBody
	@RequestMapping("pullUpList")
	public String pullUpList(@RequestParam(value="pageNo",required=false) Integer pageNo, 
			@RequestParam(value="pageSize",required=false) Integer pageSize, ScenicQO qo ,PrintWriter out){
		PullUpListResult pullUpListResult = new PullUpListResult();
		pullUpListResult.setHaveMore(true);//是否还有数据
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(qo);
		pagination.setCheckPassLastPageNo(false);
		pagination = scenicServiceImpl.queryPagination(pagination);
		if(qo.getPageSize() > pagination.getList().size()){
			pullUpListResult.setHaveMore(false);
		}
		pullUpListResult.setPagination(pagination);
		pullUpListResult.setPageNo(pageNo);
		pullUpListResult.setPageSize(pageSize);
		pullUpListResult.setQo(qo);
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		filter.getExcludes().add("province");
		filter.getExcludes().add("city");
		String s = JSON.toJSONString(pullUpListResult,filter);
		return s;
	}
	@RequestMapping(value = "/details/{scenicId}")
	private String queryUniqueScenic(HttpServletRequest request, Model model,@PathVariable String scenicId ){
		ScenicQO qo=new ScenicQO();
		qo.setId(scenicId);
		Scenic scenic=scenicServiceImpl.queryUnique(qo);
		if(scenic==null){
			model.addAttribute("redirectURL", "/scenic/index");
			return "/fail404.html";
		}
		model.addAttribute("scenic", scenic);
		String fileUploadPath = SysProperties.getInstance().get("fileUploadPath");
		model.addAttribute("fileUploadPath", fileUploadPath);
		return "/scenic/scenic_details.html";
	}
	@RequestMapping(value = "/add")
	private String addScenic(HttpServletRequest request, Model model){
		String message=request.getParameter("success");
		if(!StringUtils.isNotBlank(message)){
			message="false";
		}
		model.addAttribute("success", message);
		return "/scenic/scenic_add.html";
	}
	@RequestMapping(value = "/create")
	private RedirectView createScenic(HttpServletRequest request, Model model,CreateMemberApplyCommand command){
		RedirectView view=new RedirectView("/scenic/index",true);
		memberApplyServiceImpl.create(command);
		return view;
	}
}
 