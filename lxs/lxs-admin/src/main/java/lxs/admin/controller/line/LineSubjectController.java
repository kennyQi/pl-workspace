package lxs.admin.controller.line;

import hg.common.util.DwzJsonResultUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.app.service.app.SubjectService;
import lxs.app.service.line.LineService;
import lxs.app.service.line.LineSubjectService;
import lxs.domain.model.app.Subject;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineSubject;
import lxs.pojo.command.line.CreateLineSubjectCommand;
import lxs.pojo.qo.app.SubjectQO;
import lxs.pojo.qo.line.LineSubjectQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/lineSubject")
public class LineSubjectController {

	@Autowired
	private LineSubjectService lineSubjectService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private LineService lineService;
	
	/**
	 * @Title: addSubject 
	 * @author guok
	 * @Description: 跳转添加
	 * @Time 2015年9月24日上午9:19:39
	 * @param request
	 * @param response
	 * @param model
	 * @param lineID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/addLineSubject")
	public String addLineSubject(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "lineID") String lineID) {
		SubjectQO subjectQO = new SubjectQO();
		subjectQO.setSubjectType(Subject.SUNGECT_TYPE_LINE);
		List<Subject> subjects = subjectService.queryList(subjectQO);
		model.addAttribute("subjects", subjects);
		Line line = lineService.get(lineID);
		model.addAttribute("line", line);
		LineSubjectQO lineSubjectQO = new LineSubjectQO();
		lineSubjectQO.setLineID(lineID);
		List<LineSubject> lineSubjects = lineSubjectService.queryList(lineSubjectQO);
		int i = 0;
		if (lineSubjects.size() > 0) {
			for (LineSubject lineSubject : lineSubjects) {
				model.addAttribute("subjectID"+i, lineSubject.getSubjectID());
				i++;
			}
		}
		
		return "/line/setLineSubject.html";
	}
	
	/**
	 * @Title: setLineSubject 
	 * @author guok
	 * @Description: 设置线路主题
	 * @Time 2015年9月24日上午9:19:58
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/saveLineSubject")
	public String saveLineSubject(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute CreateLineSubjectCommand command) {
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "设置成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		try {
			LineSubjectQO lineSubjectQO = new LineSubjectQO();
			lineSubjectQO.setLineID(command.getLineID());
			List<LineSubject> lineSubjects = lineSubjectService.queryList(lineSubjectQO);
			lineSubjectService.detele(command.getLineID());
			lineSubjectService.create(command);
			for (LineSubject lineSubject : lineSubjects) {
				lineSubjectQO = new LineSubjectQO();
				lineSubjectQO.setSubjectID(lineSubject.getSubjectID());
				Integer sum = lineSubjectService.queryCount(lineSubjectQO);
				subjectService.addProduct(lineSubject.getSubjectID(),sum);
			}
			for(String string:command.getSubjectID()){
				if(!StringUtils.equals(string, "0")){
					lineSubjectQO = new LineSubjectQO();
					lineSubjectQO.setSubjectID(string);
					Integer sum = lineSubjectService.queryCount(lineSubjectQO);
					subjectService.addProduct(string,sum);
				}
			}
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage(), null, "");
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "lineList");
	}
	
}
