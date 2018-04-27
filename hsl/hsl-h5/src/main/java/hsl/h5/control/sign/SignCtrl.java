package hsl.h5.control.sign;

import java.io.PrintWriter;
import java.util.Map;

import hg.log.util.HgLogger;
import hsl.app.service.local.sign.SignLocalService;
import hsl.h5.control.HslCtrl;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.SignQo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：签到Controller
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年9月18日上午9:59:07
 *
 */
@Controller
@RequestMapping(value="/activity")
public class SignCtrl extends HslCtrl{
	
	@Autowired
	private SignLocalService              signLocalService;

	/**
	 * 
	 * @方法功能说明：跳转到签到页面
	 * @修改者名字：hgg
	 * @修改时间：2015年9月18日上午10:00:39
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/sign")
	public String toSignActivityPage(Model model){
		
		model.addAttribute("activityName", "汇购6年庆");
		
		return "user/sign/sign";
	}
	
	/**
	 * 
	 * @方法功能说明：签到
	 * @修改者名字：hgg
	 * @修改时间：2015年9月18日上午10:04:58
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/userSign")
	public String sign(Model model,SignQo signQo){
		
		HgLogger.getInstance().info("hgg", "用户签到__开始");
		
		String message = "签到成功";
		
		try {
			signLocalService.sign(signQo);
		} catch (UserException e) {
			HgLogger.getInstance().info("hgg", "用户签到__异常__原因:"+e.getMessage());
			message = "签到失败";
		}
		
		HgLogger.getInstance().info("hgg", "用户签到__结束");
		
		return JSON.toJSONString(message);
	}
	
	/**
	 * 
	 * @方法功能说明：签到详情
	 * @修改者名字：hgg
	 * @修改时间：2015年9月18日上午10:17:03
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param signQo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/signDetail")
	public String signDetail(Model model, 
			@RequestParam(required=false, defaultValue="汇购6年庆") String activityName){
		
		HgLogger.getInstance().info("hgg", "查询签到详情__开始");
		
		Map<String,String> resultMap = signLocalService.signDetail(activityName);
		
		String totalCount = resultMap.get("totalCount");
		String alreadySignCount = resultMap.get("alreadySignCount");
		String noSignCount = resultMap.get("noSignCount");
		String noSignStr = resultMap.get("noSignStr");
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("alreadySignCount", alreadySignCount);
		model.addAttribute("noSignCount", noSignCount);
		model.addAttribute("noSignStr", noSignStr);
		
		HgLogger.getInstance().info("hgg", "查询签到详情__结束");
		
		return "user/sign/signDetail";
	}
	
}
