package lxs.api.controller.mp;

import java.util.ArrayList;
import java.util.List;

import lxs.api.controller.BaseController;
import lxs.app.service.mp.GroupTicketService;
import lxs.app.service.mp.ScenicSpotPicService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.domain.model.mp.GroupTicket;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotPic;
import lxs.pojo.qo.mp.ScenicSpotPicQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mp")
public class MPController extends BaseController{

	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotPicService scenicSpotPicService;
	@Autowired
	private GroupTicketService groupTicketService;
	
	@RequestMapping("scenicspot/detail")
	public ModelAndView scenicSpotDetail(@RequestParam(value="scenicSpotID",required=true) String scenicSpotID){
		ModelAndView mav = new ModelAndView("mp/scenicspot");
		ScenicSpot scenicSpot = scenicSpotService.get(scenicSpotID);
		mav.addObject("scenicSpot", scenicSpot);
		return mav;
	}
	
	@RequestMapping("groupticket/detail")
	public ModelAndView groupTicketDetail(@RequestParam(value="ticketPolicyID",required=true) String ticketPolicyID){
		ModelAndView mav = new ModelAndView("mp/groupticket");
		GroupTicket groupTicket = groupTicketService.get(ticketPolicyID);
		mav.addObject("groupTicket", groupTicket);
		List<ScenicSpotPic> scenicSpotPics = new ArrayList<ScenicSpotPic>();
		if(groupTicket!=null&&StringUtils.isNotBlank(groupTicket.getScenicSpotID())){
			String[] scenicSpotIDs = groupTicket.getScenicSpotID().split(";");
			for (String scenicSpotID : scenicSpotIDs) {
				ScenicSpotPicQO  scenicSpotPicQO = new ScenicSpotPicQO();
				scenicSpotPicQO.setScenicSpotID(scenicSpotID);
				scenicSpotPics.addAll(scenicSpotPicService.queryList(scenicSpotPicQO));
			}
		}
		mav.addObject("scenicSpotPics", scenicSpotPics);
		return mav;
	}
}
