package hsl.admin.controller.balance;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.CheckValidCodeCommand;
import hsl.pojo.command.SendValidCodeCommand;
import hsl.pojo.command.account.BusinessPartnersCommand;
import hsl.pojo.command.account.GrantCodeRecordCommand;
import hsl.pojo.dto.account.BusinessPartnersDTO;
import hsl.pojo.dto.account.GrantCodeRecordDTO;
import hsl.pojo.dto.account.PayCodeDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.account.BusinessPartnersQO;
import hsl.pojo.qo.account.GrantCodeRecordQO;
import hsl.pojo.qo.account.PayCodeQO;
import hsl.spi.inter.account.BusinessPartnersService;
import hsl.spi.inter.account.GrantCodeRecordService;
import hsl.spi.inter.account.PayCodeService;
import hsl.spi.inter.user.UserService;

@Controller
@RequestMapping(value="/balance")
public class BalanceController extends BaseController{
	@Autowired
	private PayCodeService payCodeService;
	@Autowired
	private BusinessPartnersService businessPartnersService;
	@Autowired
	private GrantCodeRecordService grantCodeRecordService;
	
	@Autowired
	private UserService userService;
	/**
	 * @方法功能说明：跳转发放充值码页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年8月31日下午2:34:32
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toRechargeCode")
	public String toRechargeCode(HttpServletRequest request,Model model,RedirectAttributes attr){
		
		HgLogger.getInstance().info("renfeng", BalanceController.class+"BalanceController->>toRechargeCode-->发送手机验证码成功>>验证码：");
		
		List<BusinessPartnersDTO> companyList=this.businessPartnersService.queryList(new BusinessPartnersQO());
		model.addAttribute("companyList", companyList);
		
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
		
		//是否查询方法记录的兑换码
		String recordId=request.getParameter("recordId");
		String isQuery=request.getParameter("isQuery");
		if(StringUtils.isNotBlank(recordId)&&"yes".equals(isQuery)){
			PayCodeQO pQo=new PayCodeQO();
			pQo.setGrantCodeRecordID(recordId);
			pagination.setCondition(pQo);
			pagination=this.payCodeService.queryPagination(pagination);
			if(pagination.getList().size()>0){
				model.addAttribute("exist", "yes");
			}
			
			model.addAttribute("isQuery", "yes");
			model.addAttribute("recordId", recordId);
		}
		
		model.addAttribute("pagination", pagination);
		return "/balance/rechargeCode.html";
	}
	/**
	 * @方法功能说明：跳转发放记录详情页面,填写手机验证码成功后，显示兑换码列表
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月2日下午6:23:13
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/paymentRecordDetail")
	public String paymentRecordDetail(HttpServletRequest request,Model model,
			@RequestParam(value = "id", required = false) String id){
		
		GrantCodeRecordQO qo=new GrantCodeRecordQO();
		qo.setId(id);
		GrantCodeRecordDTO record = this.grantCodeRecordService.queryUnique(qo);
		model.addAttribute("record",record);
		
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
		
		//是否查询方法记录的兑换码
		String isQueryPayCodes=request.getParameter("isQueryPayCodes");
		
		if("yes".equals(isQueryPayCodes)){
			PayCodeQO pQo=new PayCodeQO();
			pQo.setGrantCodeRecordID(record.getId());
			pagination.setCondition(pQo);
			pagination=this.payCodeService.queryPagination(pagination);
			if(pagination.getList().size()>0){
				model.addAttribute("exist", "yes");
			}
			
			model.addAttribute("isQueryPayCodes", "yes");
		}
		
		model.addAttribute("pagination", pagination);
		
		return "/balance/paymentRecordDetail.html";
	}
	
	/**
	 * @throws Exception 
	 * @throws Exception 
	 * @方法功能说明：充值码发放
	 * @修改者名字：renfeng
	 * @修改时间：2015年8月31日下午2:37:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/rechargeCode")
	public String rechargeCode(HttpServletRequest request,Model model,
			@ModelAttribute GrantCodeRecordCommand command,
			RedirectAttributes attr) throws Exception{
		
		String recordId="";
		Map<String,String> map=new HashMap<String,String>();
		try {
			//关联充值码所属公司
			BusinessPartnersQO bpQO=new BusinessPartnersQO();
			bpQO.setId(request.getParameter("companyId"));
			BusinessPartnersDTO bpDto=this.businessPartnersService.queryUnique(bpQO);
				
			BusinessPartnersCommand bpCmd=BeanMapperUtils.map(bpDto, BusinessPartnersCommand.class);
			command.setBusinessPartnersCommand(bpCmd);
			
			//生成充值码
			List<PayCodeDTO> payCodeList=this.grantCodeRecordService.issueRechargeCode(command);
			 recordId=payCodeList.get(0).getGrantCodeRecord().getId();
			 
			map.put("status", "success");
			map.put("recordId", recordId);
			
			
		} catch (Exception e) {
			map.put("status", "error");
			e.printStackTrace();
		}
		
		return JSON.toJSONString(map); 
	}
	
	/**
	 * @方法功能说明：发送手机验证码
	 * @修改者名字：renfeng
	 * @修改时间：2015年8月31日下午4:59:00
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/sendCode")
	public String sendValidCode(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute SendValidCodeCommand command){
		Map<String,Object> map=new HashMap<String, Object>();
		HgLogger.getInstance().info("renfeng", "发放充值码 ，发送手机验证码 ,>>"+"手机号："+command.getMobile());
		try {
			//command.setScene(5);
			String validToken = userService.sendValidCode(command);
			HgLogger.getInstance().info("renfeng", "发送手机验证码成功>>验证码："+validToken);
			map.put("validToken", validToken);
			map.put("status", "success");
		} catch (UserException e) {
			HgLogger.getInstance().error("renfeng", "发送手机验证码失败>>"+e.getMessage()+HgLogger.getStackTrace(e));
			map.put("error", e.getMessage());
			map.put("status", "error");
			e.printStackTrace();
		}
		return JSON.toJSONString(map);
	}
	/**
	 * @方法功能说明：验证手机验证码
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月2日下午6:54:47
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@param attr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/checkValidCode")
	public String checkValidCode(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute CheckValidCodeCommand command){
		
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			//校验手机验证码
			this.userService.checkValidCode(command);
			map.put("status", "success");
		} catch (UserException e) {
			
			if(e.getCode().equals(e.RESULT_VALICODE_INVALID)||e.getCode().equals(e.RESULT_VALICODE_INVALID)
						||e.getCode().equals(e.VALIDCODE_OVERTIME)||e.getCode().equals(e.RESULT_VALICODE_WRONG)){
				
				e.printStackTrace();
				map.put("status", "error");
			}
		}
		
		
		return JSON.toJSONString(map);
	}
	
	/**
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月2日下午4:29:43
	 * @修改内容：导出兑换码
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/export")
	public String exportPayCode(HttpServletRequest request,HttpServletResponse response){
		HgLogger.getInstance().debug("renfeng", "开始导出兑换码。。。。。。");
		GrantCodeRecordQO qo=new GrantCodeRecordQO();
		qo.setId(request.getParameter("payCodeRecordId"));
		GrantCodeRecordDTO record = this.grantCodeRecordService.queryUnique(qo);
		HgLogger.getInstance().info("renfeng", "发放充值码页面，要导出的充值码列表："+JSON.toJSONString(record.getPayCodes()));
		OutputStream os;
		WritableWorkbook workbook;
		//设置内容格式
		response.setHeader("Content-Type","application/x-xls;charset=utf-8" );
		//这个用来提示下载的文件名
		response.setHeader("Content-Disposition", "attachment; filename="+"payCodes.xls");
		//jxl前面是列号，后面行号
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			if(record.getPayCodes().size()>0){
				WritableSheet ws = workbook.createSheet("兑换码记录表", 0);
				Label head1 = new Label(0,0,"序号");
				ws.addCell(head1);
				Label head2 = new Label(1,0,"兑换码");
				ws.addCell(head2);
				int i = 1;
				
				for(PayCodeDTO payCode : record.getPayCodes()){
					Label num = new Label(0, i, i+"");
					ws.addCell(num); 
					Label code = new Label(1,i,payCode.getCode());
					ws.addCell(code);
					
					i++;
				}
				workbook.write();
				workbook.close();
				os.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("renfeng", "BalanceController->exportPayCode->exception:" + HgLogger.getStackTrace(e));
		}
		return null;
	}
			
			
			
	
	/**
	 * @方法功能说明：发放记录
	 * @修改者名字：renfeng
	 * @修改时间：2015年8月31日下午2:48:25
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/paymentRecordList")
	public String paymentRecordList(HttpServletRequest request,Model model,
			@ModelAttribute GrantCodeRecordQO qo,
			@RequestParam(value = "pageNum", required = false) Integer pageNo, 
			@RequestParam(value = "numPerPage",required = false) Integer pageSize){
		
		//添加分页查询条件
		Pagination pagination = new Pagination();
		pagination.setCondition(qo);
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 20 : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination=grantCodeRecordService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("prQo", qo);
		
		return "/balance/paymentRecordList.html";
	}
	
	
	/**
	 * @方法功能说明：充值记录
	 * @修改者名字：renfeng
	 * @修改时间：2015年8月31日下午2:56:22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/rechargeRecordList")
	public String rechargeRecordList(HttpServletRequest request,Model model,
			@ModelAttribute PayCodeQO qo,
			@RequestParam(value = "pageNum", required = false) Integer pageNo, 
			@RequestParam(value = "numPerPage",required = false) Integer pageSize){
		//添加分页查询条件
		Pagination pagination = new Pagination();
		qo.setType(PayCodeQO.PAYCODE_CZ);
		pagination.setCondition(qo);
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 20 : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination=this.payCodeService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("prQo", qo);
		
		
		
		return "/balance/rechargeList.html";
	}
	
	/**
	 * 跳转审核页面
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月2日下午6:12:56
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toCheck")
	public String rechargeRecordList(HttpServletRequest request,Model model,
			@RequestParam(value = "id", required = false) String id){
		
		model.addAttribute("id",id);
		
		return "/balance/check.html";
	}
	
	
	/**
	 * @方法功能说明：审核发放兑换码
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月2日下午5:58:54
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@param attr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/check")
	public String check(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute GrantCodeRecordCommand command,RedirectAttributes attr){
		try{
			this.grantCodeRecordService.examineRechargeCode(command);
			if(command.getStatus()==2){
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "审核不通过",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "paymentRecordList");
			}else{
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "审核通过",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "paymentRecordList");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "操作失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "paymentRecordList");
		}
		
		
	}
	
	/**
	 * @方法功能说明：发放记录作废
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月14日下午4:13:08
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@param attr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/invalid")
	public String invalid(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute GrantCodeRecordCommand command,RedirectAttributes attr){
		try{
			this.grantCodeRecordService.examineRechargeCode(command);
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "操作失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "paymentRecordList");
		}
		
		return DwzJsonResultUtil.createSimpleJsonString("200", "已作废");
	}
	
	
}
