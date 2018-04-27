package plfx.admin.controller.gjjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.JsonUtil;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.app.service.local.DataTransService;
import plfx.gjjp.app.service.local.GJJPOrderLocalService;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.app.service.local.CountryLocalService;
import plfx.jp.command.admin.gj.AddExceptionOrderCommand;
import plfx.jp.domain.model.Country;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.pojo.exception.PLFXJPException;
import plfx.jp.qo.CountryQo;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.supplier.SupplierService;

/**
 * @类功能说明:国际机票异常订单控制器
 * @创建者名字：guotx
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @创建时间：2015年7月30日上午8:44:56
 * @版本：V1.0
 */
@Controller
@RequestMapping("/gjjp/exception")
public class GJExceptionOrderController {
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private GJJPOrderLocalService gjjpOrderLocalService;
	@Autowired
	private DataTransService dataTransService;
	@Autowired
	private CountryLocalService countryLocalService;
	/**
	 * 国际机票异常订单视图列表
	 * @param request
	 * @param model
	 * @param gjjpOrderQo
	 * @param pageNo
	 * @param pageSize
	 * @param beginTimeStr
	 * @param endTimeStr
	 * @return
	 */
	@RequestMapping("/orderList")
	public String orderList(
			HttpServletRequest request, Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr){
		//供应商列表
		List<SupplierDTO> supplierList=this.supplierService.getSupplierList(new SupplierQO());
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			gjjpOrderQo.setCreateDateBegin(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			gjjpOrderQo.setCreateDateEnd(DateUtil.dateStr2EndDate(endTimeStr));
		}
		//只查询异常订单
		gjjpOrderQo.setExceptionOrder(true);
		//初始化全部数据
		gjjpOrderQo.setInitAll(true);
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo==null?1:pageNo);
		pagination.setPageSize(pageSize==null?20:pageSize);
		//国家代码
		CountryQo countryQo=new CountryQo();
		List<Country> countries=countryLocalService.queryList(countryQo);
		//添加页面查询条件
		pagination.setCondition(gjjpOrderQo);
		pagination = gjjpOrderLocalService.queryPagination(pagination);
		//平台订单状态
		model.addAttribute("orderStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_STATUS_MAP));
		//平台支付状态
		model.addAttribute("orderPayStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_PAY_STATUS_MAP));
		//经销商列表
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("dataTransService",dataTransService);
		model.addAttribute("countries", countries);
		model.addAttribute("pagination", pagination);
		model.addAttribute("gjjpOrderQo", gjjpOrderQo);
		
		return "/airticket/exception/gjjp_Exception_OrderList.html";
	}
	/**
	 * 
	 * @方法功能说明：转向新增异常订单页面
	 * @修改者名字：guotx
	 * @修改时间：2015年8月3日9:30
	 * @修改内容：更改只加载非异常订单
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param gjjpOrderQo
	 * @参数：@param pageNum
	 * @参数：@param numPerPage
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAddExceptionOrder")
	public String toAddExceptionOrder(HttpServletRequest request, Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo,
			@RequestParam(value="pageNum",required=false) Integer pageNum, 
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage){
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNum = pageNum == null ? new Integer(1) : pageNum;
		numPerPage = numPerPage == null ? new Integer(10) : numPerPage;
		pagination.setPageNo(pageNum);
		pagination.setPageSize(numPerPage);
		gjjpOrderQo.setInitAll(true);
		//此时只能对非异常订单操作
		gjjpOrderQo.setExceptionOrder(false);
		// 添加查询条件
		pagination.setCondition(gjjpOrderQo);
		
		List<SupplierDTO> supplierList=this.supplierService.getSupplierList(new SupplierQO());
		pagination = gjjpOrderLocalService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		
		model.addAttribute("gjjpOrderQo", gjjpOrderQo);
		model.addAttribute("supplierList", supplierList);
		return "/airticket/exception/gjjp_addExceptionOrder.html";
	}
	
	/**
	 * 
	 * @方法功能说明：新增异常订单
	 * @修改者名字：guotx
	 * @修改时间：2015年8月3日9:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param orderNo
	 * @参数：@param adjustAmount
	 * @参数：@param voucher
	 * @参数：@param adjustReason
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addExceptionOrder")
	@ResponseBody
	public String addExceptionOrder(HttpServletRequest request, Model model,
			@ModelAttribute AddExceptionOrderCommand command
			){
		try {
			gjjpOrderLocalService.addExceptionOrder(command);
		} catch (PLFXJPException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-财务管理-异常订单新增失败"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString("300", "新增异常订单失败", "closeCurrent", "errorOrderList");
			
		}
		return DwzJsonResultUtil.createJsonString("200", "新增异常订单成功", "closeCurrent", "errorOrderList");
	}
	
	/**
	 * 国际机票异常订单添加页面详细设置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAdjustExceptionOrder")
	public String toAdjustExceptionOrder(HttpServletRequest request, Model model){
		String id=request.getParameter("id");
		model.addAttribute("platformOrderId", id);
		
		return "/airticket/exception/gjjp_adjustExceptionOrder.html";
	}
	
	
	/**
	 * 
	 * @方法功能说明：上传异常订单凭证
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午1:47:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload( HttpServletRequest request, HttpServletResponse response, Model model) {
		
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multi.getFile("voucherPictureFile");
		Map<String, String> result_map = new HashMap<String, String>();
		
		HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-财务管理-异常订单上传凭证开始");
		
		if(multipartFile != null){
			String imageName=multipartFile.getOriginalFilename();
			
			String imageType=imageName.substring(imageName.lastIndexOf(".")+1);
				FdfsFileInfo fileInfo=null;
				try {
					fileInfo = FdfsFileUtil.upload((FileInputStream)multipartFile.getInputStream(),imageType, null);
				} catch (IOException e) {
					HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-财务管理-异常订单上传凭证异常"+HgLogger.getStackTrace(e));
				}
				
				//fastdfs图片服务器地址:192.168.2.214
				String imageUrl="";
				if(fileInfo != null){
					imageUrl = SysProperties.getInstance().get("fileUploadPath")+fileInfo.getUri();
				}
				
				result_map.put("status", "success");
				result_map.put("imageUrl", imageUrl);
				result_map.put("imageName", imageName);
				
				HgLogger.getInstance().debug("yuqz", "机票分销平台-机票管理-财务管理-异常订单凭证图片上传成功");
		}else{
			result_map.put("status", "error");
			result_map.put("message", "上传异常");
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-财务管理-异常订单凭证图片上传失败");
		}
			
		HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-财务管理-异常订单上传凭证结束");
		return JsonUtil.parseObject(result_map, false);
	}
	
	
}
