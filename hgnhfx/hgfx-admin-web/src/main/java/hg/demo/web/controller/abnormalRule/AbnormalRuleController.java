package hg.demo.web.controller.abnormalRule;

import java.util.regex.Pattern;

import hg.demo.member.common.domain.model.system.DwzJsonResultUtil;
import hg.fx.command.abnormalRule.ModifyAbnormalRuleCommand;
import hg.fx.domain.AbnormalRule;
import hg.fx.spi.AbnormalRuleSPI;
import hg.fx.spi.qo.AbnormalRuleSQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 *  异常订单规则
 * @author yangkang
 * @date 2016-6-1上午9:56:50
 * */
@Controller
public class AbnormalRuleController {
	
	@Autowired
	private AbnormalRuleSPI abnormalRuleService;
	
	/** 里程数  次数判断 */
	private static final String patten = "[1-9]{1,}\\d*";
	
	
	@RequestMapping("/abnormalRule/list")
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView("/abnormalRule/abnormalRuleList.html");
		
		AbnormalRule rule = abnormalRuleService.queryUnique(new AbnormalRuleSQO());
		mav.addObject("rule", rule);
		
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping("/abnormalRule/modify")
	public String modifyAbnormalRule(@RequestParam(value="ruleId", required = false)String ruleId,
									 @RequestParam(value="dayMax", required = false)String dayMax, 
									 @RequestParam(value="mouthMax", required = false)String mouthMax,
									 @RequestParam(value="orderUnitMax", required = false)String orderUnitMax){
		
		JSONObject o = new JSONObject(); 
		ModifyAbnormalRuleCommand command = new ModifyAbnormalRuleCommand();
		
		command.setId(ruleId);
		
		if (StringUtils.isNotBlank(dayMax)){
			dayMax = dayMax.trim();
			if(dayMax.length()>10 || !Pattern.compile(patten).matcher(dayMax).matches()){
				o.put("message", "请输入正确格式");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			
			command.setDayMax(Integer.valueOf(dayMax));
		}
			
		if (StringUtils.isNotBlank(mouthMax)){
			mouthMax = mouthMax.trim();
			if(dayMax.length()>10 || !Pattern.compile(patten).matcher(mouthMax).matches()){
				o.put("message", "请输入正确格式");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			
			command.setMouthMax(Integer.valueOf(mouthMax));
		}
			
		if (StringUtils.isNotBlank(orderUnitMax)){
			orderUnitMax = orderUnitMax.trim();
			if(dayMax.length()>10 || !Pattern.compile(patten).matcher(orderUnitMax).matches()){
				o.put("message", "请输入正确格式");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
			
			command.setOrderUnitMax(Long.valueOf(orderUnitMax));
		}
			
		abnormalRuleService.modifyAbnormalRule(command);
		
		o.put("message", "修改成功");
		o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
		o.put("navTabId", "abnormalRuleList");
		
		return o.toJSONString();
		
	}
	
}
