package hsl.admin.controller.column;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.Programa.CreateProgramaContentCommand;
import hsl.pojo.command.Programa.UpdateProgramaContentCommand;
import hsl.pojo.command.Programa.UpdateProgramaStatusCommand;
import hsl.pojo.dto.programa.ProgramaContentDTO;
import hsl.pojo.dto.programa.ProgramaDTO;
import hsl.pojo.qo.programa.HslProgramaQO;
import hsl.spi.inter.programa.HslProgramaContentService;
import hsl.spi.inter.programa.HslProgramaService;

@Controller
@RequestMapping(value="/column")
public class ColumnController extends BaseController{
	
	@Autowired
	private HslProgramaService hslProgramaService;
	@Autowired
	private HslProgramaContentService hslProgramaContentService;
	@RequestMapping(value = "/list")
	public String orderList(HttpServletRequest request, Model model,
			@ModelAttribute HslProgramaQO hslProgramaQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo, 
			@RequestParam(value = "numPerPage",required = false) Integer pageSize) {
		
		//添加分页查询条件
		Pagination pagination = new Pagination();
		pagination.setCondition(hslProgramaQO);
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 20 : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		
		pagination=this.hslProgramaService.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("hslProgramaQO", hslProgramaQO);		
	
		return "/column/column_list.html";
	}
	
	/**
	 * 跳转修改栏目页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/modifyPrograma")
	public String modifyNotice(@RequestParam(value="id",required=false) String id,Model model){
				
		HslProgramaQO hslProgramaQO=new HslProgramaQO();
		hslProgramaQO.setId(id);
		
		ProgramaDTO programaDto=this.hslProgramaService.queryUnique(hslProgramaQO);
		model.addAttribute("columnDto", programaDto);		
		
		return "/column/modify_column.html";
	}
	
	
	/**
	 * 
	 * @方法功能说明：修改栏目内容
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月21日下午5:02:35
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value="/updatePrograma")
	@ResponseBody
	public Object updatePrograma(HttpServletRequest request,Model model,@RequestParam(value="id",required=false) String id){
		try {
			HslProgramaQO hslProgramaQO=new HslProgramaQO();
			hslProgramaQO.setId(id);
			
			ProgramaDTO programaDto=this.hslProgramaService.queryUnique(hslProgramaQO);
			List<ProgramaContentDTO>programaContentList=programaDto.getProgramaContentList();
			//修改栏目内容
			String[] content_arr=request.getParameterValues("content");
			if(content_arr!=null){
				for(int i=0;i<content_arr.length;i++){
					UpdateProgramaContentCommand cmd=new UpdateProgramaContentCommand();
					cmd.setId(programaContentList.get(i).getId());
					cmd.setContent(content_arr[i]);
					this.hslProgramaContentService.updateProgramaContent(cmd);
				}
			}
			
			//新增栏目内容
			String[] add_content_arr=request.getParameterValues("content_");
			if(add_content_arr!=null&&add_content_arr.length>0){
				CreateProgramaContentCommand cmd=null;
				for(String add_content:add_content_arr){
					 cmd=new CreateProgramaContentCommand();
					 cmd.setProgramaId(id);
					 cmd.setContent(add_content);
					 this.hslProgramaContentService.createProgramaContent(cmd);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "column");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "column");
	}
	
	/**
	 * 删除栏目
	 * @param command
	 * @param request
	 * @param model
	 * */
	@RequestMapping(value="/delete")
	@ResponseBody
	public  String  deleteColumn(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=false) String id){
		
		try{
			this.hslProgramaService.deletePrograma(id);
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "栏目删除",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "column");
		}
		return  DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "栏目删除成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "column");
	}
	/**
	 * 批量删除记录
	 * @param 
	 * @param model
	 * @param request
	 * */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public String deleteAllColumn(HttpServletRequest request, Model model){
			try {
				String ids = request.getParameter("ids");
				this.hslProgramaService.deletePrograma(ids);
			} catch (Exception e) {
				DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "栏目删除",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "column");
			}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "栏目删除成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "column");
	}
	
	/**
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月21日下午5:13:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/displayOrHide")
	@ResponseBody
	public  String  displayOrHide(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id,@RequestParam(value="status",required=true) String status){
		
		try{
			UpdateProgramaStatusCommand cmd=new UpdateProgramaStatusCommand();
			cmd.setId(id);
			cmd.setStatus(Integer.parseInt(status));
			this.hslProgramaService.updateProgramaStutas(cmd);
		}catch(Exception e){
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败");
		}
		return DwzJsonResultUtil.createSimpleJsonString("200", "修改成功");
	}
	
	/**
	 * @方法功能说明：删除栏目内容
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月22日下午5:23:28
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/deleteContent")
	@ResponseBody
	public String deleteProgramaContent(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id){
		try {
			this.hslProgramaContentService.deleteProgramaContent(id);
		} catch (Exception e) {
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败");
		}
		
		
		return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功");
		
		
	}
	
}
