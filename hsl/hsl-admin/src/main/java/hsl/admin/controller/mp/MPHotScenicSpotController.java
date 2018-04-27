package hsl.admin.controller.mp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.mp.pojo.dto.scenicspot.ScenicSpotsDTO;

import com.alibaba.fastjson.JSON;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.CreateHotScenicSpotCommand;
import hsl.pojo.command.ModifyHotScenicSpotCommand;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.PCScenicSpotDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslHotScenicSpotQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.spi.inter.mp.MPScenicSpotService;




@Controller
@RequestMapping(value="/hotspot")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MPHotScenicSpotController extends BaseController{
	
	@Autowired
	private MPScenicSpotService scenicSpotService;
	

	
	/**
	 * 当前热门景点列表
	 * @param request
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */

	@RequestMapping(value="/list")
	public String queryHotScenicSpotList(HttpServletRequest request, Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		Pagination pagination=new Pagination();
		pageNo=pageNo!=null?pageNo:1;
		pageSize=pageSize!=null?pageSize:20;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		//查询当前热门景点
		Map map=queryHotScenicSpot(pageNo,pageSize,true,null);
		List<HotScenicSpotDTO> list =(List<HotScenicSpotDTO>) map.get("dto");
		pagination.setTotalCount(Integer.parseInt(map.get("count").toString()));
		if(list != null && list.size() > 0){
			model.addAttribute("scenicSpotDTOList", list);
		}else{
			model.addAttribute("scenicSpotDTOList", list=new ArrayList<HotScenicSpotDTO>());
		}
		model.addAttribute("pagination", pagination);
		return "/scenicspot/hot_spot_list.html";
	}
	
	/**
	 * 历史热门景点列表
	 * @param request
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/history")
	public String queryHistoryHotScenicSpot(HttpServletRequest request, Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			@RequestParam(value="spotsname",required=false)String spotsname){
		
		Pagination pagination=new Pagination();
		pageNo=pageNo!=null?pageNo:1;
		pageSize=pageSize!=null?pageSize:20;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		//查询热门景点
		Map map=queryHotScenicSpot(pageNo,pageSize,false,spotsname);
		List<HotScenicSpotDTO> list =(List<HotScenicSpotDTO>) map.get("dto");
		pagination.setTotalCount(Integer.parseInt(map.get("count").toString()));
		
		if(list != null && list.size() > 0){
			model.addAttribute("scenicSpotDTOList", list);
		}else{
			model.addAttribute("scenicSpotDTOList", list=new ArrayList<HotScenicSpotDTO>());
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("spotsname", spotsname);
		return "/scenicspot/history_spot_list.html";
	}
	
	/**
	 * 跳转到增加热门景点页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/tocreate")
	public String toCreateHotScenicSpot(HttpServletRequest request, Model model){
		
		return "/scenicspot/add_spots.html";
	}
	
	/**
	 * 新建热门景点
	 * @param request
	 * @param model
	 * @param spotsname
	 * @param view
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/create")
	@ResponseBody
	public String createHotScenicSpot(HttpServletRequest request, Model model,
			@RequestParam(value="spotsname",required=false)String spotsname,
			@RequestParam(value="view",required=false)String view){

				List<PCScenicSpotDTO> scenicSpotDTOList=queryScenicSpot(spotsname);
				if (scenicSpotDTOList != null && scenicSpotDTOList.size() > 0) {
					//查询当前热门景点
					Map map=queryHotScenicSpot(1,20,true,null);
					List<HotScenicSpotDTO> list =(List<HotScenicSpotDTO>) map.get("dto");

					// 要设置为热门景点的景点信息
					PCScenicSpotDTO scenicSpotDTO = scenicSpotDTOList.get(0);

					// 如果当前热门景点数量已经达到最大值并且当前景点设置为当前热门景点
					if (list.size() >= 6 && "view".equals(view)) {
						HslMPScenicSpotQO mpScenicSpotQO = new HslMPScenicSpotQO();
						mpScenicSpotQO.setHotScenicSpotId(list.get(list.size() - 1).getId());
						mpScenicSpotQO.setHot(true);
						mpScenicSpotQO.setIsOpen(true);
						// 把最旧的热门景点设置为非当前热门景点
						scenicSpotService.removeCurrentHotScenicSpot(mpScenicSpotQO);
					}
					// 如果当前景点不设置为当前热门景点或当前热门景点数量未达到最大值，则直接保存
					CreateHotScenicSpotCommand command = new CreateHotScenicSpotCommand();
					command.setAddress(scenicSpotDTO.getScenicSpotGeographyInfo().getAddress());
					command.setAlias(scenicSpotDTO.getScenicSpotBaseInfo().getAlias());
					command.setCity(scenicSpotDTO.getScenicSpotGeographyInfo().getCityName());
					command.setCreateDate(new Date());
					command.setGrade(scenicSpotDTO.getScenicSpotBaseInfo().getGrade());
					command.setImage(scenicSpotDTO.getScenicSpotBaseInfo().getImage());
					command.setIntro(scenicSpotDTO.getScenicSpotBaseInfo().getIntro());
					command.setName(scenicSpotDTO.getScenicSpotBaseInfo().getName());
					if ("view".equals(view)) {
						command.setOpen(true);
						command.setOpenDate(new Date());
					} else {
						command.setOpen(false);
					}
					command.setProvince(scenicSpotDTO.getScenicSpotGeographyInfo().getProvinceName());
					command.setScenicSpotId(scenicSpotDTO.getId());
					command.setTraffic(scenicSpotDTO.getScenicSpotGeographyInfo().getTraffic());
					scenicSpotService.createHotScenicSpot(command);
					return DwzJsonResultUtil.createJsonString("200", "热门景点添加成功", "closeCurrent", "hotcurrentspot");
				}
			
			return DwzJsonResultUtil.createJsonString("300", "热门景点添加失败，没有此景区信息", "closeCurrent", "hotcurrentspot");
	}
	
	/**
	 * 景区提示框匹配
	 * @param request
	 * @param model
	 * @param spotname
	 * @return
	 */
	@RequestMapping(value="/match")
	@ResponseBody
	public String matchName(HttpServletRequest request, Model model,
			@RequestParam(value="spotname",required=false)String spotname){
		HslMPScenicSpotQO mpScenicSpotsQO =new HslMPScenicSpotQO();
		mpScenicSpotsQO.setByName(true);
		mpScenicSpotsQO.setByArea(false);
		mpScenicSpotsQO.setContent(spotname);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(false);
		mpScenicSpotsQO.setPageSize(10);
		Map scenicSpotMap=new HashMap();
		try {
			scenicSpotMap = scenicSpotService.queryScenicSpot(mpScenicSpotsQO);
			List<PCScenicSpotDTO> scenicSpotDTOList = (List<PCScenicSpotDTO>) scenicSpotMap.get("dto");
			if(scenicSpotDTOList != null && scenicSpotDTOList.size() > 0){
				List<ScenicSpotsDTO> list=new ArrayList<ScenicSpotsDTO>();
				for (PCScenicSpotDTO scenicSpotDTO : scenicSpotDTOList) {
					ScenicSpotsDTO dto=new ScenicSpotsDTO();
					dto.setName(scenicSpotDTO.getScenicSpotBaseInfo().getName());
					list.add(dto);
				}
				return JSON.toJSONString(list);
			}
		} catch (MPException e) {
			HgLogger.getInstance().error("zhangka", "MPHotScenicSpotController->matchName->exception:" + HgLogger.getStackTrace(e));
			return null;
		}
		return null;
	}
	
	/**
	 * 景区提示框数据
	 * @param request
	 * @param model
	 * @param spotname
	 * @return
	 */
	@RequestMapping(value="/prompt")
	@ResponseBody
	public String getPrompt(HttpServletRequest request, Model model,
			@RequestParam(value="spotname",required=false)String spotname){
		HslMPScenicSpotQO mpScenicSpotsQO =new HslMPScenicSpotQO();
		mpScenicSpotsQO.setByName(true);
		mpScenicSpotsQO.setByArea(false);
		mpScenicSpotsQO.setContent(spotname);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(false);
		mpScenicSpotsQO.setPageSize(10);
		Map scenicSpotMap=new HashMap();
		try {
			scenicSpotMap = scenicSpotService.queryScenicSpot(mpScenicSpotsQO);
			List<PCScenicSpotDTO> scenicSpotDTOList = (List<PCScenicSpotDTO>) scenicSpotMap.get("dto");
			if(scenicSpotDTOList != null && scenicSpotDTOList.size() > 0){
//				List<ScenicSpotsDTO> list=new ArrayList<ScenicSpotsDTO>();
				List<Prompt> prompts = new ArrayList<Prompt>();
				Prompt prompt;
				for (PCScenicSpotDTO scenicSpotDTO : scenicSpotDTOList) {
					ScenicSpotsDTO dto=new ScenicSpotsDTO();
					dto.setName(scenicSpotDTO.getScenicSpotBaseInfo().getName());
//					list.add(dto);
					prompt = new Prompt();
					prompt.setLabel(scenicSpotDTO.getScenicSpotBaseInfo().getName());
					prompts.add(prompt);
				}
				return JSON.toJSONString(prompts);
			}
		} catch (MPException e) {
			HgLogger.getInstance().error("zhangka", "MPHotScenicSpotController->matchName->exception:" + HgLogger.getStackTrace(e));
			return "{[]}";
		}
		return "{[]}";
	}
	
	/**
	 * 更新热门景点
	 * @param request
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public String updateHotScenicSpot(HttpServletRequest request, Model model,
			@RequestParam(value="ids",required=false)String ids){
		String[] idList=ids.split(",");
		
		for (int i = 0; i < idList.length; i++) {
			//查询当前热门景点
			Map map=queryHotScenicSpot(1,20,true,null);
			List<HotScenicSpotDTO> list =(List<HotScenicSpotDTO>) map.get("dto");
			HslHotScenicSpotQO qo = new HslHotScenicSpotQO();
			qo.setId(idList[i]);
			HotScenicSpotDTO current = scenicSpotService.getScenicSpot(qo);
			HotScenicSpotDTO duplicate = null;
			
			boolean exist = false;
			//加判断是否已经存在热门景点中
			for(HotScenicSpotDTO hot : list){
				if(hot.getScenicSpotId().equals(current.getScenicSpotId())){
					exist = true;
					duplicate = hot;
					break;
				}
			}
			//如果存在就移除并添加为最新的景点
			if(exist){
				HslMPScenicSpotQO hotScenicSpotsQO = new HslMPScenicSpotQO();
				hotScenicSpotsQO.setHotScenicSpotId(duplicate.getId());
				hotScenicSpotsQO.setHot(true);
				hotScenicSpotsQO.setIsOpen(true);
				scenicSpotService.removeCurrentHotScenicSpot(hotScenicSpotsQO);
			}else{
				if(list.size() >= 6 || idList.length+list.size()>6){
					HslMPScenicSpotQO hotScenicSpotsQO = new HslMPScenicSpotQO();
					hotScenicSpotsQO.setHotScenicSpotId(list.get(list.size() - 1).getId());
					hotScenicSpotsQO.setHot(true);
					hotScenicSpotsQO.setIsOpen(true);
					// 把最旧的热门景点设置为非当前热门景点
					scenicSpotService.removeCurrentHotScenicSpot(hotScenicSpotsQO);
				}
			}
			
			ModifyHotScenicSpotCommand command=new ModifyHotScenicSpotCommand();
			command.setId(idList[i]);
			command.setOpenDate(new Date());
			scenicSpotService.modifyCurrentHotScenicSpot(command);
			
		}
		
		return DwzJsonResultUtil.createSimpleJsonString("200", "热门景点修改成功");
	}
	
	/**
	 * 通过slfx接口查询景区信息
	 * @param spotsname
	 * @return
	 */
	private List<PCScenicSpotDTO> queryScenicSpot(String spotsname){
		// 按照景区名称精确查询景区信息
		HslMPScenicSpotQO mpScenicSpotsQO = new HslMPScenicSpotQO();
		mpScenicSpotsQO.setByName(true);
		mpScenicSpotsQO.setByArea(false);
		mpScenicSpotsQO.setContent(spotsname);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(true);

		List<PCScenicSpotDTO> scenicSpotDTOList=new ArrayList<PCScenicSpotDTO>();
		Map scenicSpotMap = new HashMap();
		try {
			// slfx返回的景区信息
			scenicSpotMap = scenicSpotService.queryScenicSpot(mpScenicSpotsQO);

			scenicSpotDTOList= (List<PCScenicSpotDTO>) scenicSpotMap.get("dto");
		} catch (MPException e) {
			HgLogger.getInstance().error("zhangka", "MPHotScenicSpotController->queryScenicSpot->exception:" + HgLogger.getStackTrace(e));
			return null;
		}
		
		return scenicSpotDTOList;
	}
	
	
	private Map queryHotScenicSpot(Integer pageNo,  Integer pageSize,Boolean isOpen,String name){
		HslMPScenicSpotQO mpScenicSpotsQO=new HslMPScenicSpotQO();
		// 查询热门景点
		mpScenicSpotsQO.setHot(true);
		mpScenicSpotsQO.setPageNo(pageNo);
		mpScenicSpotsQO.setPageSize(pageSize);
		mpScenicSpotsQO.setIsOpen(isOpen);
		if(StringUtils.isNotBlank(name)){
			mpScenicSpotsQO.setContent(name);
			mpScenicSpotsQO.setByName(true);
		}
		// 当前热门景点列表
		Map map=new HashMap();
		try {
			map = scenicSpotService.queryScenicSpot(mpScenicSpotsQO);
		} catch (MPException e) {
			HgLogger.getInstance().error("zhangka", "MPHotScenicSpotController->queryHotScenicSpot->exception:" + HgLogger.getStackTrace(e));
		}

		
		return map;
	}
	
}

class Prompt{
	private String hex;
	private String label;
	private String rgb;
	public String getHex() {
		return hex;
	}
	public void setHex(String hex) {
		this.hex = hex;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getRgb() {
		return rgb;
	}
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
	
}