package hsl.admin.controller.weixin;

import hg.common.util.DwzJsonResultUtil;
import hsl.app.service.local.sys.HSLConfigLocalService;
import hsl.domain.model.sys.HSLConfig;
import hsl.domain.model.sys.wx.WxAutoReplyConfig;
import hsl.domain.model.sys.wx.WxMenuCoinfig;
import hsl.pojo.command.config.wx.ModifyWxAutoReplyConfigCommand;
import hsl.pojo.command.config.wx.ModifyWxMenuConfigCommand;
import hsl.pojo.qo.sys.HSLConfigQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @类功能说明：微信操作Controller
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年10月26日下午5:34:44
 *
 */
@Controller
@RequestMapping(value="/admin/weChat")
public class WeChatController {


	@Autowired
	private HSLConfigLocalService              localService;
	
	
	/**
	 * 
	 * @方法功能说明：跳转到消息自动回复页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月26日下午5:37:01
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/autoReply")
	public String messageAutoReply(Model model){
		
		queryAutoReply(model);
		
		return "/weixin/autoReply.html";
	}
	
	/**
	 * 
	 * @方法功能说明：消息自动回复确认
	 * @修改者名字：hgg
	 * @修改时间：2015年10月27日上午9:24:22
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param configCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/autoReplySubmmit")
	@ResponseBody
	public String autoReplySubmmit(Model model,ModifyWxAutoReplyConfigCommand configCommand){
		
		localService.modifyWxAutoReplyConfig(configCommand);
		
		queryAutoReply(model);
		
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "保存成功", DwzJsonResultUtil.FLUSH_FORWARD, "autoReply");
	}
	
	/**
	 * 
	 * @方法功能说明：自动回复操作
	 * @修改者名字：hgg
	 * @修改时间：2015年11月3日下午4:06:52
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/customMenu")
	public String customMenu(Model model){
		
		queryCustomMenu(model);
		
		return "/weixin/customMenu.html";
	}
	
	/**
	 * 
	 * @方法功能说明：保存微信自定义菜单
	 * @修改者名字：hgg
	 * @修改时间：2015年11月4日上午10:29:00
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/saveCustomMenu")
	@ResponseBody
	public String saveCustomMenu(Model model,@RequestBody ModifyWxMenuConfigCommand command){
		
		localService.modifyWxMenuConfig(command);
		queryCustomMenu(model);
		
		return "/weixin/customMenu.html";
		
	}
	
	/**
	 * 
	 * @方法功能说明：查询微信菜单
	 * @修改者名字：hgg
	 * @修改时间：2015年10月27日上午10:13:24
	 * @修改内容：
	 * @参数：@param model
	 * @return:void
	 * @throws
	 */
	private void  queryCustomMenu(Model model){
		
		HSLConfigQO qo = new HSLConfigQO();
		qo.setId(HSLConfig.KEY_WX_MENU);
		HSLConfig config = localService.queryUnique(qo);
		if(config != null){
			WxMenuCoinfig wxMenuCoinfig = config.manager().parseAndGetValue(WxMenuCoinfig.class);
			model.addAttribute("wxMenuCoinfig", wxMenuCoinfig.getButton());
		}
	}
	
	/**
	 * 
	 * @方法功能说明：查询消息自动回复
	 * @修改者名字：hgg
	 * @修改时间：2015年10月27日上午10:13:24
	 * @修改内容：
	 * @参数：@param model
	 * @return:void
	 * @throws
	 */
	private void  queryAutoReply(Model model){
		
		HSLConfigQO qo = new HSLConfigQO();
		qo.setId(HSLConfig.KEY_WX_REPLY);
		HSLConfig config = localService.queryUnique(qo);
		if(config != null){
			WxAutoReplyConfig wxAutoReply = config.manager().parseAndGetValue(WxAutoReplyConfig.class);
			model.addAttribute("wxAutoReply", wxAutoReply);
		}
	}
	
}
