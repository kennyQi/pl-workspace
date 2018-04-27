/**
 * @文件名称：TemplateController.java
 * @类路径：hgtech.jfaddmin.controller
 * @描述：规则模版管理
 * @作者：xinglj
 * @时间：2014年10月13日下午1:25:08
 */
package hgtech.jfadmin.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hg.common.page.Pagination;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfadmin.dto.SessionDto;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfadmin.service.CalFlowService;
import hgtech.jfadmin.service.JfCalService;
import hgtech.jfadmin.service.TemplateService;
import hgtech.jfcal.model.RuleTemplate;
import hgtech.jfcal.rulelogic.FindSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import ucenter.admin.controller.BaseController;

/**
 * @类功能说明：session 管理
 * @类修改者：
 * @修改日期：2014年10月31日下午1:25:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xiaoying
 * @创建时间：2014年10月13日下午1:25:08
 * 
 */
@Controller
@RequestMapping(value = "/session")
public class SessionController extends BaseController {

	public static final String navTabId = "sessionList";
	public static final String rel = "jbsxBoxSession";

	@Autowired
	JfCalService calService;
	@Autowired
	TemplateService templateService;
	/**
	 * 流水列表
	 * @param request
	 * @param model
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
  @RequestMapping(value = "/list")
  public String list(HttpServletRequest request, Model model,
			@ModelAttribute SessionDto dto) throws Exception
  {
	    String user = request.getParameter("user");
		List<SessionDto> querySession = calService.querySession(user);
	    if(querySession.size()>0)
	    {
				for(SessionDto s:querySession)
				{
			    	Map<String, String> m = new HashMap<String, String>();
					//获取模板路径
					RuleTemplate rt =  templateService.get(s.getTemplateCode());
					if (null != rt)
					{
						InputStream stream = null;
						//模板路径
						String file = rt.getSrcFile();
						stream = new FileInputStream(new File(file));
						m = FindSession.findMiddleData(stream);
					}
					if(m.containsKey(s.getPropName()))
					s.setPropName(m.get(s.getPropName()));
				}
	    }
	    model.addAttribute("dto", dto);
		model.addAttribute("list",querySession);
		return "/session/sessionList.html";
	     
  }
  
  
  
}
