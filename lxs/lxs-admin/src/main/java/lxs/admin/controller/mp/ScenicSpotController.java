package lxs.admin.controller.mp;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.app.service.app.CarouselService;
import lxs.app.service.app.RecommendService;
import lxs.app.service.mp.DZPWCityService;
import lxs.app.service.mp.ScenicSpotPicService;
import lxs.app.service.mp.ScenicSpotSelectiveService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.app.service.mp.SyncDZPWService;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotSelective;
import lxs.pojo.command.mp.CreateScenicSpotSelectiveCommand;
import lxs.pojo.command.mp.DeleteScenicSpotSelectiveCommand;
import lxs.pojo.dto.mp.ScenicSpotDTO;
import lxs.pojo.qo.mp.ScenicSpotQO;
import lxs.pojo.qo.mp.ScenicSpotSelectiveQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("ticket")
public class ScenicSpotController {

	@Autowired
	private DZPWCityService dzpwCityService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotPicService scenicSpotPicService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private SyncDZPWService syncDZPWService;
	@Autowired
	private ScenicSpotSelectiveService scenicSpotSelectiveService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private CarouselService carouselService;
	
	/**
	 * @Title: ticketList 
	 * @author guok
	 * @Description: 景区门票列表
	 * @Time 2016年3月4日上午9:50:26
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return String 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/ticketList")
	public String ticketList(HttpServletRequest request,
			HttpServletResponse response, Model model,@ModelAttribute ScenicSpotQO scenicSpotQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入");
		HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入pageSize"+pageSize);
		HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入pageNo"+pageNo);
		HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入+[scenicSpotQO]："+JSON.toJSONString(scenicSpotQO));
		
		
		if (pageNo == null) {
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入if");
			pageNo = 1;
		}
		if (pageSize == null) {
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入if");
			pageSize = 20;
		}
		
		try {
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入try");
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入+[scenicSpotQO]："+JSON.toJSONString(scenicSpotQO));
			Pagination pagination = new Pagination();
			pagination.setCondition(scenicSpotQO);
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入查");
			pagination = scenicSpotService.queryPagination(pagination);
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入查完");
			List<ScenicSpotDTO> scenicSpotList = EntityConvertUtils.convertEntityToDtoList(
					(List<ScenicSpotDTO>) pagination.getList(), ScenicSpotDTO.class);
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入转完");
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表进入+[scenicSpotList]："+JSON.toJSONString(scenicSpotList));
			if (scenicSpotList!=null||scenicSpotList.size()>0) {
				model.addAttribute("scenicSpotList", scenicSpotList);
			}
			
			model.addAttribute("scenicSpotQO", scenicSpotQO);
			model.addAttribute("pagination", pagination);
		} catch (Exception e) {
			HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表"
					+ e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev","【ticketList】" + "景区列表跳转");
		return "/ticket/ticketList.html";
	}
	
	/**
	 * 
	 * @方法功能说明：同步景区
	 * @修改者名字：cangs
	 * @修改时间：2016年3月3日下午1:41:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/SyncScenicSpot")
	@ResponseBody
	public String SyncScenicSpot(HttpServletRequest request,HttpServletResponse response){
		//同步省市村
		new Thread(){
			public void run() {
				String result = syncDZPWService.syncRegion();
				if(StringUtils.equals(result,"success")){
					syncDZPWService.syncScenicSpot();
				}
			};
		}.start();
		return DwzJsonResultUtil.createSimpleJsonString("200", "正在同步，请稍等");
	}
	
	/**
	 * @Title: mpSelectiveList 
	 * @author guok
	 * @Description: 景区精选列表
	 * @Time 2016年3月4日下午3:49:53
	 * @param request
	 * @param model
	 * @param scenicSpotSelectiveQO
	 * @param pageNo
	 * @param pageSize
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/mpSelectiveList")
	public String mpSelectiveList(HttpServletRequest request,
			Model model,
			@ModelAttribute ScenicSpotSelectiveQO scenicSpotSelectiveQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		if(StringUtils.isNotBlank(scenicSpotSelectiveQO.getBeginTime()) && StringUtils.isBlank(scenicSpotSelectiveQO.getEndTime())){
			scenicSpotSelectiveQO.setEndTime(scenicSpotSelectiveQO.getBeginTime());
		}
		if(StringUtils.isBlank(scenicSpotSelectiveQO.getBeginTime()) && StringUtils.isNotBlank(scenicSpotSelectiveQO.getEndTime())){
			scenicSpotSelectiveQO.setBeginTime(scenicSpotSelectiveQO.getEndTime());
		}
		Pagination pagination = new Pagination();
		pagination.setCondition(scenicSpotSelectiveQO);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination = scenicSpotSelectiveService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("scenicSpotSelectiveQO",scenicSpotSelectiveQO);
		return "/ticket/scenicSpotSelectiveList.html";
	}
	
	/**
	 * @Title: createScenicSpotSelective 
	 * @author guok
	 * @Description: 设置景区精选
	 * @Time 2016年3月4日下午4:56:14
	 * @param request
	 * @param scenicSpotID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/selective")
	@ResponseBody
	public String createScenicSpotSelective(HttpServletRequest request,
			@RequestParam(value = "id") String scenicSpotID){
		HgLogger.getInstance().info("lxs_dev","【createScenicSpotSelective】" + "景区ID"
						+ scenicSpotID);
		//设置优选
		ScenicSpotSelectiveQO scenicSpotSelectiveQO = new ScenicSpotSelectiveQO();
		scenicSpotSelectiveQO.setScenicSpotID(scenicSpotID);
		if(scenicSpotSelectiveService.queryCount(scenicSpotSelectiveQO)==0){
			CreateScenicSpotSelectiveCommand command = new CreateScenicSpotSelectiveCommand();
			command.setScenicSpotId(scenicSpotID);
			//2:景区
			command.setType("2");
			//产品名称
			command.setName(scenicSpotService.get(scenicSpotID).getBaseInfo().getName());
			scenicSpotSelectiveService.createScenicSpotSelective(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "设置精选成功",null,"ticketList");
		}else{
			return DwzJsonResultUtil.createSimpleJsonString("500", "该景区已经是精选线路");
		}
	}
	
	/**
	 * @Title: deleteScenicSpotSelective 
	 * @author guok
	 * @Description: 取消精选
	 * @Time 2016年3月4日下午5:08:36
	 * @param request
	 * @param lineSelectiveID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/deleteScenicSpotSelective")
	@ResponseBody
	public String deleteScenicSpotSelective(HttpServletRequest request,
			@RequestParam(value = "id") String scenicSpotSelectiveID){
		HgLogger.getInstance().info("lxs_dev","【deleteScenicSpotSelective】" + "精选ID"
				+ scenicSpotSelectiveID);
		ScenicSpotSelective ScenicSpotSelective = scenicSpotSelectiveService.get(scenicSpotSelectiveID);
		ScenicSpotSelective ScenicSpotSelective2 = null;
		if(ScenicSpotSelective.getSort()==1&&scenicSpotSelectiveService.getMaxSort()!=1){
			ScenicSpotSelectiveQO ScenicSpotSelectiveQO= new ScenicSpotSelectiveQO();
			ScenicSpotSelectiveQO.setForSale(1);
			ScenicSpotSelectiveQO.setSort(ScenicSpotSelective.getSort()+1);
			ScenicSpotSelective2=scenicSpotSelectiveService.queryUnique(ScenicSpotSelectiveQO);
			int i=1;
			while(ScenicSpotSelective2==null){
				ScenicSpotSelectiveQO.setSort(ScenicSpotSelective.getSort()+1+i);
				ScenicSpotSelective2=scenicSpotSelectiveService.queryUnique(ScenicSpotSelectiveQO);
				i++;
			}
			ScenicSpotSelective2.setSort(1);
			scenicSpotSelectiveService.update(ScenicSpotSelective2);
		}
		DeleteScenicSpotSelectiveCommand deleteScenicSpotSelectiveCommand = new DeleteScenicSpotSelectiveCommand();
		deleteScenicSpotSelectiveCommand.setId(scenicSpotSelectiveID);
		scenicSpotSelectiveService.deleteScenicSpotSelective(deleteScenicSpotSelectiveCommand);
		return DwzJsonResultUtil.createSimpleJsonString("200", "取消成功");
	}
	
	/**
	 * @Title: editStatus 
	 * @author guok
	 * @Description: 修改景区状态
	 * @Time 2016年3月7日下午3:27:59
	 * @param request
	 * @param scenicSpotID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/editStatus")
	@ResponseBody
	public String editStatus(HttpServletRequest request,
			@RequestParam(value = "id") String scenicSpotID) {
		HgLogger.getInstance().info("lxs_dev","【editStatus】" + "景区ID修改状态"
				+ scenicSpotID);
		try {
			ScenicSpot scenicSpot = scenicSpotService.get(scenicSpotID);
			if (scenicSpot != null) {
				if (scenicSpot.getLocalStatus() == ScenicSpot.SHOW) {
					scenicSpot.setLocalStatus(ScenicSpot.HIDDEN);
				}else if (scenicSpot.getLocalStatus() == ScenicSpot.HIDDEN) {
					scenicSpot.setLocalStatus(ScenicSpot.SHOW);
				}
				scenicSpotService.update(scenicSpot);
			}
		} catch (Exception e) {
			HgLogger.getInstance().info("lxs_dev","【editStatus】" + "景区ID修改状态失败"
					+ scenicSpotID);
			DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, "修改失败!", null, "ticketList");
		}
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "修改成功!", null, "ticketList");
	}
	
	/**
	 * @Title: stickit 
	 * @author guok
	 * @Description: 景区置顶
	 * @Time 2016年3月7日下午3:45:34
	 * @param request
	 * @param scenicSpotID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/stickit")
	@ResponseBody
	public String scenicSpotStickit(HttpServletRequest request,
			@RequestParam(value = "id") String scenicSpotID, @RequestParam(value = "status") String status) {
		HgLogger.getInstance().info("lxs_dev","【scenicSpotStickit】" + "景区ID"
				+ scenicSpotID);
		try {
			scenicSpotService.stickit(scenicSpotID,status);
		} catch (Exception e) {
			HgLogger.getInstance().info("lxs_dev","【editStatus】" + "景区ID设置置顶失败"
					+ scenicSpotID);
			return DwzJsonResultUtil.createJsonString(
					"500", "设置失败!", null, "ticketList");
		}
		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "设置成功!", null, "ticketList");
	}
	
	/**
	 * @Title: changeSort 
	 * @author guok
	 * @Description: 精选上下移动
	 * @Time 2016年3月8日下午5:12:22
	 * @param request
	 * @param scenicSpotSelectiveID
	 * @param type
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/changeSort")
	@ResponseBody
	public String changeSort(HttpServletRequest request,
			@RequestParam(value = "id") String scenicSpotSelectiveID,
			@RequestParam(value = "type") String type){
		
		HgLogger.getInstance().info("lxs_dev","【changeSort】" + "景区精选ID"
				+ scenicSpotSelectiveID);
		
		ScenicSpotSelective scenicSpotSelective = scenicSpotSelectiveService.get(scenicSpotSelectiveID);
		ScenicSpotSelectiveQO scenicSpotSelectiveQO = new ScenicSpotSelectiveQO();
		if(scenicSpotSelective.getSort()==1&&StringUtils.equals("down", type)){
			return DwzJsonResultUtil.createSimpleJsonString("500", "该景区已在最末尾");
		}else if(scenicSpotSelective.getSort()==scenicSpotSelectiveService.getMaxSort()&&StringUtils.equals("up", type)){
			return DwzJsonResultUtil.createSimpleJsonString("500", "该景区已在最第一位");
		}else{
			if(StringUtils.equals("up", type)){
				int sort_a=scenicSpotSelective.getSort();
				int sort_b=0;
				scenicSpotSelectiveQO.setSort(scenicSpotSelective.getSort()+1);
				ScenicSpotSelective scenicSpotSelective2=scenicSpotSelectiveService.queryUnique(scenicSpotSelectiveQO);
				int i=1;
				while(scenicSpotSelective2==null){
					scenicSpotSelectiveQO.setSort(scenicSpotSelective.getSort()+1+i);
					scenicSpotSelective2=scenicSpotSelectiveService.queryUnique(scenicSpotSelectiveQO);
					i++;
				}
				sort_b=scenicSpotSelective2.getSort();
				scenicSpotSelective.setSort(sort_b);
				scenicSpotSelective2.setSort(sort_a);
				scenicSpotSelectiveService.update(scenicSpotSelective);
				scenicSpotSelectiveService.update(scenicSpotSelective2);
				return  DwzJsonResultUtil.createJsonString(
						DwzJsonResultUtil.STATUS_CODE_200, "上移成功!", null, "ticketSelectiveList");
			}else{
				int sort_a=scenicSpotSelective.getSort();
				int sort_b=0;
				scenicSpotSelectiveQO.setSort(scenicSpotSelective.getSort()-1);
				ScenicSpotSelective scenicSpotSelective2=scenicSpotSelectiveService.queryUnique(scenicSpotSelectiveQO);
				int i=1;
				while(scenicSpotSelective2==null){
					scenicSpotSelectiveQO.setSort(scenicSpotSelective.getSort()-1-i);
					scenicSpotSelective2=scenicSpotSelectiveService.queryUnique(scenicSpotSelectiveQO);
					i++;
				}
				sort_b=scenicSpotSelective2.getSort();
				scenicSpotSelective.setSort(sort_b);
				scenicSpotSelective2.setSort(sort_a);
				scenicSpotSelectiveService.update(scenicSpotSelective);
				scenicSpotSelectiveService.update(scenicSpotSelective2);
				return  DwzJsonResultUtil.createJsonString(
						DwzJsonResultUtil.STATUS_CODE_200, "下移成功!", null, "ticketSelectiveList");
			}
		}
	}
	/**
	 * 
	 * @方法功能说明：获取景区版本号
	 * @修改者名字：cangs
	 * @修改时间：2016年3月3日下午1:40:51
	 * @修改内容：
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public int getScenicSpotVersion(){
		int scenicSpotVersion = 0;
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		String redisScenicSpotVersion=jedis.get("SUIXINYOU_"+"SCENICSPOT_"+"VERSION");
		if(!StringUtils.isBlank(redisScenicSpotVersion)){
			scenicSpotVersion = Integer.parseInt(redisScenicSpotVersion);
		}
		int theNextScenicSpotVersion = scenicSpotVersion+1;
		jedis.set("SUIXINYOU_"+"SCENICSPOT_"+"VERSION",String.valueOf(theNextScenicSpotVersion));
		jedisPool.returnResource(jedis);
		return scenicSpotVersion;
	}
	
	/**
	 * @Title: queryDatail 
	 * @author guok
	 * @Description: 景区详情
	 * @Time 2016年3月9日下午1:55:52
	 * @param request
	 * @param scenicSpotID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/queryDatail")
	public String queryDatail(HttpServletRequest request,Model model,
			@RequestParam(value = "id") String scenicSpotID) {
		
		ScenicSpot scenicSpot = scenicSpotService.get(scenicSpotID);
		model.addAttribute("scenicSpot", scenicSpot);
		
		return "/ticket/ticket_detail.html";
	}
}