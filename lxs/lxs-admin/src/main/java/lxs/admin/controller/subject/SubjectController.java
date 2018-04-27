package lxs.admin.controller.subject;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.app.service.app.SubjectService;
import lxs.domain.model.app.Subject;
import lxs.pojo.command.app.AddSubjectCommand;
import lxs.pojo.command.app.ModifySubjectCommand;
import lxs.pojo.qo.app.SubjectQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	/**
	 * @Title: subjectList 
	 * @author guok
	 * @Description: 主题列表
	 * @Time 2015年9月18日下午3:17:18
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @param subjectQO
	 * @return
	 * @throws ParseException String 设定文件
	 * @throws
	 */
	@RequestMapping("/subjectList")
	public String subjectList(HttpServletRequest request,@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,SubjectQO subjectQO) throws ParseException {
		
		HgLogger.getInstance().info("lxs_dev", "【subjectList】"+"【subjectQO】" + JSON.toJSONString(subjectQO));
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(subjectQO);
		pagination= subjectService.queryPagination(pagination);
		
		model.addAttribute("subjectQO", subjectQO);
		model.addAttribute("pagination", pagination);
		
		return "/subject/subjectList.html";
	}
	
	/**
	 * @Title: addsubject 
	 * @author guok
	 * @Description: 跳转添加
	 * @Time 2015年9月18日下午3:17:31
	 * @param request
	 * @param response
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/addSubject")
	public String addSubject(HttpServletRequest request,HttpServletResponse response,Model model) {
		return "/subject/addSubject.html";
	}
	
	/**
	 * @Title: saveRecomend 
	 * @author guok
	 * @Description: 主题添加
	 * @Time 2015年9月18日下午3:17:41
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/saveSubject")
	public String saveRecomend(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute AddSubjectCommand command) {
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		try {
			subjectService.saveSubject(command);
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "subjectList");
	}
	
	/**
	 * @Title: editsubject 
	 * @author guok
	 * @Description: 跳转修改
	 * @Time 2015年9月18日下午3:17:50
	 * @param request
	 * @param response
	 * @param model
	 * @param subjectID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/editSubject")
	public String editSubject(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "subjectID") String subjectID) {
		SubjectQO subjectQO = new SubjectQO();
		subjectQO.setSubjectID(subjectID);
		Subject subject = subjectService.queryUnique(subjectQO);
		
		model.addAttribute("subject", subject);
		
		return "/subject/editSubject.html";
	}
	
	/**
	 * @Title: modifySubject 
	 * @author guok
	 * @Description: 修改主题
	 * @Time 2015年9月18日下午3:18:00
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/modifySubject")
	public String modifySubject(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute ModifySubjectCommand command) {
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			subjectService.modifySubject(command);
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "subjectList");
	}
	
	@RequestMapping(value="/changeSort")
	@ResponseBody
	public String changeSort(HttpServletRequest request,
			@RequestParam(value = "id") String ID,
			@RequestParam(value = "type") String type){
		Subject subject = subjectService.get(ID);
		if(subject.getSort()==1&&StringUtils.equals("down", type)){
			return DwzJsonResultUtil.createSimpleJsonString("500", "该线路已在最末尾");
		}else if(subject.getSort()==subjectService.getMaxSort()&&StringUtils.equals("up", type)){
			return DwzJsonResultUtil.createSimpleJsonString("500", "该线路已在最第一位");
		}else{
			if(StringUtils.equals("up", type)){
				int sort_a=subject.getSort();
				int sort_b=0;
				SubjectQO subjectQO = new SubjectQO();
				subjectQO.setSort(subject.getSort()+1);
				Subject subject2=subjectService.queryUnique(subjectQO);
				int i=1;
				while(subject2==null){
					subjectQO.setSort(subject.getSort()+1+i);
					subject2=subjectService.queryUnique(subjectQO);
					i++;
				}
				sort_b=subject2.getSort();
				subject.setSort(sort_b);
				subject2.setSort(sort_a);
				subjectService.update(subject);
				subjectService.update(subject2);
				return DwzJsonResultUtil.createSimpleJsonString("200", "上移成功");
			}else{
				int sort_a=subject.getSort();
				int sort_b=0;
				SubjectQO subjectQO = new SubjectQO();
				subjectQO.setSort(subject.getSort()-1);
				Subject subject2=subjectService.queryUnique(subjectQO);
				int i=1;
				while(subject2==null){
					subjectQO.setSort(subject.getSort()-1-i);
					subject2=subjectService.queryUnique(subjectQO);
					i++;
				}
				sort_b=subject2.getSort();
				subject.setSort(sort_b);
				subject2.setSort(sort_a);
				subjectService.update(subject);
				subjectService.update(subject2);
				return DwzJsonResultUtil.createSimpleJsonString("200", "下移成功");
			}
		}
	}
}
