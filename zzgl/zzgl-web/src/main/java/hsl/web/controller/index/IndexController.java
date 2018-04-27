package hsl.web.controller.index;
import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.MD5HashUtil;
import hg.log.util.HgLogger;
import hsl.domain.model.notice.NoticeStatus;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.dto.line.ad.LineIndexAdDTO;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.notice.NoticeDTO;
import hsl.pojo.dto.preferential.PreferentialDTO;
import hsl.pojo.dto.programa.ProgramaDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.ad.HslAdQO;
import hsl.pojo.qo.line.ad.LineIndexAdQO;
import hsl.pojo.qo.notice.HslNoticeQO;
import hsl.pojo.qo.preferential.HslPreferentialQO;
import hsl.pojo.qo.programa.HslProgramaQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.ad.HslAdPositionService;
import hsl.spi.inter.ad.HslAdService;
import hsl.spi.inter.line.ad.HslLineIndexAdService;
import hsl.spi.inter.mp.MPScenicSpotService;
import hsl.spi.inter.notice.NoticeService;
import hsl.spi.inter.preferential.HslPreferentialService;
import hsl.spi.inter.programa.HslProgramaService;
import hsl.spi.inter.user.UserService;
import hsl.web.controller.BaseController;
import hsl.web.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class IndexController extends BaseController {
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private UserService userService;
	@Resource
	private HslAdService hslAdService;
	@Resource
	private MPScenicSpotService scenicSpotService;
	@Resource
	private NoticeService noticeService;
	@Resource
	private HslAdPositionService hslAdPositionService;
	
	@Autowired
	private HslLineIndexAdService hslLineIndexAdService;
	@Autowired
	private HslProgramaService hslProgramaService;
	@Autowired
	private HslPreferentialService hslPreferentialService;
	
	/**
	 * @方法功能说明：跳转到主页
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月28日上午10:18:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/home")
	public String index(HttpServletRequest request,Model model){
		hgLogger.info("chenxy", "跳转到主页");
		Map hotScenicMap ;
		try {	
			/*********************顶部广告***********************/
			Pagination toppage = new Pagination();
			toppage.setPageNo(1);
			toppage.setPageSize(getShowNo(HslAdConstant.TOP));
			HslAdQO topAdQO = new HslAdQO();
			topAdQO.setPositionId(HslAdConstant.TOP);
			topAdQO.setIsShow(true);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
			toppage.setCondition(topAdQO);
			toppage = hslAdService.queryPagination(toppage);
			//多个页面共用顶部广告
			if(toppage.getList()!=null && toppage.getList().size()>0){
				model.addAttribute("topList", toppage.getList().get(0));
			}
			
			/*********************首页轮播***********************/
			Pagination adpage = new Pagination();
			adpage.setPageNo(1);
			adpage.setPageSize(getShowNo(HslAdConstant.AD));
			HslAdQO hslAdQO = new HslAdQO();
			hslAdQO.setPositionId(HslAdConstant.AD);
			hslAdQO.setIsShow(true);
			adpage.setCondition(hslAdQO);
			adpage = hslAdService.queryPagination(adpage);
			model.addAttribute("adList", adpage.getList());
			
			//查询公告列表
			HslNoticeQO noticeQO=new HslNoticeQO();
			noticeQO.setCheckedStatus(NoticeStatus.CHECKEDSTATUS_PASSCHECK);
			noticeQO.setCutOffTime(DateUtil.formatDateTime(new Date()));
			List<NoticeDTO> noticeDTOs=noticeService.queryNoticeTop(noticeQO,4);
			model.addAttribute("noticeDTOs", noticeDTOs);
			
			//热门景点查询
			/*HslMPScenicSpotQO hotMpScenicSpotsQO = new HslMPScenicSpotQO();
			hotMpScenicSpotsQO.setHot(true);
			hotMpScenicSpotsQO.setPageNo(1);
			hotMpScenicSpotsQO.setPageSize(6);
			hotMpScenicSpotsQO.setIsOpen(true);
			hotMpScenicSpotsQO.setContent(null);
		
			hotScenicMap = scenicSpotService.queryScenicSpot(hotMpScenicSpotsQO);
			model.addAttribute("hotScenicMap", hotScenicMap);*/
			
			
			/*********************热门景点首图***********************/
			/*Pagination hotFirstpage = new Pagination();
			hotFirstpage.setPageNo(1);
			hotFirstpage.setPageSize(getShowNo(HslAdConstant.HOTFIRST));
			HslAdQO hslHotFirstQO = new HslAdQO();
			hslHotFirstQO.setPositionId(HslAdConstant.HOTFIRST);
			hslHotFirstQO.setIsShow(true);
			hotFirstpage.setCondition(hslHotFirstQO);
			hotFirstpage = hslAdService.queryPagination(hotFirstpage);
			if(hotFirstpage.getList() != null && hotFirstpage.getList().size()>0){
				model.addAttribute("hotFirst", hotFirstpage.getList().get(0));
			}*/
			
			/*********************热门景点***********************/
			/*Pagination hotp = new Pagination();
			hotp.setPageNo(1);
			hotp.setPageSize(20);
			HslPCHotScenicSpotQO hotScenicSpotQO = new HslPCHotScenicSpotQO();
			hotScenicSpotQO.setIsShow(true);
			hotp.setCondition(hotScenicSpotQO);
			hotp = scenicSpotService.getHotScenicSpots(hotp);
			model.addAttribute("hotList", hotp.getList());
			hgLogger.info("zhuxy", newLog("首页热门景点", JSON.toJSONString(hotp.getList())));*/
			
			/*********************侧边栏广告***********************/
			Pagination sidepage = new Pagination();
			sidepage.setPageNo(1);
			sidepage.setPageSize(getShowNo(HslAdConstant.SIDE));
			HslAdQO hslSideQO = new HslAdQO();
			hslSideQO.setPositionId(HslAdConstant.SIDE);
			hslSideQO.setIsShow(true);
			sidepage.setCondition(hslSideQO);
			sidepage = hslAdService.queryPagination(sidepage);
			model.addAttribute("sideList", sidepage.getList());
			
			/*********************合作伙伴***********************/
			Pagination teamAdpage = new Pagination();
			HslAdQO hslteamAdQO = new HslAdQO();
			hslteamAdQO.setPositionId(HslAdConstant.TEAMAD);
			hslteamAdQO.setIsShow(true);
			teamAdpage.setCondition(hslteamAdQO);
			teamAdpage = hslAdService.queryPagination(teamAdpage);
			model.addAttribute("teamAdList", teamAdpage.getList());
			/*********************特价门票栏目***********************/
			/*HslProgramaQO qoMp=new HslProgramaQO();
			qoMp.setId("3619a6b5-7ac4-4ae6-b62c-ae50e6ba6a52");
			ProgramaDTO programaDTO1=this.hslProgramaService.queryUnique(qoMp);
			model.addAttribute("mpProgramaContentList", programaDTO1.getProgramaContentList());*/
			/*********************特价门票***********************/
/*			TreeMap maps=new TreeMap<String, Object>();
			HslSpecOfferMpQO mpQO = new HslSpecOfferMpQO();
	
			mpQO.setIsShow(true);
			Pagination pagination = new Pagination();
			pagination.setPageNo(1);
			pagination.setPageSize(35);
			pagination.setCondition(mpQO);
			mpQO.setPositionId("hsl_tjmp");
			mpQO.setContentId("a32c016f87f3440b9adffcf6c6d97a67");
			pagination = scenicSpotService.getSpecialList(pagination);
			//model.addAttribute("SQQS", pagination.getList());
			maps.put("B", pagination.getList());
			HgLogger.getInstance().info("查询特价门票美景人文", JSON.toJSONString(pagination.getList()));
			
			mpQO.setContentId("c876c53b2e274b06b8949a17ace5fb16");
			pagination = scenicSpotService.getSpecialList(pagination);
			//model.addAttribute("MJRW", pagination.getList());
			maps.put("C", pagination.getList());
			HgLogger.getInstance().info("查询特价门票古镇迷情 ", JSON.toJSONString(pagination.getList()));
			
			mpQO.setContentId("ec3a096d13e04c9eb30520b234ec6ff6");
			pagination = scenicSpotService.getSpecialList(pagination);
			//model.addAttribute("GZMQ", pagination.getList());
			maps.put("D", pagination.getList());
			HgLogger.getInstance().info("查询特价门票山水园林 ", JSON.toJSONString(pagination.getList()));
			
			mpQO.setContentId("fa132c1dd9af498580276d5af4b0454d");
			pagination = scenicSpotService.getSpecialList(pagination);
			//model.addAttribute("SSYL", pagination.getList());
			maps.put("E", pagination.getList());
			HgLogger.getInstance().info("查询特价门票约泡温泉", JSON.toJSONString(pagination.getList()));
			
			mpQO.setContentId("4ecf275b4772461888ae28f61f84eacc");
			pagination = scenicSpotService.getSpecialList(pagination);
			model.addAttribute("HslAdConstant", pagination.getList());
			//model.addAttribute("ZTLY", pagination.getList());
			maps.put("A", pagination.getList());
			HgLogger.getInstance().info("查询特价门票主题乐园", JSON.toJSONString(pagination.getList()));
			
			model.addAttribute("maps", maps);*/
			
			/*********************热门预订***********************//*
			HslRankListQO rankListQO = new HslRankListQO();
			rankListQO.setPageSize(10);
			List<MPSimpleDTO> rank = scenicSpotService.queryScenicSpotClickRate(rankListQO);
			if(rank!=null&&rank.size()>0){
				HgLogger.getInstance().info("chenxy", "查询景点点击排行"+JSON.toJSONString(rank));
			}else{
				HgLogger.getInstance().info("chenxy", "查询景点点击排行>>>为空");
			}
			if(rank!=null&&rank.size()>0){
				HslMPScenicSpotQO hslMPScenicSpotQO = new HslMPScenicSpotQO();
				hslMPScenicSpotQO.setImagesFetchAble(true);
				for(MPSimpleDTO mpSimpleDTO : rank){
					hslMPScenicSpotQO.setScenicSpotId(mpSimpleDTO.getScenicSpotId());
					Map map = scenicSpotService.queryScenicSpot(hslMPScenicSpotQO);
					List<PCScenicSpotDTO> list = (List<PCScenicSpotDTO>)map.get("dto");
					if(list!=null&&list.size()>0){
						try{
							mpSimpleDTO.setImageUrl(list.get(0).getImages().get(0).getUrl());
						}catch(NullPointerException e){
							e.printStackTrace();
							hgLogger.debug("zhuxy",newLog("查询热门预订", "出现空指针"+HgLogger.getStackTrace(e)));
						}
					}
				}
			}
			model.addAttribute("rank", rank);*/
			
			/*********************周边游***********************/
			Pagination zbyPage = new Pagination();
			zbyPage.setPageNo(1);
			zbyPage.setPageSize(5);
			LineIndexAdQO lineIndexAdQO=new LineIndexAdQO();
			lineIndexAdQO.setPositionId("zzgl_linead_zby");
			lineIndexAdQO.setLineType(1);
			zbyPage.setCondition(lineIndexAdQO);
			zbyPage = hslLineIndexAdService.queryLineIndexAds(zbyPage);
			List<LineIndexAdDTO> zbyList=(List<LineIndexAdDTO>) zbyPage.getList();
			model.addAttribute("zbyList", zbyList);
			
			//周边游栏目内容
			HslProgramaQO qo=new HslProgramaQO();
			qo.setId("24df401113eb40bebd313a2fe7a72de5");
			ProgramaDTO programaDTO=this.hslProgramaService.queryUnique(qo);
			model.addAttribute("zbyProgramaContentList", programaDTO.getProgramaContentList());

			//跟团游栏目内容
			HslProgramaQO qo2=new HslProgramaQO();
			qo2.setId("f400b5510ca44851a12f9f49e048b9c8");
			ProgramaDTO programaDTO2=this.hslProgramaService.queryUnique(qo2);
			model.addAttribute("gtyProgramaContentList", programaDTO2.getProgramaContentList());

			/*********************跟团游***********************/
			//Map<String,Object> linemap=new HashMap<String, Object>();
			Pagination gtyPage = new Pagination();
			gtyPage.setPageNo(1);
			gtyPage.setPageSize(5);
			LineIndexAdQO lineIndexAdQO2=new LineIndexAdQO();
			lineIndexAdQO2.setPositionId("zzgl_linead_gty");
			lineIndexAdQO2.setLineType(2);
			//lineIndexAdQO2.setProgramaId(inlandId);
			gtyPage.setCondition(lineIndexAdQO2);
			gtyPage = hslLineIndexAdService.queryLineIndexAds(gtyPage);
			List<LineIndexAdDTO> gtyList=(List<LineIndexAdDTO>) gtyPage.getList();
			model.addAttribute("gtyList", gtyList);
			
			/*********************特惠专区***********************/
			HslPreferentialQO hslPreferentialQO=new HslPreferentialQO();
			List<PreferentialDTO> prelist=hslPreferentialService.queryList(hslPreferentialQO);
			model.addAttribute("prelist", prelist);
		} catch (Exception e) {
			hotScenicMap = new HashMap();
			hotScenicMap.put("dto",new ArrayList<HotScenicSpotDTO>());
			hotScenicMap.put("count", 0);
			model.addAttribute("hotScenicMap", hotScenicMap);
			e.printStackTrace();
			HgLogger.getInstance().error("zhangka", "IndexController->index->exception:" + HgLogger.getStackTrace(e));
		}
		return "/index.html";
	}
	
	private int getShowNo(String positionId){
		try {
			int showNo=0;
			HslAdPositionQO hslAdPositionQO = new HslAdPositionQO();
			hslAdPositionQO.setPositionId(positionId);
			HslAdPositionDTO hslAdPositionDTO = hslAdPositionService.queryAdPosition(hslAdPositionQO);
			if(hslAdPositionDTO!=null&&hslAdPositionDTO.getShowInfo()!=null){
				showNo=hslAdPositionDTO.getShowInfo().getShowNo();
				HgLogger.getInstance().info("yuqz", "IndexController->getShowNo->查询广告位信息失败:" + JSON.toJSONString(showNo));
			}
			return showNo;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("yuqz", "IndexController->getShowNo->查询广告位信息失败:" + HgLogger.getStackTrace(e));
			return 0;
		}
		
		
	}
	
	/**
	 * @方法功能说明：跳转到登录页面
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月25日上午11:19:25
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/comLogin")
	public String toLogin(HttpServletRequest request,HttpServletResponse response,Model model){
		hgLogger.info("chenxy", "跳转企业用户登录页面");
		model.addAttribute("index", "1");
		return "company/login.html";
	}
	/**
	 * @方法功能说明：登录
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月25日上午11:19:42
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param hslUserQO
	 * @参数：@param attr
	 * @参数：@return
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping("/comLogin/login")
	public RedirectView companyLogin(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute HslUserQO hslUserQO,RedirectAttributes attr){
		String authcode=request.getParameter("authcode");
		String sessionCode=(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		hgLogger.info("chenxy",hslUserQO.getLoginName()+"企业用户登录");
		attr.addFlashAttribute("password2", request.getParameter("password"));
		attr.addFlashAttribute("hslUserQ", hslUserQO);
		attr.addFlashAttribute("index", "1");
		if(null!=sessionCode&&sessionCode.equalsIgnoreCase(authcode)){
			try {
				//设置企业类型
				hslUserQO.setType(2);
				hslUserQO.setPassword(MD5HashUtil.toMD5(hslUserQO.getPassword()));
				UserDTO userDTO=userService.userCheck(hslUserQO);
				//将用户放入SESSION中
				hgLogger.info("chenxy",hslUserQO.getLoginName()+"企业用户登录成功");
				putUserToSession(request, userDTO);
			} catch (UserException e) {
				hgLogger.info("chenxy",hslUserQO.getLoginName()+"企业用户登录失败"+e.getMessage());
				HgLogger.getInstance().error("chenxy", "IndexController->companyLogin->exception:" + HgLogger.getStackTrace(e));
				attr.addFlashAttribute("perror", e.getMessage());
				return new RedirectView("/comLogin",true);
			}
		}else{
			hgLogger.error("chenxy",hslUserQO.getLoginName()+"企业用户登录验证码错误");
			attr.addFlashAttribute("pcodeError", "验证码错误");
			return new RedirectView("/comLogin",true);
		}
		String redirectUrl=(String) request.getSession().getAttribute("redirectUrl");
		if(StringUtils.isNotBlank(redirectUrl)){
			//跳转后将重定向地址session删除
			request.getSession().removeAttribute("redirectUrl");
			return new RedirectView(redirectUrl,true);
		}
		return new RedirectView("/home",true);
	}
	@RequestMapping("/exist")
	public RedirectView exist(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute HslUserQO hslUserQO,RedirectAttributes attr){
		hgLogger.info("chenxy",hslUserQO.getLoginName()+"企业用户注销");
		exitUser(request);
		return new RedirectView("/user/login",true);
	}
	/**
	 * @方法功能说明：跳转邮箱地址
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月28日下午3:26:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param mail
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/redirctToMail")
	public RedirectView redirctToMail(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(required=false) String mail){
			String[] mails=mail.split("@");
			String suffix=mails[1];
			String mailHost="";
			if(suffix.equalsIgnoreCase("sina.com")){
				mailHost="https://mail.sina.com.cn";
			}else if(suffix.equalsIgnoreCase("sina.com.cn")){
				mailHost="https://mail.sina.com.cn";
			}else if(suffix.equalsIgnoreCase("sina.cn")){
				mailHost="https://mail.sina.com.cn";
			}else{
				mailHost="http://mail."+suffix;
			}
		return new RedirectView(mailHost,false);
	}
	@RequestMapping(value = "/test")
	public String test(HttpServletRequest request,Model model){
		return "public/fotter/test.html";
	}
	@RequestMapping(value = "/error")
	public String error(){
		return "error/tokenError.html";
	}
	/**
	 * @方法功能说明：查询公告的详细信息
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月24日下午2:23:12
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/noticeDetails")
	public String queryNoticeDeatil(@RequestParam(value="id",required=false) String id,Model model){
		HslNoticeQO noticeQO=new HslNoticeQO();
		noticeQO.setId(id);
		NoticeDTO noticeDto=noticeService.queryUnique(noticeQO);
		model.addAttribute("noticeDto",noticeDto);
		return "notice/notice_details.html";
	}
	@RequestMapping(value = "/home_gp")
	public String indexgp(HttpServletRequest request,Model model){
		model.addAttribute("system",CommonUtil.getWebAppPath());
		return "/index_gp.html";
	}

	/**
	 * 
	 * @方法功能说明：跟团游动态加载
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月4日上午10:18:14
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/selecttravel")
	@ResponseBody
	public String selecttravel(HttpServletRequest request, HttpServletResponse response,
			   Model model){
		Pagination gtyPage = new Pagination();
		try {
			String id=request.getParameter("id");
			gtyPage.setPageNo(1);
			gtyPage.setPageSize(100);
			LineIndexAdQO lineIndexAdQO2=new LineIndexAdQO();
			lineIndexAdQO2.setPositionId("zzgl_linead_gty");
			lineIndexAdQO2.setLineType(2);
			lineIndexAdQO2.setContentId(id);
			gtyPage.setCondition(lineIndexAdQO2);
			gtyPage = hslLineIndexAdService.queryLineIndexAds(gtyPage);
			model.addAttribute("gtyPage", gtyPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(gtyPage);
	}
	/**
	 * 
	 * @方法功能说明：周边游动态加载
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月4日上午10:29:47
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/selecttour")
	@ResponseBody
	public String selecttour(HttpServletRequest request, HttpServletResponse response,
			   Model model){
		Pagination zbyPage = new Pagination();
		try {
			String id=request.getParameter("id");
			zbyPage.setPageNo(1);
			zbyPage.setPageSize(100);
			LineIndexAdQO lineIndexAdQO=new LineIndexAdQO();
			lineIndexAdQO.setPositionId("zzgl_linead_zby");
			lineIndexAdQO.setLineType(1);
			lineIndexAdQO.setContentId(id);
			zbyPage.setCondition(lineIndexAdQO);
			zbyPage = hslLineIndexAdService.queryLineIndexAds(zbyPage);
			model.addAttribute("zbyPage", zbyPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(zbyPage);
	}
	/**
	 * 
	 * @方法功能说明：友情链接
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-16下午4:05:38
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/footLink")
	public String footLink(HttpServletRequest request,Model model){
		try {
			//查询文字广告
			Pagination adpage = new Pagination();
			adpage.setPageNo(1);
			adpage.setPageSize(getShowNo(HslAdConstant.FRIENDSHIHC));
			HslAdQO hslAdQO = new HslAdQO();
			hslAdQO.setPositionId(HslAdConstant.FRIENDSHIHC);
			hslAdQO.setIsShow(true);
			adpage.setCondition(hslAdQO);
			adpage = hslAdService.queryPagination(adpage);
			hgLogger.info("zhaows", newLog("查询友情文字连接", JSON.toJSONString(adpage.getList())));
			model.addAttribute("friendshihc", adpage.getList());//返回文字连接
			Pagination adpage1 = new Pagination();
			adpage1.setCondition(hslAdQO);
			adpage1.setPageSize(getShowNo(HslAdConstant.FRIENDSHIPIC));
			hslAdQO.setPositionId(HslAdConstant.FRIENDSHIPIC);
			adpage1 = hslAdService.queryPagination(adpage1);
			hgLogger.info("zhaows", newLog("查询友情图片连接", JSON.toJSONString(adpage1.getList())));
			model.addAttribute("friendshipic", adpage1.getList());//返回图片连接
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.debug("zhaows", newLog("查询友情连接失败", HgLogger.getStackTrace(e)));
		}
		
		return "activity/foot_link.html";
	}
	/**
	 * 
	 * @方法功能说明：跳转到活动页面
	 * @创建者名字：zhaows
	 * @创建时间：2015年5月29日下午3:59:56
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/activity")
	public String activity(HttpServletRequest request,Model model){
		return "activity/activity.html";
	}
	/**
	 * 
	 * @方法功能说明：跳转到关于票量
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-16上午10:13:25
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/aboutPL")
	public String aboutPL(HttpServletRequest request,Model model){
		return "activity/company_intro.html";
	}
	/**
	 * 
	 * @方法功能说明：跳转到网站联盟
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-18下午5:31:31
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/aboutAlliance")
	public String aboutAlliance(HttpServletRequest request,Model model){
		return "activity/foot_alliance.html";
	}
	/**
	 * 
	 * @方法功能说明：跳转到投诉建议
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-18下午5:33:19
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/aboutComplaint")
	public String aboutComplaint(HttpServletRequest request,Model model){
		return "activity/foot_complaint.html";
	}
	/**
	 * 
	 * @方法功能说明：跳转到联系我们
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-18下午5:33:19
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/aboutContactUs")
	public String aboutContactUs(HttpServletRequest request,Model model){
		return "activity/foot_contactUs.html";
	}
	/**
	 * 
	 * @方法功能说明：跳转到法律声明
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-18下午5:33:19
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/aboutLaw")
	public String aboutLaw(HttpServletRequest request,Model model){
		return "activity/foot_law.html";
	}
	/**
	 * 
	 * @方法功能说明：跳转到诚聘英才
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-18下午5:33:19
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/aboutJob")
	public String aboutJob(HttpServletRequest request,Model model){
		return "activity/foot_job.html";
	}
	
	/**
	 * @方法功能说明：跳转到关于我们页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年11月11日下午5:19:08
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/aboutUs")
	public String aboutUs(HttpServletRequest request,Model model){
		return "activity/foot_instrodustion.html";
	}
	@RequestMapping(value = "/appDownLoad")
	public String downLoadAppFile(){
		return "appDownload/download.html";
	}
}
