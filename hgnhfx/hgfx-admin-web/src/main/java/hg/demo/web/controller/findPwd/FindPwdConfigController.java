package hg.demo.web.controller.findPwd;

import java.util.regex.Pattern;

import hg.demo.member.common.domain.model.system.DwzJsonResultUtil;
import hg.demo.web.component.cache.FindPwdManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

@Controller
public class FindPwdConfigController {
	
	@Autowired
	private FindPwdManager findPwdCache;
	
	/** 次数判断 */
	private static final String patten = "[1-9]{1,}\\d*";
	
	
	@RequestMapping("/findpwdtimes/config")
	public ModelAndView findConfig(){
		
		ModelAndView mav = new ModelAndView("/login/findPwdConfig.html");

		Integer times = findPwdCache.getFindPwdTimes();
		mav.addObject("findPwdTimes", times);
		
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping("/findpwdtimes/modify")
	public String modifyTimes(@RequestParam(value="findPwdTimes", required = false)String times){
		
		JSONObject o = new JSONObject();
		
		if (StringUtils.isBlank(times)){
			o.put("message", "请输入限制次数");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		
		if (!Pattern.compile(patten).matcher(times).matches()){
			o.put("message", "限制次数格式错误");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		
		
		findPwdCache.setFindPwdTimes(Integer.valueOf(times));
		
		o.put("message", "修改成功");
		o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
		o.put("navTabId", "findPwdConfig");
		return o.toJSONString();
	}
	
}
