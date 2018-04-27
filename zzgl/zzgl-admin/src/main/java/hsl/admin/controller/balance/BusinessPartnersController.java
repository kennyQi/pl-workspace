package hsl.admin.controller.balance;

import javax.servlet.http.HttpServletRequest;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.account.BusinessPartnersCommand;
import hsl.pojo.dto.account.BusinessPartnersDTO;
import hsl.pojo.qo.account.BusinessPartnersQO;
import hsl.pojo.qo.account.GrantCodeRecordQO;
import hsl.spi.inter.account.BusinessPartnersService;
import hsl.spi.inter.account.GrantCodeRecordService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value="/businessPartners")
public class BusinessPartnersController extends BaseController{
	@Autowired
	private BusinessPartnersService businessPartnersService;
	@Autowired
	private GrantCodeRecordService grantCodeRecordService;

	/**
	 * @方法功能说明：公司记录列表
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日上午8:54:35
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request,Model model,
			@ModelAttribute BusinessPartnersQO qo,
			@RequestParam(value = "pageNum", required = false) Integer pageNo, 
			@RequestParam(value = "numPerPage",required = false) Integer pageSize){
		
		//添加分页查询条件
		Pagination pagination = new Pagination();
		pagination.setCondition(qo);
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 20 : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination=businessPartnersService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("prQo", qo);
		
		return "/businessPartners/businessPartnersList.html";
	}
	
	/**
	 * @方法功能说明：跳转添加公司页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日上午8:54:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/add")
	public String addBusinessPartners(HttpServletRequest request,Model model){
		
		return "/businessPartners/addBusinessPartners.html";
		
	}
	/**
	 * @方法功能说明：跳转添加公司页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日上午8:54:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/edit")
	public String eidtBusinessPartners(HttpServletRequest request,Model model,
			@ModelAttribute BusinessPartnersQO qo){
		
		BusinessPartnersDTO bp=this.businessPartnersService.queryUnique(qo);
		model.addAttribute("bp", bp);
		
		return "/businessPartners/addBusinessPartners.html";
		
	}
	/**
	 * @方法功能说明：保存公司
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日上午8:56:46
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public String saveBusinessPartners(HttpServletRequest request,Model model,
			@ModelAttribute BusinessPartnersCommand command){
		
		String res="添加成功";
		try {
			
			if(StringUtils.isNotBlank(command.getId())){
				res="编辑成功";
				this.businessPartnersService.updateBusinessPartners(command);
			}else{
				this.businessPartnersService.createBusinessPartners(command);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "操作失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "companyList");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, res,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "companyList");
	
	}
	
	/**
	 * @方法功能说明：公司详情，并显示发放充值码记录
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日上午9:22:38
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/detail")
	public String detail(HttpServletRequest request,Model model,
			@RequestParam(value = "id", required = false) String id,
			@ModelAttribute GrantCodeRecordQO qo){
		
		BusinessPartnersQO bpQo=new BusinessPartnersQO ();
		bpQo.setId(id);
		BusinessPartnersDTO bp=this.businessPartnersService.queryUnique(bpQo);
		model.addAttribute("bp", bp);
		qo.setCompanyId(id);
		qo.setId(null);
		//设置分页参数
		Pagination pagination = new Pagination();
		int pageNo=1;
		int pageSize=20;
		if(StringUtils.isNotBlank(request.getParameter("pageNum"))){
			pageNo=Integer.parseInt(request.getParameter("pageNum"));
		}
		if(StringUtils.isNotBlank(request.getParameter("numPerPage"))){
			pageSize=Integer.parseInt(request.getParameter("numPerPage"));
		}
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);	
		pagination.setCondition(qo);
		
		pagination=this.grantCodeRecordService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("id", id);
		model.addAttribute("prQo", qo);
		return "/businessPartners/detail.html";
		
	}
	
	
}
