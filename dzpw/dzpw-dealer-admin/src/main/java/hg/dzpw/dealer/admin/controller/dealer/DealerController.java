package hg.dzpw.dealer.admin.controller.dealer;

import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.service.api.alipay.AlipaySignProtocolService;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.dealer.admin.component.manager.DealerSessionUserManager;
import hg.dzpw.dealer.admin.controller.BaseController;
import hg.log.util.HgLogger;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;


/**
 * @类功能说明：经销商controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-4下午3:36:33
 * @版本：V1.0
 */
@Controller
public class DealerController extends BaseController{
	
	@Autowired
	private DealerLocalService dealerService;
	
	@Autowired
	private AlipaySignProtocolService alipaySignProtocolService;
	
	@Autowired
	private HgLogger hgLogger;
	
	/**
	 * 
     * @描述：当前登录经销商信息查看 
     * @author: guotx 
     * @version: 2015-12-4 下午2:55:59
	 */
	@RequestMapping("/dealerInfo")
	public ModelAndView dealerInfo(HttpServletRequest request){
		String id =DealerSessionUserManager.getSessionUserId(request);
		DealerQo qo = new DealerQo(); 
		//判断查看经销商详情的id是否为空
		if(StringUtils.isNotBlank(id)){
			qo.setId(id);
		}
		Dealer dealer = this.dealerService.queryUnique(qo,true);
		
		// 代扣签约请求URL 
		String url = alipaySignProtocolService.buildSignProtocolURL();
		
		//返回的视图对象
		ModelAndView mav = new ModelAndView("/dealer/update_dealer.html");
		mav.addObject("dealer", dealer);
		mav.addObject("url", url);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/dealer/update")
	public String update(@RequestParam(value="dealerId", required=true)String dealerId,
						 @RequestParam(value="intro", required=true)String intro,
						 @RequestParam(value="businessKey", required=false)String businessKey,
						 @RequestParam(value="businessId", required=false)String businessId,
						 @RequestParam(value="accountNumber", required=true)String accountNumber){
		
		JSONObject o = new JSONObject(); 
		
		// 检查账号是否签约
		String xmlResult = alipaySignProtocolService.queryCustomerProtocol(accountNumber);
		
		if(xmlResult==null){
			
			o.put("message", "修改失败");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}else if(xmlResult.indexOf("<is_success>F</is_success>")>0){
			
			o.put("message", "请先签约代购协议");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		
		//修改时候校验是否有同名经销商
		DealerQo qo = new DealerQo();
		qo.setId(dealerId);
		Dealer dealer = this.dealerService.queryUnique(qo);
		if(dealer==null){
			o.put("message", "经销商不存在");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		dealer.getAccountInfo().setBusinessId(businessId);
		dealer.getAccountInfo().setBusinessKey(businessKey);
		dealer.getAccountInfo().setAccountNumber(accountNumber);
		dealer.getBaseInfo().setIntro(intro);
		
		try {
			dealerService.update(dealer);
			o.put("message", "修改成功");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
			o.put("navTabId", "dealerInfo");
		} catch(Exception e){
			
			e.printStackTrace();
			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", "修改失败");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		}
		return o.toJSONString();
	}
}
