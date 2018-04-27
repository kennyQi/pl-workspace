package hsl.admin.controller.sign;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.sign.SignLocalService;
import hsl.domain.model.sign.Sign;
import hsl.pojo.command.sign.DeleteActivitySignCommand;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.SignQo;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @类功能说明：签到Controller
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年9月17日下午3:10:01
 *
 */
@Controller
@RequestMapping("/sign/activity")
public class SignController extends BaseController{

	@Autowired
	private SignLocalService          signLocalService;
	
	
	/**
	 * 
	 * @方法功能说明：查询活动签到列表
	 * @修改者名字：hgg
	 * @修改时间：2015年9月17日下午3:12:51
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/list")
	public String queryList(@RequestParam(value="pageNum",required=false)Integer pageNo,
            @RequestParam(value="numPerPage",required=false)Integer pageSize,Model model,SignQo signQo){
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? 0 : pageNo);
		pagination.setPageSize(pageSize == null ? 0 : pageSize);
		pagination.setCondition(signQo);
		pagination = signLocalService.queryPagination(pagination);
		
		@SuppressWarnings("unchecked")
		List<Sign> signList = (List<Sign>) pagination.getList();
		if(CollectionUtils.isNotEmpty(signList)){
			model.addAttribute("signList", signList);
		}
		
		model.addAttribute("signQo", signQo);
		model.addAttribute("pagination", pagination);
		
		return "sign/signList.html";
	} 
	
	/**
	 * 
	 * @方法功能说明：删除活动签到
	 * @修改者名字：hgg
	 * @修改时间：2015年9月17日下午3:42:42
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param deleteActivitySignCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/delActivitySign")
	@ResponseBody
	public String delSign(Model model,DeleteActivitySignCommand deleteActivitySignCommand){
		
		HgLogger.getInstance().info("hgg", "删除活动开始");
		
		String message ="删除成功";
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		
		try {
			signLocalService.delActivitySign(deleteActivitySignCommand);
		} catch (UserException e) {
			message ="删除失败";
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			HgLogger.getInstance().info("hgg", "异常:【"+e.getMessage()+"】");
		}
		
		HgLogger.getInstance().info("hgg", "删除活动结束");
		
		return DwzJsonResultUtil.createJsonString(statusCode, message, DwzJsonResultUtil.FLUSH_FORWARD, "acivitySignList");
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到导入excel页面
	 * @修改者名字：hgg
	 * @修改时间：2015年9月17日下午4:44:24
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/upLoadSignExcelPage")
	public String toUpLoadPage(Model model){
		
		return "sign/uploadSingExcel.html";
	}
	
	/**
	 * 
	 * @方法功能说明：上传签到数据的 excel
	 * @修改者名字：hgg
	 * @修改时间：2015年9月17日下午4:48:14
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/uploadSignDataExcel")
	@ResponseBody
	public String uploadSignDataExcel(Model model,@RequestParam("file") MultipartFile file){
		
		String message ="导入成功";
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		Integer successCount = 0;
		
		try {
			//验证文件
			signLocalService.vaildExcel(file);
			//保存数据
			successCount = signLocalService.getData(file);
		} catch (UserException e) {
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			return DwzJsonResultUtil.createJsonString(statusCode, e.getMessage(), DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "acivitySignList");
		}
		
		
		return DwzJsonResultUtil.createJsonString(statusCode, message+",本次成功导入数量:"+successCount, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "acivitySignList");
	}
	
}
