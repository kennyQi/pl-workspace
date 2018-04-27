package hsl.h5.control;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.h5.base.result.mp.MPQueryPolicyResult;
import hsl.h5.base.result.mp.MPQueryScenicSpotsResult;
import hsl.h5.base.utils.OpenidTracker;
import hsl.h5.control.constant.Constants;
import hsl.h5.exception.HslapiException;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.ImageSpecDTO;
import hsl.pojo.dto.mp.PCScenicSpotDTO;
import hsl.pojo.dto.mp.PolicyDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslMPPolicyQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.spi.inter.mp.MPScenicSpotService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：门票Action
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zhuxy
 * @创建时间：2014年11月6日上午11:03:29
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping("mp")
public class MPCtrl extends HslCtrl {
	
	@Autowired
	private MPScenicSpotService mpScenicSpotService;

	@RequestMapping("init")
	public ModelAndView init() {
		HgLogger.getInstance().debug("zhuxy", newHeader("进入门票主页"));
		ModelAndView mav = new ModelAndView("mp/init");
		mav.addObject("openid", OpenidTracker.get());
		return mav;
	}

	/**
	 * 景点查询
	 * @param out
	 */
	@RequestMapping("list")
	public ModelAndView list(String content) {
		HgLogger.getInstance().debug("zhuxy", newHeader("门票列表页开始"));
		ModelAndView mav = new ModelAndView("mp/list");
		mav.addObject("openid", OpenidTracker.get());
		mav.addObject("content", content);
		HgLogger.getInstance().debug("zhuxy", newHeader("门票列表页结束"));
		return mav;
	}
	
	@RequestMapping("ajaxList")
	public void ajaxList(PrintWriter out, HslMPScenicSpotQO mpScenicSpotsQO) {
		HgLogger.getInstance().debug("zhuxy", newHeader("开始拉取景点列表数据"));
		try{
			MPQueryScenicSpotsResult scenicSpotsResponse = getScenicSpot(mpScenicSpotsQO);
			HgLogger.getInstance().info("zhuxy", newLog("拉取景点列表数据", "列表数据", JSON.toJSONString(scenicSpotsResponse)));
			out.print(JSON.toJSONString(scenicSpotsResponse));
		}catch(RuntimeException e){
			HgLogger.getInstance().error("zhuxy", newHeader("拉取景点列表数据出错")+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("拉取景点列表数据结束"));
	}
	
	/**
	 * 景点查询
	 * @param out
	 */
	@RequestMapping("detail")
	public ModelAndView detail(String scenicSpotId) {
		HgLogger.getInstance().debug("zhuxy", newHeader("景点详情方法开始"));
		ModelAndView mav = new ModelAndView("mp/detail");
		try {
			mav.addObject("openid", OpenidTracker.get());
			ScenicSpotDTO scenicSpot = getScenicSpotDetail(scenicSpotId);
			if (scenicSpot != null) {
				if (scenicSpot.getImages() != null) {
					List<ImageSpecDTO> images = scenicSpot.getImages();
					int size = images.size();
					if (size > 10) {
						size = 10;
						scenicSpot.setImages(images.subList(0, 10));
					}
					mav.addObject("imgCount", size);
				} else {
					mav.addObject("imgCount", 0);
				}
			}
			HgLogger.getInstance().info("zhuxy", newLog("景点详情方法", "景点信息", JSON.toJSONString(scenicSpot)));
			mav.addObject("scenicSpot", scenicSpot);
			mav.addObject("policies", getPolicyList(scenicSpotId));
		}  catch (RuntimeException e){
			HgLogger.getInstance().error("zhuxy", newHeader("景点详情方法出错")+HgLogger.getStackTrace(e));
			throw e;
		}catch (Exception e) {
			log.error("hsl.err", e);
			mav.setViewName("error");
			HgLogger.getInstance().error("zhuxy", newHeader("景点详情方法出错")+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("景点详情方法结束"));
		return mav;
	}

	/**
	 * 查询景点详情
	 * @param scenicSpotId
	 * @return
	 * @throws HslapiException 
	 */
	private ScenicSpotDTO getScenicSpotDetail(String scenicSpotId) throws HslapiException {
		HslMPScenicSpotQO mpScenicSpotsQO = new HslMPScenicSpotQO();
		mpScenicSpotsQO.setScenicSpotId(scenicSpotId);
		mpScenicSpotsQO.setImagesFetchAble(true);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(true);
		MPQueryScenicSpotsResult scenicSpotsResponse = getScenicSpot(mpScenicSpotsQO);
		if (success(scenicSpotsResponse)) {
			return scenicSpotsResponse.getScenicSpots().get(0);
		} else {
			HgLogger.getInstance().error("zhuxy", "移动端景点详情查询失败："+scenicSpotsResponse.getMessage());
			throw new HslapiException(scenicSpotsResponse.getMessage());
		}
	}


	/**
	 * 查询景点的方法
	 * @param mpScenicSpotsQO
	 * @param scenicSpotsResponse
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private MPQueryScenicSpotsResult getScenicSpot(HslMPScenicSpotQO mpScenicSpotsQO) {
		HslMPScenicSpotQO hslMPScenicSpotQO = BeanMapperUtils.map(mpScenicSpotsQO, HslMPScenicSpotQO.class);
		MPQueryScenicSpotsResult scenicSpotsResponse = new MPQueryScenicSpotsResult();
		Map scenicSpotMap=null;
		try {
			scenicSpotMap = mpScenicSpotService.queryScenicSpot(hslMPScenicSpotQO);
			List<ScenicSpotDTO> scenicSpotDTOList;
			//是热门景点的话需要修改id
			if(mpScenicSpotsQO.getHot()){
				List<HotScenicSpotDTO> hotlist = (List<HotScenicSpotDTO>)scenicSpotMap.get("dto");
				scenicSpotDTOList = new ArrayList<ScenicSpotDTO>();
				if(hotlist!=null&&hotlist.size()>0){
					ScenicSpotDTO scenicSpotDTO;
					for(HotScenicSpotDTO hot: hotlist){
						scenicSpotDTO = new ScenicSpotDTO();
						scenicSpotDTO.setId(hot.getScenicSpotId());
						scenicSpotDTO.setScenicSpotBaseInfo(hot.getScenicSpotBaseInfo());
						scenicSpotDTOList.add(scenicSpotDTO);
					}
				}
			}else{
				List<PCScenicSpotDTO> pcScenicSpotDTOList = (List<PCScenicSpotDTO>) scenicSpotMap.get("dto");
				scenicSpotDTOList =BeanMapperUtils.getMapper().mapAsList(pcScenicSpotDTOList, ScenicSpotDTO.class);
			}
			if(scenicSpotDTOList != null && scenicSpotDTOList.size() > 0){
				scenicSpotsResponse.setScenicSpots(scenicSpotDTOList);
				scenicSpotsResponse.setTotalCount(Integer.parseInt(scenicSpotMap.get("count").toString()));				
			}else{
				scenicSpotsResponse.setScenicSpots(null);
				scenicSpotsResponse.setTotalCount(0);
				scenicSpotsResponse.setMessage("没有数据");
			}
		} catch (MPException e) {
			scenicSpotsResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			scenicSpotsResponse.setMessage(e.getMessage());
		}
		return scenicSpotsResponse;
	}
	
	/**
	 * 根据景点id查询政策
	 * @param scenicSpotId
	 * @param policies
	 * @return
	 * @throws HslapiException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<PolicyDTO> getPolicyList(String scenicSpotId) throws HslapiException {
		HslMPPolicyQO mpPolicyQO = new HslMPPolicyQO();
		mpPolicyQO.setScenicSpotId(scenicSpotId);
		HslMPPolicyQO hslMPPolicyQO=BeanMapperUtils.map(mpPolicyQO, HslMPPolicyQO.class);
		MPQueryPolicyResult policyResponse = new MPQueryPolicyResult();
		Map policyMap = mpScenicSpotService.queryScenicPolicy(hslMPPolicyQO);
		policyResponse.setPolicies((List<PolicyDTO>)policyMap.get("dto"));
		policyResponse.setTotalCount(Integer.parseInt(policyMap.get("count").toString()));
		if (success(policyResponse)) {
			return policyResponse.getPolicies();
		} else {
			HgLogger.getInstance().error("zhuxy", "汇商旅移动端————》门票下单————》：景点政策查询失败");
			throw new HslapiException(policyResponse.getMessage());
		}
	}
	
}
