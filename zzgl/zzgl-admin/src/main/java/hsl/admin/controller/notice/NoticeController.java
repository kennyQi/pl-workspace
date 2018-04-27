package hsl.admin.controller.notice;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;
import hsl.pojo.command.CheckNoticeCommand;
import hsl.pojo.command.CreateNoticeCommand;
import hsl.pojo.command.DeleteNoticeCommand;
import hsl.pojo.command.UpdateNoticeCommand;
import hsl.pojo.dto.notice.NoticeDTO;
import hsl.pojo.exception.NoticeException;
import hsl.pojo.qo.notice.HslNoticeQO;
import hsl.spi.inter.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @类功能说明：公告管理
 * @类修改者：
 * @修改日期：2014年12月15日下午3:22:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月15日下午3:22:05
 * 
 */
@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;

	/**
	 * @方法功能说明：查询公告
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月15日下午3:27:58
	 * @修改内容：
	 * @throws
	 */
	@RequestMapping(value="/list")
	public Object queryNoticeList(HttpServletRequest request,Model model,
			@ModelAttribute HslNoticeQO noticeQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		// 添加分页参数
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? 1 : pageNo);
		pagination.setPageSize(pageSize == null ? 20 : pageSize);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		// 添加查询条件
		pagination.setCondition(noticeQO);
		// 分页查询
		pagination = noticeService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("noticeQO", noticeQO);
		model.addAttribute("nowDate", new Date());
		return "/notice/notice_list.html";
	}
	/**
	 * 添加公告页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addNotice")
	public String addNotice(@RequestParam(value="id",required=false) String id,Model model){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("nowDate",dateFormat.format(new Date()));
		return "notice/add_notice.html";
	}
	/**
	 * @方法功能说明：保存公告页面
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月18日下午4:09:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value="/saveNotice")
	@ResponseBody
	public Object saveNotice(HttpServletRequest request,Model model,@ModelAttribute CreateNoticeCommand command){
		try {
			AuthUser user = (AuthUser)request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			command.setIssueUser(user.getLoginName());
			noticeService.createNotice(command);
		} catch (NoticeException e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "notice");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "notice");
	}
	/**
	 * 跳转修改公告页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/modifyNotice")
	public String modifyNotice(@RequestParam(value="id",required=false) String id,Model model){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		HslNoticeQO noticeQO=new HslNoticeQO();
		noticeQO.setId(id);
		NoticeDTO noticeDto=noticeService.queryUnique(noticeQO);
		model.addAttribute("nowDate",dateFormat.format(new Date()));
		model.addAttribute("noticeDto",noticeDto);
		return "notice/modify_notice.html";
	}
	/**
	 * @方法功能说明：修改公告页面
	 * @修改者名字：chenxy
	 * @修改时间：2014年12月18日下午4:09:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value="/updateNotice")
	@ResponseBody
	public Object updateNotice(HttpServletRequest request,Model model,@ModelAttribute UpdateNoticeCommand command){
		try {
			noticeService.updateNotice(command);
		} catch (NoticeException e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "notice");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "notice");
	}
	/**
	 * 查看公告详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/details")
	public String queryNoticeDeatil(@RequestParam(value="id",required=false) String id,Model model){
		HslNoticeQO noticeQO=new HslNoticeQO();
		noticeQO.setId(id);
		NoticeDTO noticeDto=noticeService.queryUnique(noticeQO);
		model.addAttribute("noticeDto",noticeDto);
		return "notice/notice_details.html";
	}
	/**
	 * 删除公告
	 * @param command
	 * @param request
	 * @param model
	 * */
	@RequestMapping(value="/delete")
	@ResponseBody
	public  String  deleteNotice(HttpServletRequest request, Model model,
			@ModelAttribute DeleteNoticeCommand command){
		//删除公告
		try{
			noticeService.deleteNotice(command);
		}catch(Exception e){
			return DwzJsonResultUtil.createSimpleJsonString("500", "公告删除失败");
		}
		return DwzJsonResultUtil.createSimpleJsonString("200", "公告删除成功");
	}
	/**
	 * 批量删除记录
	 * @param 
	 * @param model
	 * @param request
	 * @param command
	 * */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public String deleteAllNotice(HttpServletRequest request, Model model){
			try {
				String ids = request.getParameter("ids");
			    String[] id = ids.split(",");
			    for(int i=0;i<id.length;i++){
			    	DeleteNoticeCommand  command = new DeleteNoticeCommand();
			    	command.setId(id[i]);
			    	noticeService.deleteNotice(command);
			    }
			} catch (Exception e) {
				return DwzJsonResultUtil.createSimpleJsonString("500", "公告删除失败");
			}
		return DwzJsonResultUtil.createSimpleJsonString("200", "公告删除成功");
	}
	/**
	 * 跳转审核公告页面
	 * @param command
	 * @param request
	 * @param model
	 * */
	@RequestMapping(value="/toCheck")
	public  String  toCheckNotice(@RequestParam(value="id",required=false) String id,Model model){
		//审核公告
		HslNoticeQO noticeQO=new HslNoticeQO();
		noticeQO.setId(id);
		NoticeDTO noticeDto=noticeService.queryUnique(noticeQO);
		model.addAttribute("noticeDto",noticeDto);
		return "notice/check_notice.html";
	}
	/**
	 * 审核公告
	 * @param command
	 * @param request
	 * @param model
	 * */
	@RequestMapping(value="/checkNotice")
	@ResponseBody
	public  String  checkNotice(HttpServletRequest request, Model model,
			@ModelAttribute CheckNoticeCommand command){
		//审核公告
		try{
			noticeService.checkNotice(command);
		}catch(Exception e){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "审核失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "notice");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "审核成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "notice");
	}
	public NoticeService getNoticeService() {
		return noticeService;
	}
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
}
