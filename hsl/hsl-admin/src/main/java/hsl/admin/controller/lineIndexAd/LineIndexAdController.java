package hsl.admin.controller.lineIndexAd;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hsl.pojo.command.ad.HslUpdateAdStatusCommand;
import hsl.pojo.command.line.ad.CreateLineIndexAdCommand;
import hsl.pojo.command.line.ad.ModifyLineIndexAdCommand;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.dto.line.ad.LineIndexAdDTO;
import hsl.pojo.dto.programa.ProgramaDTO;
import hsl.pojo.exception.LineIndexAdException;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.ad.HslAdQO;
import hsl.pojo.qo.line.ad.LineIndexAdQO;
import hsl.pojo.qo.programa.HslProgramaQO;
import hsl.spi.inter.ad.HslAdService;
import hsl.spi.inter.line.ad.HslLineIndexAdService;
import hsl.spi.inter.programa.HslProgramaContentService;
import hsl.spi.inter.programa.HslProgramaService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("lineIndexAd")
public class LineIndexAdController {
	@Autowired
	private HslLineIndexAdService hslLineIndexAdService;
	@Autowired
	private HslProgramaContentService hslProgramaContentService;
	@Autowired
	private HslProgramaService hslProgramaService;
	
	@Autowired
	private HslAdService hslAdService;
	
	
	//周边游参数：栏目ID、广告位ID、线路类型
	public static final String ZBY_PROGRAMA_ID=	"24df401113eb40bebd313a2fe7a72de5";
	public static final String ZBY_POSITION_ID= "hsl_linead_zby";
	public static final int ZBY_LINE_TYPE=1;
	
	//跟团游参数：栏目ID、广告位ID、线路类型
	public static final String GTY_PROGRAMA_ID=	"f400b5510ca44851a12f9f49e048b9c8";
	public static final String GTY_POSITION_ID= "hsl_linead_gty";
	public static final int GTY_LINE_TYPE=2;
	
	@RequestMapping(value="/zbyList")
	public String lineIndexAdZBYList(HttpServletRequest request,@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,LineIndexAdQO lineIndexAdQO){
		
		
		
		//查询周边游栏目广告
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		lineIndexAdQO.setPositionId(ZBY_POSITION_ID);
		lineIndexAdQO.setLineType(ZBY_LINE_TYPE);
		lineIndexAdQO.setIsFecthContent(true);
		pagination.setCondition(lineIndexAdQO);
		pagination = hslLineIndexAdService.queryLineIndexAds(pagination);
		
		//初始化周边游栏目
		HslProgramaQO qo=new HslProgramaQO();
		qo.setId(ZBY_PROGRAMA_ID);
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
		
		model.addAttribute("lineIndexAdQO", lineIndexAdQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		
		model.addAttribute("lineType", ZBY_LINE_TYPE);
		model.addAttribute("programaId", ZBY_PROGRAMA_ID);
		model.addAttribute("positionId", ZBY_POSITION_ID);
		
		return "lineIndex/lineIndexAd_listl.html";
	}
	
	@RequestMapping(value="/gtyList")
	public String lineIndexAdGTList(HttpServletRequest request,@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,LineIndexAdQO lineIndexAdQO){
		
		
		//查询周边游栏目广告
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		lineIndexAdQO.setPositionId(GTY_POSITION_ID);
		lineIndexAdQO.setLineType(GTY_LINE_TYPE);
		lineIndexAdQO.setIsFecthContent(true);
		pagination.setCondition(lineIndexAdQO);
		pagination = hslLineIndexAdService.queryLineIndexAds(pagination);
		
		//初始化周边游栏目
		HslProgramaQO qo=new HslProgramaQO();
		qo.setId(GTY_PROGRAMA_ID);
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
		
		model.addAttribute("lineIndexAdQO", lineIndexAdQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		model.addAttribute("pagination", pagination);
		
		model.addAttribute("lineType", GTY_LINE_TYPE);
		model.addAttribute("programaId", GTY_PROGRAMA_ID);
		model.addAttribute("positionId", GTY_POSITION_ID);
		
		return "lineIndex/lineIndexAd_gty_list.html";
	}
	/**
	 * @方法功能说明：添加
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月27日下午2:21:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addLineIndexAd")
	public String createLineIndexAd(HttpServletRequest request,Model model){
		String programaId=request.getParameter("programaId");
		HslProgramaQO qo=new HslProgramaQO();
		if(StringUtils.isNotBlank(programaId)){
			qo.setId(programaId);
		}
		
		
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
		
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		
		model.addAttribute("lineType", request.getParameter("lineType"));
		model.addAttribute("positionId", request.getParameter("positionId"));
		
		return "lineIndex/add_lineIndexAd.html";
	}
	
	/**
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月27日下午2:21:20
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/saveLineIndexAd")
	public String saveLineIndexAd(HttpServletRequest request,Model model,
			@ModelAttribute  CreateLineIndexAdCommand command){
		String tab="lineIndexAdS";
		if(command.getLineType()==2){
			tab="lineIndexAdG";
		}
		try{
			this.hslLineIndexAdService.createLineIndexAd(command);
			
		}catch(LineIndexAdException e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",null, null);
			
		}
		
		return  DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, tab);
		
	}
	
	/**
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月27日下午2:49:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/modifyLineIndexAd")
	public String modifyLineIndexAd(HttpServletRequest request,Model model){
		
		String id=request.getParameter("id");
		LineIndexAdQO lineIndexAdQO=new LineIndexAdQO();
		lineIndexAdQO.setId(id);
		LineIndexAdDTO lineIndexAd=this.hslLineIndexAdService.queryUnique(lineIndexAdQO);
		
		HslAdQO hslAdQO = new HslAdQO();
		hslAdQO.setId(lineIndexAd.getAdId());
		HslAdDTO hslAdDto=this.hslAdService.queryAd(hslAdQO);
		
		String programaId=request.getParameter("programaId");
		HslProgramaQO qo=new HslProgramaQO();
		if(StringUtils.isNotBlank(programaId)){
			qo.setId(programaId);
		}
		ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
		
		model.addAttribute("programaContentDTOList", programaDTO.getProgramaContentList());
		model.addAttribute("lineIndexAd", lineIndexAd);
		model.addAttribute("hslAdDto", hslAdDto);
		
		return "lineIndex/edit_lineIndexAd.html";
	}
	
	/**
	 * @方法功能说明：编辑保存
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月27日下午2:54:29
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/editLineIndexAd")
	public String editLineIndexAd(HttpServletRequest request,Model model,
			@ModelAttribute  ModifyLineIndexAdCommand command){
		String tab="lineIndexAdS";
		if(command.getLineType()==2){
			tab="lineIndexAdG";
		}
		
		try{
			this.hslLineIndexAdService.modifyLineIndexAd(command);
			
		}catch(LineIndexAdException e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",null, null);
			
		}
		
		return  DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, tab);
		
	}
	
	/**
	 * @方法功能说明：删除线路首页广告
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月27日下午3:08:51
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest request,Model model){
		String id=request.getParameter("id");
		try {
			this.hslLineIndexAdService.deleteLineIndexAd(id);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,"");
		} catch (LineIndexAdException e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败",null,"");
		}
		
	}
	@ResponseBody
	@RequestMapping(value="/deleteMore")
	public String deleteMore(@RequestParam("ids")String ids,HttpServletRequest request,Model model){
		String[] id_arr=ids.split(",");
		try {
			if(id_arr!=null){
				for(String id:id_arr){
					this.hslLineIndexAdService.deleteLineIndexAd(id);
				}
			}
			
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,"");
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败",null,"");
		}
		
	}
	/**
	 * @方法功能说明：显示或隐藏
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月27日下午3:36:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param show
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/displayOrHide")
	public String displayOrHide(HttpServletRequest request,Model model,
			@RequestParam(value="adId",required=true) String adId,
			@RequestParam(value="show",required=true) Boolean show){
		HslUpdateAdStatusCommand command=new HslUpdateAdStatusCommand();
		command.setId(adId);
		command.setIsShow(show);
		try {
			this.hslAdService.modifyAdStatus(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",null,"");
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",null,"");
		}
		
	}

}
